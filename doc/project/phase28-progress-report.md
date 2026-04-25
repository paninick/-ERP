# Phase 28 进度报告

## 1. 当前状态

- 仓库目录：`D:\erp\RuoYi-Vue`
- 当前分支：`foundation`
- 当前基线：`foundation` 相对 `erp/main` 领先 42 个提交
- 当前远端：
  - `erp` -> `https://github.com/paninick/-ERP.git`
  - `github` -> `https://github.com/paninick/ui-erp.git`
  - `origin` -> `https://gitee.com/y_project/RuoYi-Vue.git`

## 2. 本阶段已完成

### 2.1 已落地提交

- `601ded6b` `feat: Phase 28 — ProduceMaterialConsume 执行成本字段扩展`
- `8770c3f5` `fix: 修正物料消耗超耗判定与 phase28 SQL 幂等性`

### 2.2 已完成修复

- 修正物料消耗超耗判定逻辑，按允许损耗量判断是否超限。
- 修正 `/erp/materialconsume/calculateLimit` 返回值，移除伪造 `sampleLoss`。
- 修正 `sql/phase28_material_consume_execution_cost.sql` 索引脚本，支持重复执行。
- 核对 `ReportController` 权限缺口，确认已补齐。

### 2.3 本次继续补齐内容

- `materialconsume` 页面补齐 Phase 28 新字段展示：
  - `batchNo`
  - `materialType`
  - `unit`
  - `unitPrice`
  - `theoreticalCost`
  - `actualCost`
  - `costDiff`
- 查询区新增：
  - `batchNo`
  - `materialType`
- 表单补齐：
  - `batchNo`
  - `materialType`
  - `unit`
  - `unitPrice`
- 成本字段改为前端只读展示，和后端自动计算逻辑保持一致。
- `zh-CN / en-US / ja-JP` 国际化文案已补齐。

## 3. 完成度判断

### 3.1 已完成

- 后端 Phase 28 数据模型、Mapper、SQL 已具备执行成本字段能力。
- 服务层已具备理论成本、实际成本、成本差异自动回填能力。
- 前端页面已具备对新增字段的查询、列表展示和表单维护能力。
- 本地代码已具备提交 GitHub 的完整性，不再是“后端完成、前端缺展示”的半成品状态。

### 3.2 未完成 / 待验证

- 尚未完成真实业务环境下的接口联调验证。
- 尚未完成数据库实库回归验证。
- 尚未完成生产流量级压力测试执行与结果留档。
- 尚未确认是否需要把 `foundation` 直接合并覆盖到 GitHub 远端 `main`，还是仅推送同名分支。

## 4. 压力测试现状

### 4.1 已有基础

- 仓库内已有测试计划文档：`doc/project/测试计划.md`
- 仓库内已有集成/性能测试场景文档：`doc/test-cases/integration-test-scenarios.md`
- 文档中已规划使用 `JMeter` 进行性能与压力测试。

### 4.2 当前实际状态

- 当前轮次完成的是代码扫描、逻辑修复、前端补齐和构建级验证。
- 当前仓库内未发现可直接执行的现成压测脚本、JMeter 工程文件或自动化压测流水线。
- 因缺少联通环境、基准数据和压测脚本，本轮无法声称“已完成真实压力测试”。

### 4.3 建议的压力测试补齐项

- 为 `materialconsume/list`、`materialconsume/add`、`materialconsume/edit`、`calculateLimit` 制作 JMeter 场景。
- 增加 50 / 100 / 200 并发分层压测，并记录 P95/P99 响应时间。
- 对 `produce_plan_id`、`batch_no`、`material_code` 查询建立 SQL 执行计划留档。
- 在压测前准备 1 万到 10 万级物料消耗样本数据，避免空库压测失真。
- 将压测结果输出到独立报告，纳入上线门禁。

## 5. 优化建议

- 后端优化：
  - 为高频查询补充更明确的复合索引评估，尤其是 `produce_plan_id + approval_status`、`produce_plan_id + is_over_limit`。
  - 在服务层增加单元测试，覆盖超耗边界值、零单价、空值回填和成本差异计算。
  - 导出接口可补充新字段，保证 Excel 与页面字段一致。

- 前端优化：
  - 列表字段较多，建议下一步增加列显隐或明细抽屉，降低横向滚动成本。
  - 成本差异可增加颜色图例或 tooltip，明确正负含义。
  - `materialType` 可统一复用字典组件，减少硬编码。

- 流程优化：
  - 把 Phase 28 相关 SQL、构建命令、验证结果固化到上线检查单。
  - 为 GitHub 推送建立固定分支策略，避免本地 `foundation` 直接追踪远端 `main` 带来的混淆。

## 6. 建议推送策略

- 保守策略：推送到 GitHub `erp/foundation`
- 激进策略：直接推送本地 `foundation` 到 GitHub `erp/main`

当前默认建议先确认分支策略；如果按“直接交付主线”处理，可执行：

```bash
git push erp foundation:main
```

如果按“先保留分支再合并”处理，可执行：

```bash
git push erp foundation:foundation
```
