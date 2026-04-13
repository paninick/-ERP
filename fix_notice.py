import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sql = "UPDATE sys_menu SET path='sampleNotice' WHERE menu_id=900;"
proc = subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out,err=proc.communicate(input=sql.encode("utf-8"))
print("OK" if proc.returncode==0 else f"ERR:{err.decode('utf-8')[:300]}")

v="SELECT menu_id,menu_name,path,component FROM sys_menu WHERE menu_id IN(107,900);"
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out2,_=p.communicate(input=v.encode("utf-8"))
print(f"\n{out2.decode('utf-8').strip()}")
