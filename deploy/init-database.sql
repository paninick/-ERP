-- ERP生产环境数据库初始化脚本
-- 创建时间: 2026-04-23

-- 创建数据库
CREATE DATABASE IF NOT EXISTS ry_erp_prod CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE ry_erp_prod;

-- 设置SQL模式
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+08:00";

-- 创建ERP生产专用用户
CREATE USER IF NOT EXISTS 'erp_prod_user'@'%' IDENTIFIED BY '${MYSQL_PASSWORD}';
GRANT ALL PRIVILEGES ON ry_erp_prod.* TO 'erp_prod_user'@'%';
FLUSH PRIVILEGES;

-- 创建必要的索引
CREATE INDEX idx_sales_order_status ON erp_sales_order(order_status);
CREATE INDEX idx_produce_plan_date ON erp_produce_plan(plan_date);
CREATE INDEX idx_inventory_stock_product ON erp_inventory_stock(product_id);
CREATE INDEX idx_finance_invoice_date ON erp_finance_invoice(invoice_date);

-- 初始化基础数据
INSERT IGNORE INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES
(100, 'ERP生产环境', 'erp.production.mode', 'true', 'Y', 'admin', NOW(), 'admin', NOW(), 'ERP生产环境标识');

-- 设置系统参数
INSERT IGNORE INTO sys_config (config_id, config_name, config_key, config_value, config_type, create_by, create_time, update_by, update_time, remark) VALUES
(101, '上传文件大小限制(MB)', 'sys.upload.maxSize', '10', 'Y', 'admin', NOW(), 'admin', NOW(), '上传文件大小限制，单位MB');

-- 创建审计日志表
CREATE TABLE IF NOT EXISTS erp_audit_log (
    log_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '用户ID',
    user_name VARCHAR(50) COMMENT '用户名称',
    operation VARCHAR(100) COMMENT '操作内容',
    method VARCHAR(200) COMMENT '方法名',
    params TEXT COMMENT '参数',
    ip VARCHAR(50) COMMENT 'IP地址',
    location VARCHAR(100) COMMENT '操作地点',
    status TINYINT DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    error_msg TEXT COMMENT '错误信息',
    operation_time DATETIME COMMENT '操作时间',
    PRIMARY KEY (log_id),
    INDEX idx_user_id (user_id),
    INDEX idx_operation_time (operation_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ERP审计日志表';

-- 创建数据备份表
CREATE TABLE IF NOT EXISTS erp_data_backup (
    backup_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '备份ID',
    backup_name VARCHAR(100) COMMENT '备份名称',
    backup_type VARCHAR(20) COMMENT '备份类型（full/partial）',
    table_count INT COMMENT '表数量',
    data_size BIGINT COMMENT '数据大小（字节）',
    backup_time DATETIME COMMENT '备份时间',
    backup_path VARCHAR(500) COMMENT '备份路径',
    status VARCHAR(20) COMMENT '状态（success/failed）',
    remark VARCHAR(500) COMMENT '备注',
    create_by VARCHAR(50) COMMENT '创建者',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (backup_id),
    INDEX idx_backup_time (backup_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='ERP数据备份记录表';

SELECT 'ERP生产数据库初始化完成' AS message;
