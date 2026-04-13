
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('StockIn table data verification:')
print('='*80)
print()

print('='*80)
print('Total records:')
print('='*80)
cursor.execute('SELECT COUNT(*) FROM t_erp_stock_in')
print(f'Total: {cursor.fetchone()[0]}')
print()

print('='*80)
print('Confirm status counts:')
print('='*80)
cursor.execute('SELECT confirm_status, COUNT(*) FROM t_erp_stock_in GROUP BY confirm_status')
for row in cursor.fetchall():
    status_label = '寰呯‘璁? if row[0] == '0' else '宸茬‘璁?
    print(f'{status_label} ({row[0]}): {row[1]} records')
print()

print('='*80)
print('Sample records with confirm status:')
print('='*80)
cursor.execute('SELECT id, sn, confirm_status FROM t_erp_stock_in ORDER BY id LIMIT 20')
for row in cursor.fetchall():
    status_label = '寰呯‘璁? if row[2] == '0' else '宸茬‘璁?
    print(f'ID: {row[0]}, SN: {row[1]}, Status: {row[2]} ({status_label})')

cursor.close()
conn.close()


