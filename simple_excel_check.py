
import pandas as pd

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

print("="*100)
print("Excel文件简单检查")
print("="*100)

try:
    df = pd.read_excel(excel_path)
    print(f"\n总行数: {len(df)}")
    print(f"\n所有列名:")
    for i, col in enumerate(df.columns, 1):
        print(f"  {i}. {col}")
    
    print(f"\n\n前5行数据:")
    print("="*100)
    print(df.head().to_string())
    
except Exception as e:
    print(f"读取Excel失败: {e}")
    import traceback
    traceback.print_exc()

