-- phase16: ERP 菜单全量注册
-- 依赖: phase15_flexible_schedule.sql 已执行
-- 执行顺序: 第17步
-- 说明: 使用 INSERT IGNORE，可重复执行，不影响已有数据
--       menu_id 从 2000 开始，避免与若依框架（1-1999）冲突
--       ERP 顶级目录 menu_id=2000，parent_id=0

SET NAMES utf8mb4;

-- ============================================================
-- 1. ERP 顶级目录
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES (2000, '服装ERP', 0, 5, 'erp', NULL, '', 1, 0, 'M', '0', '0', '', 'ep-goods', 'admin', NOW(), '', NULL, '服装ERP系统');

-- ============================================================
-- 2. 二级目录
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2001, '基础数据',   2000, 1,  'base',       NULL, '', 1, 0, 'M', '0', '0', '', 'ep-setting',    'admin', NOW(), '', NULL, ''),
(2002, '销售管理',   2000, 2,  'sale',       NULL, '', 1, 0, 'M', '0', '0', '', 'ep-shopping-cart', 'admin', NOW(), '', NULL, ''),
(2003, '生产管理',   2000, 3,  'produce',    NULL, '', 1, 0, 'M', '0', '0', '', 'ep-cpu',        'admin', NOW(), '', NULL, ''),
(2004, '质量管理',   2000, 4,  'quality',    NULL, '', 1, 0, 'M', '0', '0', '', 'ep-finished',   'admin', NOW(), '', NULL, ''),
(2005, '库存管理',   2000, 5,  'inventory',  NULL, '', 1, 0, 'M', '0', '0', '', 'ep-box',        'admin', NOW(), '', NULL, ''),
(2006, '财务管理',   2000, 6,  'finance',    NULL, '', 1, 0, 'M', '0', '0', '', 'ep-money',      'admin', NOW(), '', NULL, ''),
(2007, '报表中心',   2000, 7,  'report',     NULL, '', 1, 0, 'M', '0', '0', '', 'ep-data-line',  'admin', NOW(), '', NULL, '');

-- ============================================================
-- 2.1 报表中心子页（parent=2007）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2084, '报表总览', 2007, 1, 'reportHub', 'erp/report/index', '', 1, 0, 'C', '0', '0', 'erp:report:list', 'ep-data-analysis', 'admin', NOW(), '', NULL, '报表中心入口页');

-- ============================================================
-- 3. 基础数据（parent=2001）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2010, '客户管理',       2001, 1,  'customer',         'erp/customer/index',         '', 1, 0, 'C', '0', '0', 'erp:customer:list',         'ep-user',    'admin', NOW(), '', NULL, ''),
(2011, '供应商管理',     2001, 2,  'supplier',         'erp/supplier/index',         '', 1, 0, 'C', '0', '0', 'erp:supplier:list',         'ep-office-building', 'admin', NOW(), '', NULL, ''),
(2012, '员工档案',       2001, 3,  'employee',         'erp/employee/index',         '', 1, 0, 'C', '0', '0', 'erp:employee:list',         'ep-avatar',  'admin', NOW(), '', NULL, ''),
(2013, '物料管理',       2001, 4,  'material',         'erp/material/index',         '', 1, 0, 'C', '0', '0', 'erp:material:list',         'ep-files',   'admin', NOW(), '', NULL, ''),
(2014, '辅料管理',       2001, 5,  'auxiliary',        'erp/auxiliary/index',        '', 1, 0, 'C', '0', '0', 'erp:auxiliary:list',        'ep-paperclip','admin', NOW(), '', NULL, ''),
(2015, '仓库管理',       2001, 6,  'warehouse',        'erp/warehouse/index',        '', 1, 0, 'C', '0', '0', 'erp:warehouse:list',        'ep-house',   'admin', NOW(), '', NULL, ''),
(2016, '工序定义',       2001, 7,  'processDef',       'erp/processDef/index',       '', 1, 0, 'C', '0', '0', 'erp:processDef:list',       'ep-list',    'admin', NOW(), '', NULL, ''),
(2017, '工艺路线',       2001, 8,  'processRoute',     'erp/processRoute/index',     '', 1, 0, 'C', '0', '0', 'erp:processRoute:list',     'ep-connection','admin', NOW(), '', NULL, ''),
(2018, 'BOM管理',        2001, 9,  'bom',              'erp/bom/index',              '', 1, 0, 'C', '0', '0', 'erp:bom:list',              'ep-document', 'admin', NOW(), '', NULL, ''),
(2019, '标准色管理',     2001, 10, 'standardColor',    'erp/standardColor/index',    '', 1, 0, 'C', '0', '0', 'erp:standardColor:list',    'ep-brush',   'admin', NOW(), '', NULL, ''),
(2020, '单位换算',       2001, 11, 'unitConversion',   'erp/unitConversion/index',   '', 1, 0, 'C', '0', '0', 'erp:unitConversion:list',   'ep-scale',   'admin', NOW(), '', NULL, ''),
(2021, '损耗矩阵',       2001, 12, 'processLossMatrix','erp/processLossMatrix/index','', 1, 0, 'C', '0', '0', 'erp:processLossMatrix:list','ep-grid',    'admin', NOW(), '', NULL, '');

