-- P4-1: 三单核销 (发货单/发票/应收)
-- 幂等，可重复执行

-- 核销记录表
CREATE TABLE IF NOT EXISTS t_erp_reconciliation (
    id                  BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    shipment_id         BIGINT          DEFAULT NULL COMMENT '出货单ID',
    shipment_no         VARCHAR(50)     DEFAULT NULL COMMENT '出货单号',
    invoice_id          BIGINT          DEFAULT NULL COMMENT '发票ID',
    invoice_no          VARCHAR(50)     DEFAULT NULL COMMENT '发票号',
    amount              DECIMAL(18,4)   NOT NULL COMMENT '核销金额',
    reconcile_date      DATE            DEFAULT NULL COMMENT '核销日期',
    reconcile_by        VARCHAR(50)     DEFAULT NULL COMMENT '核销人',
    status              VARCHAR(20)     DEFAULT 'ACTIVE' COMMENT '状态(ACTIVE/REVERSED)',
    remark              VARCHAR(500)    DEFAULT NULL,
    create_by           VARCHAR(64)     DEFAULT NULL,
    create_time         DATETIME        DEFAULT CURRENT_TIMESTAMP,
    update_by           VARCHAR(64)     DEFAULT NULL,
    update_time         DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    INDEX idx_rec_shipment (shipment_id),
    INDEX idx_rec_invoice (invoice_id),
    INDEX idx_rec_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='三单核销记录';

-- 出货单→发票核销关联字段
SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_shipment' AND COLUMN_NAME='settled_amount');
SET @s1 = IF(@c1=0, 'ALTER TABLE t_erp_shipment ADD COLUMN settled_amount DECIMAL(18,4) DEFAULT 0 COMMENT ''已核销金额'' AFTER invoice_no', 'SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_shipment' AND COLUMN_NAME='receivable_amount');
SET @s2 = IF(@c2=0, 'ALTER TABLE t_erp_shipment ADD COLUMN receivable_amount DECIMAL(18,4) DEFAULT NULL COMMENT ''应收金额'' AFTER settled_amount', 'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

-- 核销状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '核销状态', 'erp_reconciliation_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_reconciliation_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'有效','ACTIVE','erp_reconciliation_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_reconciliation_status' AND dict_value='ACTIVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'已冲销','REVERSED','erp_reconciliation_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_reconciliation_status' AND dict_value='REVERSED');

SELECT 'P4-1: 三单核销表 + 出货单扩展字段 完成' AS result;
