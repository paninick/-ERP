#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
ERP系统SQL注入检查脚本
检查Mapper XML文件中的SQL注入风险
"""

import os
import re
import sys
from pathlib import Path


def check_mapper_sql_injection(mapper_path):
    """检查Mapper XML文件中的SQL注入风险"""

    issues = []

    # 查找所有Mapper XML文件
    for root, dirs, files in os.walk(mapper_path):
        for file in files:
            if file.endswith('.xml'):
                filepath = os.path.join(root, file)
                with open(filepath, 'r', encoding='utf-8') as f:
                    content = f.read()

                # 检查是否包含${}参数绑定（高风险）
                dollar_bindings = re.findall(r'\$\{[^}]+\}', content)
                if dollar_bindings:
                    for binding in dollar_bindings:
                        issues.append(f"{filepath}: 发现高风险参数绑定 {binding}")

                # 检查动态SQL中的字符串拼接
                # 查找<if test>中的字符串拼接
                if_pattern = r'<if test="[^"]*">([^<]*)</if>'
                if_matches = re.finditer(if_pattern, content)
                for match in if_matches:
                    if_content = match.group(1)
                    # 检查是否有字符串拼接
                    if '+' in if_content and "'" in if_content:
                        issues.append(f"{filepath}: 动态SQL中可能包含字符串拼接: {if_content[:50]}...")

    return issues


def main():
    """主函数"""
    # Mapper XML路径
    mapper_paths = [
        'ruoyi-admin/src/main/resources/mapper/erp',
        'ruoyi-demo/src/main/resources/mapper/demo'
    ]

    print("开始检查ERP系统SQL注入风险...")

    all_issues = []

    for mapper_path in mapper_paths:
        if os.path.exists(mapper_path):
            print(f"检查路径: {mapper_path}")
            issues = check_mapper_sql_injection(mapper_path)
            all_issues.extend(issues)
        else:
            print(f"路径不存在: {mapper_path}")

    # 输出结果
    if all_issues:
        print(f"\n发现 {len(all_issues)} 个SQL注入风险:")
        for issue in all_issues:
            print(f"  - {issue}")
        print("\n建议:")
        print("  1. 将 ${} 参数绑定改为 #{}")
        print("  2. 避免在动态SQL中进行字符串拼接")
        print("  3. 使用MyBatis的参数绑定功能")
        return 1
    else:
        print("\n✓ 未发现明显的SQL注入风险")
        return 0


if __name__ == '__main__':
    sys.exit(main())
