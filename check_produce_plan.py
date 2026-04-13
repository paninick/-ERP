import pymysql
from db_config import get_connection

try:
    conn = get_connection()
    cursor = conn.cursor()
    
    # 查询生产计划数据量
    cursor.execute("SELECT COUNT(*) FROM t_erp_produce_plan;")
    count = cursor.fetchone()[0]
    print(f"测试库t_erp_produce_plan表当前共有 {count} 条记录")
    
    # 查询前10条记录看看
    print("\n前5条记录:")
    cursor.execute("SELECT plan_no, produce_status, type, bulk_order_no FROM t_erp_produce_plan LIMIT 5;")
    records = cursor.fetchall()
    for record in records:
        print(f"- {record}")
    
except Exception as e:
    print(f"出错: {e}")
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
