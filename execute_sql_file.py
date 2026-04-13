import pymysql
from db_config import get_connection

def execute_sql_file(sql_file):
    # 连接数据库
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 读取SQL文件
        print(f"正在读取SQL文件: {sql_file}")
        with open(sql_file, 'r', encoding='utf-8') as f:
            sql_content = f.read()
        
        # 分割SQL语句（按分号）
        sql_statements = []
        current_stmt = []
        in_comment = False
        in_string = False
        string_char = ''
        
        for char in sql_content:
            if char == '--' and not in_string and not in_comment:
                # 跳过单行注释
                while char != '\n' and char:
                    char = f.read(1) if hasattr(f, 'read') else ''
                continue
                
            if char == '/*' and not in_string and not in_comment:
                in_comment = True
                continue
                
            if char == '*/' and in_comment:
                in_comment = False
                continue
                
            if (char == "'" or char == '"') and not in_comment:
                if not in_string:
                    in_string = True
                    string_char = char
                elif char == string_char:
                    in_string = False
            
            if char == ';' and not in_string and not in_comment:
                stmt = ''.join(current_stmt).strip()
                if stmt:
                    sql_statements.append(stmt)
                current_stmt = []
            else:
                current_stmt.append(char)
        
        # 添加最后一条语句
        stmt = ''.join(current_stmt).strip()
        if stmt:
            sql_statements.append(stmt)
        
        print(f"找到 {len(sql_statements)} 条SQL语句")
        
        # 执行SQL语句
        success_count = 0
        fail_count = 0
        
        for i, sql in enumerate(sql_statements):
            try:
                cursor.execute(sql)
                success_count += 1
                
                if success_count % 1000 == 0:
                    conn.commit()
                    print(f"已执行 {success_count} 条SQL...")
                    
            except Exception as e:
                fail_count += 1
                if fail_count <= 10:
                    print(f"第 {i+1} 条SQL执行失败: {e}")
        
        # 提交
        conn.commit()
        
        print(f"\n执行完成！")
        print(f"成功: {success_count} 条")
        print(f"失败: {fail_count} 条")
        
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
    sql_file = r"d:\erp\import_sample_tech_data.sql"
    execute_sql_file(sql_file)
