#!/usr/bin/env python3
# -*- coding: utf-8 -*-

"""
ERP系统权限注解检查脚本
检查所有Controller中的@PreAuthorize注解

用法:
    python scripts/check-permissions.py

从项目根目录运行，会检查:
    - ruoyi-admin/src/main/java/com/ruoyi/erp/controller/ (ERP Controller)
    - ruoyi-demo/src/main/java/com/ruoyi/demo/controller/ (Demo模块中生产路径 /erp/)
"""

import os
import re
import sys


def is_comment_or_empty(line):
    """判断是否为注释行或空行"""
    stripped = line.strip()
    if not stripped:
        return True
    if stripped.startswith('//'):
        return True
    if stripped.startswith('/*') or stripped.startswith('*'):
        return True
    return False


def is_annotation_line(line):
    """判断是否为注解行"""
    return line.strip().startswith('@')


def is_mapping_annotation(line):
    """判断是否为方法级别的请求映射注解"""
    return bool(re.search(r'@(?:GetMapping|PostMapping|PutMapping|DeleteMapping|PatchMapping)\b', line))


def check_file(filepath):
    """检查单个Controller文件，返回缺失 @PreAuthorize 的问题列表"""
    issues = []
    relpath = os.path.relpath(filepath, start=os.path.join(os.path.dirname(__file__), '..'))

    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()

    for i, line in enumerate(lines):
        if not is_mapping_annotation(line):
            continue

        # 向上查找 @PreAuthorize，跳过注释、空行、其他注解
        found = False
        j = i - 1
        while j >= 0:
            stripped = lines[j].strip()

            if stripped.startswith('@PreAuthorize'):
                found = True
                break

            # 遇到其他方法映射注解 -> 停止（属于前一个方法）
            if is_mapping_annotation(stripped):
                break

            # 跳过空行、注释、其他注解
            if is_comment_or_empty(lines[j]) or is_annotation_line(lines[j]):
                j -= 1
                continue

            # 遇到代码行（如 }、public class 等）-> 停止
            break

        if not found:
            # 提取方法名
            method_name = 'unknown'
            for k in range(i, min(i + 5, len(lines))):
                m = re.search(r'(?:public|private|protected)\s+(\S+)\s+(\w+)\s*\(', lines[k])
                if m:
                    method_name = m.group(2)
                    break

            issues.append(f"  - {relpath}:{i + 1}  方法 [{method_name}] 缺少 @PreAuthorize 注解")

    return issues


def scan_directory(controller_path, is_production_path=False):
    """扫描目录下的所有Controller文件"""
    all_issues = []
    total_files = 0

    if not os.path.isdir(controller_path):
        return all_issues, 0

    for root, _dirs, files in os.walk(controller_path):
        for file in sorted(files):
            if not file.endswith('Controller.java'):
                continue
            filepath = os.path.join(root, file)

            with open(filepath, 'r', encoding='utf-8') as f:
                content = f.read()

            if '@RestController' not in content:
                continue

            # Demo模块只检查包含 /erp/ 路径的生产 Controller
            if not is_production_path and '/erp/' not in content:
                continue

            total_files += 1
            file_issues = check_file(filepath)
            all_issues.extend(file_issues)

    return all_issues, total_files


def main():
    """主函数"""
    # 获取项目根目录（脚本在 scripts/ 下）
    project_root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

    # Controller 扫描路径
    scan_paths = [
        {
            'path': 'ruoyi-admin/src/main/java/com/ruoyi/erp/controller',
            'label': 'ERP Controller',
            'production': True,
        },
        {
            'path': 'ruoyi-demo/src/main/java/com/ruoyi/demo/controller',
            'label': 'Demo Controller (production paths)',
            'production': False,
        },
    ]

    header = '=' * 60
    print(header)
    print('  ERP 系统权限注解检查')
    print(header)
    print()

    all_issues = []
    total_files = 0

    for entry in scan_paths:
        controller_path = os.path.join(project_root, entry['path'])
        label = entry['label']
        print(f'[{label}]')
        print(f'  扫描路径: {controller_path}')

        if not os.path.exists(controller_path):
            print('  [跳过] 路径不存在')
            print()
            continue

        issues, nfiles = scan_directory(controller_path, entry['production'])
        all_issues.extend(issues)
        total_files += nfiles

        if issues:
            print(f'  发现 {len(issues)} 个问题:')
            for issue in issues:
                print(issue)
        else:
            print(f'  [OK] 全部通过 ({nfiles} 个文件)')
        print()

    # 汇总
    print('-' * 60)
    print(f'  扫描文件数: {total_files}')
    print(f'  发现问题数: {len(all_issues)}')
    print()

    if all_issues:
        print('  [FAIL] 检查未通过，请为以上方法添加 @PreAuthorize 注解')
        return 1
    else:
        print('  [OK] 所有方法均有 @PreAuthorize 注解')
        return 0


if __name__ == '__main__':
    sys.exit(main())
