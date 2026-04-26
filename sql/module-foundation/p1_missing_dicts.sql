-- P1 缺失字典补充
-- 幂等，可重复执行

-- 外协类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '外协类型', 'erp_outsource_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_outsource_type');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'全单外协','FULL_ORDER','erp_outsource_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_outsource_type' AND dict_value='FULL_ORDER');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'单工序外协','SINGLE_PROCESS','erp_outsource_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_outsource_type' AND dict_value='SINGLE_PROCESS');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'水洗外协','WASHING','erp_outsource_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_outsource_type' AND dict_value='WASHING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'缝制外协','SEWING','erp_outsource_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_outsource_type' AND dict_value='SEWING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'后整外协','FINISHING','erp_outsource_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_outsource_type' AND dict_value='FINISHING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'检品外协','INSPECTION','erp_outsource_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_outsource_type' AND dict_value='INSPECTION');

-- 审批节点
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '审批节点', 'erp_approval_node', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_approval_node');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'销售审批','SALES_APPROVE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='SALES_APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'技术审批','TECH_APPROVE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='TECH_APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'BOM审批','BOM_APPROVE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='BOM_APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'计划审批','PLAN_APPROVE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='PLAN_APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'采购审批','PURCHASE_APPROVE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='PURCHASE_APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'外协审批','OUTSOURCE_APPROVE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='OUTSOURCE_APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 7,'质量放行','QUALITY_RELEASE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='QUALITY_RELEASE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 8,'检品放行','INSPECTION_RELEASE','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='INSPECTION_RELEASE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 9,'财务确认','FINANCE_CONFIRM','erp_approval_node','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_node' AND dict_value='FINANCE_CONFIRM');

-- 返工状态
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '返工状态', 'erp_rework_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_rework_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'待返工','PENDING','erp_rework_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_rework_status' AND dict_value='PENDING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'返工中','RUNNING','erp_rework_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_rework_status' AND dict_value='RUNNING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'待检验','WAIT_CHECK','erp_rework_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_rework_status' AND dict_value='WAIT_CHECK');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'已通过','PASSED','erp_rework_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_rework_status' AND dict_value='PASSED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'已关闭','CLOSED','erp_rework_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_rework_status' AND dict_value='CLOSED');

-- 变更原因
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '变更原因', 'erp_change_reason', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_change_reason');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'交期变更','DELIVERY_CHANGE','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='DELIVERY_CHANGE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'数量变更','QTY_CHANGE','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='QTY_CHANGE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'款号变更','STYLE_CHANGE','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='STYLE_CHANGE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'工艺变更','ROUTE_CHANGE','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='ROUTE_CHANGE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'缺料','MATERIAL_SHORTAGE','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='MATERIAL_SHORTAGE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'质量问题','QUALITY_ISSUE','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='QUALITY_ISSUE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 7,'客户要求','CUSTOMER_REQUEST','erp_change_reason','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_change_reason' AND dict_value='CUSTOMER_REQUEST');

SELECT 'p1_missing_dicts.sql 执行完成' AS result;
