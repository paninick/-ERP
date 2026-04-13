
import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print('='*80)
print('All tables in database:')
print('='*80)

cursor.execute('SHOW TABLES')
tables = cursor.fetchall()
for table in tables:
    table_name = table[0]
    print(f'  - {table_name}')

print()
print('='*80)
print('Checking tables with "inventory" or "stock" in name:')
print('='*80)

for table in tables:
    table_name = table[0]
    if 'stock' in table_name.lower() or 'inventory' in table_name.lower():
        print(f'  - {table_name}')

cursor.close()
conn.close()


