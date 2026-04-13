import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

v="""SELECT dict_code, dict_label, dict_value, parent_id 
FROM sys_dict_data 
WHERE dict_type IN ('erp_sample_style','erp_nation','erp_unit')
ORDER BY dict_type, dict_sort;"""
p=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out,_=p.communicate(input=v.encode("utf-8"))
print("=== 级联字典数据示例 ===")
print(out.decode('utf-8').strip())

v2="SELECT d.dict_type,d.dict_name,COUNT(dd.dict_code) as items FROM sys_dict_type d LEFT JOIN sys_dict_data dd ON d.dict_type=dd.dict_type GROUP BY d.dict_type HAVING COUNT(dd.dict_code)>0 ORDER BY d.dict_id LIMIT 20;"
p2=subprocess.Popen([MYSQL,"-u","root","--default-character-set=utf8mb4","ry_vue"],stdin=subprocess.PIPE,stdout=subprocess.PIPE)
out2,_=p2.communicate(input=v2.encode('utf-8'))
print(f"\n=== 字典数据量 ===\n{out2.decode('utf-8').strip()}")
