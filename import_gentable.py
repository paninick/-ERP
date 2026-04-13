import mysql.connector
import zipfile
import io
import sys

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

zip_path = r"C:\Users\91306\Downloads\genCode (5).zip"

print(f"Opening: {zip_path}")

if not zipfile.is_zipfile(zip_path):
    print("Not a valid zip file")
    exit(1)

z = zipfile.ZipFile(zip_path, 'r')

# Find SQL files
sql_files = [f for f in z.namelist() if f.endswith('.sql')]
print(f"Found SQL files: {sql_files}")

if not sql_files:
    print("No SQL files found in zip")
    z.close()
    exit(1)

# Process each SQL file
for sql_file in sql_files:
    print(f"\n--- Processing {sql_file} ---")
    with z.open(sql_file) as f:
        sql_content = f.read().decode('utf-8')
    
    # Split by ; to get statements
    statements = [s.strip() for s in sql_content.split(';') if s.strip()]
    print(f"Found {len(statements)} SQL statements")
    
    success = 0
    failed = 0
    for stmt in statements:
        if not stmt.strip():
            continue
        # Skip comments
        if stmt.strip().startswith('--') or stmt.strip().startswith('/*'):
            continue
        try:
            cur.execute(stmt)
            success += 1
        except Exception as e:
            if "Duplicate entry" in str(e) or "already exists" in str(e):
                print(f"  [SKIP] Already exists: {stmt[:60]}...")
                success += 1
            else:
                print(f"  [ERROR] {e}")
                print(f"      Statement: {stmt[:100]}")
                failed += 1
    
    print(f"Done: {success} success, {failed} failed")

conn.commit()

# Verify
print("\n=== Verification ===")
cur.execute("SELECT COUNT(*) FROM gen_table")
print(f"gen_table: {cur.fetchone()[0]} rows")
cur.execute("SELECT COUNT(*) FROM gen_table_column")
print(f"gen_table_column: {cur.fetchone()[0]} rows")

z.close()
conn.close()
print("\nDONE!")
