import pymysql
from db_config import get_connection

def check_bom_tables():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 检查BOM相关表 ===")
        
        # 查找所有BOM相关的表
        cursor.execute("SHOW TABLES LIKE '%bom%'")
        tables = cursor.fetchall()
        print("BOM相关表:")
        for table in tables:
            print(f"  {table[0]}")
        
        # 检查每个BOM表的数据量
        print("\n=== 各表数据量 ===")
        for table in tables:
            table_name = table[0]
            cursor.execute(f"SELECT COUNT(*) as total FROM {table_name}")
            count = cursor.fetchone()[0]
            print(f"{table_name}: {count} 条")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    check_bom_tables()
