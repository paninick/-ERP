import pymysql
from db_config import get_connection

def clear_all_tables():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 清空各表数据 ===")
        
        # 1. 大货核版
        print("\n1. 大货核版 (t_erp_check):")
        cursor.execute("SELECT COUNT(*) FROM t_erp_check")
        before_count = cursor.fetchone()[0]
        print(f"   清除前: {before_count} 条")
        
        cursor.execute("DELETE FROM t_erp_check")
        cursor.execute("ALTER TABLE t_erp_check AUTO_INCREMENT = 1")
        deleted_count = cursor.rowcount
        print(f"   已删除: {deleted_count} 条")
        
        cursor.execute("SELECT COUNT(*) FROM t_erp_check")
        after_count = cursor.fetchone()[0]
        print(f"   清除后: {after_count} 条")
        
        # 2. 样衣BOM
        print("\n2. 样衣BOM (t_erp_bom):")
        cursor.execute("SELECT COUNT(*) FROM t_erp_bom")
        before_count = cursor.fetchone()[0]
        print(f"   清除前: {before_count} 条")
        
        cursor.execute("DELETE FROM t_erp_bom")
        cursor.execute("ALTER TABLE t_erp_bom AUTO_INCREMENT = 1")
        deleted_count = cursor.rowcount
        print(f"   已删除: {deleted_count} 条")
        
        cursor.execute("SELECT COUNT(*) FROM t_erp_bom")
        after_count = cursor.fetchone()[0]
        print(f"   清除后: {after_count} 条")
        
        # 3. 工艺指示书
        print("\n3. 工艺指示书 (t_erp_sample_tech):")
        cursor.execute("SELECT COUNT(*) FROM t_erp_sample_tech")
        before_count = cursor.fetchone()[0]
        print(f"   清除前: {before_count} 条")
        
        cursor.execute("DELETE FROM t_erp_sample_tech")
        cursor.execute("ALTER TABLE t_erp_sample_tech AUTO_INCREMENT = 1")
        deleted_count = cursor.rowcount
        print(f"   已删除: {deleted_count} 条")
        
        cursor.execute("SELECT COUNT(*) FROM t_erp_sample_tech")
        after_count = cursor.fetchone()[0]
        print(f"   清除后: {after_count} 条")
        
        conn.commit()
        print("\n✅ 所有表数据已成功清空！")
        
    except Exception as e:
        conn.rollback()
        print(f"清空出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    clear_all_tables()
