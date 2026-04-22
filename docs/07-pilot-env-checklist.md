# 07 · Pilot 部署环境核对清单(Go/No-Go)

> **定位**:P6.1 数据迁移演练 **之前** 对 Pilot 工厂服务器的环境一致性核对表。
> 目的是**阻断"装错 JDK、漏配环境变量、漏跑 SQL"三类经典事故**,在开发机之外的第一次部署前锁死基线。
> **产出日**:2026-04-22 · **项目**:对日针织外贸 ERP(基于 RuoYi-Vue 3.9.2)
> **适用读者**:Pilot 现场部署运维、DevOps、架构师(会签人)
> **使用节奏**:首次全项核查 1 次 + 每次环境变更后增量核查 + 上线演练前强制重跑
> **版本**:v1.2

---

## 0. 使用说明

### 0.1 执行角色

| 角色 | 职责 |
| :-- | :-- |
| **Pilot 运维** | 逐项执行、截图/命令输出留档、填写"实际值"列 |
| **架构师** | 复核、签字放行 |
| **PM** | 归档留存、作为 P6.1 进入判据 |

### 0.2 Go/No-Go 决策规则

- **🔴 必须项失败 → No-Go**:阻断部署,整改完成后重查
- **🟡 建议项失败 → Go with issue**:记录偏差,在 Hypercare 前补齐
- **🟢 可选项失败 → Go**:仅记录,不阻断

### 0.3 签字栏(首页版本)

| 项目 | 签字人 | 日期 |
| :-- | :-- | :-- |
| Pilot 运维核查 | _______ | ____ / ____ / ____ |
| 架构师复核 | _______ | ____ / ____ / ____ |
| PM 归档 | _______ | ____ / ____ / ____ |

---

## 1. A 组 · 操作系统与硬件

| 🔴🟡🟢 | 检查项 | 期望值 | 实际值 | 备注 |
| :-- | :-- | :-- | :-- | :-- |
| 🔴 | OS 版本 | Linux x86_64(Ubuntu 22.04 LTS / CentOS 7.9 / Kylin V10 中任一) | | `uname -a`,CentOS 7.9 需确认仍在支持期 |
| 🔴 | CPU | ≥ 4 核 | | `nproc` |
| 🔴 | 内存 | ≥ 8 GB | | `free -h`,Java 堆 4G + Redis + MySQL |
| 🔴 | 磁盘 | ≥ 100 GB 可用 | | `df -h`,含 MySQL 数据盘预留 |
| 🟡 | 时区 | Asia/Shanghai 或 Asia/Tokyo(客户数据侧)| | `timedatectl`;Flowable 历史与日志依赖 |
| 🟡 | NTP 时钟同步 | 已开启 | | `timedatectl show` → `NTPSynchronized=yes` |
| 🟡 | 文件句柄 | ulimit -n ≥ 65535 | | `/etc/security/limits.conf` |
| 🟢 | Swap | 关闭或 ≤ 2G | | `swapon --show` |

---

## 2. B 组 · 运行时环境

| 🔴🟡🟢 | 组件 | 期望版本 | 实际值 | 验证命令 |
| :-- | :-- | :-- | :-- | :-- |
| 🔴 | JDK | **17+**(项目用 Spring Boot 4.0.3,低于 17 编译/启动失败) | | `java -version` |
| 🔴 | JAVA_HOME | 指向 JDK 17 | | `echo $JAVA_HOME` |
| 🔴 | Maven | 3.6+ | | `mvn -v`(仅打包机需要,部署机可不装) |
| 🔴 | Node.js | 16.x / 18.x(LTS 优先)| | `node -v`(仅前端构建机) |
| 🔴 | npm | 与 Node 对应 | | `npm -v` |
| 🟡 | Git | 2.x+ | | `git --version` |

> **偏离若依官方语境说明**:RuoYi-Vue 官方 `hjbs` 文档以 Spring Boot 2.x 为默认语境建议 JDK 1.8。本项目 `pom.xml` 锁定 Spring Boot 4.0.3 + `java.version=17`,**JDK 8/11 必然失败**。详见 `06 §6.1` 的偏离说明。

### 2.x 禁用安装包清单(对应 `D:/下载/其他/`,详见 `09 §2`)

| 🔴 安装包 | 禁用原因 |
| :-- | :-- |
| `jdk-8u161-windows-x64.zip` | Spring Boot 4.x 要求 JDK 17+,JDK 8 编译 / 启动必败 |
| `nginx-1.8.1.zip` | 2015 年版,多个 CVE,低于 §3.3 要求 1.18+ |
| `Redis-x64-3.2.100.msi` | 2016 年微软 fork 已弃,缺 Stream / ACL,低于 §3.2 要求 5.0+ |
| `mysql57221.zip`(5.7.22) | 2018 年版,距 5.7 末版(5.7.44)累积 1000+ 补丁;**建议替换为 5.7.44 或 8.0 LTS** |

