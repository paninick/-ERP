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
print(df.head(10))

# 数据清洗
print("\n[3/6] 开始数据清洗...")
df_clean = df.copy()

# 处理空值
df_clean['打样编号'] = df_clean['打样编号'].fillna('').astype(str)
df_clean['打样类型'] = df_clean['打样类型'].fillna('').astype(str)
df_clean['客户id'] = df_clean['客户id'].fillna(0).astype(int)
df_clean['款式大类'] = df_clean['款式大类'].fillna('').astype(str)
df_clean['款式小类'] = df_clean['款式小类'].fillna('').astype(str)
df_clean['样品种类'] = df_clean['样品种类'].fillna('').astype(str)
df_clean['款号'] = df_clean['款号'].fillna('').astype(str)
df_clean['要求交期'] = pd.to_datetime(df_clean['要求交期'], errors='coerce')
df_clean['紧急程度'] = df_clean['紧急程度'].fillna('').astype(str)
df_clean['审批状态'] = df_clean['审批状态'].fillna('').astype(str)
df_clean['审批人id'] = df_clean['审批人id'].fillna('').astype(str)
df_clean['审批时间'] = pd.to_datetime(df_clean['审批时间'], errors='coerce')
df_clean['流程实例ID'] = df_clean['流程实例ID'].fillna('').astype(str)
df_clean['大货款号'] = df_clean['大货款号'].fillna('').astype(str)

print(f"    清洗后数据行数: {len(df_clean)}")

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
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
            """
            
            due_date = row['要求交期'] if pd.notna(row['要求交期']) else None
            audit_time = row['审批时间'] if pd.notna(row['审批时间']) else None
            
            values = (
                str(row['打样编号']),
                str(row['打样类型']),
                int(row['客户id']),
                str(row['款式大类']),
                str(row['款式小类']),
                str(row['样品种类']),
                str(row['款号']),
                due_date,
                str(row['紧急程度']),
                str(row['审批状态']),
                str(row['审批人id']),
                audit_time,
                str(row['流程实例ID']),
                str(row['大货款号']),
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
