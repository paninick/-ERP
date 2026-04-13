
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('Checking table counts...')
tables = [
    't_erp_produce_plan_clothes',
    't_erp_produce_plan_material',
    't_erp_sales_order_item',
    't_erp_sales_order_material',
    't_erp_sample_notice_detail',
    't_erp_sample_notice_material'
]

for table in tables:
    try:
        cursor.execute(f'SELECT COUNT(*) FROM {table}')
        count = cursor.fetchone()[0]
        print(f'{table}: {count} records')
    except Exception as e:
        print(f'{table}: Error - {e}')

cursor.close()
conn.close()


