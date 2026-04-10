# ERP 项目 - MySQL 和 Redis 环境准备指南

**日期**：2026-04-02  
**阶段**：上线准备阶段 - 环境准备

---

## 一、当前环境状态

### 1.1 已安装的环境

| 组件 | 版本 | 状态 |
|------|------|------|
| **JDK** | 17.0.10 (Microsoft) | ✅ 已安装 |
| **Maven** | 3.9.9 | ✅ 已安装 |

### 1.2 待安装/启动的环境

| 组件 | 要求版本 | 状态 |
|------|---------|------|
| **MySQL** | 8.0 或更高 | ⏳ 待安装/启动 |
| **Redis** | 6.0 或更高 | ⏳ 待安装/启动 |

---

## 二、MySQL 安装和启动指南

### 2.1 Windows 安装 MySQL

#### 方式1：使用 MySQL Installer（推荐）

1. 下载 MySQL Installer
   - 访问：https://dev.mysql.com/downloads/installer/
   - 下载 MySQL Installer for Windows

2. 运行 Installer
   - 选择"Developer Default"安装类型
   - 点击"Next"按照向导完成安装

3. 配置 MySQL
   - 设置 root 密码（请记住此密码）
   - 配置端口（默认 3306）
   - 启动 MySQL 服务

4. 验证安装
   ```bash
   # 打开命令提示符（管理员）
   mysql --version
   
   # 应该显示：
   # mysql  Ver 8.0.xx for Win64 on x86_64 (MySQL Community Server - GPL)
   ```

#### 方式2：使用 Chocolatey（如果已安装）

```bash
# 以管理员身份打开 PowerShell
choco install mysql

# 启动 MySQL 服务
net start MySQL80
```

### 2.2 启动 MySQL 服务

```bash
# 方式1：使用服务管理器
# 按下 Win + R，输入 services.msc
# 找到 MySQL80 服务，右键启动

# 方式2：使用命令行（管理员）
net start MySQL80

# 验证服务状态
sc query MySQL80
```

### 2.3 连接 MySQL 并创建数据库

```bash
# 方式1：使用命令行
mysql -u root -p
# 输入设置的 root 密码

# 方式2：使用 MySQL Workbench（图形界面）
# 打开 MySQL Workbench，连接到本地服务器
```

**创建数据库并导入数据**：
```sql
-- 在 MySQL 命令行或 Workbench 中执行

-- 1. 创建数据库
CREATE DATABASE IF NOT EXISTS `ry-vue` 
DEFAULT CHARACTER SET utf8mb4 
COLLATE utf8mb4_general_ci;

-- 2. 使用数据库
USE `ry-vue`;

-- 3. 导入基础数据
SOURCE d:/erp/RuoYi-Vue/sql/ry_20260321.sql;

-- 4. 导入定时任务数据
SOURCE d:/erp/RuoYi-Vue/sql/quartz.sql;

-- 5. 导入 Demo 模块数据
SOURCE d:/erp/RuoYi-Vue/ruoyi-demo/src/main/resources/sql/init.sql;

-- 6. 验证数据
SHOW TABLES;
SELECT COUNT(*) FROM sys_user;
SELECT COUNT(*) FROM demo_order;
```

---

## 三、Redis 安装和启动指南

### 3.1 Windows 安装 Redis

#### 方式1：使用 Memurai（Windows 上的 Redis 兼容版本，推荐）

1. 下载 Memurai
   - 访问：https://www.memurai.com/get-memurai
   - 下载 Memurai for Windows

2. 安装 Memurai
   - 运行安装程序
   - 按照向导完成安装

3. 启动 Memurai
   - Memurai 会自动作为 Windows 服务启动
   - 默认端口：6379

#### 方式2：使用 Chocolatey（如果已安装）

```bash
# 以管理员身份打开 PowerShell
choco install memurai

# 启动 Memurai 服务
net start Memurai
```

#### 方式3：使用 WSL2（Windows Subsystem for Linux）

如果已安装 WSL2：
```bash
# 在 WSL2 中安装 Redis
wsl
sudo apt update
sudo apt install redis-server

# 启动 Redis
sudo service redis-server start

# 验证 Redis
redis-cli ping
# 应该返回：PONG
```

### 3.2 验证 Redis 安装

