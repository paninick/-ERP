
import pandas as pd

new_path = r'C:\Users\91306\Downloads\入库单_全量导出_2026-04-12 (1).xlsx'
df_new = pd.read_excel(new_path)

print('='*80)
print('New file headers:')
print('='*80)
for i, col in enumerate(df_new.columns, 1):
    print(f'{i:2d}. {col}')
print()

print('='*80)
print('"入库确认" column values:')
print('='*80)
print(df_new['入库确认'].value_counts())
print()

print('='*80)
print('Sample records with "入库确认":')
print('='*80)
print(df_new[['入库单号', '入库确认']].head(10))

