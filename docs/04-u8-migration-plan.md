# 交付物 04：同类 ERP 业务逻辑对标与借鉴

> **定位（重要调整）**：本文档**不做 U8 迁移、不做数据集成、不做切换方案**。定位为"对标学习"——在 8 个关键业务领域横向比较 **用友 U8/U9、金蝶 K/3 / 云星空、SAP B1 / S/4HANA、Odoo** 四大体系的做法，再给出针织外贸 ERP 应该采纳的最佳融合方案。
> **产出日**：2026-04-21 · **版本**：v2.0（替代 v1.0 迁移版）
> **财务定位**：**中间态**——做应收应付、开票、成本核算等"业务财务"；不做总账、财务三大报表（交给专业财务软件或外部会计）
> **前置阅读**：`01-requirements-normalization.md`、`02-architecture-and-key-system.md`

---

## 0. 为何要对标四家 ERP

| 对标对象 | 借鉴价值（针织外贸语境） |
| :-- | :-- |
| **用友 U8/U9** | 中国制造业业务习惯的原型；字段命名、单据流、凭证生成的"中式路径" |
| **金蝶 K/3 WISE / 云星空** | 中国中型制造业主流 SaaS；成本核算（分步法）的清晰分层 |
| **SAP B1 / S/4HANA** | 国际外贸与纺织行业巨头（优衣库、迅销）长期实践；多工厂/多币种/三单核销的工业标准 |
| **Odoo** | 开源可读源码；模块化设计、MRP II 实现、ORM 模型 |

> 💡 **这四家不是"选哪家"，是"每家拿一块最好的"。** 中国客户用户习惯取 U8/金蝶；外贸专业性取 SAP；工程可学习性取 Odoo。

### 本文档结构

1. **§1** 四家 ERP 技术 + 业务画像
2. **§2-§9** 8 个关键业务领域的横向对比 + 针织推荐
3. **§10** 融合最佳实践的系统设计建议
4. **§11** 针织外贸特殊约束清单（其他 ERP 都不原生支持的部分）
5. **§12** 遗留问题与假设

---

## 1. 四家 ERP 画像

### 1.1 技术栈与部署形态

| 维度 | U8 / U9 | 金蝶 K/3 / 云星空 | SAP B1 / S/4HANA | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 架构 | .NET + SQL Server | K/3: Delphi + SQL Server · 云星空: .NET + BOS 平台 | B1: .NET + MSSQL/HANA · S/4: ABAP + HANA | Python + PostgreSQL + OWL (JS) |
| 部署 | 本地 / 用友云 | 本地 / 金蝶云 | 本地 / 云 | 本地 / SaaS |
| 扩展方式 | BQ 报表、EAS 插件 | BOS / 云苍穹 SDK | UDO / ABAP / Fiori | XML-RPC / Python 模块 / OWL |
| 数据模型 | 表扁平、字段多 | K/3 扁平 · 云星空元数据驱动 | 深度规范化 + 增强表 | ORM 继承式（mixin） |
| 开源 | ❌ | ❌ | ❌ | ✅ (Community) |
| 文档公开度 | 中（部分 API 文档）| 中（BOS 开发手册） | 高（SAP Help Portal）| 极高（源码 + docs）|

### 1.2 行业适用性

| 维度 | U8 / U9 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 纺织服装行业解决方案 | 有（U8 服装版）| 有（云星空服装 V2）| 有（SAP Apparel & Footwear Solution, AFS）| 有（OCA 社区模块） |
| 外贸单证 | 中 | 中 | 强（IBP、GTS） | 弱 |
| 多工厂 / 多组织 | 强（U9） | 强（云星空） | **极强**（SAP 强项） | 中 |
| 多币种 | 中 | 强 | **极强** | 强 |
| 本土税务（金税 / 发票） | **极强** | 强 | 需二开 | 需二开 |
| **针织/服装匹配度** | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| **外贸匹配度** | ⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐⭐ | ⭐⭐⭐ |

### 1.3 各家最值得学习的地方

| | 学习这一块 |
| :-- | :-- |
| **U8 / U9** | ① 中式存货核算（全月平均、计划价差异）② 凭证生成与会计期间管理 ③ 发票折扣税率计算 |
| **金蝶** | ① 分步法 / 分批法 / 品种法 成本模型 ② 云星空的**元数据驱动**单据设计 |
| **SAP** | ① 三单核销（PO-GR-IV）的工业标准 ② MRP II 物料需求计算 ③ 多工厂 / 多公司代码的组织模型 ④ 多语言 / 多币种 / 多日历 |
| **Odoo** | ① ORM 字段 + 视图 + 工作流的解耦模型 ② mrp 模块源码（工单-工序-工站）③ account_move 单据 + 分录一体化 |

---

## 2. 领域 1：存货核算方法

### 2.1 四家做法对比

| 算法 | U8 | 金蝶 | SAP | Odoo | 针织适用 |
| :-- | :-- | :-- | :-- | :-- | :-- |
| **全月平均** | ✅（默认）| ✅ | ❌（不支持） | ✅（Periodic）| ⭐⭐⭐ |
| **移动加权平均** | ✅ | ✅ | ✅（S = Moving Avg）| ✅（AVCO Perpetual）| ⭐⭐ |
| **先进先出 FIFO** | ✅ | ✅ | ✅（F）| ✅（FIFO）| ⭐⭐ |
| **后进先出 LIFO** | ✅ | ✅ | ✅（L，仅部分国家） | ❌ | ⭐ |
| **计划价 / 标准成本** | ✅（计划价 + 差异） | ✅ | ✅（S = Standard）| ✅（Standard Price）| ⭐⭐⭐ |
| **实际成本分批** | ✅ | ✅ | ✅（Batch / Split Valuation）| ✅（Landed Cost + Batch）| ⭐⭐⭐⭐（外贸推荐） |
| **加权平均按仓库分开** | ❌ | ❌（需二开）| ✅（Valuation Area）| ✅（per warehouse/company） | ⭐⭐⭐⭐ |

