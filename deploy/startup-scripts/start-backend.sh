#!/bin/bash

# ERP后端启动脚本
# 使用说明: ./start-backend.sh [prod|dev|test]

set -e

ENV=${1:-prod}
JAR_NAME="ruoyi-admin.jar"
LOG_FILE="logs/backend-$(date +%Y%m%d-%H%M%S).log"
PID_FILE="backend.pid"

echo "启动ERP后端服务 (环境: $ENV)"
echo "时间: $(date)"

# 检查Java环境
if ! command -v java &> /dev/null; then
    echo "错误: Java未安装"
    exit 1
fi

JAVA_VERSION=$(java -version 2>&1 | head -1 | cut -d'"' -f2)
echo "Java版本: $JAVA_VERSION"

# 检查JAR文件
if [ ! -f "$JAR_NAME" ]; then
    echo "错误: 找不到JAR文件 $JAR_NAME"
    exit 1
fi

echo "JAR文件: $JAR_NAME ($(du -h "$JAR_NAME" | cut -f1))"

# 设置JVM参数
JVM_OPTS="-Xms512m -Xmx2048m"
JVM_OPTS="$JVM_OPTS -XX:+UseG1GC"
JVM_OPTS="$JVM_OPTS -XX:MaxGCPauseMillis=200"
JVM_OPTS="$JVM_OPTS -XX:+HeapDumpOnOutOfMemoryError"
JVM_OPTS="$JVM_OPTS -XX:HeapDumpPath=./logs/heapdump.hprof"

# 设置Spring Profile
SPRING_OPTS="-Dspring.profiles.active=$ENV"
SPRING_OPTS="$SPRING_OPTS -Dserver.port=8080"
SPRING_OPTS="$SPRING_OPTS -Dlogging.file.name=$LOG_FILE"

# 创建日志目录
mkdir -p logs

echo "启动命令: java $JVM_OPTS $SPRING_OPTS -jar $JAR_NAME"
echo "日志文件: $LOG_FILE"

# 启动服务
nohup java $JVM_OPTS $SPRING_OPTS -jar "$JAR_NAME" > "$LOG_FILE" 2>&1 &
PID=$!

echo $PID > "$PID_FILE"
echo "服务已启动，PID: $PID"

# 等待服务启动
echo "等待服务启动..."
sleep 10

# 检查服务状态
if curl -s http://localhost:8080/ruoyi-admin/actuator/health > /dev/null 2>&1; then
    echo "✓ 后端服务启动成功"
    echo "健康检查: http://localhost:8080/ruoyi-admin/actuator/health"
else
    echo "✗ 后端服务启动失败，请检查日志: $LOG_FILE"
    exit 1
fi

echo "启动完成"
