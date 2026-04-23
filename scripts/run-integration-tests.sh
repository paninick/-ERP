#!/bin/bash

# ERP系统集成测试执行脚本
# 作者: ERP项目组
# 日期: 2026-04-23

set -euo pipefail

echo "=== ERP系统集成测试开始 ==="
echo "时间: $(date)"

# 初始化计数器
PASS_COUNT=0
FAIL_COUNT=0

# 检查后端服务是否运行
echo "检查后端服务..."
if curl -s http://localhost:8080/ruoyi-admin/ > /dev/null; then
    echo "✓ 后端服务运行正常"
    PASS_COUNT=$((PASS_COUNT + 1))
else
    echo "✗ 后端服务未运行，请先启动后端服务"
    FAIL_COUNT=$((FAIL_COUNT + 1))
    exit 1
fi

# 检查前端服务是否运行
echo "检查前端服务..."
if curl -s http://localhost/ > /dev/null; then
    echo "✓ 前端服务运行正常"
    PASS_COUNT=$((PASS_COUNT + 1))
else
    echo "✗ 前端服务未运行，请先启动前端服务"
    FAIL_COUNT=$((FAIL_COUNT + 1))
    exit 1
fi

# 检查数据库连接
echo "检查数据库连接..."
MYSQL_PWD="${MYSQL_PWD:-}"
if [ -z "$MYSQL_PWD" ]; then
    echo "提示: 未设置 MYSQL_PWD 环境变量"
    read -s -p "请输入数据库密码: " MYSQL_PWD
    echo
fi

DB_CHECK=$(mysql -h localhost -u root -p"$MYSQL_PWD" -e "SELECT 1" 2>/dev/null || echo "FAIL")
if [ "$DB_CHECK" != "FAIL" ]; then
    echo "✓ 数据库连接正常"
    PASS_COUNT=$((PASS_COUNT + 1))
else
    echo "✗ 数据库连接失败"
    FAIL_COUNT=$((FAIL_COUNT + 1))
    exit 1
fi

# 登录获取 Token
echo "登录获取 Token..."
LOGIN_RESPONSE=$(curl -s -X POST "http://localhost:8080/ruoyi-admin/login" \
    -H "Content-Type: application/json" \
    -d '{"username":"admin","password":"admin123"}')
TOKEN=$(echo "$LOGIN_RESPONSE" | grep -o '"token":"[^"]*"' | cut -d'"' -f4 || true)

if [ -n "$TOKEN" ]; then
    echo "✓ 登录成功，Token 已获取"
    PASS_COUNT=$((PASS_COUNT + 1))
else
    echo "⚠ 登录失败，后续 API 测试将不带 Token"
    # 不计数，登录失败不影响整体检查项
fi

# 执行API测试
echo "执行API接口测试..."
API_TESTS=(
    "/ruoyi-admin/erp/sales/order/list"
    "/ruoyi-admin/erp/produce/plan/list"
    "/ruoyi-admin/erp/inventory/stock/list"
    "/ruoyi-admin/erp/finance/invoice/list"
)

for api in "${API_TESTS[@]}"; do
    if [ -n "$TOKEN" ]; then
        response=$(curl -s -o /dev/null -w "%{http_code}" \
            -H "Authorization: Bearer ${TOKEN}" \
            "http://localhost:8080${api}")
    else
        response=$(curl -s -o /dev/null -w "%{http_code}" \
            "http://localhost:8080${api}")
    fi
    if [ "$response" = "200" ] || [ "$response" = "401" ]; then
        echo "✓ API ${api} 响应正常 (HTTP ${response})"
        PASS_COUNT=$((PASS_COUNT + 1))
    else
        echo "✗ API ${api} 响应异常 (HTTP ${response})"
        FAIL_COUNT=$((FAIL_COUNT + 1))
    fi
done

echo ""
echo "=== 集成测试完成 ==="
TOTAL=$((PASS_COUNT + FAIL_COUNT))
echo "总结: ${PASS_COUNT}/${TOTAL} 项检查通过"

if [ "$FAIL_COUNT" -gt 0 ]; then
    exit 1
fi
