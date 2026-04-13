import requests

base = "http://localhost:8080"

# 用form-data方式登录（不是JSON）
print("=== form-data方式登录 ===")
r = requests.post(f"{base}/login",
    data={"username": "admin", "password": "admin123"})
print(f"HTTP状态码: {r.status_code}")
print(f"Body: {r.text[:300]}")

token = None
if r.status_code == 200:
    try:
        data = r.json()
        token = data.get("token")
        print(f"Token获取成功!")
    except:
        pass

if token:
    print("\n=== 测试 /flowable/definition/list ===")
    r2 = requests.get(f"{base}/flowable/definition/list",
        headers={"Authorization": f"Bearer {token}"})
    print(f"HTTP: {r2.status_code}")
    print(f"Body: {r2.text[:500]}")

    print("\n=== 测试 /flowable/form/list ===")
    r3 = requests.get(f"{base}/flowable/form/list",
        headers={"Authorization": f"Bearer {token}"})
    print(f"HTTP: {r3.status_code}")
    print(f"Body: {r3.text[:300]}")
