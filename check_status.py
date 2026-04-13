import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

checks = [
    ("=== MySQL Connection ===", "SELECT 1 as ok;"),
    ("=== Database ===", "SELECT DATABASE();"),
    ("=== Menu Count ===", "SELECT COUNT(*) as total FROM sys_menu;"),
    ("=== Top Menus ===", "SELECT menu_id, menu_name, parent_id, order_num FROM sys_menu WHERE parent_id=0 ORDER BY order_num;"),
    ("=== User Count ===", "SELECT COUNT(*) as total FROM sys_user;"),
    ("=== Admin User ===", "SELECT user_id, user_name, nick_name, status FROM sys_user WHERE user_id=1;"),
]

for title, sql in checks:
    print(f"\n{title}")
    proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                           stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = proc.communicate(input=sql.encode('utf-8'))
    print(out.decode('utf-8').strip())
