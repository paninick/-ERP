
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking Main Material full structure:')
print('='*80)

try:
    cursor.execute('SHOW FULL COLUMNS FROM t_erp_main_material')
    print('Main Material full columns:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    print('='*80)
    print('Checking Main Material sample data with all fields:')
    print('='*80)
    cursor.execute('SELECT * FROM t_erp_main_material LIMIT 5')
    print('Sample main material data:')
    for row in cursor.fetchall():
        print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking Auxiliary Material full structure:')
print('='*80)

try:
    cursor.execute('SHOW FULL COLUMNS FROM t_erp_auxiliary_material')
    print('Auxiliary Material full columns:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    print('='*80)
    print('Checking Auxiliary Material sample data with all fields:')
    print('='*80)
    cursor.execute('SELECT * FROM t_erp_auxiliary_material LIMIT 5')
    print('Sample auxiliary material data:')
    for row in cursor.fetchall():
        print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking all tables for color or stock related fields:')
print('='*80)

try:
    cursor.execute("SHOW TABLES")
    all_tables = cursor.fetchall()
    for table in all_tables:
        table_name = table[0]
        try:
            cursor.execute(f"SHOW COLUMNS FROM {table_name}")
            columns = cursor.fetchall()
            has_color = False
            has_stock = False
            has_quantity = False
            for col in columns:
                col_name = col[0].lower()
                if 'color' in col_name or 'colour' in col_name:
                    has_color = True
                if 'stock' in col_name:
                    has_stock = True
                if 'quantity' in col_name or 'count' in col_name or 'qty' in col_name:
                    has_quantity = True
            if has_color or has_stock:
                print(f'Table: {table_name}')
                if has_color:
                    print('  - Has color field')
                if has_stock:
                    print('  - Has stock field')
                if has_quantity:
                    print('  - Has quantity/count field')
        except:
            pass
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


