# -*- coding: utf-8 -*-
import os
import sys
import time
import re
import threading
from datetime import datetime
from tkinter import *
from tkinter import ttk, filedialog, messagebox
from PIL import Image, ImageGrab
import pygetwindow as gw
import pytesseract
import pandas as pd
from openpyxl import Workbook, load_workbook
from openpyxl.styles import Font, Alignment, PatternFill, Border, Side
import numpy as np

class WeChatCatcherGUI:
    def __init__(self, root):
        self.root = root
        self.root.title('微信群聊消息抓取工具')
        self.root.geometry('600x500')
        self.root.resizable(False, False)

        self.output_dir = os.path.join(os.path.dirname(__file__), 'output')
        os.makedirs(self.output_dir, exist_ok=True)

        self.wechat_window = None
        self.is_capturing = False

        self.setup_ui()

    def setup_ui(self):
        main_frame = ttk.Frame(self.root, padding='10')
        main_frame.grid(row=0, column=0, sticky=(W, E, N, S))

        title_label = ttk.Label(main_frame, text='微信群聊消息抓取工具', font=('微软雅黑', 16, 'bold'))
        title_label.grid(row=0, column=0, columnspan=3, pady=10)

        ttk.Separator(main_frame, orient='horizontal').grid(row=1, column=0, columnspan=3, sticky='ew', pady=5)

        config_frame = ttk.LabelFrame(main_frame, text='抓取配置', padding='10')
        config_frame.grid(row=2, column=0, columnspan=3, sticky='ew', pady=5)

        ttk.Label(config_frame, text='X坐标:').grid(row=0, column=0, sticky=W, padx=5)
        self.x_var = IntVar(value=300)
        ttk.Entry(config_frame, textvariable=self.x_var, width=10).grid(row=0, column=1, padx=5)

        ttk.Label(config_frame, text='Y坐标:').grid(row=0, column=2, sticky=W, padx=5)
        self.y_var = IntVar(value=100)
        ttk.Entry(config_frame, textvariable=self.y_var, width=10).grid(row=0, column=3, padx=5)

        ttk.Label(config_frame, text='宽度:').grid(row=0, column=4, sticky=W, padx=5)
        self.width_var = IntVar(value=600)
        ttk.Entry(config_frame, textvariable=self.width_var, width=10).grid(row=0, column=5, padx=5)

        ttk.Label(config_frame, text='高度:').grid(row=1, column=0, sticky=W, padx=5)
        self.height_var = IntVar(value=500)
        ttk.Entry(config_frame, textvariable=self.height_var, width=10).grid(row=1, column=1, padx=5)

        ttk.Label(config_frame, text='(可在设置中调整抓取区域)', font=('', 8)).grid(row=1, column=2, columnspan=4, sticky=W)

        btn_frame = ttk.Frame(main_frame)
        btn_frame.grid(row=3, column=0, columnspan=3, pady=10)

        self.capture_btn = ttk.Button(btn_frame, text='开始抓取', command=self.start_capture)
        self.capture_btn.grid(row=0, column=0, padx=5)

        self.auto_btn = ttk.Button(btn_frame, text='自动监控', command=self.start_auto_capture)
        self.auto_btn.grid(row=0, column=1, padx=5)

        self.stop_btn = ttk.Button(btn_frame, text='停止', command=self.stop_capture, state=DISABLED)
        self.stop_btn.grid(row=0, column=2, padx=5)

        ttk.Button(btn_frame, text='查看输出目录', command=self.open_output_dir).grid(row=0, column=3, padx=5)

        log_frame = ttk.LabelFrame(main_frame, text='日志输出', padding='10')
        log_frame.grid(row=4, column=0, columnspan=3, sticky='nsew', pady=5)

        self.log_text = Text(log_frame, height=12, width=65, state=DISABLED)
        self.log_text.grid(row=0, column=0, sticky='nsew')

        scrollbar = ttk.Scrollbar(log_frame, orient='vertical', command=self.log_text.yview)
        scrollbar.grid(row=0, column=1, sticky='ns')
        self.log_text['yscrollcommand'] = scrollbar.set

        main_frame.columnconfigure(3, weight=1)
        main_frame.rowconfigure(4, weight=1)

    def log(self, message):
        self.log_text.config(state='normal')
        timestamp = datetime.now().strftime('%H:%M:%S')
        self.log_text.insert(END, f'[{timestamp}] {message}\n')
        self.log_text.see(END)
        self.log_text.config(state='DISABLED')
        self.root.update()

    def find_wechat_window(self):
        windows = gw.getWindowsWithTitle('微信')
        for win in windows:
            if '微信' in win.title and '企业微信' not in win.title:
                self.wechat_window = win
                return True
        return False

    def capture_chat_area(self, x=None, y=None, width=None, height=None):
        if x is None:
            x = self.x_var.get()
        if y is None:
            y = self.y_var.get()
        if width is None:
            width = self.width_var.get()
        if height is None:
            height = self.height_var.get()

        screenshot = ImageGrab.grab(bbox=(x, y, x + width, y + height))
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

        keywords_destination = ['到', '发往', '去', '送', '前往', '至', '去往', '送往', '发到']
        keywords_item = ['货', '物品', '产品', '货品', '件', '箱', '条', '个', '台', '套', '批', '吨']
        keywords_logistics = ['车牌', '司机', '运费', '吨位', '重量', '车型', '装货', '卸货', '地址', '目的地']

        for line in lines:
            line = line.strip()
            if not line:
                continue

            time_pattern = r'(\d{1,2}:\d{2})'
            time_match = re.search(time_pattern, line)

            if time_match:
                if current_record and 'sender' in current_record and current_record.get('sender'):
                    records.append(current_record)

                current_record = {
                    'time': time_match.group(1),
                    'sender': '',
                    'destination': '',
                    'item': '',
                    'raw_message': ''
                }

            has_logistics_keyword = False
            for kw in keywords_logistics:
                if kw in line:
                    has_logistics_keyword = True
                    break

            if not has_logistics_keyword:
                for kw in keywords_destination:
                    if kw in line:
                        parts = re.split(rf'[{kw}]', line)
                        if len(parts) > 1:
                            dest = parts[-1].strip()
                            dest = re.sub(r'^[\s:：,，]+', '', dest)
                            dest = re.sub(r'[，。,\.]+$', '', dest)
                            if dest and len(dest) < 50:
                                current_record['destination'] = dest
                        break

            for kw in keywords_item:
                if kw in line:
                    if not current_record.get('item'):
                        item_text = line
                        for kw_dest in keywords_destination:
                            if kw_dest in item_text:
                                parts = item_text.split(kw_dest)
                                if len(parts) > 1:
                                    item_text = parts[0]
                        item_text = re.sub(r'^[\s\d:：]+', '', item_text)
                        if item_text:
                            current_record['item'] = item_text
                    break

            if current_record.get('time') and not current_record.get('sender'):
                sender_match = re.match(r'^[^:：]+', line)
                if sender_match:
                    sender = sender_match.group(0).strip()
                    sender = re.sub(r'^[\s\d:：]+', '', sender)
                    if sender and len(sender) > 0 and sender != current_record.get('time', ''):
                        current_record['sender'] = sender

            if current_record:
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
            try:
                existing_df = pd.read_excel(filepath)
                df = pd.concat([existing_df, df], ignore_index=True)
            except:
                pass

        df.to_excel(filepath, index=False, engine='openpyxl')
        return filepath

    def do_capture(self):
        self.log('[1] 查找微信窗口...')
        if not self.find_wechat_window():
            self.log('[错误] 未找到微信窗口，请确保微信已打开')
            return

        self.log(f'[2] 已找到微信窗口')
        self.log('[3] 捕获聊天区域...')

        try:
            image, path = self.capture_chat_area()
            self.log(f'[4] 截图已保存')

            self.log('[5] OCR识别中...')
            text = self.ocr_recognize(image=image)

            self.log('[6] 解析消息...')
            records = self.parse_message(text)

            self.log(f'[7] 解析到 {len(records)} 条记录')

            if records:
                excel_path = self.export_to_excel(records)
                self.log(f'[成功] Excel已导出: {excel_path}')
            else:
                self.log('[提示] 未解析到有效消息，请检查抓取区域是否正确')

        except Exception as e:
            self.log(f'[错误] {str(e)}')

    def start_capture(self):
        self.log('=' * 30)
        threading.Thread(target=self.do_capture, daemon=True).start()

    def start_auto_capture(self):
        self.is_capturing = True
        self.capture_btn.config(state=DISABLED)
        self.auto_btn.config(state=DISABLED)
        self.stop_btn.config(state=NORMAL)

        self.log('[自动监控模式] 开始自动抓取...')

        def auto_loop():
            interval = 5
            count = 0
            while self.is_capturing:
                count += 1
                self.log(f'[自动] 第{count}次抓取...')
                self.do_capture()
                time.sleep(interval)

        threading.Thread(target=auto_loop, daemon=True).start()

    def stop_capture(self):
        self.is_capturing = False
        self.capture_btn.config(state=NORMAL)
        self.auto_btn.config(state=NORMAL)
        self.stop_btn.config(state=DISABLED)
        self.log('[提示] 已停止自动监控')

    def open_output_dir(self):
        os.startfile(self.output_dir)


def main():
    root = Tk()
    app = WeChatCatcherGUI(root)
    root.mainloop()


if __name__ == '__main__':
    main()