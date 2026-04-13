import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

print("鏌ヨ t_erp_check 琛ㄥ墠5鏉℃暟鎹紝妫€鏌ュ叧閿瓧娈碉細\n")

# 鏌ヨ鍓?鏉″畬鏁存暟鎹?cursor.execute("SELECT check_id, audit_status, progress_status, customer_name, sample_type, style_type, sample_category_type, style_no, bulk_order_no, sales_name, due_date, tech_no, sample_no FROM t_erp_check LIMIT 5")
rows = cursor.fetchall()

print("check_id | audit_status | progress_status | customer_name | sample_type | style_type | sample_category_type | style_no | bulk_order_no | sales_name | due_date | tech_no | sample_no")
print("-" * 200)

for row in rows:
    print(row)

print("\n缁熻绌哄€兼儏鍐碉細\n")

fields_to_check = [
    'audit_status',
    'progress_status', 
    'customer_name',
    'sample_type',
    'style_type',
    'sample_category_type',
    'style_no',
    'bulk_order_no', 
    'sales_name',
    'due_date',
    'tech_no',
    'sample_no'
]

for field in fields_to_check:
    cursor.execute(f"SELECT COUNT(*) FROM t_erp_check WHERE {field} IS NULL OR {field} = ''")
    null_count = cursor.fetchone()[0]
    cursor.execute("SELECT COUNT(*) FROM t_erp_check")
    total_count = cursor.fetchone()[0]
    print(f"{field}: {null_count}/{total_count} 鏉′负绌?)

cursor.close()
conn.close()

