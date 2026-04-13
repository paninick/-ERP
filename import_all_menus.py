import mysql.connector
import zipfile
import io

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

zip_path = r"C:\Users\91306\Downloads\genCode (5).zip"
z = zipfile.ZipFile(zip_path, 'r')

print("=== Importing all menu SQL from ZIP ===")

sql_files = [f for f in z.namelist() if f.endswith('.sql')]
print(f"Found {len(sql_files)} SQL files")

success = 0
failed = 0

for sql_file in sorted(sql_files):
    print(f"\n[{success+failed+1}/{len(sql_files)}] {sql_file}")
    with z.open(sql_file) as f:
        lines = f.read().decode('utf-8')
    
    # 提取 INSERT 语句
    for line in lines.split('\n'):
        line = line.strip()
        if not line or line.startswith('--'):
            continue
        if line:
            try:
                cur.execute(line)
                if cur.rowcount > 0:
                    success += 1
                    print(f"  OK: {line[:80]}...")
            except Exception as e:
                if "Duplicate entry" in str(e) or "already exists" in str(e):
                    print(f"  [SKIP] Already exists: {line[:60]}")
                    success += 1
                else:
                    print(f"  [ERROR] {e}")
                    failed += 1

conn.commit()

print(f"\n=== Summary ===")
print(f"  Success: {success}")
print(f"  Failed: {failed}")

# Check menu counts
print("\n=== Verify ERP menus in database ===")
cur.execute("SELECT COUNT(*) FROM sys_menu WHERE menu_name IN ('基础管理', '业务系统', '库存管理')")
print(f"主菜单: 基础管理/业务系统/库存管理 = {cur.fetchone()[0]} / 3")

cur.execute("SELECT COUNT(*) FROM sys_menu WHERE parent_id IN (7,8,9)")
print(f"子菜单总数: {cur.fetchone()[0]}")

# Check data import menu
print("\n=== Check if data import menu exists ===")
cur.execute("SELECT menu_id, menu_name, parent_id FROM sys_menu WHERE menu_name LIKE '%导入%' OR menu_name LIKE '%数据%'")
rows = cur.fetchall()
for r in rows:
    print(f"  id={r[0]} name={r[1]} parent={r[2]}")

conn.close()
z.close()
print("\nDONE!")
