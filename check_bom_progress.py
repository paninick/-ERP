
import pymysql

from db_config import get_connection

try:
    conn = get_connection()
    cursor = conn.cursor()
    
    print('='*80)
    print('馃搳 妫€鏌_erp_bom琛ㄧ殑杩涜鐘舵€佸瓧娈碉細')
    print('='*80)
    
    cursor.execute('DESCRIBE t_erp_bom')
    columns = cursor.fetchall()
    print('琛ㄧ粨鏋勶細')
    for col in columns:
        print(f'  {col[0]} ({col[1]})')
    print()
    
    print('='*80)
    print('馃搳 鍓?0鏉℃暟鎹殑杩涜鐘舵€侊細')
    print('='*80)
    cursor.execute('SELECT id, audit_status, progress_status, customer_name, sample_no FROM t_erp_bom LIMIT 10')
    rows = cursor.fetchall()
    for row in rows:
        print(f'ID:{row[0]} | 瀹℃壒鐘舵€?{row[1]} | 杩涜鐘舵€?{row[2]} | 瀹㈡埛:{row[3]} | 鎵撴牱缂栧彿:{row[4]}')
    print()
    
    print('='*80)
    print('馃搳 杩涜鐘舵€佸€肩粺璁★細')
    print('='*80)
    cursor.execute('SELECT progress_status, COUNT(*) as cnt FROM t_erp_bom GROUP BY progress_status')
    rows = cursor.fetchall()
    for row in rows:
        print(f'  {row[0]}: {row[1]}鏉?)
    print()
    
    print('='*80)
    print('馃搳 妫€鏌ュ瓧娈靛悕鎷煎啓锛?)
    print('='*80)
    cursor.execute('SHOW COLUMNS FROM t_erp_bom LIKE "%progress%"')
    rows = cursor.fetchall()
    print('鍚?progress"鐨勫瓧娈碉細')
    for row in rows:
        print(f'  {row[0]}')
    print()
    
    cursor.close()
    conn.close()
    print('鉁?妫€鏌ュ畬鎴愶紒')
    
except Exception as e:
    print(f'鉂?閿欒: {e}')
    import traceback
    traceback.print_exc()