### 2.2 SAP 的亮点：估价区域（Valuation Area）

SAP 允许"每个工厂/每个仓库单独估价"——同一物料在上海厂和苏州厂可以有不同成本价。这在多工厂针织企业非常实用（纱线从不同渠道进价不同）。

### 2.3 Odoo 的亮点：Landed Cost（到港成本分摊）

Odoo 的 `stock_landed_costs` 模块原生支持"海运费、关税、保险费按金额/体积/重量分摊到存货成本"——对进口纱线的 ERP 非常贴切。

### 2.4 针织外贸推荐方案

**建议：混合模型 + 多算法可选**

| 物料类型 | 推荐核算 | 理由 |
| :-- | :-- | :-- |
| 进口纱线（主料） | 实际成本 + 批次 + 到港成本分摊 | 每批价格差异大，需独立追溯 |
| 辅料（拉链/标签） | 全月平均 | 价低、批次不敏感 |
| 坯布（半成品） | 计划成本 + 差异 | 月中快算、月末调整 |
| 成品 | 实际成本（分批）| 客户签样后锁定 |

**系统设计**（`material.costing_method` 枚举字段）：
```
AVCO_PERIOD   = 全月平均
AVCO_MOVING   = 移动平均
FIFO          = 先进先出
STANDARD      = 标准成本
BATCH         = 批次实际成本
```

**借鉴路径**：
- 算法框架：Odoo `stock_move_valuation_layer` 的抽象层
- 月结流程：U8 的"月末结账 → 存货核算 → 凭证生成"三步
- 差异调整：U8 / 金蝶的"计划价差异分摊"

---

## 3. 领域 2：应收应付模型

### 3.1 四家做法对比

| 维度 | U8 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 应收基础模型 | 应收单 + 核销单 | 应收单 + 收款单 | 客户开票（`BSID`/`BSAD` 表）| account.move + account.payment |
| 账龄桶 | 配置（30/60/90/120+）| 配置 | 配置 | 配置 |
| 预收 / 预付 | ✅ 独立单据 | ✅ 独立单据 | ✅（客户/供应商特殊科目）| ✅ advance payment wizard |
| **一单对多结 / 多单对一结** | ✅ | ✅ | ✅（F-28/F-32 勾稽）| ✅（Register Payment + Multi）|
| **部分核销 / 金额差异处理** | ✅ | ✅ | ✅（剩余额 / 部分 / 折扣差异）| ✅（Writeoff） |
| 坏账管理 | 基础 | 基础 | 强（特殊总账事务）| 弱 |
| 信用管理 | 弱 | 中 | 强（SD Credit Management） | 弱（OCA 有） |
| **多币种应收**| ✅（汇兑损益自动）| ✅ | ✅（极强） | ✅ |

### 3.2 SAP 的亮点：开放项 / 已清项（Open Item Management）

SAP 的 AR / AP 使用 **Open Item** 概念：所有应收应付金额以"单项条目"形式存在，核销即"配对"。一笔收款可以部分核销多个发票、留余额在某个发票上。**表设计参考**：
- `BSID` Accounts Receivable Open Items（未清项）
- `BSAD` Accounts Receivable Cleared Items（已清项）
- `BKPF` + `BSEG` 凭证头 + 行项目

这种设计对"日方客户月结合并付款"场景极友好。

### 3.3 Odoo 的亮点：统一 account.move + payment

Odoo 把发票、收款、调整都建模为 `account.move`（一个凭证对象），只是类型不同（`out_invoice`/`in_invoice`/`entry`）。应收应付模型高度统一，源码可读性好。

### 3.4 针织外贸推荐方案

**核心：SAP 式开放项 + Odoo 式统一模型**

```sql
-- 应收主表（借鉴 SAP BSID 的开放项设计）
CREATE TABLE t_erp_ar_item (
    id                   BIGINT NOT NULL AUTO_INCREMENT,
    business_partner_id  BIGINT NOT NULL COMMENT '客户',
    source_type          VARCHAR(20) NOT NULL COMMENT 'INVOICE / PREPAY / WRITEOFF / FX_ADJ',
    source_id            BIGINT NOT NULL COMMENT '源单 ID',
    source_no            VARCHAR(64) COMMENT '源单号快照',
    currency_code        CHAR(3) NOT NULL COMMENT 'CNY/JPY/USD',
    amount               DECIMAL(18,2) NOT NULL COMMENT '原币金额（有符号）',
    amount_local         DECIMAL(18,2) NOT NULL COMMENT '本币金额',
    exchange_rate        DECIMAL(18,8) NOT NULL,
    cleared_amount       DECIMAL(18,2) NOT NULL DEFAULT 0 COMMENT '已核销金额',
    open_amount          DECIMAL(18,2) GENERATED ALWAYS AS (amount - cleared_amount) STORED,
    due_date             DATE,
    status               CHAR(1) NOT NULL COMMENT 'O=开放 C=已清 P=部分清',
    factory_id           BIGINT,
    create_time          DATETIME,
    PRIMARY KEY (id),
    KEY idx_partner_status (business_partner_id, status),
    KEY idx_due_date (due_date)
) COMMENT='应收开放项（借鉴 SAP BSID）';

-- 核销关系表
CREATE TABLE t_erp_ar_clearing (
    id           BIGINT NOT NULL AUTO_INCREMENT,
    ar_item_id   BIGINT NOT NULL COMMENT '应收项',
    payment_id   BIGINT NOT NULL COMMENT '收款',
    clear_amount DECIMAL(18,2) NOT NULL,
    fx_gain_loss DECIMAL(18,2) COMMENT '汇兑损益',
    clear_date   DATE NOT NULL,
    operator_id  BIGINT,
    PRIMARY KEY (id),
    KEY idx_ar (ar_item_id),
    KEY idx_payment (payment_id)
);
```

