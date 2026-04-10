
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
    """添加缺失的字典"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("添加缺失的字典数据")
        print("="*100)
        
        # 1. 紧急程度
        print("\n【1】添加紧急程度字典...")
        emergency_data = [
            ('0', '正常', 1),
            ('1', '紧急', 2),
            ('2', '非常紧急', 3)
        ]
        
        for value, label, sort in emergency_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_emergency' AND dict_value = %s",
                (value,)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, 'erp_sample_emergency', %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, sort))
                print(f"  已添加: 紧急程度 {value} = {label}")
            else:
                sql = """
                    UPDATE sys_dict_data 
                    SET dict_label = %s, dict_sort = %s
                    WHERE dict_type = 'erp_sample_emergency' AND dict_value = %s
                """
                cursor.execute(sql, (label, sort, value))
                print(f"  已更新: 紧急程度 {value} = {label}")
        
        # 2. 审批状态
        print("\n【2】添加审批状态字典...")
        audit_status_data = [
            ('0', '未审批', 1),
            ('1', '审批中', 2),
            ('2', '审批通过', 3),
            ('3', '审批不通过', 4)
        ]
        
        for value, label, sort in audit_status_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = 'erp_sample_audit_status' AND dict_value = %s",
                (value,)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, 'erp_sample_audit_status', %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, sort))
                print(f"  已添加: 审批状态 {value} = {label}")
            else:
                sql = """
                    UPDATE sys_dict_data 
                    SET dict_label = %s, dict_sort = %s
                    WHERE dict_type = 'erp_sample_audit_status' AND dict_value = %s
                """
                cursor.execute(sql, (label, sort, value))
                print(f"  已更新: 审批状态 {value} = {label}")
        
        conn.commit()
        
        # 3. 验证结果
        print("\n【3】验证结果...")
        print("\n紧急程度:")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_emergency' ORDER BY dict_sort")
        for row in cursor.fetchall():
            print(f"  {row[0]} = {row[1]}")
        
        print("\n审批状态:")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_audit_status' ORDER BY dict_sort")
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

