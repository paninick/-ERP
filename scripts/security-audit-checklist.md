# ERP系统安全审计检查清单

> **文档版本**：v2.0  
> **创建日期**：2025-01-15  
> **更新日期**：2026-04-23  
> **责任人**：安全审计组  
> **适用范围**：RuoYi-Vue 服装生产ERP系统（对日针织服装）  
> **审计频率**：每次上线前 / 每季度例行审计 / 重大变更后立即审计

---

## 用途说明

本清单用于指导ERP系统的安全审计工作，覆盖接口权限、数据防护、会话管理、传输安全、业务逻辑等关键领域。每个检查项附有严重性分级和验证方法，审计人员可逐项确认并记录状态。通过（Pass）/ 不通过（Fail）/ 不适用（N/A）三档标注，支持灵活审校。

### 严重性定义

| 分级 | 说明 |
|------|------|
| **Critical** | 可导致数据泄露、系统入侵、资金损失等严重后果，必须立即修复 |
| **High** | 存在明显的安全风险，应在上线前修复 |
| **Medium** | 中等风险，建议在迭代周期内修复 |
| **Low** | 低风险或最佳实践建议 |
| **Info** | 信息性提示，不构成直接风险 |

---

## 审计清单

| 编号 | 检查类别 | 检查项 | 严重性 | 验证方法 | 状态 | 备注 |
|:---:|----------|--------|:------:|----------|:----:|:----:|
| **权限控制检查** | | | | | |
| 1 | 权限控制 | 所有 `/erp/**` 接口必须有 `@PreAuthorize` 注解 | Critical | 全局搜索 `@RequestMapping` 或 `@PostMapping`/`@GetMapping`，逐一确认 Controller 方法上有 `@PreAuthorize` 注解 | | |
| 2 | 权限控制 | 管理员权限与普通用户权限分离 | Critical | 对比 `sys_role` 和 `sys_role_menu` 配置，确认超管与普通角色菜单/接口权限不同 | | |
| 3 | 权限控制 | 数据权限控制（部门、用户级别） | High | 使用不同部门账号访问同一数据接口，确认只能看到本部门数据 | | |
| 4 | 权限控制 | 菜单权限配置正确 | High | 遍历菜单树，确认每个菜单项对应正确的权限标识，无冗余或缺失 | | |
| **SQL注入防护检查** | | | | | |
| 5 | SQL注入 | 所有Mapper使用 `#{}` 参数绑定，禁止 `${}` | Critical | 全局搜索 `\$\{` 在 Mapper XML 中的出现，逐一确认是否有 SQL 注入风险 | | |
| 6 | SQL注入 | XML中动态SQL使用 `<if>` 标签，避免字符串拼接 | Critical | 审查所有 Mapper XML 文件，检查是否存在 `concat`、`'+'` 等拼接写法 | | |
| 7 | SQL注入 | 复杂查询使用MyBatis参数绑定 | High | 审查复杂查询的 Mapper XML，确认所有条件参数均使用 `#{}` | | |
| 8 | SQL注入 | 存储过程调用使用参数化 | Medium | 审查存储过程调用代码，确认使用 `#{param}` 而非字符串拼接 | | |
| **XSS防护检查** | | | | | |
| 9 | XSS防护 | 前端输入框使用 `v-html` 时进行转义 | High | 搜索 `v-html` 用法，确认渲染内容经过 `DOMPurify` 或类似库过滤 | | |
| 10 | XSS防护 | 后端接口返回HTML内容时进行过滤 | High | 审查返回 HTML 的接口，确认使用 `HtmlUtils.htmlEscape()` 或过滤器进行转义 | | |
| 11 | XSS防护 | 文件上传功能限制文件类型 | Critical | 测试上传 `.jsp`、`.exe`、`.html` 等危险后缀，确认被阻止；检查文件类型白名单配置 | | |
| 12 | XSS防护 | 富文本编辑器内容过滤 | Medium | 审查富文本编辑器配置，确认启用了 XSS 过滤规则 | | |
| **敏感信息保护** | | | | | |
| 13 | 敏感信息 | 数据库密码使用环境变量 | Critical | 检查 `application-druid.yml`、`application.yml`，确认无明文密码，使用 `${}` 引用环境变量 | | |
| 14 | 敏感信息 | 日志中不打印敏感信息（密码、token等） | Medium | 全局搜索 `log.info`/`log.debug` 在敏感操作代码中的使用，确认不打印 `password`、`token` 等字段 | | |
| 15 | 敏感信息 | 配置文件不包含硬编码密码 | Critical | 扫描所有 `.yml`、`.properties`、`.xml`、`.env`、`.json` 配置文件，确认无硬编码密钥或密码 | | |
| 16 | 敏感信息 | API密钥加密存储 | High | 检查第三方服务 API 密钥存储方式，确认已加密或使用配置中心管理 | | |
| **会话安全** | | | | | |
| 17 | 会话安全 | 会话超时时间配置合理 | Medium | 检查 `application.yml` 中 `server.servlet.session.timeout` 配置，确认 ≤ 30 分钟 | | |
| 18 | 会话安全 | 登录失败次数限制 | High | 使用错误密码连续登录，确认超过阈值后被锁定或要求验证码 | | |
| 19 | 会话安全 | 密码强度要求 | High | 注册/修改密码时测试弱密码（如 `123456`），确认被策略拒绝；检查密码策略配置 | | |
| 20 | 会话安全 | 验证码功能启用 | Medium | 访问登录页，确认验证码正常显示；连续失败后验证码是否自动启用 | | |
| **审计日志** | | | | | |
| 21 | 审计日志 | 关键操作记录审计日志 | High | 检查 `DemoLogUtil` 使用情况，确认新增/修改/删除/导入等关键操作均有日志记录 | | |
| 22 | 审计日志 | 登录登出记录 | Medium | 执行登录登出操作，检查数据库 `sys_oper_log` 表是否生成相应记录 | | |
| 23 | 审计日志 | 数据修改记录 | High | 检查关键业务表（订单、财务）的修改操作，确认记录了修改前/修改后的数据快照 | | |
| 24 | 审计日志 | 异常访问记录 | Medium | 使用非法 Token 或越权访问接口，确认系统记录异常访问日志 | | |
| **JWT/Token 安全** | | | | | |
| 25 | JWT/Token | Token有效期设置合理 | High | 检查 `application.yml` 中 `token.expireTime` 配置，确认 ≤ 12 小时（生产建议 2-4 小时） | | |
| 26 | JWT/Token | Token刷新机制正常 | High | 测试 Token 临近过期时自动刷新，确认刷新后获得新有效期的 Token；检查 Refresh Token 轮换策略 | | |
| 27 | JWT/Token | JWT签名密钥强度足够 | Critical | 检查 `application.yml` 中 `token.secret`，确认密钥长度 ≥ 256 位，非弱密钥，非默认值 | | |
| 28 | JWT/Token | Token注销机制实现 | High | 执行退出登录操作，确认 Token 被加入黑名单或从缓存清除，重新使用旧 Token 应被拒绝 | | |
| 29 | JWT/Token | 响应头不泄露Token | Medium | 检查登录接口响应头及页面源码，确认 Token 未出现在 URL 参数或 Referer 头中；确认 Token 仅通过 Header 传递 | | |
| 30 | JWT/Token | Token存储在安全位置（HttpOnly Cookie） | High | 检查前端 Token 存储方式，确认存储在 HttpOnly Cookie 或内存变量中，而非 `localStorage`（防止 XSS 窃取） | | |
| **CSRF 防护** | | | | | |
| 31 | CSRF防护 | Spring Security CSRF保护启用 | Critical | 检查 `SecurityConfig.java`，确认 `.csrf()` 未设置为 `disable()`（或仅在API对接场景下有明确考量） | | |
| 32 | CSRF防护 | CSRF Token随表单/请求传递 | High | 检查前端请求是否携带 `_csrf` 参数或 `X-CSRF-TOKEN` 头；测试不带 Token 提交表单，确认被拒绝 | | |
| 33 | CSRF防护 | 关键操作验证Referer头 | Medium | 检查重要接口（转账、删除、修改密码）是否校验 `Referer` 头，防止跨站请求伪造 | | |
| **传输安全（HTTPS/TLS）** | | | | | |
| 34 | 传输安全 | 生产环境强制HTTPS | Critical | 检查 Nginx/IIS 配置，确认 HTTP 请求 301/302 重定向到 HTTPS；确认无 HTTP 明文接口 | | |
| 35 | 传输安全 | TLS证书有效且配置正确 | Critical | 使用 `openssl s_client` 或在线工具检查证书是否在有效期内、域名匹配、证书链完整 | | |
| 36 | 传输安全 | HSTS头配置 | High | 使用 curl 检查响应头是否包含 `Strict-Transport-Security`，确认 `max-age` ≥ 31536000（1年） | | |
| 37 | 传输安全 | SSL/TLS协议版本安全 | High | 使用 nmap 或 SSL Labs 扫描，确认禁用 SSLv2、SSLv3、TLSv1.0，仅启用 TLSv1.2+ | | |
| **CORS 配置** | | | | | |
| 38 | CORS配置 | CORS允许源列表严格配置 | Critical | 检查 `CorsConfig.java` 或 `@CrossOrigin` 注解，确认 `allowedOrigins` 未设置为 `*`，且仅包含受信任域名 | | |
| 39 | CORS配置 | 凭证传递控制 | High | 检查 `allowCredentials` 配置，确认设置为 `true` 时 `allowedOrigins` 为具体域名而非 `*` | | |
| 40 | CORS配置 | 允许的HTTP方法受限 | Medium | 检查 `allowedMethods` 配置，确认仅开放业务需要的 HTTP 方法（`GET`、`POST`、`PUT`、`DELETE`），不过度开放 | | |
| **业务安全（越权/IDOR）** | | | | | |
| 41 | 业务安全 | 水平越权防护（用户只能访问自身数据） | Critical | 使用用户 A 的 Token 访问用户 B 的订单/资料详情（修改 URL 中 ID），确认被拒绝 | | |
| 42 | 业务安全 | 垂直越权防护（普通用户不能访问管理接口） | Critical | 使用普通用户 Token 访问 `/erp/**` 管理接口，确认被 403 拒绝；测试低权限角色绕过前端直接调用 API | | |
| 43 | 业务安全 | 金额/数量字段防篡改 | High | 拦截修改请求，篡改 JSON 中的金额字段后提交，确认后端重新计算/验证金额一致性 | | |
| 44 | 业务安全 | 负数/超量提交验证 | High | 提交负数金额、负数数量、超库存数量，确认后端有校验拦截；测试订单数量 > 库存数量的场景 | | |
| 45 | 业务安全 | 订单/单据状态流转校验 | High | 尝试跳过中间状态直接修改订单状态（如未付款直接改为已完成），确认后端校验状态机 | | |
| 46 | 业务安全 | 重复提交防护（幂等性） | Medium | 连续快速点击提交按钮，确认系统有防重复机制（前端按钮禁用 + 后端幂等性校验） | | |
| 47 | 业务安全 | 批量操作权限与数量限制 | Medium | 测试批量删除/导出接口，确认限制了最大操作条数，且仅允许操作本人/本部门数据 | | |
| **第三方依赖安全扫描** | | | | | |
| 48 | 依赖安全 | Maven依赖漏洞扫描 | High | 执行 `mvn org.owasp:dependency-check-maven:check` 或 `mvn dependency:tree` 审查已知漏洞 | | |
| 49 | 依赖安全 | NPM依赖漏洞扫描 | High | 在 `ruoyi-ui/` 目录执行 `npm audit`，审查高危漏洞；检查 `package.json` 中无废弃或已知漏洞版本 | | |
| 50 | 依赖安全 | 依赖版本及时更新 | Medium | 对比中央仓库版本，检查核心依赖（Spring Boot、MyBatis、Shiro、Log4j等）是否在官方支持版本范围内 | | |
| 51 | 依赖安全 | 不引入高危/已废弃依赖 | High | 检查 `pom.xml` 和 `package.json`，确认无已知高危 CVE 的依赖版本；检查 `npm audit`/`mvn dependency-check` 报告 | | |

