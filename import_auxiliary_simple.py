import pandas as pd
import pymysql
from datetime import datetime

# Excel文件路径
excel_path = r"C:\Users\91306\Downloads\auxiliary_1775702952490.xlsx"

# 数据库连接配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '123456',
    'database': 'ry-vue',
    'charset': 'utf8mb4'
}

print("=" * 60)
print("辅料数据批量导入脚本")
print("=" * 60)

# 读取Excel文件
print(f"\n[1/5] 正在读取Excel文件: {excel_path}")
df = pd.read_excel(excel_path)
print(f"    总行数: {len(df)}")
print(f"    列名: {list(df.columns)}")

# 显示数据预览
print("\n[2/5] 数据预览:")
print(df.head(10))

# 统计辅料类型
print("\n[3/5] 辅料类型统计:")
print(df['辅料类型'].value_counts().sort_index())

# 数据清洗
print("\n[4/5] 开始数据清洗...")

# 将列名重命名（与数据库字段对应）
df_clean = df.copy()

# 处理空值
df_clean['辅料类型'] = df_clean['辅料类型'].fillna(0).astype(int)
df_clean['供货方式'] = df_clean['供货方式'].fillna(0).astype(int)
df_clean['供应商id'] = df_clean['供应商id'].fillna(0).astype(int)
df_clean['计量单位'] = df_clean['计量单位'].fillna(0).astype(int)

# 处理字符串字段
df_clean['辅料编号'] = df_clean['辅料编号'].fillna('').astype(str)
df_clean['辅料品名'] = df_clean['辅料品名'].fillna('').astype(str)
df_clean['辅料成分'] = df_clean['辅料成分'].fillna('').astype(str)
df_clean['辅料规格'] = df_clean['辅料规格'].fillna('').astype(str)

print(f"    清洗后数据行数: {len(df_clean)}")

# 连接数据库并导入
print("\n[5/5] 连接数据库并导入数据...")

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # 清空表
    cursor.execute("TRUNCATE TABLE t_erp_auxiliary_material")
    print("    已清空表")
    
    # 批量插入数据
    success_count = 0
    error_count = 0
    
    now = datetime.now()
    
    for index, row in df_clean.iterrows():
        try:
            # 插入数据
            sql = """
            INSERT INTO t_erp_auxiliary_material 
            (auxiliary_material_type, auxiliary_material_no, supply_method, supplier_id, 
             name, substance, size, unit, create_by, create_time, update_by, update_time)
            VALUES (%s, %s, %s, %s, %s, %s, %s, %s, 'admin', %s, 'admin', %s)
            """
            
            values = (
                int(row['辅料类型']),
                str(row['辅料编号']),
                int(row['供货方式']),
                int(row['供应商id']),
                str(row['辅料品名']),
                str(row['辅料成分']),
                str(row['辅料规格']),
                int(row['计量单位']),
                now,
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
    print(f"\n导入完成!")
    print(f"    成功: {success_count} 条")
    print(f"    失败: {error_count} 条")
    
    # 验证导入结果
    cursor.execute("SELECT COUNT(*) FROM t_erp_auxiliary_material")
    total_count = cursor.fetchone()[0]
    print(f"\n数据库中当前总记录数: {total_count}")
    
    # 统计辅料类型
    cursor.execute("""
        SELECT auxiliary_material_type, COUNT(*) 
        FROM t_erp_auxiliary_material 
        GROUP BY auxiliary_material_type 
        ORDER BY auxiliary_material_type
    """)
    type_stats = cursor.fetchall()
    print("\n辅料类型统计:")
    for t in type_stats:
        print(f"    类型 {t[0]}: {t[1]} 条")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("导入完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n导入失败: {e}")
    import traceback
    traceback.print_exc()
