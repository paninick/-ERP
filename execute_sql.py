
import pymysql

from db_config import get_connection

def execute_sql():
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        print('='*80)
        print('Executing SQL to add missing columns...')
        print('='*80)
        
        sql = """
        ALTER TABLE t_erp_stock_in 
        ADD COLUMN bulk_order_no VARCHAR(64) COMMENT '澶ц揣娆惧彿' AFTER purchase_sn,
        ADD COLUMN in_description TEXT COMMENT '鍏ュ簱绠€浠? AFTER bulk_order_no,
        ADD COLUMN confirm_by VARCHAR(64) COMMENT '纭浜? AFTER confirm_status,
        ADD COLUMN confirm_time DATETIME COMMENT '纭鏃堕棿' AFTER confirm_by
        """
        
        cursor.execute(sql)
        conn.commit()
        print('Columns added successfully!')
        print()
        
        print('='*80)
        print('Updated table structure:')
        print('='*80)
        cursor.execute('DESCRIBE t_erp_stock_in')
        columns = cursor.fetchall()
        for col in columns:
            print(f'{col[0]}: {col[1]}')
        
    except Exception as e:
        print(f'Error: {e}')
        conn.rollback()
        import traceback
        traceback.print_exc()
    finally:
        cursor.close()
        conn.close()

if __name__ == '__main__':
    execute_sql()


