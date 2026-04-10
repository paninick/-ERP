
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def fix_data():
    """修复字段数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("修复字段数据")
        print("="*100)
        
        # 1. 把 style_category 的数据复制到 style_type
        print("\n【1】复制 style_category 数据到 style_type...")
        sql = "UPDATE t_erp_sample_notice SET style_type = style_category WHERE style_category IS NOT NULL AND style_category != ''"
        cursor.execute(sql)
        conn.commit()
        print(f"  影响行数: