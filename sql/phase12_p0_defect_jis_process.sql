SET NAMES utf8mb4;

-- ============================================================
-- phase12_p0_defect_jis_process.sql
-- P0 补充：缺陷字典库 + JIS合规字段 + 工序放行标准字段
-- 执行前提：phase3_outsource_defect.sql 已执行
-- ============================================================

-- ============================================================
-- 1. t_erp_produce_defect 缺陷表扩展字段
-- ============================================================
CALL sp_erp_add_column('t_erp_produce_defect', 'defect_category', 'VARCHAR(20)  NULL COMMENT ''缺陷大类：WEAVE=织造疵 DYE=染色疵 SPLICE=拼接疵 SEW=缝制疵 NEEDLE=残断针 PACK=包装疵'' AFTER is_outsource');
CALL sp_erp_add_column('t_erp_produce_defect', 'defect_level', 'VARCHAR(20)  NULL COMMENT ''缺陷等级：CRITICAL=致命 MAJOR=严重 MINOR=轻微'' AFTER defect_category');
CALL sp_erp_add_column('t_erp_produce_defect', 'handle_type', 'VARCHAR(20)  NULL COMMENT ''处理方式：REPAIR=返工 DOWNGRADE=降级 SCRAP=报废'' AFTER defect_level');
CALL sp_erp_add_column('t_erp_produce_defect', 'handle_result', 'VARCHAR(500) NULL COMMENT ''处理结果描述'' AFTER handle_type');
CALL sp_erp_add_column('t_erp_produce_defect', 'responsibility', 'VARCHAR(20)  NULL COMMENT ''责任归属：SELF=本厂 OUTSOURCE=外协 MATERIAL=原料'' AFTER handle_result');
CALL sp_erp_add_column('t_erp_produce_defect', 'is_broken_needle', 'CHAR(1)      NOT NULL DEFAULT ''0'' COMMENT ''是否残断针 0否 1是（对日零容忍红线）'' AFTER responsibility');
CALL sp_erp_add_column('t_erp_produce_defect', 'needle_confirm_by', 'VARCHAR(64)  NULL COMMENT ''残断针处理确认人'' AFTER is_broken_needle');
CALL sp_erp_add_column('t_erp_produce_defect', 'needle_confirm_time', 'DATETIME    NULL COMMENT ''残断针处理确认时间'' AFTER needle_confirm_by');

-- 残断针未确认索引（快速查找未处理的残断针记录）
CALL sp_erp_add_index('t_erp_produce_defect', 'idx_broken_needle_unconfirmed', '(is_broken_needle, needle_confirm_time) COMMENT ''残断针未确认快查索引''');

-- ============================================================
-- 2. t_erp_sales_order JIS 对日合规字段
-- ============================================================
CALL sp_erp_add_column('t_erp_sales_order', 'japan_order_no', 'VARCHAR(64)  NULL COMMENT ''日方订单号（日本客户管理编号）'' AFTER production_exceed');
CALL sp_erp_add_column('t_erp_sales_order', 'jis_label_status', 'CHAR(1)      NOT NULL DEFAULT ''0'' COMMENT ''JIS标签合规状态 0未确认 1合规 2不合规'' AFTER japan_order_no');
CALL sp_erp_add_column('t_erp_sales_order', 'hazard_test_status', 'CHAR(1)      NOT NULL DEFAULT ''0'' COMMENT ''有害物质检测状态 0未检测 1通过 2不通过'' AFTER jis_label_status');
CALL sp_erp_add_column('t_erp_sales_order', 'hazard_report_no', 'VARCHAR(64)  NULL COMMENT ''有害物质检测报告编号'' AFTER hazard_test_status');
CALL sp_erp_add_column('t_erp_sales_order', 'color_fastness_grade', 'VARCHAR(10)  NULL COMMENT ''色牢度等级（JIS标准，4级以上合格）'' AFTER hazard_report_no');
CALL sp_erp_add_column('t_erp_sales_order', 'audit_factory_status', 'CHAR(1)      NOT NULL DEFAULT ''0'' COMMENT ''验厂审计状态 0未审计 1通过 2整改中 3不通过'' AFTER color_fastness_grade');
CALL sp_erp_add_column('t_erp_sales_order', 'audit_factory_date', 'DATE         NULL COMMENT ''验厂日期'' AFTER audit_factory_status');
CALL sp_erp_add_column('t_erp_sales_order', 'export_declare_type', 'VARCHAR(20)  NULL COMMENT ''出口申报类型（一般贸易/加工贸易）'' AFTER audit_factory_date');
CALL sp_erp_add_column('t_erp_sales_order', 'trade_terms', 'VARCHAR(20)  NULL COMMENT ''贸易条款（FOB/CIF/EXW等）'' AFTER export_declare_type');

