import requests
import json

print("="*60)
print("  验证任务管理接口")
print("="*60)

BASE = "http://localhost:8080"

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

print("\n2. 待办任务 /flowable/task/todo/list")
try:
    resp = requests.get(f"{BASE}/flowable/task/todo/list", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        data = resp.json()
        rows = data.get("rows", [])
        print(f"   待办任务数: {len(rows)}")
        for r in rows:
            print(f"     - {r.get('taskName', '-')} [{r.get('initiatorName', '-')}]")
except Exception as e:
    print(f"   异常: {e}")

print("\n3. 已办任务 /flowable/task/finished/list")
try:
    resp = requests.get(f"{BASE}/flowable/task/finished/list", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        data = resp.json()
        rows = data.get("rows", [])
        print(f"   已办任务数: {len(rows)}")
except Exception as e:
    print(f"   异常: {e}")

print("\n4. 已发任务 /flowable/task/myProcess/list")
try:
    resp = requests.get(f"{BASE}/flowable/task/myProcess/list", headers=headers, timeout=5)
    print(f"   状态: {resp.status_code}")
    if resp.status_code == 200:
        data = resp.json()
        rows = data.get("rows", [])
        print(f"   已发任务数: {len(rows)}")
except Exception as e:
    print(f"   异常: {e}")

print("\n" + "="*60)
print("  DONE!")
print("="*60)
