import pymysql
from db_config import get_connection

# 读取SQL文件
sql_file = r'd:\erp\import_produceplan_data.sql'

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
    cursor.execute("SELECT COUNT(*) FROM t_erp_produce_plan")
    count = cursor.fetchone()[0]
    print(f"当前 t_erp_produce_plan 表中共有 {count} 条记录")
    
    # 抽查几条数据
    cursor.execute("SELECT produce_status, type, plan_no, bulk_order_no, sample_style_no, customer_name, style_category, sales_name FROM t_erp_produce_plan LIMIT 10")
    print("\n抽查前10条数据:")
    print("生产状态\t类型\t生产计划编号\t大货款号\t打样款号\t客户名称\t款式品类\t业务员")
    print("-" * 100)
    for row in cursor.fetchall():
        fields = [str(f) if f is not None else '' for f in row]
        print("\t".join(fields))
    
    cursor.close()
    conn.close()
    
except Exception as e:
    print(f"错误: {e}")
