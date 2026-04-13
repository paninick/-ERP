import requests
import json

print("="*60)
print("  验证 基础管理/业务系统/库存管理 接口")
print("="*60)

BASE = "http://localhost:8080"

print("\n1. 登录 /login")
login_data = {"username": "admin", "password": "admin123"}
try:
    resp = requests.post(f"{BASE}/login", json=login_data, timeout=10)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        login_json = resp.json()
        print(f"   Token: {login_json.get('token', '')[:30]}...")
        token = login_json.get('token', '')
    else:
        print(f"   内容: {resp.text[:200]}")
except Exception as e:
    print(f"   异常: {e}")
    token = ""

if not token:
    print("\n❌ 登录失败，无法继续验证")
    exit(1)

headers = {"Authorization": f"Bearer {token}"}

tests = [
    ("基础管理-客户管理",      "/erp/customer/list"),
    ("基础管理-供应商管理",    "/erp/supplier/list"),
    ("基础管理-主料管理",      "/erp/material/main/list"),
    ("基础管理-辅料管理",      "/erp/auxiliary/list"),
    ("基础管理-客户模板",      "/erp/customerTemplate/list"),
    ("基础管理-仓库维护",      "/erp/warehouse/list"),
    ("基础管理-库区管理",      "/erp/warehousearea/list"),
    ("基础管理-仓位管理",      "/erp/warehouselocation/list"),
    ("基础管理-数据导入",      "/erp/dataimport/list"),
    ("业务系统-打样通知",      "/erp/notice/list"),
    ("业务系统-样衣BOM",       "/erp/tech/list"),
    ("业务系统-销售订单",      "/erp/sales/order/list"),
    ("业务系统-生产计划",      "/erp/plan/list"),
    ("业务系统-采购订单",      "/erp/purchase/list"),
    ("库存管理-入库单",        "/erp/stockIn/list"),
    ("库存管理-出库单",        "/erp/stockOut/list"),
    ("库存管理-库存查询",      "/erp/stock/list"),
    ("流程管理-流程定义",      "/flowable/definition/list"),
    ("流程管理-表单配置",      "/flowable/form/list"),
    ("流程管理-流程表达式",    "/flowable/expression/list"),
    ("流程管理-流程监听",      "/flowable/listener/list"),
    ("任务管理-待办任务",      "/flowable/task/todo/list"),
    ("任务管理-已办任务",      "/flowable/task/finished/list"),
    ("任务管理-已发任务",      "/flowable/task/myProcess/list"),
]

success = 0
failed = 0

for name, path in tests:
    try:
        resp = requests.get(f"{BASE}{path}", headers=headers, timeout=5)
        if resp.status_code == 200:
            data = resp.json()
            rows = data.get('rows', [])
            print(f"  ✅ {name:<20} -> {resp.status_code} ({len(rows)} rows)")
            success += 1
        else:
            print(f"  ❌ {name:<20} -> {resp.status_code}")
            failed += 1
    except Exception as e:
        print(f"  ❌ {name:<20} -> 异常: {e}")
        failed += 1

print(f"\n{'='*60}")
print(f"  测试完成: 成功 {success} / 失败 {failed}")
print(f"{'='*60}")

if failed == 0:
    print("\n🎉 全部成功！")
else:
    print(f"\n⚠️  还有 {failed} 个接口失败，请检查！")
