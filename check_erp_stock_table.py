
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking t_erp_stock table:')
print('='*80)

try:
    cursor.execute('DESCRIBE t_erp_stock')
    columns = cursor.fetchall()
    print('Columns:')
    for col in columns:
        print(f'  - {col[0]} ({col[1]})')
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_stock')
    count = cursor.fetchone()[0]
    print(f'Total records: {count}')
    print()
    
    if count > 0:
        cursor.execute('SELECT * FROM t_erp_stock LIMIT 20')
        print('Data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


