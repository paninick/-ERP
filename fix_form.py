import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sqls = []

sqls.append("-- 1. 删除'表单列表'菜单 (menu_id=602)")
sqls.append("DELETE FROM sys_role_menu WHERE menu_id = 602;")
sqls.append("DELETE FROM sys_menu WHERE menu_id = 602;")

sqls.append("")
sqls.append("-- 2. 将4个按钮从 parent_id=602 移到 parent_id=601 (表单配置)")
sqls.append("UPDATE sys_menu SET parent_id = 601 WHERE menu_id IN (3000, 3001, 3002, 3003);")

full_sql = "\n".join(sqls)
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = proc.communicate(input=full_sql.encode('utf-8'))
print("✅ 成功!" if proc.returncode == 0 else f"❌ {err.decode('utf-8')[:400]}")

verify = """
SELECT menu_id, menu_name, parent_id, order_num,
       CASE menu_type WHEN 'M' THEN '[目录]' WHEN 'C' THEN '[菜单]' ELSE '[按钮]' END AS type,
       COALESCE(perms,'') AS perms
FROM sys_menu
WHERE menu_id=5 OR parent_id=5 OR parent_id=601 OR parent_id IN (603,605) OR parent_id IN (604,606)
ORDER BY 
  CASE WHEN menu_id=5 THEN 0 ELSE 1 END,
  parent_id, order_num;
"""
proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=verify.encode('utf-8'))
print(f"\n=== 流程管理菜单树 ===\n{out2.decode('utf-8').strip()}")
