import requests
import json

print("="*60)
print("  验证后端核心接口")
print("="*60)

BASE = "http://localhost:8080"

# 1. 登录
print("\n1. 登录 /login")
login_data = {"username": "admin", "password": "admin123"}
try:
    resp = requests.post(f"{BASE}/login", json=login_data, timeout=5)
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

# 2. 获取路由信息
print("\n2. 获取路由 /getRouters")
try:
    resp = requests.get(f"{BASE}/getRouters", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        routers = resp.json().get("data", [])
        print(f"   路由数量: {len(routers)}")
        # 找流程管理
        flow_menus = [m for m in routers if "流程" in str(m.get('meta', {}).get('title', ''))]
        print(f"   流程管理菜单: {len(flow_menus)} 个")
        for m in flow_menus:
            title = m.get('meta', {}).get('title', '')
            print(f"     - {title}")
except Exception as e:
    print(f"   异常: {e}")

# 3. 验证流程定义
print("\n3. 流程定义 /flowable/definition/list")
try:
    resp = requests.get(f"{BASE}/flowable/definition/list", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        data = resp.json()
        rows = data.get("rows", [])
        print(f"   流程数: {len(rows)}")
        for r in rows[:3]:
            print(f"     - {r.get('name', '-')}")
except Exception as e:
    print(f"   异常: {e}")

# 4. 验证表单配置
print("\n4. 表单配置 /flowable/form/list")
try:
    resp = requests.get(f"{BASE}/flowable/form/list", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        data = resp.json()
        rows = data.get("rows", [])
        print(f"   表单数: {len(rows)}")
        for r in rows[:3]:
            print(f"     - {r.get('formName', '-')}")
except Exception as e:
    print(f"   异常: {e}")

# 5. 验证流程表达式
print("\n5. 流程表达式 /flowable/expression/list")
try:
    resp = requests.get(f"{BASE}/flowable/expression/list", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        data = resp.json()
        rows = data.get("rows", [])
        print(f"   表达式数: {len(rows)}")
except Exception as e:
    print(f"   异常: {e}")

print("\n" + "="*60)
print("  DONE!")
print("="*60)
