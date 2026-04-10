
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def verify_data():
    """验证数据修复情况"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("验证数据修复情况")
        print("="*100)
        
        # 1. 检查各字段数据
        print("\n【1】各字段数据统计:")
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
            cursor.execute(sql)
            count = cursor.fetchone()[0]
            print(f"{desc}: {count} 条有数据")
        
        # 2. 显示样本数据
        print("\n【2】样本数据:")
        print("-"*100)
        sql = """
            SELECT id, sample_no, sample_type, customer_id, 
                   style_category, style_type, sample_category_type,
                   emergency_type, audit_status, audit_by
            FROM t_erp_sample_notice 
            ORDER BY id DESC 
            LIMIT 10
        """
        cursor.execute(sql)
        print("id | sample_no | sample_type | customer_id | style_category | style_type | sample_category_type | emergency_type | audit_status | audit_by")
        print("-"*150)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]} | {row[2]} | {row[3]} | {row[4]} | {row[5]} | {row[6]} | {row[7]} | {row[8]} | {row[9]}")
        
        print("\n" + "="*100)
        print("验证完成！")
        print("="*100)
        print("\n现在请刷新浏览器页面查看效果！")
        
    except Exception as e:
        print(f"验证失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    verify_data()

