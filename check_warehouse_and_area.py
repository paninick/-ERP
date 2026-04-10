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
print("查看仓库和库区表结构及数据")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/4] 查询仓库表结构:")
    cursor.execute("DESCRIBE t_erp_warehouse")
    columns = cursor.fetchall()
    for col in columns:
        print(f"    {col[0]} - {col[1]}")
    
    print("\n[2/4] 查询仓库表示例数据:")
    cursor.execute("SELECT id, code, name, warehouse_area_id FROM t_erp_warehouse LIMIT 10")
    warehouses = cursor.fetchall()
    for w in warehouses:
        print(f"    ID:{w[0]}, 编码:{w[1]}, 名称:{w[2]}, 仓库区域ID:{w[3]}")
    
    print("\n[3/4] 查询仓库区域表结构:")
    cursor.execute("DESCRIBE t_erp_warehouse_area")
    columns = cursor.fetchall()
    for col in columns:
        print(f"    {col[0]} - {col[1]}")
    
    print("\n[4/4] 查询仓库区域表示例数据:")
    cursor.execute("SELECT id, code, name FROM t_erp_warehouse_area LIMIT 10")
    areas = cursor.fetchall()
    for a in areas:
        print(f"    ID:{a[0]}, 编码:{a[1]}, 名称:{a[2]}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("查询完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
