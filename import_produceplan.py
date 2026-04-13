import pandas as pd
import numpy as np

# 读取Excel文件
df = pd.read_excel(r'C:\Users\91306\Downloads\生产计划_全量导出_2026-04-11 (1).xlsx')

print(f"总行数: {len(df)}")
print(f"列名: {list(df.columns)}")

# Excel表头 -> 数据库字段映射
mapping = {
    '生产状态': 'produce_status',
    '类型': 'type',
    '生产计划编号': 'plan_no',
    '大货款号': 'bulk_order_no',
    '打样款号': 'sample_style_no',
    '客户名称': 'customer_name',
    '款式/品类': 'style_category',
    '业务员': 'sales_name',
    '原料到货日期': 'material_arrival_date',
    '前道日期': 'pre_process_date',
    '送检日期': 'inspection_date',
    '进仓日期': 'in_bound_date',
}

# 需要插入的所有字段
all_fields = [
    'id', 
    'produce_status', 
    'type', 
    'plan_no', 
    'bulk_order_no', 
    'sample_style_no', 
    'customer_name', 
    'style_category', 
    'sales_name',
    'material_arrival_date', 
    'pre_process_date', 
    'inspection_date', 
    'in_bound_date',
    'sales_order_id', 
    'notice_id', 
    'tech_id', 
    'sales_id', 
    'start_date', 
    'picked_date', 
    'complete_date', 
    'up_date', 
    'down_date', 
    'produce_type', 
    'in_factory', 
    'out_factory', 
    'out_date', 
    'pre_produce_type', 
    'pre_in_factory', 
    'pre_out_factory', 
    'pre_out_date', 
    'post_produce_type', 
    'post_in_factory', 
    'post_out_factory', 
    'post_out_date',
]

# 生成INSERT语句
output_file = r'd:\erp\import_produceplan_data.sql'

with open(output_file, 'w', encoding='utf-8') as f:
    f.write("-- 导入正式库生产计划数据到测试库\n")
    f.write("-- 生成时间: 2026-04-12\n")
    f.write(f"-- 总记录数: {len(df)}\n")
    f.write("-- 清除原有数据\n\n")
    f.write("TRUNCATE TABLE t_erp_produce_plan;\n\n")
    f.write("-- 插入数据\n")
    
    # 分批插入，每批100条
    batch_size = 100
    total_batches = (len(df) + batch_size - 1) // batch_size
    
    for batch_idx in range(total_batches):
        start_idx = batch_idx * batch_size
        end_idx = min((batch_idx + 1) * batch_size, len(df))
        batch_df = df.iloc[start_idx:end_idx]
        
        f.write(f"INSERT INTO t_erp_produce_plan ({', '.join(all_fields)}) VALUES\n")
        
        values_list = []
        for idx, (_, row) in enumerate(batch_df.iterrows()):
            # 自增ID从1开始
            row_id = start_idx + idx + 1
            
            # 构建值列表
            values = []
            values.append(str(row_id))  # id
            
            # produce_status (生产状态)
            if pd.notna(row['生产状态']):
                val = str(row['生产状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # type (类型)
            if pd.notna(row['类型']):
                val = str(row['类型']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # plan_no (生产计划编号)
            if pd.notna(row['生产计划编号']):
                val = str(row['生产计划编号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # bulk_order_no (大货款号)
            if pd.notna(row['大货款号']):
                val = str(row['大货款号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sample_style_no (打样款号)
            if pd.notna(row['打样款号']):
                val = str(row['打样款号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # customer_name (客户名称)
            if pd.notna(row['客户名称']):
                val = str(row['客户名称']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # style_category (款式品类)
            if pd.notna(row['款式/品类']):
                val = str(row['款式/品类']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sales_name (业务员)
            if pd.notna(row['业务员']):
                val = str(row['业务员']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # material_arrival_date (原料到货日期)
            if pd.notna(row['原料到货日期']):
                val = str(row['原料到货日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # pre_process_date (前道日期)
            if pd.notna(row['前道日期']):
                val = str(row['前道日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # inspection_date (送检日期)
            if pd.notna(row['送检日期']):
                val = str(row['送检日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # in_bound_date (进仓日期)
            if pd.notna(row['进仓日期']):
                val = str(row['进仓日期']).split(' ')[0]
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sales_order_id - NULL
            values.append("NULL")
            
            # notice_id - NULL
            values.append("NULL")
            
            # tech_id - NULL
            values.append("NULL")
            
            # sales_id - NULL
            values.append("NULL")
            
            # start_date - NULL
            values.append("NULL")
            
            # picked_date - NULL
            values.append("NULL")
            
            # complete_date - NULL
            values.append("NULL")
            
            # up_date - NULL
            values.append("NULL")
            
            # down_date - NULL
            values.append("NULL")
            
            # produce_type - NULL
            values.append("NULL")
            
            # in_factory - NULL
            values.append("NULL")
            
            # out_factory - NULL
            values.append("NULL")
            
            # out_date - NULL
            values.append("NULL")
            
            # pre_produce_type - NULL
            values.append("NULL")
            
            # pre_in_factory - NULL
            values.append("NULL")
            
            # pre_out_factory - NULL
            values.append("NULL")
            
            # pre_out_date - NULL
            values.append("NULL")
            
            # post_produce_type - NULL
            values.append("NULL")
            
            # post_in_factory - NULL
            values.append("NULL")
            
            # post_out_factory - NULL
            values.append("NULL")
            
            # post_out_date - NULL
            values.append("NULL")
            
            values_list.append(f"({', '.join(values)})")
        
        f.write(',\n'.join(values_list) + ";\n\n")
        print(f"已生成批次 {batch_idx + 1}/{total_batches} ({len(batch_df)} 条记录")

print(f"\nSQL文件已生成: {output_file}")
print(f"总记录数: {len(df)}")
print("映射关系:")
for excel_col, db_col in mapping.items():
    print(f"  {excel_col} -> {db_col}")
