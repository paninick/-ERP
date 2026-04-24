-- 2026-04-23 schema compatibility hotfix
-- Purpose:
-- 1. Backfill style_code compatibility columns for legacy ERP tables.
-- 2. Copy historical style_no values into style_code during the transition window.
-- 3. Restore the missing unit conversion table required by the ERP basic-data page.
--
-- Usage:
--   mysql --default-character-set=utf8mb4 -uroot ry_vue < sql/hotfix_20260423_schema_compat_fix.sql

SET NAMES utf8mb4;

CALL sp_erp_add_column('t_erp_check', 'style_code', 'VARCHAR(50) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_sample_tech', 'style_code', 'VARCHAR(50) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_sales_order', 'style_code', 'VARCHAR(32) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_produce_plan', 'style_code', 'VARCHAR(32) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_qc_inspection', 'style_code', 'VARCHAR(64) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('demo_schedule', 'style_code', 'VARCHAR(50) DEFAULT NULL COMMENT ''style code compatibility column''');

ALTER TABLE t_erp_sales_order MODIFY COLUMN style_code VARCHAR(64) DEFAULT NULL COMMENT 'style code compatibility column';
ALTER TABLE t_erp_produce_plan MODIFY COLUMN style_code VARCHAR(64) DEFAULT NULL COMMENT 'style code compatibility column';

UPDATE t_erp_check
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

UPDATE t_erp_sample_tech
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

UPDATE t_erp_sales_order
SET style_code = COALESCE(style_no, sample_style_no)
WHERE style_code IS NULL AND (style_no IS NOT NULL OR sample_style_no IS NOT NULL);

UPDATE t_erp_produce_plan
SET style_code = COALESCE(style_no, sample_style_no)
WHERE style_code IS NULL AND (style_no IS NOT NULL OR sample_style_no IS NOT NULL);

UPDATE t_erp_qc_inspection
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

UPDATE demo_schedule
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

CALL sp_erp_add_index('t_erp_check', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_sample_tech', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_sales_order', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_produce_plan', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_qc_inspection', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('demo_schedule', 'idx_style_code', '(style_code)');

CREATE TABLE IF NOT EXISTS `t_erp_unit_conversion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '换算ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `purchase_unit` varchar(20) NOT NULL COMMENT '采购单位',
  `stock_unit` varchar(20) NOT NULL COMMENT '库存单位',
  `produce_unit` varchar(20) NOT NULL COMMENT '生产单位',
  `purchase_to_stock` decimal(15,6) NOT NULL COMMENT '采购转库存换算率',
  `stock_to_produce` decimal(15,6) NOT NULL COMMENT '库存转生产换算率',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单位换算表';

CREATE TABLE IF NOT EXISTS `t_erp_process_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工价ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '员工ID（为空表示通用价）',
  `price` decimal(10,4) NOT NULL COMMENT '工价（每件/每公斤）',
  `effective_date` date NOT NULL COMMENT '生效日期',
  `expire_date` date DEFAULT NULL COMMENT '失效日期',
  `status` char(1) DEFAULT '0' COMMENT '状态（0有效 1失效）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_process_id` (`process_id`),
  KEY `idx_employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序工价表';

CALL sp_erp_add_column('t_erp_purchase_item', 'execute_qty', 'decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT ''已执行数量（已入库）''');
CALL sp_erp_add_column('t_erp_sales_order_item', 'execute_qty', 'decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT ''已执行数量（已出库）''');
CALL sp_erp_add_column('t_erp_stock_log', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `relation_id`');
CALL sp_erp_add_column('t_erp_stock_log', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_stock_log', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');
CALL sp_erp_add_column('t_erp_stock_log', 'sku_id', 'bigint(20) DEFAULT NULL COMMENT ''SKU ID'' AFTER `material_id`');
CALL sp_erp_add_column('t_erp_sample_tech_cost', 'unit_consumption', 'decimal(18,6) DEFAULT NULL COMMENT ''单耗'' AFTER `color`');

CREATE OR REPLACE VIEW v_erp_product_trace AS
SELECT
  so.id AS sales_order_id,
  so.sales_no,
  COALESCE(so.style_code, so.style_no, so.sample_style_no) AS style_code,
  so.style_no AS legacy_style_no,
  so.sample_style_no,
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
  COALESCE(so.style_code, so.style_no, so.sample_style_no) AS style_code,
  so.style_no AS legacy_style_no,
  so.sample_style_no,
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
WHERE COALESCE(so.style_code, so.style_no, so.sample_style_no) IS NOT NULL
GROUP BY
  COALESCE(so.style_code, so.style_no, so.sample_style_no),
  so.style_no,
  so.sample_style_no,
  so.bulk_order_no,
  so.customer_name,
  so.sales_no,
  so.due_date;