-- ============================================================
-- 3. t_erp_produce_job_process 工序放行标准字段
-- ============================================================
CALL sp_erp_add_column('t_erp_produce_job_process', 'process_status', 'VARCHAR(20) NOT NULL DEFAULT ''PENDING'' COMMENT ''流转状态：PENDING=待开工 RUNNING=进行中 WAIT_CHECK=完工待检 PASS=检验通过 FAIL=检验不合格 OUTSOURCE=外发中'' AFTER outsource_id');
CALL sp_erp_add_column('t_erp_produce_job_process', 'release_by', 'VARCHAR(64) NULL COMMENT ''放行人（质检通过后签字）'' AFTER process_status');
CALL sp_erp_add_column('t_erp_produce_job_process', 'release_time', 'DATETIME    NULL COMMENT ''放行时间'' AFTER release_by');
CALL sp_erp_add_column('t_erp_produce_job_process', 'fail_handle_type', 'VARCHAR(20) NULL COMMENT ''不合格处理方式：REPAIR=返工 DOWNGRADE=降级 SCRAP=报废'' AFTER release_time');
CALL sp_erp_add_column('t_erp_produce_job_process', 'loss_qty', 'INT         NOT NULL DEFAULT 0 COMMENT ''本工序损耗数量'' AFTER fail_handle_type');
CALL sp_erp_add_column('t_erp_produce_job_process', 'loss_exceed', 'CHAR(1)     NOT NULL DEFAULT ''0'' COMMENT ''损耗是否超标 0否 1是'' AFTER loss_qty');

-- ============================================================
-- 4. 缺陷字典数据（sys_dict_data）
-- ============================================================

-- 缺陷大类字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('缺陷大类', 'erp_defect_category', '0', 'admin', NOW(), '针织服装缺陷大类分类');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1,  '织造疵', 'WEAVE',  'erp_defect_category', '', 'warning', 'N', '0', 'admin', NOW(), '横机/大圆机织造阶段缺陷'),
(2,  '染色疵', 'DYE',    'erp_defect_category', '', 'warning', 'N', '0', 'admin', NOW(), '染整阶段色差/色牢度缺陷'),
(3,  '拼接疵', 'SPLICE', 'erp_defect_category', '', 'danger',  'N', '0', 'admin', NOW(), '异面料拼接工序缺陷'),
(4,  '缝制疵', 'SEW',    'erp_defect_category', '', 'warning', 'N', '0', 'admin', NOW(), '缝制/锁扣/钉扣阶段缺陷'),
(5,  '残断针', 'NEEDLE', 'erp_defect_category', '', 'danger',  'N', '0', 'admin', NOW(), '对日零容忍红线，必须100%处理'),
(6,  '包装疵', 'PACK',   'erp_defect_category', '', 'info',    'N', '0', 'admin', NOW(), '挂牌/包装/装箱阶段缺陷');

-- 缺陷等级字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('缺陷等级', 'erp_defect_level', '0', 'admin', NOW(), '缺陷严重程度分级');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '致命', 'CRITICAL', 'erp_defect_level', '', 'danger',  'N', '0', 'admin', NOW(), '直接导致退货/客诉，必须报废或返工'),
(2, '严重', 'MAJOR',    'erp_defect_level', '', 'warning', 'N', '0', 'admin', NOW(), '影响功能或外观，需返工处理'),
(3, '轻微', 'MINOR',    'erp_defect_level', '', 'info',    'N', '0', 'admin', NOW(), '不影响功能，可降级处理');

-- 工序流转状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('工序流转状态', 'erp_process_status', '0', 'admin', NOW(), '工序流转状态枚举');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '待开工',   'PENDING',    'erp_process_status', '', 'info',    'N', '0', 'admin', NOW(), ''),
(2, '进行中',   'RUNNING',    'erp_process_status', '', 'primary', 'N', '0', 'admin', NOW(), ''),
(3, '完工待检', 'WAIT_CHECK', 'erp_process_status', '', 'warning', 'N', '0', 'admin', NOW(), ''),
(4, '检验通过', 'PASS',       'erp_process_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(5, '检验不合格','FAIL',      'erp_process_status', '', 'danger',  'N', '0', 'admin', NOW(), ''),
(6, '外发中',   'OUTSOURCE',  'erp_process_status', '', 'warning', 'N', '0', 'admin', NOW(), '');

-- JIS标签合规状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('JIS标签状态', 'erp_jis_label_status', '0', 'admin', NOW(), 'JIS对日合规标签状态');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark) VALUES
(1, '未确认', '0', 'erp_jis_label_status', '', 'info',    'N', '0', 'admin', NOW(), ''),
(2, '合规',   '1', 'erp_jis_label_status', '', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '不合规', '2', 'erp_jis_label_status', '', 'danger',  'N', '0', 'admin', NOW(), '');
