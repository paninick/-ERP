-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('采购单', '2086', '1', 'purchase', 'erp/purchase/index', 1, 0, 'C', '0', '0', 'erp:purchase:list', '#', 'admin', sysdate(), '', null, '采购单菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('采购单查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:purchase:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('采购单新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:purchase:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('采购单修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:purchase:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('采购单删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:purchase:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('采购单导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:purchase:export',       '#', 'admin', sysdate(), '', null, '');