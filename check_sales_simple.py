
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking Sales Order Material count:')
print('='*80)

try:
    cursor.execute('SELECT COUNT(*) FROM t_erp_sales_order_material')
    count = cursor.fetchone()[0]
    print(f'Total sales order material records: {count}')
    print()
    
    if count > 0:
        cursor.execute('DESCRIBE t_erp_sales_order_material')
        print('Columns:')
        for row in cursor.fetchall():
            print(row)
        print()
        
        cursor.execute('SELECT * FROM t_erp_sales_order_material LIMIT 10')
        print('Sample data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking Sales Order Item count:')
print('='*80)

try:
    cursor.execute('SELECT COUNT(*) FROM t_erp_sales_order_item')
    count = cursor.fetchone()[0]
    print(f'Total sales order item records: {count}')
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


