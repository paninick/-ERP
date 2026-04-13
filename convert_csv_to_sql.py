import csv
import re

# 定义字典映射
audit_status_map = {
    '审批通过': '3',
    '': None
}

sample_type_map = {
    '拼接': '1',
    '服饰': '2',
    '毛衫': '3'
}

style_type_map = {
    '拼接': '1',
    '针织': '2',
    '梭织': '3',
    '毛衣': '4'
}

sample_category_map = {
    '排料': '1',
    '摄影样': '2',
    '初样': '3',
    '产前样': '4',
    '大货量产': '5',
    '二次样': '6',
    '修正样': '7',
    '织片': '8',
    '三次样': '9',
    '检测样': '10',
    '四次样': '11'
}

def clean_value(value):
    if value is None:
        return None
    value = str(value).strip()
    if value == '' or value == 'nan':
        return None
    return value

def escape_sql(value):
    if value is None:
        return 'NULL'
    value = str(value).replace("'", "''")
    return f"'{value}'"

def convert_csv_to_sql(csv_file, output_sql_file):
    with open(csv_file, 'r', encoding='utf-8') as f:
        reader = csv.reader(f)
        rows = list(reader)
    
    # 跳过表头
    data_rows = rows[1:]
    
    sql_statements = []
    
    for idx, row in enumerate(data_rows, 1):
        # CSV列: (空), 审批状态, 进行状态, 客户, 打样类型, 样品款式, 样品类型, 款号, 大货款号, 业务员, 要求交期, 工艺书编号, 打样编号
        if len(row) < 13:
            continue
            
        audit_status = clean_value(row[1])
        progress_status = clean_value(row[2])
        customer_name = clean_value(row[3])
        sample_type = clean_value(row[4])
        style_type = clean_value(row[5])
        sample_category = clean_value(row[6])
        style_no = clean_value(row[7])
        bulk_order_no = clean_value(row[8])
        sales_name = clean_value(row[9])
        due_date = clean_value(row[10])
        tech_no = clean_value(row[11])
        sample_no = clean_value(row[12])
        
        # 映射字典值
        audit_status_code = audit_status_map.get(audit_status, None)
        sample_type_code = sample_type_map.get(sample_type, None)
        style_type_code = style_type_map.get(style_type, None)
        sample_category_code = sample_category_map.get(sample_category, None)
        
        # 生成check_no
        check_no = f"CHECK{idx:06d}"
        
        # 构建SQL
        sql = f"""INSERT INTO t_erp_check (check_no, audit_status, progress_status, customer_name, sample_type, style_type, sample_category_type, style_no, bulk_order_no, sales_name, due_date, tech_no, sample_no, create_by, create_time) 
VALUES ({escape_sql(check_no)}, {escape_sql(audit_status_code)}, {escape_sql(progress_status)}, {escape_sql(customer_name)}, {escape_sql(sample_type_code)}, {escape_sql(style_type_code)}, {escape_sql(sample_category_code)}, {escape_sql(style_no)}, {escape_sql(bulk_order_no)}, {escape_sql(sales_name)}, {escape_sql(due_date)}, {escape_sql(tech_no)}, {escape_sql(sample_no)}, 'admin', NOW());"""
        
        sql_statements.append(sql)
    
    # 写入SQL文件
    with open(output_sql_file, 'w', encoding='utf-8') as f:
        f.write("USE ry_vue;\n\n")
        f.write("-- 清空现有数据\n")
        f.write("DELETE FROM t_erp_check;\n\n")
        f.write("-- 导入新数据\n")
        for sql in sql_statements:
            f.write(sql + "\n")
    
    print(f"成功生成 {len(sql_statements)} 条SQL语句到 {output_sql_file}")

if __name__ == "__main__":
    csv_file = r"C:\Users\91306\Downloads\合并数据_2026-04-11.csv"
    output_sql_file = r"d:\erp\import_check_data.sql"
    convert_csv_to_sql(csv_file, output_sql_file)
