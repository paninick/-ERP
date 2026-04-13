import pymysql

config = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

# 添加缺失字段的SQL
alter_sql = """
ALTER TABLE t_erp_sample_tech ADD COLUMN audit_status VARCHAR(255) NULL DEFAULT NULL COMMENT '审批状态' AFTER id;
ALTER TABLE t_erp_sample_tech ADD COLUMN progress_status VARCHAR(255) NULL DEFAULT NULL COMMENT '进行状态' AFTER audit_status;
ALTER TABLE t_erp_sample_tech ADD COLUMN customer_name VARCHAR(255) NULL DEFAULT NULL COMMENT '客户名称' AFTER progress_status;
ALTER TABLE t_erp_sample_tech ADD COLUMN sample_type_display VARCHAR(255) NULL DEFAULT NULL COMMENT '打样类型' AFTER customer_name;
ALTER TABLE t_erp_sample_tech ADD COLUMN style_type VARCHAR(255) NULL DEFAULT NULL COMMENT '样品款式' AFTER sample_type_display;
ALTER TABLE t_erp_sample_tech ADD COLUMN sample_category_type VARCHAR(255) NULL DEFAULT NULL COMMENT '样品类型' AFTER style_type;
ALTER TABLE t_erp_sample_tech ADD COLUMN style_no VARCHAR(255) NULL DEFAULT NULL COMMENT '款号' AFTER sample_category_type;
ALTER TABLE t_erp_sample_tech ADD COLUMN bulk_order_no VARCHAR(255) NULL DEFAULT NULL COMMENT '大货款号' AFTER style_no;
ALTER TABLE t_erp_sample_tech ADD COLUMN sales_name VARCHAR(255) NULL DEFAULT NULL COMMENT '业务员' AFTER bulk_order_no;
"""

print(f"正在连接数据库 {config['database']}...")

try:
    conn = pymysql.connect(**config)
    cursor = conn.cursor()
    print("连接成功!")
    
    # 分割成多条语句执行
    statements = [s.strip() for s in alter_sql.split(';') if s.strip()]
    
    print(f"总共 {len(statements)} 条ALTER语句需要执行")
    
    for i, statement in enumerate(statements):
        try:
            cursor.execute(statement)
            print(f"已执行 {i+1}/{len(statements)}: {statement[:60]}...")
        except Exception as e:
            print(f"警告: {e}")
    
    conn.commit()
    print("\n所有字段添加完成!")
    
    # 查看表结构
    cursor.execute("DESCRIBE t_erp_sample_tech")
    result = cursor.fetchall()
    print("\n当前表结构:")
    for row in result:
        print(f"  {row[0]:<30} {row[1]:<20} {row[2]}")
    
    cursor.close()
    conn.close()
    
except Exception as e:
    print(f"错误: {e}")
