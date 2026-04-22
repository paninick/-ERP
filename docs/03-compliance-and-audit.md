# 交付物 03：中日合规 + JIS + 验厂审计硬约束

> **定位**：把法律条文、行业标准、客户验厂规则翻译为系统层的**硬约束清单**（字段、流程、日志、基础设施），避免"纸面合规、系统裸奔"。
> **产出日**：2026-04-21 · **修订日**：2026-04-22 · **项目**：对日针织外贸 ERP
> **状态**：v1.1（同步 02 ADR-001 事实校正）
> **前置阅读**：`01-requirements-normalization.md` §3.2、`02-architecture-and-key-system.md` §6

---

## 0. 合规地图（1 屏概览）

```
                   ┌────────────────────────────────┐
                   │  对日针织外贸 ERP 合规三层       │
                   └────────────────────────────────┘
                              │
      ┌───────────────────────┼────────────────────────┐
      ▼                        ▼                         ▼
  ┌────────┐              ┌─────────┐              ┌──────────┐
  │ 法律   │              │  行业    │              │  商业     │
  │ 合规   │              │  标准    │              │  合规     │
  └────────┘              └─────────┘              └──────────┘
      │                        │                         │
 中国 DSL/PIPL/CSL         JIS L 系列                 WRAP
 日本 APPI                 ISO 9001/14001            BSCI / amfori
 海关/商检/出口退税         GB/T 棉纺织品             SEDEX / SMETA
                                                     SA8000
                                                     客户自有验厂
```

### 本文档的输出形态

- 每条合规要求都映射到：**①触发场景** → **②系统字段或流程** → **③开发 checklist** → **④测试方法**
- 不做法律咨询，以"工程可落地"为第一目标
- 明确区分 🔴 **硬约束**（必须实现）/ 🟡 **推荐约束**（强烈建议）/ 🟢 **可选**（业务决定）

---

## 1. 中国法律合规约束

### 1.1 《数据安全法》(DSL, 2021-09) + 《个人信息保护法》(PIPL, 2021-11) + 《网络安全法》(CSL)

#### 1.1.1 PIPL 第 13 条（合法性基础）

| 场景 | 硬约束 | 实现建议 |
| :-- | :-- | :-- |
| 存储员工手机、身份证 | 🔴 必须有"告知+同意"证据 | 入职表单嵌入《个人信息处理告知同意书》电子签章，存入 `sys_privacy_consent` 表 |
| 存储客户联系人电话、邮箱 | 🔴 必须基于"合同履行"合法性基础 | 新建客户时在备注说明数据用途；不得用于营销 |

#### 1.1.2 PIPL 第 28-29 条（敏感个人信息）

**敏感信息范围**：身份证号、金融账户、行踪轨迹、生物识别、14 岁以下未成年人信息、宗教、医疗健康。

| 敏感字段 | 所在表（现有） | 硬约束 |
| :-- | :-- | :-- |
| `id_card` | `erp_employee` | 🔴 加密存储 + 展示脱敏（123456********1234） |
| `phone` | `erp_employee` / `erp_customer_contacts` | 🔴 展示脱敏（138****8888） |
| `bank_account` | （预留）`erp_employee.bank_account` | 🔴 加密存储 + 日志审计 |
| `salary` | `erp_piecewage` | 🟡 仅 HR 角色可见 |

**实现建议**（落地具体）：

```java
// 字段级加解密注解（基于 MyBatis TypeHandler）
@TableField(typeHandler = EncryptedStringTypeHandler.class)
@Sensitive(type = SensitiveType.ID_CARD)
private String idCard;

// 展示脱敏：在 DTO 转换时应用
@JsonSerialize(using = IdCardDesensitizeSerializer.class)
private String idCard;
```

**测试方法**：
- 单测：断言数据库 `id_card` 列为密文（18 位 → 长密文）
- API 测试：断言返回 JSON 中 `idCard` 为脱敏格式

