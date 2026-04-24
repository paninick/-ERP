-- 2026-04-24 ERP menu and permission backfill
-- Purpose:
-- 1. Restore missing ERP route menus for overview / quality / process price / data import.
-- 2. Backfill frontend v-hasPermi button rows that are still missing in sys_menu.
-- 3. Keep smoke-test runtime stable without broad role inflation.
--
-- Safe to run multiple times.

SET NAMES utf8mb4;

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

UPDATE sys_menu SET
  menu_name = '打样总览',
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
  update_time = NOW(),
  remark = 'ERP 打样总览入口'
WHERE menu_id = 2551;

UPDATE sys_menu SET
  menu_name = '质检工作台',
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
  update_time = NOW(),
  remark = 'ERP 质检工作台入口'
WHERE menu_id = 2552;

UPDATE sys_menu SET
  menu_name = '工序工价',
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
  update_time = NOW(),
  remark = 'ERP 工序工价入口'
WHERE menu_id = 2553;

UPDATE sys_menu SET
  menu_name = '数据导入',
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
  update_time = NOW(),
  remark = 'ERP 数据导入入口'
WHERE menu_id = 2554;

UPDATE sys_menu SET
  visible = '1',
  status = '1',
  update_by = 'codex',
  update_time = NOW(),
  remark = CONCAT(IFNULL(remark, ''), ' [legacy hidden by 2026-04-24 hotfix]')
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
