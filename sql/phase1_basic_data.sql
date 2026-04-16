-- ============================================
-- 阶段一：基础数据建模 - 数据库表创建脚本
-- 服装ERP+MES系统 - 针织拼接毛衫专用
-- ============================================

-- ----------------------------
-- 工序定义表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_process_def`;
CREATE TABLE `t_erp_process_def` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工序ID',
  `process_code` varchar(50) NOT NULL COMMENT '工序编码',
  `process_name` varchar(100) NOT NULL COMMENT '工序名称',
  `process_type` varchar(20) DEFAULT NULL COMMENT '工序类型（自产/外协）',
  `department` varchar(50) DEFAULT NULL COMMENT '所属部门',
  `default_price` decimal(10,2) DEFAULT NULL COMMENT '默认工价',
  `enable_outsource` tinyint(1) DEFAULT 0 COMMENT '是否支持外协（0否 1是）',
  `need_quality_check` tinyint(1) DEFAULT 0 COMMENT '需要质检（0否 1是）',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_process_code` (`process_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序定义表';

-- ----------------------------
-- 工艺路线表（产品级）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_process_route`;
CREATE TABLE `t_erp_process_route` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工艺路线ID',
  `route_name` varchar(100) NOT NULL COMMENT '工艺路线名称',
  `product_type` varchar(50) DEFAULT NULL COMMENT '产品类型',
  `product_code` varchar(50) DEFAULT NULL COMMENT '产品编码（可为空，通用路由为空）',
  `is_default` tinyint(1) DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺路线表';

-- ----------------------------
-- 工艺路线明细表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_process_route_item`;
CREATE TABLE `t_erp_process_route_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `route_id` bigint(20) NOT NULL COMMENT '工艺路线ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `sort_order` int(11) NOT NULL COMMENT '排序顺序',
  `is_control_point` tinyint(1) DEFAULT 0 COMMENT '是否齐套控制点（0否 1是）',
  `require_complete_ratio` decimal(5,2) DEFAULT 100.00 COMMENT '齐套要求比率（%）',
  `allow_force_start` tinyint(1) DEFAULT 0 COMMENT '允许强制开工（0否 1是）',
  `is_outsource` tinyint(1) DEFAULT 0 COMMENT '是否外协（0否 1是）',
  `standard_cycle_hours` decimal(10,2) DEFAULT NULL COMMENT '标准工时（小时）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  KEY `idx_route_id` (`route_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工艺路线明细表';

-- ----------------------------
-- 员工/操作工档案
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_employee`;
CREATE TABLE `t_erp_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `employee_code` varchar(50) NOT NULL COMMENT '员工编号',
  `employee_name` varchar(50) NOT NULL COMMENT '员工姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `department` varchar(50) DEFAULT NULL COMMENT '所属车间/部门',
  `station` varchar(50) DEFAULT NULL COMMENT '工位',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `leave_date` date DEFAULT NULL COMMENT '离职日期',
  `status` char(1) DEFAULT '0' COMMENT '状态（0在职 1离职）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_employee_code` (`employee_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工/操作工档案';

-- ----------------------------
-- 工序工价表（支持员工差异化工价）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_process_price`;
CREATE TABLE `t_erp_process_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工价ID',
  `process_id` bigint(20) NOT NULL COMMENT '工序ID',
  `employee_id` bigint(20) DEFAULT NULL COMMENT '员工ID（为空表示通用价）',
  `price` decimal(10,4) NOT NULL COMMENT '工价（每件/每公斤）',
  `effective_date` date NOT NULL COMMENT '生效日期',
  `expire_date` date DEFAULT NULL COMMENT '失效日期',
  `status` char(1) DEFAULT '0' COMMENT '状态（0有效 1失效）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_process_id` (`process_id`),
  KEY `idx_employee_id` (`employee_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序工价表';

-- ----------------------------
-- 标准色卡表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_standard_color`;
CREATE TABLE `t_erp_standard_color` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '色卡ID',
  `color_code` varchar(50) NOT NULL COMMENT '颜色编码',
  `color_name` varchar(100) NOT NULL COMMENT '颜色名称',
  `color_lab` varchar(100) DEFAULT NULL COMMENT 'LAB值（逗号分隔 L,a,b）',
  `default_delta_e` decimal(5,2) DEFAULT 3.00 COMMENT '默认色差允差值ΔE',
  `color_image` varchar(500) DEFAULT NULL COMMENT '色卡图片路径',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_color_code` (`color_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标准色卡表';

-- ----------------------------
-- 二维损耗矩阵表（主料类型+辅料类型+工艺 → 标准损耗率）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_process_loss_matrix`;
CREATE TABLE `t_erp_process_loss_matrix` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '矩阵ID',
  `material_a_type` varchar(50) NOT NULL COMMENT '主料类型',
  `material_b_type` varchar(50) DEFAULT NULL COMMENT '辅料类型（可为空）',
  `process_code` varchar(50) NOT NULL COMMENT '工艺代码',
  `standard_loss_rate` decimal(6,4) NOT NULL COMMENT '标准损耗率（%）',
  `actual_avg_loss` decimal(6,4) DEFAULT NULL COMMENT '历史实际平均损耗',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_loss_matrix` (`material_a_type`, `material_b_type`, `process_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='二维损耗矩阵表';

-- ----------------------------
-- 单位换算表（采购/库存/生产三单位换算）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_unit_conversion`;
CREATE TABLE `t_erp_unit_conversion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '换算ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `purchase_unit` varchar(20) NOT NULL COMMENT '采购单位',
  `stock_unit` varchar(20) NOT NULL COMMENT '库存单位',
  `produce_unit` varchar(20) NOT NULL COMMENT '生产单位',
  `purchase_to_stock` decimal(15,6) NOT NULL COMMENT '采购转库存换算率',
  `stock_to_produce` decimal(15,6) NOT NULL COMMENT '库存转生产换算率',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='单位换算表';

-- ----------------------------
-- 增强现有表：打样通知单增加颜色确认字段
-- ----------------------------
ALTER TABLE `t_erp_sample_notice`
ADD COLUMN `color_confirm_status` char(2) DEFAULT '01' COMMENT '颜色确认状态（01待提交 02客户签样 03车间比对 04允许生产）' AFTER `notice_status`,
ADD COLUMN `confirm_by_id` bigint(20) DEFAULT NULL COMMENT '确认人ID' AFTER `color_confirm_status`,
ADD COLUMN `confirm_time` datetime DEFAULT NULL COMMENT '确认时间' AFTER `confirm_by_id`,
ADD COLUMN `color_confirm_images` varchar(2000) DEFAULT NULL COMMENT '颜色对比照片（多个逗号分隔）' AFTER `confirm_time`,
ADD COLUMN `digital_signature` varchar(500) DEFAULT NULL COMMENT '数字签名图片' AFTER `color_confirm_images`,
ADD COLUMN `customer_accept_delta_e` decimal(5,2) DEFAULT NULL COMMENT '客户允许色差ΔE' AFTER `digital_signature`,
ADD COLUMN `light_source_type` varchar(20) DEFAULT 'D65' COMMENT '拍摄光源类型' AFTER `customer_accept_delta_e`;

-- ----------------------------
-- 增强现有表：销售订单增加删除原因和版本号
-- ----------------------------
ALTER TABLE `t_erp_sales_order`
ADD COLUMN `delete_reason` varchar(500) DEFAULT NULL COMMENT '删除原因' AFTER `order_status`,
ADD COLUMN `delete_by` bigint(20) DEFAULT NULL COMMENT '删除人' AFTER `delete_reason`,
ADD COLUMN `delete_time` datetime DEFAULT NULL COMMENT '删除时间' AFTER `delete_by`,
ADD COLUMN `version` int(11) DEFAULT 1 COMMENT 'BOM版本号' AFTER `delete_time`;
