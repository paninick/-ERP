import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sql = """
DELETE FROM sys_user WHERE dept_id = 100 AND user_name != 'admin';
"""
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, _ = proc.communicate(input=sql.encode('utf-8'))
print(f"清理根部门多余用户: {out.decode('utf-8').strip()}")

verify = """
SELECT 
  (SELECT COUNT(*) FROM sys_user WHERE del_flag='0') AS 总用户数,
  (SELECT COUNT(*) FROM sys_user WHERE del_flag='0' AND dept_id=100) AS 根部门,
  (SELECT COUNT(*) FROM sys_user WHERE del_flag='0' AND dept_id!=100) AS 已分配部门;
"""
proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=verify.encode('utf-8'))
print(f"\n{out2.decode('utf-8').strip()}")