---

## 3. C 组 · 中间件

### 3.1 MySQL

| 🔴🟡🟢 | 检查项 | 期望值 | 实际值 | 验证命令 |
| :-- | :-- | :-- | :-- | :-- |
| 🔴 | 版本 | **5.7.x**,**小版本下限 ≥ 5.7.44**(5.7 末版,2023-10)| | `mysql --version` |
| 🔴 | 字符集 | utf8mb4 / utf8mb4_unicode_ci(**不可用 utf8**,丢日文 IVS / emoji)| | `show variables like 'character_set_%';` |
| 🔴 | 时区 | `+08:00` 或 `Asia/Shanghai` | | `show variables like 'time_zone';` |
| 🔴 | 数据库 `ry_vue` | 已建,字符集 utf8mb4 | | `show create database ry_vue;` |
| 🔴 | 老库迁入时字符集转换 | 迁入前每张表 `ALTER TABLE x CONVERT TO CHARACTER SET utf8mb4`,禁止沿用 utf8 | | 见 `10 §4.2` |
| 🔴 | 应用账号 | 非 root,最小权限(INSERT/UPDATE/SELECT/DELETE/EXECUTE + DDL) | | `show grants for 'erp'@'%';` |
| 🟡 | `max_connections` | ≥ 500 | | `show variables like 'max_connections';` |
| 🟡 | `innodb_buffer_pool_size` | ≥ 2G | | 同上 |
| 🟡 | `sql_mode` | 不含 `ONLY_FULL_GROUP_BY` (若依 XML 存量 SQL 与该模式冲突) | | 同上 |
| 🟡 | 每日备份 | 已配置(`mysqldump` + cron) | | `crontab -l` |

### 3.2 Redis

| 🔴🟡🟢 | 检查项 | 期望值 | 实际值 | 验证命令 |
| :-- | :-- | :-- | :-- | :-- |
| 🔴 | 版本 | 5.0+ | | `redis-cli INFO server` → `redis_version` |
| 🔴 | 端口可达 | 6379 或自定义 | | `redis-cli -h <host> -p <port> -a <pwd> ping` 返回 `PONG` |
| 🔴 | 密码已启用 | requirepass 非空 | | `CONFIG GET requirepass` |
| 🔴 | `maxmemory-policy` | `allkeys-lru` 或 `volatile-lru` | | `CONFIG GET maxmemory-policy` |
| 🟡 | 持久化 | AOF 或 RDB(会话丢失容忍 5 min 内)| | `CONFIG GET appendonly` |
| 🟡 | 内存上限 | 非 0 | | `CONFIG GET maxmemory` |

### 3.3 Nginx

| 🔴🟡🟢 | 检查项 | 期望值 | 实际值 |
| :-- | :-- | :-- | :-- |
| 🔴 | 版本 | 1.18+ | |
| 🔴 | 反代规则 | `location /prod-api/` → `http://127.0.0.1:8080/`(注意结尾斜杠,与前端 `VUE_APP_BASE_API` 对齐) | |
| 🔴 | SPA 兜底 | `try_files $uri $uri/ /index.html;` | |
| 🔴 | HTTPS | 生产必须;本地 Pilot 允许 HTTP | |
| 🟡 | gzip | 开启 | |
| 🟡 | 访问日志 | 按日切割 | |

---

## 4. D 组 · 环境变量与密钥(对应 commit `6dfd59a3`)

### 4.1 已外化(必须在启动命令或 `.env.production` / systemd EnvironmentFile 中设置)

| 🔴🟡🟢 | 变量 | 用途 | 校验要求 | 实际值(**勿填真实值**,打勾即可)|
| :-- | :-- | :-- | :-- | :-- |
| 🔴 | `JWT_SECRET` | Token 密钥 | **64 位十六进制**,由密码学安全随机生成,**重启后保持一致** | ☐ 已设置且非默认 |
| 🔴 | `REDIS_PASSWORD` | Redis 鉴权 | 与 Redis `requirepass` 完全一致 | ☐ 已设置 |
| 🔴 | `DRUID_USERNAME` | Druid 监控控制台账号 | 非默认 `ruoyi` | ☐ 已修改 |
| 🔴 | `DRUID_PASSWORD` | Druid 监控控制台密码 | 非默认 `ruoyi@druid2026`,≥ 12 位含大小写数字符号 | ☐ 已修改 |
| 🔴 | `DRUID_ALLOW` | Druid 控制台 IP 白名单 | 仅含内网段,**禁止 `0.0.0.0`** | ☐ 已限内网 |

