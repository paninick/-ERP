-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('辅料', '2048', '1', 'auxiliary', 'erp/auxiliary/index', 1, 0, 'C', '0', '0', 'erp:auxiliary:list', '#', 'admin', sysdate(), '', null, '辅料菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('辅料查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('辅料新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('辅料修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('辅料删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('辅料导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:export',       '#', 'admin', sysdate(), '', null, '');