import pymysql
from db_config import get_connection

def clear_sample_notice():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 清空打样通知数据 ===")
        
        # 打样通知
        print("\n打样通知 (t_erp_sample_notice):")
        cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice")
        before_count = cursor.fetchone()[0]
        print(f"   清除前: {before_count} 条")
        
        cursor.execute("DELETE FROM t_erp_sample_notice")
        cursor.execute("ALTER TABLE t_erp_sample_notice AUTO_INCREMENT = 1")
        deleted_count = cursor.rowcount
        print(f"   已删除: {deleted_count} 条")
        
        cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice")
        after_count = cursor.fetchone()[0]
        print(f"   清除后: {after_count} 条")
        
        conn.commit()
        print("\n打样通知数据已成功清空！")
        
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
    clear_sample_notice()
