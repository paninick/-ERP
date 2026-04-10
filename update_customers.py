
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def update_customers():
    """更新客户表"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("根据对应关系更新客户表")
        print("="*100)
        
        # 客户ID对应关系
        customer_mapping = {
            427: '伊势様',
            470: 'HIGLAND-永木',
            487: 'FST-市川',
            379: 'TOA尾方',
            366: 'T2玉田',
            423: 'FST-町田',
            463: 'FST伊勢様(KEYUCA)',
            523: '市川',
            378: 'KRB池岛',
            391: 'MN-片山',
            545: 'LAEP-平野',
            408: '今村样',
            479: '今村样',
            556: '毕然',
            428: '国武样',
            498: '安井様',
            394: '勃尔金',
            404: '榊原',
            411: 'FST村上',
            410: '丰岛',
            555: '薮崎様',
            377: 'GFJ 井上様',
            453: '渡边样'
        }
        
        update_count = 0
        for customer_id, customer_name in customer_mapping.items():
            # 查找现有客户
            cursor.execute("SELECT id FROM t_erp_customer WHERE id = %s", (customer_id,))
            existing = cursor.fetchone()
            
            if existing:
                # 更新客户名称
                sql = "UPDATE t_erp_customer SET customer_name = %s WHERE id = %s"
                cursor.execute(sql, (customer_name, customer_id))
                if cursor.rowcount > 0:
                    print(f"  已更新: ID {customer_id} -> {customer_name}")
                    update_count += 1
            else:
                # 插入新客户
                sql = """
                    INSERT INTO t_erp_customer 
                    (id, customer_name, create_by, create_time)
                    VALUES (%s, %s, 'admin', NOW())
                """
                cursor.execute(sql, (customer_id, customer_name))
                print(f"  已插入: ID {customer_id} -> {customer_name}")
                update_count += 1
        
        conn.commit()
        
        print("\n" + "="*100)
        print(f"更新完成！共更新/插入 {update_count} 条记录")
        print("="*100)
        
        # 验证
        print("\n【验证结果】")
        print("-"*100)
        sql = "SELECT id, customer_name FROM t_erp_customer ORDER BY id"
        cursor.execute(sql)
        print("id | customer_name")
        print("-"*40)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]}")
        
        print("\n" + "="*100)
        print("客户表更新完成！")
        print("="*100)
        
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
    update_customers()

