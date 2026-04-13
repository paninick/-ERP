import os
with open('sql/ry_20260321_git_raw.sql', 'rb') as f:
    content = f.read()

# 解码UTF-16LE文件
try:
    utf16_content = content.decode('utf-16le')
    
    # 清理并重新编码为UTF-8
    clean_content = ''
    for line in utf16_content.splitlines():
        # 替换不可见字符为空格，保留中文
        clean_line = line.strip()
        if clean_line:
            clean_content += clean_line + '\n'
    
    # 保存为UTF-8编码的文件
    with open('sql/ry_20260321_utf8.sql', 'w', encoding='utf-8') as f:
        f.write(clean_content)
        
    print("Successfully converted to UTF-8 encoding")
except Exception as e:
    print(f"Error: {e}")
