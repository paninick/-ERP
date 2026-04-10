
import pandas as pd
import pymysql

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def delete_notice_data():
    """删除测试库打样通知数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("删除测试库打样通知数据")
        print("="*100)
        
        # 查看当前数据量
        tables = [
            't_erp_sample_notice',
            't_erp_sample_notice_detail',
            't_erp_sample_notice_file',
            't_erp_sample_notice_his',
            't_erp_sample_notice_material'
        ]
        
        print("\n删除前数据统计:")
        for table in tables:
            cursor.execute(f"SELECT COUNT(*) FROM {table}")
            count = cursor.fetchone()[0]
            print(f"  {table}: {count} 条")
        
        # 删除数据
        print("\n开始删除数据...")
        for table in reversed(tables):
            try:
                cursor.execute(f"DELETE FROM {table}")
                conn.commit()
                print(f"  已删除 {table}")
            except Exception as e:
                print(f"  删除 {table} 失败: {e}")
                conn.rollback()
        
        # 重置自增ID
        print("\n重置自增ID...")
        for table in tables:
            try:
                cursor.execute(f"ALTER TABLE {table} AUTO_INCREMENT = 1")
                conn.commit()
                print(f"  已重置 {table} 的自增ID")
            except Exception as e:
                print(f"  重置 {table} 自增ID失败: {e}")
        
        # 验证删除结果
        print("\n删除后数据统计:")
        for table in tables:
            cursor.execute(f"SELECT COUNT(*) FROM {table}")
            count = cursor.fetchone()[0]
            print(f"  {table}: {count} 条")
        
        print("\n" + "="*100)
        print("数据删除完成！")
        print("="*100)
        
    except Exception as e:
        print(f"删除失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

def compare_headers():
    """对比Excel表和测试库的表头"""
    print("\n" + "="*100)
    print("对比Excel表和测试库的表头")
    print("="*100)
    
    # 1. 读取Excel表头
    print("\n【1】Excel表头:")
    print("-"*100)
    df = pd.read_excel(excel_path)
    excel_headers = df.columns.tolist()
    for i, header in enumerate(excel_headers, 1):
        print(f"  {i}. {header}")
    
    # 2. 读取数据库表结构
    print("\n【2】数据库表结构 (t_erp_sample_notice):")
    print("-"*100)
    
    conn = None
    try:
        conn = pymysql.connect(**config)
        df_db = pd.read_sql("DESCRIBE t_erp_sample_notice", conn)
        db_fields = df_db['Field'].tolist()
        for i, field in enumerate(db_fields, 1):
            print(f"  {i}. {field}")
        
        # 3. 对比分析
        print("\n【3】字段映射对比:")
        print("-"*100)
        
        # 常见字段映射
        mapping = {
            '打样类型': 'sample_type',
            '打样编号': 'sample_no',
            '客户id': 'customer_id',
            '款式大类': 'style_category',
            '款式小类': 'style_sub_category',
            '样品种类': 'sample_category_type',
            '款号': 'style_code',
            '要求交期': 'due_date',
            '紧急程度': 'emergency_type',
            '审批状态': 'audit_status',
            '审批人id': 'audit_by',
            '审批时间': 'audit_time',
            '流程实例ID': 'process_instance_id',
            '大货款号': 'bulk_order_no'
        }
        
        print("Excel列名 -> 数据库字段名:")
        for excel_col, db_field in mapping.items():
            if excel_col in excel_headers and db_field in db_fields:
                print(f"  ✓ {excel_col} -> {db_field}")
            elif excel_col in excel_headers:
                print(f"  ⚠ {excel_col} -> 数据库字段 {db_field} 不存在")
            elif db_field in db_fields:
                print(f"  ⚠ Excel列 {excel_col} 不存在 -> {db_field}")
        
    except Exception as e:
        print(f"读取数据库表结构失败: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()
    
    print("\n" + "="*100)
    print("对比完成！")
    print("="*100)

if __name__ == "__main__":
    delete_notice_data()
    compare_headers()

