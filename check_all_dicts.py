import pymysql

# 数据库连接配置
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def check_all_dictionaries():
    conn = pymysql.connect(**DB_CONFIG)
    cursor = conn.cursor()
    
    try:
        print("=== 查看所有字典类型 ===")
        cursor.execute("SELECT DISTINCT dict_type FROM sys_dict_data")
        dict_types = cursor.fetchall()
        print("所有字典类型:")
        for dt in dict_types:
            print(f"  {dt[0]}")
        
        # 查看大货核版相关字典
        print("\n=== 查看审批状态相关字典 ===")
        cursor.execute("SELECT dict_type, dict_value, dict_label FROM sys_dict_data WHERE dict_type LIKE '%audit%' OR dict_type LIKE '%approve%' OR dict_type LIKE '%审批%'")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} = {row[2]}")
        
        print("\n=== 查看样品款式字典 ===")
        cursor.execute("SELECT dict_type, dict_value, dict_label FROM sys_dict_data WHERE dict_type LIKE '%style%' OR dict_type LIKE '%款式%'")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} = {row[2]}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    check_all_dictionaries()
