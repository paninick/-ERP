-- Wave 2: 款号主档 t_erp_style
-- 幂等，可重复执行

-- 款号状态字典
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time)
SELECT '款号状态', 'erp_style_status', '0', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_dict_type WHERE dict_type='erp_style_status');

INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 1,'活跃','ACTIVE','erp_style_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_style_status' AND dict_value='ACTIVE');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 2,'停产','DISCONTINUED','erp_style_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_style_status' AND dict_value='DISCONTINUED');
INSERT INTO sys_dict_data (dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time)
SELECT 3,'草稿','DRAFT','erp_style_status','0','admin',NOW() WHERE NOT EXISTS (SELECT 1 FROM sys_dict_data WHERE dict_type='erp_style_status' AND dict_value='DRAFT');

-- 款号主档表
CREATE TABLE IF NOT EXISTS t_erp_style (
  id                  BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  style_code          VARCHAR(50)   NOT NULL COMMENT '内部款号（系统生成）',
  customer_style_code VARCHAR(50)   COMMENT '客户款号',
  style_name          VARCHAR(100)  COMMENT '款号名称',
  product_family      VARCHAR(20)   COMMENT '产品族(字典:erp_product_family)',
  customer_id         BIGINT        COMMENT '客户ID',
  season              VARCHAR(20)   COMMENT '季节',
  yarn_type           VARCHAR(50)   COMMENT '纱线类型',
  gauge               VARCHAR(20)   COMMENT '机号',
  weight              DECIMAL(10,3) COMMENT '克重',
  bom_version         VARCHAR(20)   COMMENT '当前BOM版本',
  route_version       VARCHAR(20)   COMMENT '当前工艺路线版本',
  status              VARCHAR(20)   NOT NULL DEFAULT 'ACTIVE' COMMENT '状态(字典:erp_style_status)',
  factory_id          BIGINT        COMMENT '工厂ID',
  remark              VARCHAR(500)  COMMENT '备注',
  create_by           VARCHAR(64),
  create_time         DATETIME      DEFAULT CURRENT_TIMESTAMP,
  update_by           VARCHAR(64),
  update_time         DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY uk_style_code (style_code),
  KEY idx_customer_id (customer_id),
  KEY idx_product_family (product_family),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='款号主档';

-- 菜单注册
INSERT INTO sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time)
SELECT '款号档案', (SELECT menu_id FROM sys_menu WHERE path='erp' LIMIT 1), 32,
  'style', 'erp/style/index', 1, 0, 'C', '0', '0', 'erp:style:list', '#', 'admin', NOW()
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms='erp:style:list');

SELECT 'style_master_create.sql 执行完成' AS result;
