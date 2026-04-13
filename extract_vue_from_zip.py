import zipfile
import os

zip_path = r"C:\Users\91306\Downloads\genCode (5).zip"
dest_base = r"D:\erp\RuoYi-Vue\ruoyi-ui\src\views"

print(f"=== Extracting vue files from ZIP to {dest_base} ===\n")

z = zipfile.ZipFile(zip_path, 'r')

extracted = 0
errors = 0

for f in z.namelist():
    if f.startswith('vue/views/') and f.endswith('.vue'):
        # vue/views/erp/xxx/index.vue -> dest/erp/xxx/index.vue
        rel_path = f[len('vue/'):]
        dest_path = os.path.join(dest_base, rel_path)
        dest_dir = os.path.dirname(dest_path)
        
        if not os.path.exists(dest_dir):
            os.makedirs(dest_dir)
        
        try:
            with z.open(f) as src:
                content = src.read()
                with open(dest_path, 'wb') as dst:
                    dst.write(content)
            extracted += 1
            print(f"  ✅ {extracted}: {rel_path}")
        except Exception as e:
            print(f"  ❌ {rel_path}: {e}")
            errors += 1

print(f"\n=== Done ===")
print(f"  Extracted: {extracted} vue files")
print(f"  Errors: {errors}")

# Also check api files
print(f"\n=== Extracting api/js files ===")
extracted_api = 0
for f in z.namelist():
    if f.startswith('vue/api/') and f.endswith('.js'):
        rel_path = f[len('vue/'):]
        dest_path = os.path.join(dest_base, '..', 'api', rel_path)
        dest_dir = os.path.dirname(dest_path)
        
        if not os.path.exists(dest_dir):
            os.makedirs(dest_dir)
        
        try:
            with z.open(f) as src:
                content = src.read()
                with open(dest_path, 'wb') as dst:
                    dst.write(content)
            extracted_api += 1
            print(f"  ✅ {extracted_api}: {rel_path}")
        except Exception as e:
            print(f"  ❌ {rel_path}: {e}")
            errors += 1

print(f"\n=== Final Summary ===")
print(f"  Total extracted: {extracted + extracted_api} files")
print(f"  Views: {extracted}, API: {extracted_api}")
print(f"  Errors: {errors}")

z.close()
print("\nDONE!")
