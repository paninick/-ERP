import sys
import re
import os

def fix_sql_encoding(input_file, output_file):
    try:
        # 尝试使用UTF-8编码读取文件
        with open(input_file, 'rb') as f:
            content_bytes = f.read()
        
        # 自动检测编码
        encoding = 'utf-8'
        if content_bytes.startswith(b'\xEF\xBB\xBF'):
            encoding = 'utf-8-sig'
        
        content = content_bytes.decode(encoding, 'replace')
        
        # 移除不可见字符和控制字符，但保留空格和换行
        clean_content = []
        for line in content.split('\n'):
            # 替换不可见字符
            clean_line = re.sub(r'[^\x20-\x7F]', '', line)
            clean_content.append(clean_line)
            
        clean_content_str = '\n'.join(clean_content)
        
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write(clean_content_str)
            
        print(f"SQL file {input_file} fixed and saved to {output_file}")
        return True
    except Exception as e:
        print(f"Error: {e}")
        return False

# 使用git获取原始内容
sql_file = 'sql/ry_20260321_original.sql'
fixed_file = 'sql/ry_20260321_python_fixed.sql'
if os.path.exists(sql_file):
    fix_sql_encoding(sql_file, fixed_file)
else:
    print("File not found")