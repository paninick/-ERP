-- Wave 2: QcInspection 分层扩展字段
-- 幂等，可重复执行

-- 质检类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '质检类型', 'erp_qc_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_qc_type');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'来料检验','IQC','erp_qc_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_qc_type' AND dict_value='IQC');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'制程检验','IPQC','erp_qc_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_qc_type' AND dict_value='IPQC');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'终检','FQC','erp_qc_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_qc_type' AND dict_value='FQC');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'出货检验','OQC','erp_qc_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_qc_type' AND dict_value='OQC');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'质量审核','QA','erp_qc_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_qc_type' AND dict_value='QA');

-- 缺陷工艺段字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '缺陷工艺段', 'erp_defect_segment', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_defect_segment');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'横机','KNITTING','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='KNITTING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'套口','LINKING','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='LINKING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'缝纫','SEWING','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='SEWING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'水洗','WASHING','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='WASHING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'整烫','IRONING','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='IRONING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'包装','PACKING','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='PACKING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 7,'检针','NEEDLE_DETECT','erp_defect_segment','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_defect_segment' AND dict_value='NEEDLE_DETECT');

-- 补字段
SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='qc_type');
SET @s1 = IF(@c1=0,'ALTER TABLE t_erp_qc_inspection ADD COLUMN qc_type VARCHAR(20) DEFAULT NULL COMMENT \'质检类型(字典:erp_qc_type)\'','SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='size_measure');
SET @s2 = IF(@c2=0,'ALTER TABLE t_erp_qc_inspection ADD COLUMN size_measure VARCHAR(500) DEFAULT NULL COMMENT \'尺寸实测数据(JSON)\'','SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @c3 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='needle_detect');
SET @s3 = IF(@c3=0,'ALTER TABLE t_erp_qc_inspection ADD COLUMN needle_detect VARCHAR(100) DEFAULT NULL COMMENT \'检针结果\'','SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @c4 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='recheck_count');
SET @s4 = IF(@c4=0,'ALTER TABLE t_erp_qc_inspection ADD COLUMN recheck_count INT DEFAULT 0 COMMENT \'复检次数\'','SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

SET @c5 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection' AND COLUMN_NAME='defect_segment');
SET @s5 = IF(@c5=0,'ALTER TABLE t_erp_qc_inspection ADD COLUMN defect_segment VARCHAR(30) DEFAULT NULL COMMENT \'缺陷工艺段(字典:erp_defect_segment)\'','SELECT 1');
PREPARE p FROM @s5; EXECUTE p; DEALLOCATE PREPARE p;

SELECT COLUMN_NAME, COLUMN_TYPE FROM information_schema.COLUMNS
WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_qc_inspection'
AND COLUMN_NAME IN ('qc_type','size_measure','needle_detect','recheck_count','defect_segment');
