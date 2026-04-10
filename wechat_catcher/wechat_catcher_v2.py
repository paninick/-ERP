# -*- coding: utf-8 -*-
import os
import sys
import time
import re
import json
import threading
import hashlib
from datetime import datetime
from tkinter import *
from tkinter import ttk, filedialog, messagebox
from PIL import Image, ImageDraw, ImageGrab
import pygetwindow as gw
import pytesseract
import pandas as pd
from openpyxl import Workbook, load_workbook
import numpy as np

TESSERACT_PATHS = [
    r'C:\Program Files\Tesseract-OCR\tesseract.exe',
    r'C:\Program Files (x86)\Tesseract-OCR\tesseract.exe',
    r'D:\Program Files\Tesseract-OCR\tesseract.exe',
    r'C:\Tesseract-OCR\tesseract.exe',
]

def find_tesseract():
    for path in TESSERACT_PATHS:
        if os.path.exists(path):
            return path
    return None

def get_tesseract_path():
    tesseract_cmd = find_tesseract()
    if tesseract_cmd:
        pytesseract.pytesseract.tesseract_cmd = tesseract_cmd
        return tesseract_cmd
    return None

def check_tesseract():
    path = get_tesseract_path()
    if path:
        try:
            version = pytesseract.get_tesseract_version()
            return True, path, str(version)
        except:
            pass
    return False, None, None


class ScreenSelector:
    def __init__(self):
        self.start_x = 0
        self.start_y = 0
        self.end_x = 0
        self.end_y = 0
        self.selecting = False
        self.root = None
        self.canvas = None
        self.selection_rect = None

    def select_area(self):
        self.root = Tk()
        self.root.attributes('-fullscreen', True)
        self.root.attributes('-alpha', 0.3)
        self.root.configure(bg='black')
        self.root.bind('<Button-1>', self.on_mouse_down)
        self.root.bind('<B1-Motion>', self.on_mouse_drag)
        self.root.bind('<ButtonRelease-1>', self.on_mouse_up)
        self.root.bind('<Escape>', lambda e: self.root.destroy())

        self.canvas = Canvas(self.root, cursor='cross', bg='black')
        self.canvas.pack(fill=BOTH, expand=True)

        label = Label(self.root, text='拖拽鼠标框选微信聊天区域，按Esc取消', bg='black', fg='white', font=('微软雅黑', 20))
        label.place(relx=0.5, rely=0.1, anchor=CENTER)

        self.root.mainloop()

        if self.end_x > self.start_x and self.end_y > self.start_y:
            return (self.start_x, self.start_y, self.end_x, self.end_y)
        return None

    def on_mouse_down(self, event):
        self.start_x = event.x
        self.start_y = event.y
        self.selecting = True

    def on_mouse_drag(self, event):
        if self.selecting:
            self.canvas.delete('rect')
            self.canvas.create_rectangle(
                self.start_x, self.start_y,
                event.x, event.y,
                outline='red', width=3, tags='rect'
            )

    def on_mouse_up(self, event):
        self.selecting = False
        self.end_x = event.x
        self.end_y = event.y
        self.root.destroy()


