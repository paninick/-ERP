-- Wave 3: 统一审批日志表
-- 幂等，可重复执行

CREATE TABLE IF NOT EXISTS t_erp_approval_log (
  id              BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  business_type   VARCHAR(50)  NOT NULL COMMENT '业务类型 SALES_ORDER/BOM/PLAN/PURCHASE/OUTSOURCE/PIECE_WAGE/INVOICE',
  business_id     BIGINT       NOT NULL COMMENT '业务单据ID',
  business_no     VARCHAR(50)  COMMENT '业务单号',
  node_code       VARCHAR(50)  NOT NULL COMMENT '审批节点 SALES_APPROVE/TECH_APPROVE/PLAN_APPROVE/QUALITY_RELEASE/FINANCE_CONFIRM',
  action_type     VARCHAR(20)  NOT NULL COMMENT '操作类型 SUBMIT/APPROVE/REJECT/RECALL/RELEASE/CLOSE',
  from_status     VARCHAR(30)  COMMENT '变更前状态',
  to_status       VARCHAR(30)  COMMENT '变更后状态',
  action_by       VARCHAR(50)  NOT NULL COMMENT '操作人',
  action_time     DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  action_remark   VARCHAR(500) COMMENT '审批意见',
  factory_id      BIGINT       COMMENT '工厂ID',
  create_time     DATETIME     DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  KEY idx_business (business_type, business_id),
  KEY idx_action_by (action_by),
  KEY idx_action_time (action_time),
  KEY idx_business_no (business_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一审批日志';

-- 审批操作类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '审批操作类型', 'erp_approval_action', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_approval_action');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'提交','SUBMIT','erp_approval_action','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_action' AND dict_value='SUBMIT');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'审核通过','APPROVE','erp_approval_action','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_action' AND dict_value='APPROVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'驳回','REJECT','erp_approval_action','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_action' AND dict_value='REJECT');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'撤回','RECALL','erp_approval_action','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_action' AND dict_value='RECALL');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'放行','RELEASE','erp_approval_action','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_action' AND dict_value='RELEASE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'关闭','CLOSE','erp_approval_action','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_approval_action' AND dict_value='CLOSE');

SELECT 'approval_log_create.sql 执行完成' AS result;
