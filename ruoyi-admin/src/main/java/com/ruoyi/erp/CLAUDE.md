# module-production Spec 契约

> 生产模块 AI 协作规范。修改 `com.ruoyi.erp` 下任何代码时必须遵守。
> 最后更新：2026-04-26 · 治理 v3.4

---

## 架构约定

- **Controller**：只做入参校验 + 转发，**禁止**写业务逻辑
- **Service**：所有多表写操作必须 `@Transactional(rollbackFor = Exception.class)`
- **Mapper**：只用 `#{}` 参数化，**禁止** `${}` 拼接用户输入；`parameterType` 可省略
- **Domain**：每个字段必须有 `@Excel(name = "...")`，`toString()` 必须包含全部字段

---

## 模式约定（正例 — 新功能必须参考，禁止另起炉灶）

### 1. 出库与消耗联动
- **场景**：物料出库后自动生成消耗记录，或消耗记录关联出库单
- **参考**：`ProduceMaterialConsumeServiceImpl.syncByStockOut()` / `bindToJobProcess()`
- **要点**：先查已有绑定 → 区分新增/更新 → 统一用 Map 批量查避免 N+1

### 2. 异常收集
- **场景**：批量操作中部分失败，不中断整体，最后统一报告
- **参考**：`BizAbnormalPool` 模式
- **要点**：失败收集到 abnormal pool → 成功继续 → 最终统一通知

### 3. 批量查询避免 N+1
- **场景**：遍历列表时需要关联查询其他表
- **参考**：`ProduceMaterialConsumeServiceImpl` 中 `existingMap` 批量预查模式
- **要点**：循环前一次性查出所有关联数据 → 用 Map<Long, Entity> 缓存 → 循环内 get() 取值

### 4. Controller @Log
- **场景**：所有 Controller 端点
- **参考**：现有 Controller 中的 @Log 注解
- **要点**：title 用中文描述（项目风格），搭配 `@PreAuthorize("@ss.hasPermi('erp:模块:动作')")`

---

## 反例（禁止触碰 — 新代码绝对不能模仿）

### 1. REPLACE INTO
- **现象**：`REPLACE INTO t_xxx`
- **为什么禁止**：先 DELETE 再 INSERT，导致未指定字段重置为默认值、外键级联删除、自增 ID 跳跃
- **正确做法**：`INSERT ... ON DUPLICATE KEY UPDATE`
- **来源**：Wave 4 C2, KNOWN_ISSUES §1.4

### 2. ADD COLUMN IF NOT EXISTS
- **现象**：MySQL 中写 `ADD COLUMN IF NOT EXISTS`
- **为什么禁止**：MySQL 不支持此语法
- **正确做法**：`INFORMATION_SCHEMA` 检查 + PREPARED STATEMENT
- **来源**：Phase 29 Fix-10, KNOWN_ISSUES §3.1

### 3. 循环内单条查询
- **现象**：for 循环内调用 Mapper 查数据库
- **为什么禁止**：N+1 查询，数据量大时性能指数级下降
- **正确做法**：循环前批量查出，用 Map 缓存
- **来源**：Phase 29 Fix-4, KNOWN_ISSUES §1（相关）

### 4. 同类方法互相调用
- **现象**：Service 内 `this.otherMethod()` 调用另一个需要事务的方法
- **为什么禁止**：Spring AOP 通过代理生效，`this.method()` 绕过代理 → 事务失效
- **正确做法**：移到另一个 Bean 或用 `AopContext.currentProxy()`
- **来源**：KNOWN_ISSUES §2.2

### 5. 私有方法参数加 @NotNull/@NotBlank
- **现象**：私有方法参数上加校验注解
- **为什么禁止**：校验在系统边界（Controller）完成，内部方法信任调用方。私有方法加校验是无效代码膨胀
- **正确做法**：只在 Controller 入参和外部 API 调用处加校验

### 6. 异常被 try-catch 吞掉不抛出
- **现象**：`try { ... } catch (Exception e) { e.printStackTrace(); }`
- **为什么禁止**：事务不回滚，错误被隐藏
- **正确做法**：要么转换为业务异常抛出，要么收集到 BizAbnormalPool
- **来源**：KNOWN_ISSUES §2.2

### 7. git add 前质量自检
- **场景**：每次写完代码后，git add 之前
- **要点**：
  - `git diff` 检查每个 `+` 行：注解类型是否已存在于该文件？
  - import 是否重复？
  - 是否全部来自 Spec 或 fix-checklist？
  - 有无 `// ...` 不完整实现？
  - 有无注释掉的代码？
  - 有无硬编码配置值？
- **任何一项不通过 → 不得 git add → 不得 commit**

---

## 本模块特有的技术债务

> 已知但不急着修的问题，新代码不要模仿。

| 文件 | 问题 | 为什么暂时不修 |
|------|------|---------------|
| — | — | — |