```bash
# 方式1：使用 redis-cli
redis-cli --version
redis-cli ping
# 应该返回：PONG

# 方式2：使用 Memurai CLI
memurai-cli --version
memurai-cli ping
# 应该返回：PONG
```

---

## 四、配置应用

### 4.1 配置数据库连接

编辑文件：`d:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\application-druid.yml`

```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ry-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: root
    password: your_password  # 修改为您设置的 MySQL root 密码
```

### 4.2 配置 Redis 连接

编辑文件：`d:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\application.yml`

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password:  # 如果有密码请填写
      database: 0
      timeout: 10s
      lettuce:
        pool:
          max-active: 8
          max-wait: -1ms
          max-idle: 8
          min-idle: 0
```

### 4.3 配置文件上传路径

编辑文件：`d:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\application.yml`

```yaml
ruoyi:
  profile: D:/ruoyi/uploadPath  # 修改为您的路径
  name: RuoYi
  version: 3.9.2
  copyrightYear: 2025
```

**创建上传目录**：
```bash
# 创建上传目录
mkdir D:\ruoyi\uploadPath
```

---

## 五、启动应用

### 5.1 方式1：使用 JAR 包启动

```bash
# 进入项目目录
cd d:\erp\RuoYi-Vue\ruoyi-admin

# 启动应用
java -jar target\ruoyi-admin.jar
```

### 5.2 方式2：使用 Maven 启动（开发环境）

```bash
# 进入项目目录
cd d:\erp\RuoYi-Vue\ruoyi-admin

# 启动应用
mvn spring-boot:run
```

### 5.3 验证启动成功

**检查日志输出**：
```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::               (v4.0.3)

...
(success) =======================================
(success) 若依启动成功
(success) =======================================
...
```

**访问应用**：
- 前端地址：http://localhost:80
- 后端地址：http://localhost:8080
- API 文档：http://localhost:8080/swagger-ui/index.html
- 默认账号：admin / admin123

---

## 六、常见问题处理

### 6.1 MySQL 连接失败

**问题**：`Communications link failure`

**解决方案**：
1. 确认 MySQL 服务已启动：`net start MySQL80`
2. 检查端口 3306 是否被占用
3. 确认数据库 `ry-vue` 已创建
4. 检查用户名和密码是否正确

### 6.2 Redis 连接失败

**问题**：`Unable to connect to Redis`

**解决方案**：
1. 确认 Redis/Memurai 服务已启动
2. 检查端口 6379 是否被占用
3. 检查 Redis 密码配置

### 6.3 端口被占用

**问题**：`Port 8080 was already in use`

**解决方案**：
```bash
# 查找占用端口的进程
netstat -ano | findstr :8080

# 结束进程（替换 <PID> 为实际进程 ID）
taskkill /PID <PID> /F
```

---

## 七、部署检查清单

### 7.1 环境准备检查

| 检查项 | 状态 | 备注 |
|--------|------|------|
| JDK 17 安装 | ✅ | 17.0.10 |
| Maven 安装 | ✅ | 3.9.9 |
| MySQL 8.0 安装 | ⏳ | |
| MySQL 服务启动 | ⏳ | |
| Redis 6.0 安装 | ⏳ | |
| Redis 服务启动 | ⏳ | |
| 数据库 ry-vue 创建 | ⏳ | |
| 基础数据导入 | ⏳ | |
| Demo 模块数据导入 | ⏳ | |
| 应用配置修改 | ⏳ | |
| 应用启动成功 | ⏳ | |

---

## 八、相关文档

| 文档 | 文件 |
|------|------|
| 测试环境部署执行指南 | [测试环境部署执行指南.md](file:///d:/erp/测试环境部署执行指南.md) |
| 测试环境部署执行报告 | [测试环境部署执行报告.md](file:///d:/erp/测试环境部署执行报告.md) |
| 系统上线准备计划 | [系统上线准备计划.md](file:///d:/erp/系统上线准备计划.md) |
| 项目文档索引 | [项目文档索引.md](file:///d:/erp/项目文档索引.md) |

---

**指南结束**

**创建人**：Trae Code CN  
**日期**：2026-04-02  
**状态**：✅ 环境准备指南已创建，请按照指南安装和启动 MySQL 和 Redis！
