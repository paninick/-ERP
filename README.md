# 针织服装工贸一体 ERP 系统

基于 Spring Boot + Vue 的针织服装行业数字化管理系统，覆盖采购、生产、库存、销售、财务全流程。

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Java 17 · Spring Boot 4.x · MyBatis |
| 数据库 | MySQL 8.0 |
| 前端 | Vue 2 (ruoyi-ui) + React 18 + TypeScript (ERP-UI-2) |
| 工作流 | Flowable 7.x |
| 权限 | Spring Security + RBAC |

## 模块概览

- **销售管理**：销售订单、客户管理、供应商管理、款式档案
- **生产管理**：工单管理、工序报工、生产计划、工序列定义、甘特图、工作中心
- **物料管理**：主料/辅料管理、BOM
- **库存管理**：入库/出库/库存查询、仓库/仓位管理
- **采购管理**：采购订单、外协加工
- **品质管理**：品质检验、质检放行、检品预约、日单放行、产品追溯
- **财务管理**：计件工资、发票管理
- **人事管理**：员工管理
- **系统管理**：用户/角色/字典/组织架构

## 快速开始

```bash
# 后端
cd ruoyi-admin
mvn spring-boot:run

# 前端 (Vue 2)
cd ruoyi-ui
npm install && npm run dev
```

## 项目结构

```
RuoYi-Vue/
├── ruoyi-admin/       # 业务模块 (com.ruoyi.erp)
├── ruoyi-framework/   # 框架层 (禁止修改)
├── ruoyi-common/      # 通用工具
├── ruoyi-system/      # 系统模块
├── ruoyi-flowable/    # 工作流模块
├── ruoyi-ui/          # Vue 2 前端
├── sql/               # 数据库脚本
└── docs/              # 项目文档
```

## 开发规范

- SQL 参数化：`#{}` 禁止 `${}`
- 事务注解：`@Transactional(rollbackFor = Exception.class)`
- 权限控制：`@PreAuthorize("@ss.hasPermi('erp:模块:动作')")`
- 详细规范见 [CLAUDE.md](CLAUDE.md) 和 [docs/GOVERNANCE.md](docs/GOVERNANCE.md)

## 许可

基于 RuoYi-Vue 框架二次开发，遵循 Apache 2.0 协议。
