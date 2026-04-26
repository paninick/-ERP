-- P5: 管理驾驶舱视图
-- 基于现有表结构实际列名

-- P5-1: 订单进度看板
DROP VIEW IF EXISTS v_erp_order_progress;
CREATE VIEW v_erp_order_progress AS
SELECT
    so.id AS sales_order_id,
    so.sales_no,
    so.style_code,
    so.customer_name,
    COUNT(DISTINCT pp.id) AS plan_count,
    COUNT(DISTINCT pj.id) AS job_count,
    COALESCE(SUM(pj.plan_qty), 0) AS planned_qty,
    COALESCE(SUM(pj.confirm_qty), 0) AS confirmed_qty,
    COALESCE(SUM(sp.total_qty), 0) AS shipped_qty,
    CASE WHEN COALESCE(SUM(sp.total_qty), 0) > 0 THEN 'SHIPPED'
         WHEN COALESCE(SUM(pj.confirm_qty), 0) > 0 THEN 'CONFIRMED'
         WHEN COUNT(DISTINCT pj.id) > 0 THEN 'IN_PRODUCTION'
         WHEN COUNT(DISTINCT pp.id) > 0 THEN 'PLANNED'
         ELSE 'PENDING' END AS progress_status,
    so.create_time
FROM t_erp_sales_order so
LEFT JOIN t_erp_produce_plan pp ON pp.bulk_order_no = so.sales_no
LEFT JOIN t_erp_produce_job pj ON pj.produce_plan_id = pp.id
LEFT JOIN t_erp_shipment sp ON sp.sales_no = so.sales_no
GROUP BY so.id, so.sales_no, so.style_code, so.customer_name, so.create_time;

-- P5-2: 款号进度
DROP VIEW IF EXISTS v_erp_style_progress;
CREATE VIEW v_erp_style_progress AS
SELECT
    s.style_code,
    s.style_name,
    COUNT(DISTINCT so.id) AS order_count,
    COALESCE(SUM(pj.plan_qty), 0) AS total_plan_qty,
    COALESCE(SUM(pj.confirm_qty), 0) AS total_confirm_qty
FROM t_erp_style s
LEFT JOIN t_erp_sales_order so ON so.style_code = s.style_code
LEFT JOIN t_erp_produce_plan pp ON pp.bulk_order_no = so.sales_no
LEFT JOIN t_erp_produce_job pj ON pj.produce_plan_id = pp.id
GROUP BY s.style_code, s.style_name;

-- P5-3: 工厂产能看板
DROP VIEW IF EXISTS v_erp_capacity;
CREATE VIEW v_erp_capacity AS
SELECT
    pd.id AS process_id,
    pd.process_name,
    COUNT(DISTINCT jp.job_id) AS active_jobs,
    COALESCE(SUM(jp.plan_qty), 0) AS total_plan_qty,
    COALESCE(SUM(jp.confirm_qty), 0) AS total_confirm_qty,
    COUNT(DISTINCT jp.employee_id) AS active_workers,
    CASE WHEN COALESCE(SUM(jp.plan_qty), 0) > 0
         THEN ROUND(COALESCE(SUM(jp.confirm_qty), 0) / SUM(jp.plan_qty) * 100, 1)
         ELSE 0 END AS completion_rate
FROM t_erp_produce_job_process jp
LEFT JOIN t_erp_process_def pd ON pd.id = jp.process_id
WHERE jp.status NOT IN ('COMPLETED', 'CANCELLED')
GROUP BY pd.id, pd.process_name;

-- P5-4: 异常闭环
DROP VIEW IF EXISTS v_erp_abnormal_closure;
CREATE VIEW v_erp_abnormal_closure AS
SELECT
    biz_type AS source_type,
    abnormal_level,
    COUNT(*) AS total_count,
    SUM(CASE WHEN status = '0' THEN 1 ELSE 0 END) AS open_count,
    SUM(CASE WHEN status = '1' THEN 1 ELSE 0 END) AS handling_count,
    SUM(CASE WHEN status = '2' THEN 1 ELSE 0 END) AS closed_count,
    CASE WHEN COUNT(*) > 0
         THEN ROUND(SUM(CASE WHEN status = '2' THEN 1 ELSE 0 END) / COUNT(*) * 100, 1)
         ELSE 0 END AS closure_rate
FROM t_erp_biz_abnormal_pool
GROUP BY biz_type, abnormal_level;

-- P5-5: 日单放行看板
DROP VIEW IF EXISTS v_erp_japan_release;
CREATE VIEW v_erp_japan_release AS
SELECT
    ib.id AS booking_id,
    ib.booking_no,
    ib.sales_no,
    ib.style_code,
    ic.company_name AS inspection_company,
    ib.booking_date,
    ib.inspection_date,
    ib.inspection_result,
    ib.status,
    DATEDIFF(CURDATE(), ib.booking_date) AS days_since_booking
FROM t_erp_inspection_booking ib
LEFT JOIN t_erp_inspection_company ic ON ic.id = ib.inspection_company_id;

-- P5-6: 成本偏差
DROP VIEW IF EXISTS v_erp_cost_variance;
CREATE VIEW v_erp_cost_variance AS
SELECT
    cs.produce_plan_id,
    cs.style_code,
    cs.sales_no,
    cs.material_cost,
    cs.wage_cost,
    cs.outsource_cost,
    cs.freight_cost,
    cs.quality_loss,
    cs.other_cost,
    cs.total_cost,
    cs.finish_qty,
    cs.unit_cost,
    cs.period
FROM t_erp_cost_summary cs
WHERE cs.total_cost > 0;

SELECT 'P5: 6个管理驾驶舱视图 创建完成' AS result;
