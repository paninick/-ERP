#!/bin/bash

# ERP前端启动脚本
# 使用说明: ./start-frontend.sh [prod|dev]

set -e

ENV=${1:-prod}
NGINX_CONF="nginx-$ENV.conf"
LOG_FILE="logs/frontend-$(date +%Y%m%d-%H%M%S).log"
PID_FILE="frontend.pid"

echo "启动ERP前端服务 (环境: $ENV)"
echo "时间: $(date)"

# 检查nginx是否安装
if ! command -v nginx &> /dev/null; then
    echo "错误: nginx未安装"
    exit 1
fi

NGINX_VERSION=$(nginx -v 2>&1 | cut -d'/' -f2)
echo "nginx版本: $NGINX_VERSION"

# 检查nginx配置文件
if [ ! -f "$NGINX_CONF" ]; then
    echo "警告: 找不到nginx配置文件 $NGINX_CONF，使用默认配置"
    NGINX_CONF="nginx.conf"
fi

echo "nginx配置: $NGINX_CONF"

# 创建日志目录
mkdir -p logs

# 停止已有的nginx进程
if [ -f "$PID_FILE" ]; then
    OLD_PID=$(cat "$PID_FILE")
    if kill -0 "$OLD_PID" 2>/dev/null; then
        echo "停止旧的nginx进程 (PID: $OLD_PID)"
        nginx -s stop
        sleep 2
    fi
    rm -f "$PID_FILE"
fi

# 启动nginx
echo "启动nginx..."
nginx -c "$(pwd)/$NGINX_CONF"

# 获取nginx进程ID
sleep 2
NGINX_PID=$(pgrep -f "nginx: master" || echo "")
if [ -z "$NGINX_PID" ]; then
    echo "错误: nginx启动失败"
    exit 1
fi

echo $NGINX_PID > "$PID_FILE"
echo "nginx已启动，PID: $NGINX_PID"

# 检查nginx状态
if curl -s http://localhost/ > /dev/null 2>&1; then
    echo "✓ 前端服务启动成功"
    echo "访问地址: http://localhost/"
else
    echo "✗ 前端服务启动失败"
    exit 1
fi

echo "启动完成"
