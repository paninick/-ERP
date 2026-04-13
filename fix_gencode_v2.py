import os
import re

base = r"d:\erp\RuoYi-Vue"

def fix_all():
    fixed_userid = 0
    fixed_params = 0

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

            # Fix 1: SecurityUtils.getUserIdStr() -> String.valueOf(SecurityUtils.getUserId())
            if "SecurityUtils.getUserIdStr()" in content:
                content = content.replace(
                    "SecurityUtils.getUserIdStr()",
                    "String.valueOf(SecurityUtils.getUserId())"
                )
                fixed_userid += 1

            # Fix 2: TemplateMessage params field rename to avoid conflict with BaseEntity.getParams()
            if f == "TemplateMessage.java":
                # Rename field, setter, getter
                content = content.replace("private String params;", "private String templateParams;")
                content = content.replace("this.params = params;", "this.templateParams = templateParams;")
                content = content.replace("public void setParams(String params)", "public void setTemplateParams(String templateParams)")
                content = content.replace("public String getParams()", "public String getTemplateParams()")
                content = content.replace("return params;", "return templateParams;")
                fixed_params += 1

            if content != original:
                with open(fpath, 'w', encoding='utf-8') as fh:
                    fh.write(content)

    print(f"Fixed getUserIdStr: {fixed_userid} files")
    print(f"Fixed TemplateMessage params: {fixed_params} files")
    print("Done!")

if __name__ == "__main__":
    fix_all()
