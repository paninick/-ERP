-- phase15: 柔性排单模型升级
-- 依赖: phase14_rbac_role_permission.sql 已执行
-- 执行顺序: 第16步
--
-- 升级内容：
--   demo_schedule 新增 priority/start_date/due_date/schedule_status/conflict_flag
--   新增排单优先级字典、排单状态字典

SET NAMES utf8mb4;

-- ============================================================
-- 0. 建表：demo_schedule（如不存在则创建）
-- ============================================================
CREATE TABLE IF NOT EXISTS `demo_schedule` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `plan_id` bigint DEFAULT NULL COMMENT '生产计划ID',
  `plan_no` varchar(30) DEFAULT NULL COMMENT '计划编号',
  `style_no` varchar(50) DEFAULT NULL COMMENT '款号',
  `quantity` int DEFAULT NULL COMMENT '数量',
  `status` char(1) DEFAULT '0' COMMENT '状态',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='排产计划';

-- ============================================================
-- 1. demo_schedule 新增柔性排单字段
-- ============================================================
CALL sp_erp_add_column('demo_schedule', 'priority', 'tinyint(1)   NOT NULL DEFAULT 5  COMMENT ''优先级 1最高-10最低''');
CALL sp_erp_add_column('demo_schedule', 'start_date', 'date         DEFAULT NULL         COMMENT ''计划开始日期''');
CALL sp_erp_add_column('demo_schedule', 'due_date', 'date         DEFAULT NULL         COMMENT ''计划完成日期''');
CALL sp_erp_add_column('demo_schedule', 'schedule_status', 'char(1)      NOT NULL DEFAULT ''0'' COMMENT ''排单状态 0待排 1已排 2进行中 3已完成''');
CALL sp_erp_add_column('demo_schedule', 'conflict_flag', 'char(1)      NOT NULL DEFAULT ''0'' COMMENT ''冲突标志 0无冲突 1产能冲突 2日期冲突''');

-- ============================================================
-- 2. 字典：排单优先级
-- ============================================================
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('排单优先级', 'erp_schedule_priority', '0', 'admin', NOW(), 'ERP排单优先级');

INSERT IGNORE INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES
(1,  '最高', '1', 'erp_schedule_priority', '', 'danger',  'N', '0', 'admin', NOW(), ''),
(2,  '高',   '2', 'erp_schedule_priority', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(3,  '中',   '5', 'erp_schedule_priority', '', 'primary', 'Y', '0', 'admin', NOW(), ''),
(4,  '低',   '8', 'erp_schedule_priority', '', 'info',    'N', '0', 'admin', NOW(), ''),
(5,  '最低', '10','erp_schedule_priority', '', '',        'N', '0', 'admin', NOW(), '');

-- ============================================================
-- 3. 字典：排单状态
-- ============================================================
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('排单状态', 'erp_schedule_status', '0', 'admin', NOW(), 'ERP排单状态');

INSERT IGNORE INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES
(1, '待排',   '0', 'erp_schedule_status', '', 'info',    'N', '0', 'admin', NOW(), ''),
(2, '已排',   '1', 'erp_schedule_status', '', 'primary', 'Y', '0', 'admin', NOW(), ''),
(3, '进行中', '2', 'erp_schedule_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(4, '已完成', '3', 'erp_schedule_status', '', 'success', 'N', '0', 'admin', NOW(), '');
