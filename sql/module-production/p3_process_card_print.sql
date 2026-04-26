-- P3-3: 工序卡独立打印支持
-- 在工序快照表上补打印追踪字段
-- 幂等，可重复执行

SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job_process' AND COLUMN_NAME='card_printed');
SET @s1 = IF(@c1=0, 'ALTER TABLE t_erp_produce_job_process ADD COLUMN card_printed TINYINT(1) DEFAULT 0 COMMENT ''工序卡是否已打印'' AFTER piece_wage_applicable', 'SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job_process' AND COLUMN_NAME='card_qr_token');
SET @s2 = IF(@c2=0, 'ALTER TABLE t_erp_produce_job_process ADD COLUMN card_qr_token VARCHAR(128) DEFAULT NULL COMMENT ''工序卡二维码令牌'' AFTER card_printed', 'SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @c3 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job_process' AND COLUMN_NAME='card_print_time');
SET @s3 = IF(@c3=0, 'ALTER TABLE t_erp_produce_job_process ADD COLUMN card_print_time DATETIME DEFAULT NULL COMMENT ''打印时间'' AFTER card_qr_token', 'SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @c4 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_job_process' AND COLUMN_NAME='card_printed_by');
SET @s4 = IF(@c4=0, 'ALTER TABLE t_erp_produce_job_process ADD COLUMN card_printed_by VARCHAR(50) DEFAULT NULL COMMENT ''打印人'' AFTER card_print_time', 'SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

SELECT 'P3-3: 工序卡打印字段 (card_printed/qr_token/print_time/printed_by) 完成' AS result;
