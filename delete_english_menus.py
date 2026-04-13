import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 所有 flowable 相关菜单 ===")
cur.execute("SELECT menu_id, menu_name, parent_id, order_num, perms FROM sys_menu WHERE perms LIKE 'flowable:%' OR menu_name IN ('Process Definition','Query','Add','Edit','Remove','Expression','Listener') ORDER BY parent_id, order_num")
for r in cur.fetchall():
    print(f"  id={r[0]}  name={r[1]}  parent={r[2]}  order={r[3]}  perms={r[4]}")

print("\n=== 英文菜单ID (待删除) ===")
cur.execute("SELECT menu_id, menu_name FROM sys_menu WHERE menu_name IN ('Process Definition','Query','Add','Edit','Remove','Expression','Listener')")
english_ids = []
for r in cur.fetchall():
    english_ids.append(r[0])
    print(f"  id={r[0]}  name={r[1]}")

print(f"\n共 {len(english_ids)} 个英文菜单待删除")

if english_ids:
    placeholders = ','.join(['%s'] * len(english_ids))
    cur.execute(f"DELETE FROM sys_role_menu WHERE menu_id IN ({placeholders})", tuple(english_ids))
    deleted_rm = cur.rowcount
    cur.execute(f"DELETE FROM sys_menu WHERE menu_id IN ({placeholders})", tuple(english_ids))
    deleted_menu = cur.rowcount
    conn.commit()
    print(f"\n已删除: {deleted_menu} 个菜单, {deleted_rm} 条角色-菜单关联")

print("\n=== 剩余 flowable 菜单 ===")
cur.execute("SELECT menu_id, menu_name, parent_id, order_num, perms FROM sys_menu WHERE perms LIKE 'flowable:%' ORDER BY parent_id, order_num")
remaining = cur.fetchall()
for r in remaining:
    print(f"  id={r[0]}  name={r[1]}  parent={r[2]}  order={r[3]}  perms={r[4]}")
if not remaining:
    print("  (无)")

conn.close()
print("\nDONE!")
