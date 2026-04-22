# 09 · `D:/下载/其他/` 基础设施安装包审计

> **定位**:对 2026-04-22 下载于 `D:/下载/其他/` 的 13 个基础设施/工具安装包逐项版本审计,对照 **docs/07 Pilot 核对清单** 标出 Go/No-Go 状态,避免 Pilot 工厂装错版本导致部署失败。
> **产出日**:2026-04-22 · **项目**:对日针织外贸 ERP
> **读者**:Pilot 现场运维、DevOps、架构师
> **性质**:**审计报告**,用法见 §4 建议。
> **版本**:v1.0

---

## 1. 审计总表

| # | 文件 | 类别 | 声称版本 | docs/07 要求 | 判定 |
| :-: | :-- | :-- | :-- | :-- | :-: |
| 1 | `jdk-17_windows-x64_bin.exe` | JDK | 17 | 17+ | 🟢 匹配 |
| 2 | `jdk-17_windows-x64_bin_20250327_085819.exe` | JDK | 17(副本,2025-03-27)| 17+ | 🟢 重复,可删 |
| 3 | `jdk-8u161-windows-x64.zip` | JDK | **1.8.0_161** | 17+ | 🔴 **绝对禁用** |
| 4 | `apache-maven-3.6.3-bin.tar.gz` | Maven | 3.6.3 | 3.6+ | 🟢 匹配 |
| 5 | `node-v16.20.2-x64.msi` | Node.js | 16.20.2 | 16+ | 🟢 匹配 |
| 6 | `mysql57221.zip` | MySQL | 推测 **5.7.22**(2018)| 5.7.x | 🟡 **偏老,建议升级至 5.7.44**(5.7 末版 2023) |
| 7 | `Redis-x64-3.2.100.msi` | Redis | **3.2.100**(2016)| 5.0+ | 🔴 **4 个大版本落后, Pilot No-Go** |
| 8 | `redis.desktop.manager.exe` | Redis 客户端 GUI | 未知 | — | 🟢 工具类可选 |
| 9 | `nginx-1.8.1.zip` | Nginx | **1.8.1**(2015)| 1.18+ | 🔴 **10 年老版本, Pilot No-Go, 多个 CVE** |
| 10 | `elasticsearch-7.14.0.zip` | Elasticsearch | 7.14.0 | — 未列 | 🟢 仅 E18 easy-es 启用时用,版本 OK |
| 11 | `SQLyog-x86.zip` | MySQL 客户端 GUI | 未知(x86)| — | 🟢 工具类可选, x86 请确认 Pilot 机支持 |
| 12 | `RuoYi-Code-Style.xml` | IntelliJ 代码风格 | 官方 | — | 🟢 新人 onboarding 有用 |
| 13 | `若依平台_数据库表.doc` | Word 文档(26 页,2019)| — | — | 🟡 **需人工审阅**(见 §3)|

---

## 2. 🔴 Pilot No-Go 级别阻断项(3 项)

### 2.1 JDK 8 误装风险

- **文件**:`jdk-8u161-windows-x64.zip`(2018 年发布)
- **风险**:本项目 Spring Boot 4.0.3 强制 JDK 17+;若 Pilot 运维误装此包,**Maven 打包 / 应用启动均直接失败**。更糟的是可能装完只报一句模糊错误
- **处置**:
  - 立即**从 `D:/下载/其他/` 移出**或**重命名加前缀 `ARCHIVE_DO_NOT_USE_`**
  - docs/07 §2 添加"❌ 禁用包清单"子表
  - Pilot SOP 里加一条"安装 JDK 前需核对文件名含 `-17_` 字样"

### 2.2 Nginx 1.8.1(2015 年版)

- **文件**:`nginx-1.8.1.zip`
- **风险**:
  - 2015-01 发布,已知 CVE:CVE-2016-0742/0746/0747、CVE-2017-7529 等
  - 不支持 HTTP/2、不支持现代 TLS 密码套件配置、worker_rlimit_nofile 默认值过低
  - docs/07 §3.3 要求 **1.18+**
