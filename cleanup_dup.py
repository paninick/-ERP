import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

clean_sql = """
DELETE t1 FROM sys_user t1
INNER JOIN sys_user t2
WHERE t1.user_id > t2.user_id AND t1.user_name = t2.user_name;
"""

proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, err = proc.communicate(input=clean_sql.encode('utf-8'))
print(f"清理重复: {out.decode('utf-8').strip()}")

verify = "SELECT COUNT(*) as total FROM sys_user;"
proc2 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out2, _ = proc2.communicate(input=verify.encode('utf-8'))
print(f"剩余用户: {out2.decode('utf-8').strip()}")

dup_check = "SELECT user_name, COUNT(*) as cnt FROM sys_user GROUP BY user_name HAVING cnt > 1;"
proc3 = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                        stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out3, _ = proc3.communicate(input=dup_check.encode('utf-8'))
dups = out3.decode('utf-8').strip()
print(f"重复检查: {'无重复 ✅' if not dups or dups == '' else dups}")
