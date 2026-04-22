# 11 · Codex PR Review Brief v3(触发档)

> **定位**:Codex PR review 的**启动 brief**。任何一次对 `D:/erp/RuoYi-Vue` 的对抗式 / 二次 review 都以本文档为输入,保证跨会话一致性。
> **产出日**:2026-04-22 · **修订日**:2026-04-22 · **版本**:v3.1(baseline 推进至最新 HEAD + Slice A/D 扩展)
> **对应分支**:`appmod/java-upgrade-20260421103946`
> **对应 baseline**:`master (56dc40be) .. HEAD (ae2e5033)`
> **对应 scope**:`15 commits / 88 files / +4169 -2591`(已由 `git diff` 验证)

---

## 0. 使用说明

### 0.1 本文档用法

任何 AI reviewer(Codex / 其他 Claude session / 人类)接到本项目 PR review 任务时:
1. **读本 brief 全文** ← 从这里开始
2. 读引用的 docs/00-10 获取项目语境
3. 执行 §2 的 7 个 slice(按 §3 优先级)
4. 产出写入 `docs/11-codex-review-findings-v{N}.md`

### 0.2 与前序版本关系

| 版本 | 背景 |
| :-- | :-- |
| v1(仅 Codex 提出) | 抓到"仓库无 EasyExcel"关键点;缺 ADR-001 校正、phase14 冲突、业务量级、命名历史债等 context |
| v2(Codex 中间迭代) | 加入部分微调 |
| **v3(本档)** | 吸收 8 条盲区修复 + 6 条业务执行微调,可跑版 |

---

## 1. Scope 与过滤规则(固定)

### 1.1 Baseline(不得变更)

```
base: master (56dc40be Sprint 2: 流程打通 - 下推工作流 & 库存锁定机制)
head: HEAD (ae2e5033 fix(mapper): 修 BillNoGenerator 接入后暴露的 2 处 Mapper 老 bug)
远程: erp/main (已同步 HEAD,所有 commit 已 push)
```

### 1.2 15 个 commits(review 对象)

```
ae2e5033  fix(mapper): 修 BillNoGenerator 接入后暴露的 2 处 Mapper 老 bug  ← Slice D P0 样本
8a4fb34b  feat(erp): 接入 BillNoGenerator 到 8 个业务单据 Service        ← Slice A 扩展
685c9b0c  refactor(erp): BillNoGenerator 改用 StringRedisTemplate         ← Slice A 专项
3ee631da  fix(erp): 部署验证踩出的 4 类阻塞问题
4372f289  security: 外化 MySQL/上传路径/Redis 至环境变量                  ← Slice B
b7832c2d  fix(sql): 修复 phase9-11/sprint2 反引号缺失与字符集             ← Slice C 扩展
9f289fe3  feat(erp): 补齐 ErpInventory CRUD 控制器与 API
7700f8f5  refactor(ui): 列表页 UI/UX 重构 + 主题引擎                      ← Slice E
ce4d6151  feat(ui): 前端 i18n 三语言                                     ← Slice E
85476b0c  chore(git): ignore local Claude cache 与 sql/.backup
243ab38b  chore(sql): phase16 ERP菜单注册 + README + 归档                 ← Slice C
237262ff  feat(erp): P2 甘特图 + 损耗管控
e2aefa40  feat(erp): P1 拼接SOP/染整/RBAC                                 ← Slice C
cd03c861  feat(erp): P0 缺陷/JIS/工序放行
6dfd59a3  security: 外化 JWT / Redis / Druid 凭据                         ← Slice B
```

### 1.3 过滤规则(不审查)

| 路径 | 原因 |
| :-- | :-- |
| `sql/archive/**` | 11 个 `ry_20260321_*` 历史调试变体,无生产价值 |
| `node_modules/**` | 若误入 diff,跳过 |
| `gencode_backup/**`、根目录历史脚本 | 非生产路径,除非被 Slice D 引用 |

---

## 2. 7 个 Review Slice

### Slice A · BillNoGenerator 并发与原子性 🔴

