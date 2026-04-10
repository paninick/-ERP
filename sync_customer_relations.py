
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

def sync_customer_relations():
    """同步客户关系"""
    print("="*100)
    print("根据正式库关系调整测试库")
    print("="*100)
    
    # 1. 读取Excel
    print("\n【1】读取Excel数据...")
    df = pd.read_excel(excel_path)
    print(f"共 {len(df)} 条数据")
    
    # 2. 建立客户名称与业务员的映射
    print("\n【2】建立映射关系...")
    customer_sales_mapping = {}
    for _, row in df.iterrows():
        customer_name = row['客户名称']
        sales_id = row['业务员']
        if pd.notna(customer_name) and pd.notna(sales_id):
            customer_sales_mapping[customer_name] = int(sales_id)
    
    print(f"建立了 {len(customer_sales_mapping)} 个客户-业务员映射关系")
    
    # 3. 连接数据库
    print("\n【3】连接数据库...")
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        # 4. 更新客户表的业务员ID
        print("\n【4】更新客户表业务员ID...")
        update_count = 0
        for customer_name, sales_id in customer_sales_mapping.items():
            sql = """
                UPDATE t_erp_customer 
                SET sales_id = %s 
                WHERE customer_name = %s
            """
            cursor.execute(sql, (sales_id, customer_name))
            if cursor.rowcount > 0:
                update_count += 1
        
        conn.commit()
        print(f"更新了 {update_count} 个客户的业务员ID")
        
        # 5. 验证结果
        print("\n【5】验证结果...")
        sql = "SELECT id, customer_name, sales_id FROM t_erp_customer WHERE sales_id IS NOT NULL LIMIT 20"
        cursor.execute(sql)
        print("\n客户表样本数据:")
        print("id | customer_name | sales_id")
        print("-"*60)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]} | {row[2]}")
        
        # 6. 检查用户表中的业务员
        print("\n【6】检查用户表中的业务员...")
        sales_ids = list(customer_sales_mapping.values())
        if sales_ids:
            sales_ids_str = ','.join([str(x) for x in set(sales_ids)])
            sql = f"SELECT user_id, nick_name FROM sys_user WHERE user_id IN ({sales_ids_str})"
            cursor.execute(sql)
            print("\n业务员对应关系:")
            print("user_id | nick_name")
            print("-"*40)
            for row in cursor.fetchall():
                print(f"{row[0]} | {row[1]}")
        
        print("\n" + "="*100)
        print("同步完成！")
        print("="*100)
        
    except Exception as e:
        print(f"同步失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    sync_customer_relations()

