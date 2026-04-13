import mysql.connector
import bcrypt

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

cursor.execute("SELECT password FROM sys_user WHERE user_name = 'admin'")
row = cursor.fetchone()
stored_hash = row[0]

print(f"存储的hash: {stored_hash}")

# 验证 admin123
test_pwd = "admin123"
try:
    # MySQL存储的是$2a$前缀，bcrypt用$2b$
    test_hash = stored_hash.replace("$2a$", "$2b$")
    match = bcrypt.checkpw(test_pwd.encode('utf-8'), test_hash.encode('utf-8'))
    print(f"\n密码 'admin123' 匹配: {match}")
except Exception as e:
    print(f"验证错误: {e}")

# 重置为 admin123
print("\n=== 重置admin密码为 admin123 ===")
new_hash = bcrypt.hashpw(b"admin123", bcrypt.gensalt()).decode()
# 转换回 $2a$ 格式
new_hash = new_hash.replace("$2b$", "$2a$")
cursor.execute("UPDATE sys_user SET password = %s WHERE user_name = 'admin'", (new_hash,))
conn.commit()
print(f"新hash: {new_hash}")
print("密码已重置!")

# 验证
cursor.execute("SELECT password FROM sys_user WHERE user_name = 'admin'")
row = cursor.fetchone()
verify = row[0].replace("$2a$", "$2b$")
match = bcrypt.checkpw(b"admin123", verify.encode('utf-8'))
print(f"验证结果: {match}")

conn.close()
