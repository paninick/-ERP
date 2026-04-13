import subprocess
import os

def main():
    sql_file = r'd:\erp\RuoYi-Vue\sql\ry_20260321_fixed.sql'
    
    with open(sql_file, 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    print(f"Read SQL: {len(sql_content)} chars")
    
    mysql_exe = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'
    
    # 添加 --default-character-set=utf8mb4 参数
    proc = subprocess.Popen(
        [mysql_exe, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )
    
    stdout, stderr = proc.communicate(input=sql_content.encode('utf-8'))
    
    out_str = stdout.decode('utf-8', errors='replace')
    err_str = stderr.decode('utf-8', errors='replace')
    
    if proc.returncode == 0:
        print("SUCCESS!")
    else:
        print("ERROR:")
        # 只打印最后500个字符的错误信息
        print(err_str[-800:] if err_str else "Unknown")

if __name__ == '__main__':
    main()