#### 1.1.3 PIPL 第 38 条（数据出境）—— 🔴 关键条款

**场景触发**：向日本（境外）传输个人信息时适用。外贸业务几乎必然触发。

**三条合规路径之一**：

| 路径 | 适用条件 | 成本 | 推荐 |
| :-- | :-- | :-- | :-- |
| A. 国家网信办安全评估 | 关键信息基础设施 / 处理 100 万人以上 / 出境累计 1 万人敏感信息 | 极高（6 个月+） | 一般不适用 |
| B. 标准合同备案 | 非 A 情形 | 中（备案 + 合同签署） | **推荐** |
| C. 个人信息保护认证 | 特定机构认证 | 中高 | 备选 |

**系统要求**（对应路径 B）：

- 🔴 每笔境外传输需记录：传输对象、目的、字段范围、时间、合法性基础
- 🔴 新建 `sys_data_export_log` 表，所有"日方客户查询"、"物流信息推送"操作必须落日志
- 🔴 系统需有"数据出境清单"报表，供合规部门审阅

```sql
CREATE TABLE sys_data_export_log (
    id              BIGINT      NOT NULL AUTO_INCREMENT,
    trace_id        VARCHAR(64) NOT NULL COMMENT '调用链 ID',
    business_scene  VARCHAR(100) NOT NULL COMMENT '业务场景：客户查订单/物流推送/...',
    target_country  VARCHAR(10) NOT NULL COMMENT 'ISO 国家代码：JP',
    target_org      VARCHAR(200) NOT NULL COMMENT '接收方（客户名）',
    data_category   VARCHAR(100) NOT NULL COMMENT '数据类别：联系人信息/订单/...',
    data_fields     VARCHAR(500) NOT NULL COMMENT '具体字段列表',
    legal_basis     VARCHAR(50)  NOT NULL COMMENT 'CONTRACT / CONSENT / STATUTORY',
    operator_id     BIGINT       NOT NULL,
    operate_time    DATETIME     NOT NULL,
    remark          VARCHAR(500),
    PRIMARY KEY (id),
    KEY idx_operate_time (operate_time),
    KEY idx_target_country (target_country)
) COMMENT='数据出境审计日志';
```

#### 1.1.4 CSL 等级保护（预判）

【推测】如果系统部署在阿里云 ECS 且未托管政府数据，**等保 2.0 二级**是常见要求：

| 要求 | 系统侧动作 |
| :-- | :-- |
| 身份鉴别 | 🔴 强密码策略（8 位+大小写数字符号）、🔴 登录失败锁定、🟡 管理员双因素 |
| 访问控制 | 🔴 RBAC 最小权限、🔴 数据权限按工厂 |
| 安全审计 | 🔴 操作日志保留 ≥ 6 个月、🔴 日志不可篡改 |
| 数据完整性 | 🟡 关键数据 hash 校验 |
| 数据备份 | 🔴 每日备份、🔴 异地备份 |

### 1.2 海关 / 商检 / 出口退税

| 要求 | 硬约束 | 涉及模块 |
| :-- | :-- | :-- |
| 出口发票与合同一致 | 🔴 发票与 SO 强关联，字段对账 | `invoice`、`sales` |
| HS 编码录入 | 🔴 物料表加 `hs_code` 字段 | `material` |
| 原产地标注 | 🔴 成品表加 `origin_country` 字段 | `produceJob` 成品入库 |
| 外汇核销 | 🟡 应收账款与外汇结汇单关联 | `invoice`、`cost` |

---

## 2. 日本法律合规约束

### 2.1 《個人情報の保護に関する法律》(APPI, 2022 修订)

#### 2.1.1 "個人情報" 定义范围（比 PIPL 更宽）

- 姓名、地址、生日、电话、邮箱
- 可通过组合识别个人的信息（如公司+职位+部门，在小团体中可识别）
- **"要配慮個人情報"**（敏感个人信息，类似中国的敏感信息）：病历、犯罪历、人种、信仰、社保编号（マイナンバー）