**范围**(v3.1 扩展):
- `685c9b0c` 核心实现及相邻(调用方、Mapper、序列表结构、Redis key 约定)
- **`8a4fb34b` 接入 8 个业务单据 Service** 的一致性审查:8 个 Service 是否**都走统一入口**(避免绕开 BillNoGenerator 自行拼号);事务边界是否正确(先 INCR 后 DB insert,异常回滚处理)
- **`ae2e5033` 的 2 处 Mapper 老 bug 修复**(SampleNoticeMapper 漏写 sample_no、ProducePlanMapper plan_no 重复)— 作为**历史 review 失察样本**,反向检查 Slice D 对 Mapper 一致性的覆盖是否充分

**必查项**(每条须明确给出结论):
1. `INCR` 是否满足原子性;**验收标准**:1000 并发、10s 内、符合下方 Assumption 5 的"允许跳号"规则
2. key 命名是否按 `type/date/env/module` 前缀隔离,避免冲突
3. 跨日重置是否仅靠日期换 key,TTL 是否只做清理、不依赖 TTL 重置语义
4. `expire` 是否每次都重设,是否会意外延长旧 key 生命周期
5. Redis 不可达时 fallback 是否仍**无重复**(跳号按 Assumption 5 处置)
6. Redis 成功但 DB 同步失败 / 重试 / 超时重放,是否会双发号
7. 是否需要 Lua 脚本:**仅当** "`INCR + 首次 TTL + 额外状态写入` 必须单事务" 才建议,不默认强推
8. 替换 `StringRedisTemplate` 后实际解决什么:从 git log/blame 推断原问题(序列化 / 数值转换 / `increment` 返回类型 / key-value 编码)

**业务判定**(见 Assumption 5):
- 单号**允许跳号**(Redis INCR + rollback 天然空洞)
- 单号**不允许重复**(硬约束)
- 若发现"可能双发号"场景 → **P0**
- 若发现"可能跳号"场景 → **P2**(非阻塞)

---

### Slice B · 外化与部署回归 🔴

**范围**:`4372f289` 与 `6dfd59a3`

**文件**:
- `ruoyi-admin/src/main/resources/application.yml`
- `ruoyi-admin/src/main/resources/application-druid.yml`
- `.env.production.example`

**必查 5 项外化不破坏启动**:
1. MySQL URL / 用户 / 密码(`DB_URL` / `DB_USERNAME` / `DB_PASSWORD`)
2. 上传路径(`UPLOAD_PATH`)
3. Redis host / port / password(`REDIS_HOST` / `REDIS_PORT` / `REDIS_PASSWORD`)
4. JWT secret(`JWT_SECRET`)
5. Druid 用户/密码/白名单(`DRUID_USERNAME` / `DRUID_PASSWORD` / `DRUID_ALLOW`)

**审查标准**:
- `${VAR:default}` 本地默认值仍可启动
- Pilot 环境变量名与 `docs/07-pilot-env-checklist.md` v1.2 §4 **完全一致**
- 默认值不会误导生产连到本机或 Windows 路径(如 `D:/erp/RuoYi-Vue/uploadPath` 在 Linux 会 IOException)
- 无环境变量时空响应、连接失败、路径不存在、Druid 暴露面均覆盖

---

### Slice C · RBAC / SQL(扩展后) 🔴

