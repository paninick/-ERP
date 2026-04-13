import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

check = "SELECT menu_id, menu_name, parent_id, order_num, menu_type FROM sys_menu WHERE menu_id=5 OR parent_id=5 ORDER BY parent_id, order_num;"
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, _ = proc.communicate(input=check.encode('utf-8'))
print(f"=== 现有流程管理菜单 ===\n{out.decode('utf-8').strip()}")
