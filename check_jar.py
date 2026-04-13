import os

repo_path = os.path.join(os.path.expanduser("~"), ".m2", "repository", "com", "ruoyi", "ruoyi-framework", "3.9.2")
class_path = os.path.join(repo_path, "com", "ruoyi", "framework", "security", "filter", "JwtAuthenticationTokenFilter.class")

with open(class_path, 'rb') as f:
    data = f.read()

print(f"文件大小: {len(data)} bytes")
print(f"\n包含 'flowable': {b'flowable' in data}")
print(f"包含 'ANONYMOUS': {b'ANONYMOUS' in data}")
print(f"包含 'AnonymousAuthenticationToken': {b'AnonymousAuthenticationToken' in data}")
print(f"包含 'ANONYMOUS_PATHS': {b'ANONYMOUS_PATHS' in data}")

if b'ANONYMOUS_PATHS' in data:
    print("\n✅ 修改已包含在jar中!")
else:
    print("\n❌ 修改未包含在jar中! Maven缓存问题!")
