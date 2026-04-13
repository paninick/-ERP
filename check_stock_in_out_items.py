
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking t_erp_stock_in_item table:')
print('='*80)
try:
    cursor.execute('DESCRIBE t_erp_stock_in_item')
    columns = cursor.fetchall()
    print('Columns:')
    for col in columns:
        print(f'  - {col[0]}')
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock_in_item')
    count = cursor.fetchone()[0]
    print(f'Total records: {count}')
    print()
    
    if count > 0:
        cursor.execute('SELECT * FROM t_erp_stock_in_item LIMIT 10')
        print('Sample data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking t_erp_stock_out_item table:')
print('='*80)
try:
    cursor.execute('DESCRIBE t_erp_stock_out_item')
    columns = cursor.fetchall()
    print('Columns:')
    for col in columns:
        print(f'  - {col[0]}')
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock_out_item')
    count = cursor.fetchone()[0]
    print(f'Total records: {count}')
    print()
    
    if count > 0:
        cursor.execute('SELECT * FROM t_erp_stock_out_item LIMIT 10')
        print('Sample data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking t_erp_stock_log table:')
print('='*80)
try:
    cursor.execute('DESCRIBE t_erp_stock_log')
    columns = cursor.fetchall()
    print('Columns:')
    for col in columns:
        print(f'  - {col[0]}')
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock_log')
    count = cursor.fetchone()[0]
    print(f'Total records: {count}')
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


