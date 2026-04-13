
import pymysql

from db_config import get_connection

try:
    conn = get_connection()
    cursor = conn.cursor()
    
    print('='*80)
    print('馃搳 鐩存帴鏌ヨ鏁版嵁搴揝QL锛堝拰BomMapper.xml涓€鏍凤級锛?)
    print('='*80)
    
    sql = '''
    SELECT a.id, a.sample_no, a.sample_type, a.customer_id, a.customer_name, 
           a.style_category, a.style_sub_category, a.style_type, a.sample_category_type, 
           a.style_code, a.due_date, a.emergency_type, a.picture_url, a.audit_status, 
           a.audit_by, a.audit_by_nick_name, a.audit_time, a.process_instance_id, 
           a.bulk_order_no, a.sales_id, a.sales_name, a.progress_status, a.tech_no, 
           a.create_by, a.create_time, a.update_by, a.update_time, a.remark
    FROM t_erp_bom a
    LIMIT 3
    '''
    
    cursor.execute(sql)
    rows = cursor.fetchall()
    
    print(f'鏌ヨ鍒?{len(rows)} 鏉℃暟鎹?)
    print()
    
    print('='*80)
    print('馃搳 鍓?鏉℃暟鎹殑progress_status瀛楁锛?)
    print('='*80)
    
    for i, row in enumerate(rows):
        print(f'绗瑊i+1}鏉?')
        print(f'  id: {row[0]}')
        print(f'  audit_status: {row[13]}')
        print(f'  progress_status: {row[21]}')
        print(f'  tech_no: {row[22]}')
        print(f'  sample_no: {row[1]}')
        print(f'  customer_name: {row[4]}')
        print()
    
    cursor.close()
    conn.close()
    print('鉁?妫€鏌ュ畬鎴愶紒')
    
except Exception as e:
    print(f'鉂?閿欒: {e}')
    import traceback
    traceback.print_exc()

