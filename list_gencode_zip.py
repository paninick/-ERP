import zipfile
import io

zip_path = r"C:\Users\91306\Downloads\genCode (5).zip"
z = zipfile.ZipFile(zip_path, 'r')

print("=== All files in ZIP ===")
for f in z.namelist():
    info = z.getinfo(f)
    print(f"  {info.file_size:8d}  {f}")

print("\n=== Checking what's in each SQL file ===")
for f in z.namelist():
    if f.endswith('.sql'):
        with z.open(f) as fh:
            first_line = fh.readline().decode('utf-8')
            print(f"  {f:<30} -> {first_line[:80]}")

z.close()
print("\nDONE!")
