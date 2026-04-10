
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def check_table():
    """检查表结构"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("检查 sys_dict_type 表结构")
        print("="*100)
        
        cursor.execute("DESCRIBE sys_dict_type")
        print("\n表结构:")
        for col in cursor.fetchall():
            print(f"  {col}")
        
        print("\n现有数据:")
        cursor.execute("SELECT * FROM sys_dict_type")
        for row in cursor.fetchall():
            print(f"  {row}")
        
        print("\n" + "="*100)
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_table()

