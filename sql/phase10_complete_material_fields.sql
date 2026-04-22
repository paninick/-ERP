-- =============================================
-- 阶段十：基于金蝶ERP实践，补全物料主数据缺失字段
-- 参考金蝶BOS成熟设计，补充工业级必需字段
-- =============================================

-- ----------------------------
-- 给 t_erp_main_material 补全缺失的业务字段
-- ----------------------------
CALL sp_erp_add_column('t_erp_main_material', 'over_receipt_ratio', 'decimal(5,2) DEFAULT 0.00 COMMENT ''采购允许超收比例%'' AFTER factory_no');
CALL sp_erp_add_column('t_erp_main_material', 'over_ship_ratio', 'decimal(5,2) DEFAULT 0.00 COMMENT ''销售允许超发比例%'' AFTER over_receipt_ratio');
CALL sp_erp_add_column('t_erp_main_material', 'is_purchase', 'tinyint(1) DEFAULT 1 COMMENT ''可采购'' AFTER over_ship_ratio');
CALL sp_erp_add_column('t_erp_main_material', 'is_sale', 'tinyint(1) DEFAULT 1 COMMENT ''可销售'' AFTER is_purchase');
CALL sp_erp_add_column('t_erp_main_material', 'is_outsource', 'tinyint(1) DEFAULT 0 COMMENT ''可委外'' AFTER is_sale');
CALL sp_erp_add_column('t_erp_main_material', 'is_self_make', 'tinyint(1) DEFAULT 0 COMMENT ''可自制'' AFTER is_outsource');
CALL sp_erp_add_column('t_erp_main_material', 'default_warehouse_id', 'bigint(20) DEFAULT NULL COMMENT ''默认仓库ID'' AFTER is_self_make');
CALL sp_erp_add_column('t_erp_main_material', 'default_location_id', 'bigint(20) DEFAULT NULL COMMENT ''默认仓位ID'' AFTER default_warehouse_id');
CALL sp_erp_add_column('t_erp_main_material', 'planner_id', 'bigint(20) DEFAULT NULL COMMENT ''计划员ID'' AFTER default_location_id');
CALL sp_erp_add_column('t_erp_main_material', 'buyer_id', 'bigint(20) DEFAULT NULL COMMENT ''采购员ID'' AFTER planner_id');
CALL sp_erp_add_column('t_erp_main_material', 'producing_department', 'varchar(100) DEFAULT NULL COMMENT ''默认生产车间'' AFTER buyer_id');
CALL sp_erp_add_column('t_erp_main_material', 'min_stock_qty', 'decimal(18,6) DEFAULT NULL COMMENT ''最低库存预警'' AFTER default_warehouse_id');
CALL sp_erp_add_column('t_erp_main_material', 'max_stock_qty', 'decimal(18,6) DEFAULT NULL COMMENT ''最高库存预警'' AFTER min_stock_qty');
CALL sp_erp_add_column('t_erp_main_material', 'safe_stock_qty', 'decimal(18,6) DEFAULT NULL COMMENT ''安全库存'' AFTER max_stock_qty');
CALL sp_erp_add_column('t_erp_main_material', 'min_order_qty', 'decimal(18,6) DEFAULT NULL COMMENT ''最小订货量'' AFTER safe_stock_qty');
CALL sp_erp_add_column('t_erp_main_material', 'purchase_price', 'decimal(18,6) DEFAULT NULL COMMENT ''采购价'' AFTER min_order_qty');
CALL sp_erp_add_column('t_erp_main_material', 'outsource_price', 'decimal(18,6) DEFAULT NULL COMMENT ''委外价'' AFTER purchase_price');
CALL sp_erp_add_column('t_erp_main_material', 'standard_cost', 'decimal(18,6) DEFAULT NULL COMMENT ''标准成本'' AFTER outsource_price');
CALL sp_erp_add_column('t_erp_main_material', 'retail_price', 'decimal(18,6) DEFAULT NULL COMMENT ''零售价'' AFTER standard_cost');
CALL sp_erp_add_column('t_erp_main_material', 'wholesale_price', 'decimal(18,6) DEFAULT NULL COMMENT ''批发价'' AFTER retail_price');
CALL sp_erp_add_column('t_erp_main_material', 'vip_price', 'decimal(18,6) DEFAULT NULL COMMENT ''会员价'' AFTER wholesale_price');
CALL sp_erp_add_column('t_erp_main_material', 'min_sales_price', 'decimal(18,6) DEFAULT NULL COMMENT ''最低销售价'' AFTER vip_price');
CALL sp_erp_add_column('t_erp_main_material', 'purchase_tax_rate', 'decimal(5,2) DEFAULT NULL COMMENT ''进项税率%'' AFTER min_sales_price');
CALL sp_erp_add_column('t_erp_main_material', 'sales_tax_rate', 'decimal(5,2) DEFAULT NULL COMMENT ''销项税率%'' AFTER purchase_tax_rate');
CALL sp_erp_add_column('t_erp_main_material', 'gross_weight', 'decimal(18,3) DEFAULT NULL COMMENT ''毛重'' AFTER sales_tax_rate');
CALL sp_erp_add_column('t_erp_main_material', 'net_weight', 'decimal(18,3) DEFAULT NULL COMMENT ''净重'' AFTER gross_weight');
CALL sp_erp_add_column('t_erp_main_material', 'length', 'decimal(18,2) DEFAULT NULL COMMENT ''长'' AFTER net_weight');
CALL sp_erp_add_column('t_erp_main_material', 'width', 'decimal(18,2) DEFAULT NULL COMMENT ''宽'' AFTER length');
CALL sp_erp_add_column('t_erp_main_material', 'height', 'decimal(18,2) DEFAULT NULL COMMENT ''高'' AFTER width');
CALL sp_erp_add_column('t_erp_main_material', 'volume', 'decimal(18,3) DEFAULT NULL COMMENT ''体积'' AFTER height');
CALL sp_erp_add_column('t_erp_main_material', 'weight_unit', 'varchar(20) DEFAULT NULL COMMENT ''重量单位'' AFTER volume');
CALL sp_erp_add_column('t_erp_main_material', 'volume_unit', 'varchar(20) DEFAULT NULL COMMENT ''体积单位'' AFTER weight_unit');
CALL sp_erp_add_column('t_erp_main_material', 'is_batch_manage', 'tinyint(1) DEFAULT 0 COMMENT ''是否启用批次管理'' AFTER volume_unit');
CALL sp_erp_add_column('t_erp_main_material', 'is_expire_date', 'tinyint(1) DEFAULT 0 COMMENT ''是否启用保质期管理'' AFTER is_batch_manage');

