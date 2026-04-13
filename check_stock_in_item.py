
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking stock in item table:')
print('='*80)

try:
    cursor.execute('DESCRIBE t_erp_stock_in_item')
    print('Stock in item table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock_in_item')
    count = cursor.fetchone()[0]
    print(f'Total stock in item records: {count}')
    print()
    
    if count > 0:
        cursor.execute('SELECT * FROM t_erp_stock_in_item LIMIT 20')
        print('Sample stock in item data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking stock out item table:')
print('='*80)

try:
    cursor.execute('DESCRIBE t_erp_stock_out_item')
    print('Stock out item table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock_out_item')
    count = cursor.fetchone()[0]
    print(f'Total stock out item records: {count}')
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


