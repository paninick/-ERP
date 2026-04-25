-- phase25_process_light_inspection_hotfix.sql
-- Purpose:
-- 1. Add LIGHT_INSPECTION / 照灯/灯检 as formal process master data.
-- 2. Fix job-process initialization procedure to write process_seq instead of the invalid sort_order column.
-- 3. Keep this file additive and safe for existing environments.

SET NAMES utf8mb4;

-- Dictionaries for route customization. INSERT IGNORE keeps reruns safe.
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES
  ('工艺路线工序模式', 'erp_route_item_required_mode', '0', 'admin', NOW(), '路线明细是否必选、可选或按条件启用'),
  ('工艺路线条件编码', 'erp_route_condition_code', '0', 'admin', NOW(), '路线明细条件工序触发条件'),
  ('ERP产品类型', 'erp_product_family', '0', 'admin', NOW(), '毛衫、拼接款、普通针织衫等产品类型'),
  ('ERP工序类型', 'erp_process_type', '0', 'admin', NOW(), '本厂、外协、质检、后整质检等工序类型');

INSERT IGNORE INTO sys_dict_data
  (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES
  (1, '必选', 'REQUIRED', 'erp_route_item_required_mode', '', 'primary', 'Y', '0', 'admin', NOW(), '标准必经工序'),
  (2, '可选', 'OPTIONAL', 'erp_route_item_required_mode', '', 'info', 'N', '0', 'admin', NOW(), '路线模板可选工序'),
  (3, '条件', 'CONDITIONAL', 'erp_route_item_required_mode', '', 'warning', 'N', '0', 'admin', NOW(), '满足条件时启用的工序'),
  (1, '有印花', 'HAS_PRINT', 'erp_route_condition_code', '', 'info', 'N', '0', 'admin', NOW(), '款式包含印花工艺'),
  (2, '有绣花', 'HAS_EMBROIDERY', 'erp_route_condition_code', '', 'info', 'N', '0', 'admin', NOW(), '款式包含绣花工艺'),
  (3, '日单', 'JAPAN_ORDER', 'erp_route_condition_code', '', 'warning', 'N', '0', 'admin', NOW(), '日本客户订单'),
  (4, '需照灯/灯检', 'NEED_LIGHT_INSPECTION', 'erp_route_condition_code', '', 'warning', 'N', '0', 'admin', NOW(), '需加入照灯或灯检节点'),
  (5, '检品公司', 'THIRD_PARTY_INSPECTION', 'erp_route_condition_code', '', 'danger', 'N', '0', 'admin', NOW(), '需第三方检品公司检验'),
  (1, '毛衫', 'SWEATER', 'erp_product_family', '', 'primary', 'Y', '0', 'admin', NOW(), '毛衫/针织毛衫'),
  (2, '拼接款', 'SPLICE', 'erp_product_family', '', 'warning', 'N', '0', 'admin', NOW(), '针织拼接或异材质拼接款'),
  (3, '普通针织衫', 'KNIT_TOP', 'erp_product_family', '', 'info', 'N', '0', 'admin', NOW(), '普通针织上衣'),
  (4, '其他', 'OTHER', 'erp_product_family', '', 'default', 'N', '0', 'admin', NOW(), '其他产品类型'),
  (1, '本厂工序', '0', 'erp_process_type', '', 'primary', 'Y', '0', 'admin', NOW(), '本厂内部生产工序'),
  (2, '外协工序', '1', 'erp_process_type', '', 'warning', 'N', '0', 'admin', NOW(), '外发协作工序');

-- Current production schema keeps process_type as char(1): 0=internal, 1=outsource.
-- Quality responsibility is represented by need_quality_check, not a third process_type value.
DELETE FROM sys_dict_data
WHERE dict_type = 'erp_process_type'
  AND dict_value IN ('QUALITY', 'FINISHING_QC');

-- sys_dict_data has no unique key on dict_type + dict_value in the current RuoYi schema.
-- Repeated hotfix runs must therefore remove duplicates explicitly.
DELETE d
FROM sys_dict_data d
JOIN sys_dict_data keep
  ON keep.dict_type = d.dict_type
 AND keep.dict_value = d.dict_value
 AND keep.dict_code < d.dict_code
WHERE d.dict_type IN (
  'erp_route_item_required_mode',
  'erp_route_condition_code',
  'erp_product_family',
  'erp_process_type'
);

DROP PROCEDURE IF EXISTS sp_erp_add_column;
DELIMITER $$
CREATE PROCEDURE sp_erp_add_column(
  IN p_table_name VARCHAR(128),
  IN p_column_name VARCHAR(128),
  IN p_column_def TEXT
)
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_schema = DATABASE()
      AND table_name = p_table_name
      AND column_name = p_column_name
  ) THEN
    SET @ddl = CONCAT('ALTER TABLE `', p_table_name, '` ADD COLUMN `', p_column_name, '` ', p_column_def);
    PREPARE stmt FROM @ddl;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END$$
