import pandas as pd
import pymysql
from datetime import datetime
from db_config import get_connection

def get_dictionary_mappings():
    """获取字典类型映射"""
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        mappings = {
            'audit_status': {},
            'progress_status': {},
            'sample_type': {},
            'style_type': {},
            'sample_category': {}
        }
        
        # 获取审批状态
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'sys_normal_disable'")
        for row in cursor.fetchall():
            mappings['audit_status'][row[1]] = row[0]
        
        # 获取进行状态
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_check_progress'")
        for row in cursor.fetchall():
            mappings['progress_status'][row[1]] = row[0]
        
        # 获取打样类型
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_type'")
        for row in cursor.fetchall():
            mappings['sample_type'][row[1]] = row[0]
        
        # 获取样品款式
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_style_type'")
        for row in cursor.fetchall():
            mappings['style_type'][row[1]] = row[0]
        
        # 获取样品类型
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_category'")
        for row in cursor.fetchall():
            mappings['sample_category'][row[1]] = row[0]
        
        return mappings
    finally:
        cursor.close()
        conn.close()

def import_check_data(excel_file):
    conn = pymysql.connect(**DB_CONFIG)
    cursor = conn.cursor()
    
    try:
        # 获取字典映射
        print("正在获取字典映射...")
        mappings = get_dictionary_mappings()
        print(f"字典映射: {mappings}")
        
        # 读取Excel文件
        print(f"\n正在读取Excel文件: {excel_file}")
        df = pd.read_excel(excel_file)
        print(f"成功读取 {len(df)} 条数据")
        
        # 清空现有数据
        print("\n正在清空现有数据...")
        cursor.execute("DELETE FROM t_erp_check")
        cursor.execute("ALTER TABLE t_erp_check AUTO_INCREMENT = 1")
        conn.commit()
        print("已清空现有数据")
        
        success_count = 0
        fail_count = 0
        warnings = []
        
        print(f"\n开始导入数据...")
        for index, row in df.iterrows():
            try:
                # 映射字典值
                audit_status_label = str(row.get('审批状态', ''))
                audit_status = mappings['audit_status'].get(audit_status_label, None)
                
                progress_status_label = str(row.get('进行状态', '')) if pd.notna(row.get('进行状态')) else None
                progress_status = mappings['progress_status'].get(progress_status_label, None)
                
                sample_type_label = str(row.get('打样类型', ''))
                sample_type = mappings['sample_type'].get(sample_type_label, None)
                
                style_type_label = str(row.get('样品款式', ''))
                style_type = mappings['style_type'].get(style_type_label, None)
                
                sample_category_label = str(row.get('样品类型', ''))
                sample_category = mappings['sample_category'].get(sample_category_label, None)
                
                # 处理日期
                due_date_str = str(row.get('要求交期', '')) if pd.notna(row.get('要求交期')) else None
                due_date = None
                if due_date_str:
                    try:
                        due_date = datetime.strptime(due_date_str.split(' ')[0], '%Y-%m-%d').date()
                    except:
                        pass
                
                # 插入数据
                sql = """
                    INSERT INTO t_erp_check (
                        check_no, audit_status, progress_status, customer_name, 
                        sample_type, style_type, sample_category_type, 
                        style_no, bulk_order_no, sales_name, due_date, 
                        tech_no, sample_no, create_by, create_time, update_time
                    ) VALUES (
                        %s, %s, %s, %s, 
                        %s, %s, %s, 
                        %s, %s, %s, %s, 
                        %s, %s, %s, %s, %s
                    )
                """
                
                check_no = str(row.get('工艺书编号', ''))
                
                cursor.execute(sql, (
                    check_no,
                    audit_status,
                    progress_status,
                    str(row.get('客户', '')),
                    sample_type,
                    style_type,
                    sample_category,
                    str(row.get('款号', '')),
                    str(row.get('大货款号', '')),
                    str(row.get('业务员', '')),
                    due_date,
                    str(row.get('工艺书编号', '')),
                    str(row.get('打样编号', '')),
                    'admin',
                    datetime.now(),
                    datetime.now()
                ))
                
                success_count += 1
                
                if (success_count + fail_count) % 1000 == 0:
                    print(f"已处理 {success_count + fail_count} 条...")
                    
            except Exception as e:
                fail_count += 1
                warnings.append(f"第{index+1}行出错: {str(e)}")
                continue
        
        conn.commit()
        
        print(f"\n=== 导入完成 ===")
        print(f"✅ 成功: {success_count} 条")
        print(f"❌ 失败: {fail_count} 条")
        
        if warnings:
            print(f"\n警告信息 ({len(warnings)}条):")
            for warning in warnings[:10]:
                print(f"  {warning}")
            if len(warnings) > 10:
                print(f"  ... 还有 {len(warnings)-10} 条警告")
        
        # 验证导入结果
        cursor.execute("SELECT COUNT(*) FROM t_erp_check")
        total = cursor.fetchone()[0]
        print(f"\n当前数据库总记录数: {total} 条")
        
    except Exception as e:
        conn.rollback()
        print(f"导入出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    excel_file = r"C:\Users\91306\Downloads\大货核版_全量导出_2026-04-11 (2).xlsx"
    import_check_data(excel_file)
