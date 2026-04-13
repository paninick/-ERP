import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cur = conn.cursor()

print("=== 修复表结构 ===")

# 1. flowable_expression: 添加 expression_key 列
try:
    cur.execute("ALTER TABLE flowable_expression ADD COLUMN expression_key VARCHAR(200) DEFAULT NULL COMMENT '表达式KEY' AFTER expression_name")
    print("  flowable_expression: 添加 expression_key 列")
except Exception as e:
    if "Duplicate column" in str(e):
        print("  flowable_expression: expression_key 已存在")
    else:
        print(f"  flowable_expression 错误: {e}")

# 2. flowable_listener: 添加 value_type 和 implementation 列
try:
    cur.execute("ALTER TABLE flowable_listener ADD COLUMN value_type VARCHAR(50) DEFAULT NULL COMMENT '值类型' AFTER listener_value")
    print("  flowable_listener: 添加 value_type 列")
except Exception as e:
    if "Duplicate column" in str(e):
        print("  flowable_listener: value_type 已存在")
    else:
        print(f"  flowable_listener value_type 错误: {e}")

try:
    cur.execute("ALTER TABLE flowable_listener ADD COLUMN implementation VARCHAR(500) DEFAULT NULL COMMENT '实现类' AFTER value_type")
    print("  flowable_listener: 添加 implementation 列")
except Exception as e:
    if "Duplicate column" in str(e):
        print("  flowable_listener: implementation 已存在")
    else:
        print(f"  flowable_listener implementation 错误: {e}")

# 3. 插入示例数据（使用正确的列名）
print("\n=== 插入示例数据 ===")

cur.execute("SELECT COUNT(*) FROM flowable_expression")
if cur.fetchone()[0] == 0:
    cur.execute("""
        INSERT INTO flowable_expression (expression_name, expression_key, expression_type, expression_text, status, remark, create_by, create_time)
        VALUES 
            ('面试全签表达式', 'userList', 'variable', '${userList}', '0', '系统指定', 'admin', NOW()),
            ('系统指定人员', 'who', 'variable', '${who}', '0', '系统指定', 'admin', NOW())
    """)
    print("  flowable_expression: 插入2条")
else:
    print("  flowable_expression: 已有数据，跳过")

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
            ('样衣BOM', 'sampleBOM', 'static', '0', NULL, 'admin', NOW())
    """)
    print("  flowable_form: 插入7条")
else:
    print("  flowable_form: 已有数据，跳过")

cur.execute("SELECT COUNT(*) FROM flowable_listener")
if cur.fetchone()[0] == 0:
    cur.execute("""
        INSERT INTO flowable_listener (listener_name, listener_type, event_type, value_type, implementation, status, create_by, create_time)
        VALUES 
            ('设定用户', '任务监听', 'create', 'JAVA类', 'com.ruoyi.flowable.listener.FlowTaskListener', '0', 'admin', NOW()),
            ('流程监听', '执行监听', 'start', 'JAVA类', 'com.ruoyi.flowable.listener.FlowExecutionListener', '0', 'admin', NOW()),
            ('自定义监听器', '任务监听', 'create', 'JAVA类', 'com.ruoyi.eng.listener.CustomerFlowTaskListener', '0', 'admin', NOW())
    """)
    print("  flowable_listener: 插入3条")
else:
    print("  flowable_listener: 已有数据，跳过")

conn.commit()

print("\n=== 验证 ===")
for t in ['flowable_expression','flowable_form','flowable_listener']:
    cur.execute(f'SELECT COUNT(*) FROM `{t}`')
    print(f'  {t}: {cur.fetchone()[0]} 条')

conn.close()
print("\nDONE!")
