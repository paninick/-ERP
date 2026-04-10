
import pandas as pd

excel_path = r"C:\Users\91306\Downloads\customer_1775745201959.xlsx"

print("="*80)
print("简单分析客户Excel文件")
print("="*80)

df = pd.read_excel(excel_path)
print(f"\n总行数: {len(df)}")
print(f"\n列名: {df.columns.tolist()}")

print("\n\n前15行数据:")
print("="*80)
print(df.head(15).to_string())

print("\n\n唯一值分析:")
print("="*80)
for col in df.columns:
    unique_vals = df[col].dropna().unique()
    print(f"\n{col}: {len(unique_vals)} 个唯一值")
    if len(unique_vals) <= 20:
        print(f"  值: {unique_vals.tolist()}")
    else:
        print(f"  前10个值: {unique_vals[:10].tolist()}")

print("\n" + "="*80)
print("分析完成！")
print("="*80)