- **处置**:
  - **不可用于生产或 Pilot**
  - 替换为 nginx-1.24.0 (LTS)或 nginx-1.26.x(latest stable)
  - 推荐直接用 Docker 镜像 `nginx:1.26-alpine`(见 docs/08 E12)避免二进制管理
  - 从 `D:/下载/其他/` 移出此 zip

### 2.3 Redis 3.2.100(2016 年 Windows 版)

- **文件**:`Redis-x64-3.2.100.msi`
- **风险**:
  - 2016 年版本,**Redis 官方 Windows 构建早已停更**(微软维护的 fork 停在 3.2)
  - 缺失:Stream 数据结构(5.0)、ACL(6.0)、IO 多线程(6.0)、Redis Functions(7.0)
  - docs/07 §3.2 要求 **5.0+**(Stream + ACL 合规必需)
- **处置**:
  - **生产环境必须走 Linux 原生 Redis ≥ 5.0**(建议 7.2 LTS)
  - Windows 开发机可用微软的 Memurai(Redis 6 兼容)或 WSL2 里跑 Linux Redis
  - Pilot 工厂如只有 Windows 服务器:强烈建议 Docker(见 E12)
  - 从 `D:/下载/其他/` 移出此 msi

---

## 3. 🟡 需关注项

### 3.1 MySQL 5.7.22(推测)

- **文件**:`mysql57221.zip`
- **推测理由**:文件名 `mysql57221` ≈ mysql-5.7.22
- **风险**:
  - 5.7.22(2018-04)距今 8 年,累积 **1000+ bug 修复**(含多个安全补丁)
  - MySQL 5.7 官方 **EOL 2023-10**(社区版),Oracle 5.7 已停服
- **处置**:
  - **确认解压后实际版本** `mysql -V`
  - 若生产使用,至少升级到 5.7.44(5.7 末版,2023-10)
  - 强烈建议:**改用 MySQL 8.0 LTS** — 本项目技术栈兼容无问题,5.7 EOL 后无补丁
  - 合规侧:等保测评对 EOL 数据库有扣分
- **docs/07 §3.1 建议补一行**:`MySQL 版本 ≥ 5.7.44`(小版本下限)

### 3.2 若依平台_数据库表.doc(需人工审阅)

- **文件**:Word 97-2003 二进制(`.doc`,非 `.docx`),26 页,创建于 2019-11,最后修改 2019-11
- **推测内容**:若依框架 30+ 基础表(`sys_*`)的字段说明
- **CLI 限制**:`.doc` 二进制无法用 Python `python-docx` 或 `pandoc` 无损解析(系统无 antiword / LibreOffice)
- **处置**:
  - 由业务或架构师用 Word/WPS 打开,将章节标题与我们 `sql/ry_20260321.sql` 对照
  - 重点核对:是否文档里有但 SQL 里没落库的表 / 字段;或反之
  - 建议把关键信息提取到 Markdown,入版成 `docs/sys_tables_reference.md`
  - 产出前本文档保持"🟡 需人工审阅"状态

### 3.3 两个 JDK 17 副本

- `jdk-17_windows-x64_bin.exe` 与 `jdk-17_windows-x64_bin_20250327_085819.exe`
- 同一包,后者附日期戳(2025-03-27)
- **处置**:删一份,保留带日期戳的版本方便追溯 Update 号

---

## 4. 修订后的 Pilot 安装包白名单

基于本审计,docs/07 §2 / §3 隐含的"可用安装包"明确清单:

### 4.1 ✅ 白名单(从 `其他/` 直接可用)

