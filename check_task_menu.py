import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 任务管理菜单检查 ===")
cur.execute("""
    SELECT menu_id, menu_name, parent_id, order_num, path, component, perms, menu_type 
    FROM sys_menu 
    WHERE (menu_name LIKE '%任务%' OR menu_name LIKE '%todo%' OR path LIKE '%task%')
       AND perms LIKE 'flowable:%'
    ORDER BY parent_id, menu_id
""")
for r in cur.fetchall():
    print(f"id={r[0]} name={r[1]} parent={r[2]} path={r[4]} comp={r[5]} perms={r[6]} type={r[7]}")

print("\n=== 任务管理完整树形结构 ===")
cur.execute("SELECT menu_id FROM sys_menu WHERE menu_name = '任务管理'")
row = cur.fetchone()
if row:
    parent_id = row[0]
    print(f"任务管理 menu_id = {parent_id}")
    cur.execute("SELECT menu_id, menu_name, parent_id, order_num, path, component, perms, menu_type FROM sys_menu WHERE parent_id = %s ORDER BY order_num", (parent_id,))
    for r in cur.fetchall():
        print(f"  id={r[0]} name={r[1]} type={r[7]} perms={r[6]} comp={r[5]}")

print("\n=== 检查 flowable/task 下所有Controller文件 ===")
import os
java_base = r"D:\erp\RuoYi-Vue\ruoyi-admin\src\main\java\com\ruoyi\flowable"
if os.path.exists(java_base):
    for f in os.listdir(java_base):
        if "Controller" in f:
            full = os.path.join(java_base, f)
            with open(full, 'r', encoding='utf-8') as fh:
                content = fh.read()
                print(f"  {f}: has @RequestMapping/todo -> {'todo' in content}")

conn.close()
print("\nDONE!")
