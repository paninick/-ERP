
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('娴嬭瘯搴撴暟鎹獙璇佹姤鍛?)
print('='*80)
print()

modules = [
    ('t_erp_bom', '鏍疯。BOM'),
    ('t_erp_sales_order', '閿€鍞鍗?),
    ('t_erp_produce_plan', '鐢熶骇璁″垝'),
    ('t_erp_stock_in', '鍏ュ簱鍗?),
    ('t_erp_stock_out', '鍑哄簱鍗?),
]

for table, name in modules:
    print('-'*80)
    print(f'妯″潡: {name} ({table})')
    print('-'*80)
    
    try:
        cursor.execute(f'SELECT COUNT(*) FROM {table}')
        count = cursor.fetchone()[0]
        print(f'鏁版嵁閲? {count} 鏉?)
        
        cursor.execute(f'SHOW COLUMNS FROM {table}')
        columns = cursor.fetchall()
        print(f'瀛楁鏁? {len(columns)} 涓?)
        
        if count > 0:
            cursor.execute(f'SELECT * FROM {table} LIMIT 1')
            sample = cursor.fetchone()
            print('绀轰緥鏁版嵁:')
            for i, col in enumerate(columns):
                if i < len(sample):
                    print(f'  {col[0]}: {sample[i]}')
    except Exception as e:
        print(f'閿欒: {e}')
    print()

print('='*80)
print('楠岃瘉瀹屾垚')
print('='*80)

cursor.close()
conn.close()


