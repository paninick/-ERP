-- phase23: compatibility migration from style_no to style_code
-- Goals:
--   1. Add style_code to legacy tables that still need backfill.
--   2. Copy historical style_no values into style_code.
--   3. Keep style_no during the transition window.
-- Dependencies:
--   - sql/_helpers.sql
--   - phase20_product_traceability.sql
--   - phase22_style_no_dual_key.sql

SET NAMES utf8mb4;

CALL sp_erp_add_column('t_erp_check', 'style_code', 'VARCHAR(50) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_sample_tech', 'style_code', 'VARCHAR(50) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_sales_order', 'style_code', 'VARCHAR(32) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_produce_plan', 'style_code', 'VARCHAR(32) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('t_erp_qc_inspection', 'style_code', 'VARCHAR(64) DEFAULT NULL COMMENT ''style code compatibility column''');
CALL sp_erp_add_column('demo_schedule', 'style_code', 'VARCHAR(50) DEFAULT NULL COMMENT ''style code compatibility column''');

UPDATE t_erp_check
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

UPDATE t_erp_sample_tech
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

UPDATE t_erp_sales_order
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

UPDATE t_erp_produce_plan
SET style_code = style_no
WHERE style_code IS NULL AND style_no IS NOT NULL;

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

SELECT 't_erp_check.style_code' AS check_item, COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_check'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_sample_tech.style_code' AS check_item, COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sample_tech'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_sales_order.style_code' AS check_item, COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sales_order'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_produce_plan.style_code' AS check_item, COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_produce_plan'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_qc_inspection.style_code' AS check_item, COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_qc_inspection'
  AND COLUMN_NAME = 'style_code';

SELECT 'demo_schedule.style_code' AS check_item, COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 'demo_schedule'
  AND COLUMN_NAME = 'style_code';
