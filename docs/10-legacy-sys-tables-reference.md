# 10 · 富泉历史 ERP 系统表结构参考(老 doc 提取)

> **定位**:对 `C:/Users/91306/WPSDrive/.../输出为PDF/若依平台_数据库表_20260422121456.pdf`(26 页,源 .doc 创建于 2019-11,富泉工贸内部文档)所列 31 张表作全量归档,并与当前 `sql/ry_20260321.sql`(20 张)做 delta 分析,识别**迁移风险**与**合规地雷**。
> **产出日**:2026-04-22 · **项目**:对日针织外贸 ERP
> **读者**:架构师、合规负责人、数据迁移 PM
> **性质**:**参考文档 + 风险清单**。不用于"最新"schema 查阅,仅用于理解历史库与规划迁移。
> **版本**:v1.1

---

## 0. Doc 溯源与版本判定

| 项 | 值 |
| :-- | :-- |
| 文件路径 | `C:/Users/91306/WPSDrive/1766347427/WPS企业云盘/**南通富泉工贸有限公司**/.../输出为PDF/若依平台_数据库表_20260422121456.pdf` |
| 原始格式 | MS Word 97-2003 `.doc`,26 页,5076 字符 |
| 创建时间 | **2019-11-08**,最后修改 2019-11-11 |
| 推断来源 | **若依 RuoYi 1.x-2.x(非 Vue 版)**,富泉工贸内部定制导出 |
| 支持证据 |  1. `sys_user.facedata mediumtext` — 非官方字段,富泉定制<br>2. `sys_user_online` 独立表(Shiro Session 模型),Vue 版已废<br>3. `sys_menu.target menuItem/menuBlank` — Thymeleaf 菜单打开方式,Vue SPA 不用<br>4. 所有表 `CHARSET=utf8`,非 `utf8mb4`<br>5. `AUTO_INCREMENT` 具体数值(如 oper_log=905)表明来自**运行中的库快照**,非纯 DDL |

**结论**:此 doc 代表富泉约 **2019 年的 ERP 数据库快照**,7 年前设计。与本项目 RuoYi-Vue 3.9.2(2026)**跨越 2 个大版本 + 框架切换(Shiro→Security)**,不可直接作为新库权威。

---

## 1. 老 doc 的 31 张表清单(按 doc 章节号)

### 1.1 系统表(18 张,`sys_*` + `gen_*`)

| # | doc §  | 表名 | 中文名 | 在新基线? |
| :-: | :-- | :-- | :-- | :-: |
| 1 | 1.1.1 | `sys_user_role` | 用户-角色关联 | ✅ 保留 |
| 2 | 1.1.2 | `sys_user_post` | 用户-岗位关联 | ✅ 保留 |
| 3 | 1.1.3 | `sys_user_online` | 在线用户(Shiro Session 持久化)| ❌ **已废**,改走 Redis |
| 4 | 1.1.4 | `sys_user` | 用户 | ✅ 保留但字段有增减(见 §3.1)|
| 5 | 1.1.5 | `sys_role_menu` | 角色-菜单关联 | ✅ 保留 |
| 6 | 1.1.6 | `sys_role_dept` | 角色-部门关联 | ✅ 保留 |
| 7 | 1.1.7 | `sys_role` | 角色 | ✅ 保留 |
| 8 | 1.1.8 | `sys_post` | 岗位 | ✅ 保留 |
| 9 | 1.1.9 | `sys_oper_log` | 操作日志 | ✅ 保留 |
| 10 | 1.1.10 | `sys_notice` | 通知公告 | ✅ 保留 |
| 11 | 1.1.11 | `sys_menu` | 菜单 | ✅ 保留但字段有增减(见 §3.2)|
| 12 | 1.1.12 | `sys_logininfor` | 登录日志 | ✅ 保留 |
| 13 | 1.1.13 | `sys_job_log` | 任务日志 | ✅ 保留 |
| 14 | 1.1.14 | `sys_job` | 定时任务 | ✅ 保留 |
| 15 | 1.1.15 | `sys_dict_type` | 字典类型 | ✅ 保留 |
| 16 | 1.1.16 | `sys_dict_data` | 字典数据 | ✅ 保留 |
| 17 | 1.1.17 | `sys_dept` | 部门 | ✅ 保留 |
| 18 | 1.1.18 | `sys_config` | 参数配置 | ✅ 保留 |

### 1.2 Quartz 定时任务表(11 张,`qrtz_*`)

doc §1.1.19 ~ 1.1.29 共 11 张,**在新项目中拆为 `sql/quartz.sql` 单独脚本**。列表:

`qrtz_triggers`、`qrtz_simprop_triggers`、`qrtz_simple_triggers`、`qrtz_scheduler_state`、`qrtz_paused_trigger_grps`、`qrtz_locks`、`qrtz_job_details`、`qrtz_fired_triggers`、`qrtz_cron_triggers`、`qrtz_calendars`、`qrtz_blob_triggers`

