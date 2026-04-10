# -*- coding: utf-8 -*-
import os, sys, time, re, json, threading
from datetime import datetime
from tkinter import *
from tkinter import ttk, messagebox
import pandas as pd
from playwright.sync_api import sync_playwright

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
OUTPUT_DIR = os.path.join(BASE_DIR, 'output')
CACHE_FILE = os.path.join(OUTPUT_DIR, 'web_cache.json')
os.makedirs(OUTPUT_DIR, exist_ok=True)

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

class WebWeChatCatcher:
    def __init__(self):
        self.cache = load_json(CACHE_FILE, {'processed_msgs': {}})
        self.playwright = None
        self.browser = None
        self.page = None
        self.running = False

    def save_cache(self):
        save_json(CACHE_FILE, self.cache)

    def start_browser(self):
        """启动浏览器并打开微信网页版"""
        if self.playwright is None:
            self.playwright = sync_playwright().start()
        
        if self.browser is None:
            self.browser = self.playwright.chromium.launch(headless=False)
        
        if self.page is None:
            self.page = self.browser.new_page()
            self.page.goto('https://wx.qq.com/')
        
        return self.page

    def close_browser(self):
        """关闭浏览器"""
        if self.page:
            self.page.close()
            self.page = None
        if self.browser:
            self.browser.close()
            self.browser = None
        if self.playwright:
            self.playwright.stop()
            self.playwright = None

    def parse_message(self, msg_text, sender, msg_time):
        """解析单条消息"""
        if not msg_text or not msg_text.strip():
            return None

        msg_text = msg_text.strip()
        
        # 判断消息类型
        msg_type = 'unknown'
        is_demand = False
        is_confirm = False
        is_delivered = False

        # 1. 需求消息：@某人 + 任务描述
        at_users = re.findall(r'@(\S+)', msg_text)
        if at_users and ('送' in msg_text or '拿' in msg_text or '取' in msg_text or '带' in msg_text):
            msg_type = 'demand'
            is_demand = True

        # 2. 确认消息："我去"、"收到"、"好的"等
        confirm_keywords = ['我去', '收到', '好的', '没问题', 'ok', 'OK']
        if any(kw in msg_text for kw in confirm_keywords):
            msg_type = 'confirm'
            is_confirm = True

        # 3. 送达消息："送到"、"已到"、"完成"等
        deliver_keywords = ['送到', '已到', '完成', '搞定']
        if any(kw in msg_text for kw in deliver_keywords):
            msg_type = 'delivered'
            is_delivered = True

        # 提取详情
        applicant = sender if is_demand else ''
        driver = at_users[0] if at_users else ''
        
        # 提取起点、终点、物品
        origin = ''
        destination = ''
        item = ''
        remark = ''

        if is_demand:
            # 提取起点："从X"、"到X拿"、"X拿"
            from_match = re.search(r'从([^\s,，。]+)', msg_text)
            if from_match:
                origin = from_match.group(1)
            else:
                # 找"到X拿"模式
                to_get_match = re.search(r'([^\s,，。]+)拿', msg_text)
                if to_get_match:
                    origin = to_get_match.group(1)

            # 提取终点："到X"、"送X"、"去X"
            to_match = re.search(r'送[^\s,，。]*?([^\s,，。]+)', msg_text)
            if not to_match:
                to_match = re.search(r'到[^\s,，。]*?([^\s,，。]+)', msg_text)
            if to_match:
                destination = to_match.group(1)

            # 提取物品："拿X"、"带X"、"送X"
            item_match = re.search(r'[拿取带送]([^\s,，。]+)', msg_text)
            if item_match:
                item = item_match.group(1)

            # 提取备注（逗号后面的内容）
            remark_match = re.search(r'[,，。](.*)$', msg_text)
            if remark_match:
                remark = remark_match.group(1).strip()

        return {
            'time': msg_time,
            'sender': sender,
            'type': msg_type,
            'applicant': applicant,
            'driver': driver,
            'origin': origin,
            'destination': destination,
            'item': item,
            'remark': remark,
            'raw': msg_text
        }

    def export(self, records):
        """导出到Excel"""
        if not records:
            return 0, None

        new_records = []
        for r in records:
            msg_id = f"{r.get('time', '')}_{r.get('sender', '')}_{r.get('raw', '')}"
            if msg_id not in self.cache['processed_msgs']:
                new_records.append(r)
                self.cache['processed_msgs'][msg_id] = datetime.now().isoformat()

        if not new_records:
            return 0, None

        self.save_cache()

        data = [{
            '时间': r.get('time', ''),
            '发送人': r.get('sender', ''),
            '消息类型': r.get('type', ''),
            '申请人': r.get('applicant', ''),
            '司机': r.get('driver', ''),
            '起点': r.get('origin', ''),
            '终点': r.get('destination', ''),
            '物品': r.get('item', ''),
            '备注': r.get('remark', ''),
            '原始消息': r.get('raw', '')
        } for r in new_records]

        df = pd.DataFrame(data)
        filename = f'用车明细_web_{datetime.now().strftime("%Y%m%d")}.xlsx'
        filepath = os.path.join(OUTPUT_DIR, filename)

        if os.path.exists(filepath):
            try:
                existing = pd.read_excel(filepath)
                df = pd.concat([existing, df], ignore_index=True)
            except: pass

        df.to_excel(filepath, index=False, engine='openpyxl')
        return len(new_records), filepath

