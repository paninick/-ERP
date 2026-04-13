import pandas as pd
import pymysql
from datetime import datetime
from db_config import get_connection

# 读取Excel文件
file_path = r'c:\Users\91306\Downloads\采购订单_全量导出_2026-04-11 (1).xlsx'
df = pd.read_excel(file_path)

print(f"✅ 读取Excel成功，共 {len(df)} 条数据")
print(f"\n表头: {df.columns.tolist()}")

# 连接数据库
conn = get_connection()

cursor = conn.cursor()

# 清空原有数据
cursor.execute("DELETE FROM t_erp_purchase")
deleted_count = cursor.rowcount
print(f"\n✅ 已清空原有 {deleted_count} 条数据")

# 生成插入SQL
inserted_count = 0

for idx, row in df.iterrows():
    type_val = str(row.get('类型', '')) if pd.notna(row.get('类型')) else None
    bulk_order_no = str(row.get('大货款号', '')) if pd.notna(row.get('大货款号')) else None
    description = str(row.get('说明', '')) if pd.notna(row.get('说明')) else None
    
    # 处理日期 - 预计到货日期
    expected_delivery_date = None
    if pd.notna(row.get('预计到货日期')):
        try:
            if isinstance(row['预计到货日期'], datetime):
                expected_delivery_date = row['预计到货日期'].strftime('%Y-%m-%d')
            else:
                expected_delivery_date = str(row['预计到货日期'])
        except:
            expected_delivery_date = None
    
    purchase_name = str(row.get('采购员', '')) if pd.notna(row.get('采购员')) else None
    
    # 处理日期 - 确认时间
    confirm_time = None
    if pd.notna(row.get('确认时间')):
        try:
            if isinstance(row['确认时间'], datetime):
                confirm_time = row['确认时间'].strftime('%Y-%m-%d')
            else:
                confirm_time = str(row['确认时间'])
        except:
            confirm_time = None
    
    status = str(row.get('状态', '')) if pd.notna(row.get('状态')) else None
    sn = str(row.get('采购单号', '')) if pd.notna(row.get('采购单号')) else None
    
    # 插入SQL
    sql = """
        INSERT INTO t_erp_purchase 
        (type, bulk_order_no, description, expected_delivery_date, purchase_name, confirm_time, status, sn, create_by, create_time)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, 'admin', NOW())
    """
    
    cursor.execute(sql, (type_val, bulk_order_no, description, expected_delivery_date, purchase_name, confirm_time, status, sn))
    inserted_count += 1
    
    if inserted_count % 10 == 0:
        print(f"📊 已导入 {inserted_count} 条...")

conn.commit()

print(f"\n✅ 成功导入 {inserted_count} 条采购订单数据！")

# 验证
cursor.execute("SELECT COUNT(*) FROM t_erp_purchase")
total_count = cursor.fetchone()[0]
print(f"📊 数据库当前记录数: {total_count} 条")

cursor.close()
conn.close()

print("\n🎉 采购订单数据导入完成！")
