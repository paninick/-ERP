
import pandas as pd

excel_path = r'C:\Users\91306\Downloads\material_1775630942445.xlsx'

print("正在读取Excel文件...")
df = pd.read_excel(excel_path)

print(f"\n共 {len(df)} 条数据")
print(f"\n列名:")
for i, col in enumerate(df.columns):
    print(f"  {i+1}. [{col}]")

print(f"\n前3条数据预览:")
print(df.head(3))

