
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def verify_dicts():
    """验证字典数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("验证字典数据")
        print("="*100)
        
        # 检查所有需要的字典
        dict_types = [
            ('erp_sample_type', '打样类型'),
            ('erp_sample_style', '样品款式'),
            ('erp_sample_category', '样品种类'),
            ('erp_sample_emergency', '紧急程度'),
            ('erp_sample_audit_status', '审批状态')
        ]
        
        for dict_type, dict_name in dict_types:
            print(f"\n【{dict_name}】({dict_type}):")
            print("-"*60)
            sql = f"SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = '{dict_type}' ORDER BY dict_sort"
            cursor.execute(sql)
            rows = cursor.fetchall()
            if rows:
                for row in rows:
                    print(f"  {row[0]} = {row[1]}")
            else:
                print("  (无数据)")
        
        print("\n" + "="*100)
        print("验证完成！")
        print("="*100)
        
    except Exception as e:
        print(f"验证失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    verify_dicts()

