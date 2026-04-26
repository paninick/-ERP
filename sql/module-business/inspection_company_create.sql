-- Wave 2: 检品公司 + 检品预约表
-- 幂等，可重复执行

-- 检品结果字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '检品结果', 'erp_inspection_result', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_inspection_result');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'待预约','WAIT_BOOKING','erp_inspection_result','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_inspection_result' AND dict_value='WAIT_BOOKING');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'已预约','BOOKED','erp_inspection_result','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_inspection_result' AND dict_value='BOOKED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'通过','PASS','erp_inspection_result','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_inspection_result' AND dict_value='PASS');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 4,'不通过','FAIL','erp_inspection_result','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_inspection_result' AND dict_value='FAIL');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 5,'复检','RECHECK','erp_inspection_result','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_inspection_result' AND dict_value='RECHECK');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 6,'已放行','RELEASED','erp_inspection_result','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_inspection_result' AND dict_value='RELEASED');

-- 检品公司档案表
CREATE TABLE IF NOT EXISTS t_erp_inspection_company (
  id           BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  company_name VARCHAR(100) NOT NULL COMMENT '检品公司名称',
  company_code VARCHAR(50)  COMMENT '检品公司编码',
  contact      VARCHAR(50)  COMMENT '联系人',
  phone        VARCHAR(30)  COMMENT '联系电话',
  email        VARCHAR(100) COMMENT '邮箱',
  address      VARCHAR(200) COMMENT '地址',
  aql_standard VARCHAR(50)  COMMENT '默认AQL标准',
  status       CHAR(1)      NOT NULL DEFAULT '0' COMMENT '状态(0正常1停用)',
  remark       VARCHAR(500) COMMENT '备注',
  create_by    VARCHAR(64),
  create_time  DATETIME     DEFAULT CURRENT_TIMESTAMP,
  update_by    VARCHAR(64),
  update_time  DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_company_code (company_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检品公司档案';

-- 检品预约单表
CREATE TABLE IF NOT EXISTS t_erp_inspection_booking (
  id                    BIGINT       NOT NULL AUTO_INCREMENT COMMENT '主键',
  booking_no            VARCHAR(50)  NOT NULL COMMENT '预约单号',
  sales_order_id        BIGINT       COMMENT '关联销售订单ID',
  sales_no              VARCHAR(50)  COMMENT '销售单号',
  style_code            VARCHAR(50)  COMMENT '款号',
  inspection_company_id BIGINT       NOT NULL COMMENT '检品公司ID',
  booking_date          DATE         COMMENT '预约日期',
  inspection_date       DATE         COMMENT '检品日期',
  inspection_result     VARCHAR(20)  COMMENT '检品结果(字典:erp_inspection_result)',
  report_no             VARCHAR(100) COMMENT '检品报告编号',
  defect_summary        VARCHAR(500) COMMENT '缺陷摘要',
  release_by            VARCHAR(50)  COMMENT '放行人',
  release_time          DATETIME     COMMENT '放行时间',
  status                VARCHAR(20)  NOT NULL DEFAULT 'WAIT_BOOKING' COMMENT '状态(字典:erp_inspection_result)',
  factory_id            BIGINT       COMMENT '工厂ID',
  remark                VARCHAR(500) COMMENT '备注',
  create_by             VARCHAR(64),
  create_time           DATETIME     DEFAULT CURRENT_TIMESTAMP,
  update_by             VARCHAR(64),
  update_time           DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_booking_no (booking_no),
  KEY idx_sales_order_id (sales_order_id),
  KEY idx_inspection_company_id (inspection_company_id),
  KEY idx_style_code (style_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='检品预约单';

-- 菜单注册
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '检品公司', (SELECT menu_id FROM sys_menu WHERE path='erp' LIMIT 1), 30,
  'inspectionCompany', 'erp/inspectionCompany/index', 1, 0, 'C', '0', '0', 'erp:inspectionCompany:list', '#', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='erp:inspectionCompany:list');

INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '检品预约', (SELECT menu_id FROM sys_menu WHERE path='erp' LIMIT 1), 31,
  'inspectionBooking', 'erp/inspectionBooking/index', 1, 0, 'C', '0', '0', 'erp:inspectionBooking:list', '#', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='erp:inspectionBooking:list');

SELECT 'inspection_company_create.sql 执行完成' AS result;
