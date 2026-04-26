-- ProcessDef 补字段：product_family / workshop_type / qc_required / loss_tracked / piece_wage_applicable
-- 幂等，可重复执行

SET @col1 = (SELECT COUNT(*) FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_erp_process_def' AND COLUMN_NAME = 'product_family');
SET @sql1 = IF(@col1 = 0,
  'ALTER TABLE t_erp_process_def ADD COLUMN product_family VARCHAR(30) DEFAULT NULL COMMENT \'产品族(字典:erp_product_family)\'',
  'SELECT 1');
PREPARE stmt1 FROM @sql1; EXECUTE stmt1; DEALLOCATE PREPARE stmt1;

SET @col2 = (SELECT COUNT(*) FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_erp_process_def' AND COLUMN_NAME = 'workshop_type');
SET @sql2 = IF(@col2 = 0,
  'ALTER TABLE t_erp_process_def ADD COLUMN workshop_type VARCHAR(30) DEFAULT NULL COMMENT \'适用车间类型(字典:erp_work_center_type)\'',
  'SELECT 1');
PREPARE stmt2 FROM @sql2; EXECUTE stmt2; DEALLOCATE PREPARE stmt2;

SET @col3 = (SELECT COUNT(*) FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_erp_process_def' AND COLUMN_NAME = 'qc_required');
SET @sql3 = IF(@col3 = 0,
  'ALTER TABLE t_erp_process_def ADD COLUMN qc_required TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'是否需要质检(0否1是)\'',
  'SELECT 1');
PREPARE stmt3 FROM @sql3; EXECUTE stmt3; DEALLOCATE PREPARE stmt3;

SET @col4 = (SELECT COUNT(*) FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_erp_process_def' AND COLUMN_NAME = 'loss_tracked');
SET @sql4 = IF(@col4 = 0,
  'ALTER TABLE t_erp_process_def ADD COLUMN loss_tracked TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'是否追踪损耗(0否1是)\'',
  'SELECT 1');
PREPARE stmt4 FROM @sql4; EXECUTE stmt4; DEALLOCATE PREPARE stmt4;

SET @col5 = (SELECT COUNT(*) FROM information_schema.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_erp_process_def' AND COLUMN_NAME = 'piece_wage_applicable');
SET @sql5 = IF(@col5 = 0,
  'ALTER TABLE t_erp_process_def ADD COLUMN piece_wage_applicable TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'是否计件(0否1是)\'',
  'SELECT 1');
PREPARE stmt5 FROM @sql5; EXECUTE stmt5; DEALLOCATE PREPARE stmt5;

-- 验证
SELECT COLUMN_NAME, COLUMN_TYPE, COLUMN_COMMENT
FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 't_erp_process_def'
  AND COLUMN_NAME IN ('product_family','workshop_type','qc_required','loss_tracked','piece_wage_applicable');
