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

print("=" * 60)
print("检查数据库中打样通知数据")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/2] 查询前10条数据:")
    cursor.execute("SELECT id, sample_no, sample_type, customer_id, style_category, style_sub_category, sample_category_type, style_code, bulk_order_no, create_by, create_time FROM t_erp_sample_notice LIMIT 10")
    rows = cursor.fetchall()
    for row in rows:
        print(f"  ID:{row[0]}, 打样编号:{row[1]}, 打样类型:{row[2]}, 客户ID:{row[3]}, 款式大类:{row[4]}, 款式小类:{row[5]}, 样品种类:{row[6]}, 款号:{row[7]}, 大货款号:{row[8]}")
    
    print("\n[2/2] 查询总记录数:")
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice")
    total = cursor.fetchone()[0]
    print(f"    总记录数: {total}")
    
    print("\n检查字段是否有数据:")
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice WHERE sample_type IS NOT NULL AND sample_type != ''")
    cnt = cursor.fetchone()[0]
    print(f"    打样类型有数据: {cnt}")
    
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice WHERE sample_no IS NOT NULL AND sample_no != ''")
    cnt = cursor.fetchone()[0]
    print(f"    打样编号有数据: {cnt}")
    
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice WHERE style_code IS NOT NULL AND style_code != ''")
    cnt = cursor.fetchone()[0]
    print(f"    款号有数据: {cnt}")
    
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice WHERE bulk_order_no IS NOT NULL AND bulk_order_no != ''")
    cnt = cursor.fetchone()[0]
    print(f"    大货款号有数据: {cnt}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("检查完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
