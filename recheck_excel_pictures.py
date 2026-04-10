
import pandas as pd

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

def check_excel_detailed():
    """详细检查Excel文件内容"""
    print("="*100)
    print("Excel文件详细检查")
    print("="*100)
    
    try:
        df = pd.read_excel(excel_path)
        print(f"\n总行数: {len(df)}")
        print(f"\n所有列名:")
        for i, col in enumerate(df.columns, 1):
            print(f"  {i}. {col}")
        
        # 查找可能包含图片信息的列
        print(f"\n查找可能包含图片信息的列:")
        pic_keywords = ['图片', 'picture', 'pic', 'image', 'photo', 'file', '附件', 'url', '链接']
        pic_cols = []
        for col in df.columns:
            col_lower = str(col).lower()
            if any(keyword in col_lower for keyword in pic_keywords):
                pic_cols.append(col)
        
        if pic_cols:
            print(f"  找到可能的图片列: {pic_cols}")
            for col in pic_cols:
                print(f"\n  --- {col} 列数据样本 ---")
                non_null = df[col].dropna()
                print(f"  非空值数量: {len(non_null)}")
                if len(non_null) > 0:
                    print(f"  前10个值:")
                    for i, val in enumerate(non_null.head(10), 1):
                        print(f"    {i}. {val}")
        else:
            print("  未找到明显的图片列")
        
        # 显示前5行完整数据
        print(f"\n\n前5行完整数据:")
        print("="*100)
        print(df.head().to_string())
        
    except Exception as e:
        print(f"读取Excel失败: {e}")
        import traceback
        traceback.print_exc()

if __name__ == "__main__":
    check_excel_detailed()

