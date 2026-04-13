import urllib.request, json

req = urllib.request.Request("http://localhost:8080/login",
    data=json.dumps({"username":"admin","password":"admin123","code":"","uuid":""}).encode('utf-8'),
    headers={"Content-Type":"application/json; charset=utf-8"})
token = json.loads(urllib.request.urlopen(req).read().decode('utf-8'))['token']

req2 = urllib.request.Request("http://localhost:8080/getRouters", headers={"Authorization":f"Bearer {token}"})
data = json.loads(urllib.request.urlopen(req2).read().decode('utf-8'))

for top in data['data']:
    if '流程' in top.get('meta',{}).get('title',''):
        print(f"✅ 找到: [{top['meta']['title']}] path={top['path']} children={len(top.get('children',[]))}")
        for child in top.get('children',[]):
            ctype = child.get('meta',{}).get('title','?')
            cpath = child.get('path','')
            grandchildren = child.get('children',[])
            if grandchildren:
                print(f"  └─ [{ctype}] ({cpath}) -> {len(grandchildren)} 子项")
                for gc in grandchildren:
                    gt = gc.get('meta',{}).get('title','?')
                    gp = gc.get('meta',{}).get('perms','')
                    print(f"      ├─ [{gt}] {gp}")
            else:
                print(f"  └─ [{ctype}] ({cpath})")
        break
else:
    print("❌ 未找到流程管理菜单")
    print("\n所有顶级菜单:")
    for t in data['data']:
        print(f"  - {t.get('meta',{}).get('title','?')}")
