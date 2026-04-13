import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sqls = [
    "SELECT user_id, user_name, nick_name, dept_id FROM sys_user WHERE user_name='admin';",
    "SELECT user_id, user_name, nick_name FROM sys_user WHERE user_name IN ('admin','ry','fq') ORDER BY user_id;",
    "SELECT user_name, COUNT(*) as cnt FROM sys_user GROUP BY user_name HAVING cnt > 1;",
]

for sql in sqls:
    print(f"\n--- SQL: {sql[:60]}... ---")
    proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                           stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    out, _ = proc.communicate(input=sql.encode('utf-8'))
    print(out.decode('utf-8').strip())
