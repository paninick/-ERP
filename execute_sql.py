import subprocess
import os

def main():
    sql_file = r'd:\erp\create_bom_table.sql'
    
    # 读取修复后的SQL文件
    with open(sql_file, 'r', encoding='utf-8') as f:
        sql_content = f.read()
    
    print(f"Read SQL file: {len(sql_content)} characters")
    
    # 使用mysql命令行工具执行
    # 通过stdin传递SQL内容以避免编码问题
    mysql_exe = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'
    
    # 检查mysql.exe是否存在
    if not os.path.exists(mysql_exe):
        # 尝试其他常见路径
        mysql_exe = r'C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe'
        if not os.path.exists(mysql_exe):
            print(f"ERROR: MySQL executable not found at {mysql_exe}")
            return
    
    print(f"Using MySQL executable: {mysql_exe}")
    
    proc = subprocess.Popen(
        [mysql_exe, '-u', 'root', 'ry_vue'],
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE,
        encoding='utf-8',
        errors='replace'
    )
    
    stdout, stderr = proc.communicate(input=sql_content)
    
    if proc.returncode == 0:
        print("SUCCESS: SQL executed completely!")
        if stdout.strip():
            print("Output:", stdout[:500])
    else:
        print("ERROR during SQL execution:")
        print(stderr[-1000:] if stderr else "Unknown error")

if __name__ == '__main__':
    main()
