
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'charset': 'utf8mb4'
}

conn = None
try:
    conn = pymysql.connect(**config)
    cursor = conn.cursor()
    
    print("可用的数据库列表:")
    print("="*80)
    cursor.execute("SHOW DATABASES")
    databases = cursor.fetchall()
    for db in databases:
        print(f"  - {db[0]}")
    
    print("\n" + "="*80)
    
except Exception as e:
    print(f"错误: {e}")
finally:
    if conn:
        conn.close()
