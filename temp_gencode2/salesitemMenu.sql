-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('销售订单明细', '3', '1', 'salesitem', 'erp/salesitem/index', 1, 0, 'C', '0', '0', 'erp:salesitem:list', '#', 'admin', sysdate(), '', null, '销售订单明细菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('销售订单明细查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesitem:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('销售订单明细新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesitem:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('销售订单明细修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesitem:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('销售订单明细删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesitem:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('销售订单明细导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesitem:export',       '#', 'admin', sysdate(), '', null, '');