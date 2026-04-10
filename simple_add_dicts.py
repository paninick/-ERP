
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
    """添加字典数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("添加字典数据")
        print("="*100)
        
        # 审批状态数据
        audit_status_data = [
            ('未审批', '0', 'erp_sample_audit_status', 1),
            ('审批中', '1', 'erp_sample_audit_status', 2),
            ('审批通过', '2', 'erp_sample_audit_status', 3),
            ('审批不通过', '3', 'erp_sample_audit_status', 4)
        ]
        
        # 紧急程度数据
        emergency_data = [
            ('正常', '0', 'erp_sample_emergency', 1),
            ('紧急', '1', 'erp_sample_emergency', 2),
            ('非常紧急', '2', 'erp_sample_emergency', 3)
        ]
        
        print("\n添加审批状态字典...")
        for label, value, dict_type, sort in audit_status_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = %s AND dict_value = %s",
                (dict_type, value)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, %s, %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, dict_type, sort))
                print(f"  已添加: {label}")
        
        print("\n添加紧急程度字典...")
        for label, value, dict_type, sort in emergency_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = %s AND dict_value = %s",
                (dict_type, value)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, dict_sort, status, create_by, create_time)
                    VALUES (%s, %s, %s, %s, '0', 'admin', NOW())
                """
                cursor.execute(sql, (label, value, dict_type, sort))
                print(f"  已添加: {label}")
        
        conn.commit()
        
        print("\n" + "="*100)
        print("字典添加完成！")
        print("="*100)
        
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

