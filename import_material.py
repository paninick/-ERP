
import pandas as pd
import pymysql
from datetime import datetime

# 数据库配置
db_config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

# Excel文件路径
excel_path = r'C:\Users\91306\Downloads\material_1775630942445.xlsx'

print("正在读取Excel文件...")
df = pd.read_excel(excel_path)
print(f"成功读取 {len(df)} 条数据")

# 连接数据库
conn = pymysql.connect(**db_config)
cursor = conn.cursor()

# 清空表
cursor.execute("TRUNCATE TABLE t_erp_main_material")
conn.commit()
print("表已清空")

# 批量插入
success_count = 0
fail_count = 0
now = datetime.now()

for idx, row in df.iterrows():
    try:
        sql = """
        INSERT INTO t_erp_main_material 
        (main_material_type, main_material_no, supply_method, factory_no, supplier_id, 
         name, composition, width, weight, yarn_count, unit, pictrue_url, price,
         create_by, create_time, update_by, update_time, remark)
        VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, '1', %s, '1', %s, NULL)
        """
        values = (
            str(row.get('主料类型 ', '')) if pd.notna(row.get('主料类型 ')) else None,
            str(row.get('主料编号', '')) if pd.notna(row.get('主料编号')) else None,
            str(row.get('供货方式', '')) if pd.notna(row.get('供货方式')) else None,
            str(row.get('厂供编号', '')) if pd.notna(row.get('厂供编号')) else None,
            int(row.get('主料供应商id')) if pd.notna(row.get('主料供应商id')) else None,
            str(row.get('主料品名', '')) if pd.notna(row.get('主料品名')) else None,
            str(row.get('主料成分', '')) if pd.notna(row.get('主料成分')) else None,
            str(row.get('主料门幅', '')) if pd.notna(row.get('主料门幅')) else None,
            str(row.get('主料克重', '')) if pd.notna(row.get('主料克重')) else None,
            str(row.get('主料纱支', '')) if pd.notna(row.get('主料纱支')) else None,
            str(row.get('计量单位', '')) if pd.notna(row.get('计量单位')) else None,
            str(row.get('图片', '')) if pd.notna(row.get('图片')) else None,
            float(row.get('单价')) if pd.notna(row.get('单价')) else None,
            now,
            now
        )
        cursor.execute(sql, values)
        success_count += 1
        if success_count % 1000 == 0:
            conn.commit()
            print(f"已插入 {success_count} 条...")
    except Exception as e:
        fail_count += 1
        print(f"第 {idx+1} 条失败: {e}")

# 最后提交
conn.commit()
cursor.close()
conn.close()

print(f"\n导入完成！")
print(f"成功: {success_count} 条")
print(f"失败: {fail_count} 条")

