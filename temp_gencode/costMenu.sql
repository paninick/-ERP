-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书单耗', '3', '1', 'cost', 'erp/cost/index', 1, 0, 'C', '0', '0', 'erp:cost:list', '#', 'admin', sysdate(), '', null, '工艺书单耗菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书单耗查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:cost:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书单耗新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:cost:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书单耗修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:cost:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书单耗删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:cost:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('工艺书单耗导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:cost:export',       '#', 'admin', sysdate(), '', null, '');