-- P4-3: 质量损失成本追溯
-- 次品/返工/补片/重洗/降级/报废 的成本记录

CREATE TABLE IF NOT EXISTS t_erp_quality_loss_cost (
    id              BIGINT          NOT NULL AUTO_INCREMENT COMMENT '主键',
    job_id          BIGINT          DEFAULT NULL COMMENT '生产单ID',
    process_id      BIGINT          DEFAULT NULL COMMENT '工序ID',
    loss_type       VARCHAR(30)     NOT NULL COMMENT '损失类型(SCRAP/REWORK/PATCH/REWASH/DOWNGRADE)',
    loss_qty        DECIMAL(18,3)   DEFAULT 0 COMMENT '损失数量',
    unit_cost       DECIMAL(18,4)   DEFAULT 0 COMMENT '单位成本',
    material_cost   DECIMAL(18,4)   DEFAULT 0 COMMENT '材料损失',
    labor_cost      DECIMAL(18,4)   DEFAULT 0 COMMENT '人工损失',
    other_cost      DECIMAL(18,4)   DEFAULT 0 COMMENT '其他费用',
    total_loss      DECIMAL(18,4)   DEFAULT 0 COMMENT '总损失金额',
    loss_date       DATE            DEFAULT NULL COMMENT '发生日期',
    responsible     VARCHAR(50)     DEFAULT NULL COMMENT '责任归属(SELF/OUTSOURCE/MATERIAL)',
    defect_id       BIGINT          DEFAULT NULL COMMENT '关联缺陷记录ID',
    abnormal_id     BIGINT          DEFAULT NULL COMMENT '关联异常池ID',
    status          VARCHAR(20)     DEFAULT 'OPEN' COMMENT '状态(OPEN/CLOSED)',
    factory_id      BIGINT          DEFAULT NULL,
    create_by       VARCHAR(64)     DEFAULT NULL,
    create_time     DATETIME        DEFAULT CURRENT_TIMESTAMP,
    update_by       VARCHAR(64)     DEFAULT NULL,
    update_time     DATETIME        DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    remark          VARCHAR(500)    DEFAULT NULL,
    PRIMARY KEY (id),
    INDEX idx_loss_job (job_id),
    INDEX idx_loss_type (loss_type),
    INDEX idx_loss_date (loss_date),
    INDEX idx_loss_responsible (responsible)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='质量损失成本';

-- 损失类型字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '质量损失类型', 'erp_quality_loss_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_quality_loss_type');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'报废','SCRAP','erp_quality_loss_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_quality_loss_type' AND dict_value='SCRAP');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'返工','REWORK','erp_quality_loss_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_quality_loss_type' AND dict_value='REWORK');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'补片','PATCH','erp_quality_loss_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_quality_loss_type' AND dict_value='PATCH');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'重洗','REWASH','erp_quality_loss_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_quality_loss_type' AND dict_value='REWASH');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'降级','DOWNGRADE','erp_quality_loss_type','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_quality_loss_type' AND dict_value='DOWNGRADE');

SELECT 'P4-3: 质量损失成本表 + 字典 创建完成' AS result;
