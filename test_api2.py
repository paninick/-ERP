import requests
import json

base = "http://localhost:8080"

print("=== 测试1: POST /login ===")
r = requests.post(f"{base}/login", 
    json={"username":"admin","password":"admin123"},
    headers={"Content-Type":"application/json"})
print(f"HTTP状态码: {r.status_code}")
print(f"Body: {r.text}")

token = None
if r.status_code == 200:
    try:
        data = r.json()
        token = data.get("token")
        print(f"Token: {str(token)[:30]}..." if token else "无token字段")
    except:
        pass

if token:
    print("\n=== 测试2: GET /flowable/definition/list (带token) ===")
    r2 = requests.get(f"{base}/flowable/definition/list", 
        headers={"Authorization": f"Bearer {token}"})
    print(f"HTTP状态码: {r2.status_code}")
    print(f"Body: {r2.text[:500]}")

    print("\n=== 测试3: GET /flowable/form/list (带token) ===")
    r3 = requests.get(f"{base}/flowable/form/list",
        headers={"Authorization": f"Bearer {token}"})
    print(f"HTTP状态码: {r3.status_code}")
    print(f"Body: {r3.text[:300]}")
else:
    print("\n登录失败，无法测试其他接口!")