---

## 审计结论

| 项目 | 内容 |
|------|------|
| 审计日期 | |
| 审计人 | |
| 总计检查项 | 51 项 |
| 通过 | ___ 项 |
| 不通过 | ___ 项 |
| 不适用 | ___ 项 |
| 整体评价 | |
| 整改要求 | |
| 复查确认 | |

---

## 附录：常用审计命令速查

```bash
# 扫描 Mapper 中可能的 SQL 注入（${} 使用）
grep -rn '\${' --include="*.xml" ruoyi-demo/src/main/java/

# 检查未加权限控制的接口
grep -rn '@RequestMapping\|@PostMapping\|@GetMapping' --include="*.java" ruoyi-demo/src/main/java/com/ruoyi/demo/controller/ | grep -v '@PreAuthorize'

# 搜索前端 v-html 使用
grep -rn 'v-html' --include="*.vue" ruoyi-ui/src/

# 检查硬编码密码
grep -rn 'password:\|password=' --include="*.yml" --include="*.properties" --include="*.xml" . | grep -v '${'

# Maven 依赖漏洞检查
mvn org.owasp:dependency-check-maven:check -Dformat=HTML

# NPM 依赖漏洞检查
cd ruoyi-ui && npm audit

# CORS 配置检查
grep -rn 'allowedOrigins\|@CrossOrigin\|CorsConfig\|cors()' --include="*.java" .

# Token 配置检查
grep -rn 'token.secret\|token.expireTime\|token:' --include="*.yml" .
```
