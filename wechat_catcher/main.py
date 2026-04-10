# -*- coding: utf-8 -*-
import os, sys, time, re, json, threading, hashlib
from datetime import datetime
from tkinter import *
from tkinter import ttk, messagebox
from PIL import Image, ImageGrab, ImageEnhance, ImageOps
import pygetwindow as gw
import pytesseract
import pandas as pd
import ctypes

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
CONFIG_FILE = os.path.join(BASE_DIR, 'config.json')
OUTPUT_DIR = os.path.join(BASE_DIR, 'output')
CACHE_FILE = os.path.join(OUTPUT_DIR, 'cache.json')
os.makedirs(OUTPUT_DIR, exist_ok=True)

def get_dpi_scale():
    """获取DPI缩放比例"""
    try:
        user32 = ctypes.windll.user32
        user32.SetProcessDPIAware()
        return user32.GetDpiForSystem() / 96.0
    except:
        return 1.0

DPI_SCALE = get_dpi_scale()

TESSERACT_PATHS = [r'C:\Program Files\Tesseract-OCR\tesseract.exe']
for p in TESSERACT_PATHS:
    if os.path.exists(p):
        pytesseract.pytesseract.tesseract_cmd = p
        break

def load_json(path, default):
    if os.path.exists(path):
        try:
            with open(path, 'r', encoding='utf-8') as f:
                return json.load(f)
        except: pass
    return default

def save_json(path, data):
    with open(path, 'w', encoding='utf-8') as f:
        json.dump(data, f, ensure_ascii=False, indent=2)

class AreaSelector:
    def select(self):
        root = Tk()
        root.attributes('-fullscreen', True)
        root.attributes('-alpha', 0.35)
        root.configure(bg='black')
        canvas = Canvas(root, cursor='cross', bg='black')
        canvas.pack(fill=BOTH, expand=True)
        Label(root, text='拖拽框选微信聊天消息区域，按Esc取消', bg='black', fg='white', font=('微软雅黑', 22)).place(relx=0.5, rely=0.08, anchor=CENTER)
        
        self.start = [0, 0]
        def on_down(e): self.start = [e.x, e.y]
        def on_drag(e):
            canvas.delete('rect')
            canvas.create_rectangle(self.start[0], self.start[1], e.x, e.y, outline='red', width=4, tags='rect')
        def on_up(e):
            if e.x > self.start[0] and e.y > self.start[1]:
                self.result = (self.start[0], self.start[1], e.x, e.y)
            root.destroy()
        root.bind('<Button-1>', on_down)
        root.bind('<B1-Motion>', on_drag)
        root.bind('<ButtonRelease-1>', on_up)
        root.bind('<Escape>', lambda e: root.destroy())
        root.mainloop()
        return self.result

