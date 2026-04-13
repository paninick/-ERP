import pymysql

from db_config import get_connection

conn = get_connection()
cursor = conn.cursor()

tables = [
    ('t_erp_sample_notice', '鎵撴牱閫氱煡'),
    ('t_erp_bom', '鏍疯。BOM'),
    ('t_erp_check', '澶ц揣鏍哥増'),
]

print("鍚勬ā鍧楁暟鎹暟閲忕粺璁?\n")
for table, name in tables:
    cursor.execute(f"SELECT COUNT(*) FROM {table}")
    count = cursor.fetchone()[0]
    print(f"{name}: {count} 鏉?)

cursor.close()
conn.close()