DELIMITER ;

-- Ensure current process master schema supports both historical naming variants.
CALL sp_erp_add_column('t_erp_process_def', 'process_no', 'VARCHAR(50) NULL COMMENT ''工序编码'' AFTER id');
CALL sp_erp_add_column('t_erp_process_def', 'process_code', 'VARCHAR(50) NULL COMMENT ''工序编码'' AFTER process_no');
CALL sp_erp_add_column('t_erp_process_def', 'process_type', 'VARCHAR(20) NULL COMMENT ''工序类型'' AFTER process_name');
CALL sp_erp_add_column('t_erp_process_def', 'unit_price', 'DECIMAL(10,4) NULL COMMENT ''默认工价'' AFTER process_type');
CALL sp_erp_add_column('t_erp_process_def', 'default_price', 'DECIMAL(10,2) NULL COMMENT ''默认工价'' AFTER unit_price');
CALL sp_erp_add_column('t_erp_process_def', 'department', 'VARCHAR(50) NULL COMMENT ''所属部门'' AFTER default_price');
CALL sp_erp_add_column('t_erp_process_def', 'enable_outsource', 'TINYINT(1) DEFAULT 0 COMMENT ''是否支持外协'' AFTER department');
CALL sp_erp_add_column('t_erp_process_def', 'need_quality_check', 'TINYINT(1) DEFAULT 0 COMMENT ''是否需要质检'' AFTER enable_outsource');
CALL sp_erp_add_column('t_erp_process_def', 'sort_order', 'INT DEFAULT 0 COMMENT ''排序'' AFTER need_quality_check');
CALL sp_erp_add_column('t_erp_process_def', 'status', 'CHAR(1) DEFAULT ''0'' COMMENT ''状态'' AFTER sort_order');
CALL sp_erp_add_column('t_erp_process_def', 'remark', 'VARCHAR(500) NULL COMMENT ''备注'' AFTER status');

-- Route item extension for optional / conditional process steps.
CALL sp_erp_add_column('t_erp_process_route_item', 'required_mode', 'VARCHAR(20) NOT NULL DEFAULT ''REQUIRED'' COMMENT ''REQUIRED/OPTIONAL/CONDITIONAL'' AFTER standard_cycle_hours');
CALL sp_erp_add_column('t_erp_process_route_item', 'condition_code', 'VARCHAR(50) NULL COMMENT ''条件编码，如 HAS_PRINT/HAS_EMBROIDERY/JAPAN_ORDER/NEED_LIGHT_INSPECTION/THIRD_PARTY_INSPECTION'' AFTER required_mode');
CALL sp_erp_add_column('t_erp_process_route_item', 'qc_required', 'TINYINT(1) NOT NULL DEFAULT 0 COMMENT ''是否需要质检'' AFTER condition_code');
CALL sp_erp_add_column('t_erp_process_route_item', 'needle_check_required', 'TINYINT(1) NOT NULL DEFAULT 0 COMMENT ''是否需要检针'' AFTER qc_required');
CALL sp_erp_add_column('t_erp_process_route_item', 'loss_tracked', 'TINYINT(1) NOT NULL DEFAULT 0 COMMENT ''是否记录损耗'' AFTER needle_check_required');
CALL sp_erp_add_column('t_erp_process_route_item', 'piece_wage_applicable', 'TINYINT(1) NOT NULL DEFAULT 1 COMMENT ''是否适用计件'' AFTER loss_tracked');

