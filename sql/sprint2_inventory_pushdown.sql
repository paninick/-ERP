-- =============================================
-- Sprint 2: 流程打通 - 下推工作流 & 库存锁定机制
-- 数据库变更脚本
-- =============================================

-- 1. 给所有业务主表添加来源单据字段
-- 用于支持单据下推追溯：下游单据记录上游来源，全程可追溯
CALL sp_erp_add_column('t_erp_purchase', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_purchase', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_purchase', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

CALL sp_erp_add_column('t_erp_sales_order', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_sales_order', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_sales_order', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

CALL sp_erp_add_column('t_erp_stock_in', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_stock_in', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_stock_in', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

CALL sp_erp_add_column('t_erp_stock_out', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_stock_out', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_stock_out', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

CALL sp_erp_add_column('t_erp_produce_plan', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_produce_plan', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_produce_plan', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

CALL sp_erp_add_column('t_erp_outsource_order', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_outsource_order', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_outsource_order', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

CALL sp_erp_add_column('t_erp_sample_notice', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `id`');
CALL sp_erp_add_column('t_erp_sample_notice', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_sample_notice', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');

-- 2. 给单据明细表添加已执行数量
-- 用于跟踪上游单据（采购订单/销售订单）已被下游执行了多少数量
CALL sp_erp_add_column('t_erp_purchase_item', 'execute_qty', 'decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT ''已执行数量（已入库）''');

CALL sp_erp_add_column('t_erp_sales_order_item', 'execute_qty', 'decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT ''已执行数量（已出库）''');

-- 3. 给库存流水补充来源字段和SKU
-- 实现库存变动全程可追溯：每一笔库存变动都知道来自哪张单据
CALL sp_erp_add_column('t_erp_stock_log', 'src_bill_type', 'varchar(64) DEFAULT NULL COMMENT ''来源单据类型'' AFTER `relation_id`');
CALL sp_erp_add_column('t_erp_stock_log', 'src_bill_id', 'bigint(20) DEFAULT NULL COMMENT ''来源单据ID'' AFTER `src_bill_type`');
CALL sp_erp_add_column('t_erp_stock_log', 'src_bill_no', 'varchar(64) DEFAULT NULL COMMENT ''来源单据号'' AFTER `src_bill_id`');
CALL sp_erp_add_column('t_erp_stock_log', 'sku_id', 'bigint(20) DEFAULT NULL COMMENT ''SKU ID'' AFTER `material_id`');

-- 4. 确保库存总表存在（如果还没有创建）
-- 库存总表：按仓库+SKU维度汇总当前库存
-- 核心设计：乐观锁version + 可用库存inv_qty + 锁定库存lock_qty
CREATE TABLE IF NOT EXISTS `t_erp_inv_stock` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `warehouse_id` bigint(20) NOT NULL COMMENT '仓库ID',
  `sku_id` bigint(20) NOT NULL COMMENT 'SKU ID',
  `material_id` bigint(20) NOT NULL COMMENT '物料ID',
  `inv_qty` decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT '库存数量',
  `lock_qty` decimal(18,6) NOT NULL DEFAULT 0.000000 COMMENT '锁定数量',
  `version` int(11) NOT NULL DEFAULT 0 COMMENT '乐观锁版本',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_warehouse_sku` (`warehouse_id`, `sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存总表';

-- 执行说明：
-- 1. 此脚本需要在部署前执行
-- 2. 新增字段都允许NULL，因为手动创建的单据不需要来源
-- 3. execute_qty 默认值为0，不影响已有数据
-- 4. 唯一索引 uk_warehouse_sku 保证同一个仓库同一个SKU只有一条记录
