import pymysql

from db_config import get_connection

print(f"姝ｅ湪杩炴帴鏁版嵁搴?{config['database']}...")

try:
    conn = get_connection()
    cursor = conn.cursor()
    print("杩炴帴鎴愬姛!")
    
    # 淇敼order_status瀛楁绫诲瀷浠巄igint鏀逛负varchar
    sql = "ALTER TABLE t_erp_sales_order MODIFY COLUMN order_status VARCHAR(255) NULL DEFAULT NULL;"
    cursor.execute(sql)
    print("宸蹭慨鏀?order_status 瀛楁绫诲瀷涓?VARCHAR(255)")
    
    conn.commit()
    cursor.close()
    conn.close()
    print("\n瀹屾垚!")
    
except Exception as e:
    print(f"閿欒: {e}")

