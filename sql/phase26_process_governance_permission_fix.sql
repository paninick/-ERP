-- phase26_process_governance_permission_fix.sql
-- Purpose:
-- 1. Deduplicate route-customization dictionary data created by repeatable hotfix runs.
-- 2. Backfill missing process governance button permissions used by backend @PreAuthorize.
-- 3. Keep the script safe to rerun.

SET NAMES utf8mb4;

-- Keep the earliest dict_code for each controlled dict_type + dict_value pair.
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

-- Align process type dictionary with the current schema:
-- t_erp_process_def.process_type is char(1), where 0=internal and 1=outsource.
DELETE FROM sys_dict_data
WHERE dict_type = 'erp_process_type'
  AND dict_value IN ('QUALITY', 'FINISHING_QC');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4457, '查询', 2017, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:query', '#', 'admin', NOW(), '', NULL, '工艺路线详情权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:processRoute:query');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4458, '查询明细', 2017, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRouteItem:query', '#', 'admin', NOW(), '', NULL, '工艺路线明细详情权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:processRouteItem:query');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4459, '新增明细', 2017, 7, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRouteItem:add', '#', 'admin', NOW(), '', NULL, '工艺路线明细新增权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:processRouteItem:add');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4460, '修改明细', 2017, 8, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRouteItem:edit', '#', 'admin', NOW(), '', NULL, '工艺路线明细修改权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:processRouteItem:edit');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4461, '删除明细', 2017, 9, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRouteItem:remove', '#', 'admin', NOW(), '', NULL, '工艺路线明细删除权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:processRouteItem:remove');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4462, '导出明细', 2017, 10, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRouteItem:export', '#', 'admin', NOW(), '', NULL, '工艺路线明细导出权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:processRouteItem:export');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4463, '查询', 2042, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:query', '#', 'admin', NOW(), '', NULL, '工序流转详情和当前工序权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:produceJobProcess:query');

INSERT INTO sys_menu (
  menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache,
  menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark
)
SELECT 4464, '查询', 2061, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:query', '#', 'admin', NOW(), '', NULL, '次品详情权限'
WHERE NOT EXISTS (SELECT 1 FROM sys_menu WHERE perms = 'erp:defect:query');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id BETWEEN 4457 AND 4464;

-- Inherit process-route-item permissions for roles that can maintain process routes.
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, m.menu_id
FROM sys_role_menu rm
JOIN sys_menu m ON m.menu_id BETWEEN 4458 AND 4462
WHERE rm.menu_id = 2017;

-- Inherit detail-query permissions from the corresponding module menu assignment.
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, 4457
FROM sys_role_menu rm
WHERE rm.menu_id = 2017;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, 4463
FROM sys_role_menu rm
WHERE rm.menu_id = 2042;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT rm.role_id, 4464
FROM sys_role_menu rm
WHERE rm.menu_id = 2061;

SELECT dict_type, dict_value, COUNT(*) AS cnt
FROM sys_dict_data
WHERE dict_type IN (
  'erp_route_item_required_mode',
  'erp_route_condition_code',
  'erp_product_family',
  'erp_process_type'
)
GROUP BY dict_type, dict_value
HAVING COUNT(*) > 1;

SELECT menu_id, menu_name, parent_id, perms
FROM sys_menu
WHERE menu_id BETWEEN 4457 AND 4464
ORDER BY menu_id;
