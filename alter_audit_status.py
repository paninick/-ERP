import pymysql

config = {
    'host': 'localhost',
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

print(f"正在连接数据库 {config['database']}...")

try:
    conn = pymysql.connect(**config)
    cursor = conn.cursor()
    print("连接成功!")
    
    # 修改audit_status字段长度
    sql = "ALTER TABLE t_erp_sample_tech MODIFY COLUMN audit_status VARCHAR(255) NULL DEFAULT NULL;"
    cursor.execute(sql)
    print("已修改 audit_status 字段长度为 VARCHAR(255)")
    
    conn.commit()
    cursor.close()
    conn.close()
    print("完成!")
    
except Exception as e:
    print(f"错误: {e}")
