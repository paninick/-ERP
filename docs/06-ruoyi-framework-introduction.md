# 06 · 若依（RuoYi-Vue）框架介绍与环境部署基线

> **定位**：技术文档基础参考 — 本 ERP 所依赖的若依框架官方介绍与环境部署规范的提炼，用于新人 onboarding、部署核对、CCB 技术底座讨论的共同语言。
> **产出日**：2026-04-22 · **修订日**：2026-04-22 · **项目**：对日针织外贸 ERP（基于 RuoYi-Vue 3.9.2）
> **来源**：[RuoYi 官方文档 · 环境部署](https://doc.ruoyi.vip/ruoyi/document/hjbs.html)、[RuoYi-Vue 项目介绍](https://doc.ruoyi.vip/ruoyi-vue/document/xmjs.html)、[RuoYi-Vue 源码仓库](https://gitee.com/y_project/RuoYi-Vue)
> **适用读者**：新入职工程师、部署运维、架构评审参与者
> **版本**：v1.1

---

## 1. 若依是什么（一句话）

若依是一套**全部开源、对个人及企业免费**的 Java EE 企业级快速开发平台；**RuoYi-Vue** 是其前后端分离分支，基于 **Spring Boot + Spring Security + Redis + JWT + Vue + Element UI**，用于快速搭建带有完整权限、日志、代码生成、监控能力的后台系统。

本项目选型的是 **RuoYi-Vue 3.9.2**（对应 Spring Boot 4.0.3 / JDK 17 / Vue 2.6 / Element UI 2.15）。

---

## 2. 若依版本矩阵（选型依据）

### 2.1 后端分支

| 分支 | Spring Boot | JDK 要求 | 本项目 |
| :-- | :-- | :-- | :-- |
| `master`（默认） | 4.x | 17+ | ✅ **当前使用** |
| `springboot3` | 3.x | 17+ | — |
| `springboot2` | 2.x | 8+ | — |

> 说明：`RuoYi-Vue/pom.xml` 中 `spring-boot.version = 4.0.3`，`java.version = 17`，与 `master` 分支一致。

### 2.2 前端分支

| 项目 | 框架 | 脚本 | 构建 | UI 库 | 状态管理 | 本项目 |
| :-- | :-- | :-- | :-- | :-- | :-- | :-- |
| **RuoYi-Vue** | Vue 2 | JavaScript | Vue CLI | Element UI | Vuex | ✅ **当前使用** |
| RuoYi-Vue3 | Vue 3 | JavaScript | Vite | Element Plus | Pinia | — |
| RuoYi-Vue3-TypeScript | Vue 3 | TypeScript | Vite | Element Plus | Pinia | 候选二期（见 02 ADR-004） |

> **迁移路线**：一期保持 Vue 2；二期 2026Q4 切 Vue 3，参见 `02-architecture-and-key-system.md` ADR-004。

### 2.3 分布式版本（参考）

- **RuoYi-Cloud**：Spring Boot + Spring Cloud & Alibaba + Nacos + Vue。
- 本项目**不采用** Cloud 版本（见 `02` ADR-002 单库软隔离、ADR-006 不引入独立 Gateway）。

---

## 3. 技术栈与核心依赖

### 3.1 后端核心（与 `pom.xml` 对齐）

| 组件 | 版本 | 作用 |
| :-- | :-- | :-- |
| Spring Boot | 4.0.3 | 应用主容器 |
| Spring Security | Spring Boot 传递 | 鉴权框架（RuoYi-Vue 从 Shiro 迁至 Security） |
| JWT | 0.9.1 | 无状态登录 Token |
| MyBatis Spring Boot | 4.0.1 | ORM |
| Druid | 1.2.28 | 数据源 + SQL 监控 |
| PageHelper | 2.1.1 | 分页插件 |
| Fastjson | 2.0.61 | JSON 序列化 |
| POI | 4.1.2 | Excel 导入导出 |
| Velocity | 2.3 | 代码生成模板引擎 |
| Springdoc | 3.0.2 | OpenAPI 文档 |
| Oshi | 6.10.0 | 服务器监控信息采集 |
| Kaptcha | 2.3.3 | 图形验证码 |
| Yauaa | 8.1.0 | User-Agent 解析 |

> **本项目实际情况**：`ruoyi-framework` 已沿用 RuoYi 3.9.2 `master` 原生的 Spring Security + JWT，详见 02 ADR-001（v1.1 校正后）。

### 3.2 前端核心（与 `ruoyi-ui/package.json` 对齐）

| 组件 | 版本 | 作用 |
| :-- | :-- | :-- |
| Vue | 2.6.x | 前端框架 |
| Element UI | 2.15.14 | UI 组件库 |
| Vuex | 传递 | 状态管理 |
| Vue Router | 3.x | 路由 |
| axios | 0.30.3 | HTTP 客户端 |
| ECharts | 5.4.0 | 图表 |
| Quill | 2.0.2 | 富文本编辑器 |
| jsencrypt | 3.0.0-rc.1 | 前端 RSA 加密（登录密码） |
| @riophae/vue-treeselect | 0.4.0 | 树形下拉 |

---

## 4. 内置功能清单（19 项）

若依开箱即用的后台管理能力，本项目**全部继承**，不做裁剪：

### 4.1 基础管理（7 项）
1. **用户管理** — 系统操作者配置
2. **部门管理** — 组织机构（公司/部门/小组），树结构，数据权限载体
3. **岗位管理** — 用户所属职务
4. **菜单管理** — 菜单、操作权限、按钮权限标识
5. **角色管理** — 角色-菜单权限、按机构的数据范围划分
6. **字典管理** — 枚举型数据维护（本项目已扩展 30+ 业务字典）
7. **参数管理** — 动态系统参数

### 4.2 日志与监控（8 项）
8. **通知公告** — 信息发布
9. **操作日志** — 正常/异常操作记录
10. **登录日志** — 登录记录（含异常）
11. **在线用户** — 活跃会话监控
12. **定时任务** — 在线添加/修改/删除 Quartz 任务，含执行结果日志
13. **服务监控** — CPU / 内存 / 磁盘 / 堆栈（基于 Oshi）
14. **缓存监控** — Redis 缓存查询 / 命令统计
15. **连接池监视** — Druid SQL 分析

### 4.3 开发辅助（4 项）
16. **代码生成** — 按表生成 Java + XML + SQL + Vue（本项目 37 个业务模块大量使用）
17. **系统接口** — Springdoc / Swagger UI
18. **在线构建器** — 拖拽生成表单 HTML
19. **国际化支持** — 底座具备（本项目 i18n 仅中文，见 03 §2.2、05 P3.4）

---

## 5. 项目模块结构

### 5.1 官方标准结构

```
com.ruoyi
├── ruoyi-common     // 工具类（注解、配置、常量、枚举、异常、过滤器）
├── ruoyi-framework  // 框架核心（数据权限、拦截器、异步、权限控制、前端适配）
├── ruoyi-generator  // 代码生成（可移除）
├── ruoyi-quartz     // 定时任务（可移除）
├── ruoyi-system     // 系统管理业务代码
└── ruoyi-admin      // 后台服务启动模块
```

### 5.2 本项目扩展

```
D:\erp\RuoYi-Vue\
├── ruoyi-common     // ← 若依原生
├── ruoyi-framework  // ← 若依原生（禁止修改，见 CLAUDE.md 约束）
├── ruoyi-generator  // ← 若依原生
├── ruoyi-quartz     // ← 若依原生
├── ruoyi-system     // ← 若依原生
├── ruoyi-admin      // ← 已扩展：ERP Controller / Service / Domain 均在此模块
│   └── com.ruoyi.erp  // 37 个业务模块的主入口
├── ruoyi-demo       // ← 本项目新增：ERP 业务核心（款式/订单/排程/BOM 等 11 个 Domain + 43 测试）
├── ruoyi-ui         // ← 前端（Vue 2 + Element UI）
│   └── src/views/erp // ← ERP 业务页面
└── sql              // ← 数据库迁移脚本（phase1~phase15 增量）
```

> **边界约束**（见根目录 `CLAUDE.md`）：禁止修改 `ruoyi-framework/`；数据库脚本走 `sql/` 增量，不改动已有脚本。

---

## 6. 环境部署要求（hjbs 核心）

### 6.1 必备环境

| 组件 | 官方最低 | 官方推荐 | 本项目实际 | 说明 |
| :-- | :-- | :-- | :-- | :-- |
| JDK | 1.8 | 1.8 | **17** | Spring Boot 4.x 分支强制 17+ |
| Maven | 3.0 | 3.6+ | 3.6+ | 后端依赖管理 |
| MySQL | 5.7.0 | 5.7 | **5.7** | 见 01 现状快照 |
| Redis | 3.0 | 5.0+ | 5.0+ | 缓存 + 会话 + 验证码 |
| Node.js | 12 | 16+ | 16+ | 前端构建 |
| Nginx | — | 1.18+ | 1.18+ | 生产前端静态 + 反向代理 |

> **偏离说明**：若依原文以 Spring Boot 2.x 为默认语境建议 JDK 1.8；本项目走 `master` 分支，JDK 必须 17+。新环境装 1.8 会直接编译失败。

### 6.2 启动流程（从零到跑起来）

#### 步骤 1 · 数据库初始化

```sql
-- 1. 创建数据库（字符集必须 utf8mb4）
CREATE DATABASE ry_vue DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 2. 依次导入若依基础脚本（在 D:\erp\RuoYi-Vue\sql\ 下）
--    - ry_20xxxx.sql       (必须，若依主表 ~30 张)
--    - quartz.sql          (必须，定时任务表)

-- 3. 依次执行本项目增量脚本 phase1 ~ phase15
--    重要：不得跳序，不得修改已落库的脚本内容
```

#### 步骤 2 · 后端配置

编辑 `ruoyi-admin/src/main/resources/`:

- **`application.yml`** — 修改 Redis `host` / `port` / `password`
- **`application-druid.yml`** — 修改 MySQL `url` / `username` / `password`

> **安全规范**（见最近 commit `6dfd59a3`）：**敏感配置必须走环境变量**
> - `JWT_SECRET`
> - `REDIS_PASSWORD`
> - `DRUID_USERNAME` / `DRUID_PASSWORD`
> 禁止硬编码到 YAML 提交 Git。

#### 步骤 3 · 后端启动

- 开发环境：IDEA 运行主类 `com.ruoyi.RuoYiApplication`
- 启动成功验证：访问 `http://localhost:8080`（裸后端无静态页，但接口可通）

#### 步骤 4 · 前端启动

```bash
cd D:\erp\RuoYi-Vue\ruoyi-ui

# 首次安装依赖（强烈不建议用 cnpm，会触发诡异 bug）
npm install --registry=https://registry.npmmirror.com

# 开发启动
npm run dev
```

默认访问：`http://localhost:80`，账号 `admin / admin123`。

> **本项目注意**：生产前密码必须改，见 03 §1.1.2 敏感数据处置。

#### 步骤 5 · 生产 Nginx 反代（简版）

```nginx
server {
    listen       80;
    server_name  erp.example.com;

    location / {
        root   /opt/erp/dist;   # ruoyi-ui 的 dist
        try_files $uri $uri/ /index.html;
    }

    location /prod-api/ {
        proxy_set_header Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_pass http://127.0.0.1:8080/;   # ruoyi-admin.jar
    }
}
```

> **路径常量**：前端默认请求前缀是 `/prod-api`，写在 `ruoyi-ui/.env.production` 的 `VUE_APP_BASE_API`。若要改，需同步前端与 Nginx。

---

## 7. 与本 ERP 项目的关系（改造边界）

| 层 | 若依原生 | 本项目扩展 | 是否可改 |
| :-- | :-- | :-- | :-- |
| `ruoyi-framework` | 权限、拦截、异常、前端适配 | — | ❌ 禁改 |
| `ruoyi-common` | 工具类 | 新增少量 ERP 公共常量/注解 | ⚠️ 仅新增 |
| `ruoyi-system` | 基础管理 19 功能 | — | ⚠️ 仅扩字典/菜单 |
| `ruoyi-admin` | Controller 骨架 | `com.ruoyi.erp.*` 全部业务 | ✅ 本项目主战场 |
| `ruoyi-demo` | — | ERP 核心 Domain + 43 测试 | ✅ 本项目新增 |
| `ruoyi-ui/src/views/erp` | — | 37 个业务页面 | ✅ 本项目新增 |
| `sql/` | `ry_*.sql` / `quartz.sql` | `phase1~15_*.sql` | ✅ 仅增量 |

---

## 8. 常见部署坑（本项目踩过）

| 现象 | 原因 | 处置 |
| :-- | :-- | :-- |
| 启动报 `Unable to find a @SpringBootConfiguration` | JDK 版本低于 17 | 装 JDK 17 并配 `JAVA_HOME` |
| 前端 `npm install` 卡死或报 SSL | 默认 registry 慢 | 加 `--registry=https://registry.npmmirror.com` |
| 登录后立刻 401 | Redis 连不通 | 检查 `application.yml` Redis 配置 + `redis-cli ping` |
| 页面白屏 | Nginx `try_files` 未配 `/index.html` 兜底 | 修正 SPA 兜底规则 |
| 代码生成器生成的代码包名错 | `generator.yml` 的 `packageName` 未改 | 改为 `com.ruoyi.erp.xxx` |
| Druid 监控页打不开 | Druid 账号写死默认 | 同 commit `6dfd59a3`，走 env |

---

## 9. 参考链接

| 资源 | 链接 |
| :-- | :-- |
| 环境部署官方文档 | https://doc.ruoyi.vip/ruoyi/document/hjbs.html |
| RuoYi-Vue 介绍 | https://doc.ruoyi.vip/ruoyi-vue/ |
| 项目介绍 | https://doc.ruoyi.vip/ruoyi-vue/document/xmjs.html |
| 源码仓库 | https://gitee.com/y_project/RuoYi-Vue |
| 官方演示 | http://vue.ruoyi.vip |
| 本项目根目录约束 | `D:\erp\CLAUDE.md` |

---

## 10. 与其他文档的交叉引用

| 主题 | 本文位置 | 主文档 |
| :-- | :-- | :-- |
| 鉴权框架(Spring Security vs 其他) | §3.1 | `02` ADR-001 |
| 单库 vs 微服务 | §2.3 | `02` ADR-002、ADR-006 |
| Vue 2 保留 vs Vue 3 迁移 | §2.2 | `02` ADR-004 |
| 敏感配置环境变量化 | §6.2 步骤 2 | 最近 commit `6dfd59a3`、`03` §1.1.2 |
| 模块边界 / 禁改 `ruoyi-framework` | §7 | 根 `CLAUDE.md` |
| SQL 增量脚本规则 | §6.2 步骤 1 | 根 `CLAUDE.md` |

---

**更新规则**：
- 若依版本升级（如切到 Spring Boot 3→4 或 Vue 2→3）时，本文版本号 +1 并同步 `02-architecture-and-key-system.md`。
- `pom.xml` / `package.json` 关键依赖调整 → 同步 §3。
- 新增部署坑 → 追加 §8 表格。

---

## 变更日志

- **2026-04-22 v1.1**：同步 02 ADR-001 事实校正。§3.1 下方"ADR-001 是否迁 Security 是遗留决策"注脚改写为当前实况；§10 交叉引用表行 1 文字调整。
- **2026-04-22 v1.0**：初版。
