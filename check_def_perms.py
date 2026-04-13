import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print('=== 流程定义菜单 (menu_id=600) 及其子菜单 ===')
cur.execute("SELECT menu_id, menu_name, parent_id, order_num, perms, menu_type FROM sys_menu WHERE menu_id = 600 OR parent_id = 600 ORDER BY order_num")
rows = cur.fetchall()
if rows:
    for r in rows:
        print(f'  id={r[0]}  name={r[1]}  parent={r[2]}  order={r[3]}  perms={r[4]}  type={r[5]}')
else:
    print('  (无子菜单)')

print()
print('=== 检查admin角色是否拥有流程定义权限 ===')
cur.execute("SELECT COUNT(*) FROM sys_role_menu WHERE role_id = 1 AND menu_id = 600")
count = cur.fetchone()[0]
print(f'  admin角色拥有menu_600权限: {count > 0}')

print()
print('=== 所有flowable:definition权限 ===')
cur.execute("SELECT menu_id, menu_name, perms, menu_type FROM sys_menu WHERE perms LIKE 'flowable:definition%%' ORDER BY menu_id")
rows = cur.fetchall()
if rows:
    for r in rows:
        print(f'  id={r[0]}  name={r[1]}  perms={r[2]}  type={r[3]}')
else:
    print('  (无 - 需要创建!)')

conn.close()
