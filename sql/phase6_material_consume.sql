-- =============================================
-- 阶段六：物料损耗控制 + 超领熔断
-- 创建表：生产物料消耗记录表
-- =============================================

-- ----------------------------
-- 生产物料消耗记录表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_produce_material_consume`;
CREATE TABLE `t_erp_produce_material_consume` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '消耗记录ID',
  `produce_plan_id` bigint(20) NOT NULL COMMENT '生产计划ID',
  `order_id` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_name` varchar(64) DEFAULT NULL COMMENT '工序名称',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `material_code` varchar(64) DEFAULT NULL COMMENT '物料编码',
  `material_name` varchar(128) DEFAULT NULL COMMENT '物料名称',
  `bom_qty` decimal(18,3) NOT NULL COMMENT 'BOM理论用量',
  `actual_qty` decimal(18,3) NOT NULL COMMENT '实际领用数量',
  `standard_loss_rate` decimal(6,2) DEFAULT NULL COMMENT '标准损耗率%',
  `limit_loss_qty` decimal(18,3) DEFAULT NULL COMMENT '理论损耗限额',
  `actual_loss_qty` decimal(18,3) DEFAULT NULL COMMENT '实际损耗数量',
  `is_over_limit` char(1) DEFAULT '0' COMMENT '是否超限额 0否 1是',
  `over_limit_reason` varchar(500) DEFAULT NULL COMMENT '超限额原因',
  `approval_status` char(1) DEFAULT '0' COMMENT '审批状态 0无需审批 1待审批 2已批准 3已拒绝',
  `approval_by_id` bigint(20) DEFAULT NULL COMMENT '审批人ID',
  `approval_by_name` varchar(64) DEFAULT NULL COMMENT '审批人姓名',
  `approval_time` datetime DEFAULT NULL COMMENT '审批时间',
  `approval_remark` varchar(500) DEFAULT NULL COMMENT '审批备注',
  `unit` varchar(20) DEFAULT NULL COMMENT '物料单位',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_produce_plan_id` (`produce_plan_id`),
  KEY `idx_process_id` (`process_id`),
  KEY `idx_material_id` (`material_id`),
  KEY `idx_is_over_limit` (`is_over_limit`),
  KEY `idx_approval_status` (`approval_status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产物料消耗记录表';
