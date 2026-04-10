# -*- coding: utf-8 -*-
import os
import time
import re
from datetime import datetime
from PIL import Image, ImageGrab
import pygetwindow as gw
import pytesseract
import pandas as pd
from openpyxl import Workbook, load_workbook
from openpyxl.styles import Font, Alignment, PatternFill, Border, Side
import numpy as np

class WeChatCatcher:
    def __init__(self):
        self.wechat_window = None
        self.output_dir = os.path.join(os.path.dirname(__file__), 'output')
        os.makedirs(self.output_dir, exist_ok=True)

    def find_wechat_window(self):
        windows = gw.getWindowsWithTitle('微信')
        for win in windows:
            if '微信' in win.title and '企业微信' not in win.title:
                self.wechat_window = win
                return True
        return False

    def capture_chat_area(self, x=None, y=None, width=None, height=None):
        if self.wechat_window:
            if x is None:
                x = self.wechat_window.left + 300
            if y is None:
                y = self.wechat_window.top + 100
            if width is None:
                width = 600
            if height is None:
                height = 500

        screenshot = ImageGrab.grab(bbox=(x, y, x + width, height + height))
        timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
        screenshot_path = os.path.join(self.output_dir, f'wechat_capture_{timestamp}.png')
        screenshot.save(screenshot_path)
        return screenshot, screenshot_path

    def ocr_recognize(self, image_path=None, image=None):
        if image is None and image_path:
            image = Image.open(image_path)

        custom_config = r'--oem 3 --psm 6'
        text = pytesseract.image_to_string(image, lang='chi_sim', config=custom_config)
        return text

    def parse_message(self, text):
        records = []
        lines = text.split('\n')
        current_record = {}

        for line in lines:
            line = line.strip()
            if not line:
                continue

            time_pattern = r'(\d{1,2}:\d{2})'
            time_match = re.search(time_pattern, line)

            if time_match:
                if current_record and 'sender' in current_record:
                    records.append(current_record)

                current_record = {
                    'time': time_match.group(1),
                    'sender': '',
                    'destination': '',
                    'item': '',
                    'raw_message': ''
                }

            keywords_destination = ['到', '发往', '去', '目的地', '送', '前往']
            keywords_item = ['货', '物品', '产品', '货品', '件', '箱', '条', '个']

            if any(kw in line for kw in keywords_destination):
                parts = re.split(r'[到发往去送前往]', line)
                if len(parts) > 1:
                    current_record['destination'] = parts[-1].strip()

            if any(kw in line for kw in keywords_item):
                if not current_record.get('item'):
                    current_record['item'] = line

            if current_record.get('time') and not current_record.get('sender'):
                sender_match = re.match(r'^[^:]+', line)
                if sender_match:
                    sender = sender_match.group(0).strip()
                    if sender and sender != current_record.get('time', ''):
                        current_record['sender'] = sender.replace(current_record.get('time', ''), '').strip()

            current_record['raw_message'] += line + '\n'

        if current_record and 'sender' in current_record and current_record.get('sender'):
            records.append(current_record)

        return records

    def export_to_excel(self, records, filename=None):
        if not filename:
            filename = f'微信群消息记录_{datetime.now().strftime("%Y%m%d")}.xlsx'

        filepath = os.path.join(self.output_dir, filename)

        data = []
        for record in records:
            data.append({
                '时间': record.get('time', ''),
                '发送人': record.get('sender', ''),
                '目的地': record.get('destination', ''),
                '物品': record.get('item', ''),
                '原始消息': record.get('raw_message', '').strip()
            })

        df = pd.DataFrame(data)

        if os.path.exists(filepath):
            existing_df = pd.read_excel(filepath)
            df = pd.concat([existing_df, df], ignore_index=True)

        df.to_excel(filepath, index=False, engine='openpyxl')

        return filepath

    def run_capture(self, x=None, y=None, width=None, height=None):
        print('[1] 查找微信窗口...')
        if not self.find_wechat_window():
            print('[错误] 未找到微信窗口，请确保微信已打开')
            return None

        print(f'[2] 已找到微信窗口: {self.wechat_window.title}')
        print('[3] 捕获聊天区域...')
        image, path = self.capture_chat_area(x, y, width, height)
        print(f'[4] 截图已保存: {path}')

        print('[5] OCR识别中...')
        text = self.ocr_recognize(image=image)

        print('[6] 解析消息...')
        records = self.parse_message(text)

        print(f'[7] 解析到 {len(records)} 条记录')

        if records:
            print('[8] 导出到Excel...')
            excel_path = self.export_to_excel(records)
            print(f'[成功] Excel已导出: {excel_path}')
            return excel_path

        return None


if __name__ == '__main__':
    catcher = WeChatCatcher()
    result = catcher.run_capture()

    if result:
        print(f'\n请查看输出文件: {result}')
    else:
        print('\n未获取到有效消息，请检查微信窗口是否正确显示群聊内容')