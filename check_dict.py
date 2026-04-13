import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

v="""SELECT dict_id, dict_name, dict_type FROM sys_dict_type 
WHERE dict_type LIKE '%cascader%' OR dict_name LIKE '%级联%' OR dict_type LIKE '%flow%' OR dict_type LIKE '%erp%'
ORDER BY dict_id;"""
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out,_=p.communicate(input=v.encode("utf-8"))
print("=== 字典类型 ===")
print(out.decode('utf-8').strip())

v2="SELECT COUNT(*) as total FROM sys_dict_type; SELECT COUNT(*) as total FROM sys_dict_data;"
p2=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out2,_=p2.communicate(input=v2.encode('utf-8'))
print(f"\n=== 字典统计 ===\n{out2.decode('utf-8').strip()}")
