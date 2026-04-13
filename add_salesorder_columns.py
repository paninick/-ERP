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
ALTER TABLE t_erp_sales_order ADD COLUMN customer_name VARCHAR(255) NULL DEFAULT NULL COMMENT '客户名称' AFTER sales_type;
ALTER TABLE t_erp_sales_order ADD COLUMN bulk_order_no VARCHAR(255) NULL DEFAULT NULL COMMENT '大货款号' AFTER customer_name;
ALTER TABLE t_erp_sales_order ADD COLUMN sample_style_no VARCHAR(255) NULL DEFAULT NULL COMMENT '打样款号' AFTER bulk_order_no;
ALTER TABLE t_erp_sales_order ADD COLUMN style_category VARCHAR(255) NULL DEFAULT NULL COMMENT '款式品类' AFTER sample_style_no;
ALTER TABLE t_erp_sales_order ADD COLUMN sample_no VARCHAR(255) NULL DEFAULT NULL COMMENT '打样编号' AFTER style_category;
ALTER TABLE t_erp_sales_order ADD COLUMN sales_name VARCHAR(255) NULL DEFAULT NULL COMMENT '业务员' AFTER sample_no;
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
            print(f"已执行 {i+1}/{len(statements)}: {statement[:80]}...")
        except Exception as e:
            print(f"警告: {e}")
    
    conn.commit()
    print("\n所有字段添加完成!")
    
    # 查看当前表结构
    cursor.execute("DESCRIBE t_erp_sales_order")
    result = cursor.fetchall()
    print("\n当前表结构:")
    for row in result:
        print(f"  {row[0]:<20} {row[1]:<20}")
    
    cursor.close()
    conn.close()
    print("\n完成!")
    
except Exception as e:
    print(f"错误: {e}")
