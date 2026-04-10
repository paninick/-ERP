-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('生产计划衣服明细', '3', '1', 'planclothes', 'erp/planclothes/index', 1, 0, 'C', '0', '0', 'erp:planclothes:list', '#', 'admin', sysdate(), '', null, '生产计划衣服明细菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('生产计划衣服明细查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'erp:planclothes:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('生产计划衣服明细新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'erp:planclothes:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('生产计划衣服明细修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'erp:planclothes:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('生产计划衣服明细删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'erp:planclothes:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('生产计划衣服明细导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'erp:planclothes:export',       '#', 'admin', sysdate(), '', null, '');