说明:标准 Quartz 2.x schema,与社区版完全一致。无定制。

### 1.3 代码生成表(2 张,`gen_*`)

| doc § | 表名 | 中文名 | 在新基线? |
| :-- | :-- | :-- | :-: |
| 1.1.30 | `gen_table_column` | 代码生成字段 | ✅ 保留 |
| 1.1.31 | `gen_table` | 代码生成表 | ✅ 保留 |

---

## 2. 当前 `ry_20260321.sql` vs 老 doc · Delta

当前基线 `sql/ry_20260321.sql` 实际含 **20 张表**(老 doc 31 张 - 11 张 qrtz 拆出 - `sys_user_online` 废表 + 1 张新增 = 20)。

### 2.1 新增(1 张)

| 表名 | 来源 | 用途 |
| :-- | :-- | :-- |
| `sys_notice_read` | RuoYi-Vue 3.9.x 扩展 | 通知已读追踪 |

### 2.2 废弃(1 张)

| 表名 | 废弃原因 |
| :-- | :-- |
| `sys_user_online` | 改用 Redis `login_tokens:*` 存活 session,DB 表不再需要 |

### 2.3 表数统计

| 来源 | sys_* + gen_* | qrtz_* | 总 |
| :-- | :-: | :-: | :-: |
| 老 doc(2019 富泉) | 20 | 11 | **31** |
| 当前 ry_20260321.sql | 20 | 0(拆到 quartz.sql) | 20 |
| 当前 quartz.sql | 0 | 11 | 11 |
| 当前总 | 20 | 11 | **31** |

**结论**:表数量一致(31),但组织方式改变:
- 老:单一 SQL 文件
- 新:业务与调度分离(`ry_*.sql` + `quartz.sql`),便于独立部署

---

## 3. 字段级关键差异

### 3.1 `sys_user` 表字段差异 ⚠️

| 字段 | 老 doc | 当前基线 | 影响 |
| :-- | :-- | :-- | :-- |
| `facedata` | `mediumtext`(富泉定制人脸数据)| **不存在** | 🔴 **PIPL 合规地雷**,详见 §4.1 |
| `dept_id` | `bigint(20) N 否` | 保留 | — |
| `user_name` | `varchar(30)` 必填 | 保留 | — |
| `user_type` | `varchar(2) DEFAULT '00'`(00 系统用户)| 保留 | — |
| `phonenumber` | `varchar(11)` | 3.9.x 已扩展为 `varchar(11)`→新长度(海外号)| 对日项目需至少 `varchar(20)`支持 +81 |
| `sex` | `char(1)`(0男 1女 2未知)| 保留 | — |
| `password` | `varchar(50)` | 3.9.x 扩展至更长(BCrypt 60 字符+)| **老库迁数据时会截断**,PBKDF2 → BCrypt 迁移需重置 |
| `salt` | `varchar(20)` | **不存在**(BCrypt 自带 salt)| 迁移时丢弃该字段 |

### 3.2 `sys_menu` 表字段差异

| 字段 | 老 doc | 当前基线 | 影响 |
| :-- | :-- | :-- | :-- |
| `target` | `varchar(20)` (menuItem/menuBlank)| 保留但 **Vue SPA 不使用** | 可迁移,语义变 no-op |
| `url` | `varchar(200) default '#'` | 保留,语义同 path | — |
| `icon` | `varchar(100) default '#'` | 语义同 | — |

### 3.3 `sys_user_online` 迁移处置

- 老库若有数据:**不迁**(Session 数据无业务价值,是运行时状态)
- 新系统启动后在 Redis 中自然重建

---

## 4. 🔴 合规与迁移风险

### 4.1 `facedata mediumtext` — PIPL 第 28 条敏感信息

- **数据性质**:人脸生物识别数据,属**敏感个人信息**(PIPL 第 28 条),处理门槛高于普通 PI
- **富泉库可能有存量**:`mediumtext` = 最大 16 MB,假设平均每条人脸数据 100 KB,理论上千条用户都有
- **若要从老库迁入新系统**:
  1. 必须取得用户**单独同意**(PIPL 第 29 条)
  2. 必须完成**个人信息保护影响评估 PIPIA**
  3. 必须**加密存储**并**严格访问控制**
  4. 如用于"刷脸考勤/门禁"需额外合规评估
- **建议**:
  - **优先方案**:**不迁** — 新系统走密码 + 短信 OTP 认证,彻底绕开人脸合规包袱
  - 次优:迁入但**物理隔离表**(`sys_user_biometric`),权限最小化,并做 PIPIA 备案
  - 告知 `docs/03` 合规负责人追加此条目

### 4.2 字符集 `utf8` 历史债 ⚠️

