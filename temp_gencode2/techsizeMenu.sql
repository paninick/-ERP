-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书尺寸细节', '3', '1', 'techsize', 'erp/techsize/index', 1, 0, 'C', '0', '0', 'erp:techsize:list', '#', 'admin', sysdate(), '', null, '工艺书尺寸细节菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书尺寸细节查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:techsize:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书尺寸细节新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:techsize:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书尺寸细节修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:techsize:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书尺寸细节删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:techsize:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书尺寸细节导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:techsize:export',       '#', 'admin', sysdate(), '', null, '');