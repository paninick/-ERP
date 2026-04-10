
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

def get_mappings():
    """获取字典映射"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        
        mappings = {
            'sample_type': {},
            'sample_category': {},
            'sample_style': {},
            'customer': {},
            'user': {}
        }
        
        # 打样类型
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_type'][str(row['dict_value'])] = row['dict_value']
            mappings['sample_type'][row['dict_label']] = row['dict_value']
        
        # 样品类型
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_category'][str(row['dict_value'])] = row['dict_value']
            mappings['sample_category'][row['dict_label']] = row['dict_value']
        
        # 款式大类
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_style'][str(row['dict_value'])] = row['dict_value']
            mappings['sample_style'][row['dict_label']] = row['dict_value']
        
        # 客户
        sql = "SELECT id, customer_name FROM t_erp_customer"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['customer'][str(row['id'])] = row['id']
            mappings['customer'][row['customer_name']] = row['id']
        
        # 用户
        sql = "SELECT user_id, nick_name FROM sys_user"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['user'][str(row['user_id'])] = row['user_id']
            mappings['user'][row['nick_name']] = row['user_id']
        
        return mappings
    except Exception as e:
        print(f"获取映射失败: {e}")
        return {}
    finally:
        if conn:
            conn.close()

def delete_and_reimport():
    """删除并重新导入数据"""
    print("="*100)
    print("删除并重新导入数据")
    print("="*100)
    
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
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
        
        # 3. 获取映射
        print("\n【3】获取字典映射...")
        mappings = get_mappings()
        print(f"  打样类型: {len(mappings['sample_type'])} 个")
        print(f"  样品类型: {len(mappings['sample_category'])} 个")
        print(f"  款式大类: {len(mappings['sample_style'])} 个")
        print(f"  客户: {len(mappings['customer'])} 个")
        print(f"  用户: {len(mappings['user'])} 个")
        
        # 4. 导入数据
        print("\n【4】开始导入数据库...")
        
        success_count = 0
        error_count = 0
        
        for idx, row in df.iterrows():
            try:
                data = {
                    'sample_no': str(row['打样编号']) if pd.notna(row['打样编号']) else None,
                    'sample_type': None,
                    'customer_id': None,
                    'style_category': None,
                    'style_sub_category': None,
                    'style_type': None,
                    'sample_category_type': None,
                    'style_code': str(row['款号']) if pd.notna(row['款号']) else None,
                    'due_date': None,
                    'emergency_type': None,
                    'audit_status': None,
                    'audit_by': None,
                    'audit_time': None,
                    'process_instance_id': str(row['流程实例ID']) if pd.notna(row['流程实例ID']) else None,
                    'bulk_order_no': str(row['大货款号']) if pd.notna(row['大货款号']) else None,
                    'create_by': 'admin',
                    'create_time': datetime.now(),
                    'update_by': 'admin',
                    'update_time': datetime.now()
                }
                
                # 打样类型
                if pd.notna(row['打样类型']):
                    val = str(row['打样类型'])
                    if val in mappings['sample_type']:
                        data['sample_type'] = str(mappings['sample_type'][val])
                
                # 客户id - 关键修复！
                if pd.notna(row['客户id']):
                    val = str(int(row['客户id'])) if isinstance(row['客户id'], float) else str(row['客户id'])
                    if val in mappings['customer']:
                        data['customer_id'] = mappings['customer'][val]
                
                # 款式大类 -> style_type（前端期望的字段）
                if pd.notna(row['款式大类']):
                    val = str(int(row['款式大类'])) if isinstance(row['款式大类'], float) else str(row['款式大类'])
                    if val in mappings['sample_style']:
                        data['style_type'] = str(mappings['sample_style'][val])
                        data['style_category'] = str(mappings['sample_style'][val])
                
                # 款式小类
                if pd.notna(row['款式小类']):
                    data['style_sub_category'] = str(row['款式小类'])
                
                # 样品种类
                if pd.notna(row['样品种类']):
                    val = str(int(row['样品种类'])) if isinstance(row['样品种类'], float) else str(row['样品种类'])
                    if val in mappings['sample_category']:
                        data['sample_category_type'] = str(mappings['sample_category'][val])
                
                # 要求交期
                if pd.notna(row['要求交期']):
                    data['due_date'] = row['要求交期']
                
                # 紧急程度
                if pd.notna(row['紧急程度']):
                    data['emergency_type'] = str(row['紧急程度'])
                
                # 审批状态
                if pd.notna(row['审批状态']):
                    data['audit_status'] = str(row['审批状态'])
                
                # 审批人id
                if pd.notna(row['审批人id']):
                    val = str(int(row['审批人id'])) if isinstance(row['审批人id'], float) else str(row['审批人id'])
                    if val in mappings['user']:
