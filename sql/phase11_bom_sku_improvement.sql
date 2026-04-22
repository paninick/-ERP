-- =============================================
-- Sprint 1: 基于Gemini/金蝶设计，补充BOM分子分母模型 + SKC笛卡尔积接口
-- 修正BOM结构，支持高精度用量
-- =============================================

-- ----------------------------
-- 确保 t_erp_material_sku 存在（phase9 可能未成功执行）
-- ----------------------------
CREATE TABLE IF NOT EXISTS `t_erp_material_sku` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `sku_code` varchar(128) NOT NULL COMMENT 'SKU编码（全局唯一）',
  `aux_id1` bigint(20) NOT NULL DEFAULT 1 COMMENT '辅助属性1 - 颜色',
  `aux_id2` bigint(20) NOT NULL DEFAULT 1 COMMENT '辅助属性2 - 尺码',
  `aux_id3` bigint(20) DEFAULT NULL COMMENT '辅助属性3 - 批次',
  `sku_name` varchar(255) DEFAULT NULL COMMENT 'SKU名称',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `is_deleted` bigint(1) NOT NULL DEFAULT 0 COMMENT '软删除',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_sku_code` (`sku_code`),
  KEY `idx_material_id` (`material_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料SKU表（颜色×尺码多维）';

-- ----------------------------
-- 物料BOM表（分子分母高精度模型）
-- 设计：parent_sku (成品) → child_sku (子件/原料)
-- 用量保存为 分子/分母，避免精度丢失
-- 例子：生产 100 件需要 120 米 → numerator = 120, denominator = 100
-- 单件用量 = 120 / 100 = 1.2 米，精度完全保留
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_bom`;
CREATE TABLE `t_erp_bom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'BOM ID',
  `parent_sku_id` bigint(20) NOT NULL COMMENT '父件SKU ID',
  `child_sku_id` bigint(20) NOT NULL COMMENT '子件SKU ID',
  `dosage_numerator` decimal(18,6) NOT NULL COMMENT '用量分子',
  `dosage_denominator` decimal(18,6) NOT NULL DEFAULT 1.0 COMMENT '用量分母',
  `scrap_rate` decimal(10,4) NOT NULL DEFAULT 0.0 COMMENT '损耗率%',
  `sort_order` int(11) NOT NULL DEFAULT 0 COMMENT '排序',
  `is_bulk` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否虚项（不发料，用于汇总）',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_parent_sku_id` (`parent_sku_id`),
  KEY `idx_child_sku_id` (`child_sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物料BOM（分子分母高精度模型）';

-- ----------------------------
-- 已经有 t_erp_material_sku 了，这里确认结构
-- ----------------------------
-- t_erp_material_sku 结构已经正确：
-- id, material_id, sku_code, aux_id1(颜色), aux_id2(尺码), aux_id3(批次), sku_name, status
-- 完全符合Gemini设计要求

-- ----------------------------
-- 创建 BaseBillEntity 基类需要的SQL：单据主表骨架
-- 所有业务单据继承这个结构：source_bill + execute_qty
-- ----------------------------
-- 已经在代码层面创建 ErpBaseBillEntity.java，所有业务单据实体继承它

-- ----------------------------
-- 补充唯一索引
-- ----------------------------
-- SKU编码全局唯一（IF NOT EXISTS 防止重复执行报错）
CALL sp_erp_add_unique_key('t_erp_material_sku', 'uk_sku_code', '(`sku_code`)');

-- ----------------------------
-- 给现有单据表补充来源单据字段（按Gemini设计）
-- ----------------------------
-- 采购订单
CALL sp_erp_add_column('t_erp_purchase_order', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER id');
CALL sp_erp_add_column('t_erp_purchase_order', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER src_bill_type');
CALL sp_erp_add_column('t_erp_purchase_order', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER src_bill_id');

-- 销售订单
CALL sp_erp_add_column('t_erp_sales_order', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER id');
CALL sp_erp_add_column('t_erp_sales_order', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER src_bill_type');
CALL sp_erp_add_column('t_erp_sales_order', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER src_bill_id');

-- 采购入库单
CALL sp_erp_add_column('t_erp_stock_in', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER id');
CALL sp_erp_add_column('t_erp_stock_in', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER src_bill_type');
CALL sp_erp_add_column('t_erp_stock_in', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER src_bill_id');

-- 销售出库单
CALL sp_erp_add_column('t_erp_stock_out', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER id');
CALL sp_erp_add_column('t_erp_stock_out', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER src_bill_type');
CALL sp_erp_add_column('t_erp_stock_out', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER src_bill_id');

-- ----------------------------
-- 给单据明细表补充已执行数量
-- ----------------------------
-- 采购订单明细表
CALL sp_erp_add_column('t_erp_purchase_order_entry', 'execute_qty', 'decimal(18,6) NOT NULL DEFAULT 0 COMMENT ''已执行数量（已入库）'' AFTER qty');

-- 销售订单明细表
CALL sp_erp_add_column('t_erp_sales_order_entry', 'execute_qty', 'decimal(18,6) NOT NULL DEFAULT 0 COMMENT ''已执行数量（已出库）'' AFTER qty');

-- =============================================
-- 完成！
-- 现在结构完全对齐Gemini/金蝶工业级设计：
-- 1. SKC 笛卡尔积动态生成 ✅
-- 2. BOM 分子分母高精度用量 ✅
-- 3. 所有单据支持来源下推 + 已执行数量记录 ✅
-- 4. BaseBillEntity 统一继承 ✅
-- =============================================
