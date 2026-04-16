-- =============================================
-- 阶段三：外协工序管理 + 三方扫码 + 次品记录
-- 创建表：外协加工单（含调拨支持）、次品记录表
-- =============================================

-- ----------------------------
-- 外协加工单表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_outsource_order`;
CREATE TABLE `t_erp_outsource_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '外协订单ID',
  `outsource_no` varchar(64) DEFAULT NULL COMMENT '外协单号',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_name` varchar(64) DEFAULT NULL COMMENT '工序名称',
  `supplier_id` bigint(20) NOT NULL COMMENT '外协厂商ID',
  `supplier_name` varchar(128) DEFAULT NULL COMMENT '外协厂商名称',
  `is_transfer` int(11) DEFAULT 0 COMMENT '是否调拨 0否 1是',
  `transfer_from` bigint(20) DEFAULT NULL COMMENT '调出外协ID',
  `transfer_to` bigint(20) DEFAULT NULL COMMENT '调入外协ID',
  `job_ids` varchar(1000) DEFAULT NULL COMMENT '工票ID列表，逗号分隔',
  `total_qty` int(11) NOT NULL DEFAULT 0 COMMENT '总件数',
  `theory_weight` decimal(10,2) DEFAULT NULL COMMENT '理论总重量kg',
  `actual_weight` decimal(10,2) DEFAULT NULL COMMENT '实际收回重量kg',
  `confirm_qty` int(11) DEFAULT 0 COMMENT '确认收货数量',
  `defect_qty` int(11) DEFAULT 0 COMMENT '次品数量',
  `status` char(1) DEFAULT '0' COMMENT '状态 0待发出 1已发出 2部分收回 3全部收回',
  `outbound_time` datetime DEFAULT NULL COMMENT '发出时间',
  `receive_time` datetime DEFAULT NULL COMMENT '收回时间',
  `unit_price` decimal(10,2) DEFAULT NULL COMMENT '加工单价',
  `total_price` decimal(12,2) DEFAULT NULL COMMENT '加工总价',
  `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_process_id` (`process_id`),
  KEY `idx_supplier_id` (`supplier_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='外协加工单表';

-- ----------------------------
-- 次品记录表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_produce_defect`;
CREATE TABLE `t_erp_produce_defect` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '次品ID',
  `job_id` bigint(20) NOT NULL COMMENT '工票ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_name` varchar(64) DEFAULT NULL COMMENT '工序名称',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '操作工ID',
  `employee_name` varchar(64) DEFAULT NULL COMMENT '操作工姓名',
  `defect_qty` int(11) NOT NULL DEFAULT 0 COMMENT '次品数量',
  `defect_reason_code` varchar(32) DEFAULT NULL COMMENT '次品原因编码',
  `defect_reason_desc` varchar(200) DEFAULT NULL COMMENT '次品原因描述',
  `is_scrap` char(1) DEFAULT '0' COMMENT '是否报废 0否 1是',
  `is_repair` char(1) DEFAULT '0' COMMENT '是否修复复用 0否 1是',
  `find_time` datetime DEFAULT NULL COMMENT '发现时间',
  `outsource_id` bigint(20) DEFAULT NULL COMMENT '外协ID',
  `is_outsource` char(1) DEFAULT '0' COMMENT '是否外协次品 0否 1是',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_job_id` (`job_id`),
  KEY `idx_process_id` (`process_id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_outsource_id` (`outsource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='次品记录表';

-- ----------------------------
-- 更新ProduceJob增加次品数量字段（如果不存在）
-- ----------------------------
-- ALTER TABLE `t_erp_produce_job` ADD COLUMN `defect_qty` int(11) NOT NULL DEFAULT 0 COMMENT '次品数量' AFTER `actual_qty`;