-- ----------------------------
-- 给 t_erp_sales_order 补充字段
-- ----------------------------
CALL sp_erp_add_column('t_erp_sales_order', 'over_ship_ratio', 'decimal(5,2) DEFAULT NULL COMMENT ''允许超发比例%（取自物料，可修改）'' AFTER order_qty');

-- ----------------------------
-- 给 t_erp_purchase_order 补充字段
-- ----------------------------
CALL sp_erp_add_column('t_erp_purchase_order', 'over_receipt_ratio', 'decimal(5,2) DEFAULT NULL COMMENT ''允许超收比例%（取自物料，可修改）'' AFTER order_qty');

-- ----------------------------
-- 创建 物料价格表（支持不同客户不同价格）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_material_price`;
CREATE TABLE `t_erp_material_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '价格ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `price_type` varchar(32) NOT NULL COMMENT '价格类型：retail/wholesale/vip/custom',
  `customer_level_id` bigint(20) DEFAULT NULL COMMENT '客户等级ID',
  `price` decimal(18,6) NOT NULL COMMENT '价格',
  `effective_date` date NOT NULL COMMENT '生效日期',
  `expire_date` date DEFAULT NULL COMMENT '失效日期',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_material_id` (`material_id`),
  KEY `idx_price_type` (`price_type`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料价格表（支持多级价格/客户分级价格）';

-- ----------------------------
-- 创建 工作中心（车间/设备）
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_work_center`;
CREATE TABLE `t_erp_work_center` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '工作中心ID',
  `work_center_code` varchar(50) NOT NULL COMMENT '工作中心编码',
  `work_center_name` varchar(100) NOT NULL COMMENT '工作中心名称',
  `department_id` bigint(20) DEFAULT NULL COMMENT '所属部门',
  `capacity_per_day` decimal(18,2) DEFAULT NULL COMMENT '日产能',
  `unit` varchar(20) DEFAULT NULL COMMENT '产能单位',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '负责人ID',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_work_center_code` (`work_center_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作中心（车间/设备产能）';

