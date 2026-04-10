import pandas as pd

df = pd.read_excel(r'C:\Users\91306\Downloads\customer_1775620858164.xlsx')

print("列索引：", list(enumerate(df.columns)))
print("\n前5行完整数据：")
for i in range(5):
    print(f"行{i}:", list(df.iloc[i]))
