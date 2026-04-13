
import pandas as pd
import pymysql

# 读取新的Excel文件
new_path = r'C:\Users\91306\Downloads\入库单_全量导出_2026-04-12 (1).xlsx'
old_path = r'C:\Users\91306\Downloads\入库单_全量导出_2026-04-12.xlsx'

print('='*80)
print('Reading new Excel file...')
print('='*80)
df_new = pd.read_excel(new_path)
print(f'New records: {len(df_new)}')
print()

print('='*80)
print('Reading old Excel file...')
print('='*80)
df_old = pd.read_excel(old_path)
print(f'Old records: {len(df_old)}')
print()

# 对比
print('='*80)
print('Comparing data...')
print('='*80)

# 检查入库单号是否一致
new_sns = set(df_new['入库单号'].dropna())
old_sns = set(df_old['入库单号'].dropna())

print(f'New unique SNs: {len(new_sns)}')
print(f'Old unique SNs: {len(old_sns)}')
print()

added = new_sns - old_sns
removed = old_sns - new_sns

if added:
    print('='*80)
    print(f'Added records ({len(added)}):')
    print('='*80)
    for sn in sorted(added):
        print(sn)
    print()

if removed:
    print('='*80)
    print(f'Removed records ({len(removed)}):')
    print('='*80)
    for sn in sorted(removed):
        print(sn)
    print()

# 对比公共记录的差异
common_sns = new_sns &amp; old_sns
if common_sns:
    print('='*80)
    print('Checking for differences in common records...')
    print('='*80)
    
    df_new_sorted = df_new.set_index('入库单号').loc[list(common_sns)].sort_index()
    df_old_sorted = df_old.set_index('入库单号').loc[list(common_sns)].sort_index()
    
    differences = []
    for sn in sorted(common_sns):
        new_row = df_new_sorted.loc[sn]
        old_row = df_old_sorted.loc[sn]
        
        for col in df_new.columns:
            if col == '入库单号':
                continue
            new_val = str(new_row[col]) if pd.notna(new_row[col]) else ''
            old_val = str(old_row[col]) if pd.notna(old_row[col]) else ''
            if new_val != old_val:
                differences.append({
                    'sn': sn,
                    'column': col,
                    'new': new_val,
                    'old': old_val
                })
    
    if differences:
        print(f'Found {len(differences)} differences:')
        print()
        for diff in differences:
            print(f'SN: {diff["sn"]}')
            print(f'  Column: {diff["column"]}')
            print(f'  New: {diff["new"]}')
            print(f'  Old: {diff["old"]}')
            print()
    else:
        print('No differences found in common records.')
else:
    print('No common SNs found.')

