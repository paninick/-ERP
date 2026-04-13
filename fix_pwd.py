import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

cursor.execute("SELECT user_name, password FROM sys_user WHERE user_name = 'admin'")
row = cursor.fetchone()
print(f"用户: {row[0]}")
print(f"密码hash前60字符: {row[1][:60] if row[1] else 'NULL'}")
print(f"密码hash长度: {len(row[1]) if row[1] else 0}")

# 直接重置为已知正确的BCrypt hash (admin123)
# $2a$10$... 格式
new_pwd = '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2'
cursor.execute("UPDATE sys_user SET password = %s WHERE user_name = 'admin'", (new_pwd,))
conn.commit()
print(f"\n密码已重置为 admin123")
conn.close()
