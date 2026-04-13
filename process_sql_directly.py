import sys
import re
import os
import subprocess

def process_sql():
    # 执行 git show 命令获取原始SQL内容（二进制模式）
    try:
        git_process = subprocess.Popen('git show HEAD:sql/ry_20260321.sql', 
                                     shell=True, 
                                     stdout=subprocess.PIPE, 
                                     stderr=subprocess.PIPE)
        stdout, stderr = git_process.communicate()
        
        if git_process.returncode != 0:
            print(f"Error: Git command failed - {stderr.decode('utf-8', errors='ignore')}")
            return False
            
    except Exception as e:
        print(f"Error executing git command: {e}")
        return False
        
    # 解码内容
    try:
        git_result = stdout.decode('utf-8', errors='replace')
    except:
        git_result = stdout.decode('latin-1', errors='replace')
        
    # 移除所有中文注释
    cleaned_content = []
    
    for line in git_result.splitlines():
        if not line.strip():
            continue
            
        # 移除 -- 注释（包括中文）
        if '--' in line:
            line = line.split('--', 1)[0].strip()
            if not line:
                continue
                
        # 移除块注释
        if '/*' in line and '*/' in line:
            parts = line.split('/*', 1)
            first_part = parts[0]
            second_part = parts[1].split('*/', 1)[1]
            line = (first_part + second_part).strip()
            if not line:
                continue
                
        # 移除 comment 子句中的中文
        line = re.sub(r'comment\s*[\'"][^\'"]*[\'"]', '', line)
        
        # 只保留有效的ASCII字符
        clean_line = []
        for c in line:
            if ord(c) < 128:
                clean_line.append(c)
            elif c.isspace():
                clean_line.append(c)
                
        clean_line_str = ''.join(clean_line)
        
        if clean_line_str.strip():
            cleaned_content.append(clean_line_str)

    # 保存到文件
    with open('sql/ry_20260321_ultimate_final.sql', 'w', encoding='utf-8', errors='ignore') as f:
        f.write('\n'.join(cleaned_content))
        
    print("SQL file processed successfully: sql/ry_20260321_ultimate_final.sql")
    
    # 执行SQL文件
    mysql_cmd = r'cd "C:\Program Files\MySQL\MySQL Server 8.4\bin" && mysql -u root ry_vue < "d:\erp\RuoYi-Vue\sql\ry_20260321_ultimate_final.sql"'
    try:
        mysql_process = subprocess.Popen(mysql_cmd, 
                                       shell=True, 
                                       stdout=subprocess.PIPE, 
                                       stderr=subprocess.PIPE)
        mysql_out, mysql_err = mysql_process.communicate()
        
        if mysql_process.returncode == 0:
            print("SQL script executed successfully")
            return True
        else:
            print(f"Error executing SQL script: {mysql_err.decode('latin-1', errors='ignore')}")
            return False
            
    except Exception as e:
        print(f"Error executing SQL script: {e}")
        return False

if __name__ == "__main__":
    process_sql()