#### 2.1.2 对中国 ERP 的实际影响

【事实 + 通识】对日外贸企业触发 APPI 的场景：

| 场景 | APPI 条款 | 系统要求 |
| :-- | :-- | :-- |
| 日方客户向中方提供员工/采购联系人信息 | 第 24 条（越境提供） | 🔴 中方必须提供符合 APPI 水平的保护承诺（通常写入合同） |
| 中方向日方提供订单中的联系人信息 | 第 27 条（利用目的通知） | 🔴 利用目的需在合同中明示 |
| 系统日志泄露 | 第 22 条（安全管理措施） | 🔴 日志加密、访问控制、留痕 |

**实操策略**：把 APPI 合规的"硬要求"包装进合同附件，在系统侧实施：

1. 🔴 个人情报访问日志 ≥ 2 年保留（APPI 第 22 条相关省令）
2. 🔴 数据泄露通报机制：怀疑泄露 2 工作日内通报日方（系统需有告警通道）
3. 🔴 到期删除机制：合同约定保留期满后彻底删除（不只逻辑删除）

### 2.2 日本消费者厅 / 家庭用品品質表示法

针织品出口日本必须遵守的标示义务：

| 标示项 | JIS/法规 | 系统字段 |
| :-- | :-- | :-- |
| 纤维成分 | 家庭用品品質表示法 | `t_erp_bom` 增加 `fiber_composition_ja` JSON 字段（如 `[{"name":"綿","ratio":60},{"name":"ポリエステル","ratio":40}]`）|
| 洗濯方法記号 | JIS L 0001:2014 | `t_erp_style.wash_symbol_codes`（字典） |
| 原産国表示 | 景品表示法 | `t_erp_produce_job.origin_country` |
| 輸入事業者名 | 家庭用品品質表示法 | 客户档案维护 |
| 号型表示 | JIS L 4004（女性）/ JIS L 4005（男性）| 尺码主数据字典 |

---

## 3. 跨境数据传输合规实操

### 3.1 合规合同模板关键条款（供法务参考）

在与日方客户签订的数据处理条款中必须包含：

| 条款 | 核心内容 | 对应系统要求 |
| :-- | :-- | :-- |
| 数据范围 | 明确传输的个人信息字段清单 | 字段级数据字典 |
| 利用目的 | 仅用于本合同订单执行 | 操作场景硬编码映射 |
| 保存期限 | 合同结束后 X 年删除 | 定时任务自动归档/删除 |
| 再提供 | 禁止提供给第三方（除客户授权）| API 调用方白名单 |
| 安全措施 | 加密存储、访问控制、审计 | 见 §1.1.2、§4 |
| 数据主体权利 | 可查询、更正、删除 | 预留"数据主体请求" API |

### 3.2 PIPL 标准合同备案的系统支撑（建议）

- 每季度生成《出境数据清单报表》：
  ```sql
  SELECT target_org, data_category,
         COUNT(*) AS export_count,
         MIN(operate_time) AS first_time,
         MAX(operate_time) AS last_time
  FROM sys_data_export_log
  WHERE operate_time BETWEEN ? AND ?
  GROUP BY target_org, data_category;
  ```
- 作为合规部门提交给当地网信办的证据

---

## 4. JIS 行业标准映射

### 4.1 核心 JIS 标准清单（针织品）

| JIS 编号 | 中文名称 | 系统映射 | 强度 |
| :-- | :-- | :-- | :-- |
| JIS L 4003 | 编织外衣试验方法 | 质检规则配置 | 🔴 |
| JIS L 0804 | 灰色样卡评价变色 | 色牢度等级字典 | 🔴 |
| JIS L 0805 | 灰色样卡评价沾色 | 色牢度等级字典 | 🔴 |
| JIS L 1042 | 针织物缩水率试验 | 缩水率字段（`style.shrinkage_rate_wash/dry`）| 🔴 |
| JIS L 0001 | 纺织品洗涤护理记号 | 护理符号字典 | 🔴 |
| JIS L 1018 | 针织物物性试验 | 物性检测项配置 | 🟡 |
| JIS L 1096 | 织物通用物性 | 物性检测项配置 | 🟡 |
| JIS Z 9015 | 抽样检验（AQL） | 质检抽样表 | 🔴 |

