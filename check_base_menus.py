import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("="*60)
print(" 检查基础管理/业务系统/库存管理 菜单检查")
print("="*60)

# 检查主菜单
main_menus = [
    ("基础管理", None),
    ("业务系统", None), 
    ("库存管理", None),
]

for name, _ in main_menus:
    cur.execute("SELECT menu_id, menu_name, parent_id, menu_type, component FROM sys_menu WHERE menu_name = %s", (name,))
    row = cur.fetchone()
    if row:
        print(f"\n[{name}] menu_id={row[0]} parent={row[2]} type={row[4]} component={row[4]}")
        cur.execute("SELECT menu_id, menu_name, menu_type, perms, component FROM sys_menu WHERE parent_id = %s ORDER BY order_num", (row[0],))
        children = cur.fetchall()
        print(f"  子菜单: {len(children)} 个")
        for child in children:
            print(f"    id={child[0]} name={child[1]} type={child[3]} perms={child[3]} comp={child[4]}")
    else:
        print(f"\n[{name}] NOT FOUND in database!")

print("\n" + "="*60)
print(" 检查所有ERP相关菜单")
print("="*60)
cur.execute("SELECT menu_id, menu_name, parent_id, menu_type, perms, component FROM sys_menu WHERE menu_name LIKE '%库存%' OR menu_name LIKE '%基础%' OR menu_name LIKE '%业务%' OR perms LIKE 'erp:%' ORDER BY parent_id, menu_id")
rows = cur.fetchall()
print(f"总共 {len(rows)} 个相关菜单:")
for r in rows:
    print(f"  id={r[0]} name={r[1]} parent={r[2]} type={r[3]} perms={r[4]} comp={r[5]}")

# 检查genCode表格配置
print("\n" + "="*60)
print(" 检查代码生成表 gen_table")
print("="*60)
cur.execute("SELECT COUNT(*) FROM gen_table")
count = cur.fetchone()[0]
print(f"gen_table 总记录数: {count}")

if count > 0:
    cur.execute("SELECT table_id, table_name, table_comment, className FROM gen_table ORDER BY create_time DESC LIMIT 10")
    print("\n最近10条:")
    for r in cur.fetchall():
        print(f"  id={r[0]} table={r[1]} comment={r[2]} class={r[3]}")

# 所有flowable表在gen_table
cur.execute("SELECT COUNT(*) FROM gen_table WHERE table_name LIKE 'flowable%'")
fcount = cur.fetchone()[0]
print(f"\nflowable表在gen_table: {fcount} 个")

conn.close()
print("\nDONE!")
