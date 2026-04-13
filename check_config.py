import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()

print("=== 关键系统配置 ===")
configs = ['sys.account.captchaEnabled', 'sys.account.initPasswordModify', 'sys.account.passwordValidateDays', 'sys.login.blackIPList']
for c in configs:
    cursor.execute("SELECT config_value FROM sys_config WHERE config_key = %s", (c,))
    row = cursor.fetchone()
    print(f"  {c} = {row[0] if row else 'NOT FOUND'}")

conn.close()
