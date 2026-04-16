-- =============================================
-- 阶段二：工票生产流转核心 + 颜色确认
-- 创建表：生产工票/扎单、工序流转记录、单件流水号
-- 修复：列名统一为 snake_case，createBy/updateBy 统一为 varchar(64)
-- =============================================

-- ----------------------------
-- 生产工票/扎单表（核心流转表）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_produce_job`;
CREATE TABLE `t_erp_produce_job` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工票ID',
  `job_no` varchar(64) DEFAULT NULL COMMENT '工票编号',
  `produce_plan_id` bigint(20) NOT NULL COMMENT '生产计划ID',
  `order_id` bigint(20) NOT NULL COMMENT '销售订单ID',
  `color_code` varchar(32) DEFAULT NULL COMMENT '颜色编码',
  `size_code` varchar(32) DEFAULT NULL COMMENT '尺码编码',
  `plan_qty` int(11) NOT NULL DEFAULT 0 COMMENT '计划数量',
  `actual_qty` int(11) NOT NULL DEFAULT 0 COMMENT '实际完成数量',
  `defect_qty` int(11) NOT NULL DEFAULT 0 COMMENT '次品数量',
  `current_process_id` bigint(20) DEFAULT NULL COMMENT '当前工序ID',
  `current_process_status` char(1) DEFAULT '0' COMMENT '当前工序状态 0待开工 1进行中 2已完成',
  `start_time` datetime DEFAULT NULL COMMENT '开工时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `outsource_batch_barcode` varchar(64) DEFAULT NULL COMMENT '外协批次条码',
  `outbound_time` datetime DEFAULT NULL COMMENT '外协发出时间',
  `receive_time` datetime DEFAULT NULL COMMENT '外协收回时间',
  `confirm_qty` int(11) DEFAULT 0 COMMENT '外协确认收货数量',
  `outsource_id` bigint(20) DEFAULT NULL COMMENT '当前外协ID',
  `status` char(1) DEFAULT '0' COMMENT '工票状态 0待生产 1生产中 2已完成',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_produce_plan_id` (`produce_plan_id`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_current_process_id` (`current_process_id`),
  KEY `idx_outsource_id` (`outsource_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产工票/扎单表';

-- ----------------------------
-- 工序流转记录表（每次交接记录）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_produce_job_process`;
CREATE TABLE `t_erp_produce_job_process` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '记录ID',
  `job_id` bigint(20) NOT NULL COMMENT '工票ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `process_seq` int(11) NOT NULL COMMENT '工序顺序',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '操作工ID',
  `employee_name` varchar(64) DEFAULT NULL COMMENT '操作工姓名',
  `in_qty` int(11) NOT NULL DEFAULT 0 COMMENT '接收数量',
  `out_qty` int(11) NOT NULL DEFAULT 0 COMMENT '转出数量',
  `defect_qty` int(11) NOT NULL DEFAULT 0 COMMENT '本工序次品数量',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完成时间',
  `is_outsource` char(1) DEFAULT '0' COMMENT '是否外协 0自产 1外协',
  `outsource_id` bigint(20) DEFAULT NULL COMMENT '外协ID',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_job_id` (`job_id`),
  KEY `idx_process_id` (`process_id`),
  KEY `idx_employee_id` (`employee_id`),
  KEY `idx_outsource_id` (`outsource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序流转记录表';

-- ----------------------------
-- 单件流水号表（每件衣服唯一二维码）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_product_serial`;
CREATE TABLE `t_erp_product_serial` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `serial_no` varchar(64) NOT NULL COMMENT '流水号（唯一）',
  `order_id` bigint(20) NOT NULL COMMENT '销售订单ID',
  `job_id` bigint(20) NOT NULL COMMENT '工票ID',
  `produce_plan_id` bigint(20) NOT NULL COMMENT '生产计划ID',
  `color_code` varchar(32) DEFAULT NULL COMMENT '颜色编码',
  `size_code` varchar(32) DEFAULT NULL COMMENT '尺码编码',
  `current_process_id` bigint(20) DEFAULT NULL COMMENT '当前工序ID',
  `current_process_name` varchar(64) DEFAULT NULL COMMENT '当前工序名称',
  `status` char(1) DEFAULT '0' COMMENT '状态 0在制 1已完工 2已入库 3已出货',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `finish_time` datetime DEFAULT NULL COMMENT '完工时间',
  `warehouse_time` datetime DEFAULT NULL COMMENT '入库时间',
  `ship_time` datetime DEFAULT NULL COMMENT '出货时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_serial_no` (`serial_no`),
  KEY `idx_order_id` (`order_id`),
  KEY `idx_job_id` (`job_id`),
  KEY `idx_produce_plan_id` (`produce_plan_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单件流水号表';

-- ----------------------------
-- 已在 phase1 中执行，此处重复说明
-- t_erp_sample_notice 颜色确认字段增强
-- ----------------------------
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `color_confirm_status` char(2) DEFAULT '01' COMMENT '颜色确认状态 01待提交 02客户签样 03车间比对 04允许生产' AFTER `notice_status`;
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `confirm_by_id` bigint(20) DEFAULT NULL COMMENT '确认人ID' AFTER `color_confirm_status`;
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `confirm_time` datetime DEFAULT NULL COMMENT '确认时间' AFTER `confirm_by_id`;
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `color_confirm_images` varchar(2000) DEFAULT NULL COMMENT '颜色对比照片（多个逗号分隔）' AFTER `confirm_time`;
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `digital_signature` varchar(500) DEFAULT NULL COMMENT '确认人数字签名图片' AFTER `color_confirm_images`;
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `customer_accept_delta_e` decimal(5,2) DEFAULT NULL COMMENT '客户允差ΔE' AFTER `digital_signature`;
-- ALTER TABLE `t_erp_sample_notice` ADD COLUMN `light_source_type` varchar(32) DEFAULT NULL COMMENT '拍摄光源类型' AFTER `customer_accept_delta_e`;
