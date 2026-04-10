
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def update_customer():
    """更新客户ID 400"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("更新客户ID 400为MN-青松")
        print("="*100)
        
        # 查找现有客户
        cursor.execute("SELECT id, customer_name FROM t_erp_customer WHERE id = 400")
        existing = cursor.fetchone()
        
        if existing:
            print(f"\n当前客户 400: {existing[1]}")
            # 更新客户名称
            sql = "UPDATE t_erp_customer SET customer_name = %s WHERE id = 400"
            cursor.execute(sql, ('MN-青松',))
            print(f"已更新: ID 400 -> MN-青松")
        else:
            # 插入新客户
            sql = """
                INSERT INTO t_erp_customer 
                (id, customer_name, create_by, create_time)
                VALUES (%s, %s, 'admin', NOW())
            """
            cursor.execute(sql, (400, 'MN-青松'))
            print(f"已插入: ID 400 -> MN-青松")
        
        conn.commit()
        
        # 验证
        print("\n【验证结果】")
        print("-"*100)
        sql = "SELECT id, customer_name FROM t_erp_customer WHERE id = 400"
        cursor.execute(sql)
        result = cursor.fetchone()
        if result:
            print(f"id | customer_name")
            print("-"*40)
            print(f"{result[0]} | {result[1]}")
        
        print("\n" + "="*100)
        print("客户更新完成！")
        print("="*100)
        print("\n现在请硬刷新浏览器 (Ctrl + F5) 查看效果！")
        
    except Exception as e:
        print(f"更新失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    update_customer()

