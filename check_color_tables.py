
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

tables_to_check = [
    't_erp_sales_order_material',
    't_erp_sales_order_item',
    't_erp_sample_notice_detail',
    't_erp_produce_plan_material',
    't_erp_produce_plan_clothes',
    't_erp_sample_notice_material'
]

for table in tables_to_check:
    print('='*80)
    print(f'Checking table: {table}')
    print('='*80)
    
    try:
        cursor.execute(f'SHOW COLUMNS FROM {table}')
        columns = cursor.fetchall()
        print('Columns:')
        for col in columns:
            print(f'  - {col[0]} ({col[1]})')
        print()
        
        cursor.execute(f'SELECT COUNT(*) FROM {table}')
        count = cursor.fetchone()[0]
        print(f'Total records: {count}')
        print()
        
        if count &gt; 0:
            cursor.execute(f'SELECT * FROM {table} LIMIT 5')
            print('Sample data:')
            for row in cursor.fetchall():
                print(row)
    except Exception as e:
        print(f'Error: {e}')
    print()

cursor.close()
conn.close()


