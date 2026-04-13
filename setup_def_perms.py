import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

# 1. 给流程定义菜单本身设置权限
cur.execute("UPDATE sys_menu SET perms = 'flowable:definition:list' WHERE menu_id = 600")
print(f"1. 更新menu_600 perms = flowable:definition:list")

# 2. 获取当前最大menu_id
cur.execute("SELECT COALESCE(MAX(menu_id), 2000) FROM sys_menu")
max_id = cur.fetchone()[0]
print(f"2. 当前最大menu_id: {max_id}")

# 3. 创建流程定义的按钮权限
# (menu_id, menu_name, parent_id, order_num, menu_type, path, perms, component)
buttons = [
    (max_id + 1, '查询',   600, 1, 'F', '', 'flowable:definition:query',  ''),
    (max_id + 2, '新增',   600, 2, 'F', '', 'flowable:definition:add',    ''),
    (max_id + 3, '编辑',   600, 3, 'F', '', 'flowable:definition:edit',   ''),
    (max_id + 4, '删除',   600, 4, 'F', '', 'flowable:definition:remove', ''),
    (max_id + 5, '导出',   600, 5, 'F', '', 'flowable:definition:export', ''),
]

for btn in buttons:
    cur.execute("""
        INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, perms, component, menu_type, is_frame, is_cache, visible, status, icon, create_by, create_time)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, 1, 0, '0', '0', '#', 'admin', NOW())
    """, btn)
    print(f"3. 创建按钮: id={btn[0]} name={btn[1]} perms={btn[6]}")

# 4. 所有新权限分配给admin角色(role_id=1)
all_ids = [600] + [b[0] for b in buttons]
for mid in all_ids:
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES (1, %s)", (mid,))
print(f"\n4. 已分配 {len(all_ids)} 个权限给admin角色")

conn.commit()

# 验证
print("\n=== 验证结果 ===")
cur.execute("SELECT menu_id, menu_name, perms, menu_type FROM sys_menu WHERE menu_id = 600 OR parent_id = 600 ORDER BY order_num")
for r in cur.fetchall():
    print(f'  id={r[0]}  name={r[1]}  perms={r[2]}  type={r[3]}')

conn.close()
print("\nDONE!")
