
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def check_data():
    """检查当前数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("检查当前数据库数据")
        print("="*100)
        
        # 1. 检查打样通知表前20条
        print("\n【1】打样通知表前20条:")
        print("-"*100)
        sql = """
            SELECT id, sample_no, sample_type, customer_id, 
                   style_type, sample_category_type,
                   audit_status, audit_by
            FROM t_erp_sample_notice 
            ORDER BY id 
            LIMIT 20
        """
        cursor.execute(sql)
        print("id | sample_no | sample_type | customer_id | style_type | sample_category_type | audit_status | audit_by")
        print("-"*140)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]} | {row[2]} | {row[3]} | {row[4]} | {row[5]} | {row[6]} | {row[7]}")
        
        # 2. 检查字典数据
        print("\n\n【2】检查字典数据:")
        print("-"*100)
        
        dict_types = [
            ('erp_sample_type', '打样类型'),
            ('erp_sample_style', '样品款式'),
            ('erp_sample_category', '样品种类'),
            ('erp_sample_audit_status', '审批状态')
        ]
        
        for dict_type, dict_name in dict_types:
            print(f"\n{dict_name} ({dict_type}):")
            sql = f"SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = '{dict_type}' ORDER BY dict_sort"
            cursor.execute(sql)
            for row in cursor.fetchall():
                print(f"  {row[0]} = {row[1]}")
        
        # 3. 检查客户表
        print("\n\n【3】客户表样本:")
        print("-"*100)
        sql = "SELECT id, customer_name FROM t_erp_customer ORDER BY id LIMIT 20"
        cursor.execute(sql)
        print("id | customer_name")
        print("-"*40)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]}")
        
        print("\n" + "="*100)
        print("检查完成！")
        print("="*100)
        print("\n如果数据正确但前端显示不对，请重启后端服务！")
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_data()

