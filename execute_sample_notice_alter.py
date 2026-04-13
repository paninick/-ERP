import pymysql
from db_config import get_connection

def execute_alter_sql():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 更新打样通知表结构 ===")
        
        # 先检查字段是否已存在
        cursor.execute("SHOW COLUMNS FROM t_erp_sample_notice LIKE 'sales_id'")
        if cursor.fetchone():
            print("sales_id字段已存在，跳过添加")
        else:
            sql = "ALTER TABLE t_erp_sample_notice ADD COLUMN sales_id BIGINT COMMENT '业务员ID' AFTER audit_by_nick_name"
            cursor.execute(sql)
            print("已添加sales_id字段")
        
        cursor.execute("SHOW COLUMNS FROM t_erp_sample_notice LIKE 'sales_name'")
        if cursor.fetchone():
            print("sales_name字段已存在，跳过添加")
        else:
            sql = "ALTER TABLE t_erp_sample_notice ADD COLUMN sales_name VARCHAR(100) COMMENT '业务员名称' AFTER sales_id"
            cursor.execute(sql)
            print("已添加sales_name字段")
        
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
    execute_alter_sql()
