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
print("查看供应商表数据")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/3] 查询供应商表结构:")
    cursor.execute("DESCRIBE t_erp_supplier")
    columns = cursor.fetchall()
    for col in columns:
        print(f"    {col[0]} - {col[1]}")
    
    print("\n[2/3] 查询供应商表数据:")
    cursor.execute("SELECT id, supplier_type, supplier_no, supplier_name, supplier_brief FROM t_erp_supplier ORDER BY id")
    suppliers = cursor.fetchall()
    print(f"    供应商总数: {len(suppliers)}")
    for s in suppliers:
        print(f"    ID:{s[0]}, 类型:{s[1]}, 编号:{s[2]}, 名称:{s[3]}, 简称:{s[4]}")
    
    print("\n[3/3] 查询辅料表数据示例:")
    cursor.execute("SELECT id, auxiliary_material_no, name, supplier_id FROM t_erp_auxiliary_material LIMIT 10")
    materials = cursor.fetchall()
    print(f"    辅料示例（前10条）:")
    for m in materials:
        print(f"    ID:{m[0]}, 编号:{m[1]}, 品名:{m[2]}, 供应商ID:{m[3]}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("查询完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
