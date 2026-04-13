
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('Checking t_erp_sample_notice_detail...')
try:
    cursor.execute('SELECT * FROM t_erp_sample_notice_detail LIMIT 10')
    rows = cursor.fetchall()
    print(f'Found {len(rows)} records')
    for row in rows:
        print(row)
except Exception as e:
    print(f'Error: {e}')

print('\nChecking t_erp_produce_plan_clothes...')
try:
    cursor.execute('SELECT * FROM t_erp_produce_plan_clothes LIMIT 10')
    rows = cursor.fetchall()
    print(f'Found {len(rows)} records')
    for row in rows:
        print(row)
except Exception as e:
    print(f'Error: {e}')

print('\nChecking t_erp_sales_order_item...')
try:
    cursor.execute('SELECT * FROM t_erp_sales_order_item LIMIT 10')
    rows = cursor.fetchall()
    print(f'Found {len(rows)} records')
    for row in rows:
        print(row)
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


