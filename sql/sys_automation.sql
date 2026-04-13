-- 自动化任务表
CREATE TABLE `sys_automation` (
  `task_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `task_name` varchar(100) NOT NULL COMMENT '任务名称',
  `task_type` varchar(50) NOT NULL COMMENT '任务类型',
  `cron_expression` varchar(200) NOT NULL COMMENT '执行周期',
  `description` varchar(500) DEFAULT NULL COMMENT '任务描述',
  `status` varchar(20) NOT NULL COMMENT '任务状态',
  `execute_time` varchar(30) DEFAULT NULL COMMENT '执行时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='自动化任务表';

-- 插入测试数据
INSERT INTO `sys_automation` VALUES (1, '数据库备份任务', 'backup', '0 0 0 * * ?', '每天凌晨自动备份数据库', 'running', '2026-04-02 00:00:00', 'admin', '2026-04-02 12:00:00', 'admin', '2026-04-02 12:00:00');
INSERT INTO `sys_automation` VALUES (2, '系统检查任务', 'check', '0 0 12 * * ?', '每天中午自动检查系统状态', 'running', '2026-04-02 12:00:00', 'admin', '2026-04-02 12:00:00', 'admin', '2026-04-02 12:00:00');
INSERT INTO `sys_automation` VALUES (3, '数据清理任务', 'clean', '0 0 1 * * ?', '每月1号自动清理过期数据', 'paused', NULL, 'admin', '2026-04-02 12:00:00', 'admin', '2026-04-02 12:00:00');
