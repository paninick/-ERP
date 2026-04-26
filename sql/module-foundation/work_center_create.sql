-- WorkCenter 全栈建表 + 字典
-- 幂等，可重复执行

-- 1. 字典：工作中心类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
SELECT '工作中心类型', 'erp_work_center_type', '0', 'admin', NOW(), '工作中心类型'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'erp_work_center_type');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1, '横机', 'FLAT_KNITTING', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'FLAT_KNITTING');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2, '套口', 'LINKING', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'LINKING');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3, '缝纫', 'SEWING', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'SEWING');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4, '水洗', 'WASHING', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'WASHING');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5, '整烫', 'FINISHING', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'FINISHING');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6, '质检', 'QC', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'QC');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 7, '包装', 'PACKING', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'PACKING');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 8, '通用', 'GENERAL', 'erp_work_center_type', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_work_center_type' AND dict_value = 'GENERAL');

-- 2. 字典：工艺路线状态
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
SELECT '工艺路线状态', 'erp_route_status', '0', 'admin', NOW(), '工艺路线发布状态'
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type = 'erp_route_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1, '草稿', 'DRAFT', 'erp_route_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_route_status' AND dict_value = 'DRAFT');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2, '已发布', 'PUBLISHED', 'erp_route_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_route_status' AND dict_value = 'PUBLISHED');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3, '已停用', 'DISABLED', 'erp_route_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type = 'erp_route_status' AND dict_value = 'DISABLED');

-- 3. 工作中心表
CREATE TABLE IF NOT EXISTS t_erp_work_center (
  id            BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  center_code   VARCHAR(50)  NOT NULL COMMENT '工作中心编码',
  center_name   VARCHAR(100) NOT NULL COMMENT '工作中心名称',
  center_type   VARCHAR(30)  NOT NULL COMMENT '类型(字典:erp_work_center_type)',
  workshop_id   BIGINT       COMMENT '所属车间ID(t_erp_org_unit)',
  capacity      DECIMAL(10,2) COMMENT '日产能',
  capacity_unit VARCHAR(20)  COMMENT '产能单位',
  manager       VARCHAR(50)  COMMENT '负责人',
  status        CHAR(1)      NOT NULL DEFAULT '0' COMMENT '状态(0正常1停用)',
  remark        VARCHAR(500) COMMENT '备注',
  create_by     VARCHAR(64),
  create_time   DATETIME     DEFAULT CURRENT_TIMESTAMP,
  update_by     VARCHAR(64),
  update_time   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_center_code (center_code),
  KEY idx_workshop_id (workshop_id),
  KEY idx_center_type (center_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工作中心';

-- 4. 菜单注册
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, remark)
SELECT '工作中心', (SELECT menu_id FROM sys_menu WHERE path = 'erp' LIMIT 1), 25,
  'workCenter', 'erp/workCenter/index', 1, 0, 'C', '0', '0', 'erp:workCenter:list', '#', 'admin', NOW(), ''
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:workCenter:list');

SELECT 'work_center_create.sql 执行完成' AS result;
