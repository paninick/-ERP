import pymysql
from db_config import get_connection

def find_sample_table():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 查找打样通知相关表 ===")
        
        # 查找所有表
        cursor.execute("SHOW TABLES LIKE '%sample%'")
        tables = cursor.fetchall()
        print("Sample相关表:")
        for table in tables:
            print(f"  {table[0]}")
            # 查看每个表的记录数
            cursor.execute(f"SELECT COUNT(*) FROM {table[0]}")
            count = cursor.fetchone()[0]
            print(f"    记录数: {count}")
        
        # 查找所有打样相关的表
        cursor.execute("SHOW TABLES LIKE '%打样%'")
        tables = cursor.fetchall()
        print("\n打样相关表:")
        for table in tables:
            print(f"  {table[0]}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    find_sample_table()
