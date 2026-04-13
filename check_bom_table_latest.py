
import pymysql
import configparser

config = configparser.ConfigParser()
config.read(r'd:\erp\lightweight-mysql-config.ini')

db_config = {
    'host': config['mysql']['host'],
    'port': int(config['mysql']['port']),
    'user': config['mysql']['user'],
    'password': config['mysql']['password'],
    'database': config['mysql']['database'],
    'charset': 'utf8mb4'
}

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    print('='*80)
    print('📊 t_erp_bom表结构：')
    print('='*80)
    
    cursor.execute('DESCRIBE t_erp_bom')
    columns = cursor.fetchall()
    for col in columns:
        print(f'  {col[0]} ({col[1]})')
    print()
    
    print('='*80)
    print('📊 当前记录数：')
    print('='*80)
    cursor.execute('SELECT COUNT(*) FROM t_erp_bom')
    count = cursor.fetchone()[0]
    print(f'  当前记录数: {count}')
    print()
    
    print('='*80)
    print('📊 前3条数据：')
    print('='*80)
    cursor.execute('SELECT id, audit_status, progress_status, customer, type, style_type, sample_type, style_no, bulk_order_no, sales_name, require_date, tech_no, sample_no FROM t_erp_bom LIMIT 3')
    rows = cursor.fetchall()
    for row in rows:
        print(row)
    print()
    
    cursor.close()
    conn.close()
    print('✅ 检查完成！')
    
except Exception as e:
    print(f'❌ 错误: {e}')
