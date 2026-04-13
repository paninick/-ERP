
import pandas as pd
import pymysql
from datetime import datetime
from db_config import get_connection

excel_path = r'C:\Users\91306\Downloads\入库单_全量导出_2026-04-12 (1).xlsx'

def import_data():
    print('='*80)
    print('Reading Excel file...')
    print('='*80)
    df = pd.read_excel(excel_path)
    print(f'Total records: {len(df)}')
    print()
    
    # 转换类型字段
    type_map = {'面料': '1', '纱线': '2', '辅料': '3'}
    confirm_map = {'待确认': '0', '已确认': '1'}
    
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 先清除原有数据
        print('='*80)
        print('Clearing existing data...')
        print('='*80)
        cursor.execute('DELETE FROM t_erp_stock_in')
        conn.commit()
        print(f'Deleted {cursor.rowcount} records')
        print()
        
        # 导入新数据
        print('='*80)
        print('Importing data...')
        print('='*80)
        
        imported_count = 0
        for index, row in df.iterrows():
            try:
                # 处理类型
                in_type = type_map.get(str(row['类型']), '1')
                
                # 处理确认状态
                confirm_status = confirm_map.get(str(row['入库确认']), '0')
                
                # 处理日期
                in_date = None
                if pd.notna(row['入库日期']):
                    try:
                        date_str = str(row['入库日期'])
                        if len(date_str) &gt;= 10:
                            date_str = date_str[:10]
                        in_date = datetime.strptime(date_str, '%Y-%m-%d').date()
                    except:
                        pass
                
                confirm_time = None
                if pd.notna(row['确认时间']):
                    try:
                        date_str = str(row['确认时间'])
                        if len(date_str) &gt;= 10:
                            date_str = date_str[:10]
                        confirm_time = datetime.strptime(date_str, '%Y-%m-%d').date()
                    except:
                        pass
                
                sql = """
                INSERT INTO t_erp_stock_in 
                (sn, in_type, confirm_status, confirm_by, confirm_time, 
                 purchase_sn, bulk_order_no, in_description, in_date, create_by, create_time)
                VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                """
                
                values = (
                    str(row['入库单号']) if pd.notna(row['入库单号']) else None,
                    in_type,
                    confirm_status,
                    str(row['确认人']) if pd.notna(row['确认人']) else None,
                    confirm_time,
                    str(row['关联采购单号']) if pd.notna(row['关联采购单号']) else None,
                    str(row['大货款号']) if pd.notna(row['大货款号']) else None,
                    str(row['入库简介']) if pd.notna(row['入库简介']) else None,
                    in_date,
                    'admin',
                    datetime.now()
                )
                
                cursor.execute(sql, values)
                imported_count += 1
                
                if imported_count % 20 == 0:
                    print(f'Imported {imported_count} records...')
                    
            except Exception as e:
                print(f'Error importing record {index+1}: {e}')
                conn.rollback()
                raise
        
        conn.commit()
        print()
        print('='*80)
        print(f'Successfully imported {imported_count} records!')
        print('='*80)
        
        # 验证数据
        print()
        print('='*80)
        print('Verifying data...')
        print('='*80)
        cursor.execute('SELECT confirm_status, COUNT(*) FROM t_erp_stock_in GROUP BY confirm_status')
        for row in cursor.fetchall():
            status_label = '待确认' if row[0] == '0' else '已确认'
            print(f'{status_label}: {row[1]} records')
        
    except Exception as e:
        print(f'Error: {e}')
        conn.rollback()
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    import_data()

