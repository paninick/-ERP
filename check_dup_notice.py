import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

v="""SELECT menu_id,menu_name,parent_id,path,component,perms 
FROM sys_menu 
WHERE path='notice' OR component LIKE '%notice%' OR perms LIKE '%notice%'
ORDER BY menu_id;"""
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out,_=p.communicate(input=v.encode("utf-8"))
print(out.decode("utf-8").strip())
