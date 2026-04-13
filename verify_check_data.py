import pymysql
from db_config import get_connection

def verify_data():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 验证大货核版数据 ===")
        
        # 查看总数量
        cursor.execute("SELECT COUNT(*) FROM t_erp_check")
        total = cursor.fetchone()[0]
        print(f"总记录数: {total}")
        
        # 查看前10条数据
        print("\n前10条数据:")
        cursor.execute("SELECT * FROM t_erp_check LIMIT 10")
        columns = [desc[0] for desc in cursor.description]
        
        for i, row in enumerate(cursor.fetchall()):
            print(f"\n记录 {i+1}:")
            for col, val in zip(columns, row):
                print(f"  {col}: {val}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    verify_data()
