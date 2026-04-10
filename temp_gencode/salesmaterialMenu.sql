-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('大货订单物料明细', '0', '1', 'salesmaterial', 'erp/salesmaterial/index', 1, 0, 'C', '0', '0', 'erp:salesmaterial:list', '#', 'admin', sysdate(), '', null, '大货订单物料明细菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('大货订单物料明细查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesmaterial:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('大货订单物料明细新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesmaterial:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('大货订单物料明细修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesmaterial:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('大货订单物料明细删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesmaterial:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('大货订单物料明细导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:salesmaterial:export',       '#', 'admin', sysdate(), '', null, '');