**账龄计算**（借鉴 U8 的典型配置）：

```sql
SELECT
    business_partner_id,
    SUM(CASE WHEN DATEDIFF(NOW(), due_date) <=  0 THEN open_amount ELSE 0 END) AS not_due,
    SUM(CASE WHEN DATEDIFF(NOW(), due_date) BETWEEN   1 AND  30 THEN open_amount ELSE 0 END) AS bucket_0_30,
    SUM(CASE WHEN DATEDIFF(NOW(), due_date) BETWEEN  31 AND  60 THEN open_amount ELSE 0 END) AS bucket_31_60,
    SUM(CASE WHEN DATEDIFF(NOW(), due_date) BETWEEN  61 AND  90 THEN open_amount ELSE 0 END) AS bucket_61_90,
    SUM(CASE WHEN DATEDIFF(NOW(), due_date) >  90 THEN open_amount ELSE 0 END) AS bucket_90_plus
FROM t_erp_ar_item
WHERE status IN ('O','P')
GROUP BY business_partner_id;
```

---

## 4. 领域 3：三单核销（订单-发货-发票-收款）

### 4.1 四家做法对比

| 环节 | U8 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 销售：订单 → 发货 → 发票 → 收款 | 四步 | 四步 | 四步 | 四步 |
| 采购：订单 → 到货 → 入库 → 发票 → 付款 | 五步 | 五步 | 五步（PO-GR-IV）| 五步 |
| 三单匹配（PO-GR-IV）| 手工 / 半自动 | 半自动 | **全自动 + 差异管理** | 半自动 |
| 价格 / 数量差异容忍 | 配置 | 配置 | **容差矩阵（Tolerance Keys）**| 配置 |
| 分批发货 | ✅ | ✅ | ✅ | ✅ |
| 分批开票 | ✅ | ✅ | ✅ | ✅ |
| 合并开票（多单→1 票）| ✅ | ✅ | ✅（Collective Billing）| ✅ |
| 拆分开票（1 单→多票）| ✅ | ✅ | ✅ | ✅ |

### 4.2 SAP 的亮点：三单匹配容差（Tolerance Keys）

SAP 在 PO-GR-IV 匹配时，支持多维容差：
- `PP` Price Variance（价格差异）
- `PS` Quantity Variance（数量差异）
- `DQ` Delivery Quantity（到货数量）
- `DW` Date Variance（日期差异）
- `AN` Amount Variance（小金额差异）
- `BD` Small Difference（四舍五入差）

每个容差有 **百分比阈值** + **绝对金额阈值**，达到其一才触发告警/冻结。**这是针织外贸必须学的**——纱线进口到货重量必有差异（潮湿度、包装误差），必须有容差机制。

### 4.3 Odoo 的亮点：invoice_status 自动推导

Odoo 的销售订单、采购订单、发货单都有 `invoice_status` 字段，根据已开票数量自动推导为：
- `no`（未开）/ `to invoice`（可开）/ `invoiced`（已开）/ `upselling`（实际发货超订单）

**设计借鉴**：所有业务单据都应该有一个"下游动作状态"字段，不是手工维护而是派生。

### 4.4 针织外贸推荐方案

**销售四段流** + **采购五段流** 统一到状态机驱动：

```
销售链                           采购链
──────                          ──────
SO (Sales Order)                PO (Purchase Order)
  │ 审批后冻结                      │ 审批后冻结
  ▼                                ▼
DO (Delivery Order) 发货单         GR (Goods Receipt) 到货/入库
  │ 仓储确认                        │ 仓储确认
  ▼                                ▼
IV_Sale (Sales Invoice)           IV_Purchase (Purchase Invoice)
  │ 财务确认 → 产生 AR 开放项       │ 财务确认 → 产生 AP 开放项
  ▼                                ▼
RC (Receipt) 收款                 PY (Payment) 付款
  │ 核销 AR                         │ 核销 AP
```

**关键字段设计**（借鉴 Odoo invoice_status 派生）：

```java
public enum InvoiceStatus { NO, PARTIAL, FULL, OVER }

// 订单行上派生字段
public InvoiceStatus getInvoiceStatus(SalesOrderItem item) {
    BigDecimal invoicedQty = invoiceItemMapper.sumByItem(item.getId());
    if (invoicedQty.compareTo(BigDecimal.ZERO) == 0) return NO;
    if (invoicedQty.compareTo(item.getQty()) < 0)   return PARTIAL;
    if (invoicedQty.compareTo(item.getQty()) == 0)  return FULL;
    return OVER;
}
```

**容差配置**（借鉴 SAP Tolerance Keys）：

