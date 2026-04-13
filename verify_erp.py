import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'
v="""SELECT COUNT(*) as total FROM sys_menu WHERE menu_id IN(6,7,8) OR parent_id IN(6,7,8) OR parent_id IN(700,701,702,800,801,802,803,804,805,806,807,900,901,902,903,904,905,906,907,908) OR parent_id IN(806,900,903,904,905,907);"""
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out,_=p.communicate(input=v.encode("utf-8"))
print(f"Total: {out.decode('utf-8').strip()}")

import urllib.request,json
req=urllib.request.Request("http://localhost:8080/login",data=json.dumps({"username":"admin","password":"admin123","code":"","uuid":""}).encode("utf-8"),headers={"Content-Type":"application/json; charset=utf-8"})
token=json.loads(urllib.request.urlopen(req).read().decode("utf-8"))['token']
req2=urllib.request.Request("http://localhost:8080/getRouters",headers={"Authorization":f"Bearer {token}"})
data=json.loads(urllib.request.urlopen(req2).read().decode("utf-8"))
for top in data["data"]:
    tid=top.get("meta",{}).get("title","")
    if tid in ["任务管理","基础管理","业务系统"]:
        children=top.get("children",[])
        print(f"\n[{tid}] ({len(children)} sub)")
        for c in children:
            ct=c.get("meta",{}).get("title","")
            gc=c.get("children",[])
            if gc:
                print(f"  + [{ct}] -> {len(gc)} btns")
            else:
                print(f"  - [{ct}]")
