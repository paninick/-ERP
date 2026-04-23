-- 2026-04-23 smoke hotfix
-- Scope:
-- 1. Create missing ERP base tables required by smoke test pages.
-- 2. Repair missing menu entries for inventory/report pages.
-- 3. Disable legacy duplicated ERP menu trees so users only see the 2000+ tree.

CREATE TABLE IF NOT EXISTS `t_erp_employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `employee_code` varchar(50) NOT NULL COMMENT '员工编号',
  `employee_name` varchar(50) NOT NULL COMMENT '员工姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `id_card` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `department` varchar(50) DEFAULT NULL COMMENT '所属车间/部门',
  `station` varchar(50) DEFAULT NULL COMMENT '工位',
  `entry_date` date DEFAULT NULL COMMENT '入职日期',
  `leave_date` date DEFAULT NULL COMMENT '离职日期',
  `status` char(1) DEFAULT '0' COMMENT '状态(0在职 1离职)',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_employee_code` (`employee_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工档案表';

CREATE TABLE IF NOT EXISTS `t_erp_standard_color` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '色卡ID',
  `color_code` varchar(50) NOT NULL COMMENT '颜色编码',
  `color_name` varchar(100) NOT NULL COMMENT '颜色名称',
  `color_lab` varchar(100) DEFAULT NULL COMMENT 'LAB值(逗号分隔 L,a,b)',
  `default_delta_e` decimal(5,2) DEFAULT 3.00 COMMENT '默认色差允差值',
  `color_image` varchar(500) DEFAULT NULL COMMENT '色卡图片路径',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_color_code` (`color_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标准颜色表';

CREATE TABLE IF NOT EXISTS `t_erp_process_loss_matrix` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '矩阵ID',
  `material_a_type` varchar(50) NOT NULL COMMENT '主料类型',
  `material_b_type` varchar(50) DEFAULT NULL COMMENT '辅料类型(可为空)',
  `process_code` varchar(50) NOT NULL COMMENT '工艺代码',
  `standard_loss_rate` decimal(6,4) NOT NULL COMMENT '标准损耗率',
  `actual_avg_loss` decimal(6,4) DEFAULT NULL COMMENT '历史实际平均损耗',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_loss_matrix` (`material_a_type`, `material_b_type`, `process_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序损耗矩阵表';

UPDATE sys_menu
SET menu_name = '库存盘点',
    path = 'inventory',
    route_name = 'Inventory',
    component = 'erp/inventory/index',
    visible = '0',
    status = '0',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2073;

INSERT IGNORE INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
) VALUES (
  2084, '报表总览', 2007, 1, 'reportHub', 'erp/report/index', '', 1, 0,
  'C', '0', '0', 'erp:report:list', 'ep-data-analysis', 'admin', NOW(), '', NULL, '报表中心入口页'
);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id BETWEEN 2000 AND 2999;

INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2512, '查询', 2012, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:query', '#', 'admin', NOW(), '', NULL, ''),
(2513, '新增', 2012, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:add', '#', 'admin', NOW(), '', NULL, ''),
(2514, '修改', 2012, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:edit', '#', 'admin', NOW(), '', NULL, ''),
(2515, '删除', 2012, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:remove', '#', 'admin', NOW(), '', NULL, ''),
(2516, '导出', 2012, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:export', '#', 'admin', NOW(), '', NULL, ''),
(2517, '查询', 2016, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:query', '#', 'admin', NOW(), '', NULL, ''),
(2518, '新增', 2016, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:add', '#', 'admin', NOW(), '', NULL, ''),
(2519, '修改', 2016, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:edit', '#', 'admin', NOW(), '', NULL, ''),
(2520, '删除', 2016, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:remove', '#', 'admin', NOW(), '', NULL, ''),
(2521, '导出', 2016, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:export', '#', 'admin', NOW(), '', NULL, ''),
(2522, '查询', 2019, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:query', '#', 'admin', NOW(), '', NULL, ''),
(2523, '新增', 2019, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:add', '#', 'admin', NOW(), '', NULL, ''),
(2524, '修改', 2019, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:edit', '#', 'admin', NOW(), '', NULL, ''),
(2525, '删除', 2019, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:remove', '#', 'admin', NOW(), '', NULL, ''),
(2526, '导出', 2019, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:export', '#', 'admin', NOW(), '', NULL, ''),
(2527, '查询', 2021, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:query', '#', 'admin', NOW(), '', NULL, ''),
(2528, '新增', 2021, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:add', '#', 'admin', NOW(), '', NULL, ''),
(2529, '修改', 2021, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:edit', '#', 'admin', NOW(), '', NULL, ''),
(2530, '删除', 2021, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:remove', '#', 'admin', NOW(), '', NULL, ''),
(2531, '导出', 2021, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:export', '#', 'admin', NOW(), '', NULL, ''),
(2532, '查询', 2048, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:query', '#', 'admin', NOW(), '', NULL, ''),
(2533, '新增', 2048, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:add', '#', 'admin', NOW(), '', NULL, ''),
(2534, '修改', 2048, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:edit', '#', 'admin', NOW(), '', NULL, ''),
(2535, '删除', 2048, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:remove', '#', 'admin', NOW(), '', NULL, ''),
(2536, '导出', 2048, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:export', '#', 'admin', NOW(), '', NULL, ''),
(2537, '查询', 2080, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:query', '#', 'admin', NOW(), '', NULL, ''),
(2538, '新增', 2080, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:add', '#', 'admin', NOW(), '', NULL, ''),
(2539, '修改', 2080, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:edit', '#', 'admin', NOW(), '', NULL, ''),
(2540, '删除', 2080, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:remove', '#', 'admin', NOW(), '', NULL, ''),
(2541, '导出', 2080, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:export', '#', 'admin', NOW(), '', NULL, ''),
(2542, '查询', 2081, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:query', '#', 'admin', NOW(), '', NULL, ''),
(2543, '新增', 2081, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:add', '#', 'admin', NOW(), '', NULL, ''),
(2544, '修改', 2081, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:edit', '#', 'admin', NOW(), '', NULL, ''),
(2545, '删除', 2081, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:remove', '#', 'admin', NOW(), '', NULL, ''),
(2546, '导出', 2081, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:export', '#', 'admin', NOW(), '', NULL, '');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id BETWEEN 2512 AND 2546;

UPDATE sys_menu
SET visible = '1',
    status = '1',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id IN (7, 8, 9, 800, 801, 802, 803, 804, 805, 806, 807, 900, 901, 902, 903, 904, 905, 906, 907, 908, 910, 911, 912, 4449);

DELETE FROM sys_role_menu
WHERE menu_id IN (7, 8, 9, 800, 801, 802, 803, 804, 805, 806, 807, 900, 901, 902, 903, 904, 905, 906, 907, 908, 910, 911, 912, 4449);