- 老 doc 所有表:`DEFAULT CHARSET=utf8`(3 字节 UTF-8,**不含 emoji / 部分日文扩展字符**)
- 当前 ry_20260321.sql:**无显式 CHARSET 声明**,继承数据库默认
- docs/07 §3.1 要求:`ry_vue` 库**必须** `utf8mb4`
- **迁移脚本必加**:

  ```sql
  -- 目标库字符集
  CREATE DATABASE ry_vue DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

  -- 从老库迁移时,每张表显式转换
  ALTER TABLE sys_user CONVERT TO CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
  -- ...所有 20 张表重复
  ```
- **风险**:对日项目,日文汉字 IVS(Ideographic Variation Selector,如"渡辺"的辺字异体)、emoji、古汉字全部在 utf8mb4 范围,utf8 会丢或报错

### 4.3 当前 DDL 未显式声明 CHARSET 的隐患

- `ry_20260321.sql` 20 张表**均无** `DEFAULT CHARSET=xxx` 声明
- 当 Pilot DBA 建库时若忘了加 `utf8mb4`,表会用默认(可能 `latin1`,视 MySQL 配置)
- **建议**:ry_20260321.sql 的下一个维护版本在每张表 DDL 末尾补 `DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci`,防呆

### 4.4 索引覆盖度未知

- 老 doc 几乎不列索引(只列 PK 与偶有 unique key)
- 当前 ry_20260321.sql 有索引但未审计覆盖度
- **建议**:配合 docs/07 §3.1 `innodb_buffer_pool_size` 调优,做一次索引审计(业务表上线前的独立任务)

---

## 5. 对其他文档的影响与建议增补

| 目标文档 | 建议增补 | 状态 |
| :-- | :-- | :-- |
| `03-compliance-and-audit.md` | §6.1 敏感数据清单追加 "人脸数据(facedata)· PIPL 第 28 条 · 建议不迁入" | ✅ 已做(v1.2) |
| `04-u8-migration-plan.md` | ~~新增 §X "老 RuoYi 库迁移预案"~~ | ⚠️ **04 已改为 v2.0 对标文档**,迁移内容留在本文 §4 即为归属 |
| `07-pilot-env-checklist.md` | §3.1 MySQL 迁移段补 "禁止沿用老库 utf8,迁前 ALTER 为 utf8mb4" + 禁用包清单 | ✅ 已做(v1.2) |
| `00-overview.md` | 📌 P0 清单追加 CO-006 "人脸数据迁移或废弃" | ✅ 已做(v1.2) |
| `sql/ry_20260321.sql`(下次维护) | 20 张表补显式 `DEFAULT CHARSET=utf8mb4` | ⏸ 待办 |

---

## 6. 归档

| 项 | 路径 |
| :-- | :-- |
| 源 .doc | `D:/下载/其他/若依平台_数据库表.doc`(二进制 Word 97,CLI 不可读) |
| 转换 PDF | `C:/Users/.../WPS企业云盘/.../输出为PDF/若依平台_数据库表_20260422121456.pdf` |
| 推荐入版 | **否** — 外部参考文档,不入 git;本文档 `docs/10-legacy-sys-tables-reference.md` 是摘要与 delta,是权威产物 |
| 原 PDF 持有方 | 富泉工贸 WPS 企业云盘(`1766347427`) |

---

## 7. 相关文档引用

| 主题 | 本文位置 | 主文档 |
| :-- | :-- | :-- |
| sys_user_online 废弃与 Redis 替代 | §1.1 第 3 行 / §2.2 | `02 ADR-001 v1.1` |
| facedata PIPL 合规 | §4.1 | `03 §1.1.2`(建议追加)|
| utf8 → utf8mb4 迁移 | §4.2 / §4.3 | `07 §3.1` |
| 老库迁移规划 | §4 整段 | `04 §X` 待补 |
| Quartz 11 表拆分 | §1.2 | `sql/README.md` 步骤 2 `quartz.sql` |

---

## 变更日志

- **2026-04-22 v1.1**:§5 级联状态更新。03/07/00 已同步至 v1.2(追加合规/utf8mb4/禁用包/P0 清单);
  04 已改为 v2.0 对标文档,迁移预案内容留存本文 §4 作唯一归属,取消对 04 的交叉指向。
- **2026-04-22 v1.0**:初版。基于 `若依平台_数据库表.doc`(2019 富泉)26 页 31 表全量提取,与当前 `ry_20260321.sql` 20 表对比;
  关键发现:
  1. 🔴 `sys_user.facedata` 人脸数据字段 → PIPL 第 28 条敏感信息,建议不迁
  2. 🔴 老库 `CHARSET=utf8` → 对日项目需迁 `utf8mb4`(IVS / emoji)
  3. 🟡 当前 ry_20260321.sql 未显式声明 CHARSET,防呆建议补入
  4. `sys_user_online` 在 Vue + Security 版已废,无需迁数据
  5. 31 张表全量拆解完成,可作 U8 之外的"老 RuoYi 库"迁移基础材料
