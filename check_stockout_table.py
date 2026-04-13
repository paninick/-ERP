
import pymysql

from db_config import get_connection

def check_table():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print('='*80)
        print('Stock Out Table Structure:')
        print('='*80)
        cursor.execute('DESCRIBE t_erp_stock_out')
        columns = cursor.fetchall()
        for col in columns:
            print(f'{col[0]}: {col[1]}')
        print()
        
        print('='*80)
        print('Total records:')
        print('='*80)
        cursor.execute('SELECT COUNT(*) FROM t_erp_stock_out')
        count = cursor.fetchone()[0]
        print(f'{count} records')
        
    except Exception as e:
        print(f'Error: {e}')
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    check_table()


