# ERP运维管理面板 - 运维手册

**项目名称**：ERP运维管理面板  
**版本**：v1.0  
**创建日期**：2026-04-02  
**文档类型**：运维手册

---

## 目录

1. [系统概述](#系统概述)
2. [系统架构](#系统架构)
3. [部署说明](#部署说明)
4. [配置说明](#配置说明)
5. [日常运维](#日常运维)
6. [监控指标](#监控指标)
7. [告警处理](#告警处理)
8. [故障排查](#故障排查)
9. [备份恢复](#备份恢复)
10. [安全管理](#安全管理)
11. [应急处理](#应急处理)
12. [常见问题](#常见问题)

---

## 系统概述

### 系统简介
ERP运维管理面板是一个综合性的运维管理系统，提供服务监控、资源监控、告警管理、服务控制、数据备份等功能，帮助运维人员高效管理ERP系统。

### 核心功能
- **服务监控**：实时监控服务运行状态
- **资源监控**：监控CPU、内存、磁盘、网络等系统资源
- **告警管理**：灵活的告警规则配置和通知
- **服务控制**：服务启动、停止、重启等操作
- **数据备份**：手动和定时备份，备份恢复
- **快捷操作**：常用运维操作一键执行
- **问题诊断**：智能问题诊断和解决方案指引

### 技术栈
- **后端**：Spring Boot 2.7.x + MyBatis-Plus
- **前端**：Vue 2.x + Element UI
- **数据库**：MySQL 8.0
- **缓存**：Redis 6.x
- **Web服务器**：Nginx 1.20.x

---

## 系统架构

### 整体架构
```
                    ┌─────────────┐
                    │   用户      │
                    └──────┬──────┘
                           │
                    ┌──────▼──────┐
                    │   Nginx     │
                    │ (负载均衡)   │
                    └──────┬──────┘
                           │
            ┌──────────────┼──────────────┐
            │              │              │
    ┌───────▼───────┐ ┌───▼───────┐ ┌───▼───────┐
    │  应用服务器1   │ │ 应用服务器2 │ │ 应用服务器3 │
    │  (Spring Boot) │ │(Spring Boot)│ │(Spring Boot)│
    └───────┬───────┘ └───┬───────┘ └───┬───────┘
            │              │              │
            └──────────────┼──────────────┘
                           │
            ┌──────────────┼──────────────┐
            │              │              │
    ┌───────▼───────┐ ┌───▼───────┐ ┌───▼───────┐
    │   MySQL主库    │ │ MySQL从库  │ │   Redis    │
    │   (Master)    │ │  (Slave)   │ │  (缓存)    │
    └───────────────┘ └───────────┘ └───────────┘
```

### 网络架构
- **应用服务器**：8080端口
- **MySQL**：3306端口
- **Redis**：6379端口
- **Nginx**：80/443端口

### 目录结构
```
/home/ruoyi/
├── backend/              # 后端目录
│   ├── ruoyi-admin.jar  # 应用包
│   ├── application.yml  # 配置文件
│   ├── logs/            # 日志目录
│   └── uploadPath/      # 上传文件目录
├── frontend/             # 前端目录
│   └── dist/            # 前端构建文件
└── backup/               # 备份目录
    ├── database/        # 数据库备份
    └── application/     # 应用备份
```

---

## 部署说明

### 环境要求

#### 硬件要求
| 组件 | 最低配置 | 推荐配置 |
|------|---------|---------|
| CPU | 2核 | 4核 |
| 内存 | 4G | 8G |
| 硬盘 | 50G | 100G |

#### 软件要求
| 软件 | 版本 | 说明 |
|------|------|------|
| JDK | 17+ | Java运行环境 |
| MySQL | 8.0+ | 数据库 |
| Redis | 6.x+ | 缓存服务 |
| Nginx | 1.20.x+ | Web服务器 |

### 部署步骤

#### 1. 数据库部署
```bash
# 安装MySQL
yum install -y mysql-server

# 启动MySQL
systemctl start mysqld
systemctl enable mysqld

# 创建数据库
mysql -u root -p
CREATE DATABASE `ry-vue` CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

# 执行初始化脚本
mysql -u root -p ry-vue < ops_panel_init.sql

# 创建用户并授权
CREATE USER 'ruoyi'@'%' IDENTIFIED BY 'your_password';
GRANT ALL PRIVILEGES ON `ry-vue`.* TO 'ruoyi'@'%';
FLUSH PRIVILEGES;
```

#### 2. Redis部署
```bash
# 安装Redis
yum install -y redis

# 启动Redis
systemctl start redis
systemctl enable redis

# 验证Redis
redis-cli ping
```

#### 3. 应用部署
```bash
# 创建目录
mkdir -p /home/ruoyi/backend
mkdir -p /home/ruoyi/backend/logs
mkdir -p /home/ruoyi/backend/uploadPath

# 上传应用包
scp ruoyi-admin.jar user@server:/home/ruoyi/backend/
scp application.yml user@server:/home/ruoyi/backend/

# 创建启动脚本
cat > /home/ruoyi/backend/start.sh << 'EOF'
#!/bin/bash
nohup java -jar ruoyi-admin.jar --spring.profiles.active=prod > logs/app.log 2>&1 &
echo $! > app.pid
EOF

# 创建停止脚本
cat > /home/ruoyi/backend/stop.sh << 'EOF'
#!/bin/bash
if [ -f app.pid ]; then
    kill $(cat app.pid)
    rm -f app.pid
fi
EOF

# 设置权限
chmod +x /home/ruoyi/backend/start.sh
chmod +x /home/ruoyi/backend/stop.sh

# 启动应用
cd /home/ruoyi/backend
./start.sh

# 查看日志
tail -f logs/app.log
```

#### 4. 前端部署
```bash
# 构建前端
npm run build:prod

# 上传前端文件
scp -r dist/ user@server:/home/ruoyi/frontend/

# 配置Nginx
cat > /etc/nginx/conf.d/ops.conf << 'EOF'
server {
    listen 80;
    server_name ops.example.com;

    location / {
        root /home/ruoyi/frontend/dist;
        index index.html;
        try_files $uri $uri/ /index.html;
    }

    location /prod-api/ {
        proxy_pass http://localhost:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
    }
}
EOF

# 重启Nginx
nginx -t
systemctl reload nginx
```

---

## 配置说明

### 应用配置

#### application.yml
```yaml
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/ry-vue?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8
    username: ruoyi
    password: your_password
  redis:
    host: localhost
    port: 6379
    password:
    database: 0

ruoyi:
  profile: /home/ruoyi/backend/uploadPath
  name: RuoYi
  version: 1.0.0
  copyrightYear: 2024
```

### 日志配置

#### 日志级别
- **ERROR**：错误日志，需要立即处理
- **WARN**：警告日志，需要关注
- **INFO**：信息日志，正常运行信息
- **DEBUG**：调试日志，开发调试使用

#### 日志位置
- 应用日志：`/home/ruoyi/backend/logs/app.log`
- 错误日志：`/home/ruoyi/backend/logs/error.log`
- 访问日志：`/var/log/nginx/access.log`

---

## 日常运维

### 系统检查

#### 每日检查清单
- [ ] 检查应用服务器运行状态
- [ ] 检查数据库运行状态
- [ ] 检查Redis运行状态
- [ ] 检查Nginx运行状态
- [ ] 检查磁盘空间使用情况
- [ ] 检查系统资源使用情况
- [ ] 检查应用日志
- [ ] 检查错误日志
- [ ] 检查告警信息

#### 每周检查清单
- [ ] 检查备份情况
- [ ] 检查数据库性能
- [ ] 检查系统安全更新
- [ ] 检查用户权限
- [ ] 检查操作日志

### 服务管理

#### 应用服务管理
```bash
# 启动应用
cd /home/ruoyi/backend
./start.sh

# 停止应用
cd /home/ruoyi/backend
./stop.sh

# 重启应用
cd /home/ruoyi/backend
./stop.sh
sleep 5
./start.sh

# 查看应用状态
ps -ef | grep ruoyi-admin.jar

# 查看应用日志
tail -f /home/ruoyi/backend/logs/app.log
```

#### 数据库服务管理
```bash
# 启动MySQL
systemctl start mysqld

# 停止MySQL
systemctl stop mysqld

# 重启MySQL
systemctl restart mysqld

# 查看MySQL状态
systemctl status mysqld

# 查看MySQL日志
tail -f /var/log/mysqld.log
```

#### Redis服务管理
```bash
# 启动Redis
systemctl start redis

# 停止Redis
systemctl stop redis

# 重启Redis
systemctl restart redis

# 查看Redis状态
systemctl status redis

# 查看Redis信息
redis-cli info
```

#### Nginx服务管理
```bash
# 启动Nginx
systemctl start nginx

# 停止Nginx
systemctl stop nginx

# 重启Nginx
systemctl restart nginx

# 重载Nginx配置
systemctl reload nginx

# 查看Nginx状态
systemctl status nginx

# 测试Nginx配置
nginx -t
```

### 日志管理

#### 日志查看
```bash
# 查看应用日志
tail -f /home/ruoyi/backend/logs/app.log

# 查看错误日志
tail -f /home/ruoyi/backend/logs/error.log

# 查看Nginx访问日志
tail -f /var/log/nginx/access.log

# 查看Nginx错误日志
tail -f /var/log/nginx/error.log

# 查看MySQL日志
tail -f /var/log/mysqld.log
```

#### 日志清理
```bash
# 清理30天前的应用日志
find /home/ruoyi/backend/logs -name "*.log" -mtime +30 -delete

# 清理90天前的Nginx日志
find /var/log/nginx -name "*.log" -mtime +90 -delete
```

---

## 监控指标

### 系统监控

#### CPU使用率
- **正常范围**：< 70%
- **告警阈值**：> 80%（警告），> 90%（严重）
- **检查命令**：`top`、`vmstat`

#### 内存使用率
- **正常范围**：< 80%
- **告警阈值**：> 85%（警告），> 95%（严重）
- **检查命令**：`free -h`、`top`

#### 磁盘使用率
- **正常范围**：< 80%
- **告警阈值**：> 85%（警告），> 95%（严重）
- **检查命令**：`df -h`

#### 网络流量
- **正常范围**：根据实际情况
- **告警阈值**：持续高流量
- **检查命令**：`iftop`、`nethogs`

### 应用监控

#### JVM内存
- **堆内存使用**：< 80%
- **非堆内存使用**：< 80%
- **检查方式**：JConsole、VisualVM

#### 应用响应时间
- **正常范围**：< 500ms
- **告警阈值**：> 1s（警告），> 2s（严重）

#### 并发用户数
- **正常范围**：根据实际情况
- **告警阈值**：超过设计容量

### 数据库监控

#### 连接数
- **正常范围**：< 80%最大连接数
- **告警阈值**：> 85%（警告），> 95%（严重）
- **检查SQL**：`SHOW STATUS LIKE 'Threads_connected';`

#### 慢查询
- **正常范围**：< 10个/分钟
- **告警阈值**：> 20个/分钟
- **检查方式**：慢查询日志

#### 主从同步
- **正常状态**：同步正常
- **告警阈值**：同步延迟 > 30s
- **检查SQL**：`SHOW SLAVE STATUS;`

### Redis监控

#### 内存使用率
- **正常范围**：< 80%
- **告警阈值**：> 85%（警告），> 95%（严重）
- **检查命令**：`redis-cli info memory`

#### 命中率
- **正常范围**：> 90%
- **告警阈值**：< 80%
- **检查命令**：`redis-cli info stats`

---

## 告警处理

### 告警级别

| 级别 | 说明 | 响应时间 | 处理时间 |
|------|------|---------|---------|
| P0 - 严重 | 系统不可用、数据丢失 | 立即 | 30分钟 |
| P1 - 高 | 核心功能异常 | 30分钟 | 2小时 |
| P2 - 中 | 次要功能异常 | 1小时 | 4小时 |
| P3 - 低 | 轻微问题 | 2小时 | 24小时 |

### 告警流程

```
告警触发 → 告警通知 → 确认告警 → 问题排查 → 问题修复 → 验证修复 → 关闭告警
```

### 常见告警处理

#### CPU使用率过高
1. 检查进程占用：`top`
2. 定位高CPU进程
3. 分析进程原因
4. 优化或重启进程

#### 内存使用率过高
1. 检查内存使用：`free -h`
2. 检查进程内存：`top`
3. 分析内存泄漏
4. 优化或重启应用

#### 磁盘空间不足
1. 检查磁盘使用：`df -h`
2. 查找大文件：`du -sh *`
3. 清理日志文件
4. 清理临时文件
5. 扩容磁盘

#### 应用服务异常
1. 检查应用状态：`ps -ef | grep java`
2. 查看应用日志：`tail -f logs/app.log`
3. 查看错误日志：`tail -f logs/error.log`
4. 重启应用服务
5. 验证服务恢复

---

## 故障排查

### 排查流程

```
1. 确认问题现象
   ↓
2. 收集相关信息（日志、监控、配置）
   ↓
3. 分析问题原因
   ↓
4. 制定解决方案
   ↓
5. 实施方案
   ↓
6. 验证修复
   ↓
7. 记录总结
```

### 常见故障

#### 应用无法启动
**可能原因**：
- 端口被占用
- 配置错误
- 数据库连接失败
- 依赖缺失

**排查步骤**：
1. 检查端口：`netstat -tlnp | grep 8080`
2. 查看日志：`tail -f logs/app.log`
3. 检查配置：`cat application.yml`
4. 检查数据库连接：`telnet localhost 3306`

#### 应用响应慢
**可能原因**：
- 数据库慢查询
- 内存不足
- CPU过高
- 网络问题

**排查步骤**：
1. 查看应用日志
2. 检查数据库慢查询
3. 检查系统资源：`top`、`free -h`
4. 检查网络：`ping`、`traceroute`

#### 数据库连接失败
**可能原因**：
- 数据库未启动
- 网络问题
- 用户名密码错误
- 数据库权限问题

**排查步骤**：
1. 检查数据库状态：`systemctl status mysqld`
2. 测试连接：`mysql -u user -p -h host`
3. 检查配置：`cat application.yml`
4. 检查网络：`telnet host 3306`

#### Redis连接失败
**可能原因**：
- Redis未启动
- 网络问题
- 密码错误

**排查步骤**：
1. 检查Redis状态：`systemctl status redis`
2. 测试连接：`redis-cli ping`
3. 检查配置：`cat application.yml`
4. 检查网络：`telnet host 6379`

---

## 备份恢复

### 数据库备份

#### 手动备份
```bash
# 备份数据库
mysqldump -u root -p ry-vue > /home/ruoyi/backup/database/ry-vue_$(date +%Y%m%d_%H%M%S).sql

# 压缩备份
gzip /home/ruoyi/backup/database/ry-vue_$(date +%Y%m%d_%H%M%S).sql
```

#### 自动备份
```bash
# 创建备份脚本
cat > /home/ruoyi/backup/database/backup.sh << 'EOF'
#!/bin/bash
BACKUP_DIR="/home/ruoyi/backup/database"
DATE=$(date +%Y%m%d_%H%M%S)
mysqldump -u root -p'your_password' ry-vue | gzip > $BACKUP_DIR/ry-vue_$DATE.sql.gz

# 保留30天
find $BACKUP_DIR -name "ry-vue_*.sql.gz" -mtime +30 -delete
EOF

# 设置定时任务
crontab -e
# 每天凌晨2点备份
0 2 * * * /home/ruoyi/backup/database/backup.sh
```

#### 数据库恢复
```bash
# 解压备份
gunzip ry-vue_20240101_020000.sql.gz

# 恢复数据库
mysql -u root -p ry-vue < ry-vue_20240101_020000.sql
```

### 应用备份

#### 备份应用
```bash
# 备份应用包
cp /home/ruoyi/backend/ruoyi-admin.jar /home/ruoyi/backup/application/ruoyi-admin_$(date +%Y%m%d_%H%M%S).jar

# 备份配置
cp /home/ruoyi/backend/application.yml /home/ruoyi/backup/application/application_$(date +%Y%m%d_%H%M%S).yml
```

#### 恢复应用
```bash
# 停止应用
cd /home/ruoyi/backend
./stop.sh

# 恢复应用包
cp /home/ruoyi/backup/application/ruoyi-admin_20240101_020000.jar /home/ruoyi/backend/ruoyi-admin.jar

# 恢复配置
cp /home/ruoyi/backup/application/application_20240101_020000.yml /home/ruoyi/backend/application.yml

# 启动应用
./start.sh
```

---

## 安全管理

### 用户管理

#### 添加用户
1. 登录系统
2. 进入系统管理 → 用户管理
3. 点击新增用户
4. 填写用户信息
5. 分配角色权限
6. 保存用户

#### 修改用户
1. 登录系统
2. 进入系统管理 → 用户管理
3. 选择用户
4. 点击编辑
5. 修改用户信息
6. 保存用户

#### 删除用户
1. 登录系统
2. 进入系统管理 → 用户管理
3. 选择用户
4. 点击删除
5. 确认删除

### 权限管理

#### 角色管理
1. 登录系统
2. 进入系统管理 → 角色管理
3. 新增/编辑角色
4. 配置角色权限
5. 保存角色

#### 权限分配
1. 登录系统
2. 进入系统管理 → 用户管理
3. 选择用户
4. 点击分配角色
5. 选择角色
6. 保存分配

### 安全检查

#### 定期安全检查
- [ ] 检查系统安全更新
- [ ] 检查用户权限
- [ ] 检查操作日志
- [ ] 检查密码策略
- [ ] 检查访问日志

#### 安全更新
```bash
# 更新系统
yum update -y

# 更新应用依赖
# 重新构建应用
```

---

## 应急处理

### 应急流程

```
1. 发现应急事件
   ↓
2. 启动应急响应
   ↓
3. 评估影响范围
   ↓
4. 执行应急措施
   ↓
5. 恢复服务
   ↓
6. 验证恢复
   ↓
7. 总结改进
```

### 常见应急场景

#### 系统崩溃
**应急措施**：
1. 立即通知相关人员
2. 检查系统状态
3. 查看错误日志
4. 尝试重启服务
5. 如无法恢复，执行回滚
6. 验证服务恢复

#### 数据丢失
**应急措施**：
1. 立即停止写入
2. 评估数据丢失范围
3. 从备份恢复数据
4. 验证数据完整性
5. 分析丢失原因
6. 改进备份策略

#### 安全漏洞
**应急措施**：
1. 立即隔离受影响系统
2. 评估漏洞影响
3. 修复安全漏洞
4. 检查是否被入侵
5. 清除恶意代码
6. 加强安全防护

---

## 常见问题

### 应用相关

**Q: 应用启动失败怎么办？**
A: 查看应用日志，检查端口是否被占用，检查配置是否正确。

**Q: 应用响应慢怎么办？**
A: 检查系统资源使用情况，检查数据库慢查询，检查网络连接。

**Q: 如何查看应用日志？**
A: 使用命令：`tail -f /home/ruoyi/backend/logs/app.log`

### 数据库相关

**Q: 数据库连接失败怎么办？**
A: 检查数据库是否启动，检查网络连接，检查用户名密码。

**Q: 数据库慢查询怎么办？**
A: 查看慢查询日志，优化SQL语句，添加索引。

**Q: 如何备份数据库？**
A: 使用命令：`mysqldump -u root -p ry-vue > backup.sql`

### 系统相关

**Q: 磁盘空间不足怎么办？**
A: 清理日志文件，清理临时文件，删除不必要的文件，扩容磁盘。

**Q: CPU使用率过高怎么办？**
A: 使用top命令查看进程，定位高CPU进程，优化或重启进程。

**Q: 内存使用率过高怎么办？**
A: 使用free命令查看内存，使用top查看进程内存，优化或重启应用。

---

## 附录

### 常用命令

#### 系统命令
```bash
# 查看系统信息
uname -a

# 查看磁盘使用
df -h

# 查看内存使用
free -h

# 查看进程
top
ps -ef

# 查看网络连接
netstat -tlnp
ss -tlnp
```

#### 服务命令
```bash
# 启动服务
systemctl start service_name

# 停止服务
systemctl stop service_name

# 重启服务
systemctl restart service_name

# 查看服务状态
systemctl status service_name
```

### 联系方式

- **技术支持**：support@example.com
- **紧急联系**：138-xxxx-xxxx

---

**文档结束**

**创建人**：AI技术学习系统  
**创建日期**：2026-04-02
