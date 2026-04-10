
import pandas as pd
import pymysql

excel_path = r"C:\Users\91306\Downloads\notice_1775741775178.xlsx"

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def read_excel_data():
    """读取Excel数据"""
    df = pd.read_excel(excel_path)
    return df

def get_mappings():
    """获取所有映射关系"""
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
            mappings['sample_type'][row['dict_label']] = row['dict_value']
            mappings['sample_type'][str(row['dict_value'])] = row['dict_value']
        
        # 样品类型
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_category'][row['dict_label']] = row['dict_value']
            mappings['sample_category'][str(row['dict_value'])] = row['dict_value']
        
        # 款式大类
        sql = "SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style'"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['sample_style'][row['dict_label']] = row['dict_value']
            mappings['sample_style'][str(row['dict_value'])] = row['dict_value']
        
        # 客户
        sql = "SELECT id, customer_name FROM t_erp_customer"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['customer'][row['customer_name']] = row['id']
            mappings['customer'][str(row['id'])] = row['id']
        
        # 用户
        sql = "SELECT user_id, nick_name FROM sys_user"
        df = pd.read_sql(sql, conn)
        for _, row in df.iterrows():
            mappings['user'][row['nick_name']] = row['user_id']
            mappings['user'][str(row['user_id'])] = row['user_id']
        
        return mappings
    except Exception as e:
        print(f"获取映射失败: {e}")
        return {}
    finally:
        if conn:
            conn.close()

def update_notice_data(df, mappings):
    """更新打样通知数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        success_count = 0
        error_count = 0
        
        for idx, row in df.iterrows():
            try:
                sample_no = str(row['打样编号']) if pd.notna(row['打样编号']) else None
                
                if not sample_no:
                    continue
                
                # 查找现有记录
                cursor.execute("SELECT id FROM t_erp_sample_notice WHERE sample_no = %s", (sample_no,))
                existing = cursor.fetchone()
                
                # 准备更新数据
                update_data = {}
                
                # 打样类型
                if pd.notna(row['打样类型']):
                    val = str(row['打样类型'])
                    if val in mappings['sample_type']:
                        update_data['sample_type'] = mappings['sample_type'][val]
                
                # 客户id
                if pd.notna(row['客户id']):
                    val = str(int(row['客户id'])) if isinstance(row['客户id'], float) else str(row['客户id'])
                    if val in mappings['customer']:
                        update_data['customer_id'] = mappings['customer'][val]
                
                # 款式大类
                if pd.notna(row['款式大类']):
                    val = str(int(row['款式大类'])) if isinstance(row['款式大类'], float) else str(row['款式大类'])
                    if val in mappings['sample_style']:
                        update_data['style_category'] = mappings['sample_style'][val]
                
                # 样品种类
                if pd.notna(row['样品种类']):
                    val = str(row['样品种类'])
                    if val in mappings['sample_category']:
                        update_data['sample_category_type'] = mappings['sample_category'][val]
                
                # 审批状态
                if pd.notna(row['审批状态']):
                    update_data['audit_status'] = str(row['审批状态'])
                
                # 审批人id
                if pd.notna(row['审批人id']):
                    val = str(int(row['审批人id'])) if isinstance(row['审批人id'], float) else str(row['审批人id'])
                    if val in mappings['user']:
                        update_data['audit_by'] = str(mappings['user'][val])
                
                # 审批时间
                if pd.notna(row['审批时间']):
                    update_data['audit_time'] = row['审批时间']
                
                # 大货款号
                if pd.notna(row['大货款号']):
                    update_data['bulk_order_no'] = str(row['大货款号'])
                
                if existing:
                    # 更新现有记录
                    if update_data:
                        set_clause = ', '.join([f"{k} = %s" for k in update_data.keys()])
                        sql = f"UPDATE t_erp_sample_notice SET {set_clause} WHERE id = %s"
                        values = list(update_data.values()) + [existing[0]]
                        cursor.execute(sql, values)
                        success_count += 1
                else:
                    # 插入新记录（如果需要）
                    pass
                
                if (idx + 1) % 100 == 0:
                    print(f"已处理 {idx + 1}/{len(df)} 条记录...")
                    conn.commit()
            
            except Exception as e:
                error_count += 1
                print(f"处理第 {idx + 1} 条记录时出错: {e}")
                continue
        
        conn.commit()
        print(f"\n数据同步完成！")
        print(f"成功更新: {success_count} 条")
        print(f"错误: {error_count} 条")
        
    except Exception as e:
        print(f"更新数据失败: {e}")
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

def main():
    print("="*100)
    print("开始执行打样通知数据同步")
    print("="*100)
    
    # 1. 读取Excel
    print("\n【1】读取Excel数据...")
    df = read_excel_data()
    print(f"共 {len(df)} 条数据")
    
    # 2. 获取映射
    print("\n【2】获取映射关系...")
    mappings = get_mappings()
    print(f"打样类型映射: {len(mappings['sample_type'])} 个")
    print(f"样品类型映射: {len(mappings['sample_category'])} 个")
    print(f"款式大类映射: {len(mappings['sample_style'])} 个")
    print(f"客户映射: {len(mappings['customer'])} 个")
    print(f"用户映射: {len(mappings['user'])} 个")
    
    # 3. 执行更新
    print("\n【3】开始更新数据库...")
    update_notice_data(df, mappings)
    
    print("\n" + "="*100)
    print("同步完成！")
    print("="*100)

if __name__ == "__main__":
    main()

