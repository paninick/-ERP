import mysql.connector
import zipfile

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

zip_path = r"C:\Users\91306\Downloads\genCode (5).zip"
z = zipfile.ZipFile(zip_path, 'r')

print("=== Importing menu SQL from ZIP ===")

sql_files = [f for f in z.namelist() if f.endswith('.sql')]
print(f"Found {len(sql_files)} SQL files")

total_inserted = 0
total_skipped = 0

for sql_file in sorted(sql_files):
    print(f"\nProcessing {sql_file}...")
    with z.open(sql_file) as f:
        full_sql = f.read().decode('utf-8')
    
    # Find the INSERT statement
    if 'INSERT INTO' in full_sql:
        # Extract just the INSERT line
        lines = full_sql.strip().split('\n')
        insert_line = None
        for line in lines:
            if 'INSERT INTO' in line and 'VALUES' in line:
                insert_line = line.strip()
                break
        
        if insert_line:
            try:
                cur.execute(insert_line)
                if cur.rowcount > 0:
                    total_inserted += 1
                    print(f"  ✓ Inserted: {cur.rowcount} row")
                else:
                    total_skipped += 1
                    print(f"  - Skipped (0 rows)")
            except mysql.connector.Error as e:
                if "Duplicate entry" in str(e):
                    total_skipped += 1
                    print(f"  ⚠ Skipped (duplicate)")
                else:
                    print(f"  ✗ ERROR: {e}")
                    print(f"    SQL: {insert_line[:100]}...")
    else:
        print(f"  ! No INSERT found")

conn.commit()

print(f"\n=== Final Summary ===")
print(f"  Inserted: {total_inserted}")
print(f"  Skipped: {total_skipped}")

print("\n=== Verify menus ===")
cur.execute("SELECT menu_id, menu_name, parent_id FROM sys_menu WHERE menu_id IN (7,8,9)")
for row in cur.fetchall():
    print(f"  id={row[0]} name={row[1]} parent={row[2]}")

cur.execute("SELECT COUNT(*) FROM sys_menu WHERE parent_id = 7")
print(f"  基础管理子菜单: {cur.fetchone()[0]}")
cur.execute("SELECT COUNT(*) FROM sys_menu WHERE parent_id = 8")
print(f"  业务系统子菜单: {cur.fetchone()[0]}")
cur.execute("SELECT COUNT(*) FROM sys_menu WHERE parent_id = 9")
print(f"  库存管理子菜单: {cur.fetchone()[0]}")

conn.close()
z.close()
print("\nDONE!")
