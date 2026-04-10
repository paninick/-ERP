
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

def check_table_structure(table_name):
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

def get_table_data(table_name, limit=10):
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
    print("="*100)
    print("客户与用户数据核查")
    print("="*100)
    
    # 客户表
    print("\n【1】客户表结构 (t_erp_customer):")
    print("-"*100)
    structure = check_table_structure('t_erp_customer')
    if not structure.empty:
        print(structure.to_string(index=False))
    
    print("\n【2】客户数据样本:")
    print("-"*100)
    data = get_table_data('t_erp_customer', limit=10)
    if not data.empty:
        print(data.to_string(index=False))
    
    # 打样通知表结构
    print("\n【3】打样通知表结构 (t_erp_sample_notice):")
    print("-"*100)
    structure = check_table_structure('t_erp_sample_notice')
    if not structure.empty:
        print(structure.to_string(index=False))
    
    print("\n【4】打样通知数据样本:")
    print("-"*100)
    data = get_table_data('t_erp_sample_notice', limit=5)
    if not data.empty:
        print(data.to_string(index=False))
    
    print("\n" + "="*100)

if __name__ == "__main__":
    main()

