import requests

base = "http://localhost:8080"

print("=== 测试1: /captchaImage (应该成功) ===")
r = requests.get(f"{base}/captchaImage")
print(f"HTTP: {r.status_code}")
print(f"Body: {r.text[:200]}")

print("\n=== 测试2: /flowable/definition/list ===")
r2 = requests.get(f"{base}/flowable/definition/list", params={"pageNum":1,"pageSize":10})
print(f"HTTP: {r2.status_code}")
print(f"Body: {r2.text[:300]}")

print("\n=== 测试3: /erp/customerTemplate/list ===")
r3 = requests.get(f"{base}/erp/customerTemplate/list", params={"pageNum":1,"pageSize":10})
print(f"HTTP: {r3.status_code}")
print(f"Body: {r3.text[:300]}")
