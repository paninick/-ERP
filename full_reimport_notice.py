
import pandas as pd
import pymysql
from datetime import datetime

notice_excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"
customer_excel_path = r"C:\Users\91306\Downloads\customer_1775745201959.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def delete_notice_data():
    """删除打样通知数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("【1】删除打样通知数据")
        print("="*100)
        
        tables = [
            't_erp_sample_notice',
            't_erp_sample_notice_detail',
            't_erp_sample_notice_file',
            't_erp_sample_notice_his',
            't_erp_sample_notice_material'
        ]
        
        for table in reversed(tables):
            cursor.execute(f"DELETE FROM {table}")
            cursor.execute(f"ALTER TABLE {table} AUTO_INCREMENT = 1")
            print(f"  已清空 {table}")
        
        conn.commit()
        print("\n删除完成！")
        
    except Exception as e:
        print(f"删除失败: {e}")
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

def import_notice_data():
    """导入打样通知数据"""
    print("\n" + "="*100)
    print("【2】导入打样通知Excel数据")
    print("="*100)
    
    # 读取Excel
    df = pd.read_excel(notice_excel_path)
    print(f"共 {len(df)} 条数据")
    print(f"列名: {df.columns.tolist()}")
    
    # 建立映射
    print("\n建立映射关系...")
    
    # 客户名称 -> 客户ID
    customer_df = pd.read_excel(customer_excel_path)
    customer_name_to_id = {}
    for _, row in customer_df.iterrows():
        if pd.notna(row['客户名称']):
            customer_name_to_id[row['客户名称']] = int(row['业务员']) if pd.notna(row['业务员']) else None
    
    print(f"建立了 {len(customer_name_to_id)} 个客户名称映射")
    
    # 连接数据库
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        success_count = 0
        error_count = 0
        
        for idx, row in df.iterrows():
            try:
                data = {
                    'sample_no': str(row['打样编号']) if pd.notna(row['打样编号']) else None,
                    'sample_type': str(int(row['打样类型'])) if pd.notna(row['打样类型']) else None,
                    'customer_id': int(row['客户id']) if pd.notna(row['客户id']) else None,
                    'style_category': str(int(row['款式大类'])) if pd.notna(row['款式大类']) else None,
                    'style_type': str(int(row['款式大类'])) if pd.notna(row['款式大类']) else None,
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
                       style_type, sample_category_type,
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
    delete_notice_data()
    import_notice_data()
    print("\n" + "="*100)
    print("所有操作完成！请硬刷新浏览器 (Ctrl + F5) 查看效果！")
    print("="*100)

