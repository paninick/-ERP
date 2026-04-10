import pymysql

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

print("=" * 60)
print("查看打样通知表结构")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/2] 查询表结构:")
    cursor.execute("DESCRIBE t_erp_sample_notice")
    columns = cursor.fetchall()
    for col in columns:
        print(f"    {col[0]} - {col[1]} - {col[2]} - {col[3]} - {col[4]} - {col[5]}")
    
    print("\n[2/2] 查询表数据（前5条）:")
    cursor.execute("SELECT * FROM t_erp_sample_notice LIMIT 5")
    rows = cursor.fetchall()
    for row in rows:
        print(f"    {row}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("查询完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
