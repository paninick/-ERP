
import pymysql

from db_config import get_connection

def check_tables():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 鏌ョ湅鎵€鏈夎〃
        print('='*80)
        print('All tables in database:')
        print('='*80)
        cursor.execute('SHOW TABLES')
        tables = cursor.fetchall()
        for table in tables:
            print(table[0])
        print()
        
        # 鏌ョ湅鎵撴牱閫氱煡琛ㄧ粨鏋?        print('='*80)
        print('Sample Notice (t_erp_sample_notice) table structure:')
        print('='*80)
        cursor.execute('DESCRIBE t_erp_sample_notice')
        columns = cursor.fetchall()
        for col in columns:
            print(f'{col[0]}: {col[1]}')
        print()
        
    except Exception as e:
        print(f'Error: {e}')
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    check_tables()


