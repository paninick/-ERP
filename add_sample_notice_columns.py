import pymysql

# 数据库连接配置
DB_CONFIG = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def add_missing_columns():
    conn = pymysql.connect(**DB_CONFIG)
    cursor = conn.cursor()
    
    try:
        print("=== 为打样通知表添加缺失字段 ===")
        
        # 添加 customer_name
        cursor.execute("SHOW COLUMNS FROM t_erp_sample_notice LIKE 'customer_name'")
        if not cursor.fetchone():
            sql = "ALTER TABLE t_erp_sample_notice ADD COLUMN customer_name VARCHAR(255) COMMENT '客户名称' AFTER customer_id"
            cursor.execute(sql)
            print("已添加 customer_name 字段")
        else:
            print("customer_name 字段已存在")
        
        # 添加 audit_by_nick_name
        cursor.execute("SHOW COLUMNS FROM t_erp_sample_notice LIKE 'audit_by_nick_name'")
        if not cursor.fetchone():
            sql = "ALTER TABLE t_erp_sample_notice ADD COLUMN audit_by_nick_name VARCHAR(255) COMMENT '审批人昵称' AFTER audit_by"
            cursor.execute(sql)
            print("已添加 audit_by_nick_name 字段")
        else:
            print("audit_by_nick_name 字段已存在")
        
        # 添加 sales_id
        cursor.execute("SHOW COLUMNS FROM t_erp_sample_notice LIKE 'sales_id'")
        if not cursor.fetchone():
            sql = "ALTER TABLE t_erp_sample_notice ADD COLUMN sales_id BIGINT COMMENT '业务员ID' AFTER audit_by_nick_name"
            cursor.execute(sql)
            print("已添加 sales_id 字段")
        else:
            print("sales_id 字段已存在")
        
        # 添加 sales_name
        cursor.execute("SHOW COLUMNS FROM t_erp_sample_notice LIKE 'sales_name'")
        if not cursor.fetchone():
            sql = "ALTER TABLE t_erp_sample_notice ADD COLUMN sales_name VARCHAR(100) COMMENT '业务员名称' AFTER sales_id"
            cursor.execute(sql)
            print("已添加 sales_name 字段")
        else:
            print("sales_name 字段已存在")
        
        conn.commit()
        
        print("\n查看最终表结构:")
        cursor.execute("DESC t_erp_sample_notice")
        columns = cursor.fetchall()
        for col in columns:
            print(f"  {col[0]}: {col[1]}")
        
    except Exception as e:
        conn.rollback()
        print(f"执行出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    add_missing_columns()