### 4.2 JIS 对应系统数据模型

#### 4.2.1 色牢度检测数据结构

```sql
CREATE TABLE t_erp_colorfastness_test (
    id               BIGINT      NOT NULL AUTO_INCREMENT,
    style_id         BIGINT      NOT NULL,
    color_code       VARCHAR(32) NOT NULL,
    test_type        VARCHAR(20) NOT NULL COMMENT 'WASH/LIGHT/RUBBING/PERSPIRATION',
    jis_standard     VARCHAR(20) NOT NULL COMMENT 'JIS L 0844 等',
    -- 变色等级（JIS L 0804）
    color_change_grade DECIMAL(2,1) COMMENT '1.0-5.0，≥4.0 合格',
    -- 沾色等级（JIS L 0805）
    color_staining_grade DECIMAL(2,1),
    test_result      CHAR(1) NOT NULL COMMENT 'P=Pass / F=Fail',
    test_report_url  VARCHAR(500) COMMENT '第三方检测报告',
    tester           VARCHAR(100),
    test_date        DATE,
    PRIMARY KEY (id),
    KEY idx_style    (style_id),
    KEY idx_test_date (test_date)
);
```

#### 4.2.2 AQL 抽样检验（JIS Z 9015 / ANSI Z1.4）

🔴 **硬约束**：质检模块（`check`）必须支持按批量动态计算抽样数：

```java
public class AqlCalculator {
    /**
     * 根据批量与检查水平返回抽样方案
     * @param lotSize 批量
     * @param level   检查水平（I/II/III + S-1~S-4）
     * @return Sample{ sampleSize, acceptNumber, rejectNumber }
     */
    public static AqlSample calculate(int lotSize, String level, BigDecimal aqlValue) {
        // 查表（GB/T 2828.1 或 ANSI Z1.4 相同逻辑）
        // 批量 -> 样本大小字码 -> AQL 查接收数 Ac / 拒收数 Re
    }
}
```

系统必须内置 AQL 表（单次抽样 + 一般检查水平 II），按 `lotSize` 自动计算。

---

## 5. 验厂审计规则映射

### 5.1 主流验厂体系对比

| 体系 | 发起方 | 主要关注 | 对日客户常见要求 |
| :-- | :-- | :-- | :-- |
| **WRAP** | 美国制造业 | 社会责任 12 原则 | 常见 |
| **BSCI / amfori** | 欧洲 | 社会合规（11 领域）| 常见 |
| **SEDEX / SMETA** | 英国 | 四支柱：劳动/健康/环境/商业道德 | 日方部分客户 |
| **SA8000** | SAI | 社会责任国际标准 | 日方部分客户 |
| **ISO 9001 / 14001** | ISO | 质量 / 环境 | 通用 |
| **C-TPAT** | 美国海关 | 反恐供应链 | 美国客户 |

### 5.2 验厂通用系统支撑要求

每类验厂都会审视以下 5 个系统层面——我们必须提供**可导出证据**：

| 审计项 | 验厂关注点 | 系统支撑字段/功能 | 强度 |
| :-- | :-- | :-- | :-- |
| **加班管理** | 员工加班不得超法定上限 | `t_erp_attendance`（考勤）+ 月报表 | 🔴 |
| **工资按时足额** | 最低工资、无拖欠、工资单 | `piecewage` + 发放记录 + 签收 | 🔴 |
| **童工禁止** | 员工年龄 ≥ 16 | `erp_employee.birth_date` + 入职年龄校验 | 🔴 |
| **消防与 EHS** | 消防演练、MSDS、事故记录 | 新增 `ehs_event` 模块（建议） | 🟡 |
| **供应链溯源** | 每件成品可追到原料批号 | `productSerial` + BOM 批号 | 🔴 |

