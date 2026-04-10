
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

def get_dict_data(dict_type):
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = """
            SELECT dict_code, dict_label, dict_value, dict_type, is_default, status
            FROM sys_dict_data 
            WHERE dict_type = %s 
            ORDER BY dict_sort ASC
        """
        df = pd.read_sql(sql, conn, params=(dict_type,))
        return df
    except Exception as e:
        print(f"查询字典数据失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def get_table_sample(table_name, limit=10):
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
    print("ERP系统字典数据与表数据比对分析报告")
    print("="*100)
    
    # 1. 打样类型字典
    print("\n【1】打样类型字典 (erp_sample_type):")
    print("-"*100)
    df = get_dict_data('erp_sample_type')
    if not df.empty:
        print(df.to_string(index=False))
    else:
        print("未找到数据")
    
    # 2. 样品类型字典
    print("\n【2】样品类型字典 (erp_sample_category):")
    print("-"*100)
    df = get_dict_data('erp_sample_category')
    if not df.empty:
        print(df.to_string(index=False))
    else:
        print("未找到数据")
    
    # 3. 款式大类字典
    print("\n【3】款式大类字典 (erp_sample_style):")
    print("-"*100)
    df = get_dict_data('erp_sample_style')
    if not df.empty:
        print(df.to_string(index=False))
    else:
        print("未找到数据")
    
    # 4. 打样通知数据
    print("\n【4】打样通知数据样本 (t_erp_sample_notice):")
    print("-"*100)
    df = get_table_sample('t_erp_sample_notice', limit=5)
    if not df.empty:
        print(df[['id', 'sample_no', 'sample_type', 'sample_category_type', 
                 'style_category', 'style_subcategory', 'customer_id', 
                 'salesperson', 'approver']].to_string(index=False))
    else:
        print("未找到数据")
    
    # 5. 客户数据
    print("\n【5】客户数据样本 (t_erp_customer):")
    print("-"*100)
    df = get_table_sample('t_erp_customer', limit=5)
    if not df.empty:
        print(df[['id', 'customer_code', 'customer_name', 'customer_short']].to_string(index=False))
    else:
        print("未找到数据")
    
    # 6. 用户数据
    print("\n【6】用户数据样本 (sys_user):")
    print("-"*100)
    df = get_table_sample('sys_user', limit=10)
    if not df.empty:
        print(df[['user_id', 'user_name', 'nick_name']].to_string(index=False))
    else:
        print("未找到数据")
    
    print("\n" + "="*100)
    print("分析总结:")
    print("="*100)
    print("1. 打样类型 (erp_sample_type): 1=服饰, 2=毛衫")
    print("2. 样品类型 (erp_sample_category): 1=初样, 2=最终样")
    print("3. 款式大类 (erp_sample_style): 1=针织, 2=梭织, 3=毛衣")
    print("="*100)

if __name__ == "__main__":
    main()

