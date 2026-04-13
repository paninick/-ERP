import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

print("=== 检查admin用户 ===")
cursor.execute("SELECT user_id, user_name, nick_name, password, status FROM sys_user WHERE user_name = 'admin'")
row = cursor.fetchone()
if row:
    print(f"  ID: {row[0]}")
    print(f"  用户名: {row[1]}")
    print(f"  昵称: {row[2]}")
    print(f"  密码(BCrypt): {row[3][:50]}...")
    print(f"  状态: {row[4]}")
else:
    print("  admin用户不存在!")

print("\n=== 所有管理员用户 ===")
cursor.execute("SELECT user_id, user_name, nick_name, status FROM sys_user WHERE status = '0' LIMIT 10")
for row in cursor.fetchall():
    print(f"  {row[0]} | {row[1]} | {row[2]}")

conn.close()
