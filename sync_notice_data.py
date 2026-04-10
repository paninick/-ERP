
import pandas as pd
import pymysql
import os

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def read_excel_data():
    """读取Excel数据"""
    try:
        if not os.path.exists(excel_path):
            print(f"文件不存在: {excel_path}")
            return pd.DataFrame()
        
        # 尝试不同的读取方式
        df = pd.read_excel(excel_path)
        print(f"成功读取Excel文件，共 {len(df)} 行数据")
        print("\n列名:")
        print(df.columns.tolist())
        print("\n前5行数据:")
        print(df.head())
        return df
    except Exception as e:
        print(f"读取Excel失败: {e}")
        return pd.DataFrame()

def get_dict_mapping():
    """获取字典映射关系"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        
        mappings = {
            'sample_type': {},
            'sample_category': {},
            'sample_style': {}
        }
        
        # 打样类型
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_type'][row['dict_label']] = row['dict_value']
        
        # 样品类型
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_category'][row['dict_label']] = row['dict_value']
        
        # 款式大类
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_style'][row['dict_label']] = row['dict_value']
        
        return mappings
    except Exception as e:
        print(f"获取字典映射失败: {e}")
        return {}
    finally:
        if conn:
            conn.close()

def get_customer_mapping():
    """获取客户映射"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = "SELECT id, customer_name FROM t_erp_customer"
        df = pd.read_sql(sql, conn)
        mapping = {}
        for _, row in df.iterrows():
            mapping[row['customer_name']] = row['id']
        return mapping
    except Exception as e:
        print(f"获取客户映射失败: {e}")
        return {}
    finally:
        if conn:
            conn.close()

def get_user_mapping():
    """获取用户映射"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = "SELECT user_id, nick_name FROM sys_user"
        df = pd.read_sql(sql, conn)
        mapping = {}
        for _, row in df.iterrows():
            mapping[row['nick_name']] = row['user_id']
        return mapping
    except Exception as e:
        print(f"获取用户映射失败: {e}")
        return {}
    finally:
        if conn:
            conn.close()

def main():
    print("="*100)
    print("打样通知数据同步工具")
    print("="*100)
    
    # 1. 读取Excel数据
    print("\n【1】读取Excel数据...")
    df = read_excel_data()
    if df.empty:
        print("未找到数据，退出")
        return
    
    # 2. 获取映射关系
    print("\n【2】获取字典映射...")
    dict_mappings = get_dict_mapping()
    print("打样类型映射:", dict_mappings['sample_type'])
    print("样品类型映射:", dict_mappings['sample_category'])
    print("款式大类映射:", dict_mappings['sample_style'])
    
    print("\n【3】获取客户映射...")
    customer_mapping = get_customer_mapping()
    print(f"找到 {len(customer_mapping)} 个客户")
    
    print("\n【4】获取用户映射...")
    user_mapping = get_user_mapping()
    print(f"找到 {len(user_mapping)} 个用户")
    
    print("\n" + "="*100)
    print("数据准备完成！")
    print("="*100)

if __name__ == "__main__":
    main()

