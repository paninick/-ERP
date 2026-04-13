import pymysql
from db_config import get_connection

def clear_check_data():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 清除大货核版数据 ===")
        
        # 先查看当前数据量
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_check")
        before_count = cursor.fetchone()[0]
        print(f"清除前数据: {before_count} 条")
        
        # 清除数据
        print("正在清除数据...")
        cursor.execute("DELETE FROM t_erp_check")
        deleted_count = cursor.rowcount
        conn.commit()
        print(f"已清除 {deleted_count} 条数据")
        
        # 确认清除成功
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_check")
        after_count = cursor.fetchone()[0]
        print(f"清除后数据: {after_count} 条")
        
        if after_count == 0:
            print("\n✅ 大货核版数据已成功清除！")
        else:
            print("\n⚠️  清除可能未完全成功")
        
    except Exception as e:
        conn.rollback()
        print(f"清除出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    clear_check_data()
