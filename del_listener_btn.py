import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

ids = [3020,3021,3022,3023,3024, 4310,4311,4312,4313,4314]
sql = f"DELETE FROM sys_role_menu WHERE menu_id IN ({','.join(map(str,ids))}); DELETE FROM sys_menu WHERE menu_id IN ({','.join(map(str,ids))});"
proc = subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out,err=proc.communicate(input=sql.encode("utf-8"))
print("OK" if proc.returncode==0 else f"ERR:{err.decode('utf-8')[:300]}")

v="SELECT menu_id,menu_name,parent_id,menu_type,perms FROM sys_menu WHERE menu_name LIKE '流程监听%' ORDER BY menu_id;"
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out2,_=p.communicate(input=v.encode("utf-8"))
print(f"\n{out2.decode('utf-8').strip()}")