```sql
CREATE TABLE t_erp_tolerance_config (
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    tolerance_key       VARCHAR(10) NOT NULL COMMENT 'PP/PS/DQ/DW/AN',
    scope_type          VARCHAR(20) COMMENT 'GLOBAL/FACTORY/PARTNER/MATERIAL',
    scope_ref_id        BIGINT,
    percent_limit       DECIMAL(5,2),
    amount_limit        DECIMAL(18,2),
    behavior            VARCHAR(20) COMMENT 'WARN/BLOCK/AUTO_APPROVE',
    PRIMARY KEY (id)
);
```

---

## 5. 领域 4：BOM 多级结构

### 5.1 四家做法对比

| 维度 | U8 / U9 | 金蝶云星空 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 基础多级 BOM | ✅ | ✅ | ✅ | ✅ |
| 按配置 BOM（Variant）| ⭐⭐（有限） | ⭐⭐⭐ | ⭐⭐⭐⭐（Super BOM + Variant Config）| ⭐⭐⭐⭐（Product Template + Variant）|
| 幻影件（Phantom）| ✅ | ✅ | ✅（BOM Item Cat. L）| ✅（Phantom BoM）|
| 替代料 | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐（需 OCA）|
| 损耗系数 | ✅ | ✅ | ✅（Scrap） | ✅（efficiency + waste）|
| BOM 有效期 | ✅ | ✅ | ✅（日期 + lot sizing）| ✅ |
| 按路由分 BOM | ❌ | ⭐ | ✅（Routing Allocation） | ✅ |
| **色×码矩阵 BOM** | ❌（需二开）| ⭐ | ✅（AFS 模块原生） | ✅（OCA product_matrix） |

### 5.2 SAP AFS 的亮点：格子 BOM（Grid BOM）

SAP Apparel & Footwear Solution 原生支持 **Color × Size 格子 BOM**：
- 主款一份 BOM
- 按颜色、尺码可各自覆盖用量
- 尺码放码规则（例如 L 比 M 多 5% 面料）

**这是针织服装的行业标配**——其他 ERP 都需要"每个色/码复制一份 BOM"，量大难维护。

### 5.3 Odoo 的亮点：递归 BOM + Phantom Flatten

Odoo 的 BOM 展开使用 `_bom_explode` 方法递归，幻影件自动 flatten（半成品不落工单、直接计算底层物料需求）。**源码级可学习**。

### 5.4 针织外贸推荐方案

**核心：多级结构 + 色码矩阵 + 版本化**

```sql
-- BOM 头表
CREATE TABLE t_erp_bom (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    bom_code        VARCHAR(32) NOT NULL,
    bom_version     INT NOT NULL DEFAULT 1,
    style_id        BIGINT NOT NULL COMMENT '款号',
    bom_type        VARCHAR(20) COMMENT 'NORMAL / PHANTOM / KIT',
    output_material_id BIGINT NOT NULL COMMENT '产出物料',
    output_qty      DECIMAL(18,4) NOT NULL DEFAULT 1 COMMENT '每套产出',
    valid_from      DATE,
    valid_to        DATE,
    status          CHAR(1) COMMENT '0草稿 1启用 2停用',
    PRIMARY KEY (id),
    UNIQUE KEY uk_code_ver (bom_code, bom_version)
);

-- BOM 行表
CREATE TABLE t_erp_bom_line (
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    bom_id             BIGINT NOT NULL,
    line_no            INT NOT NULL,
    material_id        BIGINT NOT NULL COMMENT '投入物料',
    child_bom_id       BIGINT COMMENT '下级 BOM（多级时引用）',
    base_qty           DECIMAL(18,4) NOT NULL COMMENT '基准用量（单位产出）',
    loss_rate          DECIMAL(5,4) DEFAULT 0 COMMENT '损耗率',
    is_phantom         CHAR(1) DEFAULT '0' COMMENT '幻影件',
    PRIMARY KEY (id),
    KEY idx_bom (bom_id)
);

-- 色码矩阵覆盖（借鉴 SAP AFS）
CREATE TABLE t_erp_bom_line_matrix (
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    bom_line_id        BIGINT NOT NULL,
    color_code         VARCHAR(32) COMMENT '色，空=全部色',
    size_code          VARCHAR(32) COMMENT '码，空=全部码',
    override_qty       DECIMAL(18,4) NOT NULL COMMENT '覆盖用量',
    PRIMARY KEY (id),
    UNIQUE KEY uk_line_color_size (bom_line_id, color_code, size_code)
);

-- 替代料
CREATE TABLE t_erp_bom_substitute (
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    bom_line_id        BIGINT NOT NULL,
    substitute_material_id BIGINT NOT NULL,
    priority           INT NOT NULL DEFAULT 1,
    ratio              DECIMAL(5,4) DEFAULT 1 COMMENT '1 kg 主料 = X kg 替代料',
    PRIMARY KEY (id)
);
```

**展开算法**（借鉴 Odoo `_bom_explode`）：

```java
public List<MaterialRequirement> explode(Long bomId, BigDecimal qty,
                                         String color, String size) {
    List<MaterialRequirement> result = new ArrayList<>();
    List<BomLine> lines = bomLineMapper.selectByBomId(bomId);
    for (BomLine line : lines) {
        // 1. 取色码覆盖用量，否则用 base_qty
        BigDecimal baseQty = resolveQty(line, color, size);
        // 2. 应用损耗系数
        BigDecimal needQty = baseQty.multiply(qty)
                                    .multiply(BigDecimal.ONE.add(line.getLossRate()));
        // 3. 幻影件递归
        if ("1".equals(line.getIsPhantom()) && line.getChildBomId() != null) {
            result.addAll(explode(line.getChildBomId(), needQty, color, size));
        } else {
            result.add(new MaterialRequirement(line.getMaterialId(), needQty));
        }
    }
    return result;
}
```

