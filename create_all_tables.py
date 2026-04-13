import os, re, subprocess

MAPPER_DIR = r"d:\erp\RuoYi-Vue\ruoyi-admin\src\main\resources\mapper\erp"
MYSQL = r"C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe"
DB_NAME = "ry_vue"

JAVA_TYPE_MAP = {
    'Long': 'bigint', 'Integer': 'int', 'Short': 'smallint',
    'String': 'varchar(500)', 'Date': 'datetime', 'LocalDateTime': 'datetime',
    'BigDecimal': 'decimal(18,4)', 'Float': 'float', 'Double': 'double',
    'boolean': 'tinyint(1)', 'Boolean': 'tinyint(1)',
}

BASE_ENTITY_COLS = [
    ("create_by", "varchar(64)", "''", "创建者"),
    ("create_time", "datetime", "NULL", "创建时间"),
    ("update_by", "varchar(64)", "''", "更新者"),
    ("update_time", "datetime", "NULL", "更新时间"),
    ("remark", "varchar(500)", "''", "备注"),
]

def parse_mapper_xml(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()
    
    # Extract table name from "from xxx" or "into xxx"
    table_match = re.search(r'(?:from|into)\s+([a-z_][a-z0-9_]*)', content)
    if not table_match:
        return None, []
    table_name = table_match.group(1)
    
    # Extract columns from resultMap
    cols = []
    result_map = re.search(r'<resultMap[^>]*>(.*?)</resultMap>', content, re.DOTALL)
    if result_map:
        for m in re.finditer(r'<result\s+property="(\w+)"\s+column="(\w+)"\s*/>', result_map.group(1)):
            prop, col = m.group(1), m.group(2)
            if prop in ('createBy','createTime','updateBy','updateTime','remark'):
                continue  # BaseEntity fields handled separately
            col_lower = col.lower()
            cols.append((col_lower, prop))
    
    return table_name, cols

def guess_type(prop_name):
    name_lower = prop_name.lower()
    if any(k in name_lower for k in ['id', 'num', 'order', 'sort']):
        return 'bigint'
    if any(k in name_lower for k in ['status', 'type', 'flag', 'enable']):
        return "char(1)"
    if any(k in name_lower for k in ['price', 'amount', 'cost', 'money', 'total', 'fee']):
        return 'decimal(18,2)'
    if any(k in name_lower for k in ['qty', 'quantity', 'count', 'stock', 'rate', 'percent']):
        return 'decimal(18,4)'
    if any(k in name_lower for k in ['time', 'date']):
        return 'datetime'
    if any(k in name_lower for k in ['url', 'path', 'file', 'image', 'photo', 'img', 'attachment']):
        return 'varchar(500)'
    if any(k in name_lower for k in ['no', 'code', 'sn', 'ref']):
        return 'varchar(64)'
    if any(k in name_lower for k in ['name', 'brief', 'desc', 'remark', 'note', 'address', 'tel', 'phone', 'fax', 'email', 'contact']):
        return 'varchar(200)'
    if any(k in name_lower for k in ['content', 'text', 'data', 'json', 'params', 'template']):
        return 'text'
    return 'varchar(255)'

def generate_ddl(table_name, columns):
    lines = [f"CREATE TABLE IF NOT EXISTS `{table_name}` ("]
    col_defs = []
    
    has_id = False
    for col_name, prop_name in columns:
        if col_name == 'id':
            col_defs.append(f"  `{col_name}` bigint NOT NULL AUTO_INCREMENT COMMENT '主键'")
            has_id = True
        else:
            dtype = guess_type(prop_name)
            comment = ''
            col_defs.append(f"  `{col_name}` {dtype} DEFAULT NULL COMMENT '{comment}'")
    
    # Add BaseEntity columns
    for bc_name, bc_type, bc_default, bc_comment in BASE_ENTITY_COLS:
        col_defs.append(f"  `{bc_name}` {bc_type} DEFAULT {bc_default} COMMENT '{bc_comment}'")
    
    if has_id:
        col_defs.append("  PRIMARY KEY (`id`) USING BTREE")
    
    lines.append(',\n'.join(col_defs))
    lines.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='';")
    return '\n'.join(lines)

def run_sql(sql):
    proc = subprocess.Popen(
        [MYSQL, "-u", "root", DB_NAME],
        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE,
        encoding='utf-8', text=True
    )
    out, err = proc.communicate(input=sql)
    if proc.returncode != 0 and 'error' in err.lower():
        print(f"  ERROR: {err.strip()[:200]}")
        return False
    return True

def main():
    all_sql = []
    created = 0
    
    for fname in sorted(os.listdir(MAPPER_DIR)):
        if not fname.endswith('Mapper.xml'):
            continue
        fpath = os.path.join(MAPPER_DIR, fname)
        table_name, columns = parse_mapper_xml(fpath)
        
        if not table_name or not columns:
            print(f"SKIP: {fname} (no table found)")
            continue
        
        ddl = generate_ddl(table_name, columns)
        all_sql.append(ddl)
        
        if run_sql(ddl):
            print(f"OK: {table_name} ({len(columns)+5} cols)")
            created += 1
        else:
            print(f"FAIL: {table_name}")
    
    # Also handle common/sys_dict table
    common_mapper_dir = os.path.join(os.path.dirname(MAPPER_DIR), "common")
    if os.path.exists(common_mapper_dir):
        for fname in sorted(os.listdir(common_mapper_dir)):
            if not fname.endswith('Mapper.xml'):
                continue
            fpath = os.path.join(common_mapper_dir, fname)
            table_name, columns = parse_mapper_xml(fpath)
            if table_name and columns:
                ddl = generate_ddl(table_name, columns)
                if run_sql(ddl):
                    print(f"OK: {table_name} (common)")
                    created += 1
    
    print(f"\n=== 总计创建/验证: {created} 张表 ===")

if __name__ == "__main__":
    main()
