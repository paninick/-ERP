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
        
        # 查看前几行数据和列名
        print("\n列名:")
        print(df.columns.tolist())
        print("\n前5条数据:")
        print(df.head())
        
        success_count = 0
        fail_count = 0
        
        print(f"\n开始导入数据...")
        
        for idx, row in df.iterrows():
            try:
                # 构建SQL
                sql = """INSERT INTO t_erp_sample_tech (sample_type, sample_id, due_date, style_pic, tag_pic, tag_pic_remark, patten_marker, patten_checker, cutting_tip, lining_tip, thread_tip, needle_tip, sewing_tip, back_garment_tip, tag_hanging_tip, ironing_tip, fabric_tip, seam_sealing_tip, hand_stitching_tip, hand_stitching_inspection, washing_tip, audit_status, audit_by, audit_time, create_by, create_time) 
VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, 'admin', NOW())"""
                
                # 处理空值
                def get_val(col):
                    val = row.get(col)
                    if pd.isna(val) or val == '':
                        return None
                    return str(val)
                
                # 这里需要根据实际Excel文件的列名来调整
                # 先尝试导入看看有什么列
                sample_type = get_val('打样类型：1-服装 2-毛衣') if '打样类型：1-服装 2-毛衣' in df.columns else None
                sample_id = get_val('打样id') if '打样id' in df.columns else None
                due_date = get_val('要求交期') if '要求交期' in df.columns else None
                
                # 先插入一条测试数据看看
                cursor.execute(sql, (
                    sample_type,
                    sample_id,
                    due_date,
                    None,  # style_pic
                    None,  # tag_pic
                    None,  # tag_pic_remark
                    None,  # patten_marker
                    None,  # patten_checker
                    None,  # cutting_tip
                    None,  # lining_tip
                    None,  # thread_tip
                    None,  # needle_tip
                    None,  # sewing_tip
                    None,  # back_garment_tip
                    None,  # tag_hanging_tip
                    None,  # ironing_tip
                    None,  # fabric_tip
                    None,  # seam_sealing_tip
                    None,  # hand_stitching_tip
                    None,  # hand_stitching_inspection
                    None,  # washing_tip
                    None,  # audit_status
                    None,  # audit_by
                    None   # audit_time
                ))
                
                success_count += 1
                
                if success_count % 100 == 0:
                    conn.commit()
                    print(f"已导入 {success_count} 条数据...")
                    
            except Exception as e:
                fail_count += 1
                print(f"第 {idx+1} 条数据导入失败: {e}")
                # 只打印前5个错误
                if fail_count <= 5:
                    print(f"错误详情: {str(e)[:200]}")
        
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
