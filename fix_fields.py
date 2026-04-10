
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def fix_fields():
    """修复字段问题"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("修复字段问题")
        print("="*100)
        
        # 1. 把 style_category 的数据复制到 style_type
        print("\n【1】复制 style_category 到 style_type...")
        sql = "UPDATE t_erp_sample_notice SET style_type = style_category WHERE style_category IS NOT NULL AND style_category != ''"
        cursor.execute(sql)
        print(f"  更新了 {cursor.rowcount} 条记录")
        
        # 2. 把 sample_category_type 的数据复制到 sample_category（如果需要）
        print("\n【2】检查 sample_category_type 数据...")
        sql = "UPDATE t_erp_sample_notice SET sample_category = sample_category_type WHERE sample_category_type IS NOT NULL AND sample_category_type != ''"
        cursor.execute(sql)
        print(f"  更新了 {cursor.rowcount} 条记录")
        
        conn.commit()
        
        print("\n【3】验证结果...")
        sql = """
            SELECT id, sample_no, style_category, style_type, 
                   sample_category_type, sample_category
            FROM t_erp_sample_notice 
            ORDER BY id DESC 
            LIMIT 10
        """
        cursor.execute(sql)
        print("\n样本数据:")
        for row in cursor.fetchall():
            print(f"  {row}")
        
        print("\n" + "="*100)
        print("字段修复完成！")
        print("="*100)
        
    except Exception as e:
        print(f"修复失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    fix_fields()

