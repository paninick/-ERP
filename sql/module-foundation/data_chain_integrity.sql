-- 全链路数据关联补全：销售 → 计划 → 生产 → 报工 → 消耗 → 工资 → 质检 → 出货
-- 幂等，可重复执行

-- 1. produce_job 补 style_code + sales_order_id（直连销售订单，免多表JOIN）
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job' AND COLUMN_NAME='style_code');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_produce_job ADD COLUMN style_code VARCHAR(50) DEFAULT NULL COMMENT ''款号(冗余，加速追溯)'' AFTER produce_plan_id', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job' AND COLUMN_NAME='sales_order_id');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_produce_job ADD COLUMN sales_order_id BIGINT DEFAULT NULL COMMENT ''销售订单ID(冗余)'' AFTER style_code', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- 2. qc_inspection 补 job_id + process_id（质检关联到具体生产单和工序）
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='job_id');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_qc_inspection ADD COLUMN job_id BIGINT DEFAULT NULL COMMENT ''生产单ID'' AFTER style_code', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='process_id');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_qc_inspection ADD COLUMN process_id BIGINT DEFAULT NULL COMMENT ''工序ID'' AFTER job_id', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- 3. produce_job 补 created_from（源头单据类型）
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job' AND COLUMN_NAME='created_from');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_produce_job ADD COLUMN created_from VARCHAR(30) DEFAULT ''PLAN'' COMMENT ''来源(PLAN/MANUAL/SPLIT)'' AFTER sales_order_id', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- 4. 补索引
SET @c = (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job' AND INDEX_NAME='idx_job_style');
SET @s = IF(@c=0, 'CREATE INDEX idx_job_style ON t_erp_produce_job (style_code)', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND INDEX_NAME='idx_qc_job');
SET @s = IF(@c=0, 'CREATE INDEX idx_qc_job ON t_erp_qc_inspection (job_id)', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- 5. 全链路追溯视图重建
DROP VIEW IF EXISTS v_erp_full_trace;
CREATE VIEW v_erp_full_trace AS
SELECT
    so.sales_no,
    so.style_code,
    so.customer_name,
    so.create_time AS sales_date,
    pp.plan_no,
    pp.plan_status,
    pj.job_no,
    pj.status AS job_status,
    pj.confirm_qty AS job_completed_qty,
    jp.process_id,
    pd.process_name,
    jp.process_status,
    rl.id AS report_log_id,
    rl.completed_qty AS reported_qty,
    rl.defect_qty AS reported_defect_qty,
    rl.employee_name AS operator,
    rl.event_time AS report_time,
    pmc.id AS material_consume_id,
    pmc.material_name,
    pmc.actual_qty AS material_used,
    qc.id AS qc_inspection_id,
    qc.result AS qc_result,
    qc.pass_rate AS qc_pass_rate,
    ib.booking_no AS inspection_booking_no,
    ib.status AS inspection_status,
    sp.shipment_no,
    sp.total_qty AS shipped_qty,
    sp.status AS shipment_status,
    sp.release_status
FROM t_erp_sales_order so
LEFT JOIN t_erp_produce_plan pp ON pp.bulk_order_no = so.sales_no OR pp.sales_order_id = so.id
LEFT JOIN t_erp_produce_job pj ON pj.produce_plan_id = pp.id
LEFT JOIN t_erp_produce_job_process jp ON jp.job_id = pj.id
LEFT JOIN t_erp_process_def pd ON pd.id = jp.process_id
LEFT JOIN t_erp_produce_report_log rl ON rl.job_process_id = jp.id AND rl.del_flag = '0'
LEFT JOIN t_erp_produce_material_consume pmc ON pmc.report_log_id = rl.id
LEFT JOIN t_erp_qc_inspection qc ON qc.job_id = pj.id
LEFT JOIN t_erp_inspection_booking ib ON ib.sales_no = so.sales_no
LEFT JOIN t_erp_shipment sp ON sp.sales_no = so.sales_no;

SELECT '数据链路完整性补全 完成' AS result;
