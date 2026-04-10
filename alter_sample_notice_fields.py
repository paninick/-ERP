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
print("修改打样通知表字段类型")
print("=" * 60)

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print("\n修改字段：sample_category_type")
    cursor.execute("ALTER TABLE t_erp_sample_notice MODIFY COLUMN sample_category_type VARCHAR(64) COMMENT '样品种类'")
    print("    成功")
    
    print("\n修改字段：style_type")
    cursor.execute("ALTER TABLE t_erp_sample_notice MODIFY COLUMN style_type VARCHAR(64) COMMENT '样品款式'")
    print("    成功")
    
    print("\n修改字段：sample_type")
    cursor.execute("ALTER TABLE t_erp_sample_notice MODIFY COLUMN sample_type VARCHAR(64) COMMENT '打样类型'")
    print("    成功")
    
    print("\n修改字段：emergency_type")
    cursor.execute("ALTER TABLE t_erp_sample_notice MODIFY COLUMN emergency_type VARCHAR(64) COMMENT '紧急程度'")
    print("    成功")
    
    print("\n修改字段：audit_status")
    cursor.execute("ALTER TABLE t_erp_sample_notice MODIFY COLUMN audit_status VARCHAR(64) COMMENT '审批状态'")
    print("    成功")
    
    print("\n验证修改后的表结构:")
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
