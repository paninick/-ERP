import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 检查所有 ERP 相关菜单 component 路径 ===\n")

cur.execute("""
SELECT menu_id, menu_name, parent_id, component, perms
FROM sys_menu 
WHERE (parent_id IN (7,8,9) OR menu_id IN (7,8,9))
  AND menu_type = 'C'
ORDER BY parent_id, order_num
""")

rows = cur.fetchall()

for r in rows:
    print(f"id={r[0]:<4} name={r[1]:<12} parent={r[2]:<2} component={r[3] if r[3] else '(empty)'}")

print("\n=== 检查所有 ERP 页面文件是否存在 ===\n")

import os

base_dir = r"D:\erp\RuoYi-Vue\ruoyi-ui\src\views"
missing = []
existing = []

for r in rows:
    comp = r[3]
    if not comp:
        continue
    filepath = os.path.join(base_dir, comp + ".vue")
    if os.path.exists(filepath):
        existing.append((r[1], filepath))
    else:
        missing.append((r[1], filepath))

print(f"存在: {len(existing)} 个")
for name, path in existing:
    print(f"  ✅ {name}: {path}")

if missing:
    print(f"\n缺失: {len(missing)} 个")
    for name, path in missing:
        print(f"  ❌ {name}: {path}")
else:
    print("\n✅ 所有文件都存在!")

conn.close()
print("\nDONE!")
