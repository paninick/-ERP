
import pymysql
import pandas as pd

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def get_all_tables():
    """获取所有表名"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = "SHOW TABLES"
        df = pd.read_sql(sql, conn)
        return df.iloc[:, 0].tolist()
    except Exception as e:
        print(f"查询表列表失败: {e}")
        return []
    finally:
        if conn:
            conn.close()

def check_table_structure(table_name):
    """检查表结构"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = f"DESCRIBE {table_name}"
        df = pd.read_sql(sql, conn)
        return df
    except Exception as e:
        print(f"查询表结构失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def get_table_sample(table_name, limit=10):
    """获取表数据样本"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = f"SELECT * FROM {table_name} LIMIT {limit}"
        df = pd.read_sql(sql, conn)
        return df
    except Exception as e:
        print(f"查询表数据失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def main():
    print("="*80)
    print("ry_vue 数据库表结构分析")
    print("="*80)
    
    # 获取所有表
    tables = get_all_tables()
    print("\n【1】数据库表列表:")
    print("-"*80)
    for i, table in enumerate(tables, 1):
        print(f"  {i}. {table}")
    
    # 查找ERP相关表
    erp_tables = [t for t in tables if t.startswith('erp_')]
    print("\n【2】ERP相关表:")
    print("-"*80)
    for i, table in enumerate(erp_tables, 1):
        print(f"  {i}. {table}")
    
    # 检查重点表
    key_tables = ['erp_sample_notice', 'erp_customer', 'sys_user', 'sys_dict_data']
    print("\n【3】重点表结构检查:")
    print("="*80)
    
    for table in key_tables:
        if table in tables:
            print(f"\n--- {table} 表结构 ---")
            structure = check_table_structure(table)
            if not structure.empty:
                print(structure.to_string(index=False))
            
            print(f"\n--- {table} 数据样本 ---")
            sample = get_table_sample(table, limit=5)
            if not sample.empty:
                print(sample.to_string(index=False))
        else:
            print(f"\n--- {table} 表不存在 ---")
    
    print("\n" + "="*80)
    print("分析完成！")
    print("="*80)

if __name__ == "__main__":
    main()

