
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def fix_style_type():
    """修复style_type字段"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("修复 style_type 字段")
        print("="*100)
        
        # 把 style_category 的数据复制到 style_type
        print("\n【1】复制 style_category 到 style_type...")
        sql = "UPDATE t_erp_sample_notice SET style_type = style_category WHERE style_category IS NOT NULL AND style_category != ''"
        cursor.execute(sql)
        print(f"  更新了 {cursor.rowcount} 条记录")
        
        conn.commit()
        
        # 验证
        print("\n【2】验证结果...")
        sql = """
            SELECT id, sample_no, style_category, style_type
            FROM t_erp_sample_notice 
            WHERE style_category IS NOT NULL AND style_category != ''
            ORDER BY id DESC 
            LIMIT 10
        """
        cursor.execute(sql)
        print("\n样本数据:")
        print("id | sample_no | style_category | style_type")
        print("-"*70)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]} | {row[2]} | {row[3]}")
        
        # 统计
        sql = "SELECT COUNT(*) as cnt FROM t_erp_sample_notice WHERE style_type IS NOT NULL AND style_type != ''"
        cursor.execute(sql)
        count = cursor.fetchone()[0]
        print(f"\nstyle_type 有数据: {count} 条")
        
        print("\n" + "="*100)
        print("修复完成！")
        print("="*100)
        print("\n现在请刷新浏览器页面！")
        
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
    fix_style_type()

