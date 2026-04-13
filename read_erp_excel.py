import pandas as pd

def read_erp_excel(excel_file):
    # 读取Excel文件的所有sheet
    print(f"正在读取Excel文件: {excel_file}")
    
    # 获取所有sheet名
    xl = pd.ExcelFile(excel_file)
    print(f"\nSheet列表: {xl.sheet_names}")
    
    # 读取每个sheet
    for sheet_name in xl.sheet_names:
        print(f"\n=== Sheet: {sheet_name} ===")
        df = pd.read_excel(excel_file, sheet_name=sheet_name)
        print(f"数据量: {len(df)} 条")
        print(f"列名: {df.columns.tolist()}")
        if len(df) > 0:
            print("\n前3条数据:")
            print(df.head(3))

if __name__ == "__main__":
    excel_file = r"C:\Users\91306\Downloads\ERP数据_全量导出_2026-04-11.xlsx"
    read_erp_excel(excel_file)
