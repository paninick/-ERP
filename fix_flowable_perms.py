import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

cursor.execute("SELECT COALESCE(MAX(menu_id), 2000) FROM sys_menu")
max_id = cursor.fetchone()[0]

# Correct column order:
# 0:menu_id 1:menu_name 2:parent_id 3:order_num 4:path 5:component 6:query 7:route_name 
# 8:is_frame(int) 9:is_cache(int) 10:menu_type 11:visible 12:status 13:perms 14:icon 
# 15:create_by 16:create_time 17:update_by 18:update_time 19:remark
menus_data = [
    (max_id+1, 'Process Definition', 0, 5, 'definition', 'flowable/definition/index', '', '', 1, 0, 'C', '0', '0', 'flowable:definition:list', '', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+2, 'Query', max_id+1, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:query', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+3, 'Add', max_id+1, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:add', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+4, 'Edit', max_id+1, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:edit', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+5, 'Remove', max_id+1, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:definition:remove', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+10, 'Expression', 0, 6, 'expression', 'flowable/expression/index', '', '', 1, 0, 'C', '0', '0', 'flowable:expression:list', '', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+11, 'Query', max_id+10, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:expression:query', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+12, 'Add', max_id+10, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:expression:add', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+13, 'Edit', max_id+10, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:expression:edit', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+14, 'Remove', max_id+10, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:expression:remove', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+20, 'Listener', 0, 7, 'listener', 'flowable/listener/index', '', '', 1, 0, 'C', '0', '0', 'flowable:listener:list', '', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+21, 'Query', max_id+20, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:listener:query', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+22, 'Add', max_id+20, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:listener:add', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+23, 'Edit', max_id+20, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:listener:edit', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
    (max_id+24, 'Remove', max_id+20, 4, '', '', '', '', 1, 0, 'F', '0', '0', 'flowable:listener:remove', '#', 'admin', '2026-01-01 00:00:00', '', None, ''),
]

sql = "INSERT INTO sys_menu VALUES (%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s)"

for m in menus_data:
    try:
        cursor.execute(sql, m)
        print(f"OK: {m[13]}")
    except Exception as e:
        print(f"SKIP: {m[13]} - {e}")

for m in menus_data:
    try:
        cursor.execute("INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES (1, %s)", (m[0],))
    except:
        pass

conn.commit()
cursor.execute("SELECT COUNT(*) FROM sys_role_menu WHERE role_id=1 AND menu_id IN (SELECT menu_id FROM sys_menu WHERE perms LIKE 'flowable:%')")
print(f"\nTotal flowable perms for admin: {cursor.fetchone()[0]}")
conn.close()
print("DONE! Please re-login.")
