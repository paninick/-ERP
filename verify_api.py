import urllib.request, json

try:
    req = urllib.request.Request("http://localhost:8080/login",
        data=json.dumps({"username":"admin","password":"admin123","code":"","uuid":""}).encode('utf-8'),
        headers={"Content-Type":"application/json; charset=utf-8"})
    resp = urllib.request.urlopen(req)
    raw = json.loads(resp.read().decode('utf-8'))
    print(f"Login response: {raw}")
    token = raw.get('token')
    if not token:
        print("No token in response, checking if backend is running...")
        exit(1)

    req2 = urllib.request.Request("http://localhost:8080/system/user/list?pageNum=1&pageSize=10", headers={"Authorization":f"Bearer {token}"})
    data = json.loads(urllib.request.urlopen(req2).read().decode('utf-8'))
    total = data.get('data',{}).get('total',0)
    rows = data.get('data',{}).get('rows',[])
    print(f"\nAPI返回: 共 {total} 条用户\n")
    for u in rows[:8]:
        dept = u.get('dept',{})
        dname = dept.get('deptName','?') if isinstance(dept, dict) else str(dept)
        print(f"  [{u.get('userName',''):6s}] {u.get('nickName',''):6s} → {dname}")
except Exception as e:
    print(f"Error: {e}")
