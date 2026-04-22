-- phase14: ERP RBAC 岗位权限矩阵
-- 依赖: phase13_p1_splice_dye_threshold.sql 已执行
-- 执行顺序: 第16步（在 sys_automation.sql 之前）
--
-- 岗位角色设计：
--   role_id 100: 生产主管  - 生产全链路读写，不含财务/人事
--   role_id 101: 技术员    - 工艺/BOM/工序只读+编辑，不含财务/销售
--   role_id 102: 销售员    - 销售/客户/打样通知读写，不含生产内部/财务
--   role_id 103: 财务      - 计件工资/成本/发票读写，其余只读
--   role_id 104: 仓库管理员 - 库存/出入库读写，其余只读

-- ============================================================
-- 1. 角色定义
-- ============================================================
INSERT IGNORE INTO sys_role (role_id, role_name, role_key, role_sort, data_scope, menu_check_strictly, dept_check_strictly, status, del_flag, create_by, create_time, remark)
VALUES
(100, '生产主管',   'erp_produce_manager', 10, '1', 1, 1, '0', '0', 'admin', NOW(), 'ERP生产主管，生产全链路管理'),
(101, '技术员',     'erp_technician',      11, '1', 1, 1, '0', '0', 'admin', NOW(), 'ERP技术员，工艺/BOM/工序管理'),
(102, '销售员',     'erp_salesperson',     12, '1', 1, 1, '0', '0', 'admin', NOW(), 'ERP销售员，销售订单与客户管理'),
(103, '财务',       'erp_finance',         13, '1', 1, 1, '0', '0', 'admin', NOW(), 'ERP财务，工资/成本/发票管理'),
(104, '仓库管理员', 'erp_warehouse_admin', 14, '1', 1, 1, '0', '0', 'admin', NOW(), 'ERP仓库管理员，库存出入库管理');

-- ============================================================
-- 2. 权限字符串表（sys_menu perms 字段对应）
--    通过 sys_role_menu 关联，需先确认 menu_id
--    若依框架中 perms 字段即为权限标识，角色通过 sys_role_menu 关联菜单
--    此处改为直接插入 sys_role_menu（需要 menu_id）
--    由于 menu_id 在不同环境可能不同，改用存储过程动态关联
-- ============================================================

-- 动态关联：生产主管（role_id=100）
-- 拥有权限：plan/produceJob/produceJobProcess/defect/outsource/piecewage/piecewagedetail/
--           materialconsume/produceboard/producegantt/check/bizabnormal/productSerial
--           + 基础数据只读（customer/supplier/material/warehouse/employee）
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 100, menu_id FROM sys_menu
WHERE perms IN (
  -- 生产计划
  'erp:plan:list','erp:plan:query','erp:plan:add','erp:plan:edit','erp:plan:remove','erp:plan:export',
  'erp:planclothes:list','erp:planclothes:query','erp:planclothes:add','erp:planclothes:edit','erp:planclothes:remove',
  'erp:planmaterial:list','erp:planmaterial:query','erp:planmaterial:add','erp:planmaterial:edit','erp:planmaterial:remove',
  -- 生产工单
  'erp:produceJob:list','erp:produceJob:query','erp:produceJob:add','erp:produceJob:edit','erp:produceJob:remove','erp:produceJob:export',
  'erp:produceJobProcess:list','erp:produceJobProcess:query','erp:produceJobProcess:add','erp:produceJobProcess:edit','erp:produceJobProcess:remove',
  -- 次品管理
  'erp:defect:list','erp:defect:query','erp:defect:add','erp:defect:edit','erp:defect:remove','erp:defect:export',
  -- 外协
  'erp:outsource:list','erp:outsource:query','erp:outsource:add','erp:outsource:edit','erp:outsource:remove','erp:outsource:export',
  -- 物料消耗
  'erp:materialconsume:list','erp:materialconsume:query','erp:materialconsume:add','erp:materialconsume:edit','erp:materialconsume:remove',
  -- 计件工资（只读）
  'erp:piecewage:list','erp:piecewage:query',
  'erp:piecewagedetail:list','erp:piecewagedetail:query',
  -- 生产看板/甘特
  'erp:produceboard:query','erp:producegantt:list','erp:producegantt:edit',
  -- 质检
  'erp:check:list','erp:check:query','erp:check:add','erp:check:edit','erp:check:approve',
  -- 异常池
  'erp:bizabnormal:list','erp:bizabnormal:query','erp:bizabnormal:add','erp:bizabnormal:edit',
  -- 产品序列号
  'erp:productSerial:list','erp:productSerial:query','erp:productSerial:add',
  -- 基础数据只读
  'erp:customer:list','erp:customer:query',
  'erp:supplier:list','erp:supplier:query',
  'erp:material:list','erp:material:query',
  'erp:employee:list','erp:employee:query',
  'erp:warehouse:list','erp:warehouse:query',
  'erp:processDef:list','erp:processDef:query',
  'erp:processRoute:list','erp:processRoute:query',
  'erp:bom:list','erp:bom:query'
);

