import urllib.request
import json

login_data = json.dumps({"username": "admin", "password": "admin123", "code": "", "uuid": ""}).encode('utf-8')
req = urllib.request.Request("http://localhost:8080/login", data=login_data, headers={"Content-Type": "application/json; charset=utf-8"})
resp = urllib.request.urlopen(req)
token = json.loads(resp.read().decode('utf-8'))['token']

req2 = urllib.request.Request("http://localhost:8080/system/dept/list", headers={"Authorization": f"Bearer {token}"})
resp2 = urllib.request.urlopen(req2)
data = json.loads(resp2.read().decode('utf-8'))

print(f"=== 部门列表 API 返回 (共 {len(data['data'])} 条) ===\n")

def print_tree(items, parent_id=0, level=0):
    children = [x for x in items if x.get('parentId') == parent_id]
    for dept in children:
        indent = "  │" * level + "  ├─ " if level > 0 else ""
        print(f"{indent}[{dept['deptId']}] {dept['deptName']} (排序:{dept.get('orderNum', '?')})")
        print_tree(items, dept['deptId'], level + 1)

print_tree(data['data'])

print("\n" + "=" * 50)
print("树形结构预览:")
print("=" * 50)
print("富泉工贸")
print("├── 技术支持")
print("│   ├── 研发部门")
print("│   └── 测试部门")
print("├── 公司领导")
print("│   ├── 行政部")
print("│   ├── 业务部")
print("│   └── 采购部")
print("└── 生产部")
print("    ├── 服装生产部")
print("    │   └── 毛衣生产部")
print("    ├── 技术部")
print("    │   ├── 服装技术部")
print("    │   └── 毛衣技术部")
print("    ├── 前道车间")
print("    ├── 后道车间")
print("    ├── 仓库")
print("    ├── 财务部")
print("    └── 单证")
