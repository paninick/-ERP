-- =============================================
-- ERP运维管理面板 - 数据库初始化脚本
-- 版本: v1.0
-- 创建日期: 2026-04-02
-- =============================================

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- =============================================
-- 1. 服务表 (ops_service)
-- =============================================
DROP TABLE IF EXISTS `ops_service`;
CREATE TABLE `ops_service` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(100) NOT NULL COMMENT '服务名称',
    `type` VARCHAR(50) NOT NULL COMMENT '服务类型: backend/frontend/database/cache/other',
    `status` VARCHAR(20) NOT NULL DEFAULT 'unknown' COMMENT '状态: unknown/healthy/warning/error',
    `health_check_url` VARCHAR(500) DEFAULT NULL COMMENT '健康检查URL',
    `host` VARCHAR(100) DEFAULT NULL COMMENT '主机地址',
    `port` INT DEFAULT NULL COMMENT '端口号',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '服务描述',
    `created_by` VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(100) DEFAULT NULL COMMENT '更新人',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_type` (`type`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服务表';

-- 插入示例数据
INSERT INTO `ops_service` (`name`, `type`, `status`, `health_check_url`, `host`, `port`, `description`, `created_by`) VALUES
('后端服务', 'backend', 'healthy', 'http://localhost:8080/health', 'localhost', 8080, 'Spring Boot后端应用', 'system'),
('前端服务', 'frontend', 'healthy', 'http://localhost:80', 'localhost', 80, 'Vue前端应用', 'system'),
('MySQL数据库', 'database', 'healthy', NULL, 'localhost', 3306, 'MySQL 8.0数据库', 'system'),
('Redis缓存', 'cache', 'healthy', NULL, 'localhost', 6379, 'Redis 6.x缓存', 'system');

-- =============================================
-- 2. 备份记录表 (ops_backup_record)
-- =============================================
DROP TABLE IF EXISTS `ops_backup_record`;
CREATE TABLE `ops_backup_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `service_id` BIGINT DEFAULT NULL COMMENT '关联服务ID',
    `type` VARCHAR(50) NOT NULL COMMENT '备份类型: manual/scheduled',
    `scope` JSON DEFAULT NULL COMMENT '备份范围: {system_config: true, application_data: true, database: true, log_files: true}',
    `status` VARCHAR(20) NOT NULL COMMENT '状态: pending/running/success/failed',
    `file_path` VARCHAR(500) DEFAULT NULL COMMENT '文件路径',
    `file_name` VARCHAR(200) DEFAULT NULL COMMENT '文件名',
    `file_size` BIGINT DEFAULT NULL COMMENT '文件大小(字节)',
    `checksum_md5` VARCHAR(32) DEFAULT NULL COMMENT 'MD5校验值',
    `checksum_sha256` VARCHAR(64) DEFAULT NULL COMMENT 'SHA256校验值',
    `started_at` DATETIME DEFAULT NULL COMMENT '开始时间',
    `completed_at` DATETIME DEFAULT NULL COMMENT '完成时间',
    `error_message` TEXT DEFAULT NULL COMMENT '错误信息',
    `created_by` VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    INDEX `idx_service_id` (`service_id`),
    INDEX `idx_status` (`status`),
    INDEX `idx_type` (`type`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='备份记录表';

-- =============================================
-- 3. 定时备份配置表 (ops_scheduled_backup)
-- =============================================
DROP TABLE IF EXISTS `ops_scheduled_backup`;
CREATE TABLE `ops_scheduled_backup` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `service_id` BIGINT DEFAULT NULL COMMENT '关联服务ID',
    `name` VARCHAR(100) NOT NULL COMMENT '配置名称',
    `schedule_type` VARCHAR(20) NOT NULL COMMENT '周期类型: daily/weekly/monthly',
    `schedule_time` TIME NOT NULL COMMENT '备份时间',
    `schedule_day` INT DEFAULT NULL COMMENT '周/月的第几天(1-7/1-31)',
    `retention_policy` VARCHAR(20) NOT NULL COMMENT '保留策略: count/time/permanent',
    `retention_count` INT DEFAULT NULL COMMENT '保留数量',
    `retention_days` INT DEFAULT NULL COMMENT '保留天数',
    `backup_scope` JSON DEFAULT NULL COMMENT '备份范围',
    `custom_path` VARCHAR(500) DEFAULT NULL COMMENT '自定义备份路径',
    `enabled` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否启用',
    `last_run_at` DATETIME DEFAULT NULL COMMENT '上次运行时间',
    `next_run_at` DATETIME DEFAULT NULL COMMENT '下次运行时间',
    `created_by` VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(100) DEFAULT NULL COMMENT '更新人',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`id`),
    INDEX `idx_service_id` (`service_id`),
    INDEX `idx_enabled` (`enabled`),
    INDEX `idx_next_run_at` (`next_run_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时备份配置表';

-- 插入示例定时备份配置
INSERT INTO `ops_scheduled_backup` (`service_id`, `name`, `schedule_type`, `schedule_time`, `retention_policy`, `retention_count`, `backup_scope`, `enabled`, `created_by`) VALUES
(3, '数据库每日备份', 'daily', '02:00:00', 'count', 7, '{"database": true, "system_config": true}', TRUE, 'system'),
(1, '后端应用每周备份', 'weekly', '03:00:00', 'count', 4, '{"application_data": true, "log_files": true}', TRUE, 'system');

-- =============================================
-- 4. 告警历史表 (ops_alert_history)
-- =============================================
DROP TABLE IF EXISTS `ops_alert_history`;
CREATE TABLE `ops_alert_history` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `service_id` BIGINT DEFAULT NULL COMMENT '关联服务ID',
    `backup_record_id` BIGINT DEFAULT NULL COMMENT '关联备份ID',
    `alert_type` VARCHAR(50) NOT NULL COMMENT '告警类型: service/resource/backup',
    `level` VARCHAR(20) NOT NULL COMMENT '告警级别: info/warning/critical',
    `title` VARCHAR(200) NOT NULL COMMENT '告警标题',
    `message` TEXT DEFAULT NULL COMMENT '告警内容',
    `metric_name` VARCHAR(100) DEFAULT NULL COMMENT '指标名称',
    `metric_value` DECIMAL(20,4) DEFAULT NULL COMMENT '指标值',
    `threshold_value` DECIMAL(20,4) DEFAULT NULL COMMENT '阈值',
    `resolved` BOOLEAN NOT NULL DEFAULT FALSE COMMENT '是否已解决',
    `resolved_at` DATETIME DEFAULT NULL COMMENT '解决时间',
    `resolved_by` VARCHAR(100) DEFAULT NULL COMMENT '解决人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_service_id` (`service_id`),
    INDEX `idx_level` (`level`),
    INDEX `idx_resolved` (`resolved`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警历史表';

-- =============================================
-- 5. 操作日志表 (ops_operation_log)
-- =============================================
DROP TABLE IF EXISTS `ops_operation_log`;
CREATE TABLE `ops_operation_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `user_id` BIGINT DEFAULT NULL COMMENT '操作用户ID',
    `username` VARCHAR(100) DEFAULT NULL COMMENT '操作用户名',
    `operation_type` VARCHAR(50) NOT NULL COMMENT '操作类型: service_start/service_stop/service_restart/backup_create/backup_restore/other',
    `target_type` VARCHAR(50) DEFAULT NULL COMMENT '目标类型: service/backup',
    `target_id` VARCHAR(100) DEFAULT NULL COMMENT '目标ID',
    `target_name` VARCHAR(200) DEFAULT NULL COMMENT '目标名称',
    `operation_detail` TEXT DEFAULT NULL COMMENT '操作详情(JSON)',
    `result` VARCHAR(20) NOT NULL COMMENT '操作结果: success/failed',
    `error_message` TEXT DEFAULT NULL COMMENT '错误信息',
    `ip_address` VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    `user_agent` VARCHAR(500) DEFAULT NULL COMMENT 'User Agent',
    `duration` INT DEFAULT NULL COMMENT '执行耗时(毫秒)',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `idx_user_id` (`user_id`),
    INDEX `idx_operation_type` (`operation_type`),
    INDEX `idx_result` (`result`),
    INDEX `idx_created_at` (`created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- =============================================
-- 6. 快捷操作表 (ops_quick_action)
-- =============================================
DROP TABLE IF EXISTS `ops_quick_action`;
CREATE TABLE `ops_quick_action` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(100) NOT NULL COMMENT '操作名称',
    `icon` VARCHAR(100) DEFAULT NULL COMMENT '图标',
    `action_type` VARCHAR(50) NOT NULL COMMENT '操作类型',
    `action_config` JSON DEFAULT NULL COMMENT '操作配置',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    `enabled` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否启用',
    `created_by` VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(100) DEFAULT NULL COMMENT '更新人',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_enabled` (`enabled`),
    INDEX `idx_sort_order` (`sort_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='快捷操作表';

-- 插入默认快捷操作
INSERT INTO `ops_quick_action` (`name`, `icon`, `action_type`, `action_config`, `sort_order`, `enabled`, `created_by`) VALUES
('恢复数据库服务', 'el-icon-refresh-left', 'service_restart', '{"service_id": 3}', 1, TRUE, 'system'),
('重启所有应用服务', 'el-icon-refresh-right', 'batch_restart', '{"service_types": ["backend", "frontend"]}', 2, TRUE, 'system'),
('清理临时文件', 'el-icon-delete', 'clean_temp', '{}', 3, TRUE, 'system'),
('检查系统健康', 'el-icon-thumb', 'health_check', '{}', 4, TRUE, 'system');

-- =============================================
-- 7. 告警规则表 (ops_alert_rule)
-- =============================================
DROP TABLE IF EXISTS `ops_alert_rule`;
CREATE TABLE `ops_alert_rule` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `name` VARCHAR(100) NOT NULL COMMENT '规则名称',
    `metric_type` VARCHAR(50) NOT NULL COMMENT '指标类型',
    `metric_name` VARCHAR(100) NOT NULL COMMENT '指标名称',
    `condition` VARCHAR(20) NOT NULL COMMENT '条件: gt/lt/eq/ne/gte/lte',
    `threshold` DECIMAL(20,4) NOT NULL COMMENT '阈值',
    `level` VARCHAR(20) NOT NULL COMMENT '告警级别: info/warning/critical',
    `enabled` BOOLEAN NOT NULL DEFAULT TRUE COMMENT '是否启用',
    `created_by` VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(100) DEFAULT NULL COMMENT '更新人',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    INDEX `idx_enabled` (`enabled`),
    INDEX `idx_metric_type` (`metric_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='告警规则表';

-- 插入默认告警规则
INSERT INTO `ops_alert_rule` (`name`, `metric_type`, `metric_name`, `condition`, `threshold`, `level`, `enabled`, `created_by`) VALUES
('CPU使用率过高', 'resource', 'cpu_usage', 'gt', 80.0, 'warning', TRUE, 'system'),
('CPU使用率严重过高', 'resource', 'cpu_usage', 'gt', 95.0, 'critical', TRUE, 'system'),
('内存使用率过高', 'resource', 'memory_usage', 'gt', 85.0, 'warning', TRUE, 'system'),
('内存使用率严重过高', 'resource', 'memory_usage', 'gt', 95.0, 'critical', TRUE, 'system'),
('磁盘使用率过高', 'resource', 'disk_usage', 'gt', 85.0, 'warning', TRUE, 'system'),
('磁盘使用率严重过高', 'resource', 'disk_usage', 'gt', 95.0, 'critical', TRUE, 'system');

-- =============================================
-- 8. 系统配置表 (ops_config)
-- =============================================
DROP TABLE IF EXISTS `ops_config`;
CREATE TABLE `ops_config` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `config_key` VARCHAR(100) NOT NULL COMMENT '配置键',
    `config_value` TEXT DEFAULT NULL COMMENT '配置值',
    `config_type` VARCHAR(50) NOT NULL COMMENT '配置类型: string/number/boolean/json',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '配置描述',
    `created_by` VARCHAR(100) DEFAULT NULL COMMENT '创建人',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updated_by` VARCHAR(100) DEFAULT NULL COMMENT '更新人',
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_config_key` (`config_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置表';

-- 插入默认系统配置
INSERT INTO `ops_config` (`config_key`, `config_value`, `config_type`, `description`, `created_by`) VALUES
('monitor.refresh_interval', '10', 'number', '监控刷新间隔(秒)', 'system'),
('monitor.sound_enabled', 'true', 'boolean', '是否启用声音提醒', 'system'),
('backup.default_path', '/data/backups', 'string', '默认备份路径', 'system'),
('backup.max_retention_days', '30', 'number', '备份最大保留天数', 'system'),
('alert.auto_resolve_seconds', '300', 'number', '告警自动解决秒数', 'system');

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- =============================================
-- 初始化完成
-- =============================================
SELECT 'ERP运维管理面板数据库初始化完成!' AS message;