class WebApp:
    def __init__(self):
        self.root = Tk()
        self.root.title('🚚 Web微信用车明细抓取工具')
        self.root.geometry('520x500')
        self.root.resizable(False, False)

        self.catcher = WebWeChatCatcher()
        self.running = False

        self.build_ui()

    def build_ui(self):
        f = ttk.Frame(self.root, padding=12)
        f.pack(fill=BOTH, expand=True)

        # 标题
        ttk.Label(f, text='🚚 Web微信用车明细抓取', font=('微软雅黑', 16, 'bold')).pack(pady=8)
        ttk.Label(f, text='推荐方案 - 直接读取网页消息', font=('', 9), foreground='gray').pack()

        # 按钮行1
        btn_row1 = ttk.Frame(f)
        btn_row1.pack(pady=12)
        self.open_btn = ttk.Button(btn_row1, text='🌐 打开微信网页', command=self.open_wechat, width=18)
        self.open_btn.grid(row=0, column=0, padx=5)
        self.scan_btn = ttk.Button(btn_row1, text='📝 立即扫描', command=self.scan, width=18, state=DISABLED)
        self.scan_btn.grid(row=0, column=1, padx=5)

        # 按钮行2
        btn_row2 = ttk.Frame(f)
        btn_row2.pack(pady=8)
        self.auto_btn = ttk.Button(btn_row2, text='🔄 自动监控', command=self.start_auto, width=18, state=DISABLED)
        self.auto_btn.grid(row=0, column=0, padx=5)
        self.stop_btn = ttk.Button(btn_row2, text='⏹ 停止', command=self.stop, width=18, state=DISABLED)
        self.stop_btn.grid(row=0, column=1, padx=5)
        ttk.Button(btn_row2, text='📁 查看表格', command=self.open_output, width=18).grid(row=0, column=2, padx=5)

        # 选项
        opt_frame = ttk.Frame(f)
        opt_frame.pack(pady=5)
        self.interval_var = IntVar(value=10)
        ttk.Label(opt_frame, text='监控间隔:').pack(side=LEFT)
        ttk.Combobox(opt_frame, textvariable=self.interval_var, values=[5,10,15,30,60], width=5, state='readonly').pack(side=LEFT, padx=5)
        ttk.Label(opt_frame, text='秒').pack(side=LEFT)

        ttk.Separator(f, orient='horizontal').pack(fill='x', pady=10)

        # 日志
        self.log = Text(f, height=10, width=58, font=('Consolas', 9), state=DISABLED)
        self.log.pack()
        scrollbar = ttk.Scrollbar(f, orient='vertical', command=self.log.yview)
        scrollbar.place(relx=1, rely=0, height=160)
        self.log['yscrollcommand'] = scrollbar.set

        self.write('请先点击"打开微信网页"，扫码登录后选择群聊')

    def write(self, msg):
        self.log.config(state=NORMAL)
        self.log.insert(END, f'[{datetime.now().strftime("%H:%M:%S")}] {msg}\n')
        self.log.see(END)
        self.log.config(state=DISABLED)
        self.root.update()

    def open_wechat(self):
        self.write('正在打开微信网页版...')
        try:
            self.catcher.start_browser()
            self.write('✅ 浏览器已打开')
            self.write('👉 请在浏览器中扫码登录，然后选择"富泉用车运输组"群聊')
            self.scan_btn.config(state=NORMAL)
            self.auto_btn.config(state=NORMAL)
        except Exception as e:
            self.write(f'❌ 打开失败: {e}')

    def do_scan(self):
        self.write('正在扫描消息...')
        try:
            page = self.catcher.page
            if not page:
                self.write('❌ 请先打开微信网页')
                return

            # 这里需要根据实际网页结构调整选择器
            # 由于微信网页版结构可能变化，这里给出基础框架
            self.write('⚠️ 提示：需要根据实际网页结构调整消息选择器')
            self.write('当前为演示框架，实际使用时需要调试选择器')
            
            # 模拟一些测试数据
            test_records = [
                self.catcher.parse_message('@其实不然 帮我到康主任那般拿个吊瓶熨斗送吉恩检品，我有一个人在那里回修', '小蒋', '15:02'),
                self.catcher.parse_message('我去', '范晓军61557', '15:03'),
                self.catcher.parse_message('送到', '范晓军61557', '15:59'),
                self.catcher.parse_message('@范晓军61557 到冒兆收衣服回来', '康康', '15:02')
            ]
            
            valid_records = [r for r in test_records if r]
            self.write(f'✅ 扫描到 {len(valid_records)} 条消息')

            if valid_records:
                count, filepath = self.catcher.export(valid_records)
                if count > 0:
                    self.write(f'✅ 新增 {count} 条记录')
                    self.write(f'📁 {os.path.basename(filepath)}')
                    os.startfile(OUTPUT_DIR)
                else:
                    self.write('ℹ️ 无新记录（去重过滤）')

        except Exception as e:
            self.write(f'❌ 扫描失败: {e}')

    def scan(self):
        threading.Thread(target=self.do_scan, daemon=True).start()

    def start_auto(self):
        self.running = True
        self.auto_btn.config(state=DISABLED)
        self.stop_btn.config(state=NORMAL)
        self.write(f'[自动监控] 每 {self.interval_var.get()} 秒扫描一次')

        def run():
            n = 0
            while self.running:
                n += 1
                self.write(f'--- 第{n}次 ---')
                self.do_scan()
                time.sleep(self.interval_var.get())

        threading.Thread(target=run, daemon=True).start()

    def stop(self):
        self.running = False
        self.auto_btn.config(state=NORMAL)
        self.stop_btn.config(state=DISABLED)
        self.write('[已停止]')

    def open_output(self):
        os.startfile(OUTPUT_DIR)

    def on_closing(self):
        self.catcher.close_browser()
        self.root.destroy()

if __name__ == '__main__':
    app = WebApp()
    app.root.protocol('WM_DELETE_WINDOW', app.on_closing)
    app.root.mainloop()
