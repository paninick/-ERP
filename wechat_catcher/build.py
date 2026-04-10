# -*- coding: utf-8 -*-
import PyInstaller.__main__
import os
import shutil

print('=' * 50)
print('正在打包微信群消息抓取工具...')
print('=' * 50)

# 确保output目录存在
os.makedirs('output', exist_ok=True)

# PyInstaller打包参数
PyInstaller.__main__.run([
    'main.py',
    '--name=微信群消息抓取',
    '--onefile',              # 打包成单个exe
    '--windowed',             # 无控制台窗口
    '--icon=icon.ico',        # 如有图标可取消注释
    '--add-data=output;output',  # 包含输出目录
    '--clean',
    '--noconfirm'
])

print('=' * 50)
print('打包完成！')
print('请在 dist 目录下查找 微信群消息抓取.exe')
print('=' * 50)

input('\n按回车键退出...')