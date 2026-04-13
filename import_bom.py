import pandas as pd
import numpy as np

# 读取Excel文件
df = pd.read_excel(r'C:\Users\91306\Downloads\样衣BOM_全量导出_2026-04-11.xlsx')

print(f"总行数: {len(df)}")
print(f"列名: {list(df.columns)}")

# 表头映射关系
# Excel表头 -> 数据库字段
mapping = {
    '审批状态': 'audit_status',
    '进行状态': 'progress_status',
    '客户': 'customer_name',
    '打样类型': 'sample_type',
    '样品款式': 'style_type',
    '样品类型': 'sample_category_type',
    '款号': 'style_code',
    '大货款号': 'bulk_order_no',
    '业务员': 'sales_name'
}

# 需要插入的所有字段（包含Excel没有的字段，设为NULL）
all_fields = [
    'id', 'sample_no', 'sample_type', 'customer_id', 'customer_name', 
    'style_category', 'style_sub_category', 'style_type', 'sample_category_type',
    'style_code', 'due_date', 'emergency_type', 'picture_url', 'audit_status',
    'audit_by', 'audit_by_nick_name', 'audit_time', 'process_instance_id',
    'bulk_order_no', 'sales_id', 'sales_name', 'progress_status', 'tech_no',
    'create_by', 'create_time', 'update_by', 'update_time', 'remark'
]

# 生成INSERT语句
output_file = r'd:\erp\import_bom_data.sql'

with open(output_file, 'w', encoding='utf-8') as f:
    f.write("-- 导入正式库样衣BOM数据到测试库\n")
    f.write("-- 生成时间: 2026-04-11\n")
    f.write("-- 总记录数: " + str(len(df)) + "\n\n")
    f.write("-- 先清空原有数据\n")
    f.write("TRUNCATE TABLE t_erp_bom;\n\n")
    f.write("-- 插入数据\n")
    
    # 分批插入，每批100条
    batch_size = 100
    total_batches = (len(df) + batch_size - 1) // batch_size
    
    for batch_idx in range(total_batches):
        start_idx = batch_idx * batch_size
        end_idx = min((batch_idx + 1) * batch_size, len(df))
        batch_df = df.iloc[start_idx:end_idx]
        
        f.write(f"INSERT INTO t_erp_bom ({', '.join(all_fields)}) VALUES\n")
        
        values_list = []
        for idx, (_, row) in enumerate(batch_df.iterrows()):
            # 自增ID从1开始
            row_id = start_idx + idx + 1
            
            # 构建值列表
            values = []
            values.append(str(row_id))  # id
            
            # sample_no (打样编号) - NULL
            values.append("NULL")
            
            # sample_type (打样类型)
            if pd.notna(row['打样类型']):
                val = str(row['打样类型']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # customer_id - NULL
            values.append("NULL")
            
            # customer_name (客户)
            if pd.notna(row['客户']):
                val = str(row['客户']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # style_category - NULL
            values.append("NULL")
            
            # style_sub_category - NULL
            values.append("NULL")
            
            # style_type (样品款式)
            if pd.notna(row['样品款式']):
                val = str(row['样品款式']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sample_category_type (样品类型)
            if pd.notna(row['样品类型']):
                val = str(row['样品类型']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # style_code (款号)
            if pd.notna(row['款号']):
                val = str(row['款号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # due_date - NULL
            values.append("NULL")
            
            # emergency_type - NULL
            values.append("NULL")
            
            # picture_url - NULL
            values.append("NULL")
            
            # audit_status (审批状态)
            if pd.notna(row['审批状态']):
                val = str(row['审批状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # audit_by - NULL
            values.append("NULL")
            
            # audit_by_nick_name - NULL
            values.append("NULL")
            
            # audit_time - NULL
            values.append("NULL")
            
            # process_instance_id - NULL
            values.append("NULL")
            
            # bulk_order_no (大货款号)
            if pd.notna(row['大货款号']):
                val = str(row['大货款号']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # sales_id - NULL
            values.append("NULL")
            
            # sales_name (业务员)
            if pd.notna(row['业务员']):
                val = str(row['业务员']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # progress_status (进行状态)
            if pd.notna(row['进行状态']):
                val = str(row['进行状态']).replace("'", "''")
                values.append(f"'{val}'")
            else:
                values.append("NULL")
            
            # tech_no (工艺书编号) - NULL
            values.append("NULL")
            
            # create_by - NULL
            values.append("NULL")
            
            # create_time - 当前时间
            values.append("NOW()")
            
            # update_by - NULL
            values.append("NULL")
            
            # update_time - 当前时间
            values.append("NOW()")
            
            # remark - NULL
            values.append("NULL")
            
            values_list.append(f"({', '.join(values)})")
        
        f.write(',\n'.join(values_list) + ";\n\n")
        print(f"已生成批次 {batch_idx + 1}/{total_batches} ({len(batch_df)} 条记录)")

print(f"\nSQL文件已生成: {output_file}")
print(f"总记录数: {len(df)}")
print("映射关系:")
for excel_col, db_col in mapping.items():
    print(f"  {excel_col} -> {db_col}")
