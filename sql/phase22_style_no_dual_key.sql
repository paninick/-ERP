-- phase22: add style_code indexes for traceability queries
-- Dependency: phase20_product_traceability.sql

SET NAMES utf8mb4;

CALL sp_erp_add_column(
  't_erp_sales_order',
  'style_code',
  'VARCHAR(32) DEFAULT NULL COMMENT ''工厂内部款号（KN-YY-SS-NNN）'''
);

CALL sp_erp_add_column(
  't_erp_produce_plan',
  'style_code',
  'VARCHAR(32) DEFAULT NULL COMMENT ''继承自销售单的生产款号'''
);

DROP PROCEDURE IF EXISTS sp_add_idx_style_code;
DELIMITER $$
CREATE PROCEDURE sp_add_idx_style_code(IN tbl VARCHAR(64))
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.STATISTICS
    WHERE TABLE_SCHEMA = DATABASE()
      AND TABLE_NAME = tbl
      AND INDEX_NAME = 'idx_style_code'
  ) THEN
    SET @sql = CONCAT('ALTER TABLE `', tbl, '` ADD INDEX idx_style_code(style_code)');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END$$
DELIMITER ;

CALL sp_add_idx_style_code('t_erp_sales_order');
CALL sp_add_idx_style_code('t_erp_produce_plan');
DROP PROCEDURE IF EXISTS sp_add_idx_style_code;

SELECT 'style_code index on sales_order' AS check_item,
       COUNT(*) AS has_index
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_sales_order'
  AND INDEX_NAME = 'idx_style_code';

SELECT 'style_code index on produce_plan' AS check_item,
       COUNT(*) AS has_index
FROM information_schema.STATISTICS
WHERE TABLE_SCHEMA = DATABASE()
  AND TABLE_NAME = 't_erp_produce_plan'
  AND INDEX_NAME = 'idx_style_code';
