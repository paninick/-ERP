
import pymysql

from db_config import get_connection

def check_data():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 妫€鏌ユ墦鏍烽€氱煡琛ㄦ暟鎹?        print('='*80)
        print('Sample Notice Data Check:')
        print('='*80)
        
        cursor.execute('SELECT COUNT(*) FROM t_erp_sample_notice')
        count = cursor.fetchone()[0]
        print(f'Total records: {count}')
        print()
        
        if count > 0:
            print('='*80)
            print('First 10 records:')
            print('='*80)
            cursor.execute('SELECT id, sample_no, customer_name, sample_type, audit_status, create_time FROM t_erp_sample_notice LIMIT 10')
            rows = cursor.fetchall()
            for row in rows:
                print(f'ID: {row[0]}, No: {row[1]}, Customer: {row[2]}, Type: {row[3]}, Status: {row[4]}, Time: {row[5]}')
            print()
            
            # 妫€鏌ュ鎵圭姸鎬佸垎甯?            print('='*80)
            print('Audit Status Distribution:')
            print('='*80)
            cursor.execute('SELECT audit_status, COUNT(*) as cnt FROM t_erp_sample_notice GROUP BY audit_status')
            rows = cursor.fetchall()
            for row in rows:
                print(f'{row[0]}: {row[1]}')
        else:
            print('No data in sample notice table!')
        
    except Exception as e:
        print(f'Error: {e}')
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    check_data()


