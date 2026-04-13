import pymysql
from db_config import get_connection

def check_data():
    conn = get_connection()
    cursor = conn.cursor(pymysql.cursors.DictCursor)
    
    try:
        # 查询前10条数据
        print("=== 查询前10条大货核版数据 ===")
        cursor.execute("SELECT check_id, check_no, audit_status, progress_status, customer_name, sample_type, style_type, sample_category_type, style_no, bulk_order_no, sales_name, due_date, tech_no, sample_no FROM t_erp_check LIMIT 10")
        rows = cursor.fetchall()
        
        for idx, row in enumerate(rows, 1):
            print(f"\n记录 {idx}:")
            print(f"  check_id: {row['check_id']}")
            print(f"  check_no: {row['check_no']}")
            print(f"  audit_status: {row['audit_status']}")
            print(f"  progress_status: {row['progress_status']}")
            print(f"  customer_name: {row['customer_name']}")
            print(f"  sample_type: {row['sample_type']}")
            print(f"  style_type: {row['style_type']}")
            print(f"  sample_category_type: {row['sample_category_type']}")
            print(f"  style_no: {row['style_no']}")
            print(f"  bulk_order_no: {row['bulk_order_no']}")
            print(f"  sales_name: {row['sales_name']}")
            print(f"  due_date: {row['due_date']}")
            print(f"  tech_no: {row['tech_no']}")
            print(f"  sample_no: {row['sample_no']}")
        
        # 查询总记录数
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_check")
        total = cursor.fetchone()
        print(f"\n总记录数: {total['total']}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    check_data()
