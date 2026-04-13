import bcrypt
import mysql.connector

conn = mysql.connector.connect(host='localhost', port=3306, user='root', password='', database='ry_vue')
cursor = conn.cursor()
cursor.execute("SELECT password FROM sys_user WHERE user_name = 'admin'")
stored = cursor.fetchone()[0]
conn.close()

print(f"Stored hash: {stored}")

# Test with admin123
pwd = b"admin123"
try:
    # Try with $2a$ prefix as-is
    match = bcrypt.checkpw(pwd, stored.encode('utf-8'))
    print(f"bcrypt.checkpw('admin123', hash): {match}")
except Exception as e:
    print(f"Error: {e}")

# Also test generating a new hash and comparing
new_hash = bcrypt.hashpw(pwd, bcrypt.gensalt(rounds=12))
print(f"\nNew hash (rounds=12): {new_hash.decode()}")
match2 = bcrypt.checkpw(pwd, new_hash)
print(f"Self-check new hash: {match2}")

# Check if the issue is $2a vs $2b prefix
try:
    fixed = stored.replace('$2a$', '$2b$')
    match3 = bcrypt.checkpw(pwd, fixed.encode('utf-8'))
    print(f"With $2b$ prefix: {match3}")
except Exception as e:
    print(f"$2b$ error: {e}")

# Generate a proper hash for admin123
proper_hash = bcrypt.hashpw(pwd, bcrypt.gensalt()).decode()
print(f"\nProper hash to use: {proper_hash}")
