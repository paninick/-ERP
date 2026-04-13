
import pymysql
from db_config import get_connection

def check_bom_table():
    conn = None
    try:
        conn = get_connection()
        cursor = conn.cursor()
        
        # 查看所有表
        print("=" * 80)
        print("数据库中的表")
        print("=" * 80)
        cursor.execute("SHOW TABLES LIKE '%bom%'")
        for row in cursor.fetchall():
            print(row)
        
        # 查看t_erp_bom表结构
        print("\n" + "=" * 80)
        print("t_erp_bom表结构")
        print("=" * 80)
        try:
            cursor.execute("DESCRIBE t_erp_bom")
            for row in cursor.fetchall():
                print(row)
        except Exception as e:
            print(f"表不存在: {e}")
        
        # 查看t_erp_sample_notice表结构作为参考
        print("\n" + "=" * 80)
        print("t_erp_sample_notice表结构（参考）")
        print("=" * 80)
        cursor.execute("DESCRIBE t_erp_sample_notice")
        for row in cursor.fetchall():
            print(row)
            
    except Exception as e:
        print(f"错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_bom_table()

