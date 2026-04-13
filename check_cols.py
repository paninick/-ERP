import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

# Get actual column order and sample data
cursor.execute("SELECT * FROM sys_menu WHERE menu_type = 'C' LIMIT 1")
row = cursor.fetchone()
cursor.execute("DESCRIBE sys_menu")
cols = [r[0] for r in cursor.fetchall()]

print("Columns:")
for i, c in enumerate(cols):
    print(f"  {i}: {c} = {repr(row[i])}")

print("\n--- Button sample ---")
cursor.execute("SELECT * FROM sys_menu WHERE menu_type = 'F' LIMIT 1")
row2 = cursor.fetchone()
for i, c in enumerate(cols):
    print(f"  {i}: {c} = {repr(row2[i])}")
conn.close()
