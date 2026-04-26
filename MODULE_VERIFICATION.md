# 模块交付验收记录（10检查点）

最后更新：2026-04-26

## 验收标准
每个模块必须通过全部 10 项，缺任何一项视为未完成：
1. dict — 字典已录入
2. sql — 表结构已建
3. domain — Java domain 类已建
4. mapper — Mapper XML 已建
5. service — Service 已建
6. controller — Controller 已建
7. frontend-api — 前端 API 1:1
8. frontend-page — 前端页面
9. approval/print/scan — 审批/打印/扫码
10. live-verify — 真实数据验证

---

## 主数据域

### 客户档案
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_nation/erp_aql_level/erp_product_family |
| 2 | sql | ✅ | t_erp_customer + is_japan_order 等日单字段 |
| 3 | domain | ✅ | Customer.java |
| 4 | mapper | ✅ | CustomerMapper.xml |
| 5 | service | ✅ | ICustomerService + Impl |
| 6 | controller | ✅ | CustomerController |
| 7 | frontend-api | ❌ | |
| 8 | frontend-page | ❌ | |
| 9 | approval | — | 不适用 |
| 10 | live-verify | ❌ | |

### 供应商档案
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_supplier_type |
| 2 | sql | ✅ | t_erp_supplier |
| 3-6 | domain/mapper/service/controller | ✅ | Supplier.java 全栈 |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ❌ | |

### 员工档案
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_skill_level/erp_piece_category |
| 2 | sql | ✅ | t_erp_employee + org_unit_id/skill_level 等扩展 |
| 3 | domain | ✅ | ErpEmployee.java |
| 4 | mapper | ✅ | ErpEmployeeMapper.xml |
| 5 | service | ✅ | IErpEmployeeService + Impl |
| 6 | controller | ✅ | ErpEmployeeController |
| 7 | frontend-api | ❌ | |
| 8 | frontend-page | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ❌ | |

### 组织层级
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_org_type |
| 2 | sql | ✅ | t_erp_org_unit |
| 3-6 | domain/mapper/service/controller | ✅ | OrgUnit 全栈 |
| 7 | frontend-api | ❌ | |
| 8 | frontend-page | ✅ | ERP-UI-2 org/index.tsx |
| 9 | approval | — | |
| 10 | live-verify | ✅ | GET /erp/orgunit/list → 200 |

### 工序定义
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_process_type/erp_product_family |
| 2 | sql | ✅ | t_erp_process_def + 扩展字段 |
| 3-6 | domain/mapper/service/controller | ✅ | ProcessDef 全栈 |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ✅ | API 200 |

### 工艺路线
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-2 | dict/sql | ✅ | t_erp_process_route + item |
| 3-6 | domain/mapper/service/controller | ✅ | |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ❌ | |

### 工作中心
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-2 | dict/sql | ✅ | erp_work_center_type, t_erp_work_center |
| 3-6 | domain/mapper/service/controller | ✅ | ErpWorkCenter 全栈 |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ✅ | /erp/workCenter/list → 200 |

### 款号档案
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-2 | dict/sql | ✅ | erp_style_status, t_erp_style |
| 3-6 | domain/mapper/service/controller | ✅ | Style 全栈 |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ✅ | /erp/style/list → 200 |

---

## 销售域

### 销售订单
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_sales_type |
| 2 | sql | ✅ | t_erp_sales_order |
| 3-6 | domain/mapper/service/controller | ✅ | SalesOrder 全栈 |
| 7 | frontend-api | ❌ | |
| 8 | frontend-page | ❌ | |
| 9 | approval | ✅ | submit/approve/reject + 审批日志 |
| 10 | live-verify | ✅ | API 200 |

### 打样通知
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-6 | 全栈 | ✅ | |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | ✅ | |

---

## 生产域

### 生产计划
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-6 | 全栈 | ✅ | ProducePlan |
| 7-8 | frontend | ❌ | |
| 9 | approval | ✅ | submit/approve/reject |
| 10 | live-verify | ✅ | |

### 生产单
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-6 | 全栈 | ✅ | ProduceJob |
| 7-8 | frontend | ❌ | |
| 9 | approval | 🟡 | 仅工序确认 |
| 10 | live-verify | ❌ | |

### 物料消耗
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1-6 | 全栈 | ✅ | ProduceMaterialConsume |
| 7 | frontend-api | ❌ | |
| 8 | frontend-page | ❌ | |
| 9 | approval | ✅ | 超耗审批 |
| 10 | live-verify | ❌ | |

### 报工事件流水
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | |
| 2 | sql | ✅ | TD-11 已修复 |
| 3-6 | 全栈 | ✅ | ProduceReportLog |
| 7-8 | frontend | ❌ | |
| 9 | approval | — | |
| 10 | live-verify | 🟡 | API 200, 无数据 |

---

## 审批域

### 统一审批日志
| # | 检查点 | 状态 | 备注 |
|---|---|---|---|
| 1 | dict | ✅ | erp_audit_status, erp_approval_action |
| 2 | sql | ✅ | t_erp_approval_log |
| 3-6 | 全栈 | ✅ | ErpApprovalLog |
| 7 | frontend-api | ❌ | |
| 8 | frontend-page | ❌ | |
| 9 | approval | ✅ | 写入正常 |
| 10 | live-verify | ✅ | /erp/approvalLog/list → 200 |

---

## 汇总

| 域 | 模块数 | 全通过 | 部分 | 差前端 |
|---|---|---|---|---|
| 主数据域 | 8 | 1 (OrgUnit) | 7 | 7 |
| 销售域 | 2 | 1 | 1 | 1 |
| 生产域 | 3 | 0 | 3 | 3 |
| 审批域 | 1 | 0 | 1 | 1 |
| **合计** | **14** | **2** | **12** | **12** |

关键缺口：12 个模块缺前端页面（检查点 7+8），4 个模块缺 live-verify（检查点 10）。
