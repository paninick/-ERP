import pandas as pd
import pymysql
from datetime import datetime

# Excel文件路径
excel_path = r"C:\Users\91306\Downloads\auxiliary_1775702952490.xlsx"

# 数据库连接配置 - 使用mysql_native_password
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

print("=" * 60)
print("辅料数据批量导入脚本")
print("=" * 60)

# 读取Excel文件
print(f"\n[1/5] 正在读取Excel文件: {excel_path}")
df = pd.read_excel(excel_path)
print(f"    总行数: {len(df)}")

# 数据清洗
print("\n[2/5] 开始数据清洗...")

df_clean = df.copy()

# 处理空值
df_clean['辅料类型'] = df_clean['辅料类型'].fillna(0).astype(int)
df_clean['供货方式'] = df_clean['供货方式'].fillna(0).astype(int)
df_clean['供应商id'] = df_clean['供应商id'].fillna(0).astype(int)
df_clean['计量单位'] = df_clean['计量单位'].fillna(0).astype(int)

df_clean['辅料编号'] = df_clean['辅料编号'].fillna('').astype(str)
df_clean['辅料品名'] = df_clean['辅料品名'].fillna('').astype(str)
df_clean['辅料成分'] = df_clean['辅料成分'].fillna('').astype(str)
df_clean['辅料规格'] = df_clean['辅料规格'].fillna('').astype(str)

print(f"    清洗后数据行数: {len(df_clean)}")

# 连接数据库并导入
print("\n[3/5] 连接数据库并导入数据...")

try:
    conn = pymysql.connect(**db_config)
    cursor = conn.cursor()
    
    # 先添加字典
    print("\n[3.1/5] 添加辅料类型字典...")
    
    # 检查字典类型
    cursor.execute("SELECT dict_id FROM sys_dict_type WHERE dict_type = 'erp_auxiliary_material_type'")
    if not cursor.fetchone():
        cursor.execute("""
        INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
        VALUES ('辅料类型', 'erp_auxiliary_material_type', '0', 'admin', NOW(), '辅料类型字典')
        """)
        print("    字典类型已添加")
    else:
        print("    字典类型已存在")
    
    # 添加字典数据
    dict_data = [
        (1, '纽扣', '1', 'erp_auxiliary_material_type', '0', 'admin', '纽扣类辅料'),
        (2, '拉链', '2', 'erp_auxiliary_material_type', '0', 'admin', '拉链类辅料'),
        (3, '织带', '3', 'erp_auxiliary_material_type', '0', 'admin', '织带类辅料'),
        (4, '衬布', '4', 'erp_auxiliary_material_type', '0', 'admin', '衬布类辅料'),
        (5, '线', '5', 'erp_auxiliary_material_type', '0', 'admin', '线类辅料'),
        (6, '商标', '6', 'erp_auxiliary_material_type', '0', 'admin', '商标类辅料'),
        (7, '包装', '7', 'erp_auxiliary_material_type', '0', 'admin', '包装类辅料'),
        (8, '其他', '8', 'erp_auxiliary_material_type', '0', 'admin', '其他辅料'),
        (9, '衬料', '9', 'erp_auxiliary_material_type', '0', 'admin', '衬料类辅料'),
    ]
    
    added_dict = 0
    for item in dict_data:
        cursor.execute("""
        INSERT IGNORE INTO sys_dict_data 
        (dict_sort, dict_label, dict_value, dict_type, status, create_by, remark)
        VALUES (%s, %s, %s, %s, %s, %s, %s)
        """, item)
        if cursor.rowcount > 0:
            added_dict += 1
    print(f"    添加了 {added_dict} 条字典数据")
    
    # 清空表
    print("\n[3.2/5] 清空辅料表...")
    cursor.execute("TRUNCATE TABLE t_erp_auxiliary_material")
    print("    表已清空")
    
    # 批量插入数据
    print("\n[3.3/5] 开始导入数据...")
    success_count = 0
    error_count = 0
    now = datetime.now()
    
    for index, row in df_clean.iterrows():
        try:
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
    
    conn.commit()
    
    print(f"\n[4/5] 导入完成!")
    print(f"    成功: {success_count} 条")
    print(f"    失败: {error_count} 条")
    
    # 验证
    cursor.execute("SELECT COUNT(*) FROM t_erp_auxiliary_material")
    total_count = cursor.fetchone()[0]
    print(f"\n[5/5] 验证:")
    print(f"    数据库总记录数: {total_count}")
    
    cursor.execute("""
        SELECT auxiliary_material_type, COUNT(*) 
        FROM t_erp_auxiliary_material 
        GROUP BY auxiliary_material_type 
        ORDER BY auxiliary_material_type
    """)
    type_stats = cursor.fetchall()
    print("\n    辅料类型统计:")
    for t in type_stats:
        print(f"      类型 {t[0]}: {t[1]} 条")
    
    cursor.close()
    conn.close()
    
    print("\n" + "=" * 60)
    print("全部完成！")
    print("=" * 60)
    
except Exception as e:
    print(f"\n失败: {e}")
    import traceback
    traceback.print_exc()
