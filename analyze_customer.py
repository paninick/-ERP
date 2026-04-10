
import pandas as pd
import pymysql

excel_path = r"C:\Users\91306\Downloads\customer_1775745201959.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def analyze_customer_data():
    """分析客户数据"""
    print("="*100)
    print("分析客户及业务员数据")
    print("="*100)
    
    # 1. 读取Excel文件
    print("\n【1】读取Excel文件...")
    df = pd.read_excel(excel_path)
    print(f"共 {len(df)} 条数据")
    print(f"\n列名: {df.columns.tolist()}")
    
    print("\n前10行数据:")
    print(df.head(10).to_string())
    
    # 2. 分析数据
    print("\n\n【2】数据分析...")
    
    if '客户id' in df.columns:
        print(f"\n客户id 唯一值数量: {df['客户id'].nunique()}")
        print(f"客户id 样本: {df['客户id'].dropna().unique()[:10].tolist()}")
    
    if '客户名称' in df.columns:
        print(f"\n客户名称 唯一值数量: {df['客户名称'].nunique()}")
        print(f"客户名称 样本: {df['客户名称'].dropna().unique()[:10].tolist()}")
    
    if '业务员' in df.columns:
        print(f"\n业务员 唯一值数量: {df['业务员'].nunique()}")
        print(f"业务员 样本: {df['业务员'].dropna().unique()[:10].tolist()}")
    
    if '客户id' in df.columns and '客户名称' in df.columns:
        print("\n\n【3】客户ID与客户名称对应关系:")
        print("-"*100)
        customer_mapping = df[['客户id', '客户名称']].dropna().drop_duplicates()
        for _, row in customer_mapping.head(20).iterrows():
            print(f"  {row['客户id']} → {row['客户名称']}")
        if len(customer_mapping) > 20:
            print(f"  ... 共 {len(customer_mapping)} 条")
    
    if '业务员' in df.columns and '客户名称' in df.columns:
        print("\n\n【4】业务员与客户对应关系:")
        print("-"*100)
        sales_customer = df[['业务员', '客户名称']].dropna().drop_duplicates()
        for _, row in sales_customer.head(20).iterrows():
            print(f"  {row['业务员']} → {row['客户名称']}")
        if len(sales_customer) > 20:
            print(f"  ... 共 {len(sales_customer)} 条")
    
    # 3. 检查数据库现有数据
    print("\n\n【5】检查数据库现有数据...")
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        # 客户表
        print("\n客户表 (t_erp_customer):")
        cursor.execute("SELECT COUNT(*) FROM t_erp_customer")
        count = cursor.fetchone()[0]
        print(f"  记录数: {count}")
        
        if count &gt; 0:
            cursor.execute("SELECT id, customer_no, customer_name, customer_brief FROM t_erp_customer LIMIT 20")
            print("\n  样本数据:")
            for row in cursor.fetchall():
                print(f"    {row}")
        
        # 用户表
        print("\n用户表 (sys_user):")
        cursor.execute("SELECT COUNT(*) FROM sys_user")
        count = cursor.fetchone()[0]
        print(f"  记录数: {count}")
        
        if count &gt; 0:
            cursor.execute("SELECT user_id, user_name, nick_name FROM sys_user LIMIT 20")
            print("\n  样本数据:")
            for row in cursor.fetchall():
                print(f"    {row}")
        
    except Exception as e:
        print(f"  检查数据库失败: {e}")
    finally:
        if conn:
            conn.close()
    
    print("\n" + "="*100)
    print("分析完成！")
    print("="*100)

if __name__ == "__main__":
    analyze_customer_data()

