import pymysql
from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

# 更新所有生产计划的审批状态为"未审批"
update_sql = """
    UPDATE t_erp_produce_plan 
    SET audit_status = '未审批'
    WHERE audit_status IS NULL OR audit_status = ''
"""

cursor.execute(update_sql)
affected_rows = cursor.rowcount

conn.commit()

print(f"✅ 成功更新 {affected_rows} 条数据的审批状态为'未审批'")

# 验证结果
print("\n=== 更新后的审批状态统计 ===\n")
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
