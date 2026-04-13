import os
with open('sql/ry_20260321_git_raw.sql', 'rb') as f:
    content = f.read()

print("--- Checking UTF-8 encoding ---")
try:
    decoded = content.decode('utf-8')
    print("Success")
    print(decoded[:200])  # Print first 200 characters
except Exception as e:
    print(f"Failed: {e}")

print("\n--- Checking UTF-8-SIG encoding ---")
try:
    decoded = content.decode('utf-8-sig')
    print("Success")
    print(decoded[:200])
except Exception as e:
    print(f"Failed: {e}")

print("\n--- Checking GBK encoding ---")
try:
    decoded = content.decode('gbk')
    print("Success")
    print(decoded[:200])
except Exception as e:
    print(f"Failed: {e}")
