import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

print("=" * 60)
print("数据库数据完整性检查")
print("=" * 60)

# 检查所有ERP相关表
cursor.execute("SHOW TABLES LIKE 't_%'")
tables = cursor.fetchall()
print(f"\n1. ERP业务表数量: {len(tables)}")
for t in tables:
    print(f"   - {t[0]}")

# 检查关键系统表
print("\n2. 关键系统表数据量:")
key_tables = ['sys_menu', 'sys_dict_data', 'sys_user', 'sys_role', 'sys_dept']
for t in key_tables:
    try:
        cursor.execute(f"SELECT COUNT(*) FROM {t}")
        count = cursor.fetchone()[0]
        print(f"   {t}: {count} 条")
    except Exception as e:
        print(f"   {t}: 错误 - {e}")

# 检查ERP菜单
print("\n3. ERP菜单统计:")
try:
    cursor.execute("SELECT COUNT(*) FROM sys_menu WHERE menu_name LIKE '%原料%' OR menu_name LIKE '%流程%' OR menu_name LIKE '%生产%' OR menu_name LIKE '%销售%' OR menu_name LIKE '%采购%'")
    count = cursor.fetchone()[0]
    print(f"   ERP功能菜单: {count} 条")
except Exception as e:
    print(f"   错误: {e}")

# 检查flowable表
print("\n4. Flowable表:")
try:
    cursor.execute("SHOW TABLES LIKE 'flw_%'")
    flowable_tables = cursor.fetchall()
    print(f"   Flowable表数量: {len(flowable_tables)}")
    for t in flowable_tables[:10]:
        print(f"   - {t[0]}")
    if len(flowable_tables) > 10:
        print(f"   ... 还有 {len(flowable_tables)-10} 张表")
except Exception as e:
    print(f"   错误: {e}")

conn.close()