-- ============================================================
-- 4. 销售管理（parent=2002）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2030, '销售订单',   2002, 1, 'sales',       'erp/sales/index',       '', 1, 0, 'C', '0', '0', 'erp:sales:list',       'ep-document-checked', 'admin', NOW(), '', NULL, ''),
(2031, '打样通知',   2002, 2, 'notice',      'erp/notice/index',      '', 1, 0, 'C', '0', '0', 'erp:notice:list',      'ep-bell',             'admin', NOW(), '', NULL, ''),
(2032, '工艺指示书', 2002, 3, 'tech',        'erp/tech/index',        '', 1, 0, 'C', '0', '0', 'erp:tech:list',        'ep-reading',          'admin', NOW(), '', NULL, ''),
(2033, '联系人管理', 2002, 4, 'contacts',    'erp/contacts/index',    '', 1, 0, 'C', '0', '0', 'erp:contacts:list',    'ep-phone',            'admin', NOW(), '', NULL, ''),
(2034, '发票管理',   2002, 5, 'invoice',     'erp/invoice/index',     '', 1, 0, 'C', '0', '0', 'erp:invoice:list',     'ep-tickets',          'admin', NOW(), '', NULL, '');

-- ============================================================
-- 5. 生产管理（parent=2003）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2040, '生产计划',     2003, 1,  'plan',              'erp/plan/index',              '', 1, 0, 'C', '0', '0', 'erp:plan:list',              'ep-calendar',   'admin', NOW(), '', NULL, ''),
(2041, '生产工单',     2003, 2,  'produceJob',        'erp/produceJob/index',        '', 1, 0, 'C', '0', '0', 'erp:produceJob:list',        'ep-tickets',    'admin', NOW(), '', NULL, ''),
(2042, '工序流转',     2003, 3,  'produceJobProcess', 'erp/produceJobProcess/index', '', 1, 0, 'C', '0', '0', 'erp:produceJobProcess:list', 'ep-sort',       'admin', NOW(), '', NULL, ''),
(2043, '外协管理',     2003, 4,  'outsource',         'erp/outsource/index',         '', 1, 0, 'C', '0', '0', 'erp:outsource:list',         'ep-share',      'admin', NOW(), '', NULL, ''),
(2044, '物料消耗',     2003, 5,  'materialconsume',   'erp/materialconsume/index',   '', 1, 0, 'C', '0', '0', 'erp:materialconsume:list',   'ep-data-board', 'admin', NOW(), '', NULL, ''),
(2045, '损耗管控',     2003, 6,  'losscontrol',       'erp/losscontrol/index',       '', 1, 0, 'C', '0', '0', 'erp:materialconsume:list',   'ep-warning',    'admin', NOW(), '', NULL, ''),
(2046, '柔性排单',     2003, 7,  'producegantt',      'erp/producegantt/index',      '', 1, 0, 'C', '0', '0', 'erp:producegantt:list',      'ep-grid',       'admin', NOW(), '', NULL, ''),
(2047, '生产看板',     2003, 8,  'produceboard',      'erp/produceboard/index',      '', 1, 0, 'C', '0', '0', 'erp:produceboard:query',     'ep-monitor',    'admin', NOW(), '', NULL, ''),
(2048, '产品序列号',   2003, 9,  'productSerial',     'erp/productSerial/index',     '', 1, 0, 'C', '0', '0', 'erp:productSerial:list',     'ep-barcode',    'admin', NOW(), '', NULL, ''),
(2049, '业务异常池',   2003, 10, 'bizabnormal',       'erp/bizabnormal/index',       '', 1, 0, 'C', '0', '0', 'erp:bizabnormal:list',       'ep-warning-filled','admin', NOW(), '', NULL, '');

