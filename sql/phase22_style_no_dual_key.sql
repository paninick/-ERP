-- phase22: P3.2 款号双轨主键模型 - 补全索引
-- 依赖: phase20_product_traceability.sql 已执行（style_no 列已存在）
-- 执行顺序: 第22步

SET NAMES utf8mb4;

-- ============================================================
-- 1. 确保 style_no 列存在（phase20 已加，此处幂等）
-- ============================================================
CALL sp_erp_add_column('t_erp_sales_order', 'style_no',
  'VARCHAR(32) DEFAULT NULL COMMENT ''工厂内部款号（KN-YY-SS-NNN）''');

CALL sp_erp_add_column('t_erp_produce_plan', 'style_no',
  'VARCHAR(32) DEFAULT NULL COMMENT ''工厂内部款号，继承自关联销售订单''');

-- ============================================================
-- 2. 添加索引（按款号查询/追溯，幂等）
-- ============================================================
DROP PROCEDURE IF EXISTS sp_add_idx_style_no;
DELIMITER $$
CREATE PROCEDURE sp_add_idx_style_no(IN tbl VARCHAR(64))
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = tbl AND INDEX_NAME = 'idx_style_no'
  ) THEN
    SET @sql = CONCAT('ALTER TABLE `', tbl, '` ADD INDEX idx_style_no(style_no)');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END$$
DELIMITER ;

CALL sp_add_idx_style_no('t_erp_sales_order');
CALL sp_add_idx_style_no('t_erp_produce_plan');
DROP PROCEDURE IF EXISTS sp_add_idx_style_no;

-- ============================================================
-- 3. 验证
-- ============================================================
SELECT 'style_no index on sales_order' AS check_item,
  COUNT(*) AS has_index
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sales_order'
  AND INDEX_NAME = 'idx_style_no';

SELECT 'style_no index on produce_plan' AS check_item,
  COUNT(*) AS has_index
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_produce_plan'
  AND INDEX_NAME = 'idx_style_no';
