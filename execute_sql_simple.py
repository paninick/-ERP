import pymysql
from db_config import get_connection

def execute_sql_file_simple(sql_file):
    # 连接数据库
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 先清空表
        print("正在清空现有数据...")
        cursor.execute("DELETE FROM t_erp_sample_tech")
        conn.commit()
        print(f"已清空 {cursor.rowcount} 条数据")
        
        # 读取SQL文件
        print(f"正在读取SQL文件: {sql_file}")
        with open(sql_file, 'r', encoding='utf-8') as f:
            lines = f.readlines()
        
        # 找到INSERT语句开始的位置
        insert_start = False
        success_count = 0
        fail_count = 0
        current_sql = []
        
        for line in lines:
            line = line.strip()
            
            # 跳过注释和空行
            if line.startswith('--') or line.startswith('/*') or not line:
                continue
                
            # 找到INSERT语句
            if 'INSERT INTO' in line:
                insert_start = True
            
            if insert_start:
                current_sql.append(line)
                
                # 如果这行以分号结尾，执行这条SQL
                if line.endswith(';'):
                    sql = ' '.join(current_sql)
                    try:
                        cursor.execute(sql)
                        success_count += 1
                        
                        if success_count % 1000 == 0:
                            conn.commit()
                            print(f"已导入 {success_count} 条数据...")
                            
                    except Exception as e:
                        fail_count += 1
                        if fail_count <= 10:
                            print(f"第 {success_count + fail_count} 条SQL执行失败: {e}")
                    
                    current_sql = []
        
        # 提交剩余数据
        conn.commit()
        
        print(f"\n导入完成！")
        print(f"成功: {success_count} 条")
        print(f"失败: {fail_count} 条")
        
    except Exception as e:
        conn.rollback()
        print(f"导入出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    sql_file = r"d:\erp\import_sample_tech_data.sql"
    execute_sql_file_simple(sql_file)