-- ============================================================
-- 6. 质量管理（parent=2004）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2060, '质检管理', 2004, 1, 'check',  'erp/check/index',  '', 1, 0, 'C', '0', '0', 'erp:check:list',  'ep-finished', 'admin', NOW(), '', NULL, ''),
(2061, '次品管理', 2004, 2, 'defect', 'erp/defect/index', '', 1, 0, 'C', '0', '0', 'erp:defect:list', 'ep-close-bold','admin', NOW(), '', NULL, '');

-- ============================================================
-- 7. 库存管理（parent=2005）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2070, '库存查询',   2005, 1, 'stock',            'erp/stock/index',            '', 1, 0, 'C', '0', '0', 'erp:stock:list',            'ep-box',        'admin', NOW(), '', NULL, ''),
(2071, '入库管理',   2005, 2, 'stockin',          'erp/stockin/index',          '', 1, 0, 'C', '0', '0', 'erp:stockin:list',          'ep-download',   'admin', NOW(), '', NULL, ''),
(2072, '出库管理',   2005, 3, 'stockout',         'erp/stockout/index',         '', 1, 0, 'C', '0', '0', 'erp:stockout:list',         'ep-upload',     'admin', NOW(), '', NULL, ''),
(2073, '库存盘点',   2005, 4, 'inventory',        'erp/inventory/index',        '', 1, 0, 'C', '0', '0', 'erp:inventory:list',        'ep-checked',    'admin', NOW(), '', NULL, ''),
(2074, '仓库区域',   2005, 5, 'warehousearea',    'erp/warehousearea/index',    '', 1, 0, 'C', '0', '0', 'erp:warehousearea:list',    'ep-map-location','admin', NOW(), '', NULL, ''),
(2075, '库位管理',   2005, 6, 'warehouselocation','erp/warehouselocation/index','', 1, 0, 'C', '0', '0', 'erp:warehouselocation:list','ep-location',   'admin', NOW(), '', NULL, '');

-- 避免与父级目录 inventory 生成重复路由名 Inventory
UPDATE sys_menu
SET route_name = 'InventoryCheck', update_by = 'codex', update_time = NOW()
WHERE menu_id = 2073;

-- ============================================================
-- 8. 财务管理（parent=2006）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2080, '计件工资',   2006, 1, 'piecewage',       'erp/piecewage/index',       '', 1, 0, 'C', '0', '0', 'erp:piecewage:list',       'ep-coin',       'admin', NOW(), '', NULL, ''),
(2081, '工资明细',   2006, 2, 'piecewagedetail', 'erp/piecewagedetail/index', '', 1, 0, 'C', '0', '0', 'erp:piecewagedetail:list', 'ep-document',   'admin', NOW(), '', NULL, ''),
(2082, '成本核算',   2006, 3, 'cost',            'erp/cost/index',            '', 1, 0, 'C', '0', '0', 'erp:cost:list',            'ep-data-analysis','admin', NOW(), '', NULL, ''),
(2083, '采购管理',   2006, 4, 'purchase',        'erp/purchase/index',        '', 1, 0, 'C', '0', '0', 'erp:purchase:list',        'ep-shopping-bag','admin', NOW(), '', NULL, '');

