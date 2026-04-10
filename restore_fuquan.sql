-- 恢复富泉工贸架构数据
USE `ry-vue`;

-- 删除旧的部门数据
DELETE FROM sys_dept WHERE dept_id >= 1;

-- 插入富泉工贸部门数据
INSERT INTO sys_dept VALUES(100, 0, '0', '富泉工贸', 1, '管理员', '15888888888', 'admin@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(101, 100, '0,100', '技术支持', 2, '技术支持', '15888888888', 'tech@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(102, 101, '0,100,101', '研发部门', 1, '研发部门', '15888888888', 'dev@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(103, 101, '0,100,101', '测试部门', 2, '测试部门', '15888888888', 'test@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(104, 100, '0,100', '公司领导', 3, '公司领导', '15888888888', 'leader@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(105, 100, '0,100', '行政部', 4, '行政部', '15888888888', 'admin@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(106, 100, '0,100', '业务部', 5, '业务部', '15888888888', 'business@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(107, 100, '0,100', '采购部', 6, '采购部', '15888888888', 'purchase@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(108, 100, '0,100', '生产部', 7, '生产部', '15888888888', 'production@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(109, 108, '0,100,108', '服装生产部', 1, '服装生产部', '15888888888', 'clothing@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(110, 108, '0,100,108', '毛衣生产部', 2, '毛衣生产部', '15888888888', 'sweater@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(111, 100, '0,100', '技术部', 8, '技术部', '15888888888', 'tech@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(112, 111, '0,100,111', '服装技术部', 1, '服装技术部', '15888888888', 'clothing_tech@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(113, 111, '0,100,111', '毛衣技术部', 2, '毛衣技术部', '15888888888', 'sweater_tech@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(114, 100, '0,100', '前道车间', 9, '前道车间', '15888888888', 'pre_workshop@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(115, 100, '0,100', '后道车间', 10, '后道车间', '15888888888', 'post_workshop@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(116, 100, '0,100', '仓库', 11, '仓库', '15888888888', 'warehouse@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(117, 100, '0,100', '财务部', 12, '财务部', '15888888888', 'finance@fuquan.com', '0', '0', 'admin', sysdate(), '', null);
INSERT INTO sys_dept VALUES(118, 100, '0,100', '单证', 13, '单证', '15888888888', 'document@fuquan.com', '0', '0', 'admin', sysdate(), '', null);

-- 删除旧的角色数据
DELETE FROM sys_role WHERE role_id >= 1;

-- 插入富泉工贸角色数据
INSERT INTO sys_role VALUES(1, '超级管理员', 'admin', 1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '超级管理员');
INSERT INTO sys_role VALUES(100, '系统管理员', 'sys_admin', 2, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '系统管理员');
INSERT INTO sys_role VALUES(2, '测试角色', 'test', 3, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '测试角色');
INSERT INTO sys_role VALUES(101, '业务部', 'business', 4, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '业务部');
INSERT INTO sys_role VALUES(104, '采购部', 'purchase', 5, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '采购部');
INSERT INTO sys_role VALUES(116, '采购部-面料', 'purchase_fabric', 6, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '采购部-面料');
INSERT INTO sys_role VALUES(117, '采购部-纱线', 'purchase_yarn', 7, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '采购部-纱线');
INSERT INTO sys_role VALUES(118, '采购部-辅料', 'purchase_accessory', 8, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '采购部-辅料');
INSERT INTO sys_role VALUES(102, '技术部', 'tech', 9, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '技术部');
INSERT INTO sys_role VALUES(105, '技术部-服饰', 'tech_clothing', 10, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '技术部-服饰');

-- 删除旧的菜单数据
DELETE FROM sys_menu WHERE menu_id >= 1;

-- 插入富泉工贸菜单数据
-- 一级菜单
INSERT INTO sys_menu VALUES('1', '报表统计', '0', '1', 'report', null, '', '', 1, 0, 'M', '0', '0', '', 'chart', 'admin', sysdate(), '', null, '报表统计目录');
INSERT INTO sys_menu VALUES('2', '系统管理', '0', '10', 'system', null, '', '', 1, 0, 'M', '0', '0', '', 'system', 'admin', sysdate(), '', null, '系统管理目录');
INSERT INTO sys_menu VALUES('3', '系统监控', '0', '20', 'monitor', null, '', '', 1, 0, 'M', '0', '0', '', 'monitor', 'admin', sysdate(), '', null, '系统监控目录');
INSERT INTO sys_menu VALUES('4', '系统工具', '0', '30', 'tool', null, '', '', 1, 0, 'M', '0', '0', '', 'tool', 'admin', sysdate(), '', null, '系统工具目录');
INSERT INTO sys_menu VALUES('5', '流程管理', '0', '60', 'process', null, '', '', 1, 0, 'M', '0', '0', '', 'flow', 'admin', sysdate(), '', null, '流程管理目录');
INSERT INTO sys_menu VALUES('6', '任务管理', '0', '70', 'task', null, '', '', 1, 0, 'M', '0', '0', '', 'task', 'admin', sysdate(), '', null, '任务管理目录');
INSERT INTO sys_menu VALUES('7', '基础管理', '0', '100', 'basic', null, '', '', 1, 0, 'M', '0', '0', '', 'building', 'admin', sysdate(), '', null, '基础管理目录');
INSERT INTO sys_menu VALUES('8', '业务系统', '0', '110', 'business', null, '', '', 1, 0, 'M', '0', '0', '', 'shopping', 'admin', sysdate(), '', null, '业务系统目录');
INSERT INTO sys_menu VALUES('9', '库存管理', '0', '120', 'inventory', null, '', '', 1, 0, 'M', '0', '0', '', 'box', 'admin', sysdate(), '', null, '库存管理目录');

-- 二级菜单 - 系统管理
INSERT INTO sys_menu VALUES('100', '用户管理', '2', '1', 'user', 'system/user/index', '', '', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', sysdate(), '', null, '用户管理菜单');
INSERT INTO sys_menu VALUES('101', '角色管理', '2', '2', 'role', 'system/role/index', '', '', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', sysdate(), '', null, '角色管理菜单');
INSERT INTO sys_menu VALUES('102', '菜单管理', '2', '3', 'menu', 'system/menu/index', '', '', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', sysdate(), '', null, '菜单管理菜单');
INSERT INTO sys_menu VALUES('103', '部门管理', '2', '4', 'dept', 'system/dept/index', '', '', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', sysdate(), '', null, '部门管理菜单');

-- 验证数据
SELECT '部门数据' AS type, COUNT(*) AS count FROM sys_dept;
SELECT '角色数据' AS type, COUNT(*) AS count FROM sys_role;
SELECT '菜单数据' AS type, COUNT(*) AS count FROM sys_menu;
