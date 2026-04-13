import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=" * 60)
print("  流程管理 全面配置修复")
print("=" * 60)

# =============================================
# 1. 修复流程表达式(603)权限 system: -> flowable:
# =============================================
print("\n[1] 修复流程表达式权限...")
cur.execute("UPDATE sys_menu SET perms = 'flowable:expression:list' WHERE menu_id = 603")
fixes_expr = [
    (4305, '查询',   'flowable:expression:query'),
    (4306, '新增',   'flowable:expression:add'),
    (4307, '编辑',   'flowable:expression:edit'),
    (4308, '删除',   'flowable:expression:remove'),
    (4309, '导出',   'flowable:expression:export'),
]
for mid, name, perm in fixes_expr:
    cur.execute("UPDATE sys_menu SET menu_name = %s, perms = %s WHERE menu_id = %s", (name, perm, mid))
    print(f"  按钮 id={mid}: {name} -> {perm}")

# =============================================
# 2. 修复流程监听(605)权限 system: -> flowable:
# =============================================
print("\n[2] 修复流程监听权限...")
cur.execute("UPDATE sys_menu SET perms = 'flowable:listener:list' WHERE menu_id = 605")
fixes_lsn = [
    (4315, '查询',   'flowable:listener:query'),
    (4316, '新增',   'flowable:listener:add'),
    (4317, '编辑',   'flowable:listener:edit'),
    (4318, '删除',   'flowable:listener:remove'),
    (4319, '导出',   'flowable:listener:export'),
]
for mid, name, perm in fixes_lsn:
    cur.execute("UPDATE sys_menu SET menu_name = %s, perms = %s WHERE menu_id = %s", (name, perm, mid))
    print(f"  按钮 id={mid}: {name} -> {perm}")

# =============================================
# 3. 修复表单配置(601)按钮权限格式错误
# =============================================
print("\n[3] 修复表单配置按钮权限...")
cur.execute("UPDATE sys_menu SET perms = 'flowable:form:remove' WHERE menu_id = 3002")
cur.execute("UPDATE sys_menu SET perms = 'flowable:form:query' WHERE menu_id = 3003")
print("  3002: flowable.form:remove -> flowable:form:remove")
print("  3003: flowable.form:query -> flowable:form:query")

# 检查表单是否缺少查询和导出按钮
cur.execute("SELECT menu_id FROM sys_menu WHERE parent_id = 601 AND perms IN ('flowable:form:query','flowable:form:export')")
existing = [r[0] for r in cur.fetchall()]
cur.execute("SELECT COALESCE(MAX(menu_id),2000) FROM sys_menu")
max_id = cur.fetchone()[0]

if 3003 not in existing:
    max_id += 1
    cur.execute("""INSERT INTO sys_menu (menu_id,menu_name,parent_id,order_num,path,perms,component,menu_type,is_frame,is_cache,visible,status,icon,create_by,create_time)
        VALUES (%s,'查询',601,1,'','flowable:form:query','', 'F',1,0,'0','0','#','admin',NOW())""", (max_id,))
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id,menu_id) VALUES (1,%s)", (max_id,))
    print(f"  新增查询按钮 id={max_id}")

if not any(p[0] in existing for p in [(None,)]):
    pass

need_export = True
cur.execute("SELECT menu_id FROM sys_menu WHERE parent_id = 601 AND perms = 'flowable:form:export'")
if not cur.fetchone():
    max_id += 1
    cur.execute("""INSERT INTO sys_menu (menu_id,menu_name,parent_id,order_num,path,perms,component,menu_type,is_frame,is_cache,visible,status,icon,create_by,create_time)
        VALUES (%s,'导出',601,5,'','flowable:form:export','', 'F',1,0,'0','0','#','admin',NOW())""", (max_id,))
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id,menu_id) VALUES (1,%s)", (max_id,))
    print(f"  新增导出按钮 id={max_id}")

# =============================================
# 4. 确保所有新权限都分配给admin角色
# =============================================
print("\n[4] 分配所有权限给admin角色...")
all_flowable_ids = [600, 601, 603, 605]
for mid in all_flowable_ids:
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id,menu_id) VALUES (1,%s)", (mid,))
cur.execute("SELECT menu_id FROM sys_menu WHERE parent_id IN (600,601,603,605)")
for r in cur.fetchall():
    cur.execute("INSERT IGNORE INTO sys_role_menu (role_id,menu_id) VALUES (1,%s)", (r[0],))
