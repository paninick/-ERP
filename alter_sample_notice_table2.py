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
print("修改打样通知表结构")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n[1/7] 查询当前表结构:")
    cursor.execute("DESCRIBE t_erp_sample_notice")
    columns = cursor.fetchall()
    for col in columns:
        print(f"    {col[0]} - {col[1]}")
    
    print("\n[2/7] 添加字段：打样编号")
    try:
        cursor.execute("ALTER TABLE t_erp_sample_notice ADD COLUMN sample_no VARCHAR(64) COMMENT '打样编号' AFTER id")
        print("    成功")
    except Exception as e:
        print(f"    已存在或失败: {e}")
    
    print("\n[3/7] 添加字段：款式大类")
    try:
        cursor.execute("ALTER TABLE t_erp_sample_notice ADD COLUMN style_category VARCHAR(64) COMMENT '款式大类' AFTER customer_id")
        print("    成功")
    except Exception as e:
        print(f"    已存在或失败: {e}")
    
    print("\n[4/7] 添加字段：款式小类")
    try:
        cursor.execute("ALTER TABLE t_erp_sample_notice ADD COLUMN style_sub_category VARCHAR(64) COMMENT '款式小类' AFTER style_category")
        print("    成功")
    except Exception as e:
        print(f"    已存在或失败: {e}")
    
    print("\n[5/7] 添加字段：流程实例ID")
    try:
        cursor.execute("ALTER TABLE t_erp_sample_notice ADD COLUMN process_instance_id VARCHAR(64) COMMENT '流程实例ID' AFTER audit_time")
        print("    成功")
    except Exception as e:
        print(f"    已存在或失败: {e}")
    
    print("\n[6/7] 添加字段：大货款号")
    try:
        cursor.execute("ALTER TABLE t_erp_sample_notice ADD COLUMN bulk_order_no VARCHAR(64) COMMENT '大货款号' AFTER process_instance_id")
        print("    成功")
    except Exception as e:
        print(f"    已存在或失败: {e}")
    
    print("\n[7/7] 查询修改后的表结构:")
    cursor.execute("DESCRIBE t_erp_sample_notice")
    columns = cursor.fetchall()
    for col in columns:
        print(f"    {col[0]} - {col[1]}")
    
    conn.commit()
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("表结构修改完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
