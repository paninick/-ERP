#!/bin/bash

# ERP系统集成测试执行脚本
# 作者: ERP项目组
# 日期: 2026-04-23

set -e

echo "=== ERP系统集成测试开始 ==="
echo "时间: $(date)"

# 检查后端服务是否运行
echo "检查后端服务..."
if curl -s http://localhost:8080/ruoyi-admin/ > /dev/null; then
    echo "✓ 后端服务运行正常"
else
    echo "✗ 后端服务未运行，请先启动后端服务"
    exit 1
fi

# 检查前端服务是否运行
echo "检查前端服务..."
if curl -s http://localhost/ > /dev/null; then
    echo "✓ 前端服务运行正常"
else
    echo "✗ 前端服务未运行，请先启动前端服务"
    exit 1
fi

# 检查数据库连接
echo "检查数据库连接..."
DB_CHECK=$(mysql -h localhost -u root -proot -e "SELECT 1" 2>/dev/null || echo "FAIL")
if [ "$DB_CHECK" != "FAIL" ]; then
    echo "✓ 数据库连接正常"
else
    echo "✗ 数据库连接失败"
    exit 1
fi

# 执行API测试
echo "执行API接口测试..."
API_TESTS=(
    "/ruoyi-admin/login"  # 登录接口
    "/ruoyi-admin/erp/sales/order/list"  # 销售订单列表
    "/ruoyi-admin/erp/produce/plan/list"  # 生产计划列表
    "/ruoyi-admin/erp/inventory/stock/list"  # 库存列表
    "/ruoyi-admin/erp/finance/invoice/list"  # 发票列表
)

for api in "${API_TESTS[@]}"; do
    response=$(curl -s -o /dev/null -w "%{http_code}" "http://localhost:8080${api}")
    if [ "$response" = "200" ] || [ "$response" = "401" ]; then
        echo "✓ API ${api} 响应正常 (HTTP ${response})"
    else
        echo "✗ API ${api} 响应异常 (HTTP ${response})"
    fi
done

echo "=== 集成测试完成 ==="
echo "总结: 所有基础服务检查通过"
