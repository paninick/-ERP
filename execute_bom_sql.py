
import pymysql
from db_config import get_connection

def execute_sql():
    conn = None
    try:
        conn = get_connection()
        cursor = conn.cursor()
        
        # 读取SQL文件
        with open('d:\\erp\\alter_bom_table.sql', 'r', encoding='utf-8') as f:
            sql_content = f.read()
        
        # 分割SQL语句并执行
        sql_statements = [stmt.strip() for stmt in sql_content.split(';') if stmt.strip()]
        
        print("=" * 80)
        print("开始执行SQL")
        print("=" * 80)
        
        for i, sql in enumerate(sql_statements):
            if sql:
                try:
                    print(f"\n执行SQL {i+1}:")
                    if len(sql) &gt; 200:
                        print(sql[:200] + "...")
                    else:
                        print(sql)
                    cursor.execute(sql)
                    conn.commit()
                    print("执行成功")
                except Exception as e:
                    print(f"执行失败: {e}")
                    # 继续执行下一个SQL
        
        print("\n" + "=" * 80)
        print("SQL执行完成")
        print("=" * 80)
        
        # 验证表结构
        print("\n验证表结构:")
        cursor.execute("DESCRIBE t_erp_bom")
        for row in cursor.fetchall():
            print(row)
            
    except Exception as e:
        print(f"错误: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    execute_sql()