> **生成示例**:`openssl rand -hex 32` 生成 64 位 JWT_SECRET;`openssl rand -base64 18` 生成 Druid 强密码。

### 4.2 ✅ 第二轮外化已完成(2026-04-22 补丁)

**初版本节标题为"尚未外化"**,经 2026-04-22 补丁 commit 后全部落地,现列作**Pilot 部署时必须设置的增量变量**:

| 🔴🟡🟢 | 变量 | 用途 | 配置位置 | 默认值(本地) | 生产要求 |
| :-- | :-- | :-- | :-- | :-- | :-- |
| 🔴 | `DB_URL` | MySQL 完整 JDBC URL | `application-druid.yml` master 数据源 | `jdbc:mysql://localhost:3306/ry_vue?...` | 指向 Pilot 工厂内网 MySQL |
| 🔴 | `DB_USERNAME` | MySQL 应用账号 | 同上 | `root` | 禁用 root,走应用专用账号 |
| 🔴 | `DB_PASSWORD` | MySQL 密码 | 同上 | 空 | 强密码 ≥ 16 位 |
| 🔴 | `UPLOAD_PATH` | 文件上传目录 | `application.yml` `ruoyi.profile` | `D:/erp/RuoYi-Vue/uploadPath`(Windows) | **Linux 必须覆盖**,对应目录需预创并赋权 |
| 🟡 | `REDIS_HOST` | Redis 地址 | `application.yml` spring.data.redis.host | `localhost` | 独立 Redis 时改内网 IP |
| 🟡 | `REDIS_PORT` | Redis 端口 | 同上 | `6379` | 非默认端口时设 |

> **关键特性**:所有变量**带默认值**,本地开发机不设环境变量也能启动,零破坏性。
> **验证**:查看 `application-druid.yml` master 段与 `application.yml` 的 `ruoyi.profile` / redis 段,应为 `${VAR:default}` 占位形式。

---

## 5. E 组 · 网络与端口

| 🔴🟡🟢 | 端口 | 用途 | 期望 | 实际 |
| :-- | :-- | :-- | :-- | :-- |
| 🔴 | 80 / 443 | Nginx 前端 | 对办公网开放 | |
| 🔴 | 8080 | ruoyi-admin 后端 | **仅 127.0.0.1 或内网**,禁止公网 | |
| 🔴 | 3306 | MySQL | **仅内网** | |
| 🔴 | 6379 | Redis | **仅内网** | |
| 🟡 | 22 | SSH | 跳板机白名单 | |
| 🟡 | `/druid` 路径 | Druid 监控 | `DRUID_ALLOW` 内网 IP 白名单拦截 | |

---

## 6. F 组 · 数据库初始化(按 `sql/README.md` 的 20 步)

| 🔴🟡🟢 | 步骤 | 脚本 | 执行结果 |
| :-- | :-- | :-- | :-- |
| 🔴 | 1 | `ry_20260321.sql` | ☐ 成功 / ☐ 失败 |
| 🔴 | 2 | `quartz.sql` | ☐ |
| 🔴 | 3 | `phase1_basic_data.sql` | ☐ |
| 🔴 | 4-17 | `phase2` ~ `phase16` 按顺序 | ☐ 全部成功 |
| 🔴 | 18 | `sprint2_inventory_pushdown.sql` | ☐ |
| 🔴 | 18b | `sys_automation.sql` | ☐ |
| 🟡 | 19 | `init_inventory_data.sql`(可选) | ☐ |
| 🟡 | 20 | `ops_panel_init.sql`(可选) | ☐ |
| 🔴 | 字符集验证 | 随机抽 3 张含中文字段的表 `show create table ...` 确认 `DEFAULT CHARSET=utf8mb4` | ☐ |
| 🔴 | 菜单验证 | 登录前端后 ERP 顶级目录 + 7 个二级目录 + 功能页全部显示 | ☐ |

> **禁止事项**:跳序、修改已落库脚本、手工补数据(见根 `CLAUDE.md`)。

---

## 7. G 组 · 应用部署验证

