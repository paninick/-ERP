import pandas as pd
import pymysql
from datetime import datetime

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
print("打样通知数据批量导入")
print("=" * 60)

# 读取Excel文件
print(f"\n[1/6] 正在读取Excel文件: {excel_path}")
df = pd.read_excel(excel_path)
print(f"    总行数: {len(df)}")
print(f"    列名: {list(df.columns)}")

# 显示数据预览
print("\n[2/6] 数据预览:")
print(df.head(3))

# 数据清洗
print("\n[3/6] 开始数据清洗...")
df_clean = df.copy()

# 连接数据库并导入
print("\n[4/6] 连接数据库并导入数据...")

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute("TRUNCATE TABLE t_erp_sample_notice")
    print("    已清空表")
    
    # 批量插入数据
    success_count = 0
    error_count = 0
    
    now = datetime.now()
    
    for index, row in df_clean.iterrows():
        try:
            sql = """
            INSERT INTO t_erp_sample_notice 
            (sample_no, sample_type, customer_id, style_category, style_sub_category,
             sample_category_type, style_code, due_date, emergency_type,
             audit_status, audit_by, audit_time, process_instance_id, bulk_order_no,
             create_by, create_time, update_by, update_time)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            """
            
            # 处理空值
            sample_no = str(row['打样编号']) if pd.notna(row['打样编号']) else ''
            sample_type = str(row['打样类型']) if pd.notna(row['打样类型']) else ''
            customer_id = int(row['客户id']) if pd.notna(row['客户id']) else 0
            style_category = str(row['款式大类']) if pd.notna(row['款式大类']) else ''
            style_sub_category = str(row['款式小类']) if pd.notna(row['款式小类']) else ''
            sample_category_type = str(row['样品种类']) if pd.notna(row['样品种类']) else ''
            style_code = str(row['款号']) if pd.notna(row['款号']) else ''
            due_date = row['要求交期'] if pd.notna(row['要求交期']) else None
            emergency_type = str(row['紧急程度']) if pd.notna(row['紧急程度']) else ''
            audit_status = str(row['审批状态']) if pd.notna(row['审批状态']) else ''
            audit_by = str(row['审批人id']) if pd.notna(row['审批人id']) else ''
            audit_time = row['审批时间'] if pd.notna(row['审批时间']) else None
            process_instance_id = str(row['流程实例ID']) if pd.notna(row['流程实例ID']) else ''
            bulk_order_no = str(row['大货款号']) if pd.notna(row['大货款号']) else ''
            
            values = (
                sample_no,
                sample_type,
                customer_id,
                style_category,
                style_sub_category,
                sample_category_type,
                style_code,
                due_date,
                emergency_type,
                audit_status,
                audit_by,
                audit_time,
                process_instance_id,
                bulk_order_no,
                'admin',
                now,
                'admin',
                now
            )
            
            cursor.execute(sql, values)
            success_count += 1
            
            if success_count % 100 == 0:
                print(f"    已导入 {success_count} 条...")
                
        except Exception as e:
            error_count += 1
            if error_count <= 5:
                print(f"    第 {index + 1} 条导入失败: {e}")
            continue
    
    # 提交事务
    conn.commit()
    print(f"\n[5/6] 导入完成!")
    print(f"    成功: {success_count} 条")
    print(f"    失败: {error_count} 条")
    
    # 验证导入结果
    cursor.execute("SELECT COUNT(*) FROM t_erp_sample_notice")
    total_count = cursor.fetchone()[0]
    print(f"\n[6/6] 验证:")
    print(f"    数据库中当前总记录数: {total_count}")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("导入完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n导入失败: {e}")
    import traceback
    traceback.print_exc()
