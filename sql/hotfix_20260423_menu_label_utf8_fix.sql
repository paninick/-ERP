-- 2026-04-23 menu label utf8 fix
-- Purpose:
-- 1. Repair ERP menu labels that were written as question marks.
-- 2. Keep menu ids, paths and permissions unchanged.
-- Usage:
--   mysql --default-character-set=utf8mb4 -uroot ry_vue < sql/hotfix_20260423_menu_label_utf8_fix.sql

SET NAMES utf8mb4;

UPDATE sys_menu SET menu_name = '服装ERP', update_by = 'codex', update_time = NOW() WHERE menu_id = 2000;
UPDATE sys_menu SET menu_name = '基础数据', update_by = 'codex', update_time = NOW() WHERE menu_id = 2001;
UPDATE sys_menu SET menu_name = '销售管理', update_by = 'codex', update_time = NOW() WHERE menu_id = 2002;
UPDATE sys_menu SET menu_name = '生产管理', update_by = 'codex', update_time = NOW() WHERE menu_id = 2003;
UPDATE sys_menu SET menu_name = '质量管理', update_by = 'codex', update_time = NOW() WHERE menu_id = 2004;
UPDATE sys_menu SET menu_name = '库存管理', update_by = 'codex', update_time = NOW() WHERE menu_id = 2005;
UPDATE sys_menu SET menu_name = '财务管理', update_by = 'codex', update_time = NOW() WHERE menu_id = 2006;
UPDATE sys_menu SET menu_name = '报表中心', update_by = 'codex', update_time = NOW() WHERE menu_id = 2007;

UPDATE sys_menu SET menu_name = '员工档案', update_by = 'codex', update_time = NOW() WHERE menu_id = 2012;
UPDATE sys_menu SET menu_name = '工序定义', update_by = 'codex', update_time = NOW() WHERE menu_id = 2016;
UPDATE sys_menu SET menu_name = '标准色管理', update_by = 'codex', update_time = NOW() WHERE menu_id = 2019;
UPDATE sys_menu SET menu_name = '损耗矩阵', update_by = 'codex', update_time = NOW() WHERE menu_id = 2021;
UPDATE sys_menu SET menu_name = '产品序列号', update_by = 'codex', update_time = NOW() WHERE menu_id = 2048;
UPDATE sys_menu SET menu_name = '库存盘点', update_by = 'codex', update_time = NOW() WHERE menu_id = 2073;
UPDATE sys_menu SET menu_name = '计件工资', update_by = 'codex', update_time = NOW() WHERE menu_id = 2080;
UPDATE sys_menu SET menu_name = '工资明细', update_by = 'codex', update_time = NOW() WHERE menu_id = 2081;
UPDATE sys_menu SET menu_name = '报表总览', update_by = 'codex', update_time = NOW() WHERE menu_id = 2084;

-- Legacy button rows that were previously written with "?????????" under 大货核版
UPDATE sys_menu SET menu_name = '查询', update_by = 'codex', update_time = NOW() WHERE menu_id = 4450;
UPDATE sys_menu SET menu_name = '新增', update_by = 'codex', update_time = NOW() WHERE menu_id = 4451;
UPDATE sys_menu SET menu_name = '修改', update_by = 'codex', update_time = NOW() WHERE menu_id = 4452;
UPDATE sys_menu SET menu_name = '删除', update_by = 'codex', update_time = NOW() WHERE menu_id = 4453;
UPDATE sys_menu SET menu_name = '导出', update_by = 'codex', update_time = NOW() WHERE menu_id = 4454;
UPDATE sys_menu SET menu_name = '审批', update_by = 'codex', update_time = NOW() WHERE menu_id = 4455;
UPDATE sys_menu SET menu_name = '导入', update_by = 'codex', update_time = NOW() WHERE menu_id = 4456;
