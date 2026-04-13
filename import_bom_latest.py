
import pandas as pd
import pymysql
import os
from db_config import get_connection

excel_path = r'C:\Users\91306\Downloads\样衣BOM_全量导出_2026-04-12.xlsx'

try:
    conn = get_connection()
    cursor = conn.cursor()
    
    print('='*80)
    print('📊 读取Excel...')
    print('='*80)
    df = pd.read_excel(excel_path)
    print(f'✅ 读取成功，共 {len(df)} 条数据')
    print()
    
    print('='*80)
    print('🗑️  清空原有数据...')
    print('='*80)
    cursor.execute('DELETE FROM t_erp_bom')
    conn.commit()
    print('✅ 清空完成')
    print()
    
    print('='*80)
    print('📥 导入新数据...')
    print('='*80)
    
    insert_sql = '''
    INSERT INTO t_erp_bom 
    (audit_status, progress_status, customer_name, sample_type, style_type, sample_category_type, style_code, bulk_order_no, sales_name, due_date, tech_no, sample_no, create_time)
    VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, NOW())
    '''
    
    success_count = 0
    for index, row in df.iterrows():
        try:
            due_date = pd.to_datetime(row['要求交期']).date() if pd.notna(row['要求交期']) else None
            
            values = (
                str(row['审批状态']) if pd.notna(row['审批状态']) else None,
                str(row['进行状态']) if pd.notna(row['进行状态']) else None,
                str(row['客户']) if pd.notna(row['客户']) else None,
                str(row['打样类型']) if pd.notna(row['打样类型']) else None,
                str(row['样品款式']) if pd.notna(row['样品款式']) else None,
                str(row['样品类型']) if pd.notna(row['样品类型']) else None,
                str(row['款号']) if pd.notna(row['款号']) else None,
                str(row['大货款号']) if pd.notna(row['大货款号']) else None,
                str(row['业务员']) if pd.notna(row['业务员']) else None,
                due_date,
                str(row['工艺书编号']) if pd.notna(row['工艺书编号']) else None,
                str(row['打样编号']) if pd.notna(row['打样编号']) else None
            )
            
            cursor.execute(insert_sql, values)
            success_count += 1
            
            if (index + 1) % 500 == 0:
                print(f'  已导入 {index + 1}/{len(df)} 条...')
                conn.commit()
                
        except Exception as e:
            print(f'  ❌ 第 {index + 1} 条失败: {e}')
            continue
    
    conn.commit()
    
    print()
    print('='*80)
    print(f'✅ 导入完成！成功导入 {success_count} 条数据')
    print('='*80)
    print()
    
    print('='*80)
    print('📊 验证导入结果：')
    print('='*80)
    
    cursor.execute('SELECT COUNT(*) FROM t_erp_bom')
    count = cursor.fetchone()[0]
    print(f'  数据库当前记录数: {count}')
    print()
    
    cursor.execute('SELECT id, audit_status, progress_status, customer_name, due_date, tech_no, sample_no FROM t_erp_bom LIMIT 5')
    rows = cursor.fetchall()
    print('  前5条数据：')
    for row in rows:
        print(f'  {row}')
    
    cursor.close()
    conn.close()
    print()
    print('🎉 全部完成！')
    
except Exception as e:
    print(f'❌ 错误: {e}')
    import traceback
    traceback.print_exc()
