import pandas as pd

# 读取Excel文件
df = pd.read_excel(r'C:\Users\91306\Downloads\customer_1775620858164.xlsx')

# 打印表头和前10行数据
print("表头列名：")
print(list(df.columns))
print("\n前10行数据：")
print(df.head(10))
print("\n总行数：", len(df))