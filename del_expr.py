import subprocess
MYSQL=r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sqls = []
sqls.append("-- 删除'流程表达式查询'菜单(604)及其5个按钮(3010-3014)")
sqls.append("DELETE FROM sys_role_menu WHERE menu_id IN (604, 3010, 3011, 3012, 3013, 3014);")
sqls.append("DELETE FROM sys_menu WHERE menu_id IN (604, 3010, 3011, 3012, 3013, 3014);")

full_sql = "\n".join(sqls)
proc = subprocess.Popen([MYSQL, "-u", "root", "--default-character-set=utf8mb4", "ry_vue"],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = proc.communicate(input=full_sql.encode("utf-8"))
print("OK" if proc.returncode == 0 else f"ERR:{err.decode('utf-8')[:300]}")

v = """SELECT menu_id, menu_name, parent_id, order_num,
       CASE menu_type WHEN 'M' THEN '[M]' WHEN 'C' THEN '[C]' ELSE '[F]' END AS T
FROM sys_menu WHERE parent_id=5 OR menu_id=5
ORDER BY parent_id, order_num;"""
proc2 = subprocess.Popen([MYSQL, "-u", "root", "--default-character-set=utf8mb4", "ry_vue"],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE)
out2, _ = proc2.communicate(input=v.encode("utf-8"))
print(f"\n=== 流程管理当前结构 ===\n{out2.decode('utf-8').strip()}")
