# CLAUDE.md

> ⚠️ 本项目是 `d:/erp` 的子模块。所有治理规则定义在父项目中。
> 治理 v3.4 · 硬门禁见 `../CLAUDE.md` · 完整方法论见 `../docs/GOVERNANCE.md`
> 项目配置见 `../PROJECT_CONFIG.md`

## 启动时必须执行

1. 读取 `../docs/audit/AUDIT_TRACKER.md` — 检查 P0 OPEN
2. 读取 `../docs/KNOWN_ISSUES.md` — 故障决策表
3. 读取 `../docs/session-handoff.md` — 上次会话上下文
4. 读取 `../docs/GOVERNANCE.md` — 完整方法论（SDD/审计流程/成本策略）

**No Spec No Code：没有 Spec 确认 = 不准写代码。**

## 模块级契约

修改 `com.ruoyi.erp` 下代码前，先读：
- `ruoyi-admin/src/main/java/com/ruoyi/erp/CLAUDE.md` — 生产模块正例/反例

## 关键约定（与父项目一致，详见 ../PROJECT_CONFIG.md）

- MyBatis SQL：`#{}` 参数化，禁止 `${}` 拼接用户输入
- 事务：多表写操作 `@Transactional(rollbackFor = Exception.class)`
- 权限：Controller 端点 `@PreAuthorize`
- Domain：新增字段后 `mvn compile` 再 commit
- DDL：PREPARED STATEMENT + INFORMATION_SCHEMA 幂等检查
- 模块边界：`ruoyi-framework/` 禁止修改
- 修复范围：只改 fix-checklist 列出的文件/行号
- 提交后：输出摘要 + 等待审查反馈

## 禁止

- ❌ 带着 P0 OPEN 提交新代码
- ❌ 自验自宣告修复通过
- ❌ 合并修复与新功能在同一个 commit
- ❌ 没有 Spec 确认就写代码
