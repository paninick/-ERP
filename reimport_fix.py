
import pandas as pd
import pymysql
from datetime import datetime

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def delete_and_reimport():
    """删除并重新导入数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("删除并重新导入打样通知数据")
        print("="*100)
        
        # 1. 删除现有数据
        print("\n【1】删除现有数据...")
        tables = [
            't_erp_sample_notice_material',
            't_erp_sample_notice_his',
            't_erp_sample_notice_file',
            't_erp_sample_notice_detail',
            't_erp_sample_notice'
        ]
        for table in tables:
            cursor.execute(f"DELETE FROM {table}")
            cursor.execute(f"ALTER TABLE {table} AUTO_INCREMENT = 1")
            print(f"  已清空 {table}")
        conn.commit()
        
        # 2. 读取Excel
        print("\n【2】读取Excel数据...")
        df = pd.read_excel(excel_path)
        print(f"  共 {len(df)} 条数据")
        
        # 显示Excel列名
        print("\nExcel列名:")
        for i, col in enumerate(df.columns, 1):
            print(f"  {i}. {col}")
        
        # 3. 获取字典映射
        print("\n【3】获取字典映射...")
        
        # 打样类型
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type'")
        sample_type_map = {str(r[0]): r[0] for r in cursor.fetchall()}
        
        # 样品类型
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category'")
        sample_category_map = {str(r[0]): r[0] for r in cursor.fetchall()}
        
        # 款式大类
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style'")
        style_map = {str(r[0]): r[0] for r in cursor.fetchall()}
        
        # 客户
        cursor.execute("SELECT id, customer_name FROM t_erp_customer")
        customer_map = {str(r[0]): r[0] for r in cursor.fetchall()}
        
        # 用户
        cursor.execute("SELECT user_id, nick_name FROM sys_user")
        user_map = {str(r[0]): r[0] for r in cursor.fetchall()}
        
        print(f"  打样类型: {len(sample_type_map)} 个")
        print(f"  样品类型: {len(sample_category_map)} 个")
        print(f"  款式大类: {len(style_map)} 个")
        print(f"  客户: {len(customer_map