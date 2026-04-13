
import pandas as pd

excel_path = r'C:\Users\91306\Downloads\入库单_全量导出_2026-04-12 (1).xlsx'

print('='*80)
print('Reading Excel file...')
print('='*80)
df = pd.read_excel(excel_path)
print(f'Total records: {len(df)}')
print()

print('='*80)
print('Headers:')
print('='*80)
for i, col in enumerate(df.columns, 1):
    print(f'{i:2d}. {col}')
print()

print('='*80)
print('First 5 records:')
print('='*80)
print(df.head())
print()

print('='*80)
print('Data types:')
print('='*80)
print(df.dtypes)

