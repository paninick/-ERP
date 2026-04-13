import pandas as pd

def generate_sql(excel_file, output_sql_file):
    # 读取Excel文件
    print(f"正在读取Excel文件: {excel_file}")
    df = pd.read_excel(excel_file)
    print(f"成功读取 {len(df)} 条数据")
    
    sql_statements = []
    
    # 处理空值的函数
    def get_val(col):
        val = col
        if pd.isna(val) or val == '' or str(val).strip() == 'nan':
            return 'NULL'
        val_str = str(val).strip().replace("'", "''")
        return f"'{val_str}'"
    
    print(f"\n开始生成SQL...")
    
    for idx, row in df.iterrows():
        # 获取各列数据
        sample_type = get_val(row.get('打样类型：1-服装 2-毛衣'))
        sample_id = get_val(row.get('打样id'))
        due_date = get_val(row.get('要求交期'))
        style_pic = get_val(row.get('款式图'))
        tag_pic = get_val(row.get('订标位置示意图'))
        tag_pic_remark = get_val(row.get('订表位置说明'))
        patten_marker = get_val(row.get('制版人id'))
        patten_checker = get_val(row.get('核版人id'))
        cutting_tip = get_val(row.get('裁剪要求'))
        lining_tip = get_val(row.get('用衬要求'))
        thread_tip = get_val(row.get('用线要求'))
        needle_tip = get_val(row.get('运针要求'))
        sewing_tip = get_val(row.get('缝制工艺说明'))
        back_garment_tip = get_val(row.get('后套工艺说明'))
        tag_hanging_tip = get_val(row.get('吊牌挂法'))
        ironing_tip = get_val(row.get('整烫要求'))
        fabric_tip = get_val(row.get('织造要求'))
        seam_sealing_tip = get_val(row.get('套口要求'))
        hand_stitching_tip = get_val(row.get('手缝要求'))
        hand_stitching_inspection = get_val(row.get('套口手缝检验'))
        washing_tip = get_val(row.get('水洗要求'))
        audit_status = get_val(row.get('审批状态'))
        audit_by = get_val(row.get('审批人'))
        audit_time = get_val(row.get('审批时间'))
        
        # 构建SQL
        sql = f"""INSERT INTO t_erp_sample_tech (
            sample_type, sample_id, due_date, style_pic, tag_pic, tag_pic_remark, 
            patten_marker, patten_checker, cutting_tip, lining_tip, thread_tip, 
            needle_tip, sewing_tip, back_garment_tip, tag_hanging_tip, ironing_tip, 
            fabric_tip, seam_sealing_tip, hand_stitching_tip, hand_stitching_inspection, 
            washing_tip, audit_status, audit_by, audit_time, create_by, create_time
        ) VALUES (
            {sample_type}, {sample_id}, {due_date}, {style_pic}, {tag_pic}, {tag_pic_remark},
            {patten_marker}, {patten_checker}, {cutting_tip}, {lining_tip}, {thread_tip},
            {needle_tip}, {sewing_tip}, {back_garment_tip}, {tag_hanging_tip}, {ironing_tip},
            {fabric_tip}, {seam_sealing_tip}, {hand_stitching_tip}, {hand_stitching_inspection},
            {washing_tip}, {audit_status}, {audit_by}, {audit_time}, 'admin', NOW()
        );"""
        
        sql_statements.append(sql)
        
        if (idx + 1) % 1000 == 0:
            print(f"已生成 {idx + 1} 条SQL...")
    
    # 写入SQL文件
    with open(output_sql_file, 'w', encoding='utf-8') as f:
        f.write("USE ry_vue;\n\n")
        f.write("-- 清空现有数据\n")
        f.write("DELETE FROM t_erp_sample_tech;\n\n")
        f.write("-- 导入新数据\n")
        for sql in sql_statements:
            f.write(sql + "\n")
    
    print(f"\n成功生成 {len(sql_statements)} 条SQL语句到 {output_sql_file}")

if __name__ == "__main__":
    excel_file = r"C:\Users\91306\Downloads\工艺指示书_全量导出_2026-04-11.xlsx"
    output_sql_file = r"d:\erp\import_sample_tech_data.sql"
    generate_sql(excel_file, output_sql_file)
