
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def check_bom_menu():
    """检查BOM菜单配置"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("检查样衣BOM菜单配置")
        print("="*100)
        
        # 查找包含"BOM"的菜单
        sql = """
            SELECT menu_id, menu_name, parent_id, order_num, path, component, visible, status
            FROM sys_menu 
            WHERE menu_name LIKE '%BOM%' OR menu_name LIKE '%bom%'
            ORDER BY parent_id, order_num
        """
        cursor.execute(sql)
        menus = cursor.fetchall()
        
        if menus:
            print("\n找到的BOM相关菜单:")
            print("-"*100)
            print("menu_id | menu_name | parent_id | path | component | status")
            print("-"*100)
            for menu in menus:
                print(f"{menu[0]} | {menu[1]} | {menu[2]} | {menu[4]} | {menu[5]} | {menu[7]}")
        else:
            print("\n未找到BOM相关菜单！")
        
        print("\n" + "="*100)
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_bom_menu()