class WeChatCatcherPro:
    def __init__(self):
        self.config_file = os.path.join(os.path.dirname(__file__), 'capture_config.json')
        self.output_dir = os.path.join(os.path.dirname(__file__), 'output')
        self.record_file = os.path.join(self.output_dir, 'records_cache.json')
        os.makedirs(self.output_dir, exist_ok=True)
        self.load_config()
        self.load_record_cache()

    def load_config(self):
        if os.path.exists(self.config_file):
            with open(self.config_file, 'r', encoding='utf-8') as f:
                self.config = json.load(f)
        else:
            self.config = {
                'x': 300, 'y': 100, 'width': 800, 'height': 600,
                'window_title': '微信',
                'auto_interval': 5
            }
        self.x = self.config.get('x', 300)
        self.y = self.config.get('y', 100)
        self.width = self.config.get('width', 800)
        self.height = self.config.get('height', 600)
        self.window_title = self.config.get('window_title', '微信')
        self.auto_interval = self.config.get('auto_interval', 5)

    def save_config(self):
        self.config = {
            'x': self.x, 'y': self.y,
            'width': self.width, 'height': self.height,
            'window_title': self.window_title,
            'auto_interval': self.auto_interval
        }
        with open(self.config_file, 'w', encoding='utf-8') as f:
            json.dump(self.config, f, ensure_ascii=False, indent=2)

    def load_record_cache(self):
        if os.path.exists(self.record_file):
            try:
                with open(self.record_file, 'r', encoding='utf-8') as f:
                    self.record_cache = json.load(f)
            except:
                self.record_cache = {}
        else:
            self.record_cache = {}

    def save_record_cache(self):
        try:
            with open(self.record_file, 'w', encoding='utf-8') as f:
                json.dump(self.record_cache, f, ensure_ascii=False)
        except:
            pass

    def get_message_hash(self, message):
        content = f"{message.get('time', '')}_{message.get('sender', '')}_{message.get('raw_message', '')}"
        return hashlib.md5(content.encode('utf-8')).hexdigest()

    def is_duplicate(self, message):
        msg_hash = self.get_message_hash(message)
        return msg_hash in self.record_cache

    def add_to_cache(self, message):
        msg_hash = self.get_message_hash(message)
        self.record_cache[msg_hash] = datetime.now().isoformat()
        self.save_record_cache()

    def clean_old_cache(self, days=7):
        cutoff = datetime.now().timestamp() - (days * 24 * 3600)
        to_remove = []
        for key, value in self.record_cache.items():
            try:
                timestamp = datetime.fromisoformat(value).timestamp()
                if timestamp < cutoff:
                    to_remove.append(key)
            except:
                to_remove.append(key)
        for key in to_remove:
            del self.record_cache[key]
        if to_remove:
            self.save_record_cache()

    def find_wechat_window(self):
        windows = gw.getWindowsWithTitle(self.window_title)
        for win in windows:
            if self.window_title in win.title:
                if '企业微信' not in win.title:
                    return win
        return None

    def capture_and_ocr(self):
        win = self.find_wechat_window()
        if not win:
            return None, '未找到微信窗口'

        self.x = win.left + self.x
        self.y = win.top + self.y

        screenshot = ImageGrab.grab(bbox=(self.x, self.y, self.x + self.width, self.y + self.height))
        timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
        capture_path = os.path.join(self.output_dir, f'wechat_capture_{timestamp}.png')
        screenshot.save(capture_path)

        try:
            custom_config = r'--oem 3 --psm 6'
            text = pytesseract.image_to_string(screenshot, lang='chi_sim', config=custom_config)
            return text, capture_path
        except Exception as e:
            return None, str(e)

    def parse_message(self, text):
        records = []
        lines = text.split('\n')
        current_record = {}

        keywords_destination = ['到', '发往', '去', '送', '前往', '至', '去往', '送往', '发到', '送往', '目的']
        keywords_item = ['货', '物品', '产品', '货品', '件', '箱', '条', '个', '台', '套', '批', '吨', '袋', '包']
        keywords_logistics = ['车牌', '司机', '运费', '吨位', '重量', '车型', '装货', '卸货', '地址', '目的地', '物流']

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
                            dest = re.sub(r'\d+.*$', '', dest)
                            if dest and 1 < len(dest) < 50:
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
                        item_text = item_text[:100]
                        if item_text:
                            current_record['item'] = item_text
                    break

            if current_record.get('time') and not current_record.get('sender'):
                sender_match = re.match(r'^[^:：]{1,20}', line)
                if sender_match:
                    sender = sender_match.group(0).strip()
                    sender = re.sub(r'^[\s\d:：]+', '', sender)
                    if sender and 1 < len(sender) < 20 and sender != current_record.get('time', ''):
                        current_record['sender'] = sender

            if current_record:
                current_record['raw_message'] += line + '\n'

        if current_record and 'sender' in current_record and current_record.get('sender'):
            records.append(current_record)

        return records

    def export_to_excel(self, records, deduplicate=True):
        today = datetime.now().strftime('%Y%m%d')
        filename = f'微信群消息记录_{today}.xlsx'
        filepath = os.path.join(self.output_dir, filename)

        new_records = []
        for record in records:
            if deduplicate:
                if not self.is_duplicate(record):
                    self.add_to_cache(record)
                    new_records.append(record)
            else:
                new_records.append(record)

        if not new_records:
            return filepath, 0

        data = []
        for record in new_records:
            data.append({
                '时间': record.get('time', ''),
                '发送人': record.get('sender', ''),
                '目的地': record.get('destination', ''),
                '物品': record.get('item', ''),
                '原始消息': record.get('raw_message', '').strip()
            })

        df_new = pd.DataFrame(data)

        try:
            if os.path.exists(filepath):
                try:
                    existing_df = pd.read_excel(filepath)
                    df_final = pd.concat([existing_df, df_new], ignore_index=True)
                except:
                    df_final = df_new
            else:
                df_final = df_new

            df_final.to_excel(filepath, index=False, engine='openpyxl')
        except Exception as e:
            print(f'Excel导出错误: {e}')
            df_new.to_excel(filepath, index=False, engine='openpyxl')

        return filepath, len(new_records)


