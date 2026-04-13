import subprocess

MYSQL = r'C:\Program Files\MySQL\MySQL Server 8.4\bin\mysql.exe'

sql = "SELECT menu_id, menu_name FROM sys_menu WHERE menu_id IN (5,500,501,502,503,504,505,506,2000,2001,2002,2003,2010,2011,2012,2013,2014,2020,2021,2022,2023,2024) ORDER BY menu_id;"
proc = subprocess.Popen([MYSQL, '-u', 'root', '--default-character-set=utf8mb4', 'ry_vue'],
                       stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
out, _ = proc.communicate(input=sql.encode('utf-8'))
print(f"已占用ID:\n{out.decode('utf-8').strip()}")
