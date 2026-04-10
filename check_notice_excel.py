import pandas as pd

# Excel文件路径
excel_path = r"C:\Users\91306\Downloads\notice_1775725511832.xlsx"

print("=" * 60)
print("查看正式库打样通知Excel数据")
print("=" * 60)

# 读取Excel文件
df = pd.read_excel(excel_path)
print(f"\n总行数: {len(df)}")
print(f"列名: {list(df.columns)}")

print("\n前10条数据:")
print(df.head(10))

print("\n数据类型:")
print(df.dtypes)

print("\n各列非空值统计:")
print(df.count())

print("\n" + "=" * 60)
print("查看完成！")
print("=" * 60)
