-- Wave 3: BOM + 外协 审批状态字段
-- 幂等，可重复执行

-- BOM 审批状态
SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom' AND COLUMN_NAME='audit_status');
SET @s1 = IF(@c1=0,'ALTER TABLE t_erp_bom ADD COLUMN audit_status VARCHAR(20) NOT NULL DEFAULT \'DRAFT\' COMMENT \'审批状态 DRAFT/SUBMITTED/APPROVED/REJECTED\'','SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom' AND COLUMN_NAME='is_frozen');
SET @s2 = IF(@c2=0,'ALTER TABLE t_erp_bom ADD COLUMN is_frozen TINYINT(1) NOT NULL DEFAULT 0 COMMENT \'是否冻结(0否1是)\'','SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

-- 外协 审批状态
SET @c3 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='audit_status');
SET @s3 = IF(@c3=0,'ALTER TABLE t_erp_outsource_order ADD COLUMN audit_status VARCHAR(20) NOT NULL DEFAULT \'DRAFT\' COMMENT \'审批状态 DRAFT/SUBMITTED/APPROVED/REJECTED\'','SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

-- 采购 audit_status（confirm_status 已有，补标准 audit_status）
SET @c4 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_purchase' AND COLUMN_NAME='audit_status');
SET @s4 = IF(@c4=0,'ALTER TABLE t_erp_purchase ADD COLUMN audit_status VARCHAR(20) NOT NULL DEFAULT \'DRAFT\' COMMENT \'审批状态 DRAFT/SUBMITTED/APPROVED/REJECTED\'','SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

-- 审批状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '审批状态', 'erp_audit_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_audit_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'草稿','DRAFT','erp_audit_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_audit_status' AND dict_value='DRAFT');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'待审批','SUBMITTED','erp_audit_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_audit_status' AND dict_value='SUBMITTED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'审批通过','APPROVED','erp_audit_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_audit_status' AND dict_value='APPROVED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'已驳回','REJECTED','erp_audit_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_audit_status' AND dict_value='REJECTED');

SELECT 'approval_status_extend.sql 执行完成' AS result;
