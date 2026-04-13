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
                
                # 构建SQL，逐个字段赋值，避免参数匹配问题
                sql_parts = []
                sql_values = []
                
                sql_parts.append("INSERT INTO t_erp_sample_tech (")
                sql_parts.append("create_by, create_time")
                sql_values.append("'admin'")
                sql_values.append("NOW()")
                
                if sample_type is not None:
                    sql_parts.append(", sample_type")
                    sql_values.append(f"'{sample_type.replace(\"'\", \"''\")}'")
                if sample_id is not None:
                    sql_parts.append(", sample_id")
                    sql_values.append(f"'{sample_id.replace(\"'\", \"''\")}'")
                if due_date is not None:
                    sql_parts.append(", due_date")
                    sql_values.append(f"'{due_date.replace(\"'\", \"''\")}'")
                if style_pic is not None:
                    sql_parts.append(", style_pic")
                    sql_values.append(f"'{style_pic.replace(\"'\", \"''\")}'")
                if tag_pic is not None:
                    sql_parts.append(", tag_pic")
                    sql_values.append(f"'{tag_pic.replace(\"'\", \"''\")}'")
                if tag_pic_remark is not None:
                    sql_parts.append(", tag_pic_remark")
                    sql_values.append(f"'{tag_pic_remark.replace(\"'\", \"''\")}'")
                if patten_marker is not None:
                    sql_parts.append(", patten_marker")
                    sql_values.append(f"'{patten_marker.replace(\"'\", \"''\")}'")
                if patten_checker is not None:
                    sql_parts.append(", patten_checker")
                    sql_values.append(f"'{patten_checker.replace(\"'\", \"''\")}'")
                if cutting_tip is not None:
                    sql_parts.append(", cutting_tip")
                    sql_values.append(f"'{cutting_tip.replace(\"'\", \"''\")}'")
                if lining_tip is not None:
                    sql_parts.append(", lining_tip")
                    sql_values.append(f"'{lining_tip.replace(\"'\", \"''\")}'")
                if thread_tip is not None:
                    sql_parts.append(", thread_tip")
                    sql_values.append(f"'{thread_tip.replace(\"'\", \"''\")}'")
                if needle_tip is not None:
                    sql_parts.append(", needle_tip")
                    sql_values.append(f"'{needle_tip.replace(\"'\", \"''\")}'")
                if sewing_tip is not None:
                    sql_parts.append(", sewing_tip")
                    sql_values.append(f"'{sewing_tip.replace(\"'\", \"''\")}'")
                if back_garment_tip is not None:
                    sql_parts.append(", back_garment_tip")
                    sql_values.append(f"'{back_garment_tip.replace(\"'\", \"''\")}'")
                if tag_hanging_tip is not None:
                    sql_parts.append(", tag_hanging_tip")
                    sql_values.append(f"'{tag_hanging_tip.replace(\"'\", \"''\")}'")
                if ironing_tip is not None:
                    sql_parts.append(", ironing_tip")
                    sql_values.append(f"'{ironing_tip.replace(\"'\", \"''\")}'")
                if fabric_tip is not None:
                    sql_parts.append(", fabric_tip")
                    sql_values.append(f"'{fabric_tip.replace(\"'\", \"''\")}'")
                if seam_sealing_tip is not None:
                    sql_parts.append(", seam_sealing_tip")
                    sql_values.append(f"'{seam_sealing_tip.replace(\"'\", \"''\")}'")
                if hand_stitching_tip is not None:
                    sql_parts.append(", hand_stitching_tip")
                    sql_values.append(f"'{hand_stitching_tip.replace(\"'\", \"''\")}'")
                if hand_stitching_inspection is not None:
                    sql_parts.append(", hand_stitching_inspection")
                    sql_values.append(f"'{hand_stitching_inspection.replace(\"'\", \"''\")}'")
                if washing_tip is not None:
                    sql_parts.append(", washing_tip")
                    sql_values.append(f"'{washing_tip.replace(\"'\", \"''\")}'")
                if audit_status is not None:
                    sql_parts.append(", audit_status")
                    sql_values.append(f"'{audit_status.replace(\"'\", \"''\")}'")
                if audit_by is not None:
                    sql_parts.append(", audit_by")
                    sql_values.append(f"'{audit_by.replace(\"'\", \"''\")}'")
                if audit_time is not None:
                    sql_parts.append(", audit_time")
                    sql_values.append(f"'{audit_time.replace(\"'\", \"''\")}'")
                
                sql_parts.append(") VALUES (")
                sql_parts.append(", ".join(sql_values))
                sql_parts.append(")")
                
                sql = "".join(sql_parts)
                
                cursor.execute(sql)
                
                success_count += 1
                
                if success_count % 100 == 0:
                    conn.commit()
                    print(f"已导入 {success_count} 条数据...")
                    
            except Exception as e:
                fail_count += 1
                if fail_count <= 10:
                    print(f"第 {idx+1} 条数据导入失败: {e}")
        
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
