-- Wave 4: 成本闭环字段补齐
-- 幂等，可重复执行

-- W4-1: 入库单关联生产完工
SET @c1 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_stock_in' AND COLUMN_NAME='produce_job_id');
SET @s1 = IF(@c1=0,'ALTER TABLE t_erp_stock_in ADD COLUMN produce_job_id BIGINT DEFAULT NULL COMMENT \'关联生产单ID\'','SELECT 1');
PREPARE p FROM @s1; EXECUTE p; DEALLOCATE PREPARE p;

SET @c2 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_stock_in' AND COLUMN_NAME='produce_plan_id');
SET @s2 = IF(@c2=0,'ALTER TABLE t_erp_stock_in ADD COLUMN produce_plan_id BIGINT DEFAULT NULL COMMENT \'关联生产计划ID\'','SELECT 1');
PREPARE p FROM @s2; EXECUTE p; DEALLOCATE PREPARE p;

SET @c3 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_stock_in' AND COLUMN_NAME='finish_qty');
SET @s3 = IF(@c3=0,'ALTER TABLE t_erp_stock_in ADD COLUMN finish_qty DECIMAL(18,3) DEFAULT NULL COMMENT \'完工数量\'','SELECT 1');
PREPARE p FROM @s3; EXECUTE p; DEALLOCATE PREPARE p;

SET @c4 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_stock_in' AND COLUMN_NAME='cost_amount');
SET @s4 = IF(@c4=0,'ALTER TABLE t_erp_stock_in ADD COLUMN cost_amount DECIMAL(18,4) DEFAULT NULL COMMENT \'入库成本金额\'','SELECT 1');
PREPARE p FROM @s4; EXECUTE p; DEALLOCATE PREPARE p;

-- W4-2: 物料消耗事件化绑定
SET @c5 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='report_log_id');
SET @s5 = IF(@c5=0,'ALTER TABLE t_erp_produce_material_consume ADD COLUMN report_log_id BIGINT DEFAULT NULL COMMENT \'关联报工事件ID\'','SELECT 1');
PREPARE p FROM @s5; EXECUTE p; DEALLOCATE PREPARE p;

SET @c6 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='stock_out_id');
SET @s6 = IF(@c6=0,'ALTER TABLE t_erp_produce_material_consume ADD COLUMN stock_out_id BIGINT DEFAULT NULL COMMENT \'关联出库单ID\'','SELECT 1');
PREPARE p FROM @s6; EXECUTE p; DEALLOCATE PREPARE p;

SET @c7 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='unit_cost');
SET @s7 = IF(@c7=0,'ALTER TABLE t_erp_produce_material_consume ADD COLUMN unit_cost DECIMAL(18,6) DEFAULT NULL COMMENT \'单位成本\'','SELECT 1');
PREPARE p FROM @s7; EXECUTE p; DEALLOCATE PREPARE p;

SET @c8 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='cost_amount');
SET @s8 = IF(@c8=0,'ALTER TABLE t_erp_produce_material_consume ADD COLUMN cost_amount DECIMAL(18,4) DEFAULT NULL COMMENT \'消耗成本金额\'','SELECT 1');
PREPARE p FROM @s8; EXECUTE p; DEALLOCATE PREPARE p;

SET @c9 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND COLUMN_NAME='event_source');
SET @s9 = IF(@c9=0,'ALTER TABLE t_erp_produce_material_consume ADD COLUMN event_source VARCHAR(20) DEFAULT \'MANUAL\' COMMENT \'来源 MANUAL/REPORT_LOG/STOCK_OUT\'','SELECT 1');
PREPARE p FROM @s9; EXECUTE p; DEALLOCATE PREPARE p;

-- W4-3: 外协结算事件化
SET @c10 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='settle_status');
SET @s10 = IF(@c10=0,'ALTER TABLE t_erp_outsource_order ADD COLUMN settle_status VARCHAR(20) DEFAULT \'UNSETTLED\' COMMENT \'结算状态 UNSETTLED/PARTIAL/SETTLED\'','SELECT 1');
PREPARE p FROM @s10; EXECUTE p; DEALLOCATE PREPARE p;

SET @c11 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='settle_amount');
SET @s11 = IF(@c11=0,'ALTER TABLE t_erp_outsource_order ADD COLUMN settle_amount DECIMAL(18,4) DEFAULT NULL COMMENT \'已结算金额\'','SELECT 1');
PREPARE p FROM @s11; EXECUTE p; DEALLOCATE PREPARE p;

SET @c12 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='settle_time');
SET @s12 = IF(@c12=0,'ALTER TABLE t_erp_outsource_order ADD COLUMN settle_time DATETIME DEFAULT NULL COMMENT \'结算时间\'','SELECT 1');
PREPARE p FROM @s12; EXECUTE p; DEALLOCATE PREPARE p;

