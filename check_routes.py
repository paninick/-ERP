import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 流程管理(5) 及其子菜单完整配置 ===")
cur.execute("""
    SELECT menu_id, menu_name, parent_id, order_num, path, component, menu_type, perms
    FROM sys_menu 
    WHERE parent_id = 5 OR menu_id = 5
    ORDER BY parent_id, order_num
""")
for r in cur.fetchall():
    path = r[4] or ''
    comp = r[5] or ''
    perms = r[7] or ''
    print(f"  id={r[0]} name={r[1]} parent={r[2]} path={path} comp={comp} type={r[6]} perms=({perms})")

print("\n=== 所有含flowable组件的菜单 ===")
cur.execute("""
    SELECT menu_id, menu_name, parent_id, path, component, menu_type
    FROM sys_menu 
    WHERE (component LIKE '%flowable%' OR component LIKE '%definition%' OR component LIKE '%expression%' OR component LIKE '%listener%' OR component LIKE '%form%')
      AND component != ''
    ORDER BY menu_id
""")
for r in cur.fetchall():
    comp = r[4] or ''
    print(f"  id={r[0]} name={r[1]} parent={r[2]} comp={comp}")

conn.close()