**范围**(比 v1 扩展):
- `sql/phase14_rbac_role_permission.sql`(已知 Task #9)
- `sql/phase16_menu_register.sql`
- `sql/phase9-11/sprint2_*` 修复(`b7832c2d`)
- `sql/_helpers.sql`(新增,审谁引用)
- `sql/init_inventory_data.sql`(新增)
- `sql/sys_automation.sql`(新增)
- `sql/sprint2_inventory_pushdown.sql`(新增)
- `sql/README.md` v17 步骤完整性

**必查项**:
1. `role_id 100-104` 与现网 `erp_finance` 等角色冲突 → 给出修复方案:
   - 选项 A:重编号 200-204
   - 选项 B:DROP + REINSERT
   - 评估两者对已有数据的破坏性
2. 可重复执行幂等性(反复 apply 不出错)
3. SQL 语法与引号完整性(`b7832c2d` 修过反引号缺失)
4. `sys_role_menu` 依赖的 `perms` 是否完整存在
5. 角色是否越权 / 漏权
6. phase 间依赖链:phase13 → phase12?phase15 → phase11?phase16 → phase12-15?
7. 首次全量执行(老库无 phase 历史)顺序是否等同于增量执行
8. `sql/_helpers.sql` 是否被引用;若未引用,存在理由

**交付**:明确给出 phase14 是否阻塞部署、是否必须改 role_id 段位、是否需清理/映射旧角色

---

### Slice D · ERP 后端主链 🟡

**范围**:`ruoyi-admin/src/main/java/com/ruoyi/erp/**`、`mapper/erp/**`,加 `ruoyi-demo/src/main/java/com/ruoyi/demo/**`(实际 ERP 业务,见 Slice G)

**重点 commits**:`cd03c861` / `e2aefa40` / `237262ff` / `9f289fe3` / `3ee631da` / `685c9b0c` / `8a4fb34b` / `ae2e5033`

**审查重点**:
1. 字段新增是否 Java / XML / SQL / UI 全链一致
2. 空值、默认值、枚举、日期格式
3. 事务边界与批量操作
4. 运行时 SQL 映射和 controller / service 契约
5. 单号生成、排程、损耗、缺陷、JIS 合规等关键路径回归
6. **`3ee631da` hotfix 质量审查**:4 类阻塞修复是否遗漏类似场景,未来是否还会再踩
7. **`ae2e5033` Mapper bug 样本警示**(v3.1 新增):
   - SampleNoticeMapper.xml `insertSampleNotice` 原本漏写 `sample_no` 字段 → Service 层即使 set 也落 NULL
   - ProducePlanMapper.xml `insertProducePlan` `plan_no` 重复声明 → 非空时 500 错
   - **反向审查**:本 PR 涉及的**其他 Mapper XML**(`ProduceDefectMapper` / `SalesOrderMapper` / `SampleTechMapper` / `ProcessDefMapper` / `ProduceJobProcessMapper` / `ProduceMaterialConsumeMapper` / `BizAbnormalPoolMapper`)是否也有同类漏写或重复的隐患

---

### Slice E · 前端 ERP 页面与 i18n 🟡

**范围**:`ruoyi-ui/src/views/erp/**`、`src/api/erp/**`、`src/locales/**`、主题与入口

**评审规则**(前置约束,避免噪音 findings):
- `7700f8f5` 的 ErpTable 封装、三主题、列宽 / 边距 / hover 变化**视为设计意图**,不做样式类 finding
- `ce4d6151` 后残留硬编码中文**统一记 P2 i18n debt**,不单独立 finding

**必查**:
1. API 路径与参数是否对齐后端(Slice D 返回值)
2. 页面重构是否丢按钮、权限点、导入导出、列字段
3. locales 抽取是否导致空文案、回退错误、运行时异常
4. 日文字符 / IVS / emoji 在 UI 层是否正确显示(对日项目关键)

---

### Slice F · Excel 导入相邻风险 🟡

**范围**:`ruoyi-common/utils/poi/ExcelUtil` + 实际导入 controller / service(非 diff 但相邻)

**业务量级前提**:
- 常态 < 5000 行
- 风险阈值:**5 万行不 OOM + 30s 内响应**
- **不以"百万行不支持"作为 P0**

**重点仍是**:
1. 全量内存模型(POI `WorkbookFactory.create(is)` 是否 SAX 模式)
2. BigDecimal 精度(Excel 数字读出 Double 中转)
3. 15 位以上数字 / 前导零 / 科学计数法
4. 日文 / IVS / emoji / utf8mb4 传递链
5. 并发导入隔离性(`ExcelUtil` 实例/字段缓存跨请求串数据风险)
6. 导入 controller 的调试输出、异常处理、空行跳过、重复单号更新逻辑

**反向触发**:若判定"5 万行超时 / OOM",触发 `docs/05 P3.7.1 EasyExcel` 优先级拉至 **P3.0 即时**

---

### Slice G · ruoyi-demo 命名 / 组织债 🟢

**背景**:`ruoyi-demo/` 下的 `DemoSchedule`、`ProduceGanttController` 是**正式 ERP 业务代码**(承载生产排程能力),命名为"demo"是历史遗留。见 `docs/01 RN-005`。

**处置规则**:
- 命名 / 组织不一致**只标 P3 技术债**,不升 P1 / P2
- **除非发现**:打包/依赖边界错误、权限/路由错误、运行时配置混淆 → 按实际严重度定级

**不把"放在 demo 模块"本身视为上线阻塞**。

---

## 3. 优先级顺序

```
1. Slice A  · BillNoGenerator(最新 commit,并发/原子性最关键)
2. Slice B  · 外化回归(生产启动门槛)
3. Slice C  · RBAC / SQL(已知 Task #9)
4. Slice E  · 前端大重构回归
5. Slice D  · 其余 P0/P1/P2 业务改动
6. Slice F  · Excel 导入相邻风险
7. Slice G  · 命名/组织债
```

---

## 4. Test Scenarios

### 4.1 BillNoGenerator
- 1000 并发发号
- 同日多类型并发(SO / JOB / PO 混发)
- 跨日 00:00 切换
- Redis 不可达
- Redis 成功但 DB 同步失败
- 重试 / 超时重放
- 空返回或异常吞掉导致调用方空响应

### 4.2 外化
- 无 env 启动
- 有 env 覆盖启动
- Linux 上传路径(对比 Windows 默认)
- Redis / MySQL 连接失败时行为

### 4.3 RBAC
- phase14 / 16 可重复执行
- role / menu / post 不冲突
- 权限覆盖正确

### 4.4 前后端
- ERP 列表页、导入导出、详情编辑、三语切换、主题切换

### 4.5 Excel
- 5000 行常态
- 5 万行阈值
- 精度、长数字、日文字符、并发导入

### 4.6 测试工具 / 数据源(执行时补,不阻塞)
- 并发压测:建议 JMeter 或 Gatling 脚本
- 5 万行 Excel:从现有 `import_*.py` 生成的模板随机扩 10x
- 日文样本:包含 `"辺"`(IVS 异体字 `U+8FBA VS17`)、`"🍵"`(emoji)、代理对 `"𠮟"`

---

## 5. Assumptions(核心)

| # | 假设 | 来源 |
| :-- | :-- | :-- |
| 1 | Baseline = `master..HEAD`,13 commits,78 files | git diff 验证 |
| 2 | `erp/main` 已同步,commit 已 push,不审"是否推送" | `git log erp/main..HEAD` 返 0 |
| 3 | `ruoyi-demo/` 中 ERP 业务代码属正式生产路径 | `docs/01 RN-005` |
| 4 | `sql/archive/**` 与归档冗余不进入主 findings | 11 个历史调试变体 |
| **5** | **业务单号(SO/JOB/PO)允许跳号,不允许重复** | 见下方 §5.1 说明 |
| 6 | 鉴权栈 = Spring Security + JWT,**不是 Shiro** | `docs/02 ADR-001 v1.1`(已校正) |
| 7 | 敏感配置已用 `${VAR:default}` 外化,非硬编码 | commits `6dfd59a3` + `4372f289` |
| 8 | 百万行 Excel 非 P0 触发条件,5 万行/30s 为阈值 | 业务量级前提 |

### 5.1 关于"允许跳号"的 Assumption 5 说明

**默认判定**:
- 业务单号(销售单 SO / 生产工单 JOB / 采购单 PO)**允许跳号**
- Redis `INCR` + 业务 rollback 天然产生序列空洞,符合常规 ERP 实践
- 中国增值税**发票号** `invoice_no` 硬要求连续性,但那是发票号**不是单据号**,本项目当前未触及

**升级到 P0 的条件**:
- 业务 Owner / 合规明确指定某类单号"**不允许跳号**"
- 此时该类单号需要 **DB 序列表 + 事务**,不是 Redis
- findings 中如发现业务"要求连续性"证据(注释 / 需求文档 / 对日客户要求),立即升级 P0

**当前处置**:Redis INCR 允许跳号实现**不作为 P0**,跳号场景只作为 **P2 业务风险登记**。

---

## 6. Findings 输出规范

### 6.1 落位

```
D:/erp/docs/11-codex-review-findings-v{N}.md
```
每次跑一次 review 递增 N(v1, v2, ...),保留历史便于对比。

### 6.2 格式

```markdown
# Codex PR Review Findings v{N}

> 对应 brief:11-codex-review-brief-v3.md
> 产出日:YYYY-MM-DD · 耗时:X 小时 · 覆盖 slice:A-G

## P0(阻塞上线)

### [P0-01] 标题(一句话)
- **文件**:路径:行号
- **触发条件**:具体复现步骤
- **影响**:业务/安全/性能 + 严重度评估
- **建议修复**:具体方案(不是"建议检查")
- **证据**:git commit / 代码片段 / 测试结果
- **WBS 联动**(可选):触发 `docs/05 P3.x.y` 优先级拉高到 ...

## P1(必须修,可分阶段)
...

## P2(应修,进 progress_todo)
...

## P3(建议,可搁置)
...

## Open Questions(无证据但值得问)
...
```

### 6.3 不接受的 finding 形态

- "建议检查 X"(无具体方案)
- "可能有问题"(无证据)
- "最佳实践建议 Y"(未与项目上下文关联)

---

## 7. 与 WBS / progress_todo 的联动

| findings 级别 | 去向 |
| :-- | :-- |
| P0 | 立即修,追加 commit 到 `appmod/java-upgrade-20260421103946` |
| P1 | 必修,架构师 sign-off 后 merge;进 progress_todo "立即可做" |
| P2 | 进 progress_todo P3 段;不阻塞当前 merge |
| P3 | 进 progress_todo "已归档(技术债)"段 |

**特殊联动**:
- 若 Slice F 判 POI 超时 → **触发 `docs/05 P3.7.1 EasyExcel` 升级为 P3.0 即时**
- 若 Slice A 发现业务要求"不能跳号"→ **触发 Assumption 5 升级**,BillNoGenerator 重新设计为 DB 序列表

---

## 8. 执行流程

```
0. (5 min) 业务 Owner 核实 Assumption 5:单号是否允许跳号?
   ↓
1. (15 min) AI reviewer 读本 brief 全文 + 引用的 docs/00-10 关键节
   ↓
2. (2-4 hr) 按 §3 优先级执行 Slice A→B→C→E→D→F→G
   超 4 小时暂停并报告进度
   ↓
3. (30 min) findings 写入 docs/11-codex-review-findings-v{N}.md
   ↓
4. (30 min) Claude Opus 4.7 裁决:接受 / 反驳(要证据) / 补充
   ↓
5. 架构师人工 sign-off P0/P1 修复计划
   ↓
6. 实施 → re-review(必要时)→ 合并
```

---

## 9. 版本与变更日志

### v3.1(2026-04-22)· 当前版

baseline 推进修订,因会话期间 RuoYi-Vue 落地 2 个新 commit:
- `8a4fb34b feat(erp): 接入 BillNoGenerator 到 8 个业务单据 Service`
- `ae2e5033 fix(mapper): 修 BillNoGenerator 接入后暴露的 2 处 Mapper 老 bug`

调整:
- §1.1 HEAD 从 `685c9b0c` 推进至 `ae2e5033`
- §1.2 commit 列表 13 → 15,scope 78 → 88 files,+4108 → +4169 插入
- Slice A 扩展到 3 个 commit 的"单号生成 + 8 Service 接入 + 2 Mapper bug 修复"三层审查
- Slice D 新增第 7 审查重点:参照 `ae2e5033` 修复的 2 处 Mapper 老 bug(漏写字段 / 字段重复),
  **反向审查本 PR 其他 7 张 Mapper XML 是否存在同类隐患**

### v3.0(2026-04-22)· 前版

由以下输入合并:
- Codex 自提 v3 计划(7 slice 结构 + 审查标准数字化 + 优先级排序)
- 本会话 8 条盲区修复(ADR-001 / baseline / phase14 / 业务量级 / 前端语境 / archive 过滤 / BillNoGenerator 专项 / demo 命名债)
- 本会话 6 条业务执行微调(Assumption 5 条件化 / findings 落位 / Slice C 扩展 / WBS 联动 / 时限 / 裁决链)

### 过往版本

- v2:Codex 中间迭代(未单独归档)
- v1:Codex 首版(抓到"仓库无 EasyExcel"关键点,缺 ADR-001 校正等 context)

---

## 10. 相关文档引用

| 主题 | 位置 | 文档 |
| :-- | :-- | :-- |
| ADR-001 鉴权事实校正 | Assumption 6 | `02 v1.1` |
| Pilot 环境变量清单 | Slice B | `07 v1.2 §4` |
| ruoyi-demo 命名历史债 | Slice G | `01 RN-005` |
| EasyExcel 未来路线 | Slice F 反向触发 | `08 v1.1 §E8` |
| utf8mb4 / 人脸合规 | Slice E / 字符测试 | `10 v1.1` |
| phase14 role_id 冲突 | Slice C | `progress_todo.md` Task #9 |
| WBS 工作包 | §7 | `05 v1.2` |
| 决策速查 / P0 清单 | 决策联动 | `00 v1.2` |
