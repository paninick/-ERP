import os
import re

def clean_sql_file(input_file, output_file):
    try:
        with open(input_file, 'rb') as f:
            content = f.read()
            
        # 解码并清理
        decoded_content = content.decode('utf-8', errors='ignore')
        
        # 1. 移除所有注释
        # 移除 -- 注释，包括多行
        content_no_comments = []
        in_multi_line_comment = False
        
        for line in decoded_content.splitlines():
            line = line.strip()
            
            if in_multi_line_comment:
                if '*/' in line:
                    in_multi_line_comment = False
                    line = line.split('*/', 1)[1]
                else:
                    continue
                    
            # 处理块注释
            if '/*' in line:
                parts = line.split('/*', 1)
                content_no_comments.append(parts[0])
                if '*/' in parts[1]:
                    content_no_comments.append(parts[1].split('*/', 1)[1])
                else:
                    in_multi_line_comment = True
                continue
                
            # 移除 -- 注释
            if '--' in line:
                line = line.split('--', 1)[0].strip()
                
            if line:
                # 移除所有 comment 子句
                line = re.sub(r'comment\s*[\'"][^\'"]*[\'"]', '', line)
                line = re.sub(r'comment\s*[^\s,]+', '', line)
                content_no_comments.append(line)
        
        # 2. 合并并格式化 SQL
        cleaned_content = '\n'.join(content_no_comments)
        
        # 3. 移除多余的空格和换行
        cleaned_content = re.sub(r'\s+', ' ', cleaned_content)
        cleaned_content = re.sub(r'\s*,\s*', ', ', cleaned_content)
        cleaned_content = re.sub(r'\s*;\s*', ';\n', cleaned_content)
        
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write(cleaned_content)
            
        print(f"SQL file cleaned and saved to: {output_file}")
        return True
        
    except Exception as e:
        print(f"Error: {e}")
        return False

if __name__ == "__main__":
    input_file = "sql/ry_20260321_utf8.sql"
    output_file = "sql/ry_20260321_final_clean.sql"
    clean_sql_file(input_file, output_file)
