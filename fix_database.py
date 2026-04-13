import subprocess
import os
import re

def main():
    # Step 1: 从git获取原始SQL文件（二进制模式）
    print("Step 1: Fetching SQL from git repository...")
    git_proc = subprocess.Popen(
        'git show HEAD:sql/ry_20260321.sql',
        shell=True, cwd=r'd:\erp\RuoYi-Vue',
        stdout=subprocess.PIPE, stderr=subprocess.PIPE
    )
    raw_bytes, _ = git_proc.communicate()
    
    if git_proc.returncode != 0:
        print("ERROR: Failed to fetch from git")
        return
    
    # Step 2: 检测并解码编码
    # Git输出的文件是UTF-16LE编码
    if raw_bytes[:2] == b'\xff\xfe':
        content = raw_bytes.decode('utf-16-le')
        print(f"Detected encoding: UTF-16-LE (file size: {len(raw_bytes)} bytes)")
    else:
        content = raw_bytes.decode('utf-8', errors='replace')
        print("Detected encoding: UTF-8")
    
    # Step 3: 智能处理SQL - 保留中文内容，只修复语法问题
    lines = content.split('\n')
    cleaned_lines = []
    in_multiline_comment = False
    
    for line in lines:
        stripped = line.strip()
        
        # 处理多行注释
        if in_multiline_comment:
            if '*/' in stripped:
                in_multiline_comment = False
                after = stripped.split('*/', 1)[1].strip()
                if after:
                    cleaned_lines.append(after)
            continue
        
        if stripped.startswith('/*'):
            if '*/' in stripped:
                after = stripped.split('*/', 1)[1].strip()
                if after:
                    cleaned_lines.append(after)
            else:
                in_multiline_comment = True
            continue
        
        # 跳过单行注释（--开头）
        if stripped.startswith('--'):
            continue
        
        # 处理行内注释和comment子句中的问题字符
        # 保留中文，只移除导致SQL解析问题的控制字符
        safe_line = ''
        for ch in line:
            code = ord(ch)
            # 保留：可打印ASCII、中文(CJK)、基本标点、换行
            if (0x20 <= code <= 0x7E) or (0x4E00 <= code <= 0x9FFF) or \
               (0x3000 <= code <= 0x303F) or (0xFF00 <= code <= 0xFFEF) or \
               code in (0x09, 0x0A, 0x0D):
                safe_line += ch
            elif code < 0x20 and code not in (0x09, 0x0A, 0x0D):
                # 控制字符替换为空格
                safe_line += ' '
        
        if safe_line.strip():
            cleaned_lines.append(safe_line)
    
    output_file = r'd:\erp\RuoYi-Vue\sql\ry_20260321_fixed.sql'
    with open(output_file, 'w', encoding='utf-8') as f:
        f.write('\n'.join(cleaned_lines))
    
    print(f"Step 2: Fixed SQL saved to {output_file}")
    print(f"Total lines: {len(cleaned_lines)}")
    
    # 验证中文是否保留
    chinese_count = sum(1 for c in '\n'.join(cleaned_lines) if '\u4e00' <= c <= '\u9fff')
    print(f"Chinese characters preserved: {chinese_count}")
    
    # Step 4: 执行SQL到MySQL
    print("\nStep 3: Executing SQL to MySQL...")
    mysql_cmd = (
        r'cd "C:\Program Files\MySQL\MySQL Server 8.4\bin" && '
        r'mysql -u root ry_vue < "d:\erp\RuoYi-Vue\sql\ry_20260321_fixed.sql"'
    )
    proc = subprocess.Popen(mysql_cmd, shell=True,
                            stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, err = proc.communicate()
    
    if proc.returncode == 0:
        print("SUCCESS: Database initialized completely!")
    else:
        error_msg = err.decode('utf-8', errors='replace') if err else 'Unknown error'
        print(f"ERROR: {error_msg}")

if __name__ == '__main__':
    main()
