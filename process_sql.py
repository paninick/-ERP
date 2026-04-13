import subprocess
import os
import re

def run_git_command(cmd):
    process = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    stdout, stderr = process.communicate()
    
    try:
        output = stdout.decode('utf-8', 'replace')
    except Exception as e:
        output = stdout.decode('gbk', 'replace')
    
    try:
        error = stderr.decode('utf-8', 'replace')
    except Exception as e:
        error = stderr.decode('gbk', 'replace')
    
    return process.returncode, output, error

def main():
    git_cmd = 'cd "d:\\erp\\RuoYi-Vue" && git show HEAD:sql/ry_20260321.sql'
    result = run_git_command(git_cmd)
    
    if result[0] == 0:
        content = result[1]
        # 使用更简单的方法处理内容
        processed_content = ''
        for line in content.splitlines():
            # 替换不可见字符为空格
            processed_line = re.sub(r'[^\x20-\x7F]', lambda m: ' ', line)
            # 处理中文
            processed_line = re.sub(r'[\u4e00-\u9fff]+', '', processed_line)
            processed_content += processed_line + '\n'
        
        # 写入到临时文件
        temp_file = 'd:\\erp\\RuoYi-Vue\\sql\\ry_20260321_processed.sql'
        with open(temp_file, 'w', encoding='utf-8', errors='ignore') as f:
            f.write(processed_content)
            
        print(f"Processed SQL file saved to: {temp_file}")
        
        # 执行SQL文件
        mysql_cmd = r'cd "C:\Program Files\MySQL\MySQL Server 8.4\bin" && mysql -u root ry_vue < "d:\erp\RuoYi-Vue\sql\ry_20260321_processed.sql"'
        mysql_result = run_git_command(mysql_cmd)
        
        if mysql_result[0] == 0:
            print("MySQL import successful")
        else:
            print(f"MySQL import failed: {mysql_result[2]}")
    else:
        print(f"Git command failed: {result[2]}")

if __name__ == "__main__":
    main()