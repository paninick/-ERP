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
print("查看仓库区域、仓库、仓位关系")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/3] 查询所有仓库区域:")
    cursor.execute("SELECT id, code, name FROM t_erp_warehouse_area ORDER BY id")
    areas = cursor.fetchall()
    for a in areas:
        print(f"  ID:{a[0]}, 编码:{a[1]}, 名称:{a[2]}")
    
    print("\n[2/3] 查询所有仓库:")
    cursor.execute("SELECT id, code, name, warehouse_area_id FROM t_erp_warehouse ORDER BY id")
    warehouses = cursor.fetchall()
    for w in warehouses:
        print(f"  ID:{w[0]}, 编码:{w[1]}, 名称:{w[2]}, 仓库区域ID:{w[3]}")
    
    print("\n[3/3] 查询所有仓位:")
    cursor.execute("SELECT id, code, name, warehouse_area_id, warehouse_id FROM t_erp_warehouse_location ORDER BY id")
    locations = cursor.fetchall()
    for loc in locations:
        print(f"  ID:{loc[0]}, 编码:{loc[1]}, 名称:{loc[2]}, 仓库区域ID:{loc[3]}, 仓库ID:{loc[4]}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("查询完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
