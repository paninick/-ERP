import urllib.request, json
req = urllib.request.Request("http://localhost:8080/login", data=json.dumps({"username":"admin","password":"admin123","code":"","uuid":""}).encode('utf-8'), headers={"Content-Type":"application/json; charset=utf-8"})
token = json.loads(urllib.request.urlopen(req).read().decode('utf-8'))['token']
req2 = urllib.request.Request("http://localhost:8080/getRouters", headers={"Authorization":f"Bearer {token}"})
data = json.loads(urllib.request.urlopen(req2).read().decode('utf-8'))
for top in data['data']:
    if '流程' in top.get('meta',{}).get('title',''):
        for child in top.get('children',[]):
            if child.get('meta',{}).get('title','') == '表单配置':
                print(f"[{child['meta']['title']}] -> {len(child.get('children',[]))} 个子项")
                for gc in child.get('children',[]):
                    print(f"  └─ [{gc['meta']['title']}] perms={gc.get('meta',{}).get('perms','')}")
        break
