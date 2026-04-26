-- P3-5: 异常池责任链字段扩展
-- 幂等，可重复执行

SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_biz_abnormal_pool' AND COLUMN_NAME='dept_responsible');
SET @s1 = IF(@c1=0, 'ALTER TABLE t_erp_biz_abnormal_pool ADD COLUMN dept_responsible VARCHAR(50) DEFAULT NULL COMMENT ''责任部门'' AFTER handle_result', 'SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_biz_abnormal_pool' AND COLUMN_NAME='person_responsible');
SET @s2 = IF(@c2=0, 'ALTER TABLE t_erp_biz_abnormal_pool ADD COLUMN person_responsible VARCHAR(50) DEFAULT NULL COMMENT ''责任人'' AFTER dept_responsible', 'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @c3 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_biz_abnormal_pool' AND COLUMN_NAME='affect_delivery');
SET @s3 = IF(@c3=0, 'ALTER TABLE t_erp_biz_abnormal_pool ADD COLUMN affect_delivery CHAR(1) DEFAULT ''N'' COMMENT ''是否影响交期(Y/N)'' AFTER person_responsible', 'SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @c4 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_biz_abnormal_pool' AND COLUMN_NAME='affect_cost');
SET @s4 = IF(@c4=0, 'ALTER TABLE t_erp_biz_abnormal_pool ADD COLUMN affect_cost CHAR(1) DEFAULT ''N'' COMMENT ''是否影响成本(Y/N)'' AFTER affect_delivery', 'SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

SET @c5 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_biz_abnormal_pool' AND COLUMN_NAME='close_by');
SET @s5 = IF(@c5=0, 'ALTER TABLE t_erp_biz_abnormal_pool ADD COLUMN close_by VARCHAR(50) DEFAULT NULL COMMENT ''关闭人'' AFTER handle_time', 'SELECT 1');
PREPARE p FROM @s5; EXECUTE p; DEALLOCATE PREPARE p;

SET @c6 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_biz_abnormal_pool' AND COLUMN_NAME='close_time');
SET @s6 = IF(@c6=0, 'ALTER TABLE t_erp_biz_abnormal_pool ADD COLUMN close_time DATETIME DEFAULT NULL COMMENT ''关闭时间'' AFTER close_by', 'SELECT 1');
PREPARE p FROM @s6; EXECUTE p; DEALLOCATE PREPARE p;

-- 异常来源字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '异常来源', 'erp_abnormal_source', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_abnormal_source');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'计划异常','PLAN','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='PLAN');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'物料异常','MATERIAL','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='MATERIAL');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'设备异常','EQUIPMENT','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='EQUIPMENT');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'工艺异常','PROCESS','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='PROCESS');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'品质异常','QUALITY','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='QUALITY');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'外协异常','OUTSOURCE','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='OUTSOURCE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 7,'交期异常','DELIVERY','erp_abnormal_source','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_abnormal_source' AND dict_value='DELIVERY');

SELECT 'P3-5: 异常池责任链字段 + 异常来源字典 完成' AS result;
