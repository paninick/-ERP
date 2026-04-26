CREATE TABLE IF NOT EXISTS t_erp_org_unit (
  id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
  org_name VARCHAR(100) NOT NULL COMMENT '组织名称',
  org_code VARCHAR(50) COMMENT '组织编码',
  org_type VARCHAR(30) NOT NULL COMMENT '组织类型',
  factory_id BIGINT COMMENT '所属工厂ID, 顶级节点就是工厂',
  order_num INT DEFAULT 0 COMMENT '显示顺序',
  leader VARCHAR(50) COMMENT '负责人',
  phone VARCHAR(30) COMMENT '联系电话',
  status CHAR(1) DEFAULT '0' COMMENT '状态, 0正常 1停用',
  remark VARCHAR(500) COMMENT '备注',
  create_by VARCHAR(64) COMMENT '创建者',
  create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  update_by VARCHAR(64) COMMENT '更新者',
  update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id),
  KEY idx_parent_id (parent_id),
  KEY idx_factory_id (factory_id),
  KEY idx_org_type (org_type),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织层级表, 工厂, 分厂, 车间, 班组, 工位';
INSERT IGNORE INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time) VALUES ('组织类型', 'erp_org_type', '0', 'admin', NOW());
INSERT IGNORE INTO sys_dict_data (dict_type, dict_label, dict_value, dict_sort, status, create_by, create_time) VALUES ('erp_org_type', '工厂', 'FACTORY', 10, '0', 'admin', NOW()), ('erp_org_type', '分厂', 'BRANCH_FACTORY', 20, '0', 'admin', NOW()), ('erp_org_type', '车间', 'WORKSHOP', 30, '0', 'admin', NOW()), ('erp_org_type', '班组', 'TEAM', 40, '0', 'admin', NOW()), ('erp_org_type', '工位', 'STATION', 50, '0', 'admin', NOW()), ('erp_org_type', '外协厂', 'OUTSOURCE_VENDOR', 60, '0', 'admin', NOW()), ('erp_org_type', '检品公司', 'INSPECTION_COMPANY', 70, '0', 'admin', NOW());
DELETE d1 FROM sys_dict_data d1 JOIN sys_dict_data d2 ON d1.dict_type = d2.dict_type AND d1.dict_value = d2.dict_value AND d1.dict_code < d2.dict_code WHERE d1.dict_type = 'erp_org_type';
