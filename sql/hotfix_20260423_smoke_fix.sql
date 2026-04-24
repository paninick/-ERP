-- 2026-04-23 smoke hotfix
-- Purpose:
-- 1. Backfill the three ERP base tables required by smoke-test pages.
-- 2. Self-heal the ERP menu tree for the affected pages even if phase16 was only partially applied.
-- 3. Add missing inventory button permissions so page actions match the frontend v-hasPermi checks.
-- 4. Disable legacy duplicated ERP menu trees to avoid mixed old/new navigation.
--
-- Notes:
-- - This script is intentionally idempotent.
-- - Canonical menu definitions should stay aligned with sql/phase16_menu_register.sql.
-- - Execute with utf8mb4 client encoding, e.g.
--   mysql --default-character-set=utf8mb4 -uroot ry_vue < sql/hotfix_20260423_smoke_fix.sql

SET NAMES utf8mb4;

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

-- ============================================================
-- 1. Ensure required ERP directory menus exist.
-- ============================================================
INSERT IGNORE INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
) VALUES
(2000, '服装ERP', 0, 5, 'erp', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-goods', 'admin', NOW(), '', NULL, '服装ERP系统'),
(2001, '基础数据', 2000, 1, 'base', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-setting', 'admin', NOW(), '', NULL, ''),
(2003, '生产管理', 2000, 3, 'produce', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-cpu', 'admin', NOW(), '', NULL, ''),
(2005, '库存管理', 2000, 5, 'inventory', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-box', 'admin', NOW(), '', NULL, ''),
(2006, '财务管理', 2000, 6, 'finance', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-money', 'admin', NOW(), '', NULL, ''),
(2007, '报表中心', 2000, 7, 'report', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-data-line', 'admin', NOW(), '', NULL, '');

UPDATE sys_menu SET menu_name = '服装ERP', parent_id = 0, order_num = 5, path = 'erp', component = NULL, menu_type = 'M', visible = '0', status = '0', perms = '', icon = 'ep-goods', remark = '服装ERP系统', update_by = 'codex', update_time = NOW() WHERE menu_id = 2000;
UPDATE sys_menu SET menu_name = '基础数据', parent_id = 2000, order_num = 1, path = 'base', component = NULL, menu_type = 'M', visible = '0', status = '0', perms = '', icon = 'ep-setting', update_by = 'codex', update_time = NOW() WHERE menu_id = 2001;
UPDATE sys_menu SET menu_name = '生产管理', parent_id = 2000, order_num = 3, path = 'produce', component = NULL, menu_type = 'M', visible = '0', status = '0', perms = '', icon = 'ep-cpu', update_by = 'codex', update_time = NOW() WHERE menu_id = 2003;
UPDATE sys_menu SET menu_name = '库存管理', parent_id = 2000, order_num = 5, path = 'inventory', component = NULL, menu_type = 'M', visible = '0', status = '0', perms = '', icon = 'ep-box', update_by = 'codex', update_time = NOW() WHERE menu_id = 2005;
UPDATE sys_menu SET menu_name = '财务管理', parent_id = 2000, order_num = 6, path = 'finance', component = NULL, menu_type = 'M', visible = '0', status = '0', perms = '', icon = 'ep-money', update_by = 'codex', update_time = NOW() WHERE menu_id = 2006;
UPDATE sys_menu SET menu_name = '报表中心', parent_id = 2000, order_num = 7, path = 'report', component = NULL, menu_type = 'M', visible = '0', status = '0', perms = '', icon = 'ep-data-line', update_by = 'codex', update_time = NOW() WHERE menu_id = 2007;

-- ============================================================
-- 2. Ensure affected page menus exist and point to canonical components.
-- ============================================================
INSERT IGNORE INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
) VALUES
(2012, '员工档案', 2001, 3, 'employee', 'erp/employee/index', '', 1, 0, 'C', '0', '0', 'erp:employee:list', 'ep-avatar', 'admin', NOW(), '', NULL, ''),
(2016, '工序定义', 2001, 7, 'processDef', 'erp/processDef/index', '', 1, 0, 'C', '0', '0', 'erp:processDef:list', 'ep-list', 'admin', NOW(), '', NULL, ''),
(2019, '标准色管理', 2001, 10, 'standardColor', 'erp/standardColor/index', '', 1, 0, 'C', '0', '0', 'erp:standardColor:list', 'ep-brush', 'admin', NOW(), '', NULL, ''),
(2021, '损耗矩阵', 2001, 12, 'processLossMatrix', 'erp/processLossMatrix/index', '', 1, 0, 'C', '0', '0', 'erp:processLossMatrix:list', 'ep-grid', 'admin', NOW(), '', NULL, ''),
(2048, '产品序列号', 2003, 9, 'productSerial', 'erp/productSerial/index', '', 1, 0, 'C', '0', '0', 'erp:productSerial:list', 'ep-barcode', 'admin', NOW(), '', NULL, ''),
(2073, '库存盘点', 2005, 4, 'inventory', 'erp/inventory/index', '', 1, 0, 'C', '0', '0', 'erp:inventory:list', 'ep-checked', 'admin', NOW(), '', NULL, ''),
(2080, '计件工资', 2006, 1, 'piecewage', 'erp/piecewage/index', '', 1, 0, 'C', '0', '0', 'erp:piecewage:list', 'ep-coin', 'admin', NOW(), '', NULL, ''),
(2081, '工资明细', 2006, 2, 'piecewagedetail', 'erp/piecewagedetail/index', '', 1, 0, 'C', '0', '0', 'erp:piecewagedetail:list', 'ep-document', 'admin', NOW(), '', NULL, ''),
(2084, '报表总览', 2007, 1, 'reportHub', 'erp/report/index', '', 1, 0, 'C', '0', '0', 'erp:report:list', 'ep-data-analysis', 'admin', NOW(), '', NULL, '报表中心入口页');

UPDATE sys_menu SET menu_name = '员工档案', parent_id = 2001, order_num = 3, path = 'employee', component = 'erp/employee/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:employee:list', icon = 'ep-avatar', update_by = 'codex', update_time = NOW() WHERE menu_id = 2012;
UPDATE sys_menu SET menu_name = '工序定义', parent_id = 2001, order_num = 7, path = 'processDef', component = 'erp/processDef/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:processDef:list', icon = 'ep-list', update_by = 'codex', update_time = NOW() WHERE menu_id = 2016;
UPDATE sys_menu SET menu_name = '标准色管理', parent_id = 2001, order_num = 10, path = 'standardColor', component = 'erp/standardColor/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:standardColor:list', icon = 'ep-brush', update_by = 'codex', update_time = NOW() WHERE menu_id = 2019;
UPDATE sys_menu SET menu_name = '损耗矩阵', parent_id = 2001, order_num = 12, path = 'processLossMatrix', component = 'erp/processLossMatrix/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:processLossMatrix:list', icon = 'ep-grid', update_by = 'codex', update_time = NOW() WHERE menu_id = 2021;
UPDATE sys_menu SET menu_name = '产品序列号', parent_id = 2003, order_num = 9, path = 'productSerial', component = 'erp/productSerial/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:productSerial:list', icon = 'ep-barcode', update_by = 'codex', update_time = NOW() WHERE menu_id = 2048;
UPDATE sys_menu SET menu_name = '库存盘点', parent_id = 2005, order_num = 4, path = 'inventory', component = 'erp/inventory/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:inventory:list', icon = 'ep-checked', update_by = 'codex', update_time = NOW() WHERE menu_id = 2073;
UPDATE sys_menu SET route_name = 'InventoryCheck', update_by = 'codex', update_time = NOW() WHERE menu_id = 2073;
UPDATE sys_menu SET menu_name = '计件工资', parent_id = 2006, order_num = 1, path = 'piecewage', component = 'erp/piecewage/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:piecewage:list', icon = 'ep-coin', update_by = 'codex', update_time = NOW() WHERE menu_id = 2080;
UPDATE sys_menu SET menu_name = '工资明细', parent_id = 2006, order_num = 2, path = 'piecewagedetail', component = 'erp/piecewagedetail/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:piecewagedetail:list', icon = 'ep-document', update_by = 'codex', update_time = NOW() WHERE menu_id = 2081;
UPDATE sys_menu SET menu_name = '报表总览', parent_id = 2007, order_num = 1, path = 'reportHub', component = 'erp/report/index', menu_type = 'C', visible = '0', status = '0', perms = 'erp:report:list', icon = 'ep-data-analysis', remark = '报表中心入口页', update_by = 'codex', update_time = NOW() WHERE menu_id = 2084;

-- ============================================================
-- 3. Ensure affected button permissions exist, including inventory CRUD.
-- ============================================================
INSERT IGNORE INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
) VALUES
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
(2546, '导出', 2081, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:export', '#', 'admin', NOW(), '', NULL, ''),
(2547, '查询', 2073, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:query', '#', 'admin', NOW(), '', NULL, ''),
(2548, '新增', 2073, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:add', '#', 'admin', NOW(), '', NULL, ''),
(2549, '修改', 2073, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:edit', '#', 'admin', NOW(), '', NULL, ''),
(2550, '删除', 2073, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:remove', '#', 'admin', NOW(), '', NULL, '');

INSERT IGNORE INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
) VALUES
(2555, '鏌ヨ', 2032, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:query', '#', 'admin', NOW(), '', NULL, ''),
(2556, '鏂板', 2032, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:add', '#', 'admin', NOW(), '', NULL, ''),
(2557, '淇敼', 2032, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:edit', '#', 'admin', NOW(), '', NULL, ''),
(2558, '鍒犻櫎', 2032, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:remove', '#', 'admin', NOW(), '', NULL, ''),
(2559, '瀵煎嚭', 2032, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:export', '#', 'admin', NOW(), '', NULL, ''),
(2560, '瀵煎叆', 2032, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:import', '#', 'admin', NOW(), '', NULL, ''),
(2561, '鏌ヨ', 2071, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:query', '#', 'admin', NOW(), '', NULL, ''),
(2562, '鏂板', 2071, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:add', '#', 'admin', NOW(), '', NULL, ''),
(2563, '淇敼', 2071, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:edit', '#', 'admin', NOW(), '', NULL, ''),
(2564, '鍒犻櫎', 2071, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:remove', '#', 'admin', NOW(), '', NULL, ''),
(2565, '瀵煎嚭', 2071, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:export', '#', 'admin', NOW(), '', NULL, ''),
(2566, '瀵煎叆', 2071, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:import', '#', 'admin', NOW(), '', NULL, ''),
(2567, '鏌ヨ', 2072, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:query', '#', 'admin', NOW(), '', NULL, ''),
(2568, '鏂板', 2072, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:add', '#', 'admin', NOW(), '', NULL, ''),
(2569, '淇敼', 2072, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:edit', '#', 'admin', NOW(), '', NULL, ''),
(2570, '鍒犻櫎', 2072, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:remove', '#', 'admin', NOW(), '', NULL, ''),
(2571, '瀵煎嚭', 2072, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:export', '#', 'admin', NOW(), '', NULL, '');

-- ============================================================
-- 4. Grant only the menu nodes repaired by this script to super admin.
-- ============================================================
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id IN (
  2000, 2001, 2003, 2005, 2006, 2007,
  2012, 2016, 2019, 2021, 2032, 2048, 2071, 2072, 2073, 2080, 2081, 2084,
  2512, 2513, 2514, 2515, 2516,
  2517, 2518, 2519, 2520, 2521,
  2522, 2523, 2524, 2525, 2526,
  2527, 2528, 2529, 2530, 2531,
  2532, 2533, 2534, 2535, 2536,
  2537, 2538, 2539, 2540, 2541,
  2542, 2543, 2544, 2545, 2546,
  2547, 2548, 2549, 2550,
  2555, 2556, 2557, 2558, 2559, 2560,
  2561, 2562, 2563, 2564, 2565, 2566,
  2567, 2568, 2569, 2570, 2571
);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 201, menu_id FROM sys_menu
WHERE perms IN ('erp:tech:list', 'erp:tech:query', 'erp:tech:add', 'erp:tech:edit', 'erp:tech:remove', 'erp:tech:export', 'erp:tech:import');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 202, menu_id FROM sys_menu
WHERE perms IN ('erp:tech:list', 'erp:tech:query');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 204, menu_id FROM sys_menu
WHERE perms IN (
  'erp:stockin:list', 'erp:stockin:query', 'erp:stockin:add', 'erp:stockin:edit', 'erp:stockin:remove', 'erp:stockin:export', 'erp:stockin:import',
  'erp:stockout:list', 'erp:stockout:query', 'erp:stockout:add', 'erp:stockout:edit', 'erp:stockout:remove', 'erp:stockout:export'
);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, 2555
FROM sys_role_menu rm
WHERE rm.menu_id = 2032;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id,
       CASE rm.menu_id
         WHEN 4389 THEN 2556
         WHEN 4390 THEN 2557
         WHEN 4391 THEN 2558
         WHEN 4392 THEN 2559
       END AS menu_id
FROM sys_role_menu rm
WHERE rm.menu_id IN (4389, 4390, 4391, 4392);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, 2561
FROM sys_role_menu rm
WHERE rm.menu_id = 2071;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, 2567
FROM sys_role_menu rm
WHERE rm.menu_id = 2072;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id,
       CASE rm.menu_id
         WHEN 4405 THEN 2562
         WHEN 4406 THEN 2563
         WHEN 4407 THEN 2564
         WHEN 4408 THEN 2565
       END AS menu_id
FROM sys_role_menu rm
WHERE rm.menu_id IN (4405, 4406, 4407, 4408);

-- ============================================================
-- 5. Hide legacy duplicated ERP menus and remove their role bindings.
-- ============================================================
UPDATE sys_menu
SET path = 'warehouselocation-legacy-disabled',
    route_name = 'WarehouseLocationLegacyDisabled',
    visible = '1',
    status = '1',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 4320;

UPDATE sys_menu
SET visible = '1',
    status = '1',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id IN (7, 8, 9, 800, 801, 802, 803, 804, 805, 806, 807, 900, 901, 902, 903, 904, 905, 906, 907, 908, 910, 911, 912, 4320, 4449);

DELETE FROM sys_role_menu
WHERE menu_id IN (7, 8, 9, 800, 801, 802, 803, 804, 805, 806, 807, 900, 901, 902, 903, 904, 905, 906, 907, 908, 910, 911, 912, 4320, 4449);

-- ============================================================
-- 6. 2026-04-24 backfill: missing ERP button perms and route alignment.
-- ============================================================
INSERT IGNORE INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
) VALUES
(2551, '打样总览', 2002, 6, 'overview', 'erp/overview/index', '', 1, 0, 'C', '0', '0', 'erp:overview:list', 'ep-view', 'admin', NOW(), '', NULL, 'ERP 打样总览入口'),
(2552, '质检工作台', 2004, 3, 'quality', 'erp/quality/index', '', 1, 0, 'C', '0', '0', 'erp:qc:list', 'ep-finished', 'admin', NOW(), '', NULL, 'ERP 质检工作台入口'),
(2553, '工序工价', 2006, 5, 'processPrice', 'erp/processPrice/index', '', 1, 0, 'C', '0', '0', 'erp:processPrice:list', 'ep-coin', 'admin', NOW(), '', NULL, 'ERP 工序工价入口'),
(2554, '数据导入', 2001, 13, 'dataimport', 'erp/dataimport/index', '', 1, 0, 'C', '0', '0', 'erp:dataimport:list', 'ep-upload', 'admin', NOW(), '', NULL, 'ERP 数据导入入口'),
(2572, '导入', 2014, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:import', '#', 'admin', NOW(), '', NULL, ''),
(2573, '新增', 2049, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:add', '#', 'admin', NOW(), '', NULL, ''),
(2574, '修改', 2049, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:edit', '#', 'admin', NOW(), '', NULL, ''),
(2575, '删除', 2049, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:remove', '#', 'admin', NOW(), '', NULL, ''),
(2576, '导出', 2049, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:export', '#', 'admin', NOW(), '', NULL, ''),
(2577, '新增', 2018, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:add', '#', 'admin', NOW(), '', NULL, ''),
(2578, '修改', 2018, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:edit', '#', 'admin', NOW(), '', NULL, ''),
(2579, '删除', 2018, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:remove', '#', 'admin', NOW(), '', NULL, ''),
(2580, '导出', 2018, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:export', '#', 'admin', NOW(), '', NULL, ''),
(2581, '导入', 2018, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:import', '#', 'admin', NOW(), '', NULL, ''),
(2582, '导入', 2010, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:customer:import', '#', 'admin', NOW(), '', NULL, ''),
(2583, '新增', 2061, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:add', '#', 'admin', NOW(), '', NULL, ''),
(2584, '修改', 2061, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:edit', '#', 'admin', NOW(), '', NULL, ''),
(2585, '删除', 2061, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:remove', '#', 'admin', NOW(), '', NULL, ''),
(2586, '导出', 2061, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:export', '#', 'admin', NOW(), '', NULL, ''),
(2587, '导入', 2013, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:material:import', '#', 'admin', NOW(), '', NULL, ''),
(2588, '导入', 2031, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:notice:import', '#', 'admin', NOW(), '', NULL, ''),
(2589, '新增', 2043, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:add', '#', 'admin', NOW(), '', NULL, ''),
(2590, '修改', 2043, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:edit', '#', 'admin', NOW(), '', NULL, ''),
(2591, '删除', 2043, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:remove', '#', 'admin', NOW(), '', NULL, ''),
(2592, '导出', 2043, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:export', '#', 'admin', NOW(), '', NULL, ''),
(2593, '查询', 2553, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:query', '#', 'admin', NOW(), '', NULL, ''),
(2594, '新增', 2553, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:add', '#', 'admin', NOW(), '', NULL, ''),
(2595, '修改', 2553, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:edit', '#', 'admin', NOW(), '', NULL, ''),
(2596, '删除', 2553, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:remove', '#', 'admin', NOW(), '', NULL, ''),
(2597, '导出', 2553, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:export', '#', 'admin', NOW(), '', NULL, ''),
(2598, '新增', 2017, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:add', '#', 'admin', NOW(), '', NULL, ''),
(2599, '修改', 2017, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:edit', '#', 'admin', NOW(), '', NULL, ''),
(2600, '删除', 2017, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:remove', '#', 'admin', NOW(), '', NULL, ''),
(2601, '导出', 2017, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:export', '#', 'admin', NOW(), '', NULL, ''),
(2602, '新增', 2041, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:add', '#', 'admin', NOW(), '', NULL, ''),
(2603, '修改', 2041, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:edit', '#', 'admin', NOW(), '', NULL, ''),
(2604, '删除', 2041, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:remove', '#', 'admin', NOW(), '', NULL, ''),
(2605, '导出', 2041, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:export', '#', 'admin', NOW(), '', NULL, ''),
(2606, '新增', 2042, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:add', '#', 'admin', NOW(), '', NULL, ''),
(2607, '修改', 2042, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:edit', '#', 'admin', NOW(), '', NULL, ''),
(2608, '删除', 2042, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:remove', '#', 'admin', NOW(), '', NULL, ''),
(2609, '导出', 2042, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:export', '#', 'admin', NOW(), '', NULL, ''),
(2610, '导入', 2011, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:supplier:import', '#', 'admin', NOW(), '', NULL, ''),
(2611, '新增', 2020, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:add', '#', 'admin', NOW(), '', NULL, ''),
(2612, '修改', 2020, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:edit', '#', 'admin', NOW(), '', NULL, ''),
(2613, '删除', 2020, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:remove', '#', 'admin', NOW(), '', NULL, ''),
(2614, '导出', 2020, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:export', '#', 'admin', NOW(), '', NULL, ''),
(2615, '导入', 2015, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:warehouse:import', '#', 'admin', NOW(), '', NULL, ''),
(2616, '查询', 2552, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:qc:query', '#', 'admin', NOW(), '', NULL, ''),
(2617, '修改', 2552, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:qc:edit', '#', 'admin', NOW(), '', NULL, '');

UPDATE sys_menu
SET menu_name = '打样总览',
    parent_id = 2002,
    order_num = 6,
    path = 'overview',
    component = 'erp/overview/index',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:overview:list',
    icon = 'ep-view',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2551;

UPDATE sys_menu
SET menu_name = '质检工作台',
    parent_id = 2004,
    order_num = 3,
    path = 'quality',
    component = 'erp/quality/index',
    route_name = 'QualityWorkbench',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:qc:list',
    icon = 'ep-finished',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2552;

UPDATE sys_menu
SET menu_name = '工序工价',
    parent_id = 2006,
    order_num = 5,
    path = 'processPrice',
    component = 'erp/processPrice/index',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:processPrice:list',
    icon = 'ep-coin',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2553;

UPDATE sys_menu
SET menu_name = '数据导入',
    parent_id = 2001,
    order_num = 13,
    path = 'dataimport',
    component = 'erp/dataimport/index',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:dataimport:list',
    icon = 'ep-upload',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2554;

UPDATE sys_menu
SET visible = '1',
    status = '1',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 4449;

DELETE FROM sys_role_menu WHERE menu_id = 4449;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id BETWEEN 2551 AND 2617;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2551
FROM sys_role_menu
WHERE menu_id = 2031;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2552
FROM sys_role_menu
WHERE menu_id = 2060;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2616
FROM sys_role_menu
WHERE menu_id = 2060;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2617
FROM sys_role_menu
WHERE menu_id = 2060;
