import pymysql
from db_config import get_connection

def final_verify():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print("=== 大货核版数据最终验证 ===")
        
        # 总数量
        cursor.execute("SELECT COUNT(*) FROM t_erp_check")
        total = cursor.fetchone()[0]
        print(f"总记录数: {total}")
        
        # 检查各字段分布
        print("\n各字段统计:")
        
        cursor.execute("SELECT audit_status, COUNT(*) FROM t_erp_check GROUP BY audit_status")
        print("\n审批状态分布:")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} 条")
        
        cursor.execute("SELECT sample_type, COUNT(*) FROM t_erp_check GROUP BY sample_type")
        print("\n打样类型分布:")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} 条")
        
        cursor.execute("SELECT style_type, COUNT(*) FROM t_erp_check GROUP BY style_type")
        print("\n样品款式分布:")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} 条")
        
        cursor.execute("SELECT sample_category_type, COUNT(*) FROM t_erp_check GROUP BY sample_category_type")
        print("\n样品类型分布:")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} 条")
        
        # 查看前5条完整数据
        print("\n前5条完整数据:")
        cursor.execute("SELECT * FROM t_erp_check LIMIT 5")
        columns = [desc[0] for desc in cursor.description]
        
        for i, row in enumerate(cursor.fetchall()):
            print(f"\n记录 {i+1}:")
            for col, val in zip(columns, row):
                if val is not None:
                    print(f"  {col}: {val}")
        
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    final_verify()
