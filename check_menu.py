
import pymysql

db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

conn = pymysql.connect(**db_config)
cursor = conn.cursor()

print('='*80)
print('Checking menu table:')
print('='*80)

try:
    cursor.execute('SELECT menu_id, parent_id, menu_name, path, component, perms, menu_type FROM sys_menu WHERE menu_name LIKE "%库存%" OR path LIKE "%stock%" OR path LIKE "%warehouse%" ORDER BY parent_id, order_num')
    menus = cursor.fetchall()
    print(f'Found {len(menus)} stock/warehouse related menus:')
    print()
    for menu in menus:
        print(f'  ID: {menu[0]}')
        print(f'  Parent ID: {menu[1]}')
        print(f'  Name: {menu[2]}')
        print(f'  Path: {menu[3]}')
        print(f'  Component: {menu[4]}')
        print(f'  Perms: {menu[5]}')
        print(f'  Type: {menu[6]}')
        print()
except Exception as e:
    print(f'Error: {e}')

cursor.close()
conn.close()

