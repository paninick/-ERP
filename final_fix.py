import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 全面修复 ===\n")

# 1. 表单配置(601): 修正组件路径
cur.execute("UPDATE sys_menu SET component = 'flowable/form/list' WHERE menu_id = 601")
print(f"[1] 表单配置(601) component -> flowable/form/list")

# 2. 流程表达式(603): 修正权限
cur.execute("UPDATE sys_menu SET perms = 'flowable:expression:list' WHERE menu_id = 603")
print(f"[2] 流程表达式(603) perms -> flowable:expression:list")

# 3. 流程监听(605): 修正权限
cur.execute("UPDATE sys_menu SET perms = 'flowable:listener:list' WHERE menu_id = 605")
print(f"[3] 流程监听(605) perms -> flowable:listener:list")

# 4. 按钮权限也再次确认修复
fixes = [
    # 表达式按钮
    (4305, 'flowable:expression:query'),
    (4306, 'flowable:expression:add'),
    (4307, 'flowable:expression:edit'),
    (4308, 'flowable:expression:remove'),
    (4309, 'flowable:expression:export'),
    # 监听器按钮
    (4315, 'flowable:listener:query'),
    (4316, 'flowable:listener:add'),
    (4317, 'flowable:listener:edit'),
    (4318, 'flowable:listener:remove'),
    (4319, 'flowable:listener:export'),
]
for mid, perm in fixes:
    cur.execute("UPDATE sys_menu SET perms = %s WHERE menu_id = %s", (perm, mid))

print("[4] 所有按钮权限已确认")

conn.commit()

# 验证
print("\n=== 验证结果 ===")
for mid in [600, 601, 603, 605]:
    cur.execute("SELECT menu_id, menu_name, component, perms FROM sys_menu WHERE menu_id = %s", (mid,))
    r = cur.fetchone()
    print(f"  id={r[0]} {r[1]} comp={r[2]} perms={r[3]}")

conn.close()
print("\nDONE!")
