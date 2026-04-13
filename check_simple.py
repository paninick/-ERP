
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('Checking t_erp_sample_notice_detail table:')
print('='*80)
try:
    cursor.execute('SHOW COLUMNS FROM t_erp_sample_notice_detail')
    columns = cursor.fetchall()
    print('Columns:')
    for col in columns:
        print(f'  - {col[0]}')
    print()
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_sample_notice_detail')
    count = cursor.fetchone()[0]
    print(f'Total records: {count}')
    print()
    
    if count > 0:
        cursor.execute('SELECT * FROM t_erp_sample_notice_detail LIMIT 5')
        print('Sample data:')
        for row in cursor.fetchall():
            print(row)
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()