class WeChatCatcherApp:
    def __init__(self, root):
        self.root = root
        self.root.title('微信群聊消息抓取工具 v2.1')
        self.root.geometry('680x600')
        self.root.resizable(False, False)

        self.catcher = WeChatCatcherPro()
        self.is_capturing = False
        self.selector = None
        self.tesseract_ok = False

        self.check_ocr()
        self.setup_ui()

    def check_ocr(self):
        ok, path, version = check_tesseract()
        self.tesseract_ok = ok
        self.tesseract_path = path or ''
        self.tesseract_version = version or ''

    def setup_ui(self):
        main_frame = ttk.Frame(self.root, padding='10')
        main_frame.grid(row=0, column=0, sticky=(W, E, N, S))

        title_label = ttk.Label(main_frame, text='微信群聊消息抓取工具', font=('微软雅黑', 18, 'bold'))
        title_label.grid(row=0, column=0, columnspan=4, pady=10)

        status_color = 'green' if self.tesseract_ok else 'red'
        status_text = f'OCR: {"已就绪 (" + self.tesseract_version + ")" if self.tesseract_ok else "未安装"}'
        self.status_label = ttk.Label(main_frame, text=status_text, foreground=status_color, font=('', 9))
        self.status_label.grid(row=0, column=3, sticky=E)

        ttk.Separator(main_frame, orient='horizontal').grid(row=1, column=0, columnspan=4, sticky='ew', pady=5)

        config_frame = ttk.LabelFrame(main_frame, text='区域配置', padding='10')
        config_frame.grid(row=2, column=0, columnspan=4, sticky='ew', pady=5)

        if not self.tesseract_ok:
            warn_frame = ttk.Frame(config_frame)
            warn_frame.grid(row=0, column=0, columnspan=4, pady=5)
            ttk.Label(warn_frame, text='⚠️ Tesseract OCR 未安装或路径不正确', foreground='red', font=('', 10, 'bold')).pack()
            ttk.Label(warn_frame, text='请先安装: https://github.com/UB-Mannheim/tesseract/wiki', font=('', 9)).pack()

        self.btn_select = ttk.Button(config_frame, text='📐 框选抓取区域', command=self.select_capture_area, width=18)
        self.btn_select.grid(row=1, column=0, columnspan=2, padx=5, pady=5)

        ttk.Button(config_frame, text='🔄 刷新区域', command=self.refresh_area, width=12).grid(row=1, column=2, padx=5)

        ttk.Label(config_frame, text='X:').grid(row=2, column=0, sticky=E, padx=3)
        self.x_var = IntVar(value=self.catcher.x)
        ttk.Entry(config_frame, textvariable=self.x_var, width=10).grid(row=2, column=1, padx=3, sticky=W)

        ttk.Label(config_frame, text='Y:').grid(row=2, column=2, sticky=E, padx=3)
        self.y_var = IntVar(value=self.catcher.y)
        ttk.Entry(config_frame, textvariable=self.y_var, width=10).grid(row=2, column=3, padx=3, sticky=W)

        ttk.Label(config_frame, text='宽:').grid(row=3, column=0, sticky=E, padx=3)
        self.w_var = IntVar(value=self.catcher.width)
        ttk.Entry(config_frame, textvariable=self.w_var, width=10).grid(row=3, column=1, padx=3, sticky=W)

        ttk.Label(config_frame, text='高:').grid(row=3, column=2, sticky=E, padx=3)
        self.h_var = IntVar(value=self.catcher.height)
        ttk.Entry(config_frame, textvariable=self.h_var, width=10).grid(row=3, column=3, padx=3, sticky=W)

        ttk.Label(config_frame, text='窗口:').grid(row=4, column=0, sticky=E, padx=3)
        self.win_var = StringVar(value=self.catcher.window_title)
        ttk.Entry(config_frame, textvariable=self.win_var, width=20).grid(row=4, column=1, columnspan=2, padx=3, sticky=W)

        ttk.Label(config_frame, text='间隔:').grid(row=4, column=3, sticky=E, padx=3)
        self.interval_var = IntVar(value=self.catcher.auto_interval)
        self.interval_combo = ttk.Combobox(config_frame, textvariable=self.interval_var, values=[3, 5, 10, 15, 30], width=8, state='readonly')
        self.interval_combo.grid(row=4, column=4, padx=3, sticky=W)
        self.interval_combo.bind('<<ComboboxSelected>>', lambda e: self.save_config())

        btn_frame = ttk.Frame(main_frame)
        btn_frame.grid(row=3, column=0, columnspan=4, pady=12)

        self.capture_btn = ttk.Button(btn_frame, text='📸 立即抓取', command=self.start_capture, width=14)
        self.capture_btn.grid(row=0, column=0, padx=5)

        self.auto_btn = ttk.Button(btn_frame, text='🔄 自动监控', command=self.start_auto_capture, width=14)
        self.auto_btn.grid(row=0, column=1, padx=5)

        self.stop_btn = ttk.Button(btn_frame, text='⏹ 停止', command=self.stop_capture, state=DISABLED, width=14)
        self.stop_btn.grid(row=0, column=2, padx=5)

        ttk.Button(btn_frame, text='📁 查看结果', command=self.open_output_dir, width=14).grid(row=0, column=3, padx=5)

        options_frame = ttk.Frame(main_frame)
        options_frame.grid(row=4, column=0, columnspan=4, pady=5)

        self.dedup_var = BooleanVar(value=True)
        ttk.Checkbutton(options_frame, text='启用去重', variable=self.dedup_var).pack(side=LEFT, padx=10)

        self.clean_cache_btn = ttk.Button(options_frame, text='🗑 清理缓存', command=self.clean_cache, width=12)
        self.clean_cache_btn.pack(side=LEFT, padx=10)

        ttk.Label(main_frame, text='日志输出:', font=('', 10)).grid(row=5, column=0, sticky=W, pady=(10,0))

        log_frame = ttk.Frame(main_frame)
        log_frame.grid(row=6, column=0, columnspan=4, sticky='nsew', pady=5)

        self.log_text = Text(log_frame, height=12, width=75, state=DISABLED, font=('Consolas', 9))
        self.log_text.grid(row=0, column=0, sticky='nsew')

        scrollbar = ttk.Scrollbar(log_frame, orient='vertical', command=self.log_text.yview)
        scrollbar.grid(row=0, column=1, sticky='ns')
        self.log_text['yscrollcommand'] = scrollbar.set

        main_frame.columnconfigure(3, weight=1)
        main_frame.rowconfigure(6, weight=1)

        self.log('='*45)
        self.log('微信群聊消息抓取工具 v2.1 已就绪')
        if self.tesseract_ok:
            self.log(f'✅ OCR引擎已就绪 ({self.tesseract_version})')
        else:
            self.log('❌ 请先安装 Tesseract OCR')
        self.log('请先点击"框选抓取区域"选择微信聊天区域')
        self.log('='*45)

    def log(self, message):
        self.log_text.config(state='normal')
        timestamp = datetime.now().strftime('%H:%M:%S')
        self.log_text.insert(END, f'[{timestamp}] {message}\n')
        self.log_text.see(END)
        self.log_text.config(state='DISABLED')
        self.root.update()

    def save_config(self):
        self.catcher.x = self.x_var.get()
        self.catcher.y = self.y_var.get()
        self.catcher.width = self.w_var.get()
        self.catcher.height = self.h_var.get()
        self.catcher.window_title = self.win_var.get()
        self.catcher.auto_interval = self.interval_var.get()
        self.catcher.save_config()

    def select_capture_area(self):
        self.log('请在弹出的黑色窗口中框选微信聊天区域...')
        self.root.iconify()
        time.sleep(0.3)

        selector = ScreenSelector()
        area = selector.select_area()

        self.root.deiconify()
        self.root.focus()

        if area:
            self.x_var.set(area[0])
            self.y_var.set(area[1])
            self.w_var.set(area[2] - area[0])
            self.h_var.set(area[3] - area[1])

            self.save_config()
            self.log(f'✅ 区域已设置: X={area[0]}, Y={area[1]}, W={area[2]-area[0]}, H={area[3]-area[1]}')
            self.log('💡 区域已保存，下次启动自动加载')
        else:
            self.log('⚠️ 未选择区域')

    def refresh_area(self):
        self.save_config()
        self.log('✅ 配置已刷新')

    def clean_cache(self):
        self.catcher.clean_old_cache(7)
        self.log('🗑️ 已清理7天前的缓存记录')

    def do_capture(self):
        self.log('[1] 查找微信窗口...')
        win = self.catcher.find_wechat_window()
        if not win:
            self.log('[错误] 未找到微信窗口，请确保微信已打开')
            return

        self.log(f'    找到: {win.title[:30]}...')
        self.log('[2] 捕获聊天区域...')

        capture_x = win.left + self.x_var.get()
        capture_y = win.top + self.y_var.get()
        capture_w = self.w_var.get()
        capture_h = self.h_var.get()

        screenshot = ImageGrab.grab(bbox=(capture_x, capture_y, capture_x + capture_w, capture_y + capture_h))

        timestamp = datetime.now().strftime('%Y%m%d_%H%M%S')
        capture_path = os.path.join(self.catcher.output_dir, f'wechat_capture_{timestamp}.png')
        screenshot.save(capture_path)
        self.log(f'    截图: {os.path.basename(capture_path)}')

        self.log('[3] OCR识别中...')
        if not self.tesseract_ok:
            self.log('[错误] OCR未安装，请先安装Tesseract')
            return

        try:
            custom_config = r'--oem 3 --psm 6'
            text = pytesseract.image_to_string(screenshot, lang='chi_sim', config=custom_config)
            if not text or not text.strip():
                self.log('[警告] 未识别到文字，请检查区域是否正确')
                return
        except Exception as e:
            self.log(f'[错误] OCR识别失败: {e}')
            return

        self.log('[4] 解析消息...')
        records = self.catcher.parse_message(text)
        self.log(f'    解析到 {len(records)} 条消息')

        if records:
            use_dedup = self.dedup_var.get()
            excel_path, new_count = self.catcher.export_to_excel(records, deduplicate=use_dedup)
            if new_count > 0:
                self.log(f'[成功] 新增 {new_count} 条记录到Excel')
            else:
                self.log('[信息] 无新记录（去重过滤）')
            self.log(f'    文件: {os.path.basename(excel_path)}')
        else:
            self.log('[提示] 未解析到有效消息')

    def start_capture(self):
        if not self.tesseract_ok:
            messagebox.showerror('错误', 'Tesseract OCR 未安装，请先安装！')
            return
        self.save_config()
        threading.Thread(target=self.do_capture, daemon=True).start()

    def start_auto_capture(self):
        if not self.tesseract_ok:
            messagebox.showerror('错误', 'Tesseract OCR 未安装，请先安装！')
            return

        self.is_capturing = True
        self.capture_btn.config(state=DISABLED)
        self.auto_btn.config(state=DISABLED)
        self.stop_btn.config(state=NORMAL)
        self.btn_select.config(state=DISABLED)

        interval = self.interval_var.get()
        self.log(f'[自动监控] 开始抓取，间隔 {interval} 秒')

        def auto_loop():
            count = 0
            while self.is_capturing:
                count += 1
                self.log(f'--- 第{count}次抓取 ---')
                self.do_capture()
                time.sleep(interval)

        threading.Thread(target=auto_loop, daemon=True).start()

    def stop_capture(self):
        self.is_capturing = False
        self.capture_btn.config(state=NORMAL)
        self.auto_btn.config(state=NORMAL)
        self.stop_btn.config(state=DISABLED)
        self.btn_select.config(state=NORMAL)
        self.log('[提示] 已停止自动监控')

    def open_output_dir(self):
        os.startfile(self.catcher.output_dir)


def main():
    root = Tk()
    app = WeChatCatcherApp(root)
    root.mainloop()


if __name__ == '__main__':
    main()