-- ============================================================
-- 9. 损耗管控按钮权限（parent=2045）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2450, '查询', 2045, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:list',   '#', 'admin', NOW(), '', NULL, ''),
(2451, '审批', 2045, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:edit',   '#', 'admin', NOW(), '', NULL, ''),
(2452, '导出', 2045, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:export', '#', 'admin', NOW(), '', NULL, '');

-- ============================================================
-- 10. 柔性排单按钮权限（parent=2046）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2460, '查询',     2046, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:producegantt:list',   '#', 'admin', NOW(), '', NULL, ''),
(2461, '重新排期', 2046, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:producegantt:edit',   '#', 'admin', NOW(), '', NULL, ''),
(2462, '冲突检测', 2046, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:producegantt:detect', '#', 'admin', NOW(), '', NULL, '');

-- ============================================================
-- 11. 物料消耗按钮权限（parent=2044）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2440, '查询', 2044, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:list',   '#', 'admin', NOW(), '', NULL, ''),
(2441, '新增', 2044, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:add',    '#', 'admin', NOW(), '', NULL, ''),
(2442, '修改', 2044, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:edit',   '#', 'admin', NOW(), '', NULL, ''),
(2443, '删除', 2044, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:remove', '#', 'admin', NOW(), '', NULL, ''),
(2444, '导出', 2044, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:materialconsume:export', '#', 'admin', NOW(), '', NULL, '');

-- ============================================================
-- 12. 将新菜单授权给超级管理员角色（role_id=1）
-- ============================================================
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu
WHERE menu_id BETWEEN 2000 AND 2999;

-- ============================================================
-- 13. 将损耗管控/柔性排单授权给生产主管（role_id=200，见 phase14）
-- ============================================================
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 200, menu_id FROM sys_menu
WHERE perms IN (
  'erp:producegantt:list','erp:producegantt:edit','erp:producegantt:detect',
  'erp:materialconsume:list','erp:materialconsume:query','erp:materialconsume:add',
  'erp:materialconsume:edit','erp:materialconsume:remove','erp:materialconsume:export'
);

-- 同时授权目录菜单（无 perms 的目录节点）
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 200, menu_id FROM sys_menu WHERE menu_id IN (2000, 2003);

-- ============================================================
-- 14. ERP CRUD button permissions
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2512, '查询', 2012, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:query', '#', 'admin', NOW(), '', NULL, ''),
(2513, '新增', 2012, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:add', '#', 'admin', NOW(), '', NULL, ''),
(2514, '修改', 2012, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:edit', '#', 'admin', NOW(), '', NULL, ''),
(2515, '删除', 2012, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:remove', '#', 'admin', NOW(), '', NULL, ''),
(2516, '导出', 2012, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:employee:export', '#', 'admin', NOW(), '', NULL, ''),
(2517, '查询', 2016, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:query', '#', 'admin', NOW(), '', NULL, ''),
(2518, '新增', 2016, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:add', '#', 'admin', NOW(), '', NULL, ''),
(2519, '修改', 2016, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:edit', '#', 'admin', NOW(), '', NULL, ''),
(2520, '删除', 2016, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:remove', '#', 'admin', NOW(), '', NULL, ''),
(2521, '导出', 2016, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processDef:export', '#', 'admin', NOW(), '', NULL, ''),
(2522, '查询', 2019, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:query', '#', 'admin', NOW(), '', NULL, ''),
(2523, '新增', 2019, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:add', '#', 'admin', NOW(), '', NULL, ''),
(2524, '修改', 2019, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:edit', '#', 'admin', NOW(), '', NULL, ''),
(2525, '删除', 2019, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:remove', '#', 'admin', NOW(), '', NULL, ''),
(2526, '导出', 2019, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:standardColor:export', '#', 'admin', NOW(), '', NULL, ''),
(2527, '查询', 2021, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:query', '#', 'admin', NOW(), '', NULL, ''),
(2528, '新增', 2021, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:add', '#', 'admin', NOW(), '', NULL, ''),
(2529, '修改', 2021, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:edit', '#', 'admin', NOW(), '', NULL, ''),
(2530, '删除', 2021, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:remove', '#', 'admin', NOW(), '', NULL, ''),
(2531, '导出', 2021, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processLossMatrix:export', '#', 'admin', NOW(), '', NULL, ''),
(2532, '查询', 2048, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:query', '#', 'admin', NOW(), '', NULL, ''),
(2533, '新增', 2048, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:add', '#', 'admin', NOW(), '', NULL, ''),
(2534, '修改', 2048, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:edit', '#', 'admin', NOW(), '', NULL, ''),
(2535, '删除', 2048, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:remove', '#', 'admin', NOW(), '', NULL, ''),
(2536, '导出', 2048, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:productSerial:export', '#', 'admin', NOW(), '', NULL, ''),
(2537, '查询', 2080, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:query', '#', 'admin', NOW(), '', NULL, ''),
(2538, '新增', 2080, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:add', '#', 'admin', NOW(), '', NULL, ''),
(2539, '修改', 2080, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:edit', '#', 'admin', NOW(), '', NULL, ''),
(2540, '删除', 2080, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:remove', '#', 'admin', NOW(), '', NULL, ''),
(2541, '导出', 2080, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewage:export', '#', 'admin', NOW(), '', NULL, ''),
(2542, '查询', 2081, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:query', '#', 'admin', NOW(), '', NULL, ''),
(2543, '新增', 2081, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:add', '#', 'admin', NOW(), '', NULL, ''),
(2544, '修改', 2081, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:edit', '#', 'admin', NOW(), '', NULL, ''),
(2545, '删除', 2081, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:remove', '#', 'admin', NOW(), '', NULL, ''),
(2546, '导出', 2081, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:piecewagedetail:export', '#', 'admin', NOW(), '', NULL, ''),
(2547, '查询', 2073, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:query', '#', 'admin', NOW(), '', NULL, ''),
(2548, '新增', 2073, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:add', '#', 'admin', NOW(), '', NULL, ''),
(2549, '修改', 2073, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:edit', '#', 'admin', NOW(), '', NULL, ''),
(2550, '删除', 2073, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:inventory:remove', '#', 'admin', NOW(), '', NULL, '');

INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2555, '鏌ヨ', 2032, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:query', '#', 'admin', NOW(), '', NULL, ''),
(2556, '鏂板', 2032, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:add', '#', 'admin', NOW(), '', NULL, ''),
(2557, '淇敼', 2032, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:edit', '#', 'admin', NOW(), '', NULL, ''),
(2558, '鍒犻櫎', 2032, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:remove', '#', 'admin', NOW(), '', NULL, ''),
(2559, '瀵煎嚭', 2032, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:export', '#', 'admin', NOW(), '', NULL, ''),
(2560, '瀵煎叆', 2032, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:tech:import', '#', 'admin', NOW(), '', NULL, ''),
(2561, '鏌ヨ', 2071, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:query', '#', 'admin', NOW(), '', NULL, ''),
(2562, '鏂板', 2071, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:add', '#', 'admin', NOW(), '', NULL, ''),
(2563, '淇敼', 2071, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:edit', '#', 'admin', NOW(), '', NULL, ''),
(2564, '鍒犻櫎', 2071, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:remove', '#', 'admin', NOW(), '', NULL, ''),
(2565, '瀵煎嚭', 2071, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:export', '#', 'admin', NOW(), '', NULL, ''),
(2566, '瀵煎叆', 2071, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockin:import', '#', 'admin', NOW(), '', NULL, ''),
(2567, '鏌ヨ', 2072, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:query', '#', 'admin', NOW(), '', NULL, ''),
(2568, '鏂板', 2072, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:add', '#', 'admin', NOW(), '', NULL, ''),
(2569, '淇敼', 2072, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:edit', '#', 'admin', NOW(), '', NULL, ''),
(2570, '鍒犻櫎', 2072, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:remove', '#', 'admin', NOW(), '', NULL, ''),
(2571, '瀵煎嚭', 2072, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:stockout:export', '#', 'admin', NOW(), '', NULL, '');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id BETWEEN 2512 AND 2571;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 201, menu_id FROM sys_menu
WHERE perms IN ('erp:tech:list', 'erp:tech:query', 'erp:tech:add', 'erp:tech:edit', 'erp:tech:remove', 'erp:tech:export', 'erp:tech:import');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 202, menu_id FROM sys_menu
WHERE perms IN ('erp:tech:list', 'erp:tech:query');

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 204, menu_id FROM sys_menu
WHERE perms IN (
  'erp:stockin:list', 'erp:stockin:query', 'erp:stockin:add', 'erp:stockin:edit', 'erp:stockin:remove', 'erp:stockin:export', 'erp:stockin:import',
  'erp:stockout:list', 'erp:stockout:query', 'erp:stockout:add', 'erp:stockout:edit', 'erp:stockout:remove', 'erp:stockout:export'
);

-- ============================================================
-- 15. 新增缺失菜单（打样总览、质检工作台、工序工价、数据导入）
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2551, '打样总览',   2002, 6,  'overview',       'erp/overview/index',       '', 1, 0, 'C', '0', '0', 'erp:overview:list',       'ep-view',            'admin', NOW(), '', NULL, ''),
(2552, '质检工作台', 2004, 3,  'quality',        'erp/quality/index',        '', 1, 0, 'C', '0', '0', 'erp:qc:list',             'ep-finished',        'admin', NOW(), '', NULL, ''),
(2553, '工序工价',   2006, 5,  'processPrice',   'erp/processPrice/index',   '', 1, 0, 'C', '0', '0', 'erp:processPrice:list',   'ep-coin',            'admin', NOW(), '', NULL, ''),
(2554, '数据导入',   2001, 13, 'dataimport',     'erp/dataimport/index',     '', 1, 0, 'C', '0', '0', 'erp:dataimport:list',     'ep-upload',          'admin', NOW(), '', NULL, '');
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id IN (2551, 2552, 2553, 2554);

-- ============================================================
-- 16. 2026-04-24 backfill: missing ERP button perms and route alignment
-- ============================================================
INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES
(2572, '导入', 2014, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:auxiliary:import', '#', 'admin', NOW(), '', NULL, ''),
(2573, '新增', 2049, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:add', '#', 'admin', NOW(), '', NULL, ''),
(2574, '修改', 2049, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:edit', '#', 'admin', NOW(), '', NULL, ''),
(2575, '删除', 2049, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:remove', '#', 'admin', NOW(), '', NULL, ''),
(2576, '导出', 2049, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bizabnormal:export', '#', 'admin', NOW(), '', NULL, ''),
(2577, '新增', 2018, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:add', '#', 'admin', NOW(), '', NULL, ''),
(2578, '修改', 2018, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:edit', '#', 'admin', NOW(), '', NULL, ''),
(2579, '删除', 2018, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:remove', '#', 'admin', NOW(), '', NULL, ''),
(2580, '导出', 2018, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:export', '#', 'admin', NOW(), '', NULL, ''),
(2581, '导入', 2018, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:bom:import', '#', 'admin', NOW(), '', NULL, ''),
(2582, '导入', 2010, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:customer:import', '#', 'admin', NOW(), '', NULL, ''),
(2583, '新增', 2061, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:add', '#', 'admin', NOW(), '', NULL, ''),
(2584, '修改', 2061, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:edit', '#', 'admin', NOW(), '', NULL, ''),
(2585, '删除', 2061, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:remove', '#', 'admin', NOW(), '', NULL, ''),
(2586, '导出', 2061, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:defect:export', '#', 'admin', NOW(), '', NULL, ''),
(2587, '导入', 2013, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:material:import', '#', 'admin', NOW(), '', NULL, ''),
(2588, '导入', 2031, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:notice:import', '#', 'admin', NOW(), '', NULL, ''),
(2589, '新增', 2043, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:add', '#', 'admin', NOW(), '', NULL, ''),
(2590, '修改', 2043, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:edit', '#', 'admin', NOW(), '', NULL, ''),
(2591, '删除', 2043, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:remove', '#', 'admin', NOW(), '', NULL, ''),
(2592, '导出', 2043, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:outsource:export', '#', 'admin', NOW(), '', NULL, ''),
(2593, '查询', 2553, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:query', '#', 'admin', NOW(), '', NULL, ''),
(2594, '新增', 2553, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:add', '#', 'admin', NOW(), '', NULL, ''),
(2595, '修改', 2553, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:edit', '#', 'admin', NOW(), '', NULL, ''),
(2596, '删除', 2553, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:remove', '#', 'admin', NOW(), '', NULL, ''),
(2597, '导出', 2553, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processPrice:export', '#', 'admin', NOW(), '', NULL, ''),
(2598, '新增', 2017, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:add', '#', 'admin', NOW(), '', NULL, ''),
(2599, '修改', 2017, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:edit', '#', 'admin', NOW(), '', NULL, ''),
(2600, '删除', 2017, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:remove', '#', 'admin', NOW(), '', NULL, ''),
(2601, '导出', 2017, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:processRoute:export', '#', 'admin', NOW(), '', NULL, ''),
(2602, '新增', 2041, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:add', '#', 'admin', NOW(), '', NULL, ''),
(2603, '修改', 2041, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:edit', '#', 'admin', NOW(), '', NULL, ''),
(2604, '删除', 2041, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:remove', '#', 'admin', NOW(), '', NULL, ''),
(2605, '导出', 2041, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJob:export', '#', 'admin', NOW(), '', NULL, ''),
(2606, '新增', 2042, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:add', '#', 'admin', NOW(), '', NULL, ''),
(2607, '修改', 2042, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:edit', '#', 'admin', NOW(), '', NULL, ''),
(2608, '删除', 2042, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:remove', '#', 'admin', NOW(), '', NULL, ''),
(2609, '导出', 2042, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:produceJobProcess:export', '#', 'admin', NOW(), '', NULL, ''),
(2610, '导入', 2011, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:supplier:import', '#', 'admin', NOW(), '', NULL, ''),
(2611, '新增', 2020, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:add', '#', 'admin', NOW(), '', NULL, ''),
(2612, '修改', 2020, 3, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:edit', '#', 'admin', NOW(), '', NULL, ''),
(2613, '删除', 2020, 4, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:remove', '#', 'admin', NOW(), '', NULL, ''),
(2614, '导出', 2020, 5, '#', '', '', 1, 0, 'F', '0', '0', 'erp:unitConversion:export', '#', 'admin', NOW(), '', NULL, ''),
(2615, '导入', 2015, 6, '#', '', '', 1, 0, 'F', '0', '0', 'erp:warehouse:import', '#', 'admin', NOW(), '', NULL, ''),
(2616, '查询', 2552, 1, '#', '', '', 1, 0, 'F', '0', '0', 'erp:qc:query', '#', 'admin', NOW(), '', NULL, ''),
(2617, '修改', 2552, 2, '#', '', '', 1, 0, 'F', '0', '0', 'erp:qc:edit', '#', 'admin', NOW(), '', NULL, '');

UPDATE sys_menu
SET menu_name = '打样总览',
    parent_id = 2002,
    order_num = 6,
    path = 'overview',
    component = 'erp/overview/index',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:overview:list',
    icon = 'ep-view',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2551;

UPDATE sys_menu
SET menu_name = '质检工作台',
    parent_id = 2004,
    order_num = 3,
    path = 'quality',
    component = 'erp/quality/index',
    route_name = 'QualityWorkbench',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:qc:list',
    icon = 'ep-finished',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2552;

UPDATE sys_menu
SET menu_name = '工序工价',
    parent_id = 2006,
    order_num = 5,
    path = 'processPrice',
    component = 'erp/processPrice/index',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:processPrice:list',
    icon = 'ep-coin',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2553;

UPDATE sys_menu
SET menu_name = '数据导入',
    parent_id = 2001,
    order_num = 13,
    path = 'dataimport',
    component = 'erp/dataimport/index',
    menu_type = 'C',
    visible = '0',
    status = '0',
    perms = 'erp:dataimport:list',
    icon = 'ep-upload',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 2554;

UPDATE sys_menu
SET visible = '1',
    status = '1',
    update_by = 'codex',
    update_time = NOW()
WHERE menu_id = 4449;

DELETE FROM sys_role_menu WHERE menu_id = 4449;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id
FROM sys_menu
WHERE menu_id BETWEEN 2551 AND 2617;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2551
FROM sys_role_menu
WHERE menu_id = 2031;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2552
FROM sys_role_menu
WHERE menu_id = 2060;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2616
FROM sys_role_menu
WHERE menu_id = 2060;

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT DISTINCT role_id, 2617
FROM sys_role_menu
WHERE menu_id = 2060;