-- Job process snapshot extension for inserted / skipped / rework traceability.
CALL sp_erp_add_column('t_erp_produce_job_process', 'source_route_item_id', 'BIGINT NULL COMMENT ''来源工艺路线明细ID'' AFTER loss_exceed');
CALL sp_erp_add_column('t_erp_produce_job_process', 'is_inserted', 'CHAR(1) NOT NULL DEFAULT ''0'' COMMENT ''是否临时插入工序 0否 1是'' AFTER source_route_item_id');
CALL sp_erp_add_column('t_erp_produce_job_process', 'insert_reason', 'VARCHAR(500) NULL COMMENT ''临时插入原因'' AFTER is_inserted');
CALL sp_erp_add_column('t_erp_produce_job_process', 'is_skipped', 'CHAR(1) NOT NULL DEFAULT ''0'' COMMENT ''是否跳过工序 0否 1是'' AFTER insert_reason');
CALL sp_erp_add_column('t_erp_produce_job_process', 'skip_reason', 'VARCHAR(500) NULL COMMENT ''跳过原因'' AFTER is_skipped');
CALL sp_erp_add_column('t_erp_produce_job_process', 'is_rework', 'CHAR(1) NOT NULL DEFAULT ''0'' COMMENT ''是否返修工序 0否 1是'' AFTER skip_reason');
CALL sp_erp_add_column('t_erp_produce_job_process', 'rework_source_process_id', 'BIGINT NULL COMMENT ''返修来源工序记录ID'' AFTER is_rework');
CALL sp_erp_add_column('t_erp_produce_job_process', 'qc_required', 'CHAR(1) NOT NULL DEFAULT ''0'' COMMENT ''是否需要质检'' AFTER rework_source_process_id');
CALL sp_erp_add_column('t_erp_produce_job_process', 'needle_check_required', 'CHAR(1) NOT NULL DEFAULT ''0'' COMMENT ''是否需要检针'' AFTER qc_required');
CALL sp_erp_add_column('t_erp_produce_job_process', 'loss_tracked', 'CHAR(1) NOT NULL DEFAULT ''0'' COMMENT ''是否记录损耗'' AFTER needle_check_required');
CALL sp_erp_add_column('t_erp_produce_job_process', 'piece_wage_applicable', 'CHAR(1) NOT NULL DEFAULT ''1'' COMMENT ''是否适用计件'' AFTER loss_tracked');

UPDATE t_erp_process_def
SET process_no = process_code
WHERE process_no IS NULL
  AND process_code IS NOT NULL;

UPDATE t_erp_process_def
SET unit_price = default_price
WHERE unit_price IS NULL
  AND default_price IS NOT NULL;

-- Add formal light inspection process. Keep both naming variants aligned for mixed historical schemas.
INSERT INTO t_erp_process_def
  (process_no, process_name, process_type, unit_price, sort_order, status, create_by, create_time, remark)
SELECT
  'LIGHT_INSPECTION',
  '照灯/灯检',
  '0',
  0.3000,
  150,
  '0',
  'admin',
  NOW(),
  '正式质检工序：用于发现洞眼、污渍、色差、薄档、漏针、套口/缝制后瑕疵；日单和高品质订单建议纳入路线。'
WHERE NOT EXISTS (
  SELECT 1 FROM t_erp_process_def
  WHERE process_no = 'LIGHT_INSPECTION'
     OR process_code = 'LIGHT_INSPECTION'
);

UPDATE t_erp_process_def
SET process_code = 'LIGHT_INSPECTION'
WHERE process_no = 'LIGHT_INSPECTION'
  AND process_code IS NULL;

UPDATE t_erp_process_def
SET need_quality_check = 1,
    enable_outsource = COALESCE(enable_outsource, 0),
    department = COALESCE(department, '质检/后整')
WHERE process_no = 'LIGHT_INSPECTION'
   OR process_code = 'LIGHT_INSPECTION';

-- Process-route initialization procedure. This version matches t_erp_produce_job_process.process_seq.
DROP PROCEDURE IF EXISTS sp_erp_init_job_processes;
DELIMITER $$
CREATE PROCEDURE sp_erp_init_job_processes(
  IN p_job_id BIGINT,
  IN p_route_id BIGINT
)
BEGIN
  DELETE FROM t_erp_produce_job_process
  WHERE job_id = p_job_id;

  INSERT INTO t_erp_produce_job_process
    (job_id, process_id, process_seq, is_outsource, process_status, in_qty, out_qty, defect_qty,
     loss_qty, loss_exceed, source_route_item_id, is_inserted, is_skipped, is_rework,
     qc_required, needle_check_required, loss_tracked, piece_wage_applicable, create_by, create_time)
  SELECT
    p_job_id,
    ri.process_id,
    ri.sort_order,
    CASE WHEN ri.is_outsource = 1 THEN '1' ELSE '0' END,
    'PENDING',
    0,
    0,
    0,
    0,
    '0',
    ri.id,
    '0',
    '0',
    '0',
    CASE WHEN ri.qc_required = 1 THEN '1' ELSE '0' END,
    CASE WHEN ri.needle_check_required = 1 THEN '1' ELSE '0' END,
    CASE WHEN ri.loss_tracked = 1 THEN '1' ELSE '0' END,
    CASE WHEN ri.piece_wage_applicable = 0 THEN '0' ELSE '1' END,
    'system',
    NOW()
  FROM t_erp_process_route_item ri
  WHERE ri.route_id = p_route_id
  ORDER BY ri.sort_order;
END$$
DELIMITER ;

SELECT id, process_no, process_code, process_name, process_type, need_quality_check, sort_order, status
FROM t_erp_process_def
WHERE process_no = 'LIGHT_INSPECTION'
   OR process_code = 'LIGHT_INSPECTION';
