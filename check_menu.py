import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sql = "SELECT menu_id, menu_name, parent_id, order_num, path, component, menu_type FROM sys_menu ORDER BY parent_id, order_num;"
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = proc.communicate(input=sql.encode('utf-8'))
print(out.decode('utf-8'))
if err:
    err_text = err.decode('utf-8')
    if 'Warning' not in err_text:
        print('ERR:', err_text)

print("\n=== Total count ===")
sql2 = "SELECT COUNT(*) as total FROM sys_menu;"
proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=sql2.encode('utf-8'))
print(out2.decode('utf-8'))

print("\n=== Top-level menus only ===")
sql3 = "SELECT menu_id, menu_name, parent_id, order_num, path, component, menu_type FROM sys_menu WHERE parent_id = 0 ORDER BY order_num;"
proc3 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out3, _ = proc3.communicate(input=sql3.encode('utf-8'))
print(out3.decode('utf-8'))

print("\n=== Role-menu assignments for admin ===")
sql4 = "SELECT COUNT(*) as assigned_menus FROM sys_role_menu WHERE role_id = 1;"
proc4 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out4, _ = proc4.communicate(input=sql4.encode('utf-8'))
print(out4.decode('utf-8'))
