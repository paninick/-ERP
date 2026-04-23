-- =============================================
-- Sprint D — 质检与发票模块建表脚本
-- 执行顺序：本脚本单独执行，无依赖
-- =============================================

-- ----------------------------
-- 质检单主表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_qc_inspection`;
CREATE TABLE `t_erp_qc_inspection` (
  `id`            bigint(20)     NOT NULL AUTO_INCREMENT COMMENT '主键',
  `batch_no`      varchar(64)    NOT NULL COMMENT '批次号',
  `style_no`      varchar(64)    DEFAULT NULL COMMENT '款号',
  `order_no`      varchar(64)    DEFAULT NULL COMMENT '关联工单号',
  `result`        varchar(16)    NOT NULL COMMENT '检验结果: PASS/FAIL/HOLD',
  `pass_rate`     decimal(5,2)   DEFAULT NULL COMMENT '合格率(%)',
  `sample_qty`    int(11)        DEFAULT 0 COMMENT '抽样数量',
  `defect_qty`    int(11)        DEFAULT 0 COMMENT '缺陷数量',
  `inspector_id`  bigint(20)     DEFAULT NULL COMMENT '检验员ID',
  `inspector_name` varchar(64)   DEFAULT NULL COMMENT '检验员姓名',
  `factory_id`    bigint(20)     DEFAULT NULL COMMENT '工厂ID',
  `remark`        varchar(500)   DEFAULT NULL COMMENT '备注',
  `status`        varchar(16)    DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/REJECTED',
  `reject_reason` varchar(500)   DEFAULT NULL COMMENT '打回原因',
  `create_by`     varchar(64)    DEFAULT NULL,
  `create_time`   datetime       DEFAULT NULL,
  `update_by`     varchar(64)    DEFAULT NULL,
  `update_time`   datetime       DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_batch_no` (`batch_no`),
  KEY `idx_factory_id` (`factory_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='质检单主表';

-- ----------------------------
-- 质检缺陷明细表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_qc_defect_record`;
CREATE TABLE `t_erp_qc_defect_record` (
  `id`             bigint(20)   NOT NULL AUTO_INCREMENT COMMENT '主键',
  `inspection_id`  bigint(20)   NOT NULL COMMENT '质检单ID',
  `defect_type`    varchar(64)  NOT NULL COMMENT '缺陷类型（如：色差/尺寸/断线）',
  `defect_level`   varchar(16)  NOT NULL COMMENT '严重程度: MINOR/MAJOR/CRITICAL',
  `qty`            int(11)      DEFAULT 1 COMMENT '缺陷数量',
  `create_time`    datetime     DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_inspection_id` (`inspection_id`),
  KEY `idx_defect_level` (`defect_level`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='质检缺陷明细表';

-- ----------------------------
-- 发票主表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_fin_invoice`;
CREATE TABLE `t_erp_fin_invoice` (
  `id`              bigint(20)      NOT NULL AUTO_INCREMENT COMMENT '主键',
  `invoice_no`      varchar(64)     NOT NULL COMMENT '发票号',
  `customer_id`     bigint(20)      DEFAULT NULL COMMENT '客户ID',
  `customer_name`   varchar(128)    DEFAULT NULL COMMENT '客户名称',
  `total_amount`    decimal(18,2)   NOT NULL COMMENT '发票总金额',
  `settled_amount`  decimal(18,2)   DEFAULT 0.00 COMMENT '已核销金额',
  `tax_number`      varchar(255)    DEFAULT NULL COMMENT '税号(AES加密存储)',
  `status`          varchar(16)     DEFAULT 'PENDING' COMMENT '状态: PENDING/PARTIAL/SETTLED/RED_ISSUED',
  `invoice_date`    date            DEFAULT NULL COMMENT '开票日期',
  `factory_id`      bigint(20)      DEFAULT NULL COMMENT '工厂ID',
  `remark`          varchar(500)    DEFAULT NULL COMMENT '备注',
  `create_by`       varchar(64)     DEFAULT NULL,
  `create_time`     datetime        DEFAULT NULL,
  `update_by`       varchar(64)     DEFAULT NULL,
  `update_time`     datetime        DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_invoice_no` (`invoice_no`),
  KEY `idx_status` (`status`),
  KEY `idx_factory_id` (`factory_id`),
  KEY `idx_invoice_date` (`invoice_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务发票主表';

-- ----------------------------
-- 发票对账明细表
-- ----------------------------
DROP TABLE IF EXISTS `t_erp_fin_reconciliation`;
CREATE TABLE `t_erp_fin_reconciliation` (
  `id`                bigint(20)    NOT NULL AUTO_INCREMENT COMMENT '主键',
  `invoice_id`        bigint(20)    NOT NULL COMMENT '发票ID',
  `stock_in_id`       bigint(20)    DEFAULT NULL COMMENT '关联入库单ID',
  `reconcile_amount`  decimal(18,2) NOT NULL COMMENT '核销金额',
  `status`            varchar(16)   DEFAULT 'ACTIVE' COMMENT '状态: ACTIVE/REVERSED',
  `create_by`         varchar(64)   DEFAULT NULL,
  `create_time`       datetime      DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_invoice_id` (`invoice_id`),
  KEY `idx_stock_in_id` (`stock_in_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='发票对账明细表';
