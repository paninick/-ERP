import os
import re

def remove_comments_and_fix_encoding(input_file, output_file):
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
            
            # 处理 /* */ 多行注释
            # 简化处理，假设每行最多一个块注释
            line = re.sub(r'/\*[\s\S]*?\*/', '', line)
            
            # 移除空行
            if line.strip():
                content_no_comments.append(line.strip())
        
        # 保存到输出文件
        with open(output_file, 'w', encoding='utf-8') as f:
            f.write('\n'.join(content_no_comments) + '\n')
            
        print("Successfully removed comments")
        return True
        
    except Exception as e:
        print(f"Error: {e}")
        return False

if __name__ == "__main__":
    input_file = "sql/ry_20260321_utf8.sql"
    output_file = "sql/ry_20260321_no_comments.sql"
    remove_comments_and_fix_encoding(input_file, output_file)
