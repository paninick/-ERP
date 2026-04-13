import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 添加数据导入功能菜单 ===\n")

# 找到基础管理下最大order_num
cur.execute("SELECT COALESCE(MAX(order_num), 0) FROM sys_menu WHERE parent_id = 7")
max_order = cur.fetchone()[0]
new_order = max_order + 1

print(f"当前最大order_num: {max_order}, 新菜单order_num: {new_order}")

# 获取当前最大menu_id
cur.execute("SELECT COALESCE(MAX(menu_id), 0) FROM sys_menu")
max_id = cur.fetchone()[0]
new_id = max_id + 1

print(f"当前最大menu_id: {max_id}, 新菜单menu_id: {new_id}")

# 插入数据导入菜单
sql = """
INSERT INTO sys_menu 
(menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache, visible, status, menu_type, perms, icon, create_by, create_time)
VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, NOW())
"""

values = (
    new_id, 
    '数据导入',           # menu_name
    7,                   # parent_id (基础管理)
    new_order,            # order_num
    'erp/dataimport',    # path
    'erp/dataimport/index', # component
    1,                   # is_frame
    0,                   # is_cache  
    '0',                 # visible
    '0',                 # status
    'C',                 # menu_type (C=目录菜单)
    'erp:dataimport:list', # perms
    '#',                 # icon
    'admin'              # create_by
)

try:
    cur.execute(sql, values)
    conn.commit()
    print(f"\n✅ 已插入菜单: menu_id={new_id} name=数据导入 parent=7(基础管理)")
    
    # 分配权限给admin角色
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id, menu_id) VALUES (1, %s)", (new_id,))
    conn.commit()
    print(f"✅ 已分配权限给admin角色")
    
    print("\n=== 验证 ===")
    cur.execute("SELECT menu_id, menu_name, parent_id, perms FROM sys_menu WHERE menu_id = %s", (new_id,))
    row = cur.fetchone()
    print(f"  id={row[0]} name={row[1]} parent={row[2]} perms={row[3]}")
    
except Exception as e:
    print(f"\n❌ 错误: {e}")

conn.close()
print("\nDONE!")
