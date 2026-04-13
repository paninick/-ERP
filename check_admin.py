import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

cursor.execute("SELECT user_id, user_name, nick_name, password, status, del_flag FROM sys_user WHERE user_name = 'admin'")
row = cursor.fetchone()
if row:
    print(f"user_id: {row[0]}")
    print(f"user_name: {row[1]}")
    print(f"nick_name: {row[2]}")
    print(f"password: {row[3]}")
    print(f"status: {row[4]}")
    print(f"del_flag: {row[5]}")

# 检查所有admin用户
print("\n--- 所有admin用户 ---")
cursor.execute("SELECT user_id, user_name, status, del_flag, password FROM sys_user WHERE user_name LIKE '%admin%' OR user_name LIKE '%Admin%'")
for r in cursor.fetchall():
    print(f"  {r}")

conn.close()
