import pandas as pd

def read_check_file(excel_file):
    # 读取Excel文件
    print(f"正在读取Excel文件: {excel_file}")
    df = pd.read_excel(excel_file)
    print(f"成功读取 {len(df)} 条数据")
    
    # 查看前几行数据和列名
    print("\n列名:")
    print(df.columns.tolist())
    print("\n前10条数据:")
    print(df.head(10))
    
    # 查看数据类型
    print("\n数据类型:")
    print(df.dtypes)
    
    # 检查是否有重复数据
    if '工艺书编号' in df.columns:
        print(f"\n工艺书编号去重后: {df['工艺书编号'].nunique()} 个")

if __name__ == "__main__":
    excel_file = r"C:\Users\91306\Downloads\大货核版_全量导出_2026-04-11 (2).xlsx"
    read_check_file(excel_file)
