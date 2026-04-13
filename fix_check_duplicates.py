import pymysql
from db_config import get_connection

def fix_duplicates():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 修复大货核版重复数据 ===")
        
        # 1. 先删除check_no为NULL的数据
        print("正在删除check_no为NULL的数据...")
        cursor.execute("DELETE FROM t_erp_check WHERE check_no IS NULL")
        deleted_null = cursor.rowcount
        print(f"已删除 {deleted_null} 条check_no为NULL的数据")
        conn.commit()
        
        # 2. 保留每个check_no的第一条，删除重复的
        print("正在删除重复数据...")
        cursor.execute("""
            DELETE t1 FROM t_erp_check t1
            INNER JOIN (
                SELECT check_no, MIN(check_id) as min_id
                FROM t_erp_check
                GROUP BY check_no
                HAVING COUNT(*) > 1
            ) t2 ON t1.check_no = t2.check_no AND t1.check_id > t2.min_id
        """)
        deleted_duplicates = cursor.rowcount
        print(f"已删除 {deleted_duplicates} 条重复数据")
        conn.commit()
        
        # 3. 检查最终数量
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_check")
        final_count = cursor.fetchone()[0]
        print(f"\n最终大货核版数据: {final_count} 条")
        
        print("\n=== 检查是否还有重复数据 ===")
        cursor.execute("""
            SELECT check_no, COUNT(*) as cnt 
            FROM t_erp_check 
            GROUP BY check_no 
            HAVING cnt > 1 
            LIMIT 10
        """)
        check_duplicates = cursor.fetchall()
        if check_duplicates:
            print("仍有重复数据:")
            for row in check_duplicates:
                print(f"  {row[0]}: {row[1]} 次")
        else:
            print("✅ 未发现重复数据")
        
    except Exception as e:
        conn.rollback()
        print(f"修复出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    fix_duplicates()
