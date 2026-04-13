
import shutil

pom_path = r"d:\erp\RuoYi-Vue\ruoyi-admin\pom.xml"
bak_path = r"d:\erp\RuoYi-Vue\ruoyi-admin\pom.xml.bak"

# 备份
shutil.copy2(pom_path, bak_path)

with open(pom_path, 'r', encoding='utf-8') as f:
    content = f.read()

old_block = '''        &lt;!-- spring-boot-devtools --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
            &lt;artifactId&gt;spring-boot-devtools&lt;/artifactId&gt;
            &lt;optional&gt;true&lt;/optional&gt; &lt;!-- 表示依赖不会传递 --&gt;
        &lt;/dependency&gt;'''

new_block = '''        &lt;!-- spring-boot-devtools --&gt;
        &lt;!-- &lt;dependency&gt;
            &lt;groupId&gt;org.springframework.boot&lt;/groupId&gt;
            &lt;artifactId&gt;spring-boot-devtools&lt;/artifactId&gt;
            &lt;optional&gt;true&lt;/optional&gt;
        &lt;/dependency&gt; --&gt;'''

content = content.replace(old_block, new_block)

with open(pom_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("=" * 80)
print("spring-boot-devtools 已注释掉!")
print("=" * 80)
