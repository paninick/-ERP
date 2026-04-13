
import pandas as pd
import os

excel_path = r'C:\Users\91306\Downloads\销售订单_全量导出_2026-04-12.xlsx'

if os.path.exists(excel_path):
    print('File exists:', excel_path)
    print()
    
    df = pd.read_excel(excel_path)
    print('='*80)
    print('Sales Order Headers:')
    print('='*80)
    print(list(df.columns))
    print()
    
    print('='*80)
    print(f'Total Records: {len(df)}')
    print('='*80)
    print()
    
    print('='*80)
    print('First 5 records:')
    print('='*80)
    print(df.head())
    print()
    
    print('Read completed!')
else:
    print('File not found:', excel_path)

