import pandas as pd
import pymysql
from datetime import datetime
from db_config import get_connection

def update_check_data(excel_file):
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 读取Excel文件
        print(f"正在读取Excel文件: {excel_file}")
        df = pd.read_excel(excel_file)
        print(f"成功读取 {len(df)} 条数据")
        
        # 获取字典映射
        print("\n正在获取字典映射...")
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_audit_status'")
        audit_status_map = {row[1]: row[0] for row in cursor.fetchall()}
        
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_sample_style'")
        style_type_map = {row[1]: row[0] for row in cursor.fetchall()}
        
        cursor.execute("SELECT dict_value, dict_label FROM sys_dict_data WHERE dict_type = 'erp_check_progress'")
        progress_status_map = {row[1]: row[0] for row in cursor.fetchall()}
        
        print(f"审批状态映射: {audit_status_map}")
        print(f"样品款式映射: {style_type_map}")
        print(f"进行状态映射: {progress_status_map}")
        
        # 逐行更新数据
        print(f"\n开始更新数据...")
        updated_count = 0
        
        for index, row in df.iterrows():
            tech_no = str(row.get('工艺书编号', ''))
            
            # 映射字典值
            audit_status_label = str(row.get('审批状态', ''))
            audit_status = audit_status_map.get(audit_status_label, None)
            
            progress_status_label = str(row.get('进行状态', '')) if pd.notna(row.get('进行状态')) else None
            progress_status = progress_status_map.get(progress_status_label, None)
            
            style_type_label = str(row.get('样品款式', ''))
            style_type = style_type_map.get(style_type_label, None)
            
            # 更新数据
            sql = """
                UPDATE t_erp_check 
                SET audit_status = %s, 
                    progress_status = %s, 
                    style_type = %s
                WHERE tech_no = %s
            """
            
            cursor.execute(sql, (audit_status, progress_status, style_type, tech_no))
            
            if cursor.rowcount > 0:
                updated_count += 1
            
            if (index + 1) % 1000 == 0:
                print(f"已处理 {index + 1} 条，已更新 {updated_count} 条...")
        
        conn.commit()
        
        print(f"\n=== 更新完成 ===")
        print(f"已更新: {updated_count} 条数据")
        
    except Exception as e:
        conn.rollback()
        print(f"更新出错: {e}")
        import traceback
        traceback.print_exc()
        raise
    finally:
        cursor.close()
        conn.close()

if __name__ == "__main__":
    excel_file = r"C:\Users\91306\Downloads\大货核版_全量导出_2026-04-11 (2).xlsx"
    update_check_data(excel_file)
