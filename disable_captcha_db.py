import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

# 关闭验证码
cursor.execute("UPDATE sys_config SET config_value = 'false' WHERE config_key = 'sys.account.captchaEnabled'")
conn.commit()

# 验证
cursor.execute("SELECT config_value FROM sys_config WHERE config_key = 'sys.account.captchaEnabled'")
print(f"captchaEnabled = {cursor.fetchone()[0]}")

conn.close()
print("DONE! Verification code disabled.")
