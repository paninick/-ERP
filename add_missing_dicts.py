
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
        print("添加缺失的字典配置")
        print("="*100)
        
        # 1. 先检查是否已存在
        print("\n【1】检查现有字典...")
        
        cursor.execute("SELECT dict_code FROM sys_dict_type WHERE dict_type = 'erp_sample_audit_status'")
        audit_status_type_exists = cursor.fetchone()
        
        cursor.execute("SELECT dict_code FROM sys_dict_type WHERE dict_type = 'erp_sample_emergency'")
        emergency_type_exists = cursor.fetchone()
        
        # 2. 添加字典类型
        print("\n【2】添加字典类型...")
        
        if not audit_status_type_exists:
            sql = """
                INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
                VALUES ('审批状态', 'erp_sample_audit_status', '0', 'admin', NOW(), '打样通知审批状态')
            """
            cursor.execute(sql)
            print("  ✓ 已添加字典类型: erp_sample_audit_status")
        
        if not emergency_type_exists:
            sql = """
                INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
                VALUES ('紧急程度', 'erp_sample_emergency', '0', 'admin', NOW(), '打样通知紧急程度')
            """
            cursor.execute(sql)
            print("  ✓ 已添加字典类型: erp_sample_emergency")
        
        # 3. 添加字典数据
        print("\n【3】添加字典数据...")
        
        # 审批状态
        audit_status_data = [
            ('0', '未审批', '0', 1, 'erp_sample_audit_status'),
            ('1', '审批中', '1', 2, 'erp_sample_audit_status'),
            ('2', '审批通过', '2', 3, 'erp_sample_audit_status'),
            ('3', '审批不通过', '3', 4, 'erp_sample_audit_status')
        ]
        
        # 紧急程度
        emergency_data = [
            ('0', '正常', '0', 1, 'erp_sample_emergency'),
            ('1', '紧急', '1', 2, 'erp_sample_emergency'),
            ('2', '非常紧急', '2', 3, 'erp_sample_emergency')
        ]
        
        for label, value, css, sort, dict_type in audit_status_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = %s AND dict_value = %s",
                (dict_type, value)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, css_class, list_class, is_default, status, dict_sort, create_by, create_time, remark)
                    VALUES (%s, %s, %s, '', '', 'N', '0', %s, 'admin', NOW(), '')
                """
                cursor.execute(sql, (label, value, dict_type, sort))
                print(f"  ✓ 已添加: {dict_type} - {label}")
        
        for label, value, css, sort, dict_type in emergency_data:
            cursor.execute(
                "SELECT dict_code FROM sys_dict_data WHERE dict_type = %s AND dict_value = %s",
                (dict_type, value)
            )
            if not cursor.fetchone():
                sql = """
                    INSERT INTO sys_dict_data 
                    (dict_label, dict_value, dict_type, css_class, list_class, is_default, status, dict_sort, create_by, create_time, remark)
                    VALUES (%s, %s, %s, '', '', 'N', '0', %s, 'admin', NOW(), '')
                """
                cursor.execute(sql, (label, value, dict_type, sort))
                print(f"  ✓ 已添加: {dict_type} - {label}")
        
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