print("  所有菜单和按钮权限已分配给admin")

# =============================================
# 5. 插入示例数据到三张空表
# =============================================
print("\n[5] 插入示例数据...")

# flowable_expression
cur.execute("SELECT COUNT(*) FROM flowable_expression")
if cur.fetchone()[0] == 0:
    cur.execute("""
        INSERT INTO flowable_expression (expression_name, expression_type, expression_content, status, remark, create_by, create_time)
        VALUES 
            ('面试全签表达式', 'variable', '${userList}', '0', '系统指定', 'admin', NOW()),
            ('系统指定人员', 'variable', '${who}', '0', '系统指定', 'admin', NOW())
    """)
    print("  flowable_expression: 插入2条示例数据")
else:
    print("  flowable_expression: 已有数据，跳过")

# flowable_form
cur.execute("SELECT COUNT(*) FROM flowable_form")
if cur.fetchone()[0] == 0:
    cur.execute("""
        INSERT INTO flowable_form (form_name, form_key, form_type, status, remark, create_by, create_time)
        VALUES 
            ('测试模型', 'testModel', 'dynamic', '0', NULL, 'admin', NOW()),
            ('数量', 'quantity', 'static', '0', NULL, 'admin', NOW()),
            ('全损', 'allLoss', 'static', '0', NULL, 'admin', NOW()),
            ('测试送底版', 'testBottomPlate', 'static', '0', '111', 'admin', NOW()),
            ('打样通知单', 'sampleNotice', 'static', '0', NULL, 'admin', NOW()),
            ('工艺搭标书', 'craftBookmark', 'static', '0', NULL, 'admin', NOW()),
            ('样衣BOM', 'sampleBOM', 'static', '0', NULL, 'admin', NOW()),
            ('测试模型', 'testModel2', 'static', '0', NULL, 'admin', NOW())
    """)
    print("  flowable_form: 插入8条示例数据")
else:
    print("  flowable_form: 已有数据，跳过")

# flowable_listener
cur.execute("SELECT COUNT(*) FROM flowable_listener")
if cur.fetchone()[0] == 0:
    cur.execute("""
        INSERT INTO flowable_listener (listener_name, listener_type, event_type, listener_value, value_type, implementation, status, remark, create_by, create_time)
        VALUES 
            ('设定用户', '任务监听', 'create', NULL, 'JAVA类', 'com.ruoyi.flowable.listener.FlowTaskListener', '0', NULL, 'admin', NOW()),
            ('流程监听', '执行监听', 'start', NULL, 'JAVA类', 'com.ruoyi.flowable.listener.FlowExecutionListener', '0', NULL, 'admin', NOW()),
            ('自定义监听器', '任务监听', 'create', NULL, 'JAVA类', 'com.ruoyi.eng.listener.CustomerFlowTaskListener', '0', NULL, 'admin', NOW())
    """)
    print("  flowable_listener: 插入3条示例数据")
else:
    print("  flowable_listener: 已有数据，跳过")

conn.commit()

# =============================================
# 验证结果
# =============================================
print("\n" + "=" * 60)
print("  验证结果")
print("=" * 60)

for mid, name in [(600,'流程定义'),(601,'表单配置'),(603,'流程表达式'),(605,'流程监听')]:
    cur.execute("SELECT perms FROM sys_menu WHERE menu_id = %s", (mid,))
    r = cur.fetchone()
    print(f"\n[{name}] 菜单权限: {r[0]}")
    cur.execute("SELECT menu_name, perms FROM sys_menu WHERE parent_id = %s ORDER BY order_num", (mid,))
    for b in cur.fetchall():
        print(f"  └─ {b[0]}: {b[1]}")

print()
for table in ['flowable_expression','flowable_form','flowable_listener']:
    cur.execute(f"SELECT COUNT(*) FROM {table}")
    count = cur.fetchone()[0]
    print(f"{table}: {count} 条数据")

conn.close()
print("\nDONE!")
