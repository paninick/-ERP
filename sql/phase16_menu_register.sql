-- phase16: ERP 菜单全量注册
-- 依赖: phase15_flexible_schedule.sql 已执行
-- 执行顺序: 第17步
-- 说明: 使用 INSERT IGNORE，可重复执行，不影响已有数据
--       menu_id 从 2000 开始，避免与若依框架（1-1999）冲突
--       ERP 顶级目录 menu_id=2000，parent_id=0

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
-- 13. 将损耗管控/柔性排单授权给生产主管（role_id=100）
-- ============================================================
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 100, menu_id FROM sys_menu
WHERE perms IN (
  'erp:producegantt:list','erp:producegantt:edit','erp:producegantt:detect',
  'erp:materialconsume:list','erp:materialconsume:query','erp:materialconsume:add',
  'erp:materialconsume:edit','erp:materialconsume:remove','erp:materialconsume:export'
);

-- 同时授权目录菜单（无 perms 的目录节点）
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 100, menu_id FROM sys_menu WHERE menu_id IN (2000, 2003);
