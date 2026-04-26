-- P3-2: 出货单独立模块
-- 幂等，可重复执行

CREATE TABLE IF NOT EXISTS t_erp_shipment (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    shipment_no     VARCHAR(50)     NOT NULL COMMENT '出货单号',
    sales_order_id  BIGINT          DEFAULT NULL COMMENT '销售订单ID',
    sales_no        VARCHAR(50)     DEFAULT NULL COMMENT '销售单号',
    style_code      VARCHAR(50)     DEFAULT NULL COMMENT '款号',
    customer_id     BIGINT          DEFAULT NULL COMMENT '客户ID',
    customer_name   VARCHAR(128)    DEFAULT NULL COMMENT '客户名称',
    shipment_date   DATE            DEFAULT NULL COMMENT '出货日期',
    total_qty       DECIMAL(18,3)   DEFAULT NULL COMMENT '出货总数量',
    total_carton    INT             DEFAULT 0 COMMENT '总箱数',
    total_weight    DECIMAL(18,3)   DEFAULT NULL COMMENT '总毛重(kg)',
    total_volume    DECIMAL(18,3)   DEFAULT NULL COMMENT '总体积(m³)',
    vessel_name     VARCHAR(100)    DEFAULT NULL COMMENT '船名/航次',
    container_no    VARCHAR(100)    DEFAULT NULL COMMENT '集装箱号',
    bl_no           VARCHAR(100)    DEFAULT NULL COMMENT '提单号',
    invoice_no      VARCHAR(100)    DEFAULT NULL COMMENT '发票号',
    release_status  VARCHAR(20)     DEFAULT 'PENDING' COMMENT '放行状态(PENDING/RELEASED/HOLD)',
    release_by      VARCHAR(50)     DEFAULT NULL COMMENT '放行人',
    release_time    DATETIME        DEFAULT NULL COMMENT '放行时间',
    status          VARCHAR(20)     DEFAULT 'DRAFT' COMMENT '状态(DRAFT/CONFIRMED/SHIPPED)',
    factory_id      BIGINT          DEFAULT NULL COMMENT '工厂ID',
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark          VARCHAR(500)    DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uk_shipment_no (shipment_no),
    INDEX idx_shipment_sales (sales_order_id),
    INDEX idx_shipment_style (style_code),
    INDEX idx_shipment_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出货单';

-- 出货状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '出货状态', 'erp_shipment_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_shipment_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'草稿','DRAFT','erp_shipment_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_shipment_status' AND dict_value='DRAFT');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'已确认','CONFIRMED','erp_shipment_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_shipment_status' AND dict_value='CONFIRMED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'已出运','SHIPPED','erp_shipment_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_shipment_status' AND dict_value='SHIPPED');

-- 放行状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '出货放行状态', 'erp_shipment_release', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_shipment_release');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'待放行','PENDING','erp_shipment_release','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_shipment_release' AND dict_value='PENDING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'已放行','RELEASED','erp_shipment_release','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_shipment_release' AND dict_value='RELEASED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'暂扣','HOLD','erp_shipment_release','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_shipment_release' AND dict_value='HOLD');

SELECT 'P3-2: 出货单表+字典 创建完成' AS result;
