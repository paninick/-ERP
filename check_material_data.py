
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking Main Material data:')
print('='*80)
try:
    cursor.execute('DESCRIBE t_erp_main_material')
    print('Main Material table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_main_material')
    count = cursor.fetchone()[0]
    print(f'Total main materials: {count}')
    print()
    
    if count &gt; 0:
        cursor.execute('SELECT id, name, main_material_no, composition, width, weight, unit FROM t_erp_main_material LIMIT 5')
        print('Sample main materials:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error checking main material: {e}')

print()
print('='*80)
print('Checking Auxiliary Material data:')
print('='*80)
try:
    cursor.execute('DESCRIBE t_erp_auxiliary_material')
    print('Auxiliary Material table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_auxiliary_material')
    count = cursor.fetchone()[0]
    print(f'Total auxiliary materials: {count}')
    print()
    
    if count &gt; 0:
        cursor.execute('SELECT id, name, auxiliary_material_no, substance, size, unit FROM t_erp_auxiliary_material LIMIT 5')
        print('Sample auxiliary materials:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error checking auxiliary material: {e}')

print()
print('='*80)
print('Checking all tables:')
print('='*80)
try:
    cursor.execute("SHOW TABLES LIKE '%stock%' OR SHOW TABLES LIKE '%material%' OR SHOW TABLES LIKE '%inventory%'")
    tables = cursor.fetchall()
    print('Related tables:')
    for table in tables:
        print(table[0])
except Exception as e:
    print(f'Error checking tables: {e}')
    # 鍙︿竴绉嶆柟寮忔煡璇㈣〃
    cursor.execute("SHOW TABLES")
    all_tables = cursor.fetchall()
    print('\nAll tables:')
    for table in all_tables:
        if 'stock' in table[0].lower() or 'material' in table[0].lower():
            print(f'  - {table[0]}')

cursor.close()
conn.close()


