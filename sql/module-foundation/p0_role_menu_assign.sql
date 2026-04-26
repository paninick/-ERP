-- P0-3: 角色菜单权限配置
-- 按照 ERP_MASTER_PLAN.md §3.2 菜单权限矩阵
-- 幂等：先删后插（同一 role_id 的菜单分配）

-- 105 erp_purchaser 采购员
DELETE FROM sys_role_menu WHERE role_id = 105;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(105, 904),(105, 2030),  -- 销售订单(查看)
(105, 2012),              -- 员工档案
(105, 2018),              -- BOM管理(查看)
(105, 2016),              -- 工序定义
(105, 2044),              -- 物料消耗
(105, 105);               -- 字典管理

-- 106 erp_qc 品质
DELETE FROM sys_role_menu WHERE role_id = 106;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(106, 904),(106, 2030),   -- 销售订单(查看)
(106, 2016),              -- 工序定义
(106, 2017),              -- 工艺路线
(106, 2018),              -- BOM管理(查看)
(106, 105);               -- 字典管理

-- 107 erp_outsource 外协专员
DELETE FROM sys_role_menu WHERE role_id = 107;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(107, 904),(107, 2030),   -- 销售订单(查看)
(107, 905),(107, 2040),   -- 生产计划(查看)
(107, 2016),              -- 工序定义
(107, 2044),              -- 物料消耗
(107, 105);               -- 字典管理

-- 108 erp_pmc PMC/计划员
DELETE FROM sys_role_menu WHERE role_id = 108;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(108, 904),(108, 2030),   -- 销售订单(查看)
(108, 905),(108, 2040),   -- 生产计划(✅)
(108, 2016),              -- 工序定义
(108, 2017),              -- 工艺路线
(108, 2018),              -- BOM管理(查看)
(108, 2044),              -- 物料消耗
(108, 2047),              -- 生产看板
(108, 105);               -- 字典管理

-- 109 erp_workshop_leader 车间主任
DELETE FROM sys_role_menu WHERE role_id = 109;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(109, 905),(109, 2040),   -- 生产计划(查看)
(109, 2016),              -- 工序定义
(109, 2044),              -- 物料消耗
(109, 2047),              -- 生产看板
(109, 2080),              -- 计件工资(✅)
(109, 105);               -- 字典管理

-- 110 erp_operator 操作工（仅工序报工）
DELETE FROM sys_role_menu WHERE role_id = 110;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(110, 2016);              -- 工序定义(查看)

-- 111 erp_hr 人事
DELETE FROM sys_role_menu WHERE role_id = 111;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(111, 2012),              -- 员工档案(✅)
(111, 2080),              -- 计件工资(查看)
(111, 105);               -- 字典管理

-- 112 erp_sample 打样员
DELETE FROM sys_role_menu WHERE role_id = 112;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(112, 900),(112, 2031),   -- 打样通知(✅)
(112, 2016),              -- 工序定义
(112, 2018),              -- BOM管理(查看)
(112, 105);               -- 字典管理

-- 113 erp_outsource_vendor 外协厂账号
DELETE FROM sys_role_menu WHERE role_id = 113;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(113, 904);               -- 销售订单(查看) -- limited to own orders

-- 114 erp_inspection_company 检品公司账号
DELETE FROM sys_role_menu WHERE role_id = 114;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(114, 4466),              -- 检品公司
(114, 4467);              -- 检品预约

-- 115 erp_management 管理层（全集团只读）
DELETE FROM sys_role_menu WHERE role_id = 115;
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
(115, 904),(115, 2030),   -- 销售订单
(115, 900),(115, 2031),   -- 打样通知
(115, 905),(115, 2040),   -- 生产计划
(115, 2018),              -- BOM管理
(115, 2016),              -- 工序定义
(115, 2017),              -- 工艺路线
(115, 2044),              -- 物料消耗
(115, 2047),              -- 生产看板
(115, 2080),              -- 计件工资
(115, 4468),              -- 款号档案
(115, 4465),              -- 工作中心
(115, 2012),              -- 员工档案
(115, 2554),(115, 4449),  -- 数据导入
(115, 105);               -- 字典管理

SELECT CONCAT('P0-3: 角色菜单权限已配置 (105-115)') AS result;
