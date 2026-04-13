import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 检查 flowable 相关表 ===")
tables = ['flow_expression', 'fl_form', 'fl_listener', 'flow_expression', 'flow_form', 'flow_listener',
          'flowable_expression', 'flowable_form', 'flowable_listener']
for t in tables:
    cur.execute(f"SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='ry_vue' AND table_name='{t}'")
    count = cur.fetchone()[0]
    if count > 0:
        cur.execute(f"SELECT COUNT(*) FROM `{t}`")
        rows = cur.fetchone()[0]
        print(f'  {t}: EXISTS ({rows} rows)')
    else:
        print(f'  {t}: NOT FOUND')

print()
print("=== 当前三个菜单的权限配置 ===")
for mid, name in [(600,'流程定义'),(601,'表单配置'),(603,'流程表达式'),(605,'流程监听')]:
    cur.execute("SELECT menu_id, menu_name, perms FROM sys_menu WHERE menu_id = %s", (mid,))
    r = cur.fetchone()
    if r:
        print(f'  id={r[0]} name={r[1]} perms=({r[2]})')
    else:
        print(f'  id={mid} NOT FOUND')

print()
print("=== 检查按钮权限 ==="
)
for mid in [600, 601, 603, 605]:
    cur.execute("SELECT menu_id, menu_name, perms FROM sys_menu WHERE parent_id = %s ORDER BY order_num", (mid,))
    children = cur.fetchall()
    parent_name = ''
    cur.execute("SELECT menu_name FROM sys_menu WHERE menu_id = %s", (mid,))
    pn = cur.fetchone()
    if pn: parent_name = pn[0]
    if children:
        for c in children:
            print(f'  [{parent_name}] id={c[0]} name={c[1]} perms={c[2]}')
    else:
        print(f'  [{parent_name}] (无子菜单/按钮)')

conn.close()
