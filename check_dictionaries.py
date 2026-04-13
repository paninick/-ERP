import pymysql
from db_config import get_connection

def check_dictionaries():
    conn = get_connection()
    cursor = conn.cursor(pymysql.cursors.DictCursor)
    
    try:
        # 检查字典类型
        print("=== 检查字典类型 ===")
        cursor.execute("SELECT dict_type, dict_name FROM sys_dict_type WHERE dict_type LIKE '%erp%'")
        dict_types = cursor.fetchall()
        for dt in dict_types:
            print(f"- {dt['dict_type']}: {dt['dict_name']}")
        
        # 检查具体字典数据
        print("\n=== erp_sample_type (打样类型) ===")
        cursor.execute("SELECT dict_code, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type' ORDER BY dict_sort")
        sample_type = cursor.fetchall()
        for d in sample_type:
            print(f"  {d['dict_code']}: {d['dict_label']}")
        
        print("\n=== erp_sample_style (样品款式) ===")
        cursor.execute("SELECT dict_code, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style' ORDER BY dict_sort")
        sample_style = cursor.fetchall()
        for d in sample_style:
            print(f"  {d['dict_code']}: {d['dict_label']}")
        
        print("\n=== erp_sample_category (样品类型) ===")
        cursor.execute("SELECT dict_code, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category' ORDER BY dict_sort")
        sample_category = cursor.fetchall()
        for d in sample_category:
            print(f"  {d['dict_code']}: {d['dict_label']}")
        
        print("\n=== erp_sample_audit_status (审批状态) ===")
        cursor.execute("SELECT dict_code, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_audit_status' ORDER BY dict_sort")
        audit_status = cursor.fetchall()
        for d in audit_status:
            print(f"  {d['dict_code']}: {d['dict_label']}")
        
        print("\n=== erp_check_progress (大货核版进行状态) ===")
        cursor.execute("SELECT dict_code, dict_label FROM sys_dict_data WHERE dict_type = 'erp_check_progress' ORDER BY dict_sort")
        check_progress = cursor.fetchall()
        for d in check_progress:
            print(f"  {d['dict_code']}: {d['dict_label']}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    check_dictionaries()
