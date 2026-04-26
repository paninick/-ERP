-- ============================================================
-- phase32_employee_org_extension.sql
-- 目的：员工档案扩展关联组织层级 + 技能等级/计件工种字典
-- 迁移策略：保留现有 department/station 自由文本字段
-- 幂等性：可安全重跑，不会报错或产生重复数据
-- ============================================================

-- 1. 技能等级字典
-- dict_type 去重（防止 INSERT IGNORE 在无唯一约束时产生重复）
DELETE d1 FROM sys_dict_type d1
JOIN sys_dict_type d2 ON d1.dict_type = d2.dict_type AND d1.dict_id > d2.dict_id
WHERE d1.dict_type IN ('erp_skill_level', 'erp_piece_category');
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time) VALUES ('技能等级', 'erp_skill_level', '0', 'admin', NOW());

-- 字典数据去重（INSERT IGNORE 在无唯一键时不保证幂等，先清理再插入）
DELETE d1 FROM sys_dict_data d1
JOIN sys_dict_data d2 ON d1.dict_type = d2.dict_type AND d1.dict_value = d2.dict_value AND d1.dict_code > d2.dict_code
WHERE d1.dict_type IN ('erp_skill_level', 'erp_piece_category');

INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_skill_level', '学徒', 'TRAINEE', 10, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_skill_level', '初级', 'JUNIOR', 20, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_skill_level', '中级', 'INTERMEDIATE', 30, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_skill_level', '高级', 'SENIOR', 40, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_skill_level', '技师', 'MASTER', 50, '0', 'admin', NOW());

-- 2. 计件工种字典
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time) VALUES ('计件工种', 'erp_piece_category', '0', 'admin', NOW());

INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '缝纫', 'SEWING', 10, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '套口', 'LINKING', 20, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '整烫', 'IRONING', 30, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '检验', 'INSPECTION', 40, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '包装', 'PACKING', 50, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '外协跟单', 'OUTSOURCE_SUPPORT', 60, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '横机', 'KNITTING', 5, '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_piece_category', '水洗', 'WASHING', 7, '0', 'admin', NOW());

-- 3. 员工表扩展字段
-- 逐列独立检查 + PREPARED STATEMENT 逐列补齐，避免：
--   - 整体计数式 ALTER TABLE 在部分列已存在时因重复列报错
--   - DELIMITER/PROCEDURE 执行链路依赖
--   - 索引和列绑定的"全有或全无"问题
-- 每个 ALTER 独立执行，已存在的列/索引被 SELECT 1 跳过

-- 辅助宏：取表名和数据库名（MySQL 8.0 兼容）
SET @db = (SELECT DATABASE());

-- 逐列检查并补齐（8 个独立判断）
SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'org_unit_id');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN org_unit_id BIGINT DEFAULT NULL COMMENT \'所属组织节点ID(t_erp_org_unit)\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'workshop_id');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN workshop_id BIGINT DEFAULT NULL COMMENT \'所属车间ID(t_erp_org_unit)\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'team_id');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN team_id BIGINT DEFAULT NULL COMMENT \'所属班组ID(t_erp_org_unit)\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'station_id');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN station_id BIGINT DEFAULT NULL COMMENT \'所属工位ID(t_erp_org_unit)\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'skill_level');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN skill_level VARCHAR(30) DEFAULT NULL COMMENT \'技能等级(erp_skill_level)\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'piece_category');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN piece_category VARCHAR(30) DEFAULT NULL COMMENT \'计件工种(erp_piece_category)\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'cross_workshop');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN cross_workshop TINYINT(1) DEFAULT 0 COMMENT \'是否可跨车间作业\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @col = (SELECT COUNT(*) FROM information_schema.columns WHERE table_schema = @db AND table_name = 't_erp_employee' AND column_name = 'qualification');
SET @sql = IF(@col = 0, 'ALTER TABLE t_erp_employee ADD COLUMN qualification VARCHAR(200) DEFAULT NULL COMMENT \'上岗资格\' AFTER station', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

-- 逐索引检查并补齐（7 个独立判断）
SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_org_unit_id');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_org_unit_id (org_unit_id) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_workshop_id');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_workshop_id (workshop_id) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_team_id');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_team_id (team_id) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_skill_level');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_skill_level (skill_level) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_piece_category');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_piece_category (piece_category) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_station_id');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_station_id (station_id) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;

SET @idx = (SELECT COUNT(*) FROM information_schema.statistics WHERE table_schema = @db AND table_name = 't_erp_employee' AND index_name = 'idx_cross_workshop');
SET @sql = IF(@idx = 0, 'ALTER TABLE t_erp_employee ADD INDEX idx_cross_workshop (cross_workshop) USING BTREE', 'SELECT 1');
PREPARE stmt FROM @sql; EXECUTE stmt; DEALLOCATE PREPARE stmt;