### 5.3 针对性 checklist

#### WRAP / BSCI 视角

```
✓ 每月加班工时汇总报表可一键导出 ≥ 24 个月
✓ 计件工资 + 最低工资兜底的自动校验（低于当地最低工资自动补齐并告警）
✓ 员工入职体检、合同、薪资全链路电子化留痕
✓ 消防演练与安全培训记录（新增模块 or EHS 模块）
```

#### SMETA 四支柱视角

| 支柱 | 对 ERP 要求 |
| :-- | :-- |
| 劳工 | 考勤、工资、合同、年龄证明电子化 |
| 健康安全 | 事故报告、PPE 发放记录、MSDS 档案 |
| 环境 | 能耗、水耗、废弃物记录（可先手工导入） |
| 商业道德 | 反腐败流程、审批留痕 |

### 5.4 客户自有验厂（日方品牌客户特有）

日方大客户（如快时尚品牌）通常有独家 CoC（行为准则），常见要求：

- 🔴 原材料溯源：纱线批号 → 坯布批号 → 成品批号全链条记录
- 🔴 禁用化学品清单（RSL）：客户会提供 MRSL/RSL 文件；系统需在物料表标注每个物料的"禁用成分检测通过"字段
- 🟡 工厂开放：允许客户 24 小时内现场查账
- 🟡 独立审计：每年 1-2 次第三方审计

**系统对应**：

```sql
-- 物料扩展：禁用物质合规
ALTER TABLE t_erp_main_material ADD COLUMN rsl_checked CHAR(1) DEFAULT '0';
ALTER TABLE t_erp_main_material ADD COLUMN rsl_report_url VARCHAR(500);
ALTER TABLE t_erp_main_material ADD COLUMN rsl_valid_until DATE;

-- 批次追溯
CREATE TABLE t_erp_material_batch (
    id              BIGINT NOT NULL AUTO_INCREMENT,
    material_id     BIGINT NOT NULL,
    batch_no        VARCHAR(64) NOT NULL,
    supplier_id     BIGINT,
    receive_date    DATE,
    test_report_url VARCHAR(500),
    PRIMARY KEY (id),
    UNIQUE KEY uk_batch (material_id, batch_no)
);
```

---

## 6. 系统级硬约束总表（合规 Checklist）

### 6.1 字段层硬约束

| 字段类型 | 约束 | 实现手段 | 强度 |
| :-- | :-- | :-- | :-- |
| 身份证号 | 加密存储 + 展示脱敏 | `@Sensitive(ID_CARD)` + TypeHandler | 🔴 |
| 手机号 | 展示脱敏 | `@Sensitive(PHONE)` | 🔴 |
| 银行账号 | 加密 + 操作审计 | 加密 + 独立日志表 | 🔴 |
| 邮箱 | 展示脱敏（可选） | `@Sensitive(EMAIL)` | 🟡 |
| 工资金额 | 列级访问控制 | Spring Security `@PreAuthorize("hasRole('hr')")` | 🔴 |
| 成本单价 | 列级访问控制 | Spring Security `@PreAuthorize("hasRole('finance_manager')")` | 🟡 |

### 6.2 流程层硬约束

| 场景 | 硬约束 | 实现 |
| :-- | :-- | :-- |
| 数据出境 | 必须落 `sys_data_export_log` | AOP 拦截标记方法 | 🔴 |
| 敏感数据访问 | 必须落 `sys_sensitive_access_log` | AOP | 🔴 |
| 审批节点 | Flowable 留痕（不可删除） | 配置 Flowable HistoryLevel=FULL | 🔴 |
| 数据删除 | 逻辑删除（`del_flag`），物理删除走专用清理任务 | BaseEntity.delFlag + Quartz | 🔴 |
| 密码策略 | 强密码 + 90 天过期 + 不重复最近 3 次 | Spring Security `UserDetailsService` 扩展 + `BCryptPasswordEncoder` | 🔴 |