-- 技术员（role_id=101）
-- 拥有权限：tech/techmaterial/techsize/processDef/processRoute/processRouteItem/
--           processLossMatrix/bom/standardColor/check + 基础数据只读
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 101, menu_id FROM sys_menu
WHERE perms IN (
  -- 工艺指示书
  'erp:tech:list','erp:tech:query','erp:tech:add','erp:tech:edit','erp:tech:remove','erp:tech:export','erp:tech:import',
  'erp:techmaterial:list','erp:techmaterial:query','erp:techmaterial:add','erp:techmaterial:edit','erp:techmaterial:remove',
  'erp:techsize:list','erp:techsize:query','erp:techsize:add','erp:techsize:edit','erp:techsize:remove',
  -- 工序定义
  'erp:processDef:list','erp:processDef:query','erp:processDef:add','erp:processDef:edit','erp:processDef:remove',
  -- 工艺路线
  'erp:processRoute:list','erp:processRoute:query','erp:processRoute:add','erp:processRoute:edit','erp:processRoute:remove',
  'erp:processRouteItem:list','erp:processRouteItem:query','erp:processRouteItem:add','erp:processRouteItem:edit','erp:processRouteItem:remove',
  -- 损耗矩阵
  'erp:processLossMatrix:list','erp:processLossMatrix:query','erp:processLossMatrix:add','erp:processLossMatrix:edit','erp:processLossMatrix:remove',
  -- BOM
  'erp:bom:list','erp:bom:query','erp:bom:add','erp:bom:edit','erp:bom:remove',
  -- 标准色
  'erp:standardColor:list','erp:standardColor:query','erp:standardColor:add','erp:standardColor:edit',
  -- 质检（只读）
  'erp:check:list','erp:check:query',
  -- 打样通知（只读）
  'erp:notice:list','erp:notice:query',
  -- 基础数据只读
  'erp:customer:list','erp:customer:query',
  'erp:material:list','erp:material:query',
  'erp:auxiliary:list','erp:auxiliary:query',
  'erp:unitConversion:list','erp:unitConversion:query'
);

-- 销售员（role_id=102）
-- 拥有权限：sales/salesitem/salesmaterial/customer/contacts/invoice/notice + 打样通知
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 102, menu_id FROM sys_menu
WHERE perms IN (
  -- 销售订单
  'erp:sales:list','erp:sales:query','erp:sales:add','erp:sales:edit','erp:sales:remove','erp:sales:export','erp:sales:import',
  'erp:salesitem:list','erp:salesitem:query','erp:salesitem:add','erp:salesitem:edit','erp:salesitem:remove',
  'erp:salesmaterial:list','erp:salesmaterial:query','erp:salesmaterial:add','erp:salesmaterial:edit','erp:salesmaterial:remove',
  -- 客户管理
  'erp:customer:list','erp:customer:query','erp:customer:add','erp:customer:edit','erp:customer:remove','erp:customer:export',
  'erp:contacts:list','erp:contacts:query','erp:contacts:add','erp:contacts:edit','erp:contacts:remove',
  'erp:invoice:list','erp:invoice:query','erp:invoice:add','erp:invoice:edit',
  -- 打样通知
  'erp:notice:list','erp:notice:query','erp:notice:add','erp:notice:edit','erp:notice:remove',
  -- 工艺指示书（只读）
  'erp:tech:list','erp:tech:query',
  -- 生产计划（只读）
  'erp:plan:list','erp:plan:query',
  -- 库存（只读）
  'erp:stock:list','erp:stock:query'
);

