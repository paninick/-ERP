
import pandas as pd
import pymysql
from datetime import datetime
import warnings
from db_config import get_connection

warnings.filterwarnings('ignore')

def import_sample_notice(excel_file):
    """导入打样通知数据"""
    print("=" * 80)
    print("打样通知数据导入")
    print("=" * 80)
    
    # 读取Excel文件
    print(f"\n正在读取Excel文件: {excel_file}")
    df = pd.read_excel(excel_file)
    print(f"成功读取 {len(df)} 条数据")
    
    # 查看列名
    print("\n列名:")
    print(df.columns.tolist())
    
    # 列名映射
    column_mapping = {
        '审批状态': 'audit_status',
        '客户名称': 'customer_name',
        '打样类型': 'sample_type',
        '样品款式': 'style_type',
        '样品类型': 'sample_category_type',
        '打样款号': 'style_code',
        '大货款号': 'bulk_order_no',
        '要求交期': 'due_date',
        '业务员': 'sales_name',
        '申请时间': 'create_time',
        '审批人': 'audit_by_nick_name',
        '审批时间': 'audit_time',
        '打样编号': 'sample_no'
    }
    
    # 重命名列
    df = df.rename(columns=column_mapping)
    
    conn = None
    try:
        conn = get_connection()
        cursor = conn.cursor()
        
        # 清空表数据
        print("\n正在清空打样通知表数据...")
        cursor.execute("TRUNCATE TABLE t_erp_sample_notice")
        print("表数据已清空")
        
        # 准备插入SQL
        insert_sql = """
        INSERT INTO t_erp_sample_notice (
            audit_status, customer_name, sample_type, style_type, 
            sample_category_type, style_code, bulk_order_no, due_date, 
            sales_name, create_time, audit_by_nick_name, audit_time, sample_no,
            create_by, update_by
        ) VALUES (
            %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, 'admin', 'admin'
        )
        """
        
        # 处理数据并插入
        success_count = 0
        fail_count = 0
        
        print(f"\n开始导入数据，共 {len(df)} 条...")
        
        for index, row in df.iterrows():
            try:
                # 处理日期字段
                due_date = None
                if pd.notna(row.get('due_date')):
                    if isinstance(row['due_date'], str):
                        due_date = pd.to_datetime(row['due_date']).to_pydatetime()
                    else:
                        due_date = row['due_date']
                
                create_time = None
                if pd.notna(row.get('create_time')):
                    if isinstance(row['create_time'], str):
                        create_time = pd.to_datetime(row['create_time']).to_pydatetime()
                    else:
                        create_time = row['create_time']
                
                audit_time = None
                if pd.notna(row.get('audit_time')):
                    if isinstance(row['audit_time'], str):
                        audit_time = pd.to_datetime(row['audit_time']).to_pydatetime()
                    else:
                        audit_time = row['audit_time']
                
                # 处理其他字段
                values = (
                    str(row.get('audit_status', '')) if pd.notna(row.get('audit_status')) else None,
                    str(row.get('customer_name', '')) if pd.notna(row.get('customer_name')) else None,
                    str(row.get('sample_type', '')) if pd.notna(row.get('sample_type')) else None,
                    str(row.get('style_type', '')) if pd.notna(row.get('style_type')) else None,
                    str(row.get('sample_category_type', '')) if pd.notna(row.get('sample_category_type')) else None,
                    str(row.get('style_code', '')) if pd.notna(row.get('style_code')) else None,
                    str(row.get('bulk_order_no', '')) if pd.notna(row.get('bulk_order_no')) else None,
                    due_date,
                    str(row.get('sales_name', '')) if pd.notna(row.get('sales_name')) else None,
                    create_time,
                    str(row.get('audit_by_nick_name', '')) if pd.notna(row.get('audit_by_nick_name')) else None,
                    audit_time,
                    str(row.get('sample_no', '')) if pd.notna(row.get('sample_no')) else None
                )
                
                cursor.execute(insert_sql, values)
                success_count += 1
                
                # 每100条提交一次
                if success_count % 100 == 0:
                    conn.commit()
                    print(f"已导入 {success_count} 条...")
                    
            except Exception as e:
                fail_count += 1
                print(f"第 {index + 1} 条数据导入失败: {e}")
                print(f"数据内容: {row.tolist()}")
        
        # 提交剩余数据
        conn.commit()
        
        print("\n" + "=" * 80)
        print(f"导入完成!")
        print(f"成功: {success_count} 条")
        print(f"失败: {fail_count} 条")
        print("=" * 80)
        
        # 验证数据
        cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice")
        total_count = cursor.fetchone()[0]
        print(f"\n数据库中共有 {total_count} 条打样通知数据")
        
        # 查看前5条数据
        print("\n前5条数据预览:")
        cursor.execute("SELECT sample_no, customer_name, style_code, sales_name, audit_status FROM t_erp_sample_notice LIMIT 5")
        for row in cursor.fetchall():
            print(row)
            
    except Exception as e:
        if conn:
            conn.rollback()
        print(f"\n导入过程中发生错误: {e}")
        import traceback
        traceback.print_exc()
    finally:
        if conn:
            conn.close()
        print("\n数据库连接已关闭")

if __name__ == "__main__":
    excel_file = r"C:\Users\91306\Downloads\打样通知_全量导出_2026-04-11 (3).xlsx"
    import_sample_notice(excel_file)

