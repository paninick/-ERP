
import pandas as pd

excel_path = r'C:\Users\91306\Downloads\stock_1775934207078.xlsx'

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
print('First 10 records:')
print('='*80)
print(df.head(10))
print()

print('='*80)
print('Data types:')
print('='*80)
print(df.dtypes)