-- 财务（role_id=103）
-- 拥有权限：piecewage/piecewagedetail/cost/invoice + 其余只读
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 103, menu_id FROM sys_menu
WHERE perms IN (
  -- 计件工资
  'erp:piecewage:list','erp:piecewage:query','erp:piecewage:add','erp:piecewage:edit','erp:piecewage:remove','erp:piecewage:export',
  'erp:piecewagedetail:list','erp:piecewagedetail:query','erp:piecewagedetail:add','erp:piecewagedetail:edit','erp:piecewagedetail:remove',
  -- 成本
  'erp:cost:list','erp:cost:query','erp:cost:add','erp:cost:edit','erp:cost:remove','erp:cost:export',
  -- 发票
  'erp:invoice:list','erp:invoice:query','erp:invoice:add','erp:invoice:edit','erp:invoice:remove','erp:invoice:export',
  -- 采购（只读）
  'erp:purchase:list','erp:purchase:query',
  -- 销售（只读）
  'erp:sales:list','erp:sales:query',
  -- 员工（只读）
  'erp:employee:list','erp:employee:query',
  -- 库存（只读）
  'erp:stock:list','erp:stock:query',
  -- 客户/供应商（只读）
  'erp:customer:list','erp:customer:query',
  'erp:supplier:list','erp:supplier:query'
);

-- 仓库管理员（role_id=104）
-- 拥有权限：stock/stockin/stockout/warehouse/warehousearea/warehouselocation/inventory/materialconsume
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT 104, menu_id FROM sys_menu
WHERE perms IN (
  -- 库存
  'erp:stock:list','erp:stock:query','erp:stock:add','erp:stock:edit','erp:stock:remove','erp:stock:export',
  'erp:inventory:list','erp:inventory:query','erp:inventory:add','erp:inventory:edit','erp:inventory:remove',
  -- 入库
  'erp:stockin:list','erp:stockin:query','erp:stockin:add','erp:stockin:edit','erp:stockin:remove','erp:stockin:export',
  -- 出库
  'erp:stockout:list','erp:stockout:query','erp:stockout:add','erp:stockout:edit','erp:stockout:remove','erp:stockout:export',
  -- 仓库基础数据
  'erp:warehouse:list','erp:warehouse:query','erp:warehouse:add','erp:warehouse:edit',
  'erp:warehousearea:list','erp:warehousearea:query','erp:warehousearea:add','erp:warehousearea:edit',
  'erp:warehouselocation:list','erp:warehouselocation:query','erp:warehouselocation:add','erp:warehouselocation:edit',
  -- 物料消耗
  'erp:materialconsume:list','erp:materialconsume:query','erp:materialconsume:add','erp:materialconsume:edit',
  -- 物料/辅料（只读）
  'erp:material:list','erp:material:query',
  'erp:auxiliary:list','erp:auxiliary:query',
  -- 采购（只读）
  'erp:purchase:list','erp:purchase:query'
);

-- ============================================================
-- 3. 岗位定义（sys_post）
-- ============================================================
INSERT IGNORE INTO sys_post (post_id, post_code, post_name, post_sort, status, create_by, create_time, remark)
VALUES
(10, 'erp_produce_mgr',  '生产主管',   10, '0', 'admin', NOW(), 'ERP生产主管岗位'),
(11, 'erp_technician',   '技术员',     11, '0', 'admin', NOW(), 'ERP技术员岗位'),
(12, 'erp_salesperson',  '销售员',     12, '0', 'admin', NOW(), 'ERP销售员岗位'),
(13, 'erp_finance',      '财务',       13, '0', 'admin', NOW(), 'ERP财务岗位'),
(14, 'erp_warehouse',    '仓库管理员', 14, '0', 'admin', NOW(), 'ERP仓库管理员岗位');
