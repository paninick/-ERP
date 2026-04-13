import requests
import json

base = "http://localhost:8080"

# 1. 登录获取token
print("=== 1. 登录 ===")
login_resp = requests.post(f"{base}/login", json={
    "username": "admin",
    "password": "admin123"
})
print(f"登录状态码: {login_resp.status_code}")
print(f"登录响应: {login_resp.text[:500]}")

token = None
if login_resp.status_code == 200:
    data = login_resp.json()
    token = data.get("token")
    print(f"Token获取成功: {token[:30]}..." if token else "无token")
else:
    # 尝试其他密码
    for pwd in ["admin", "ruoyi123", "RuoYi@2026"]:
        r = requests.post(f"{base}/login", json={"username": "admin", "password": pwd})
        if r.status_code == 200:
            token = r.json().get("token")
            print(f"密码 {pwd} 成功!")
            break

if not token:
    print("\n所有密码都失败!")
    exit(1)

# 2. 测试流程定义接口
print("\n=== 2. 测试 /flowable/definition/list ===")
headers = {"Authorization": f"Bearer {token}"}
resp = requests.get(f"{base}/flowable/definition/list", headers=headers)
print(f"状态码: {resp.status_code}")
print(f"响应: {resp.text[:500]}")

# 3. 测试其他flowable接口
print("\n=== 3. 测试 /flowable/form/list ===")
resp2 = requests.get(f"{base}/flowable/form/list", headers=headers)
print(f"状态码: {resp2.status_code}")
print(f"响应: {resp2.text[:300]}")

# 4. 测试ERP接口
print("\n=== 4. 测试 /erp/customer/list ===")
resp3 = requests.get(f"{base}/erp/customerTemplate/list", headers=headers, params={"pageNum":1,"pageSize":10})
print(f"状态码: {resp3.status_code}")
print(f"响应: {resp3.text[:300]}")
