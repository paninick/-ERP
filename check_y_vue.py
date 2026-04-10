
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'charset': 'utf8mb4'
}

def check_y_vue():
    """检查y_vue数据库"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("检查 y_vue 数据库")
        print("="*100)
        
        # 使用y_vue数据库
        cursor.execute("USE y_vue")
        
        # 查看所有表
        cursor.execute("SHOW TABLES")
        tables = [t[0] for t in cursor.fetchall()]
        print(f"\n所有表 ({len(tables)} 个):")
        for i, table in enumerate(tables, 1):
            print(f"  {i}. {table}")
        
        # 查找打样通知相关表
        notice_tables = [t for t in tables if 'notice' in t.lower()]
        if notice_tables:
            print(f"\n打样通知相关表: {notice_tables}")
            
            for table in notice_tables:
                print(f"\n--- {table} ---")
                cursor.execute(f"SELECT COUNT(*) FROM {table}")
                count = cursor.fetchone()[0]
                print(f"记录数: {count}")
                
                if count > 0:
                    cursor.execute(f"DESCRIBE {table}")
                    print("\n表结构:")
                    for col in cursor.fetchall():
                        print(f"  {col[0]} - {col[1]}")
                    
                    if 'file' in table.lower() or 'picture' in table.lower() or 'pic' in table.lower():
                        print("\n样本数据:")
                        cursor.execute(f"SELECT * FROM {table} LIMIT 10")
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
    check_y_vue()

