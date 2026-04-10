import pandas as pd
import pymysql

# Excel文件路径
excel_path = r"C:\Users\91306\Downloads\notice_1775725511832.xlsx"

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

print("=" * 60)
print("检查Excel数据字段")
print("=" * 60)

# 读取Excel文件
df = pd.read_excel(excel_path)
print(f"\n总行数: {len(df)}")
print(f"\n列名: {list(df.columns)}")

print("\n前5条数据:")
print(df.head())

print("\n查看某一列的示例值:")
print(f"\n打样类型: {df['打样类型'].unique()[:10]}")
print(f"\n打样编号: {df['打样编号'].head(10).tolist()}")
print(f"\n客户id: {df['客户id'].head(10).tolist()}")
print(f"\n款式大类: {df['款式大类'].unique()[:10]}")
print(f"\n款式小类: {df['款式小类'].unique()[:10]}")
print(f"\n样品种类: {df['样品种类'].unique()[:10]}")
print(f"\n款号: {df['款号'].head(10).tolist()}")
print(f"\n要求交期: {df['要求交期'].head(10).tolist()}")
print(f"\n紧急程度: {df['紧急程度'].unique()[:10]}")
print(f"\n审批状态: {df['审批状态'].unique()[:10]}")
print(f"\n审批人id: {df['审批人id'].head(10).tolist()}")
print(f"\n审批时间: {df['审批时间'].head(10).tolist()}")
print(f"\n流程实例ID: {df['流程实例ID'].head(10).tolist()}")
print(f"\n大货款号: {df['大货款号'].head(10).tolist()}")

print("\n" + "=" * 60)
print("检查完成！")
print("=" * 60)
