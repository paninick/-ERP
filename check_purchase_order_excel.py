import pandas as pd

# 读取正式库采购订单Excel
file_path = r'c:\Users\91306\Downloads\采购订单_全量导出_2026-04-11 (1).xlsx'

df = pd.read_excel(file_path)

print("=== 表头字段 ===\n")
print(df.columns.tolist())

print("\n=== 前10条数据 ===\n")
print(df.head(10).to_string())

print(f"\n=== 总数据量: {len(df)} 条 ===")