SET @c13 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND COLUMN_NAME='cost_amount');
SET @s13 = IF(@c13=0,'ALTER TABLE t_erp_outsource_order ADD COLUMN cost_amount DECIMAL(18,4) DEFAULT NULL COMMENT \'外协成本金额（含运费）\'','SELECT 1');
PREPARE p FROM @s13; EXECUTE p; DEALLOCATE PREPARE p;

-- W4-4: 计件工资治理
SET @c14 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND COLUMN_NAME='settle_status');
SET @s14 = IF(@c14=0,'ALTER TABLE t_erp_piece_wage ADD COLUMN settle_status VARCHAR(20) DEFAULT \'DRAFT\' COMMENT \'结算状态 DRAFT/CONFIRMED/PAID\'','SELECT 1');
PREPARE p FROM @s14; EXECUTE p; DEALLOCATE PREPARE p;

SET @c15 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND COLUMN_NAME='report_log_id');
SET @s15 = IF(@c15=0,'ALTER TABLE t_erp_piece_wage ADD COLUMN report_log_id BIGINT DEFAULT NULL COMMENT \'关联报工事件ID\'','SELECT 1');
PREPARE p FROM @s15; EXECUTE p; DEALLOCATE PREPARE p;

SET @c16 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND COLUMN_NAME='cost_amount');
SET @s16 = IF(@c16=0,'ALTER TABLE t_erp_piece_wage ADD COLUMN cost_amount DECIMAL(18,4) DEFAULT NULL COMMENT \'工资成本金额\'','SELECT 1');
PREPARE p FROM @s16; EXECUTE p; DEALLOCATE PREPARE p;

SET @c17 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND COLUMN_NAME='confirm_by');
SET @s17 = IF(@c17=0,'ALTER TABLE t_erp_piece_wage ADD COLUMN confirm_by VARCHAR(50) DEFAULT NULL COMMENT \'确认人\'','SELECT 1');
PREPARE p FROM @s17; EXECUTE p; DEALLOCATE PREPARE p;

SET @c18 = (SELECT COUNT(*) FROM information_schema.COLUMNS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND COLUMN_NAME='confirm_time');
SET @s18 = IF(@c18=0,'ALTER TABLE t_erp_piece_wage ADD COLUMN confirm_time DATETIME DEFAULT NULL COMMENT \'确认时间\'','SELECT 1');
PREPARE p FROM @s18; EXECUTE p; DEALLOCATE PREPARE p;

-- 结算状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '结算状态', 'erp_settle_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_settle_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'未结算','UNSETTLED','erp_settle_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_settle_status' AND dict_value='UNSETTLED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'部分结算','PARTIAL','erp_settle_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_settle_status' AND dict_value='PARTIAL');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'已结算','SETTLED','erp_settle_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_settle_status' AND dict_value='SETTLED');

-- 工资结算状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '工资结算状态', 'erp_wage_settle_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_wage_settle_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'草稿','DRAFT','erp_wage_settle_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_wage_settle_status' AND dict_value='DRAFT');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'已确认','CONFIRMED','erp_wage_settle_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_wage_settle_status' AND dict_value='CONFIRMED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'已发放','PAID','erp_wage_settle_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_wage_settle_status' AND dict_value='PAID');

-- 新外键列索引（幂等，PREPARE STATEMENT 模式）
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_stock_in' AND INDEX_NAME='idx_stock_in_job')=0, 'CREATE INDEX idx_stock_in_job ON t_erp_stock_in (produce_job_id)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_stock_in' AND INDEX_NAME='idx_stock_in_plan')=0, 'CREATE INDEX idx_stock_in_plan ON t_erp_stock_in (produce_plan_id)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND INDEX_NAME='idx_mat_consume_report')=0, 'CREATE INDEX idx_mat_consume_report ON t_erp_produce_material_consume (report_log_id)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_produce_material_consume' AND INDEX_NAME='idx_mat_consume_stockout')=0, 'CREATE INDEX idx_mat_consume_stockout ON t_erp_produce_material_consume (stock_out_id)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_outsource_order' AND INDEX_NAME='idx_outsource_settle')=0, 'CREATE INDEX idx_outsource_settle ON t_erp_outsource_order (settle_status)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND INDEX_NAME='idx_piece_wage_settle')=0, 'CREATE INDEX idx_piece_wage_settle ON t_erp_piece_wage (settle_status)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;
SET @s = IF((SELECT COUNT(*) FROM information_schema.STATISTICS WHERE TABLE_SCHEMA=DATABASE() AND TABLE_NAME='t_erp_piece_wage' AND INDEX_NAME='idx_piece_wage_report')=0, 'CREATE INDEX idx_piece_wage_report ON t_erp_piece_wage (report_log_id)', 'SELECT 1'); PREPARE p FROM @s; EXECUTE p; DEALLOCATE PREPARE p;

SELECT 'cost_closure_extend.sql 执行完成' AS result;
