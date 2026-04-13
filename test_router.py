import urllib.request
import json
import ssl

ssl._create_default_https_context = ssl._create_unverified_context

login_data = json.dumps({"username": "admin", "password": "admin123", "code": "", "uuid": ""}).encode('utf-8')
req = urllib.request.Request("http://localhost:8080/login", data=login_data, headers={"Content-Type": "application/json; charset=utf-8"})
resp = urllib.request.urlopen(req)
token_info = json.loads(resp.read().decode('utf-8'))
token = token_info['token']
print(f"Token obtained: {token[:30]}...")

req2 = urllib.request.Request("http://localhost:8080/getRouters", headers={"Authorization": f"Bearer {token}"})
resp2 = urllib.request.urlopen(req2)
raw_bytes = resp2.read()
content_type = resp2.headers.get('Content-Type', 'unknown')
print(f"Content-Type: {content_type}")
print(f"Raw response length: {len(raw_bytes)} bytes")

text = raw_bytes.decode('utf-8')
data = json.loads(text)

print(f"\n=== Top-level menus ({len(data['data'])}) ===")
for m in data['data']:
    title = m.get('meta', {}).get('title', '?')
    path = m.get('path', '?')
    children_count = len(m.get('children', []))
    print(f"  [{title}] path={path} children={children_count}")

with open(r'd:\erp\RuoYi-Vue\router_response.json', 'w', encoding='utf-8') as f:
    json.dump(data, f, ensure_ascii=False, indent=2)
print("\nFull response saved to router_response.json")
