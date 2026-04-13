import pymysql
from db_config import get_connection

def verify_empty():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 验证各表数据 ===")
        
        tables = [
            ('大货核版', 't_erp_check'),
            ('样衣BOM', 't_erp_bom'),
            ('工艺指示书', 't_erp_sample_tech'),
            ('打样通知', 't_erp_sample_notice')
        ]
        
        for name, table in tables:
            cursor.execute(f"SELECT COUNT(*) FROM {table}")
            count = cursor.fetchone()[0]
            print(f"{name}: {count} 条")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    verify_empty()
