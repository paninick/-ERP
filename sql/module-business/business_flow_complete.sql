-- 业务全流程字段补全：寻样→打样→下单→采购→生产→外发→质检→出货
-- 幂等，可重复执行

-- ═══════════════════════════════════════
-- 1. 客供/厂供 材料区分
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sales_order_material' AND COLUMN_NAME='supply_type');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sales_order_material ADD COLUMN supply_type VARCHAR(20) DEFAULT ''FACTORY'' COMMENT ''供应方式 FACTORY=厂供 CUSTOMER=客供 MIXED=混合'' AFTER material_type', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_bom_material' AND COLUMN_NAME='supply_type');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_bom_material ADD COLUMN supply_type VARCHAR(20) DEFAULT ''FACTORY'' COMMENT ''供应方式'' AFTER material_name', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- 供应方式字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time) SELECT '供应方式','erp_supply_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_supply_type');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 1,'厂供','FACTORY','erp_supply_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_supply_type' AND dict_value='FACTORY');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 2,'客供','CUSTOMER','erp_supply_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_supply_type' AND dict_value='CUSTOMER');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 3,'混合','MIXED','erp_supply_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_supply_type' AND dict_value='MIXED');

-- ═══════════════════════════════════════
-- 2. 颜色确认（设计可能先下单后确认颜色）
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sales_order' AND COLUMN_NAME='color_confirmed');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sales_order ADD COLUMN color_confirmed CHAR(1) DEFAULT ''N'' COMMENT ''颜色是否已确认(Y/N)'' AFTER style_code', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sales_order' AND COLUMN_NAME='color_confirm_date');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sales_order ADD COLUMN color_confirm_date DATE DEFAULT NULL COMMENT ''颜色确认日期'' AFTER color_confirmed', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- ═══════════════════════════════════════
-- 3. 设计变更单
-- ═══════════════════════════════════════
CREATE TABLE IF NOT EXISTS t_erp_design_change (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    sales_order_id  BIGINT NOT NULL COMMENT '销售订单ID',
    sales_no        VARCHAR(50) COMMENT '销售单号',
    change_type     VARCHAR(30) NOT NULL COMMENT '变更类型 COLOR/SIZE/QTY/STYLE/MATERIAL',
    before_value    VARCHAR(500) COMMENT '变更前',
    after_value     VARCHAR(500) COMMENT '变更后',
    change_reason   VARCHAR(500) COMMENT '变更原因',
    change_by       VARCHAR(50) COMMENT '申请人',
    change_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    approved_by     VARCHAR(50) COMMENT '审批人',
    approved_time   DATETIME COMMENT '审批时间',
    status          VARCHAR(20) DEFAULT 'PENDING' COMMENT 'PENDING/APPROVED/REJECTED',
    factory_id      BIGINT,
    create_by       VARCHAR(64),
    create_time     DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64),
    update_time     DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark          VARCHAR(500),
    PRIMARY KEY (id),
    INDEX idx_dc_sales (sales_order_id),
    INDEX idx_dc_type (change_type),
    INDEX idx_dc_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='设计变更单';

-- 变更类型字典
INSERT INTO sys_dict_type (dict_name,dict_type,status,create_by,create_time) SELECT '设计变更类型','erp_design_change_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_design_change_type');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 1,'颜色变更','COLOR','erp_design_change_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_design_change_type' AND dict_value='COLOR');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 2,'尺码变更','SIZE','erp_design_change_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_design_change_type' AND dict_value='SIZE');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 3,'数量变更','QTY','erp_design_change_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_design_change_type' AND dict_value='QTY');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 4,'款号变更','STYLE','erp_design_change_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_design_change_type' AND dict_value='STYLE');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 5,'材料变更','MATERIAL','erp_design_change_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_design_change_type' AND dict_value='MATERIAL');

-- ═══════════════════════════════════════
-- 4. 打样轮次 + 客户确认
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sample_notice' AND COLUMN_NAME='round_number');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sample_notice ADD COLUMN round_number INT DEFAULT 1 COMMENT ''打样轮次(1=初样2=修改样3=确认样)'' AFTER sample_no', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sample_notice' AND COLUMN_NAME='customer_approved');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sample_notice ADD COLUMN customer_approved CHAR(1) DEFAULT ''N'' COMMENT ''客户是否确认(Y/N)'' AFTER round_number', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sample_notice' AND COLUMN_NAME='customer_feedback');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sample_notice ADD COLUMN customer_feedback VARCHAR(1000) COMMENT ''客户反馈/修改意见'' AFTER customer_approved', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- ═══════════════════════════════════════
-- 5. 插单/优先级
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_plan' AND COLUMN_NAME='priority');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_produce_plan ADD COLUMN priority VARCHAR(20) DEFAULT ''NORMAL'' COMMENT ''优先级 NORMAL/URGENT/INSERT'' AFTER plan_status', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_plan' AND COLUMN_NAME='insert_reason');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_produce_plan ADD COLUMN insert_reason VARCHAR(500) COMMENT ''插单原因'' AFTER priority', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- 优先级字典
INSERT INTO sys_dict_type (dict_name,dict_type,status,create_by,create_time) SELECT '排单优先级','erp_plan_priority','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_plan_priority');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 1,'普通','NORMAL','erp_plan_priority','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_plan_priority' AND dict_value='NORMAL');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 2,'加急','URGENT','erp_plan_priority','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_plan_priority' AND dict_value='URGENT');
INSERT INTO sys_dict_data (dict_sort,dict_label,dict_value,dict_type,status,create_by,create_time) SELECT 3,'插单','INSERT','erp_plan_priority','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_plan_priority' AND dict_value='INSERT');

-- ═══════════════════════════════════════
-- 6. 外协分批
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='batch_no');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_outsource_order ADD COLUMN batch_no VARCHAR(50) COMMENT ''分批号'' AFTER job_ids', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='batch_seq');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_outsource_order ADD COLUMN batch_seq INT DEFAULT 1 COMMENT ''批次序号'' AFTER batch_no', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='batch_total');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_outsource_order ADD COLUMN batch_total INT DEFAULT 1 COMMENT ''总批次数'' AFTER batch_seq', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- ═══════════════════════════════════════
-- 7. 物流/船运
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_shipment' AND COLUMN_NAME='etd');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_shipment ADD COLUMN etd DATE COMMENT ''预计开船日'' AFTER vessel_name', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_shipment' AND COLUMN_NAME='eta');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_shipment ADD COLUMN eta DATE COMMENT ''预计到港日'' AFTER etd', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_shipment' AND COLUMN_NAME='port_of_loading');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_shipment ADD COLUMN port_of_loading VARCHAR(100) COMMENT ''装运港'' AFTER eta', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_shipment' AND COLUMN_NAME='port_of_discharge');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_shipment ADD COLUMN port_of_discharge VARCHAR(100) COMMENT ''卸货港'' AFTER port_of_loading', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

-- ═══════════════════════════════════════
-- 8. 样品确认到工厂
-- ═══════════════════════════════════════
SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sample_notice' AND COLUMN_NAME='factory_confirmed');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sample_notice ADD COLUMN factory_confirmed CHAR(1) DEFAULT ''N'' COMMENT ''工厂是否确认可生产'' AFTER customer_feedback', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SET @c = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_sample_notice' AND COLUMN_NAME='factory_confirm_date');
SET @s = IF(@c=0, 'ALTER TABLE t_erp_sample_notice ADD COLUMN factory_confirm_date DATE COMMENT ''工厂确认日期'' AFTER factory_confirmed', 'SELECT 1');
PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SELECT '业务全流程字段补全 完成' AS result;