| 包 | 版本 | 用途 |
| :-- | :-- | :-- |
| `jdk-17_windows-x64_bin.exe`(或带日期戳的副本) | 17.x | 后端运行 |
| `apache-maven-3.6.3-bin.tar.gz` | 3.6.3 | 打包机 |
| `node-v16.20.2-x64.msi` | 16.20.2 | 前端构建 |
| `elasticsearch-7.14.0.zip` | 7.14.0 | **仅 E18 easy-es 启用时** |
| `redis.desktop.manager.exe` | — | 可选,GUI 监控 |
| `SQLyog-x86.zip` | — | 可选,DB GUI(x86 架构需确认) |
| `RuoYi-Code-Style.xml` | — | IDE 配置,新人上手 |

### 4.2 ❌ 黑名单(从 `其他/` 必须移除或重命名归档)

| 包 | 原因 | 替代 |
| :-- | :-- | :-- |
| `jdk-8u161-windows-x64.zip` | 与 Spring Boot 4.x 不兼容 | JDK 17 副本 |
| `nginx-1.8.1.zip` | 2015 年版 + CVE | nginx 1.24/1.26 或 Docker `nginx:1.26-alpine` |
| `Redis-x64-3.2.100.msi` | Windows 构建已废 | Linux Redis 7.2 或 Docker `redis:7.2-alpine` |
| `mysql57221.zip` | 推测 5.7.22,过老 | MySQL 5.7.44(末版)或 8.0.x LTS |

### 4.3 🟡 待核查

| 项 | 动作 |
| :-- | :-- |
| `若依平台_数据库表.doc` | 指派业务 / 架构师阅读,产出 `docs/sys_tables_reference.md` |
| `mysql57221.zip` | 解压核对实际小版本,决定是否升级 |

---

## 5. 给 docs/07 Pilot 清单的增量建议(本文产出副作用)

依据本次审计,docs/07 建议 v1.2 时追加以下:

```markdown
### 2.x 禁用安装包清单(新增子章节)
| 安装包 | 禁用原因 |
| :-- | :-- |
| jdk-8u161-windows-x64.zip | Spring Boot 4.x 要求 JDK 17+ |
| nginx-1.8.1.zip | 2015 年版, 多个 CVE, 低于 §3.3 要求 1.18+ |
| Redis-x64-3.2.100.msi | 2016 年微软 fork, 官方已弃, 低于 §3.2 要求 5.0+ |

### 3.1 新增一行: MySQL 小版本下限
| 🔴 | 小版本 | ≥ 5.7.44 (末版)| (命令) `mysql -V` |
```

---

## 6. 建议的一次性整理动作

**推荐运行一次性整理**(session 结束后,由人工执行):

```bash
# 建议的目录结构
mkdir -p "D:/下载/其他-ARCHIVE"

# 归档禁用包
mv "D:/下载/其他/jdk-8u161-windows-x64.zip" \
   "D:/下载/其他/nginx-1.8.1.zip" \
   "D:/下载/其他/Redis-x64-3.2.100.msi" \
   "D:/下载/其他-ARCHIVE/"

# 删重复 JDK 17(保留带日期戳的)
# rm "D:/下载/其他/jdk-17_windows-x64_bin.exe"

# mysql57221 解压核对
unzip -l "D:/下载/其他/mysql57221.zip" | head -3
```

---

## 7. 相关文档引用

| 主题 | 本文位置 | 主文档 |
| :-- | :-- | :-- |
| Pilot 环境核对清单 | §1 / §5 | `07` |
| Nginx 最低版本 | §2.2 | `07 §3.3` |
| Redis 最低版本 | §2.3 | `07 §3.2` |
| MySQL 版本要求 | §3.1 | `07 §3.1` |
| Docker 方案规避老版本 | §2.2 / §2.3 | `08 E12` |
| Elasticsearch 版本 | §4.1 | `08 E18 easy-es` |

---

## 变更日志

- **2026-04-22 v1.0**:初版。13 个基础设施安装包逐项审计,识别 3 个 🔴 Pilot No-Go 阻断项(JDK 8 / Nginx 1.8.1 / Redis 3.2.100),2 个 🟡 关注项(MySQL 小版本 / .doc 需人工)。产出给 docs/07 的 v1.2 增量建议。
