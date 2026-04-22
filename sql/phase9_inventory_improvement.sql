SET NAMES utf8mb4;
-- =============================================
-- 阶段九：库存模块完善 - 补全设计坑
-- 基于穷尽爆破后补充：补充缺失表和索引
-- 1. 物料SKU表（颜色 × 尺码 多维）
-- 2. 库存总表（支持锁定库存 + 乐观锁
-- 3. 库存调整单（盘点调账留痕）
-- 4. 单据号sequence表（Redis兜底）
-- =============================================

-- ----------------------------
-- 物料SKU表
-- 一个物料（款式）下，按颜色 × 尺码生成多个SKU，支持三维（aux_id1=颜色, aux_id2=尺码, aux_id3=批次
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_material_sku`;
CREATE TABLE `t_erp_material_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `sku_code` varchar(128) NOT NULL COMMENT 'SKU编码（全局唯一）',
  `aux_id1` bigint(20) NOT NULL DEFAULT 1 COMMENT '辅助属性1 - 颜色（固定顺序，不允许空，默认1=无颜色）',
  `aux_id2` bigint(20) NOT NULL DEFAULT 1 COMMENT '辅助属性2 - 尺码（固定顺序，不允许空，默认1=均码',
  `aux_id3` bigint(20) DEFAULT NULL COMMENT '辅助属性3 - 批次',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'SKU名称（物料名+颜色+尺码）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `is_deleted` bigint(1) NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sku_code` (`sku_code`),
  KEY `idx_material_id` (`material_id`),
  KEY `idx_aux_id1` (`aux_id1`),
  KEY `idx_aux_id2` (`aux_id2`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料SKU表（颜色×尺码多维）';

-- ----------------------------
-- 辅助属性值表（颜色/尺码等
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_aux_property_value`;
CREATE TABLE `t_erp_aux_property_value` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `property_type` varchar(32) NOT NULL COMMENT '属性类型（color/size）',
  `value_code` varchar(64) NOT NULL COMMENT '属性值编码',
  `value_name` varchar(64) NOT NULL COMMENT '属性值名称',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序（用于颜色尺码自定义排序，不按拼音）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_type_code` (`property_type`, `value_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='辅助属性值表（颜色/尺码）';

-- ----------------------------
-- 库存总表（按SKU + 仓库汇总
-- 支持可用库存 + 锁定库存分离，乐观锁version防超卖
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_inv_stock`;
CREATE TABLE `t_erp_inv_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库存ID',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库ID',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `inv_qty` decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT '可用库存数量',
  `lock_qty` decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT '锁定库存数量（订单锁定，不能卖）',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouse_sku` (`warehouse_id`, `sku_id`),
  KEY `idx_material_id` (`material_id`),
  KEY `idx_sku_id` (`sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存总表';

-- ----------------------------
-- 库存调整单（盘点调账，必须留痕
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_inv_adjust_bill`;
CREATE TABLE `t_erp_inv_adjust_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调整单ID',
  `bill_no` varchar(64) NOT NULL COMMENT '调整单号',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库ID',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
  `before_qty` decimal(18,6) NOT NULL COMMENT '调整前数量',
  `adjust_qty` decimal(18,6) NOT NULL COMMENT '调整数量（正增负减',
  `after_qty` decimal(18,6) NOT NULL COMMENT '调整后数量',
  `adjust_reason` varchar(500) NOT NULL COMMENT '调整原因',
  `status` char(1) DEFAULT '0' COMMENT '0待审核 1已审核',
  `audit_by_id` bigint(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_by_name` varchar(64) DEFAULT NULL COMMENT '审核人姓名',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `is_deleted` bigint(1) NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_bill_no` (`bill_no`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_sku_id` (`sku_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存调整单';

-- ----------------------------
-- 单据号sequence表（Redis持久化兜底）
-- Redis宕机重启后，从这里 reload，避免流水号重复
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_bill_sequence`;
CREATE TABLE `t_erp_bill_sequence` (
  `seq_date` date NOT NULL COMMENT '日期',
  `bill_type` varchar(32) NOT NULL COMMENT '单据类型（PO=采购单,SO=销售订单,ST=入库单...）',
  `current_val` int(11) NOT NULL DEFAULT 0 COMMENT '当前值',
  PRIMARY KEY (`seq_date`, `bill_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单据号sequence表（Redis兜底持久化）';

-- ----------------------------
-- 插入默认数据：无颜色/均码（避免NULL坑
-- ----------------------------
INSERT INTO `t_erp_aux_property_value` (`id`, `property_type`, `value_code`, `value_name`, `sort_order`) VALUES
(1, 'color', 'default', '默认', 1),
(2, 'size', 'free', '均码', 1);

-- ----------------------------
-- 给现有表补充缺失的唯一索引
-- ----------------------------
-- 物料编码唯一
ALTER TABLE `t_erp_main_material ADD UNIQUE KEY uk_main_material_no(main_material_no);

-- 工序编码唯一
ALTER TABLE `t_erp_process_def` ADD UNIQUE KEY uk_process_code(process_code);

-- 员工编码唯一
ALTER TABLE `t_erp_employee` ADD UNIQUE KEY uk_employee_code(employee_code);

-- 色卡编码唯一
ALTER TABLE `t_erp_standard_color` ADD UNIQUE KEY uk_color_code(color_code);
