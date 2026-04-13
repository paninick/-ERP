import pandas as pd

# 读取正式库生产计划Excel
file_path = r'c:\Users\91306\Downloads\生产计划_全量导出_2026-04-11 (1).xlsx'

df = pd.read_excel(file_path)

print("=== 表头字段 ===\n")
print(df.columns.tolist())

print("\n=== 前10条数据 ===\n")
print(df.head(10).to_string())

print("\n=== 生产状态统计 ===\n")
print(df['生产状态'].value_counts() if '生产状态' in df.columns else '无生产状态列')

print("\n=== 审批状态统计 ===\n")
print(df['审批状态'].value_counts() if '审批状态' in df.columns else '无审批状态列')
