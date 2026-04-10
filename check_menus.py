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
print("查看菜单配置")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/2] 查询库区管理相关的菜单:")
    cursor.execute("""
        SELECT menu_id, parent_id, menu_name, path, component, menu_type, visible, status 
        FROM sys_menu 
        WHERE menu_name LIKE '%库区%' OR menu_name LIKE '%数据导入%'
        ORDER BY parent_id, order_num
    """)
    menus = cursor.fetchall()
    print(f"  找到 {len(menus)} 个菜单:")
    for m in menus:
        print(f"    ID:{m[0]}, 父ID:{m[1]}, 名称:{m[2]}, 路径:{m[3]}, 组件:{m[4]}, 类型:{m[5]}, 可见:{m[6]}, 状态:{m[7]}")
    
    print("\n[2/2] 查询库区管理的子菜单:")
    cursor.execute("""
        SELECT menu_id, parent_id, menu_name, path, component, menu_type, visible, status 
        FROM sys_menu 
        WHERE parent_id IN (
            SELECT menu_id FROM sys_menu WHERE menu_name LIKE '%库区管理%'
        )
        ORDER BY order_num
    """)
    sub_menus = cursor.fetchall()
    print(f"  找到 {len(sub_menus)} 个子菜单:")
    for m in sub_menus:
        print(f"    ID:{m[0]}, 父ID:{m[1]}, 名称:{m[2]}, 路径:{m[3]}, 组件:{m[4]}, 类型:{m[5]}, 可见:{m[6]}, 状态:{m[7]}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("查询完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
