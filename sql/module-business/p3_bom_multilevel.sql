-- P3-1: BOM 多级支持 (纱/坯/成衣三级)
-- 幂等，可重复执行

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom' AND COLUMN_NAME='parent_bom_id');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_bom ADD COLUMN parent_bom_id BIGINT DEFAULT NULL COMMENT ''上级BOM ID(纱→坯→成衣三级)'' AFTER id', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom' AND COLUMN_NAME='bom_level');
SET @s2 = IF(@c2=0, 'ALTER TABLE t_erp_bom ADD COLUMN bom_level TINYINT DEFAULT 1 COMMENT ''BOM层级(1成衣/2坯布/3纱线)'' AFTER parent_bom_id', 'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @c3 = (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom' AND INDEX_NAME='idx_bom_parent');
SET @s3 = IF(@c3=0, 'CREATE INDEX idx_bom_parent ON t_erp_bom (parent_bom_id)', 'SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @c4 = (SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom' AND INDEX_NAME='idx_bom_level');
SET @s4 = IF(@c4=0, 'CREATE INDEX idx_bom_level ON t_erp_bom (bom_level)', 'SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

-- BOM层级字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT 'BOM层级', 'erp_bom_level', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_bom_level');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'成衣BOM','1','erp_bom_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_bom_level' AND dict_value='1');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'坯布BOM','2','erp_bom_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_bom_level' AND dict_value='2');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'纱线BOM','3','erp_bom_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_bom_level' AND dict_value='3');

SELECT 'P3-1: BOM多级字段 (parent_bom_id/bom_level) 完成' AS result;
