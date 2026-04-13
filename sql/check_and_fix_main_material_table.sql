-- ============================================================
-- 主料表结构检查与修复脚本
-- 用途：检查t_erp_main_material表结构，确认factory_no字段是否存在
-- ============================================================

-- 1. 查看表结构
DESCRIBE t_erp_main_material;

-- 2. 如果factory_no字段不存在，添加该字段
-- 注意：请先运行上面的DESCRIBE命令确认后再执行下面的语句

-- ALTER TABLE t_erp_main_material 
-- ADD COLUMN factory_no VARCHAR(100) DEFAULT NULL COMMENT '厂供编号' 
-- AFTER supply_method;

-- 3. 验证字段是否添加成功
-- DESCRIBE t_erp_main_material;

-- ============================================================
-- 完整的表结构定义（参考）
-- ============================================================
/*
CREATE TABLE IF NOT EXISTS t_erp_main_material (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
    main_material_type VARCHAR(50) DEFAULT NULL COMMENT '主料类型',
    main_material_no VARCHAR(100) DEFAULT NULL COMMENT '主料编号',
    supply_method VARCHAR(50) DEFAULT NULL COMMENT '供货方式',
    factory_no VARCHAR(100) DEFAULT NULL COMMENT '厂供编号',
    supplier_id BIGINT(20) DEFAULT NULL COMMENT '主料供应商id',
    name VARCHAR(200) DEFAULT NULL COMMENT '主料品名',
    composition VARCHAR(500) DEFAULT NULL COMMENT '主料成分',
    width VARCHAR(100) DEFAULT NULL COMMENT '主料门幅',
    weight VARCHAR(100) DEFAULT NULL COMMENT '主料克重',
    yarn_count VARCHAR(100) DEFAULT NULL COMMENT '主料纱支',
    unit VARCHAR(50) DEFAULT NULL COMMENT '计量单位',
    pictrue_url VARCHAR(500) DEFAULT NULL COMMENT '图片',
    create_by VARCHAR(64) DEFAULT NULL COMMENT '创建者',
    create_time DATETIME DEFAULT NULL COMMENT '创建时间',
    update_by VARCHAR(64) DEFAULT NULL COMMENT '更新者',
    update_time DATETIME DEFAULT NULL COMMENT '更新时间',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COMMENT='主料表';
*/
