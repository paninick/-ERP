import subprocess, sys, os

MYSQL = r"C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
DB_NAME = "ry_vue"

def run_sql(sql):
    proc = subprocess.Popen(
        [MYSQL, "-u", "root", DB_NAME],
        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE,
        encoding='utf-8', text=True
    )
    out, err = proc.communicate(input=sql)
    if proc.returncode != 0 and 'error' in err.lower():
        print(f"SQL Error: {err}")
    return out

print("=== 1. 检查ERP/Flowable相关菜单的component路径 ===")
result = run_sql("""
SELECT menu_id, menu_name, path, component, parent_id 
FROM sys_menu 
WHERE path LIKE 'erp/%' OR path LIKE 'flowable/%' OR path LIKE 'common/%'
ORDER BY parent_id, order_num;
""")
print(result)

print("\n=== 2. 检查需要的数据表是否存在 ===")
tables_to_check = [
    'auxiliary_material', 'corp_contacts', 'corp_invoice',
    'customer', 'customer_template', 'customer_template_material', 'customer_template_size',
    'main_material', 'produce_plan', 'produce_plan_clothes', 'produce_plan_material',
    'purchase', 'purchase_item',
    'sales_order', 'sales_order_item', 'sales_order_material', 'sales_order_pack',
    'sample_notice', 'sample_notice_detail', 'sample_notice_file', 'sample_notice_his',
    'sample_tech', 'sample_tech_cost', 'sample_tech_material', 'sample_tech_size',
    'stock_in', 'stock_in_item', 'stock_log', 'stock_out', 'stock_out_item',
    'supplier', 't_erp_sample_notice_material',
    'warehouse', 'warehouse_area', 'warehouse_location',
    'sys_dict',  # genCode common模块的表
]
for t in tables_to_check:
    r = run_sql(f"SELECT COUNT(*) FROM information_schema.tables WHERE table_schema='{DB_NAME}' AND table_name='{t}';")
    exists = '1' in r or '1\r' in r
    status = "✅" if exists else "❌ MISSING"
    print(f"  {status} {t}")

print("\n=== 3. 检查warehouse相关菜单(路径可能重复) ===")
result = run_sql("""
SELECT menu_id, menu_name, path, component, parent_id, order_num
FROM sys_menu 
WHERE (path LIKE '%warehouse%' OR component LIKE '%warehouse%')
ORDER BY menu_id;
""")
print(result)
