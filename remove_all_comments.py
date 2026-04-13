import os
import re

def remove_all_comments(input_file, output_file):
    try:
        with open(input_file, 'rb') as f:
            content = f.read()
            
        # 尝试解码
        decoded_content = content.decode('utf-8', errors='ignore')
        
        # 移除SQL注释
        content_no_comments = []
        
        for line in decoded_content.splitlines():
            # 移除 -- 注释
            line = re.sub(r'--[^\n]*', '', line)
            
            # 移除字段和表注释，但保留SQL语法
            # 移除字段注释，如 comment '閮ㄩ棬id'
            line = re.sub(r'comment\s*\'[^\']*\'', '', line)
            # 移除中文括号，如 锛?, 锛?
            line = re.sub(r'[^\x00-\x7F]', '', line)
            
            # 处理 /* */ 多行注释
            line = re.sub(r'/\*[\s\S]*?\*/', '', line)
            
            # 移除空行
            if line.strip():
                content_no_comments.append(line.strip())
        
        # 保存到输出文件
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write('\n'.join(content_no_comments) + '\n')
            
        print("Successfully removed all comments")
        return True
        
    except Exception as e:
        print(f"Error: {e}")
        return False

if __name__ == "__main__":
    input_file = "sql/ry_20260321_utf8.sql"
    output_file = "sql/ry_20260321_clean.sql"
    remove_all_comments(input_file, output_file)
