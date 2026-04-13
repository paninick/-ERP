import pymysql

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # 查询主要表的数据量
    tables = [
        ('t_erp_sample_notice', '打样通知'),
        ('t_erp_bom', '样衣BOM'),
        ('t_erp_produce_plan', '生产计划'),
        ('t_erp_sales_order', '销售订单'),
        ('t_erp_bulk_check', '大货核版'),
        ('t_erp_tech', '工艺指示书')
    ]
    
    print("各表数据量统计：")
    print("-" * 50)
    for table_name, table_desc in tables:
        try:
            cursor.execute(f"SELECT COUNT(*) FROM {table_name};")
            count = cursor.fetchone()[0]
            print(f"{table_desc} ({table_name}): {count} 条")
        except Exception as e:
            print(f"{table_desc} ({table_name}): 查询出错 - {e}")
    
except Exception as e:
    print(f"出错: {e}")
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
