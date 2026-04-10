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
print("隐藏数据导入菜单项")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n查询当前数据导入菜单:")
    cursor.execute("SELECT menu_id, menu_name, parent_id, visible, status FROM sys_menu WHERE menu_id = 4449")
    menu = cursor.fetchone()
    if menu:
        print(f"  当前状态 - ID:{menu[0]}, 名称:{menu[1]}, 父ID:{menu[2]}, 可见:{menu[3]}, 状态:{menu[4]}")
        
        print("\n更新菜单为不可见...")
        cursor.execute("UPDATE sys_menu SET visible = '1' WHERE menu_id = 4449")
        conn.commit()
        print(f"  更新成功，影响行数: {cursor.rowcount}")
        
        print("\n查询更新后的状态:")
        cursor.execute("SELECT menu_id, menu_name, parent_id, visible, status FROM sys_menu WHERE menu_id = 4449")
        updated = cursor.fetchone()
        print(f"  更新后状态 - ID:{updated[0]}, 名称:{updated[1]}, 父ID:{updated[2]}, 可见:{updated[3]}, 状态:{updated[4]}")
    else:
        print("  未找到菜单ID 4449")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("操作完成！")
    print("=" * 60)
    print("\n提示：请刷新页面或重新登录后，数据导入菜单项将不再显示")
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
