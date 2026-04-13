import pandas as pd

try:
    print("=== 测试读取 user_1775309546654.xlsx ===")
    df1 = pd.read_excel(r'C:\Users\91306\Downloads\user_1775309546654.xlsx')
    print(f"文件1 - 数据形状: {df1.shape}")
    print(f"文件1 - 列名: {list(df1.columns)}")
    
    print("\n=== 测试读取 user_1775309613502.xlsx ===")
    df2 = pd.read_excel(r'C:\Users\91306\Downloads\user_1775309613502.xlsx')
    print(f"文件2 - 数据形状: {df2.shape}")
    print(f"文件2 - 列名: {list(df2.columns)}")
    
    print("\n=== 前5行数据比较 ===")
    print("\n文件1前5行：")
    print(df1.head())
    
    print("\n文件2前5行：")
    print(df2.head())
    
    print("\n=== 数据一致性检查 ===")
    if df1.equals(df2):
        print("✅ 两个Excel文件内容完全一致")
    else:
        print("❌ 两个Excel文件内容不一致")
    
    print("\n=== 部门名称分布 ===")
    dept_counts = df1['部门名称'].value_counts()
    print(dept_counts)
    
except Exception as e:
    print(f"❌ 错误: {e}")
    import traceback
    print(traceback.format_exc())
