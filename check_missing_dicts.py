
import pymysql
import pandas as pd

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def check_dicts():
    """检查字典"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        
        print("="*100)
        print("检查字典配置")
        print("="*100)
        
        # 检查所有字典类型
        print("\n【1】所有字典类型:")
        print("-"*100)
        sql = "SELECT DISTINCT dict_type FROM sys_dict_data ORDER BY dict_type"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            print(f"  - {row['dict_type']}")
        
        # 检查需要的字典
        print("\n【2】检查需要的字典:")
        print("-"*100)
        
        required_dicts = [
            'erp_sample_type',
            'erp_sample_category', 
            'erp_sample_style',
            'erp_sample_emergency',
            'erp_sample_audit_status'
        ]
        
        for dict_type in required_dicts:
            sql = f"SELECT COUNT(*) as cnt FROM sys_dict_data WHERE dict_type = '{dict_type}'"
            df_count = pd.read_sql(sql, conn)
            count = df_count['cnt'].iloc[0]
            
            if count > 0:
                print(f"  ✓ {dict_type}: {count} 条")
                # 显示数据
                sql = f"SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = '{dict_type}'"
                df_data = pd.read_sql(sql, conn)
                for _, row in df_data.iterrows():
                    print(f"    {row['dict_value']} = {row['dict_label']}")
            else:
                print(f"  ✗ {dict_type}: 不存在")
        
        print("\n" + "="*100)
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_dicts()

