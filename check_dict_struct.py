import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

v="SHOW COLUMNS FROM sys_dict_data;"
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out,_=p.communicate(input=v.encode("utf-8"))
print("=== sys_dict_data 表结构 ===")
print(out.decode('utf-8').strip())

v2="SELECT dict_code,dict_label,dict_value,dict_sort,dict_type,status FROM sys_dict_data WHERE dict_type='erp_sample_style' ORDER BY dict_sort LIMIT 10;"
p2=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out2,_=p2.communicate(input=v2.encode('utf-8'))
print(f"\n=== erp_sample_style 数据 ===\n{out2.decode('utf-8').strip()}")
