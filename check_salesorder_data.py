
import pandas as pd
import pymysql
from db_config import get_connection

excel_path = r'C:\Users\91306\Downloads\销售订单_全量导出_2026-04-12 (1).xlsx'

def check_data():
    # 先读取Excel看看原始数据
    print('='*80)
    print('Reading Excel file to check raw data:')
    print('='*80)
    df = pd.read_excel(excel_path)
    print(f'Total records: {len(df)}')
    print()
    
    # 检查审批状态和订单状态在Excel中的数据
    print('='*80)
    print('Excel - Audit Status (审批状态) values:')
    print('='*80)
    print(df['审批状态'].value_counts(dropna=False))
    print()
    
    print('='*80)
    print('Excel - Order Status (订单状态) values:')
    print('='*80)
    print(df['订单状态'].value_counts(dropna=False))
    print()
    
    # 检查数据库中的数据
    print('='*80)
    print('Checking database data:')
    print('='*80)
    
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    try:
        # 检查总记录数
        cursor.execute('SELECT COUNT(*) FROM t_erp_sales_order')
        count = cursor.fetchone()[0]
        print(f'Total records in database: {count}')
        print()
        
        # 检查审批状态数据
        print('='*80)
        print('Database - Audit Status (audit_status) values:')
        print('='*80)
        cursor.execute('SELECT audit_status, COUNT(*) as cnt FROM t_erp_sales_order GROUP BY audit_status')
        results = cursor.fetchall()
        for row in results:
            print(f'{row[0]}: {row[1]}')
        print()
        
        # 检查订单状态数据
        print('='*80)
        print('Database - Order Status (order_status) values:')
        print('='*80)
        cursor.execute('SELECT order_status, COUNT(*) as cnt FROM t_erp_sales_order GROUP BY order_status')
        results = cursor.fetchall()
        for row in results:
            print(f'{row[0]}: {row[1]}')
        print()
        
        # 查看前10条记录的详细数据
        print('='*80)
        print('First 10 records from database:')
        print('='*80)
        cursor.execute('SELECT id, sales_no, audit_status, order_status FROM t_erp_sales_order LIMIT 10')
        results = cursor.fetchall()
        for row in results:
            print(f'ID: {row[0]}, SalesNo: {row[1]}, AuditStatus: {row[2]}, OrderStatus: {row[3]}')
        
    except Exception as e:
        print(f'Error: {e}')
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    check_data()

