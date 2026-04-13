import requests

base = "http://localhost:8080"

print("=== 测试 /flowable/definition/list (无需token) ===")
r = requests.get(f"{base}/flowable/definition/list", params={"pageNum":1,"pageSize":10})
print(f"HTTP: {r.status_code}")
print(f"Body: {r.text[:500]}")

print("\n=== 测试 /flowable/form/list (无需token) ===")
r2 = requests.get(f"{base}/flowable/form/list", params={"pageNum":1,"pageSize":10})
print(f"HTTP: {r2.status_code}")
print(f"Body: {r2.text[:300]}")