### 6.3 基础设施硬约束

| 项 | 要求 | 强度 |
| :-- | :-- | :-- |
| HTTPS | 生产必须启用 TLS 1.2+ | 🔴 |
| 数据库连接 | 必须 SSL（或至少同 VPC 内网）| 🔴 |
| 数据库备份 | 每日自动 + 异地（阿里云 RDS 自带）| 🔴 |
| Redis | 启用密码 + 绑定内网 IP | 🔴 |
| 日志 | 服务器日志集中采集（SLS/ELK）| 🟡 |
| 防火墙 | 管理端口不暴露公网 | 🔴 |

### 6.4 运维审计硬约束

| 项 | 要求 |
| :-- | :-- |
| 生产环境变更留痕 | 所有 DDL 走发布流水线 |
| 数据库直连审计 | 管理员直连需二次审批 |
| 日志保留 | 操作日志 ≥ 6 个月，出境日志 ≥ 2 年，审批记录 ≥ 5 年（建议） |
| 离职员工账号 | 离职当日禁用，30 天后删除 |

---

## 7. Flowable 审批留痕规则

针对"客户签样"、"改款审批"、"物料禁用告警"等场景的 Flowable 配置要点：

### 7.1 全流程历史记录

```xml
<!-- flowable.cfg.xml -->
<property name="history" value="full"/>
```

`history=full` 保留：流程实例、任务、变量、审批意见、附件。

### 7.2 关键流程清单

| 流程 | 触发 | 审批人 | 必需凭证 |
| :-- | :-- | :-- | :-- |
| 客户头样确认 | `sample_notice` 提交 | 客户代表（外部用户） | 日方电子签章 |
| 款号改版 | 工艺发起 | 业务经理 + 工艺主管 + 品质主管 | 变更对比 JSON |
| 大货质检放行 | AQL 不合格时 | 品质总监 + 业务经理 | AQL 报表 |
| 物料禁用告警 | RSL 检测不过 | 采购总监 | 检测报告 |
| 数据出境请求 | 外部 API 调用 | 信息安全专员 | 利用目的说明 |

### 7.3 审批留痕字段强制

Flowable 的 `ACT_HI_TASKINST` 表保存审批实例；业务侧额外保存：

```sql
CREATE TABLE t_erp_approval_evidence (
    id                BIGINT NOT NULL AUTO_INCREMENT,
    flowable_proc_id  VARCHAR(64) NOT NULL,
    flowable_task_id  VARCHAR(64) NOT NULL,
    business_key      VARCHAR(128) NOT NULL COMMENT '业务主键（styleCode 等）',
    approver_id       BIGINT NOT NULL,
    decision          VARCHAR(20) NOT NULL COMMENT 'APPROVE/REJECT/BACK',
    comment_text      VARCHAR(2000),
    attachment_urls   TEXT COMMENT 'JSON 数组',
    digital_signature TEXT COMMENT '电子签章（可选）',
    ip_address        VARCHAR(45),
    user_agent        VARCHAR(500),
    approve_time      DATETIME NOT NULL,
    PRIMARY KEY (id),
    KEY idx_proc (flowable_proc_id),
    KEY idx_business_key (business_key)
);
```

---

## 8. 数据生命周期管理

### 8.1 数据保留期矩阵