---

## 6. 领域 5：生产工单与 MRP

### 6.1 四家做法对比

| 维度 | U8 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| MRP 计算 | ✅（T + 提前期）| ✅ | **⭐⭐⭐⭐⭐（MRP Live, SAP 强项）**| ✅（schedule_date 驱动）|
| 工单 | MO | 生产任务单 | Production Order | mrp.production |
| 工单 × 工序 | ✅ | ✅ | ✅（Operations）| ✅（workorders）|
| 工序 × 工位 / 机台 | ⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐（Work Center）| ⭐⭐⭐⭐（Work Center）|
| 有限能力排产 | 弱 | 中（APS）| 强（SAP PP-DS）| 弱 |
| 外协 / 委外工序 | ✅ | ✅ | ✅（Subcontracting）| ✅（subcontract） |
| 倒冲（backflush）| ✅ | ✅ | ✅ | ✅ |
| 不合格品返工 | ⭐⭐⭐ | ⭐⭐⭐ | ⭐⭐⭐⭐ | ⭐⭐ |
| 工序完工报工 | ✅ | ✅ | ✅ | ✅ |

### 6.2 SAP 的亮点：MRP Live + Demand-Driven MRP

SAP S/4HANA 的 MRP Live 在 HANA 列存引擎上实时计算物料需求（秒级），而不是传统的"夜间跑批"。**对针织外贸**：同一客户可能每天加订单、改交期，夜批 MRP 来不及反应。

### 6.3 Odoo 的亮点：mrp 源码开放

Odoo 的工单-工序-工站模型完全开源可读：
- `mrp.bom` / `mrp.bom.line`
- `mrp.production` 工单
- `mrp.workorder` 工序
- `mrp.workcenter` 工站

源码结构清晰，可以直接搬运到我们的 Java 项目（当然是重写不是抄代码）。

### 6.4 针织外贸推荐方案

**核心：多级工单 + 色码拆分 + 外协工序 + 简化版 MRP**

```
SO (Sales Order)
  │
  ▼ 排产（按款号×色×码拆裂）
MO-L1 (成衣工单)
  │
  ├── 工序 1：缝合（自厂）
  ├── 工序 2：印花（外协）→ 外协单
  ├── 工序 3：后整（自厂）
  └── 工序 4：包装（自厂）
  │
  ▼ 依赖
MO-L2 (坯布工单) ← 如果自织
  │
  └── 工序：编织
  │
  ▼ 依赖
PO (纱线采购) ← 如果外购
```

**MRP 分级实现**（先实时计算，再上 Queue）：

```java
@Service
public class MrpService {
    /**
     * 简化版 MRP：不做 APS，按交期倒推提前期
     */
    public MrpResult calculate(SalesOrder so) {
        MrpResult result = new MrpResult();
        for (SalesOrderItem item : so.getItems()) {
            // 1. 展开 BOM（含色码）
            List<MaterialRequirement> reqs = bomService.explode(
                item.getBomId(), item.getQty(),
                item.getColorCode(), item.getSizeCode());
            // 2. 扣减现有库存
            for (MaterialRequirement req : reqs) {
                BigDecimal available = stockService.getAvailable(
                    req.getMaterialId(), so.getFactoryId());
                BigDecimal shortage = req.getQty().subtract(available);
                if (shortage.signum() > 0) {
                    // 3. 按物料类型生成建议单据（PO or MO）
                    MaterialType type = materialMapper.getType(req.getMaterialId());
                    if (type == RAW) result.addPoSuggestion(req, shortage);
                    if (type == WIP) result.addMoSuggestion(req, shortage);
                }
            }
        }
        return result;
    }
}
```

**外协工序**（借鉴 Odoo subcontract 模块）：

```sql
CREATE TABLE t_erp_produce_job_process (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    job_id          BIGINT NOT NULL,
    process_seq     INT NOT NULL,
    process_def_id  BIGINT NOT NULL,
    is_outsource    CHAR(1) DEFAULT '0',
    subcontractor_id BIGINT COMMENT '外协厂（外协时必填）',
    plan_qty        DECIMAL(18,4),
    done_qty        DECIMAL(18,4),
    status          CHAR(1),
    ...
);
```

---

## 7. 领域 6：多币种与汇率

### 7.1 四家做法对比

| 维度 | U8 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 币种数量限制 | 无 | 无 | 无 | 无 |
| 汇率类型（买入/卖出/中间/月末）| ✅ | ✅ | **⭐⭐⭐⭐（Exchange Rate Types）** | ⭐⭐⭐ |
| 下单锁定汇率 | ✅ | ✅ | ✅（Forward Contract） | ⭐⭐ |
| 汇兑损益自动计算 | ✅ | ✅ | ✅ | ✅ |
| 多本位币（集团）| ⭐⭐ | ⭐⭐⭐ | **⭐⭐⭐⭐⭐**（Group Currency + Local + Doc）| ⭐ |
| 汇率历史查询 | ✅ | ✅ | ✅ | ✅ |

### 7.2 SAP 的亮点：汇率类型矩阵

SAP 可以定义多种汇率类型（`TCURR` 表）并指定用于不同场景：
- `M` 期末汇率（资产负债表）
- `B` 银行买入
- `G` 银行卖出
- `P` 标准汇率（集团内部用）

**对针织外贸**：报价用 P、开票用 M、银行结汇用 B，差异体现为汇兑损益。

### 7.3 针织外贸推荐方案

