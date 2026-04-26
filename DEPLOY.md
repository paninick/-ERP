# ERP 部署手册

## 环境要求
- JDK 17+
- MySQL 8.0+
- Redis 6+
- Node 18+ (前端构建)
- Docker 24+ (可选)

## 快速部署

### 1. 数据库
```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS ry_vue DEFAULT CHARSET utf8mb4"
mysql -u root -p ry_vue < sql/init_all.sql
```

### 2. 配置文件
```bash
cp .env.example .env
# 编辑 .env 填入生产密钥:
#   JWT_SECRET=64位随机hex
#   DB_PASSWORD=数据库密码
#   REDIS_PASSWORD=Redis密码
#   ERP_AES_KEY=16字节加密密钥
```

### 3. 后端启动
```bash
mvn package -pl ruoyi-admin -am -DskipTests
java -jar ruoyi-admin/target/ruoyi-admin.jar
```

### 4. 前端构建
```bash
cd ../ERP-UI-2
npm install && npm run build
# dist/ 目录部署到 nginx
```

### 5. Docker 一键部署
```bash
docker compose up -d
```

## 验证
```bash
# 健康检查
curl http://localhost:8080/profile/health

# 登录
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}'
```

## 备份
```bash
# 手动备份
bash tools/backup_db.sh

# 自动备份 (crontab)
0 2 * * * /app/tools/backup_db.sh 30 >> /var/log/erp_backup.log 2>&1
```

## 端口
| 服务 | 端口 |
|------|------|
| 后端 API | 8080 |
| 前端 | 80 |
| MySQL | 3306 |
| Redis | 6379 |
| Druid监控 | 8080/druid |