| 🔴🟡🟢 | 检查项 | 期望 | 实际 |
| :-- | :-- | :-- | :-- |
| 🔴 | 后端启动日志 | 无 ERROR,输出 `Started RuoYiApplication` | |
| 🔴 | `/actuator/health` 或 `/` 首页 | 200 OK | |
| 🔴 | 默认 admin 密码 | **已改**,非 `admin123` | |
| 🔴 | 登录成功 | 返回 JWT,Authorization 头走通 | |
| 🔴 | 前端白屏排查 | `dist/` 部署位置、Nginx `try_files` 生效 | |
| 🔴 | 前端请求前缀 | `.env.production` 的 `VUE_APP_BASE_API=/prod-api` 与 Nginx 一致 | |
| 🟡 | 日志目录权限 | 应用进程有写权限 | |
| 🟡 | 上传路径 | 真实存在且可写(对齐 §4.2 `UPLOAD_PATH`)| |
| 🟡 | Flowable 表初始化 | `act_*` 表自动创建成功 | |
| 🟡 | Druid 监控页 | 登录正常,仅白名单可达 | |
| 🟡 | 服务监控页 | CPU/内存/磁盘数据返回 | |
| 🟢 | Swagger / Springdoc | 可访问(生产建议关闭) | |

---

## 8. H 组 · 安全基线与备份回滚

| 🔴🟡🟢 | 项 | 期望 | 实际 |
| :-- | :-- | :-- | :-- |
| 🔴 | admin 账号默认密码 | 已改 | |
| 🔴 | 系统账户中默认测试账号(`ry`、`test`)| 已禁用或删除 | |
| 🔴 | 数据库备份预案 | Pilot 上线前基线全量 1 次 + 每日增量 | |
| 🔴 | 回滚方案 | 上一版本 jar 包 + 数据库备份文件,文档化 | |
| 🟡 | 操作日志表保留策略 | 与 03 §7 对齐(6 个月) | |
| 🟡 | Flowable 历史留痕 | `history-level: full` | |

---

## 9. I 组 · 遗留动作(核查后同步更新)

初版本节的 5 项 P0 补丁已在 **2026-04-22 补丁 commit** 中全部落地(详见 v1.1 变更日志)。Pilot 核查时 §4.2 的 6 个变量必须逐项设置并勾选。

若后续再发现新的硬编码或未外化配置,在此记录:

- [ ] _(预留空行以便后续增量)_

---

## 10. 签字与归档

```
___________________________     ___________________________
Pilot 运维(姓名/日期)         架构师复核(姓名/日期)


___________________________     ___________________________
PM 归档(姓名/日期)            CCB 决策(上线 Go/No-Go)
```

**归档路径**:`docs/pilot-checklist-records/{工厂代号}-{YYYYMMDD}.md`
**有效期**:90 天,超期或配置变更后重跑

---

## 11. 与其他文档的交叉引用

| 主题 | 本文位置 | 主文档 |
| :-- | :-- | :-- |
| 部署流程 5 步 | 全文 | `06 §6.2` |
| 敏感配置外化 | §4 | `03 §1.1.2`、commit `6dfd59a3` |
| SQL 脚本顺序 | §6 | `sql/README.md` |
| JDK 17 强制 | §2、§1.1 | `06 §6.1` 偏离说明 |
| P6.1 数据迁移演练 | §0 | `05 §1.1` WBS |
| 行级权限验证 | §7 | `02 §6` |
| ADR-001 鉴权实现 | §4(JWT_SECRET) | `02 ADR-001 v1.1` |

---

## 变更日志

- **2026-04-22 v1.2**:同步 `docs/09`(基础设施审计)与 `docs/10`(老库参考)。
  §2 追加"禁用安装包清单"(JDK 8 / Nginx 1.8.1 / Redis 3.2 / MySQL 5.7.22 过老)。
  §3.1 MySQL 段新增"小版本下限 ≥ 5.7.44"与"老库迁入时必须 `ALTER TABLE CONVERT TO utf8mb4`"两行。
  字符集期望值强调"不可用 utf8,丢日文 IVS / emoji"。
- **2026-04-22 v1.1**:同日补丁落地 5 项 P0 外化
  (`DB_URL` / `DB_USERNAME` / `DB_PASSWORD` / `UPLOAD_PATH` / `REDIS_HOST` / `REDIS_PORT`)。
  §4.2 从"尚未外化"改为"第二轮外化已完成",§9 遗留动作清空。
  修改文件:`ruoyi-admin/src/main/resources/application-druid.yml`、
  `ruoyi-admin/src/main/resources/application.yml`、`.env.production.example`。
  零破坏:所有变量带默认值,本地开发不受影响。
- **2026-04-22 v1.0**:初版。基于 ADR-001 事实校正(`02` v1.1)同步产出,作为 P6.1 Pilot 部署前 Go/No-Go 判据。
