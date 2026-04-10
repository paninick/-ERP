# ERP 项目 - 自动化部署脚本
# 日期：2026-04-02
# 作者：Trae Code CN

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  ERP 项目 - 自动化部署" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# 配置变量
$mysqlPath = "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
$mysqlAdminPath = "C:\Program Files\MySQL\MySQL Server 8.4\bin\mysqladmin.exe"
$mysqlPassword = "password"
$dbName = "ry-vue"
$projectPath = "d:\erp\RuoYi-Vue"
$sqlPath = "$projectPath\sql"
$demoSqlPath = "$projectPath\ruoyi-demo\src\main\resources\sql"
$jarPath = "$projectPath\ruoyi-admin\target\ruoyi-admin.jar"

Write-Host "[1/7] 检查环境..." -ForegroundColor Yellow

# 检查 MySQL 是否存在
if (-not (Test-Path $mysqlPath)) {
    Write-Host "错误：MySQL 未找到！路径：$mysqlPath" -ForegroundColor Red
    Write-Host "请安装 MySQL 或修改脚本中的路径。" -ForegroundColor Red
    exit 1
}
Write-Host "✅ MySQL 已找到" -ForegroundColor Green

# 检查 SQL 文件
$baseSql = "$sqlPath\ry_20260321.sql"
$quartzSql = "$sqlPath\quartz.sql"
$demoSql = "$demoSqlPath\init.sql"

if (-not (Test-Path $baseSql)) {
    Write-Host "错误：基础数据 SQL 未找到：$baseSql" -ForegroundColor Red
    exit 1
}
Write-Host "✅ 基础数据 SQL 已找到" -ForegroundColor Green

if (-not (Test-Path $quartzSql)) {
    Write-Host "警告：定时任务 SQL 未找到：$quartzSql" -ForegroundColor Yellow
} else {
    Write-Host "✅ 定时任务 SQL 已找到" -ForegroundColor Green
}

if (-not (Test-Path $demoSql)) {
    Write-Host "警告：Demo 模块 SQL 未找到：$demoSql" -ForegroundColor Yellow
} else {
    Write-Host "✅ Demo 模块 SQL 已找到" -ForegroundColor Green
}

# 检查 JAR 文件
if (-not (Test-Path $jarPath)) {
    Write-Host "错误：JAR 文件未找到：$jarPath" -ForegroundColor Red
    Write-Host "请先编译项目。" -ForegroundColor Red
    exit 1
}
Write-Host "✅ JAR 文件已找到" -ForegroundColor Green

Write-Host ""
Write-Host "[2/7] 测试 MySQL 连接..." -ForegroundColor Yellow

# 测试 MySQL 连接
try {
    & $mysqlPath -u root -p"$mysqlPassword" -e "SELECT VERSION();" 2>$null
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ MySQL 连接成功" -ForegroundColor Green
    } else {
        Write-Host "错误：MySQL 连接失败！" -ForegroundColor Red
        Write-Host "请确保：" -ForegroundColor Yellow
        Write-Host "  1. MySQL 服务正在运行" -ForegroundColor Yellow
        Write-Host "  2. 用户名和密码正确（当前：root / $mysqlPassword）" -ForegroundColor Yellow
        Write-Host ""
        Write-Host "启动 MySQL 服务的方式：" -ForegroundColor Yellow
        Write-Host "  方式1：使用 MySQL Installer → Reconfigure" -ForegroundColor Yellow
        Write-Host "  方式2：Windows 服务管理器 → 找到 MySQL 服务 → 启动" -ForegroundColor Yellow
        Write-Host ""
        exit 1
    }
} catch {
    Write-Host "错误：MySQL 连接异常！" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[3/7] 创建数据库..." -ForegroundColor Yellow

# 创建数据库
try {
    & $mysqlPath -u root -p"$mysqlPassword" -e "CREATE DATABASE IF NOT EXISTS \`$dbName\` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ 数据库创建成功：$dbName" -ForegroundColor Green
    } else {
        Write-Host "警告：数据库可能已存在" -ForegroundColor Yellow
    }
} catch {
    Write-Host "错误：数据库创建失败！" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[4/7] 导入基础数据..." -ForegroundColor Yellow

# 导入基础数据
try {
    Write-Host "正在导入 ry_20260321.sql（这可能需要几分钟）..." -ForegroundColor Cyan
    & $mysqlPath -u root -p"$mysqlPassword" $dbName < $baseSql
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ 基础数据导入成功" -ForegroundColor Green
    } else {
        Write-Host "警告：基础数据导入可能有问题" -ForegroundColor Yellow
    }
} catch {
    Write-Host "错误：基础数据导入失败！" -ForegroundColor Red
    exit 1
}

Write-Host ""
Write-Host "[5/7] 导入定时任务数据..." -ForegroundColor Yellow

# 导入定时任务数据
if (Test-Path $quartzSql) {
    try {
        & $mysqlPath -u root -p"$mysqlPassword" $dbName < $quartzSql
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ 定时任务数据导入成功" -Foreground