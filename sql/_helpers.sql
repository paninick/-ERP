-- _helpers.sql
-- MySQL 8.4 兼容的幂等 DDL 存储过程
-- 解决 MariaDB 专属 ADD COLUMN IF NOT EXISTS 语法在 MySQL 上 ERROR 1064 的问题
-- 用法（在其它脚本开头 SOURCE 本文件，或在流水线最前面执行）：
--   CALL sp_erp_add_column('t_erp_xxx', 'col_name', 'VARCHAR(20) NULL COMMENT ''...'' AFTER other_col');
--   CALL sp_erp_add_index('t_erp_xxx', 'idx_name', '(col1, col2)');
--   CALL sp_erp_add_unique_key('t_erp_xxx', 'uk_name', '(col1)');

SET NAMES utf8mb4;

DROP PROCEDURE IF EXISTS sp_erp_add_column;
DROP PROCEDURE IF EXISTS sp_erp_add_index;
DROP PROCEDURE IF EXISTS sp_erp_add_unique_key;

DELIMITER $$

CREATE PROCEDURE sp_erp_add_column(
  IN p_tbl VARCHAR(64),
  IN p_col VARCHAR(64),
  IN p_def TEXT
)
BEGIN
  IF (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
      WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME   = p_tbl
        AND COLUMN_NAME  = p_col) = 0 THEN
    SET @s = CONCAT('ALTER TABLE `', p_tbl, '` ADD COLUMN `', p_col, '` ', p_def);
    PREPARE stmt FROM @s;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END$$

CREATE PROCEDURE sp_erp_add_index(
  IN p_tbl  VARCHAR(64),
  IN p_idx  VARCHAR(64),
  IN p_cols TEXT
)
BEGIN
  IF (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
      WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME   = p_tbl
        AND INDEX_NAME   = p_idx) = 0 THEN
    SET @s = CONCAT('CREATE INDEX `', p_idx, '` ON `', p_tbl, '` ', p_cols);
    PREPARE stmt FROM @s;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END$$

CREATE PROCEDURE sp_erp_add_unique_key(
  IN p_tbl  VARCHAR(64),
  IN p_idx  VARCHAR(64),
  IN p_cols TEXT
)
BEGIN
  IF (SELECT COUNT(*) FROM INFORMATION_SCHEMA.STATISTICS
      WHERE TABLE_SCHEMA = DATABASE()
        AND TABLE_NAME   = p_tbl
        AND INDEX_NAME   = p_idx) = 0 THEN
    SET @s = CONCAT('ALTER TABLE `', p_tbl, '` ADD UNIQUE KEY `', p_idx, '` ', p_cols);
    PREPARE stmt FROM @s;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END$$

DELIMITER ;
