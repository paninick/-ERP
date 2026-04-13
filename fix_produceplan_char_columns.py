import pymysql

from db_config import get_connection

# 淇敼char(1)瀛楁涓簐archar(255)
alter_sql = """
ALTER TABLE t_erp_produce_plan MODIFY COLUMN produce_type VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE t_erp_produce_plan MODIFY COLUMN plan_status VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE t_erp_produce_plan MODIFY COLUMN audit_status VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE t_erp_produce_plan MODIFY COLUMN pre_produce_type VARCHAR(255) NULL DEFAULT NULL;
ALTER TABLE t_erp_produce_plan MODIFY COLUMN post_produce_type VARCHAR(255) NULL DEFAULT NULL;
"""

print(f"姝ｅ湪杩炴帴鏁版嵁搴?{config['database']}...")

try:
    conn = get_connection()
    cursor = conn.cursor()
    print("杩炴帴鎴愬姛!")
    
    statements = [s.strip() for s in alter_sql.split(';') if s.strip()]
    
    print(f"鎬诲叡 {len(statements)} 鏉LTER璇彞闇€瑕佹墽琛?)
    
    for i, statement in enumerate(statements):
        try:
            cursor.execute(statement)
            print(f"宸叉墽琛?{i+1}/{len(statements)}: {statement}")
        except Exception as e:
            print(f"璀﹀憡: {e}")
    
    conn.commit()
    print("\n鎵€鏈夊瓧娈典慨鏀瑰畬鎴?")
    
    cursor.close()
    conn.close()
    print("\n瀹屾垚!")
    
except Exception as e:
    print(f"閿欒: {e}")

