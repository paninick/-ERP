-- =============================================
-- 阶段七：规则校验 + 异常池
-- 创建表：业务异常池
-- =============================================

-- ----------------------------
-- 业务异常池表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_biz_abnormal_pool`;
CREATE TABLE `t_erp_biz_abnormal_pool` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '异常ID',
  `biz_type` varchar(64) NOT NULL COMMENT '关联业务类型',
  `biz_id` bigint(20) NOT NULL COMMENT '关联业务ID',
  `abnormal_code` varchar(64) DEFAULT NULL COMMENT '异常编码',
  `abnormal_title` varchar(128) NOT NULL COMMENT '异常标题',
  `abnormal_desc` varchar(1000) DEFAULT NULL COMMENT '异常描述',
  `abnormal_level` int(11) NOT NULL DEFAULT '1' COMMENT '异常等级 1低 2中 3高',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态 0待处理 1处理中 2已处理 3已关闭',
  `handle_by_id` bigint(20) DEFAULT NULL COMMENT '处理人ID',
  `handle_by_name` varchar(64) DEFAULT NULL COMMENT '处理人姓名',
  `handle_time` datetime DEFAULT NULL COMMENT '处理时间',
  `handle_result` varchar(500) DEFAULT NULL COMMENT '处理结果描述',
  `lock_biz` char(1) NOT NULL DEFAULT '0' COMMENT '是否锁定业务 0不锁定 1锁定',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_biz_type` (`biz_type`),
  KEY `idx_biz_id` (`biz_id`),
  KEY `idx_status` (`status`),
  KEY `idx_abnormal_level` (`abnormal_level`),
  KEY `idx_lock_biz` (`lock_biz`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务异常池';
