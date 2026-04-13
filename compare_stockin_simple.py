
import pandas as pd

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

# 检查是否完全相同
if df_new.equals(df_old):
    print('两个文件完全相同！')
else:
    print('两个文件有差异！')
    # 检查行数
    if len(df_new) != len(df_old):
        print(f'行数不同：新={len(df_new)}, 旧={len(df_old)}')
    else:
        print('行数相同，检查内容差异...')
        # 简单对比前几行
        print('\n前3行对比：')
        print('\n新文件:')
        print(df_new.head(3))
        print('\n旧文件:')
        print(df_old.head(3))

