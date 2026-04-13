import os
import re

base = r"d:\erp\RuoYi-Vue"

def fix_java_files():
    fixed_servlet = 0
    fixed_lombok = 0

    erp_dir = os.path.join(base, "ruoyi-system", "src", "main", "java", "com", "ruoyi")

    for root, dirs, files in os.walk(erp_dir):
        for f in files:
            if not f.endswith(".java"):
                continue
            fpath = os.path.join(root, f)
            try:
                with open(fpath, 'r', encoding='utf-8') as fh:
                    content = fh.read()
            except:
                continue

            original = content

            # Fix 1: javax.servlet -> jakarta.servlet
            content = content.replace(
                "import javax.servlet.http.HttpServletResponse;",
                "import jakarta.servlet.http.HttpServletResponse;"
            )
            if content != original:
                fixed_servlet += 1

            # Fix 2: Remove @RequiredArgsConstructor + import, replace with @Autowired constructor pattern
            if "@RequiredArgsConstructor" in content or "lombok.RequiredArgsConstructor" in content:
                # Remove lombok import
                content = re.sub(r'\nimport lombok\.RequiredArgsConstructor;\n', '\n', content)
                # Remove @RequiredArgsConstructor annotation
                content = content.replace("\n@RequiredArgsConstructor\n", "\n")
                # Replace private final XxxMapper xxxMapper; with @Autowired private XxxMapper xxxMapper;
                content = re.sub(
                    r'\n    private final (\w+Mapper) (\w+);\n',
                    r'\n    @Autowired\n    private \1 \2;\n',
                    content
                )
                if content != original or "@RequiredArgsConstructor" in original:
                    fixed_lombok += 1

            if content != original:
                with open(fpath, 'w', encoding='utf-8') as fh:
                    fh.write(content)

    print(f"Fixed servlet imports: {fixed_servlet} files")
    print(f"Fixed lombok annotations: {fixed_lombok} files")
    print("Done!")

if __name__ == "__main__":
    fix_java_files()
