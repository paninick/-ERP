import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sql = "SHOW COLUMNS FROM sys_dept;"
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, _ = proc.communicate(input=sql.encode('utf-8'))
print("=== sys_dept 表结构 ===")
print(out.decode('utf-8'))

print("\n=== 当前部门数据 ===")
sql2 = "SELECT dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status FROM sys_menu ORDER BY ancestors, order_num;"
sql2 = "SELECT dept_id, parent_id, ancestors, dept_name, order_num, leader, phone, email, status FROM sys_dept ORDER BY ancestors, order_num;"
proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=sql2.encode('utf-8'))
print(out2.decode('utf-8'))
