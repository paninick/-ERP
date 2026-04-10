
import pandas as pd

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

print("="*100)
print("检查Excel中的紧急程度和审批状态值")
print("="*100)

df = pd.read_excel(excel_path)

# 检查紧急程度
print("\n【1】紧急程度唯一值:")
print("-"*100)
emergency_values = df['紧急程度'].dropna().unique()
for val in emergency_values:
    print(f"  {val}")

# 检查审批状态
print("\n【2】审批状态唯一值:")
print("-"*100)
audit_values = df['审批状态'].dropna().unique()
for val in audit_values:
    print(f"  {val}")

print("\n" + "="*100)