-- ----------------------------
-- 创建 工作日日历
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_work_calendar`;
CREATE TABLE `t_erp_work_calendar` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日历ID',
  `work_date` date NOT NULL COMMENT '日期',
  `year` int(11) NOT NULL COMMENT '年份',
  `month` int(11) NOT NULL COMMENT '月份',
  `day` int(11) NOT NULL COMMENT '日期',
  `is_work_day` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否工作日 1是 0否',
  `work_hours` decimal(5,2) DEFAULT 8.0 COMMENT '工作时长',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_work_date` (`work_date`),
  KEY `idx_year_month` (`year`, `month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作日日历';

-- ----------------------------
-- 创建 客户分类 + 客户
-- 参考金蝶模板结构
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_customer_category`;
CREATE TABLE `t_erp_customer_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_code` varchar(50) NOT NULL COMMENT '分类编码',
  `category_name` varchar(100) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父分类ID',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_code` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户分类';

DROP TABLE IF EXISTS `t_erp_customer`;
CREATE TABLE `t_erp_customer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_code` varchar(50) NOT NULL COMMENT '客户编码',
  `customer_name` varchar(100) NOT NULL COMMENT '客户名称',
  `customer_short_name` varchar(50) DEFAULT NULL COMMENT '客户简称',
  `category_id` bigint(20) NOT NULL COMMENT '客户分类ID',
  `level_id` bigint(20) DEFAULT NULL COMMENT '客户等级ID',
  `credit_limit` decimal(18,2) DEFAULT NULL COMMENT '信用额度',
  `payment_days` int(11) DEFAULT 30 COMMENT '付款账期天数',
  `contact_name` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `tax_id` varchar(50) DEFAULT NULL COMMENT '税号',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户行',
  `bank_account` varchar(50) DEFAULT NULL COMMENT '银行账号',
  `delivery_cycle` int(11) DEFAULT NULL COMMENT '默认交货周期（天）',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_customer_code` (`customer_code`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户档案';

-- ----------------------------
-- 创建 供应商分类 + 供应商
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_supplier_category`;
CREATE TABLE `t_erp_supplier_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `category_code` varchar(50) NOT NULL COMMENT '分类编码',
  `category_name` varchar(100) NOT NULL COMMENT '分类名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父分类ID',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_category_code` (`category_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商分类';

DROP TABLE IF EXISTS `t_erp_supplier`;
CREATE TABLE `t_erp_supplier` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_code` varchar(50) NOT NULL COMMENT '供应商编码',
  `supplier_name` varchar(100) NOT NULL COMMENT '供应商名称',
  `supplier_short_name` varchar(50) DEFAULT NULL COMMENT '供应商简称',
  `category_id` bigint(20) NOT NULL COMMENT '供应商分类ID',
  `contact_name` varchar(50) DEFAULT NULL COMMENT '联系人',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `tax_id` varchar(50) DEFAULT NULL COMMENT '税号',
  `bank_name` varchar(100) DEFAULT NULL COMMENT '开户行',
  `bank_account` varchar(50) DEFAULT NULL COMMENT '银行账号',
  `payment_days` int(11) DEFAULT 30 COMMENT '付款账期天数',
  `default_leadtime` int(11) DEFAULT NULL COMMENT '默认采购提前期（天）',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_supplier_code` (`supplier_code`),
  KEY `idx_category_id` (`category_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商档案';

-- ----------------------------
-- 创建 仓库 + 库区 + 库位
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_warehouse`;
CREATE TABLE `t_erp_warehouse` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '仓库ID',
  `warehouse_code` varchar(50) NOT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(100) NOT NULL COMMENT '仓库名称',
  `warehouse_type` char(1) DEFAULT '1' COMMENT '仓库类型 1原料仓 2半成品仓 3成品仓 4次品仓',
  `status` char(1) DEFAULT '0' COMMENT '状态 0启用 1停用',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '仓管员ID',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouse_code` (`warehouse_code`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='仓库';

DROP TABLE IF EXISTS `t_erp_warehouse_area`;
CREATE TABLE `t_erp_warehouse_area` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库区ID',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库ID',
  `area_code` varchar(50) NOT NULL COMMENT '库区编码',
  `area_name` varchar(100) NOT NULL COMMENT '库区名称',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态 0启用 1停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_area_code` (`warehouse_id`, `area_code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库区';

DROP TABLE IF EXISTS `t_erp_warehouse_location`;
CREATE TABLE `t_erp_warehouse_location` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '库位ID',
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库ID',
  `area_id` bigint(20) NOT NULL COMMENT '库区ID',
  `location_code` varchar(50) NOT NULL COMMENT '库位编码',
  `location_name` varchar(100) NOT NULL COMMENT '库位名称',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态 0启用 1停用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_location_code` (`warehouse_id`, `area_id`, `location_code`),
  KEY `idx_warehouse_id` (`warehouse_id`),
  KEY `idx_area_id` (`area_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库位';

-- =============================================
-- 完成！
-- 现在物料/客户/供应商/仓库 四个基础档案都达到金蝶同等完备性
-- =============================================
