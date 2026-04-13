import pymysql
from db_config import get_connection

def check_data_counts():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 检查各表数据数量 ===")
        
        # 检查大货核版
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_check")
        check_count = cursor.fetchone()[0]
        print(f"大货核版 (t_erp_check): {check_count} 条")
        
        # 检查样衣BOM
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_bom")
        bom_count = cursor.fetchone()[0]
        print(f"样衣BOM (t_erp_bom): {bom_count} 条")
        
        # 检查工艺指示书
        cursor.execute("SELECT COUNT(*) as total FROM t_erp_sample_tech")
        tech_count = cursor.fetchone()[0]
        print(f"工艺指示书 (t_erp_sample_tech): {tech_count} 条")
        
        print("\n=== 检查大货核版是否有重复数据 ===")
        cursor.execute("""
            SELECT check_no, COUNT(*) as cnt 
            FROM t_erp_check 
            GROUP BY check_no 
            HAVING cnt > 1 
            LIMIT 10
        """)
        check_duplicates = cursor.fetchall()
        if check_duplicates:
            print("发现重复数据:")
            for row in check_duplicates:
                print(f"  {row[0]}: {row[1]} 次")
        else:
            print("未发现重复数据")
        
        print("\n=== 检查样衣BOM是否有重复数据 ===")
        cursor.execute("""
            SELECT bom_code, COUNT(*) as cnt 
            FROM t_erp_bom 
            GROUP BY bom_code 
            HAVING cnt > 1 
            LIMIT 10
        """)
        bom_duplicates = cursor.fetchall()
        if bom_duplicates:
            print("发现重复数据:")
            for row in bom_duplicates:
                print(f"  {row[0]}: {row[1]} 次")
        else:
            print("未发现重复数据")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    check_data_counts()
