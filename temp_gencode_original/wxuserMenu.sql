-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户', '3', '1', 'wxuser', 'wx/wxuser/index', 1, 0, 'C', '0', '0', 'wx:wxuser:list', '#', 'admin', sysdate(), '', null, '微信用户菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'wx:wxuser:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'wx:wxuser:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'wx:wxuser:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'wx:wxuser:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('微信用户导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'wx:wxuser:export',       '#', 'admin', sysdate(), '', null, '');