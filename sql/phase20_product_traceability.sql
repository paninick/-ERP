-- phase20: product traceability with style_code
-- SalesOrder -> ProducePlan -> ProduceJob -> ProductSerial

SET NAMES utf8mb4;

CALL sp_erp_add_column(
  't_erp_sales_order',
  'style_code',
  'VARCHAR(32) DEFAULT NULL COMMENT ''工厂内部款号（格式 KN-YY-SS-NNN）'''
);

CALL sp_erp_add_column(
  't_erp_produce_plan',
  'style_code',
  'VARCHAR(32) DEFAULT NULL COMMENT ''关联 SalesOrder.style_code 的生产款号'''
);

CREATE OR REPLACE VIEW v_erp_product_trace AS
SELECT
  so.id AS sales_order_id,
  so.sales_no,
  so.style_code,
  so.bulk_order_no,
  so.customer_name,
  pp.id AS produce_plan_id,
  pp.plan_no,
  pj.id AS produce_job_id,
  pj.job_no,
  pj.color_code,
  pj.size_code,
  pj.plan_qty,
  pj.actual_qty,
  ps.id AS serial_id,
  ps.serial_no,
  ps.status AS serial_status,
  CASE ps.status
    WHEN '0' THEN '在制'
    WHEN '1' THEN '已完工'
    WHEN '2' THEN '已入库'
    WHEN '3' THEN '已出货'
    ELSE ps.status
  END AS serial_status_name,
  ps.current_process_name,
  ps.create_time AS serial_create_time,
  ps.finish_time,
  ps.warehouse_time,
  ps.ship_time
FROM t_erp_sales_order so
LEFT JOIN t_erp_produce_plan pp ON pp.sales_order_id = so.id
LEFT JOIN t_erp_produce_job pj ON pj.produce_plan_id = pp.id
LEFT JOIN t_erp_product_serial ps ON ps.order_id = so.id AND ps.job_id = pj.id;

CREATE OR REPLACE VIEW v_erp_style_progress AS
SELECT
  so.style_code,
  so.bulk_order_no,
  so.customer_name,
  so.sales_no,
  COUNT(DISTINCT pj.id) AS total_jobs,
  SUM(pj.plan_qty) AS total_plan_qty,
  SUM(pj.actual_qty) AS total_actual_qty,
  ROUND(SUM(pj.actual_qty) * 100.0 / NULLIF(SUM(pj.plan_qty), 0), 1) AS complete_rate_pct,
  COUNT(CASE WHEN ps.status = '3' THEN 1 END) AS shipped_qty,
  MIN(so.due_date) AS due_date
FROM t_erp_sales_order so
LEFT JOIN t_erp_produce_plan pp ON pp.sales_order_id = so.id
LEFT JOIN t_erp_produce_job pj ON pj.produce_plan_id = pp.id
LEFT JOIN t_erp_product_serial ps ON ps.order_id = so.id AND ps.job_id = pj.id
WHERE so.style_code IS NOT NULL
GROUP BY so.style_code, so.bulk_order_no, so.customer_name, so.sales_no, so.due_date;

SELECT 'style_code added to sales_order' AS check_item,
       COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sales_order'
  AND COLUMN_NAME = 'style_code';
