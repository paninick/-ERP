-- Wave 2: 客户日单质量画像字段 (P1-A)
-- 幂等，可重复执行

SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer' AND COLUMN_NAME='is_japan_order');
SET @s1 = IF(@c1=0,'ALTER TABLE t_erp_customer ADD COLUMN is_japan_order TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'是否日单客户\'','SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer' AND COLUMN_NAME='require_inspection');
SET @s2 = IF(@c2=0,'ALTER TABLE t_erp_customer ADD COLUMN require_inspection TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'是否必须第三方检品\'','SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @c3 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer' AND COLUMN_NAME='aql_level');
SET @s3 = IF(@c3=0,'ALTER TABLE t_erp_customer ADD COLUMN aql_level VARCHAR(20) DEFAULT NULL COMMENT \'AQL等级(字典:erp_aql_level)\'','SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @c4 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer' AND COLUMN_NAME='needle_detect_req');
SET @s4 = IF(@c4=0,'ALTER TABLE t_erp_customer ADD COLUMN needle_detect_req VARCHAR(50) DEFAULT NULL COMMENT \'检针要求\'','SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

SET @c5 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer' AND COLUMN_NAME='block_shipment_without_release');
SET @s5 = IF(@c5=0,'ALTER TABLE t_erp_customer ADD COLUMN block_shipment_without_release TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'未放行禁止出运\'','SELECT 1');
PREPARE p FROM @s5; EXECUTE p; DEALLOCATE PREPARE p;

SET @c6 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer' AND COLUMN_NAME='inspection_lead_days');
SET @s6 = IF(@c6=0,'ALTER TABLE t_erp_customer ADD COLUMN inspection_lead_days INT DEFAULT 3 COMMENT \'检品提前期(天)\'','SELECT 1');
PREPARE p FROM @s6; EXECUTE p; DEALLOCATE PREPARE p;

-- AQL等级字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT 'AQL等级', 'erp_aql_level', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_aql_level');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'AQL 0.65','AQL_0_65','erp_aql_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_aql_level' AND dict_value='AQL_0_65');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'AQL 1.0','AQL_1_0','erp_aql_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_aql_level' AND dict_value='AQL_1_0');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'AQL 1.5','AQL_1_5','erp_aql_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_aql_level' AND dict_value='AQL_1_5');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'AQL 2.5','AQL_2_5','erp_aql_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_aql_level' AND dict_value='AQL_2_5');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'AQL 4.0','AQL_4_0','erp_aql_level','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_aql_level' AND dict_value='AQL_4_0');

SELECT COLUMN_NAME, COLUMN_TYPE FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_customer'
AND COLUMN_NAME IN ('is_japan_order','require_inspection','aql_level','needle_detect_req','block_shipment_without_release','inspection_lead_days');