```sql
-- 汇率表（借鉴 SAP TCURR）
CREATE TABLE t_erp_exchange_rate (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    from_currency   CHAR(3) NOT NULL,
    to_currency     CHAR(3) NOT NULL,
    rate_type       VARCHAR(10) NOT NULL COMMENT 'SPOT/QUOTE/INVOICE/CLOSE/BANK_BUY/BANK_SELL',
    rate_date       DATE NOT NULL,
    rate            DECIMAL(18,8) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_rate (from_currency, to_currency, rate_type, rate_date)
);

-- 订单存原币 + 本币双金额
ALTER TABLE t_erp_sales_order ADD COLUMN currency_code CHAR(3) NOT NULL;
ALTER TABLE t_erp_sales_order ADD COLUMN exchange_rate DECIMAL(18,8) NOT NULL COMMENT '下单锁定';
ALTER TABLE t_erp_sales_order ADD COLUMN rate_type VARCHAR(10) NOT NULL DEFAULT 'QUOTE';
-- salesitem.amount 原币 / amount_local 本币 两个字段
```

**汇率源**：一期手工维护或对接中国银行外汇牌价 API（每日一次）。

---

## 8. 领域 7：订单粒度与主从结构

### 8.1 四家做法对比

| 维度 | U8 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 订单主从层次 | 2 级（主-明细） | 2 级 | **3 级（Header-Item-Schedule Line）**| 2 级 |
| Schedule Line（交期分批） | ❌ | ❌ | ✅ | 需 OCA |
| 合约 / 框架协议 | ⭐⭐ | ⭐⭐⭐ | **⭐⭐⭐⭐（Outline Agreement）** | ⭐⭐ |
| 订单变更留痕 | ⭐⭐ | ⭐⭐ | ⭐⭐⭐⭐（Change Doc） | ⭐⭐⭐ |
| 订单状态机 | 配置 | 配置 | 强（Status Profile） | 配置 |
| 订单号编码规则 | 配置（前缀 + 流水）| 配置 | 配置 + 范围 | 序列 |

### 8.2 SAP 的亮点：三级结构 + Schedule Line

SAP SD 销售订单天然三级：
- **Header**（订单头）：客户、币种、付款方式、总金额
- **Item**（订单行）：款号、数量、价格
- **Schedule Line**（交期行）：同一 Item 可分多次交货（例如 500 件分 5/20 / 6/10 两批）

**对针织外贸**：日方客户常要求"分批交货"——500 件先交 200 件头批、后交 300 件尾批。不拆 Schedule Line 就只能拆单。

### 8.3 针织外贸推荐方案

**三级结构**（沿用 `01 §4.2 C02` 决策，具象化为 SAP 模式）：

```
SO Header (t_erp_sales_order)
├── SO Item (t_erp_sales_order_item) ← 一款一行
│   └── SO Schedule Line (t_erp_sales_order_schedule) ← 分批交期
└── SO Color-Size Matrix (t_erp_sales_order_material) ← 色×码
```

```sql
CREATE TABLE t_erp_sales_order_schedule (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    so_item_id      BIGINT NOT NULL,
    sched_seq       INT NOT NULL COMMENT '第几批',
    sched_qty       DECIMAL(18,4) NOT NULL,
    sched_delivery_date DATE NOT NULL,
    confirmed_qty   DECIMAL(18,4) DEFAULT 0 COMMENT 'ATP 确认数量',
    status          CHAR(1),
    PRIMARY KEY (id),
    KEY idx_item (so_item_id)
);
```

**合约/框架协议**（借鉴 SAP Outline Agreement）：

```sql
-- 一期不做完整合约，但预留字段
ALTER TABLE t_erp_sales_order ADD COLUMN agreement_id BIGINT COMMENT '框架协议（预留）';
ALTER TABLE t_erp_sales_order ADD COLUMN order_type VARCHAR(20) NOT NULL DEFAULT 'NORMAL'
  COMMENT 'NORMAL / AGREEMENT_CALLOFF（协议下单）/ SAMPLE（样品）';
```

**订单变更文档**（借鉴 SAP Change Doc）：

```sql
CREATE TABLE t_erp_order_change_doc (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    order_type      VARCHAR(20) COMMENT 'SO/PO',
    order_id        BIGINT NOT NULL,
    change_field    VARCHAR(100) NOT NULL COMMENT '改了什么字段',
    before_value    VARCHAR(500),
    after_value     VARCHAR(500),
    reason          VARCHAR(500),
    flowable_proc_id VARCHAR(64) COMMENT '如走了审批',
    operator_id     BIGINT,
    change_time     DATETIME,
    PRIMARY KEY (id),
    KEY idx_order (order_type, order_id)
);
```

---

## 9. 领域 8：审批流与状态机

### 9.1 四家做法对比

| 维度 | U8 | 金蝶 | SAP | Odoo |
| :-- | :-- | :-- | :-- | :-- |
| 审批流引擎 | 工作流中心 | 审批流（BOS）| Workflow (WF) + Fiori | Approval + Studio |
| 配置化 | ✅ | **⭐⭐⭐⭐（BOS 拖拽）**| ⭐⭐⭐ | ⭐⭐⭐ |
| 图形化建模 | 弱 | ✅ | ⭐（老 WF）/ ⭐⭐⭐（Business Workflow） | ⭐⭐ |
| 状态机 | 硬编码字段 | 状态流转配置 | **Status Profile + User Status** | 字段派生 |
| BPMN 2.0 | ❌ | ⭐⭐ | ⭐ | ⭐ |
| 并行 / 会签 | ✅ | ✅ | ✅ | ✅ |
| 委派 | ✅ | ✅ | ✅ | ✅ |
| 通知集成 | 企微 / 钉钉 | 企微 / 云之家 | SAPoffice | Odoo Bot |

