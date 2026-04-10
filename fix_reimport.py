
import pandas as pd
import pymysql
from datetime import datetime

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def delete_data():
    """删除现有数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        tables = [
            't_erp_sample_notice',
            't_erp_sample_notice_detail',
            't_erp_sample_notice_file',
            't_erp_sample_notice_his',
            't_erp_sample_notice_material'
        ]
        
        print("删除现有数据...")
        for table in reversed(tables):
            cursor.execute(f"DELETE FROM {table}")
            cursor.execute(f"ALTER TABLE {table} AUTO_INCREMENT = 1")
            print(f"  已清空 {table}")
        
        conn.commit()
        print("删除完成！")
        
    except Exception as e:
        print(f"删除失败: {e}")
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

def import_data():
    """重新导入数据"""
    print("="*100)
    print("开始重新导入打样通知数据")
    print("="*100)
    
    # 1. 读取Excel
    print("\n【1】读取Excel数据...")
    df = pd.read_excel(excel_path)
    print(f"共 {len(df)} 条数据")
    print(f"\nExcel列名: {df.columns.tolist()}")
    
    # 2. 导入数据
    print("\n【2】开始导入数据库...")
    
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        success_count = 0
        error_count = 0
        
        for idx, row in df.iterrows():
            try:
                # 准备数据 - 确保字段对应正确
                data = {
                    'sample_no': str(row['打样编号']) if pd.notna(row['打样编号']) else None,
                    'sample_type': str(int(row['打样类型'])) if pd.notna(row['打样类型']) else None,
                    'customer_id': int(row['客户id']) if pd.notna(row['客户id']) else None,
                    'style_category': str(int(row['款式大类'])) if pd.notna(row['款式大类']) else None,
                    'style_sub_category': str(row['款式小类']) if pd.notna(row['款式小类']) else None,
                    'sample_category_type': str(int(row['样品种类'])) if pd.notna(row['样品种类']) else None,
                    'style_code': str(row['款号']) if pd.notna(row['款号']) else None,
                    'due_date': row['要求交期'] if pd.notna(row['要求交期']) else None,
                    'emergency_type': str(row['紧急程度']) if pd.notna(row['紧急程度']) else None,
                    'audit_status': str(int(row['审批状态'])) if pd.notna(row['审批状态']) else None,
                    'audit_by': str(int(row['审批人id'])) if pd.notna(row['审批人id']) else None,
                    'audit_time': row['审批时间'] if pd.notna(row['审批时间']) else None,
                    'process_instance_id': str(row['流程实例ID']) if pd.notna(row['流程实例ID']) else None,
                    'bulk_order_no': str(row['大货款号']) if pd.notna(row['大货款号']) else None,
                    'create_by': 'admin',
                    'create_time': datetime.now(),
                    'update_by': 'admin',
                    'update_time': datetime.now()
                }
                
                # 插入数据
                columns = list(data.keys())
                values = list(data.values())
                placeholders = ', '.join(['%s'] * len(values))
                sql = f"INSERT INTO t_erp_sample_notice ({', '.join(columns)}) VALUES ({placeholders})"
                cursor.execute(sql, values)
                
                success_count += 1
                
                if (idx + 1) % 100 == 0:
                    print(f"已导入 {idx + 1}/{len(df)} 条...")
                    conn.commit()
            
            except Exception as e:
                error_count += 1
                print(f"导入第 {idx + 1} 条失败: {e}")
                continue
        
        conn.commit()
        
        print("\n" + "="*100)
        print(f"导入完成！")
        print(f"成功: {success_count} 条")
        print(f"失败: {error_count} 条")
        print("="*100)
        
        # 验证
        print("\n【3】验证导入结果...")
        cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice")
        total = cursor.fetchone()[0]
        print(f"数据库中总记录数: {total}")
        
        if total > 0:
            print("\n样本数据:")
            sql = """
                SELECT id, sample_no, sample_type, customer_id, 
                       style_category, sample_category_type,
                       emergency_type, audit_status, audit_by
                FROM t_erp_sample_notice 
                ORDER BY id DESC 
                LIMIT 10
            """
            cursor.execute(sql)
            for row in cursor.fetchall():
                print(f"  {row}")
        
    except Exception as e:
        print(f"导入失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    delete_data()
    import_data()

