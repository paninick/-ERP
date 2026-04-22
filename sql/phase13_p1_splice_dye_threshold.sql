-- phase13: P1 拼接工艺SOP字段 + 染整阈值约束字段
-- 依赖: phase12_p0_defect_jis_process.sql 已执行
-- 执行顺序: 第13步

SET NAMES utf8mb4;

-- ============================================================
-- 0. 建表：t_erp_process_def（如不存在则创建）
-- ============================================================
CREATE TABLE IF NOT EXISTS `t_erp_process_def` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `process_no` varchar(30) DEFAULT NULL COMMENT '工序编号',
  `process_name` varchar(50) NOT NULL COMMENT '工序名称',
  `process_type` char(1) DEFAULT '0' COMMENT '工序类型 0本厂 1外协',
  `standard_time` decimal(10,2) DEFAULT NULL COMMENT '标准工时(分钟)',
  `unit_price` decimal(10,4) DEFAULT NULL COMMENT '计件单价',
  `sort_order` int DEFAULT 0 COMMENT '排序',
  `status` char(1) DEFAULT '0' COMMENT '状态 0正常 1停用',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  `update_by` varchar(64) DEFAULT '',
  `update_time` datetime DEFAULT NULL,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序定义';

-- ============================================================
-- 1. t_erp_process_def 拼接工艺SOP字段
-- ============================================================
CALL sp_erp_add_column('t_erp_process_def', 'is_splice_process', 'CHAR(1)        NOT NULL DEFAULT ''0'' COMMENT ''是否拼接工序 0否 1是''');
CALL sp_erp_add_column('t_erp_process_def', 'shrinkage_baseline', 'DECIMAL(5,2)   DEFAULT NULL COMMENT ''缩水基线(%)，拼接前预缩处理基准值''');
CALL sp_erp_add_column('t_erp_process_def', 'elasticity_compensation', 'DECIMAL(5,2)   DEFAULT NULL COMMENT ''弹力补偿量(%)，拼接时弹力损耗补偿''');
CALL sp_erp_add_column('t_erp_process_def', 'seam_width', 'DECIMAL(5,1)   DEFAULT NULL COMMENT ''拼缝规格(mm)，拼缝宽度标准''');
CALL sp_erp_add_column('t_erp_process_def', 'splice_direction', 'VARCHAR(10)    DEFAULT NULL COMMENT ''拼接方向: WARP经向 WEFT纬向 BIAS斜向 ANY不限''');
CALL sp_erp_add_column('t_erp_process_def', 'fabric_compatibility', 'VARCHAR(200)   DEFAULT NULL COMMENT ''拼接面料兼容性说明''');

-- ============================================================
-- 2. t_erp_sample_tech 染整阈值约束字段
-- ============================================================
CALL sp_erp_add_column('t_erp_sample_tech', 'shrinkage_rate_limit', 'DECIMAL(5,2)  DEFAULT NULL COMMENT ''缩水率上限(%)，超出触发预缩工序''');
CALL sp_erp_add_column('t_erp_sample_tech', 'color_difference_grade_min', 'DECIMAL(3,1)  DEFAULT NULL COMMENT ''色差等级下限(GB/T 250)，低于此值不合格''');
CALL sp_erp_add_column('t_erp_sample_tech', 'setting_temp_max', 'SMALLINT      DEFAULT NULL COMMENT ''定型温度上限(℃)，超出损伤面料''');
CALL sp_erp_add_column('t_erp_sample_tech', 'setting_temp_min', 'SMALLINT      DEFAULT NULL COMMENT ''定型温度下限(℃)''');
CALL sp_erp_add_column('t_erp_sample_tech', 'wash_fastness_requirement', 'VARCHAR(20)   DEFAULT NULL COMMENT ''水洗色牢度要求(JIS L 0844)，如 4级''');
CALL sp_erp_add_column('t_erp_sample_tech', 'rub_fastness_requirement', 'VARCHAR(20)   DEFAULT NULL COMMENT ''摩擦色牢度要求(JIS L 0849)，如 4级''');
CALL sp_erp_add_column('t_erp_sample_tech', 'ph_range', 'VARCHAR(20)   DEFAULT NULL COMMENT ''pH值范围，对日要求 4.0-7.5''');

-- ============================================================
-- 3. 字典数据：拼接方向
-- ============================================================
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('拼接方向', 'erp_splice_direction', '0', 'admin', NOW(), '针织拼接工序方向要求');

INSERT IGNORE INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES
(1, '经向', 'WARP',  'erp_splice_direction', '', 'default', 'N', '0', 'admin', NOW(), '经向拼接'),
(2, '纬向', 'WEFT',  'erp_splice_direction', '', 'default', 'N', '0', 'admin', NOW(), '纬向拼接'),
(3, '斜向', 'BIAS',  'erp_splice_direction', '', 'warning', 'N', '0', 'admin', NOW(), '斜向拼接，需特别注意缩水'),
(4, '不限', 'ANY',   'erp_splice_direction', '', 'info',    'Y', '0', 'admin', NOW(), '方向不限');

-- ============================================================
-- 4. 字典数据：色牢度等级（JIS标准）
-- ============================================================
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('色牢度等级', 'erp_fastness_grade', '0', 'admin', NOW(), 'JIS色牢度等级，1-5级，4级以上合格');

INSERT IGNORE INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, css_class, list_class, is_default, status, create_by, create_time, remark)
VALUES
(1, '5级（优）', '5', 'erp_fastness_grade', '', 'success', 'N', '0', 'admin', NOW(), '最高等级'),
(2, '4-5级',    '4.5','erp_fastness_grade','', 'success', 'N', '0', 'admin', NOW(), ''),
(3, '4级（合格）','4', 'erp_fastness_grade','', 'success', 'Y', '0', 'admin', NOW(), '对日最低合格线'),
(4, '3-4级',    '3.5','erp_fastness_grade','', 'warning', 'N', '0', 'admin', NOW(), ''),
(5, '3级（警告）','3', 'erp_fastness_grade','', 'warning', 'N', '0', 'admin', NOW(), '需客户确认'),
(6, '2级（不合格）','2','erp_fastness_grade','','danger',  'N', '0', 'admin', NOW(), '不合格'),
(7, '1级（不合格）','1','erp_fastness_grade','','danger',  'N', '0', 'admin', NOW(), '不合格');
