
import pandas as pd
import os

excel_path = r'C:\Users\91306\Downloads\样衣BOM_全量导出_2026-04-12.xlsx'

if os.path.exists(excel_path):
    print(f'✅ 文件存在: {excel_path}')
    print()
    
    df = pd.read_excel(excel_path)
    print('='*80)
    print('📊 样衣BOM表头：')
    print('='*80)
    print(list(df.columns))
    print()
    
    print('='*80)
    print(f'📊 总记录数: {len(df)}')
    print('='*80)
    print()
    
    print('='*80)
    print('📊 前5条数据：')
    print('='*80)
    print(df.head())
    print()
    
    if '进行状态' in df.columns:
        print('='*80)
        print('📊 进行状态值统计：')
        print('='*80)
        print(df['进行状态'].value_counts(dropna=False))
        print()
    
    if '要求交期' in df.columns:
        print('='*80)
        print('📊 要求交期前10条：')
        print('='*80)
        print(df['要求交期'].head(10))
        print()
    
    if '工艺书编号' in df.columns:
        print('='*80)
        print('📊 工艺书编号前10条：')
        print('='*80)
        print(df['工艺书编号'].head(10))
        print()
    
    if '打样编号' in df.columns:
        print('='*80)
        print('📊 打样编号前10条：')
        print('='*80)
        print(df['打样编号'].head(10))
        print()
        
    print('✅ 读取完成！')
else:
    print(f'❌ 文件不存在: {excel_path}')