### 9.2 SAP 的亮点：Status Profile（状态画像）

SAP 的状态管理支持两种维度：
- **System Status**（系统状态，硬编码）：如 `CRTD` 创建、`REL` 已释放
- **User Status**（自定义状态）：按业务配置的自由状态

每个状态有"允许的业务事务"（Business Transactions），进入某状态就自动禁止/允许某些操作。**这比"字段 status + 硬编码判断"优雅得多**。

### 9.3 Odoo 的亮点：派生字段 + Record Rules

Odoo 的状态多为派生字段（基于其他字段计算），避免状态字段手工维护脱节。例如发票的 `invoice_status` 根据 `amount_total` 和 `amount_residual` 派生。

### 9.4 针织外贸推荐方案

**沿用现有 Flowable + 引入 Status Profile 模式**

```sql
-- 状态画像配置
CREATE TABLE sys_status_profile (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    entity_type     VARCHAR(50) NOT NULL COMMENT 'SALES_ORDER/PRODUCE_JOB/...',
    status_code     VARCHAR(20) NOT NULL,
    status_name_zh  VARCHAR(50),
    status_name_ja  VARCHAR(50),
    is_initial      CHAR(1) DEFAULT '0',
    is_final        CHAR(1) DEFAULT '0',
    PRIMARY KEY (id),
    UNIQUE KEY uk_entity_status (entity_type, status_code)
);

-- 状态允许的事务
CREATE TABLE sys_status_transition (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    entity_type     VARCHAR(50) NOT NULL,
    from_status     VARCHAR(20) NOT NULL,
    to_status       VARCHAR(20) NOT NULL,
    action_code     VARCHAR(50) NOT NULL COMMENT 'APPROVE/REJECT/CANCEL/DELIVER/...',
    required_role   VARCHAR(50) COMMENT '操作人角色',
    flowable_key    VARCHAR(100) COMMENT '若需审批，走哪个流程',
    PRIMARY KEY (id),
    KEY idx_entity (entity_type, from_status)
);
```

**使用**（拦截业务方法）：

```java
@Aspect
public class StatusTransitionAspect {
    @Around("@annotation(stateChange)")
    public Object check(ProceedingJoinPoint pjp, StateChange stateChange) {
        // 1. 查当前状态 → 目标状态的 transition 是否合法
        // 2. 不合法：抛异常
        // 3. 合法 → 若 required_role 不满足当前用户：抛异常
        // 4. 合法 → 若有 flowable_key：启动审批流程而非直接改状态
        return pjp.proceed();
    }
}
```

**Flowable 流程清单**（一期实现）：

| 流程 key | 场景 |
| :-- | :-- |
| `so.approve` | 销售订单审批 |
| `so.change` | 订单改单审批（触发 §7 Change Doc） |
| `po.approve` | 采购订单审批 |
| `mo.release` | 工单释放 |
| `invoice.approve` | 发票审核 |
| `writeoff.approve` | 应收应付核销 |
| `style.version.approve` | 款号升版审批 |
| `sample.confirm` | 客户签样 |

---

## 10. 融合最佳实践的系统设计（总结）

### 10.1 每个领域我们采纳了谁

| 领域 | 主要借鉴 | 次要借鉴 | 针织特化 |
| :-- | :-- | :-- | :-- |
| 存货核算 | Odoo 分层架构 | U8 月结流程、SAP 估价区域 | 批次追溯 + 到港分摊 |
| 应收应付 | SAP 开放项模型 | Odoo 统一 account.move | 多币种账龄 |
| 三单核销 | SAP Tolerance Keys | Odoo invoice_status 派生 | 日方月结合并 |
| BOM 多级 | SAP AFS 格子 BOM | Odoo 递归展开 | 色×码矩阵 |
| 生产工单 | Odoo mrp 模块 | SAP MRP 概念 | 外协工序 |
| 多币种 | SAP 汇率类型 | 各家汇兑损益 | 日元报价 |
| 订单主从 | SAP 三级结构 | Odoo 变更文档 | 分批交货 |
| 审批流 | SAP Status Profile | Odoo 派生状态 | Flowable 实现 |

### 10.2 核心新增表汇总（基于本文的推荐）

| 表 | 作用 | 借鉴自 |
| :-- | :-- | :-- |
| `t_erp_ar_item` / `t_erp_ar_clearing` | 应收开放项 + 核销 | SAP BSID/BSAD |
| `t_erp_ap_item` / `t_erp_ap_clearing` | 应付开放项（对称）| 同上 |
| `t_erp_tolerance_config` | 三单匹配容差 | SAP Tolerance Keys |
| `t_erp_exchange_rate` | 汇率表 | SAP TCURR |
| `t_erp_bom_line_matrix` | BOM 色码覆盖 | SAP AFS Grid BOM |
| `t_erp_bom_substitute` | 替代料 | SAP/Odoo |
| `t_erp_sales_order_schedule` | 分批交期 | SAP Schedule Line |
| `t_erp_order_change_doc` | 订单变更 | SAP Change Doc |
| `sys_status_profile` / `sys_status_transition` | 状态画像 | SAP Status Profile |
| `t_erp_inventory_month_snapshot` | 存货月结 | 通用 |

### 10.3 财务"中间态"边界清单

