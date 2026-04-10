
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

def check_data():
    """检查数据库实际数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        
        print("="*100)
        print("检查数据库实际数据")
        print("="*100)
        
        # 查看样本数据
        print("\n【1】样本数据 (前10条):")
        print("-"*100)
        
        sql = """
            SELECT id, sample_no, sample_type, customer_id, 
                   style_category, style_type, sample_category_type,
                   emergency_type, audit_status, audit_by
            FROM t_erp_sample_notice 
            ORDER BY id DESC 
            LIMIT 10
        """
        df = pd.read_sql(sql, conn)
        print(df.to_string(index=False))
        
        # 统计各字段数据情况
        print("\n【2】各字段数据统计:")
        print("-"*100)
        
        fields = [
            ('sample_type', '打样类型'),
            ('customer_id', '客户ID'),
            ('style_category', '款式大类 (style_category)'),
            ('style_type', '样品款式 (style_type)'),
            ('sample_category_type', '样品种类'),
            ('emergency_type', '紧急程度'),
            ('audit_status', '审批状态'),
            ('audit_by', '审批人')
        ]
        
        for field, desc in fields:
            sql = f"SELECT COUNT(*) as cnt FROM t_erp_sample_notice WHERE {field} IS NOT NULL AND {field} != ''"
            df_count = pd.read_sql(sql, conn)
            print(f"{desc}: {df_count['cnt'].iloc[0]} 条有数据")
        
        print("\n" + "="*100)
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_data()

