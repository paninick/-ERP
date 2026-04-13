import pandas as pd
import pymysql
from datetime import datetime
from db_config import get_connection

def import_sample_tech(excel_file):
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
                def get_val(col):
                    val = row.get(col)
                    if pd.isna(val) or val == '' or str(val).strip() == 'nan':
                        return None
                    return str(val).strip()
                
                # 构建简单的SQL，只填几个字段测试
                sql = "INSERT INTO t_erp_sample_tech (create_by, create_time) VALUES ('admin', NOW())"
                cursor.execute(sql)
                
                # 获取刚插入的ID
                last_id = cursor.lastrowid
                
                # 更新其他字段
                update_sql = """UPDATE t_erp_sample_tech SET 
                    sample_type = %s,
                    sample_id = %s,
                    due_date = %s,
                    style_pic = %s,
                    tag_pic = %s,
                    tag_pic_remark = %s,
                    patten_marker = %s,
                    patten_checker = %s,
                    cutting_tip = %s,
                    lining_tip = %s,
                    thread_tip = %s,
                    needle_tip = %s,
                    sewing_tip = %s,
                    back_garment_tip = %s,
                    tag_hanging_tip = %s,
                    ironing_tip = %s,
                    fabric_tip = %s,
                    seam_sealing_tip = %s,
                    hand_stitching_tip = %s,
                    hand_stitching_inspection = %s,
                    washing_tip = %s,
                    audit_status = %s,
                    audit_by = %s,
                    audit_time = %s
                WHERE id = %s"""
                
                # 获取各列数据
                sample_type = get_val('打样类型：1-服装 2-毛衣')
                sample_id = get_val('打样id')
                due_date = get_val('要求交期')
                style_pic = get_val('款式图')
                tag_pic = get_val('订标位置示意图')
                tag_pic_remark = get_val('订表位置说明')
                patten_marker = get_val('制版人id')
                patten_checker = get_val('核版人id')
                cutting_tip = get_val('裁剪要求')
                lining_tip = get_val('用衬要求')
                thread_tip = get_val('用线要求')
                needle_tip = get_val('运针要求')
                sewing_tip = get_val('缝制工艺说明')
                back_garment_tip = get_val('后套工艺说明')
                tag_hanging_tip = get_val('吊牌挂法')
                ironing_tip = get_val('整烫要求')
                fabric_tip = get_val('织造要求')
                seam_sealing_tip = get_val('套口要求')
                hand_stitching_tip = get_val('手缝要求')
                hand_stitching_inspection = get_val('套口手缝检验')
                washing_tip = get_val('水洗要求')
                audit_status = get_val('审批状态')
                audit_by = get_val('审批人')
                audit_time = get_val('审批时间')
                
                cursor.execute(update_sql, (
                    sample_type, sample_id, due_date, style_pic, tag_pic, tag_pic_remark,
                    patten_marker, patten_checker, cutting_tip, lining_tip, thread_tip,
                    needle_tip, sewing_tip, back_garment_tip, tag_hanging_tip, ironing_tip,
                    fabric_tip, seam_sealing_tip, hand_stitching_tip, hand_stitching_inspection,
                    washing_tip, audit_status, audit_by, audit_time,
                    last_id
                ))
                
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
    import_sample_tech(excel_file)
