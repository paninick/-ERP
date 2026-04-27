-- ================================================================
-- AD-003 权限迁移脚本
-- 将新增 @PreAuthorize 权限字符串录入 sys_menu
-- 日期: 2026-04-27
-- 权限来源: 3个 Controller (ErpPushDown + FlowableDefinition + FlowableTodo)
--           2个只读 Controller (FlowableFinished + FlowableMyProcess)
-- 唯一权限字符串: 15个
-- ================================================================

-- C级容器菜单 (parent_id=1=系统管理, visible=0=显示, status=0=正常)
insert into sys_menu values('1070', '下推库存管理', '1', '8', '#',  '', '', '', 1, 0, 'C', '0', '0', '',           'server',    'admin', sysdate(), '', null, 'AD-003: ErpPushDownController 权限容器');
insert into sys_menu values('1071', '流程定义管理', '1', '9', '#',  '', '', '', 1, 0, 'C', '0', '0', '',           'component', 'admin', sysdate(), '', null, 'AD-003: FlowableDefinitionController 权限容器');
insert into sys_menu values('1072', '流程任务管理', '1', '10', '#', '', '', '', 1, 0, 'C', '0', '0', '',           'checkbox',  'admin', sysdate(), '', null, 'AD-003: FlowableTodoController 权限容器');

-- F级权限: erp:pushdown:* (5条) — parent: 1070 下推库存管理
insert into sys_menu values('1075', '下推库存查询', '1070', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:pushdown:list',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1076', '下推库存详情', '1070', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:pushdown:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1077', '下推库存新增', '1070', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:pushdown:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1078', '下推库存修改', '1070', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:pushdown:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1079', '下推库存删除', '1070', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:pushdown:remove', '#', 'admin', sysdate(), '', null, '');

-- F级权限: flowable:definition:* (6条) — parent: 1071 流程定义管理
insert into sys_menu values('1080', '流程定义查询', '1071', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:list',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1081', '流程定义详情', '1071', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1082', '流程定义新增', '1071', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1083', '流程定义修改', '1071', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1084', '流程定义删除', '1071', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1085', '流程定义导出', '1071', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:export', '#', 'admin', sysdate(), '', null, '');

-- F级权限: flowable:task:* (4条) — parent: 1072 流程任务管理
insert into sys_menu values('1086', '流程任务查询', '1072', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:list',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1087', '流程任务详情', '1072', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1088', '流程任务审批', '1072', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:approve', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1089', '流程任务驳回', '1072', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'flowable:task:reject',  '#', 'admin', sysdate(), '', null, '');

-- ================================================================
-- 权限汇总:
-- erp:pushdown:* (5): list/query/add/edit/remove
-- flowable:definition:* (6): list/query/add/edit/remove/export
-- flowable:task:* (4): list/query/approve/reject
-- 总计: 15个唯一权限字符串 + 3个容器菜单 = 18条 INSERT
-- ================================================================
