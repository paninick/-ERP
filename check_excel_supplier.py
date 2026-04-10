import pandas as pd

# Excel文件路径
excel_path = r"C:\Users\91306\Downloads\auxiliary_1775702952490.xlsx"

print("=" * 60)
print("查看Excel中供应商ID数据")
print("=" * 60)

# 读取Excel文件
df = pd.read_excel(excel_path)
print(f"\n总行数: {len(df)}")
print(f"列名: {list(df.columns)}")

print("\n供应商ID列统计:")
print(df['供应商id'].value_counts().sort_index())

print("\n前20条数据的供应商ID:")
for i in range(min(20, len(df))):
    print(f"  第{i+1}条: 供应商ID={df['供应商id'].iloc[i]}, 辅料品名={df['辅料品名'].iloc[i]}")

print("\n" + "=" * 60)
