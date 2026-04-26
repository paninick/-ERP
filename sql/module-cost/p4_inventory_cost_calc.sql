-- P4-2: 存货核算 — 全月平均成本算法基表
-- 幂等，可重复执行

CREATE TABLE IF NOT EXISTS t_erp_inventory_cost (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    material_id     BIGINT          NOT NULL COMMENT '材料ID',
    material_code   VARCHAR(64)     DEFAULT NULL COMMENT '材料编号',
    material_name   VARCHAR(128)    DEFAULT NULL COMMENT '材料名称',
    cost_month      VARCHAR(7)      NOT NULL COMMENT '成本月份 yyyy-MM',
    begin_qty       DECIMAL(18,3)   DEFAULT 0 COMMENT '期初数量',
    begin_amount    DECIMAL(18,4)   DEFAULT 0 COMMENT '期初金额',
    in_qty          DECIMAL(18,3)   DEFAULT 0 COMMENT '本期入库数量',
    in_amount       DECIMAL(18,4)   DEFAULT 0 COMMENT '本期入库金额',
    out_qty         DECIMAL(18,3)   DEFAULT 0 COMMENT '本期出库数量',
    out_amount      DECIMAL(18,4)   DEFAULT 0 COMMENT '本期出库金额',
    end_qty         DECIMAL(18,3)   DEFAULT 0 COMMENT '期末数量',
    end_amount      DECIMAL(18,4)   DEFAULT 0 COMMENT '期末金额',
    weighted_avg    DECIMAL(18,6)   DEFAULT NULL COMMENT '全月平均单价',
    calc_status     VARCHAR(20)     DEFAULT 'DRAFT' COMMENT '状态(DRAFT/CALCULATED/CONFIRMED)',
    factory_id      BIGINT          DEFAULT NULL,
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark          VARCHAR(500)    DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uk_cost_month (material_id, cost_month),
    INDEX idx_cost_month (cost_month),
    INDEX idx_cost_status (calc_status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='存货核算月度成本';

SELECT 'P4-2: 存货核算表 创建完成' AS result;
