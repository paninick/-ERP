import os
import zipfile

jar_path = r"d:\erp\RuoYi-Vue\ruoyi-admin\target\ruoyi-admin.jar"
extract_dir = r"d:\erp\RuoYi-Vue\ruoyi-admin\target\extracted"

os.makedirs(extract_dir, exist_ok=True)

with zipfile.ZipFile(jar_path, 'r') as z:
    framework_jar_name = "BOOT-INF/lib/ruoyi-framework-3.9.2.jar"
    z.extract(framework_jar_name, extract_dir)

framework_path = os.path.join(extract_dir, framework_jar_name)
with zipfile.ZipFile(framework_path, 'r') as fz:
    class_data = fz.read("com/ruoyi/framework/security/filter/JwtAuthenticationTokenFilter.class")

print(f"Class file size: {len(class_data)} bytes")
print(f"Contains 'ANONYMOUS_PATHS': {b'ANONYMOUS_PATHS' in class_data}")
print(f"Contains 'flowable': {b'flowable' in class_data}")
print(f"Contains 'ROLE_ADMIN': {b'ROLE_ADMIN' in class_data}")

if b'ROLE_ADMIN' in class_data:
    print("\n=== 最新版本(UsernamePasswordAuthenticationToken) ===")
elif b'AnonymousAuthenticationToken' in class_data:
    print("\n=== 中间版本(AnonymousAuthenticationToken) ===")
else:
    print("\n=== 原始版本(无修改) ===")
