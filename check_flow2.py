import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

v="SELECT menu_id,menu_name,parent_id,order_num,menu_type FROM sys_menu WHERE parent_id IN(5,603,605) OR menu_id=5 ORDER BY parent_id,order_num;"
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out,_=p.communicate(input=v.encode("utf-8"))
print("=== 当前流程管理结构 ===")
print(out.decode("utf-8").strip())
