
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking stock log table:')
print('='*80)

try:
    cursor.execute('DESCRIBE t_erp_stock_log')
    print('Stock log table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock_log')
    count = cursor.fetchone()[0]
    print(f'Total stock log records: {count}')
    print()
    
    if count > 0:
        cursor.execute('SELECT * FROM t_erp_stock_log LIMIT 10')
        print('Sample stock log data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking backend controllers:')
print('='*80)

cursor.close()
conn.close()


