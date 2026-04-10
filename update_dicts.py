
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def update_dicts():
    """更新字典数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("更新字典数据")
        print("="*100)
        
        # 1. 先查看现有字典
        print("\n【1】查看现有字典...")
        
        print("\n打样类型 (erp_sample_type):")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n样品种类 (erp_sample_category):")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        # 2. 更新打样类型 - 添加"拼接"
        print("\n\n【2】更新打样类型字典...")
        sample_type_data = [
            ('1', '服饰', 1),
            ('2', '毛衫', 2),
            ('3', '拼接', 3)
        ]
        
        for value, label, sort in sample_type_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_type' AND dict_value = %s",
                (value,)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, 'erp_sample_type', %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, sort))
                print(f"  已添加: 打样类型 {value} = {label}")
            else:
                sql = """
                    UPDATE sys_dict_data 
                    SET dict_label = %s, dict_sort = %s
                    WHERE dict_type = 'erp_sample_type' AND dict_value = %s
                """
                cursor.execute(sql, (label, sort, value))
                print(f"  已更新: 打样类型 {value} = {label}")
        
        # 3. 更新样品种类
        print("\n\n【3】更新样品种类字典...")
        sample_category_data = [
            ('1', '初样', 1),
            ('2', '二次样', 2),
            ('3', '三次样', 3),
            ('6', '摄影样', 6),
            ('8', '大货量产', 8),
            ('9', '产前样', 9)
        ]
        
        for value, label, sort in sample_category_data:
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
                print(f"  已添加: 样品种类 {value} = {label}")
            else:
                sql = """
                    UPDATE sys_dict_data 
                    SET dict_label = %s, dict_sort = %s
                    WHERE dict_type = 'erp_sample_category' AND dict_value = %s
                """
                cursor.execute(sql, (label, sort, value))
                print(f"  已更新: 样品种类 {value} = {label}")
        
        conn.commit()
        
        # 4. 验证更新结果
        print("\n\n【4】验证更新结果...")
        print("\n打样类型 (erp_sample_type):")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n样品种类 (erp_sample_category):")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n" + "="*100)
        print("字典更新完成！")
        print("="*100)
        print("\n现在请刷新浏览器页面！")
        
    except Exception as e:
        print(f"更新失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    update_dicts()