class WeChatCatcher:
    def __init__(self):
        self.config = load_json(CONFIG_FILE, {'x': 300, 'y': 100, 'w': 700, 'h': 500})
        self.cache = load_json(CACHE_FILE, {})

    def save_config(self):
        save_json(CONFIG_FILE, self.config)
        save_json(CACHE_FILE, self.cache)

    def capture(self, x=None, y=None, w=None, h=None):
        # 如果没有传入坐标，使用配置的坐标（框选的坐标）
        if x is None:
            x = self.config.get('x', 300)
        if y is None:
            y = self.config.get('y', 100)
        if w is None:
            w = self.config.get('w', 700)
        if h is None:
            h = self.config.get('h', 500)
        
        # 不应用DPI缩放 - 让ImageGrab自动处理
        # DPI缩放问题通过其他方式解决
        
        img = ImageGrab.grab((x, y, x + w, y + h))
        return img

    def preprocess_image(self, img):
        """图片预处理 - 提高OCR识别准确率"""
        # 转换为灰度图
        img = img.convert('L')
        
        # 提高对比度
        enhancer = ImageEnhance.Contrast(img)
        img = enhancer.enhance(2.0)
        
        # 提高亮度
        enhancer = ImageEnhance.Brightness(img)
        img = enhancer.enhance(1.5)
        
        # 反色（如果是深色背景）
        # 计算平均亮度
        pixels = list(img.getdata())
        avg_brightness = sum(pixels) / len(pixels)
        if avg_brightness < 128:
            img = ImageOps.invert(img)
        
        # 二值化
        threshold = 150
        img = img.point(lambda p: p > threshold and 255 or 0)
        
        return img

    def ocr(self, img, return_processed=False):
        try:
            # 预处理图片
            processed_img = self.preprocess_image(img)
            # 使用更优化的OCR参数
            text = pytesseract.image_to_string(processed_img, lang='chi_sim', config='--oem 3 --psm 6 --dpi 300')
            if return_processed:
                return text, processed_img
            return text
        except:
            if return_processed:
                return '', None
            return ''

    def parse_logistics(self, text):
        """解析物流/用车申请消息 - 简化版"""
        records = []

        # 清理OCR识别错误
        text = re.sub(r'[_\-~`「」【】|]', '', text)
        lines = [l.strip() for l in text.split('\n') if l.strip()]

        for line in lines:
            # 查找时间
            time_match = re.search(r'(\d{1,2}:\d{2})', line)
            if not time_match:
                continue

            time_str = time_match.group(1)

            # 提取所有@提及
            mentions = ','.join(re.findall(r'@(\S+)', line))

            # 提取申请人（@后面的人名）
            applicants = re.findall(r'@(\S+)', line)

            # 提取目的地关键词后的内容
            destination = ''
            for kw in ['到', '发往', '送去', '送至', '发到', '去往', '送', '去', '送往']:
                if kw in line:
                    idx = line.index(kw)
                    dest = line[idx+len(kw):]
                    # 截取到@或特殊字符
                    match = re.search(r'[@#\s\d]', dest)
                    if match:
                        dest = dest[:match.start()]
                    dest = dest.strip()[:20]
                    if len(dest) >= 2:
                        destination = dest
                        break

            # 提取物品/数量
            item = ''
            for kw in ['件', '箱', '个', '台', '套', '批', '吨', '袋', '车', '人', '位']:
                if kw in line:
                    match = re.search(rf'(\d+.*{kw})', line)
                    if match:
                        item = match.group(1)[:15]
                        break

            # 提取送货意见
            opinion = ''
            for kw in ['急', '尽快', '加急', '速', '优先', '重要', '注意', '麻烦', '谢谢']:
                if kw in line:
                    opinion = kw
                    break

            record = {
                'time': time_str,
                'applicant': applicants[0] if applicants else '',
                'item': item,
                'destination': destination,
                'opinion': opinion,
                'mentions': mentions,
                'raw': line
            }

            records.append(record)

        return records

    def is_dup(self, content):
        h = hashlib.md5(content.encode()).hexdigest()
        return h in self.cache

    def add_cache(self, content):
        h = hashlib.md5(content.encode()).hexdigest()
        self.cache[h] = datetime.now().isoformat()
        self.save_config()

    def export(self, records):
        """导出到Excel（带汇总统计）"""
        if not records:
            return 0, None, {}
        
        new_records = [r for r in records if r.get('raw') and not self.is_dup(r.get('raw', ''))]
        for r in new_records:
            self.add_cache(r.get('raw', ''))
        
        if not new_records:
            return 0, None, {}
        
        # 准备明细数据
        data = [{
            '时间': r.get('time', ''),
            '申请人': r.get('applicant', ''),
            '物品/人': r.get('item', ''),
            '目的地': r.get('destination', ''),
            '送货意见': r.get('opinion', ''),
            '@提及': r.get('mentions', ''),
            '原始消息': r.get('raw', '')
        } for r in new_records]
        
        df = pd.DataFrame(data)
        filename = f'用车明细_{datetime.now().strftime("%Y%m%d")}.xlsx'
        filepath = os.path.join(OUTPUT_DIR, filename)
        
        # 读取现有数据
        all_data = df
        if os.path.exists(filepath):
            try:
                existing = pd.read_excel(filepath)
                all_data = pd.concat([existing, df], ignore_index=True)
            except:
                all_data = df
        
        # 统计数据
        stats = self.calculate_statistics(all_data)
        
        # 写入Excel（两个工作表：明细 + 统计）
        with pd.ExcelWriter(filepath, engine='openpyxl') as writer:
            all_data.to_excel(writer, sheet_name='用车明细', index=False)
            
            # 统计数据
            stats_df = pd.DataFrame({
                '项目': ['总需求数', '今日新增'],
                '数量': [len(all_data), len(new_records)]
            })
            stats_df.to_excel(writer, sheet_name='统计汇总', index=False, startrow=0)
            
            # 按目的地统计
            if stats['by_destination']:
                dest_stats = pd.DataFrame(list(stats['by_destination'].items()), columns=['目的地', '需求数'])
                dest_stats.to_excel(writer, sheet_name='统计汇总', index=False, startrow=5)
        
        return len(new_records), filepath, stats

    def calculate_statistics(self, all_data):
        """计算统计数据"""
        stats = {
            'total': len(all_data),
            'by_destination': {}
        }
        
        for idx, row in all_data.iterrows():
            dest = str(row.get('目的地', '')).strip()
            if dest and len(dest) >= 2:
                if dest not in stats['by_destination']:
                    stats['by_destination'][dest] = 0
                stats['by_destination'][dest] += 1
        
        return stats

