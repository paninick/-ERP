
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'charset': 'utf8mb4'
}

def check_all_databases():
    """检查所有数据库"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("检查所有可用数据库")
        print("="*100)
        
        # 获取所有数据库
        cursor.execute("SHOW DATABASES")
        databases = [db[0] for db in cursor.fetchall()]
        
        print(f"\n找到 {len(databases)} 个数据库:")
        for i, db in enumerate(databases, 1):
            print(f"  {i}. {db}")
        
        # 检查每个数据库中是否有打样通知相关表
        print("\n\n检查各数据库中的打样通知相关表:")
        print("="*100)
        
        for db in databases:
            try:
                cursor.execute(f"USE {db}")
                cursor.execute("SHOW TABLES")
                tables = [t[0] for t in cursor.fetchall()]
                
                notice_tables = [t for t in tables if 'notice' in t.lower()]
                
                if notice_tables:
                    print(f"\n【{db}】")
                    print(f"  找到打样通知相关表: {notice_tables}")
                    
                    # 检查图片关联表
                    for table in notice_tables:
                        if 'file' in table.lower() or 'picture' in table.lower() or 'pic' in table.lower():
                            cursor.execute(f"SELECT COUNT(*) FROM {table}")
                            count = cursor.fetchone()[0]
                            print(f"    {table}: {count} 条记录")
                            
                            if count > 0:
                                cursor.execute(f"SELECT * FROM {table} LIMIT 5")
                                rows = cursor.fetchall()
                                print(f"    样本数据:")
                                for row in rows:
                                    print(f"      {row}")
            except Exception as e:
                continue
        
        print("\n" + "="*100)
        print("检查完成！")
        print("="*100)
        
    except Exception as e:
        print(f"检查失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    check_all_databases()

