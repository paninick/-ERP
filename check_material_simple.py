
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking tables:')
print('='*80)

cursor.execute("SHOW TABLES")
all_tables = cursor.fetchall()
print('All tables:')
for table in all_tables:
    table_name = table[0]
    if 'stock' in table_name.lower() or 'material' in table_name.lower():
        print(f'  - {table_name}')

print()
print('='*80)
print('Checking Main Material count:')
print('='*80)
try:
    cursor.execute('SELECT COUNT(*) FROM t_erp_main_material')
    count = cursor.fetchone()[0]
    print(f'Total main materials: {count}')
    if count > 0:
        cursor.execute('SELECT id, name, main_material_no, composition, width, weight, unit FROM t_erp_main_material LIMIT 5')
        print('Sample main materials:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking Auxiliary Material count:')
print('='*80)
try:
    cursor.execute('SELECT COUNT(*) FROM t_erp_auxiliary_material')
    count = cursor.fetchone()[0]
    print(f'Total auxiliary materials: {count}')
    if count > 0:
        cursor.execute('SELECT id, name, auxiliary_material_no, substance, size, unit FROM t_erp_auxiliary_material LIMIT 5')
        print('Sample auxiliary materials:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


