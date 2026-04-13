
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking t_erp_stock_out_item table columns:')
print('='*80)

try:
    cursor.execute('SHOW FULL COLUMNS FROM t_erp_stock_out_item')
    columns = cursor.fetchall()
    for col in columns:
        print(f'{col[0]:20} {col[1]:30} {col[8]}')
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


