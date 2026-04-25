-- Material consume execution and cost attribution enhancement
-- Date: 2026-04-25

ALTER TABLE `t_erp_produce_material_consume`
    ADD COLUMN IF NOT EXISTS `job_id` bigint(20) DEFAULT NULL COMMENT 'produce job id' AFTER `produce_plan_id`,
    ADD COLUMN IF NOT EXISTS `job_process_id` bigint(20) DEFAULT NULL COMMENT 'job process snapshot id' AFTER `job_id`,
    ADD COLUMN IF NOT EXISTS `report_log_id` bigint(20) DEFAULT NULL COMMENT 'report log id' AFTER `job_process_id`,
    ADD COLUMN IF NOT EXISTS `batch_no` varchar(64) DEFAULT NULL COMMENT 'batch no' AFTER `report_log_id`,
    ADD COLUMN IF NOT EXISTS `material_type` varchar(32) DEFAULT NULL COMMENT 'MAIN or AUXILIARY' AFTER `material_name`,
    ADD COLUMN IF NOT EXISTS `unit_price` decimal(12,4) DEFAULT NULL COMMENT 'unit price' AFTER `unit`,
    ADD COLUMN IF NOT EXISTS `theoretical_cost` decimal(14,4) DEFAULT NULL COMMENT 'theoretical cost' AFTER `unit_price`,
    ADD COLUMN IF NOT EXISTS `actual_cost` decimal(14,4) DEFAULT NULL COMMENT 'actual cost' AFTER `theoretical_cost`,
    ADD COLUMN IF NOT EXISTS `cost_diff` decimal(14,4) DEFAULT NULL COMMENT 'cost difference' AFTER `actual_cost`;

SET @idx_job_exists := (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 't_erp_produce_material_consume'
      AND index_name = 'idx_erp_pmc_job'
);
SET @idx_job_sql := IF(@idx_job_exists = 0,
    'CREATE INDEX `idx_erp_pmc_job` ON `t_erp_produce_material_consume` (`job_id`)',
    'SELECT 1');
PREPARE stmt FROM @idx_job_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @idx_job_process_exists := (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 't_erp_produce_material_consume'
      AND index_name = 'idx_erp_pmc_job_process'
);
SET @idx_job_process_sql := IF(@idx_job_process_exists = 0,
    'CREATE INDEX `idx_erp_pmc_job_process` ON `t_erp_produce_material_consume` (`job_process_id`)',
    'SELECT 1');
PREPARE stmt FROM @idx_job_process_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET @idx_report_log_exists := (
    SELECT COUNT(1)
    FROM information_schema.statistics
    WHERE table_schema = DATABASE()
      AND table_name = 't_erp_produce_material_consume'
      AND index_name = 'idx_erp_pmc_report_log'
);
SET @idx_report_log_sql := IF(@idx_report_log_exists = 0,
    'CREATE INDEX `idx_erp_pmc_report_log` ON `t_erp_produce_material_consume` (`report_log_id`)',
    'SELECT 1');
PREPARE stmt FROM @idx_report_log_sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;