**✅ 新 ERP 要做**：
- 销售开票（含 JP/CN 双模板、多币种）
- 应收开放项 + 核销（含汇兑损益）
- 采购发票接收
- 应付开放项 + 核销
- 成本核算（BOM 展开 + 料工费 + 分摊）
- 存货金额结转（月结）
- 计件工资计算

**❌ 新 ERP 不做**（交给专业财务软件 / 会计外包）：
- 总账 / 凭证生成
- 财务三大报表（资产负债表、利润表、现金流量表）
- 财务分析（费用率、毛利率）
- 合并报表 / 集团抵消
- 固定资产折旧
- 税务申报 / 金税接口

**✅ 但要输出**：
- 标准化的"业务事项明细表"（带会计期间），让财务导入 / 手工记账

---

## 11. 针织外贸特殊约束清单（四家都不原生支持的部分）

这些点**任何 ERP 都要二开**，我们一次设计到位：

| 约束 | 为什么四家都弱 | 我们的做法 |
| :-- | :-- | :-- |
| **款号 = 业务主键** | 通用 ERP 用物料编码，款号是附加字段 | `style_code` 一等公民（见 `02 §2`） |
| **色×码矩阵单据** | 通用 ERP 一款一行，色码作为变体 | `salesmaterial` 子表专做矩阵 |
| **样品 → 大货的款号继承** | 通用 ERP 无样品阶段 | `style.parent_style_id` 允许继承 |
| **中日双语业务数据** | 通用 ERP 多语言只管 UI | 主数据表 `_zh/_ja` 双字段 |
| **日方电子签章合法性** | 非针织专属，但日方必需 | e 签宝集成（见 `05 P5.4`）|
| **RSL / 禁用物质 / JIS 检测** | 行业强标准 | 专用表（见 `03 §5.4`）|
| **Delta-E 色差分级** | 通用 ERP 无 | `standardColor.default_delta_e` 分级 |
| **缩水率、克重等物性** | 通用 ERP 无 | style 表专门字段 |
| **客户签样流程（头样-大货）** | 行业流程 | Flowable `sample.confirm` |

---

## 12. 对标学习的推进路径（给技术团队）

### 12.1 学习优先级

1. **必读源码**（1 周）：Odoo `mrp` 和 `stock` 模块核心文件
2. **必看文档**（3 天）：SAP Help Portal 的 SD / MM / PP 三大模块概念章节
3. **参考实战**（持续）：U8 / 金蝶的中式单据习惯，保持用户体验不产生文化断裂

### 12.2 建议资源

| 资源 | 用途 |
| :-- | :-- |
| Odoo 18 Community 源码（github.com/odoo/odoo）| 读 mrp, stock, account, sale 模块 |
| SAP Help Portal | 搜索 "Apparel and Footwear Solution" |
| 用友开发者中心 | BQ 报表、EAI 接口文档（供参考，不做集成） |
| 金蝶 BOS 开发手册 | 元数据驱动的思想 |

### 12.3 团队技术储备

| 角色 | 建议学习内容 |
| :-- | :-- |
| 架构师 | SAP 三级订单结构、SAP 估价区域、Odoo ORM 思想 |
| 后端高级 | Odoo mrp 源码、SAP tolerance 概念 |
| 后端中级 | U8 / 金蝶的用户界面习惯 |
| 前端 | SAP Fiori 设计语言（列表-详情-动作的典范） |

---

## 13. 遗留问题

| ID | 问题 | 默认决策 | 紧迫度 |
| :-- | :-- | :-- | :-- |
| RE-001 | 存货核算是否支持多算法并存（按物料类型分配算法）| ✅ 支持 | 🔴 P0 |
| RE-002 | 应收开放项的 currency_code 是否允许客户级多币种 | ✅ 支持 | 🟡 P1 |
| RE-003 | BOM 色码矩阵是否做"放码规则"自动派生 | ❌ 一期不做，二期考虑 | 🟢 P2 |
| RE-004 | Schedule Line 是否纳入一期 | ✅ 纳入 | 🔴 P0 |
| RE-005 | 状态画像是否全面替换现有 `status` 字段 | ❌ 新模块用，旧模块保留 | 🟡 P1 |
| RE-006 | 是否引入订单改单重审（金额 10%、数量 5%）| ✅ 是（沿用 01 C10） | 🟡 P1 |
| RE-007 | 到港成本分摊算法（金额/体积/重量）| 默认金额分摊 | 🟡 P1 |
| RE-008 | 汇率来源（手工 / API）| 一期手工 | 🟢 P2 |
| RE-009 | 是否实现 BOM 替代料 | 一期不做 | 🟢 P2 |
| RE-010 | MRP 频率（实时 / 夜批 / 混合）| 默认按用户触发 + 每日一次调度 | 🟡 P1 |

---

## 14. 假设（本文档基于的假设）

| 假设 | 来源 | 校正方式 |
| :-- | :-- | :-- |
| 企业财务走"业务财务分家"：业务 ERP 做业务层财务，不做总账 | 用户明确 | - |
| 四家对标 ERP 各家优劣基于公开资料 + 作者通识 | 行业通识 | 团队深入学习后校正 |
| 针织外贸需要色码矩阵、批次追溯、电子签章 | 行业通识 | 业务 Owner 确认 |
| 不集成任何财务软件 | 用户明确 | - |

---

**关联交付物**：`01-requirements` · `02-architecture` · `03-compliance` · `05-wbs-risk` · `99-assumptions`
**本文档字数**：约 6200 字 | **表格**：28 张 | **对标 ERP**：4 家 | **业务领域**：8 个 | **借鉴新表**：10 张
