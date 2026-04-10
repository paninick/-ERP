
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def add_dicts():
    """添加更多字典"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("添加更多字典数据")
        print("="*100)
        
        # 1. 添加样品类型7为检测样
        print("\n【1】添加样品类型 7=检测样...")
        
        value = '7'
        label = '检测样'
        sort = 7
        
        cursor.execute(
            "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_category' AND dict_value = %s",
            (value,)
        )
        if not cursor.fetchone():
            sql = """
                INSERT INTO sys_dict_data 
                (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                VALUES (%s, %s, 'erp_sample_category', %s, '0', 'admin', NOW())
            """
            cursor.execute(sql, (label, value, sort))
            print(f"  已添加: 样品类型 {value} = {label}")
        else:
            sql = """
                UPDATE sys_dict_data 
                SET dict_label = %s, dict_sort = %s
                WHERE dict_type = 'erp_sample_category' AND dict_value = %s
            """
            cursor.execute(sql, (label, sort, value))
            print(f"  已更新: 样品类型 {value} = {label}")
        
        # 2. 添加样品款式6为雪纺
        print("\n【2】添加样品款式 6=雪纺...")
        
        value = '6'
        label = '雪纺'
        sort = 6
        
        cursor.execute(
            "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_style' AND dict_value = %s",
            (value,)
        )
        if not cursor.fetchone():
            sql = """
                INSERT INTO sys_dict_data 
                (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                VALUES (%s, %s, 'erp_sample_style', %s, '0', 'admin', NOW())
            """
            cursor.execute(sql, (label, value, sort))
            print(f"  已添加: 样品款式 {value} = {label}")
        else:
            sql = """
                UPDATE sys_dict_data 
                SET dict_label = %s, dict_sort = %s
                WHERE dict_type = 'erp_sample_style' AND dict_value = %s
            """
            cursor.execute(sql, (label, sort, value))
            print(f"  已更新: 样品款式 {value} = {label}")
        
        conn.commit()
        
        # 3. 验证结果
        print("\n【3】验证结果...")
        print("\n样品类型:")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n样品款式:")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n" + "="*100)
        print("字典添加完成！")
        print("="*100)
        print("\n现在请硬刷新浏览器 (Ctrl + F5) 查看效果！")
        
    except Exception as e:
        print(f"添加失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    add_dicts()

