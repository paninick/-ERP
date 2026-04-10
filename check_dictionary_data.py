
import pymysql
import pandas as pd
import json

# 数据库连接配置
config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'y_vue',
    'charset': 'utf8mb4'
}

def get_dict_data(dict_type):
    """获取指定字典类型的数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = """
            SELECT dict_code, dict_label, dict_value, dict_type, css_class, 
                   list_class, is_default, status, create_by, create_time, remark
            FROM sys_dict_data 
            WHERE dict_type = %s 
            ORDER BY dict_sort ASC
        """
        df = pd.read_sql(sql, conn, params=(dict_type,))
        return df
    except Exception as e:
        print(f"查询字典数据失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def get_all_dict_types():
    """获取所有字典类型"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = "SELECT DISTINCT dict_type FROM sys_dict_data ORDER BY dict_type"
        df = pd.read_sql(sql, conn)
        return df['dict_type'].tolist()
    except Exception as e:
        print(f"查询字典类型失败: {e}")
        return []
    finally:
        if conn:
            conn.close()

def get_sample_notice_data():
    """获取打样通知数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = """
            SELECT id, sample_no, sample_type, sample_category_type, 
                   style_category, style_subcategory, customer_id, 
                   salesperson, approver, create_time
            FROM erp_sample_notice 
            LIMIT 20
        """
        df = pd.read_sql(sql, conn)
        return df
    except Exception as e:
        print(f"查询打样通知数据失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def get_customer_data():
    """获取客户数据"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = "SELECT id, customer_code, customer_name, customer_short FROM erp_customer LIMIT 20"
        df = pd.read_sql(sql, conn)
        return df
    except Exception as e:
        print(f"查询客户数据失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def get_user_data():
    """获取用户数据（业务员、审批人员）"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        sql = "SELECT user_id, user_name, nick_name FROM sys_user LIMIT 20"
        df = pd.read_sql(sql, conn)
        return df
    except Exception as e:
        print(f"查询用户数据失败: {e}")
        return pd.DataFrame()
    finally:
        if conn:
            conn.close()

def main():
    print("="*80)
    print("ERP系统字典数据与表数据比对分析报告")
    print("="*80)
    
    # 1. 获取所有字典类型
    print("\n【1】所有字典类型列表:")
    print("-"*80)
    dict_types = get_all_dict_types()
    for dt in dict_types:
        print(f"  - {dt}")
    
    # 2. 检查样品类型和打样类型字典
    print("\n【2】打样类型字典 (erp_sample_type):")
    print("-"*80)
    sample_type_df = get_dict_data('erp_sample_type')
    if not sample_type_df.empty:
        print(sample_type_df[['dict_code', 'dict_label', 'dict_value', 'is_default', 'status']].to_string(index=False))
    else:
        print("  未找到数据")
    
    print("\n【3】样品类型字典 (erp_sample_category):")
    print("-"*80)
    sample_category_df = get_dict_data('erp_sample_category')
    if not sample_category_df.empty:
        print(sample_category_df[['dict_code', 'dict_label', 'dict_value', 'is_default', 'status']].to_string(index=False))
    else:
        print("  未找到数据")
    
    # 4. 检查款式大类字典
    print("\n【4】款式大类字典:")
    print("-"*80)
    # 查找可能的款式大类字典类型
    style_category_types = [dt for dt in dict_types if 'style' in dt.lower() or 'category' in dt.lower()]
    if style_category_types:
        for st in style_category_types:
            print(f"\n  字典类型: {st}")
            df = get_dict_data(st)
            if not df.empty:
                print(df[['dict_code', 'dict_label', 'dict_value', 'is_default', 'status']].to_string(index=False))
    else:
        print("  未找到款式大类相关字典")
    
    # 5. 检查打样通知数据
    print("\n【5】打样通知数据样本:")
    print("-"*80)
    notice_df = get_sample_notice_data()
    if not notice_df.empty:
        print(notice_df.to_string(index=False))
    else:
        print("  未找到数据")
    
    # 6. 检查客户数据
    print("\n【6】客户数据样本:")
    print("-"*80)
    customer_df = get_customer_data()
    if not customer_df.empty:
        print(customer_df.to_string(index=False))
    else:
        print("  未找到数据")
    
    # 7. 检查用户数据
    print("\n【7】用户数据样本（业务员/审批人员）:")
    print("-"*80)
    user_df = get_user_data()
    if not user_df.empty:
        print(user_df.to_string(index=False))
    else:
        print("  未找到数据")
    
    print("\n" + "="*80)
    print("分析完成！")
    print("="*80)

if __name__ == "__main__":
    main()

