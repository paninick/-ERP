# SQL 脚本说明

## 新环境初始化（按顺序执行）

| 顺序 | 文件 | 说明 |
|------|------|------|
| 1 | `ry_20260321.sql` | 若依框架基础表（系统权限/字典/菜单等） |
| 2 | `quartz.sql` | 定时任务表 |
| 3 | `phase1_basic_data.sql` | ERP 基础数据（客户/供应商/仓库/物料） |
| 4 | `phase2_produce_core.sql` | 生产核心（生产计划/工单/工序） |
| 5 | `phase3_outsource_defect.sql` | 外发管理 + 缺陷记录 |
| 6 | `phase5_piece_wage.sql` | 计件工资 |
| 7 | `phase6_material_consume.sql` | 物料消耗 |
| 8 | `phase7_biz_abnormal_pool.sql` | 业务异常池 |
| 9 | `phase8_produce_board_gantt.sql` | 生产看板甘特图 |
| 10 | `phase9_inventory_improvement.sql` | 库存改进 |
| 11 | `_helpers.sql` | 存储过程定义（sp_erp_add_column/index/unique_key），phase10 及后续均依赖 |
| 12 | `phase10_complete_material_fields.sql` | 物料字段完善 |
| 13 | `phase11_bom_sku_improvement.sql` | BOM & SKU 改进 |
| 14 | `phase12_p0_defect_jis_process.sql` | P0：缺陷字典库 + JIS合规字段 + 工序放行标准 |
| 15 | `phase13_p1_splice_dye_threshold.sql` | P1：拼接工艺SOP字段 + 染整阈值约束字段 |
| 16 | `phase14_rbac_role_permission.sql` | P1：RBAC岗位权限矩阵（5个ERP岗位角色） |
| 17 | `phase15_flexible_schedule.sql` | P2：柔性排单字段（priority/start_date/due_date/schedule_status/conflict_flag） |
| 18 | `phase16_menu_register.sql` | P2：ERP 菜单全量注册（顶级目录+7个二级目录+所有功能页+losscontrol+producegantt） |
| 19 | `sprint2_inventory_pushdown.sql` | 库存下推 |
| 20 | `sys_automation.sql` | 系统自动化配置 |
| 21 | `init_inventory_data.sql` | 初始库存数据（可选） |
| 22 | `ops_panel_init.sql` | 运维面板（可选） |

## 其他脚本

| 文件 | 说明 |
|------|------|
| `mysql_init.sql` | MySQL 用户/库初始化（仅首次建库用） |
| `add_price_to_main_material.sql` | 主材料加价格字段补丁 |
| `alter_sample_notice_table.sql` | 打样通知表结构变更补丁 |
| `check_and_fix_main_material_table.sql` | 主材料表检查修复脚本 |
| `insert_auxiliary_material_dict.sql` | 辅材料字典数据 |

## 归档脚本

`archive/` 目录存放历史调试过程产生的冗余变体，不需要执行：
- ry_20260321_ascii/clean/fixed/git_raw/no_comments/original/processed/python_fixed/repaired/ultimate_final/utf8/final_clean

**主脚本：`ry_20260321.sql`（61K，包含中文注释，UTF-8）**
**最终对比版：`ry_20260321_final.sql`（45K，精简版）**
