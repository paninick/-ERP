
import pandas as pd

file1 = r'C:\Users\91306\Downloads\销售订单_全量导出_2026-04-12.xlsx'
file2 = r'C:\Users\91306\Downloads\销售订单_全量导出_2026-04-12 (1).xlsx'

print('='*80)
print('Comparing two sales order files')
print('='*80)
print()

# Read both files
df1 = pd.read_excel(file1)
df2 = pd.read_excel(file2)

print('File 1: 销售订单_全量导出_2026-04-12.xlsx')
print(f'  Records: {len(df1)}')
print(f'  Columns: {list(df1.columns)}')
print()

print('File 2: 销售订单_全量导出_2026-04-12 (1).xlsx')
print(f'  Records: {len(df2)}')
print(f'  Columns: {list(df2.columns)}')
print()

# Compare columns
print('='*80)
print('Column Differences:')
print('='*80)
cols1 = set(df1.columns)
cols2 = set(df2.columns)
print(f'Columns in file1 but not file2: {cols1 - cols2}')
print(f'Columns in file2 but not file1: {cols2 - cols1}')
print()

# Check for missing salesNo in df2 that are in df1
print('='*80)
print('Checking sales order numbers:')
print('='*80)
sales_no1 = set(df1['销售单号'].dropna())
sales_no2 = set(df2['销售单号'].dropna())
print(f'File1 unique salesNo: {len(sales_no1)}')
print(f'File2 unique salesNo: {len(sales_no2)}')
print()

missing_in_file2 = sales_no1 - sales_no2
missing_in_file1 = sales_no2 - sales_no1

print(f'SalesNo in file1 but not in file2: {len(missing_in_file2)}')
if missing_in_file2:
    print(f'  {list(missing_in_file2)[:10]}')  # Show first 10
    if len(missing_in_file2) > 10:
        print(f'  ... and {len(missing_in_file2) - 10} more')
print()

print(f'SalesNo in file2 but not in file1: {len(missing_in_file1)}')
if missing_in_file1:
    print(f'  {list(missing_in_file1)[:10]}')  # Show first 10
    if len(missing_in_file1) > 10:
        print(f'  ... and {len(missing_in_file1) - 10} more')
print()

# Check data completeness in file2
print('='*80)
print('Data completeness in file2:')
print('='*80)
for col in df2.columns:
    missing = df2[col].isna().sum()
    total = len(df2)
    pct = (missing / total) * 100
    print(f'{col}: {missing}/{total} missing ({pct:.1f}%)')

