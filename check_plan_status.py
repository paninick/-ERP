import pymysql
from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

# 查询前10条数据，看生产状态和审批状态字段
print("=== 前10条生产计划数据 ===\n")
cursor.execute("""
    SELECT id, plan_no, produce_status, audit_status
    FROM t_erp_produce_plan 
    LIMIT 10
""")

rows = cursor.fetchall()
for row in rows:
    print(f"ID: {row[0]}, 计划编号: {row[1]}, 生产状态: '{row[2]}', 审批状态: '{row[3]}'")

# 统计生产状态的分布
print("\n=== 生产状态统计 ===\n")
cursor.execute("""
    SELECT produce_status, COUNT(*) as cnt 
    FROM t_erp_produce_plan 
    GROUP BY produce_status
""")
rows = cursor.fetchall()
for row in rows:
    print(f"生产状态: '{row[0]}' - {row[1]} 条")

# 统计审批状态的分布
print("\n=== 审批状态统计 ===\n")
cursor.execute("""
    SELECT audit_status, COUNT(*) as cnt 
    FROM t_erp_produce_plan 
    GROUP BY audit_status
""")
rows = cursor.fetchall()
for row in rows:
    print(f"审批状态: '{row[0]}' - {row[1]} 条")

cursor.close()
conn.close()
