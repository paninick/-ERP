# SQL 文件索引 — 按模块归属

## 模块化目录 (sql/module-*/)

### module-foundation (基础)
- `process_def_extend.sql` — 工序定义扩展字段
- `work_center_create.sql` — 工作中心建表
- `p0_role_menu_assign.sql` — 角色菜单权限
- `p1_missing_dicts.sql` — 缺失字典补充
- `p1_route_templates.sql` — 工艺路线模板
- `p2_encrypt_sensitive.sql` — 敏感数据加密

### module-approval (审批)
- `approval_log_create.sql` — 统一审批日志表
- `approval_status_extend.sql` — 审批状态字段补齐

### module-business (业务)
- `customer_japan_extend.sql` — 客户日单字段
- `inspection_company_create.sql` — 检品公司+预约表
- `qc_inspection_extend.sql` — 质检分层扩展
- `style_master_create.sql` — 款号档案表

### module-cost (成本)
- `cost_closure_extend.sql` — 成本字段补齐+索引
- `cost_summary_create.sql` — 统一成本汇总表

### module-production (生产)
- `produce_report_log_create.sql` — 报工事件流水表

## Phase SQL 文件 (历史，sql/phase*.sql)

### 基础/权限
| 文件 | 阶段 | 用途 |
|---|---|---|
| phase14_rbac_role_permission.sql | P0 | 角色权限初始化 |
| phase16_menu_register.sql | P0 | 菜单注册 |
| phase30_p0_roles_menu_permission.sql | P0 | P0角色菜单 |
| phase26_process_governance_permission_fix.sql | P2 | 权限治理修正 |

### 组织/员工/工序
| 文件 | 阶段 | 用途 |
|---|---|---|
| phase1_basic_data.sql | P1 | 基础数据字典 |
| phase17_process_def_seed.sql | P1 | 工序定义种子数据 |
| phase18_process_route_and_auto_flow.sql | P1 | 工艺路线+自动流转 |
| phase19_employee_process_price_matrix.sql | P1 | 员工工序价格矩阵 |
| phase25_process_light_inspection_hotfix.sql | P2 | 轻检验热修复 |
| phase31_p1_org_unit.sql | P1 | 组织层级表 |
| phase32_employee_org_extension.sql | P1 | 员工组织扩展 |

### 生产/物料/报工
| 文件 | 阶段 | 用途 |
|---|---|---|
| phase2_produce_core.sql | P2 | 生产核心表 |
| phase5_piece_wage.sql | P2 | 计件工资 |
| phase6_material_consume.sql | P2 | 物料消耗 |
| phase8_produce_board_gantt.sql | P2 | 生产看板甘特图 |
| phase10_complete_material_fields.sql | P2 | 材料完整字段 |
| phase27_produce_report_log.sql | P2 | 报工流水 |
| phase28_material_consume_execution_cost.sql | P2 | 物料消耗执行成本 |
| phase29_material_consume_stockout_link.sql | P2 | 物料消耗出库联动 |
| phase15_flexible_schedule.sql | P2 | 弹性排程 |

### 品质/异常/追溯
| 文件 | 阶段 | 用途 |
|---|---|---|
| phase3_outsource_defect.sql | P3 | 外协缺陷 |
| phase7_biz_abnormal_pool.sql | P2 | 异常池 |
| phase12_p0_defect_jis_process.sql | P0 | 日标缺陷工序 |
| phase13_p1_splice_dye_threshold.sql | P1 | 拼接染色阈值 |
| phase20_product_traceability.sql | P2 | 产品追溯 |

### SKU/库存/供应商
| 文件 | 阶段 | 用途 |
|---|---|---|
| phase9_inventory_improvement.sql | P2 | 库存改进 |
| phase11_bom_sku_improvement.sql | P2 | BOM SKU改进 |
| phase21_product_category_and_supplier_rating.sql | P2 | 产品分类+供应商评分 |
| phase22_style_no_dual_key.sql | P2 | 款号双轨主键 |
| phase23_style_code_compat_migration.sql | P2 | 款号兼容迁移 |

## 迁移指引
1. 新建 SQL 一律放入 `sql/module-{category}/` 对应目录
2. 现有 phase*.sql 保持原位（历史脚本仍有引用），逐步按模块拆分后移入 module-*/
3. 执行顺序：先 module-foundation → module-business → module-production → module-approval → module-cost
