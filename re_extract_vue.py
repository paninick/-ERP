import zipfile
import os

zip_path = r"C:\Users\91306\Downloads\genCode (5).zip"
dest_base = r"D:\erp\RuoYi-Vue\ruoyi-ui\src"

print(f"=== Re-extracting all vue and api files with correct path ===\n")

z = zipfile.ZipFile(zip_path, 'r')

extracted = 0
errors = 0

for f in z.namelist():
    if f.startswith('vue/'):
        # vue/views/erp/xxx/index.vue -> dest_base/views/erp/xxx/index.vue
        rel_path = f[len('vue/'):]
        dest_path = os.path.join(dest_base, rel_path)
        dest_dir = os.path.dirname(dest_path)
        
        if not os.path.exists(dest_dir):
            os.makedirs(dest_dir)
        
        try:
            with z.open(f) as src:
                content = src.read()
                # Remove zero-width space characters
                content = content.replace(b'\u200b', b'')
                content = content.replace(b'\ufeff', b'')
                with open(dest_path, 'wb') as dst:
                    dst.write(content)
            extracted += 1
            print(f"  ✅ {extracted}: {rel_path}")
        except Exception as e:
            print(f"  ❌ {rel_path}: {e}")
            errors += 1

print(f"\n=== Done ===")
print(f"  Extracted: {extracted} files")
print(f"  Errors: {errors}")

z.close()
print("\nDONE!")