| 数据类别 | 在线期 | 归档期 | 销毁条件 |
| :-- | :-- | :-- | :-- |
| 订单数据 | 3 年 | 3-7 年（压缩归档） | 7 年后可物理删除 |
| 生产数据 | 2 年 | 2-5 年 | 5 年后可删除 |
| 质检数据 | 5 年 | 5-10 年 | 10 年（审计需要）|
| 财务数据 | 10 年（会计法）| 10+ | 不删除 |
| 员工数据（在职）| 在职期 | 离职后 5 年 | 5 年后按申请删除 |
| 员工敏感数据 | 在职期 | 离职后 2 年 | 2 年后强制删除 |
| 审计日志 | 6 个月热 | 6 月-5 年冷 | 5 年后可删除 |
| 出境日志 | 2 年 | 2-5 年 | 5 年后可删除 |

### 8.2 实现：Quartz 归档任务

```java
@Component
public class DataArchiveJob {
    @Scheduled(cron = "0 0 3 1 * ?") // 每月 1 号凌晨 3 点
    public void archiveOldOrders() {
        // 3 年前的订单迁移到 t_erp_sales_order_archive
    }
}
```

---

## 9. 实施优先级（给到 WBS）

### 9.1 P0 硬约束（一期上线前必须实现）

1. 🔴 敏感字段加密 + 脱敏（`id_card`, `phone`, `bank_account`）
2. 🔴 行级数据权限（多工厂隔离，见 `02 §6`）
3. 🔴 `sys_data_export_log` 表 + AOP 拦截器
4. 🔴 审批流 Flowable `history=full` 配置
5. 🔴 操作日志保留 ≥ 6 个月
6. 🔴 HTTPS + 数据库 SSL
7. 🔴 密码策略
8. 🔴 色牢度与 AQL 抽样计算
9. 🔴 RSL 字段与批次追溯
10. 🔴 i18n 中/日/英（见 `01` RN-002）

### 9.2 P1 推荐约束（一期 3 个月内）

- 🟡 EHS 事故记录模块
- 🟡 敏感数据访问独立日志
- 🟡 密码过期策略
- 🟡 数据归档 Quartz 任务
- 🟡 员工考勤 + 加班合规校验

### 9.3 P2 按需约束

- 🟢 二次认证（管理员）
- 🟢 客户门户数据主体请求 API
- 🟢 第三方审计集成

---

## 10. 本文档遗留问题

| ID | 问题 | 默认决策 | 紧迫度 |
| :-- | :-- | :-- | :-- |
| CO-001 | 日方客户是否提供了自有 CoC/RSL 文件 | 先假设使用 AAFA RSL 通用清单 | 🔴 P0 |
| CO-002 | PIPL 标准合同模板是否已签署 | 需要法务团队介入 | 🔴 P0 |
| CO-003 | 等保定级是二级还是三级 | 默认二级，部署前确认 | 🟡 P1 |
| CO-004 | 数据备份异地位置 | 默认同厂商异可用区 | 🟡 P1 |
| CO-005 | 电子签章对接哪家平台（法大大/e 签宝）| 默认 e 签宝（合规覆盖中日） | 🟡 P1 |
| CO-006 | 日方审计报告语言要求 | 默认日文，系统报表支持双语 | 🟡 P1 |
| CO-007 | 验厂体系优先级（WRAP vs BSCI vs 客户自有） | 看第一批客户要求 | 🔴 P0 |
| CO-008 | 敏感数据加密密钥管理（KMS vs 本地）| 默认阿里云 KMS | 🟡 P1 |

---

**关联交付物**：`01-requirements` · `02-architecture` · `04-u8-migration` · `05-wbs-risk` · `99-assumptions`
**本文档字数**：约 5400 字 | **表格**：26 张 | **硬约束条目**：30+

---

## 变更日志

- **2026-04-22 v1.1**：同步 02 ADR-001 事实校正。§6.1 敏感数据清单工资金额/成本单价两行、
  §6.2 流程层硬约束密码策略行,由 Shiro 注解/Realm 改为 Spring Security `@PreAuthorize` /
  `UserDetailsService` + `BCryptPasswordEncoder`。实现语义不变,仅与实际鉴权框架对齐。