class App:
    def __init__(self):
        self.root = Tk()
        self.root.title('用车明细抓取工具')
        self.root.geometry('480x420')
        self.root.resizable(False, False)
        
        self.catcher = WeChatCatcher()
        self.running = False
        self.last_area = None
        
        # 从配置文件加载上次的区域
        cfg = self.catcher.config
        if 'x' in cfg and 'y' in cfg and 'w' in cfg and 'h' in cfg:
            self.last_area = (cfg['x'], cfg['y'], cfg['x'] + cfg['w'], cfg['y'] + cfg['h'])
        
        self.build_ui()

    def build_ui(self):
        f = ttk.Frame(self.root, padding=12)
        f.pack(fill=BOTH, expand=True)
        
        # 标题
        ttk.Label(f, text='🚚 用车明细抓取工具', font=('微软雅黑', 16, 'bold')).pack(pady=8)
        
        # 状态提示
        status_text = '✅ 区域已锁定' if self.last_area else '⚠️ 请先框选区域'
        self.status_label = ttk.Label(f, text=status_text, font=('', 10), foreground='gray')
        self.status_label.pack()
        
        # 按钮行1
        btn_row1 = ttk.Frame(f)
        btn_row1.pack(pady=12)
        ttk.Button(btn_row1, text='📷 框选区域', command=self.select_area, width=16).grid(row=0, column=0, padx=5)
        ttk.Button(btn_row1, text='📝 立即扫描', command=self.capture, width=16).grid(row=0, column=1, padx=5)
        
        # 按钮行2
        btn_row2 = ttk.Frame(f)
        btn_row2.pack(pady=8)
        self.auto_btn = ttk.Button(btn_row2, text='🔄 自动监控', command=self.start_auto, width=16)
        self.auto_btn.grid(row=0, column=0, padx=5)
        self.stop_btn = ttk.Button(btn_row2, text='⏹ 停止', command=self.stop, width=16, state=DISABLED)
        self.stop_btn.grid(row=0, column=1, padx=5)
        ttk.Button(btn_row2, text='📁 查看表格', command=self.open_output, width=16).grid(row=0, column=2, padx=5)
        
        # 选项
        opt_frame = ttk.Frame(f)
        opt_frame.pack(pady=5)
        self.dedup_var = BooleanVar(value=True)
        ttk.Checkbutton(opt_frame, text='去重', variable=self.dedup_var).pack(side=LEFT, padx=10)
        ttk.Label(opt_frame, text='间隔:').pack(side=LEFT)
        self.interval_var = IntVar(value=5)
        ttk.Combobox(opt_frame, textvariable=self.interval_var, values=[3,5,10,15,30], width=5, state='readonly').pack(side=LEFT, padx=5)
        ttk.Label(opt_frame, text='秒').pack(side=LEFT)
        
        ttk.Separator(f, orient='horizontal').pack(fill='x', pady=10)
        
        # 日志
        self.log = Text(f, height=8, width=54, font=('Consolas', 9), state=DISABLED)
        self.log.pack()
        scrollbar = ttk.Scrollbar(f, orient='vertical', command=self.log.yview)
        scrollbar.place(relx=1, rely=0, height=130)
        self.log['yscrollcommand'] = scrollbar.set
        
        if self.last_area:
            self.write('区域已锁定，点击"立即扫描"开始')
        else:
            self.write('请先框选微信聊天区域')
        
        # 启动时读取现有统计
        self.show_existing_stats()

    def write(self, msg):
        self.log.config(state=NORMAL)
        self.log.insert(END, f'[{datetime.now().strftime("%H:%M:%S")}] {msg}\n')
        self.log.see(END)
        self.log.config(state=DISABLED)
        self.root.update()

    def select_area(self):
        self.write('👉 请框选微信聊天消息区域...')
        self.root.update()
        time.sleep(0.3)
        
        selector = AreaSelector()
        area = selector.select()
        
        if area:
            self.last_area = area
            x, y, x2, y2 = area
            w, h = x2 - x, y2 - y
            # 保存到配置文件，下次启动自动加载
            self.catcher.config['x'] = x
            self.catcher.config['y'] = y
            self.catcher.config['w'] = w
            self.catcher.config['h'] = h
            self.catcher.save_config()
            # 更新状态标签
            self.status_label.config(text='✅ 区域已锁定')
            self.write(f'✅ 区域已锁定: {w}x{h} (已保存)')
        else:
            self.write('⚠️ 未框选区域')

    def do_capture(self):
        # 使用框选的区域
        if self.last_area:
            x, y, x2, y2 = self.last_area
            w, h = x2 - x, y2 - y
            self.write(f'[抓取] 使用框选区域: 起点({x},{y}), 大小{w}x{h}')
        else:
            x = self.catcher.config.get('x', 300)
            y = self.catcher.config.get('y', 100)
            w = self.catcher.config.get('w', 700)
            h = self.catcher.config.get('h', 500)
            self.write(f'[抓取] 使用配置区域: 起点({x},{y}), 大小{w}x{h}')

        # 截图
        img = self.catcher.capture(x, y, w, h)

        self.write('[抓取] OCR识别中...')
        
        text = self.catcher.ocr(img)
        
        if not text.strip():
            self.write('❌ 未识别到文字')
            return
        
        self.write(f'✅ 识别到 {len(text)} 字符')
        self.write(f'📝 原始OCR结果预览：')
        for line in text.strip().split('\n')[:5]:
            if line.strip():
                self.write(f'   {line.strip()[:50]}')
        
        records = self.catcher.parse_logistics(text)
        self.write(f'解析到 {len(records)} 条记录')
        
        if records:
            count, filepath, stats = self.catcher.export(records)
            if count > 0:
                self.write(f'✅ 新增 {count} 条用车明细')
                self.write(f'📁 {os.path.basename(filepath)}')
                # 显示统计信息
                self.write(f'📊 总需求数: {stats["total"]}')
                if stats['by_destination']:
                    self.write('📍 按目的地统计:')
                    for dest, cnt in sorted(stats['by_destination'].items(), key=lambda x: x[1], reverse=True)[:5]:
                        self.write(f'   {dest}: {cnt}条')
                os.startfile(OUTPUT_DIR)
            else:
                self.write('ℹ️ 无新记录（去重过滤）')
        else:
            self.write('⚠️ 未解析到有效消息')

    def capture(self):
        threading.Thread(target=self.do_capture, daemon=True).start()

    def start_auto(self):
        self.running = True
        self.auto_btn.config(state=DISABLED)
        self.stop_btn.config(state=NORMAL)
        self.write(f'[自动监控] 每 {self.interval_var.get()} 秒抓取一次')
        
        def run():
            n = 0
            while self.running:
                n += 1
                self.write(f'--- 第{n}次 ---')
                self.do_capture()
                time.sleep(self.interval_var.get())
        
        threading.Thread(target=run, daemon=True).start()

    def stop(self):
        self.running = False
        self.auto_btn.config(state=NORMAL)
        self.stop_btn.config(state=DISABLED)
        self.write('[已停止]')

    def open_output(self):
        os.startfile(OUTPUT_DIR)

    def show_existing_stats(self):
        """显示现有统计数据"""
        filename = f'用车明细_{datetime.now().strftime("%Y%m%d")}.xlsx'
        filepath = os.path.join(OUTPUT_DIR, filename)
        
        if os.path.exists(filepath):
            try:
                df = pd.read_excel(filepath, sheet_name='用车明细')
                stats = self.catcher.calculate_statistics(df)
                self.write('--- 今日统计 ---')
                self.write(f'📊 总需求数: {stats["total"]}')
                if stats['by_destination']:
                    self.write('📍 按目的地统计:')
                    for dest, cnt in sorted(stats['by_destination'].items(), key=lambda x: x[1], reverse=True)[:5]:
                        self.write(f'   {dest}: {cnt}条')
            except Exception as e:
                pass

if __name__ == '__main__':
    App().root.mainloop()