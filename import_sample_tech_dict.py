import pandas as pd
import pymysql
from db_config import get_connection

# 字典映射
audit_status_map = {
    '未审批': '1',
    '审批中': '2',
    '审批通过': '3',
    '审批不通过': '4'
}

sample_type_map = {
    '服饰': '1',
    '毛衫': '2'
}

def import_sample_tech_with_dict(excel_file):
    # 连接数据库
    conn = get_connection()
    cursor = conn.cursor()
    
    try:
        # 清空现有数据
        print("正在清空现有工艺指示书数据...")
        cursor.execute("DELETE FROM t_erp_sample_tech")
        conn.commit()
        print(f"已清空 {cursor.rowcount} 条数据")
        
        # 读取Excel文件
        print(f"正在读取Excel文件: {excel_file}")
        df = pd.read_excel(excel_file)
        print(f"成功读取 {len(df)} 条数据")
        
        success_count = 0
        fail_count = 0
        
        print(f"\n开始导入数据...")
        
        for idx, row in df.iterrows():
            try:
                # 处理空值的函数
                def get_val(col_name):
                    val = row.get(col_name)
                    if pd.isna(val) or val == '' or str(val).strip() == 'nan':
                        return None
                    return str(val).strip()
                
                # 获取字典值
                def get_dict_val(val, val_map, field_name):
                    if val is None:
                        return None
                    if val in val_map:
                        return val_map[val]
                    return None
                
                # 获取原始值
                sample_type_text = get_val('打样类型：1-服装 2-毛衣')
                audit_status_text = get_val('审批状态')
                
                # 映射字典值
                sample_type = get_dict_val(sample_type_text, sample_type_map, '打样类型')
                audit_status = get_dict_val(audit_status_text, audit_status_map, '审批状态')
                
                # 构建SQL，使用%s占位符
                sql = """INSERT INTO t_erp_sample_tech (
                    sample_type, sample_id, due_date, style_pic, tag_pic, tag_pic_remark,
                    patten_marker, patten_checker, cutting_tip, lining_tip, thread_tip,
                    needle_tip, sewing_tip, back_garment_tip, tag_hanging_tip, ironing_tip,
                    fabric_tip, seam_sealing_tip, hand_stitching_tip, hand_stitching_inspection,
                    washing_tip, audit_status, audit_by, audit_time, create_by, create_time
                ) VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, 'admin', NOW())"""
                
                # 获取数据
                values = [
                    sample_type,
                    get_val('打样id'),
                    get_val('要求交期'),
                    get_val('款式图'),
                    get_val('订标位置示意图'),
                    get_val('订表位置说明'),
                    get_val('制版人id'),
                    get_val('核版人id'),
                    get_val('裁剪要求'),
                    get_val('用衬要求'),
                    get_val('用线要求'),
                    get_val('运针要求'),
                    get_val('缝制工艺说明'),
                    get_val('后套工艺说明'),
                    get_val('吊牌挂法'),
                    get_val('整烫要求'),
                    get_val('织造要求'),
                    get_val('套口要求'),
                    get_val('手缝要求'),
                    get_val('套口手缝检验'),
                    get_val('水洗要求'),
                    audit_status,
                    get_val('审批人'),
                    get_val('审批时间')
                ]
                
                cursor.execute(sql, values)
                
                success_count += 1
                
                if success_count % 100 == 0:
                    conn.commit()
                    print(f"已导入 {success_count} 条数据...")
                    
            except Exception as e:
                fail_count += 1
                if fail_count <= 10:
                    print(f"第 {idx+1} 条数据导入失败: {e}")
                    import traceback
                    traceback.print_exc()
        
        # 提交剩余数据
        conn.commit()
        
        print(f"\n导入完成！")
        print(f"成功: {success_count} 条")
        print(f"失败: {fail_count} 条")
        
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
    excel_file = r"C:\Users\91306\Downloads\工艺指示书_全量导出_2026-04-11.xlsx"
    import_sample_tech_with_dict(excel_file)
