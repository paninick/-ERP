
import pandas as pd
import pymysql
from datetime import datetime
import os
from db_config import get_connection

excel_path = r'C:\Users\91306\Downloads\销售订单_全量导出_2026-04-12 (1).xlsx'

def import_sales_order():
    print('Reading Excel file...')
    df = pd.read_excel(excel_path)
    print(f'Total records: {len(df)}')
    print(f'Columns: {list(df.columns)}')
    print()
    
    # 连接数据库
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 先清空原有数据
        print('Clearing existing data...')
        cursor.execute('DELETE FROM t_erp_sales_order')
        conn.commit()
        print('Existing data cleared.')
        print()
        
        # 处理每一行数据
        imported_count = 0
        for index, row in df.iterrows():
            # 处理字段
            sales_type = str(row['销售类型']) if pd.notna(row['销售类型']) else None
            sales_no = str(row['销售单号']) if pd.notna(row['销售单号']) else None
            customer_name = str(row['客户名称']) if pd.notna(row['客户名称']) else None
            bulk_order_no = str(row['大货款号']) if pd.notna(row['大货款号']) else None
            sample_style_no = str(row['打样款号']) if pd.notna(row['打样款号']) else None
            style_category = str(row['款式/品类']) if pd.notna(row['款式/品类']) else None
            sample_no = str(row['打样编号']) if pd.notna(row['打样编号']) else None
            
            # 处理销售日期
            sales_date = None
            if pd.notna(row['销售日期']):
                try:
                    if isinstance(row['销售日期'], datetime):
                        sales_date = row['销售日期']
                    else:
                        sales_date = pd.to_datetime(row['销售日期']).to_pydatetime()
                except:
                    sales_date = None
            
            # 处理交货日期
            due_date = None
            if pd.notna(row['交货日期']):
                try:
                    if isinstance(row['交货日期'], datetime):
                        due_date = row['交货日期']
                    else:
                        due_date = pd.to_datetime(row['交货日期']).to_pydatetime()
                except:
                    due_date = None
            
            sales_name = str(row['业务员']) if pd.notna(row['业务员']) else None
            bulk_opinion = str(row['大货意见']) if pd.notna(row['大货意见']) else None
            audit_status = str(row['审批状态']) if pd.notna(row['审批状态']) else None
            
            # 处理订单状态（注意Excel中有空格）
            order_status = None
            if '订单状 态' in df.columns:
                order_status = str(row['订单状 态']) if pd.notna(row['订单状 态']) else None
            elif '订单状态' in df.columns:
                order_status = str(row['订单状态']) if pd.notna(row['订单状态']) else None
            
            production_exceed = str(row['排产需求是否超出']) if pd.notna(row['排产需求是否超出']) else None
            
            # 处理审批时间
            audit_time = None
            if '审批时间' in df.columns and pd.notna(row['审批时间']):
                try:
                    if isinstance(row['审批时间'], datetime):
                        audit_time = row['审批时间']
                    else:
                        audit_time = pd.to_datetime(row['审批时间']).to_pydatetime()
                except:
                    audit_time = None
            
            # 处理创建时间
            create_time = None
            if '创建时间' in df.columns and pd.notna(row['创建时间']):
                try:
                    if isinstance(row['创建时间'], datetime):
                        create_time = row['创建时间']
                    else:
                        create_time = pd.to_datetime(row['创建时间']).to_pydatetime()
                except:
                    create_time = None
            
            # 插入数据
            sql = '''
            INSERT INTO t_erp_sales_order 
            (sales_type, customer_name, bulk_order_no, sample_style_no, style_category, 
             sample_no, sales_name, sales_no, sales_date, due_date, audit_status, 
             order_status, bulk_opinion, production_exceed, audit_time, create_time, create_by)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            '''
            
            cursor.execute(sql, (
                sales_type, customer_name, bulk_order_no, sample_style_no, style_category,
                sample_no, sales_name, sales_no, sales_date, due_date, audit_status,
                order_status, bulk_opinion, production_exceed, audit_time, create_time, 'admin'
            ))
            
            imported_count += 1
            if imported_count % 100 == 0:
                print(f'Imported {imported_count} records...')
        
        conn.commit()
        print()
        print(f'Successfully imported {imported_count} records!')
        
        # 验证导入
        cursor.execute('SELECT COUNT(*) FROM t_erp_sales_order')
        count = cursor.fetchone()[0]
        print(f'Total records in database: {count}')
        
    except Exception as e:
        conn.rollback()
        print(f'Error: {e}')
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    import_sales_order()

