
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking Sales Order Material table:')
print('='*80)

try:
    cursor.execute('DESCRIBE t_erp_sales_order_material')
    print('Sales Order Material table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_sales_order_material')
    count = cursor.fetchone()[0]
    print(f'Total sales order material records: {count}')
    print()
    
    if count &gt; 0:
        cursor.execute('SELECT * FROM t_erp_sales_order_material LIMIT 20')
        print('Sample sales order material data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

print()
print('='*80)
print('Checking Sales Order Item table:')
print('='*80)

try:
    cursor.execute('DESCRIBE t_erp_sales_order_item')
    print('Sales Order Item table structure:')
    for row in cursor.fetchall():
        print(row)
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_sales_order_item')
    count = cursor.fetchone()[0]
    print(f'Total sales order item records: {count}')
    print()
    
    if count &gt; 0:
        cursor.execute('SELECT * FROM t_erp_sales_order_item LIMIT 20')
        print('Sample sales order item data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


