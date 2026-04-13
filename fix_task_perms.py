import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 任务管理权限修复 ===\n")

# 任务管理主菜单 id=6
# 更新三个子菜单的权限
fixes = [
    (700, '已发任务',   'flowable:task:myProcess:list'),
    (701, '待办任务',   'flowable:task:todo:list'),
    (702, '已办任务',   'flowable:task:finished:list'),
]

for mid, name, perm in fixes:
    cur.execute("UPDATE sys_menu SET perms = %s WHERE menu_id = %s", (perm, mid))
    print(f"[FIXED] id={mid} name={name} perms={perm}")

# 添加按钮权限
print("\n=== 添加按钮权限 ===")
cur.execute("SELECT COALESCE(MAX(menu_id), 0) FROM sys_menu")
max_id = cur.fetchone()[0]
print(f"当前最大 menu_id: {max_id}")

buttons = [
    (max_id + 1, '查询',   700, 1, 'F', 'flowable:task:myProcess:query'),
    (max_id + 2, '导出',   700, 2, 'F', 'flowable:task:myProcess:export'),
    (max_id + 3, '查询',   701, 1, 'F', 'flowable:task:todo:query'),
    (max_id + 4, '签收',   701, 2, 'F', 'flowable:task:todo:claim'),
    (max_id + 5, '完成',   701, 3, 'F', 'flowable:task:todo:complete'),
    (max_id + 6, '驳回',   701, 4, 'F', 'flowable:task:todo:back'),
    (max_id + 7, '导出',   701, 5, 'F', 'flowable:task:todo:export'),
    (max_id + 8, '查询',   702, 1, 'F', 'flowable:task:finished:query'),
    (max_id + 9, '导出',   702, 2, 'F', 'flowable:task:finished:export'),
]

for btn in buttons:
    cur.execute("""
        INSERT IGNORE INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, perms, component, menu_type, is_frame, is_cache, visible, status, icon, create_by, create_time)
        VALUES (%s, %s, %s, %s, '', %s, '', %s, 1, 0, '0', '0', '#', 'admin', NOW())
    """, btn)
    print(f"[ADDED] id={btn[0]} name={btn[1]} parent={btn[2]} perms={btn[5]}")

# 分配给admin角色
all_ids = [700, 701, 702] + [b[0] for b in buttons]
for mid in all_ids:
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES (1, %s)", (mid,))

conn.commit()

print("\n=== 验证结果 ===")
cur.execute("SELECT menu_id, menu_name, perms FROM sys_menu WHERE menu_id IN (700,701,702)")
for r in cur.fetchall():
    print(f"  id={r[0]} name={r[1]} perms={r[2]}")

conn.close()
print("\nDONE!")
