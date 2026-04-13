
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('StockIn table structure:')
print('='*80)
cursor.execute('DESCRIBE t_erp_stock_in')
for row in cursor.fetchall():
    print(row)
print()

print('='*80)
print('Sample records from t_erp_stock_in:')
print('='*80)
cursor.execute('SELECT id, sn, confirm_status FROM t_erp_stock_in LIMIT 10')
for row in cursor.fetchall():
    print(row)
print()

print('='*80)
print('Confirm status values:')
print('='*80)
cursor.execute('SELECT confirm_status, COUNT(*) FROM t_erp_stock_in GROUP BY confirm_status')
for row in cursor.fetchall():
    print(row)

cursor.close()
conn.close()


