import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print('=== 流程管理下的所有子菜单 ===')
cur.execute("SELECT menu_id FROM sys_menu WHERE menu_name = '流程管理'")
row = cur.fetchone()
if row:
    parent_id = row[0]
    print(f'流程管理 menu_id = {parent_id}')
    cur.execute("SELECT menu_id, menu_name, order_num, perms, menu_type FROM sys_menu WHERE parent_id = %s ORDER BY order_num", (parent_id,))
    for r in cur.fetchall():
        print(f'  id={r[0]}  name={r[1]}  order={r[2]}  type={r[4]}  perms={r[3]}')

print()
print('=== 所有含flowable权限的菜单 ===')
cur.execute("SELECT menu_id, menu_name, parent_id, menu_type, perms FROM sys_menu WHERE perms LIKE 'flowable%%' ORDER BY parent_id, order_num")
rows = cur.fetchall()
if rows:
    for r in rows:
        print(f'  id={r[0]}  name={r[1]}  parent={r[2]}  type={r[3]}  perms={r[4]}')
else:
    print('  (无)')

conn.close()
