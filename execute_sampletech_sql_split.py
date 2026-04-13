import pymysql
from db_config import get_connection

# 读取SQL文件
sql_file = r'd:\erp\import_sampletech_data_split.sql'

print(f"正在连接数据库...")

try:
    # 连接数据库
    conn = get_connection()
    cursor = conn.cursor()
    print("连接成功!")
    
    # 读取SQL文件内容
    print(f"正在读取SQL文件: {sql_file}")
    with open(sql_file, 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    # 按分号分割成多个语句
    statements = sql_content.split(';')
    
    total_statements = len([s for s in statements if s.strip()])
    print(f"总共 {total_statements} 条SQL语句需要执行")
    
    # 执行每个语句
    executed = 0
    for statement in statements:
        statement = statement.strip()
        if statement:
            try:
                cursor.execute(statement)
                executed += 1
                if executed % 10 == 0:
                    print(f"已执行 {executed}/{total_statements}...")
            except Exception as e:
                print(f"警告: 执行语句时出错: {e}")
                print(f"语句: {statement[:100]}...")
    
    # 提交事务
    conn.commit()
    print(f"\n执行完成! 成功执行 {executed} 条语句")
    
    # 查询插入了多少条数据
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_tech")
    count = cursor.fetchone()[0]
    print(f"当前 t_erp_sample_tech 表中共有 {count} 条记录")
    
    # 抽查几条数据看看拆分效果
    cursor.execute("SELECT audit_status, progress_status, customer_name, sample_type_display, style_type, sample_category_type, style_no, bulk_order_no, sales_name, due_date FROM t_erp_sample_tech LIMIT 10")
    print("\n抽查前10条数据看看拆分效果:")
    print("审批状态\t进行状态\t客户\t\t打样类型\t样品款式\t样品类型\t款号\t\t大货款号\t\t业务员\t要求交期")
    print("-" * 140)
    for row in cursor.fetchall():
        fields = [str(f) if f is not None else '' for f in row]
        print("\t".join(fields))
    
    cursor.close()
    conn.close()
    
except Exception as e:
    print(f"错误: {e}")
