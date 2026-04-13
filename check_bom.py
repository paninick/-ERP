import pymysql

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # 查询样衣BOM数据量
    cursor.execute("SELECT COUNT(*) FROM t_erp_bom;")
    count = cursor.fetchone()[0]
    print(f"测试库t_erp_bom表（样衣BOM）当前共有 {count} 条记录")
    
    # 查询前5条记录看看
    print("\n前5条记录:")
    cursor.execute("SELECT id, tech_no, sample_no, progress_status FROM t_erp_bom LIMIT 5;")
    records = cursor.fetchall()
    for record in records:
        print(f"- {record}")
    
except Exception as e:
    print(f"出错: {e}")
finally:
    if 'cursor' in locals():
        cursor.close()
    if 'conn' in locals():
        conn.close()
