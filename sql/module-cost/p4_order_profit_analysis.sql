-- P4-4: 订单毛利分析基表
-- 按销售订单汇总: 物料成本 + 人工成本 + 外协成本 + 质量损失 + 运费

CREATE TABLE IF NOT EXISTS t_erp_order_profit (
    id                  BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    sales_order_id      BIGINT          NOT NULL COMMENT '销售订单ID',
    sales_no            VARCHAR(50)     DEFAULT NULL COMMENT '销售单号',
    style_code          VARCHAR(50)     DEFAULT NULL COMMENT '款号',
    customer_name       VARCHAR(128)    DEFAULT NULL COMMENT '客户',
    order_qty           DECIMAL(18,3)   DEFAULT 0 COMMENT '订单数量',
    shipped_qty         DECIMAL(18,3)   DEFAULT 0 COMMENT '已出货数量',
    revenue             DECIMAL(18,4)   DEFAULT 0 COMMENT '销售收入',
    material_cost       DECIMAL(18,4)   DEFAULT 0 COMMENT '物料成本',
    labor_cost          DECIMAL(18,4)   DEFAULT 0 COMMENT '人工成本(计件工资)',
    outsource_cost      DECIMAL(18,4)   DEFAULT 0 COMMENT '外协成本',
    freight_cost        DECIMAL(18,4)   DEFAULT 0 COMMENT '运费',
    quality_loss        DECIMAL(18,4)   DEFAULT 0 COMMENT '质量损失',
    total_cost          DECIMAL(18,4)   DEFAULT 0 COMMENT '总成本',
    gross_profit        DECIMAL(18,4)   DEFAULT 0 COMMENT '毛利',
    gross_margin        DECIMAL(10,4)   DEFAULT 0 COMMENT '毛利率',
    period              VARCHAR(7)      DEFAULT NULL COMMENT '统计期间 yyyy-MM',
    factory_id          BIGINT          DEFAULT NULL,
    create_by           VARCHAR(64)     DEFAULT NULL,
    create_time         DATETIME        DEFAULT CURRENT_TIMESTAMP,
    update_by           VARCHAR(64)     DEFAULT NULL,
    update_time         DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark              VARCHAR(500)    DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uk_order_period (sales_order_id, period),
    INDEX idx_profit_period (period),
    INDEX idx_profit_style (style_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单毛利分析';

SELECT 'P4-4: 订单毛利分析表 创建完成' AS result;
