import pymysql
from db_config import get_connection

def check_table_structure():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== t_erp_sample_notice 表结构 ===")
        cursor.execute("DESC t_erp_sample_notice")
        columns = cursor.fetchall()
        for col in columns:
            print(f"  {col[0]}: {col[1]}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    check_table_structure()
