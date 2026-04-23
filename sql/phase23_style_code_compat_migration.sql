-- phase23: P3.1 款号字段兼容迁移（style_no -> style_code）
-- 目标:
--   1. 为仍使用 style_no 的业务表补齐 style_code
--   2. 将历史数据从 style_no 回填到 style_code
--   3. 保留 style_no 作为过渡期兼容列，避免分批改造时中断业务
-- 依赖:
--   - sql/_helpers.sql 已先执行
--   - phase20_product_traceability.sql / phase22_style_no_dual_key.sql 已执行

SET NAMES utf8mb4;

-- ============================================================
-- 1. 为 legacy 表补齐 style_code 列
-- ============================================================
CALL sp_erp_add_column(
  't_erp_check',
  'style_code',
  'VARCHAR(50) DEFAULT NULL COMMENT ''款号兼容字段，迁移期与 style_no 并存'''
);

CALL sp_erp_add_column(
  't_erp_sample_tech',
  'style_code',
  'VARCHAR(50) DEFAULT NULL COMMENT ''款号兼容字段，迁移期与 style_no 并存'''
);

CALL sp_erp_add_column(
  't_erp_sales_order',
  'style_code',
  'VARCHAR(32) DEFAULT NULL COMMENT ''工厂内部款号（兼容字段，最终替代 style_no）'''
);

CALL sp_erp_add_column(
  't_erp_produce_plan',
  'style_code',
  'VARCHAR(32) DEFAULT NULL COMMENT ''工厂内部款号（兼容字段，最终替代 style_no）'''
);

-- ============================================================
-- 2. 回填历史数据
-- ============================================================
UPDATE t_erp_check
SET style_code = style_no
WHERE style_code IS NULL
  AND style_no IS NOT NULL;

UPDATE t_erp_sample_tech
SET style_code = style_no
WHERE style_code IS NULL
  AND style_no IS NOT NULL;

UPDATE t_erp_sales_order
SET style_code = style_no
WHERE style_code IS NULL
  AND style_no IS NOT NULL;

UPDATE t_erp_produce_plan
SET style_code = style_no
WHERE style_code IS NULL
  AND style_no IS NOT NULL;

-- ============================================================
-- 3. 补齐 style_code 索引
-- ============================================================
CALL sp_erp_add_index('t_erp_check', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_sample_tech', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_sales_order', 'idx_style_code', '(style_code)');
CALL sp_erp_add_index('t_erp_produce_plan', 'idx_style_code', '(style_code)');

-- ============================================================
-- 4. 验证
-- ============================================================
SELECT 't_erp_check.style_code' AS check_item,
       COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_check'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_sample_tech.style_code' AS check_item,
       COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sample_tech'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_sales_order.style_code' AS check_item,
       COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sales_order'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_produce_plan.style_code' AS check_item,
       COUNT(*) AS has_column
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_produce_plan'
  AND COLUMN_NAME = 'style_code';

SELECT 't_erp_check.backfill' AS check_item,
       COUNT(*) AS copied_rows
FROM t_erp_check
WHERE style_code IS NOT NULL;

SELECT 't_erp_sample_tech.backfill' AS check_item,
       COUNT(*) AS copied_rows
FROM t_erp_sample_tech
WHERE style_code IS NOT NULL;

SELECT 't_erp_sales_order.backfill' AS check_item,
       COUNT(*) AS copied_rows
FROM t_erp_sales_order
WHERE style_code IS NOT NULL;

SELECT 't_erp_produce_plan.backfill' AS check_item,
       COUNT(*) AS copied_rows
FROM t_erp_produce_plan
WHERE style_code IS NOT NULL;
