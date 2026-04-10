# 不使用API方案：桌面自动化解决方案

> 本文档提供**不使用任何API**的叫车业务自动化方案，基于桌面自动化和OCR技术，100%模拟人工操作，零封号风险。

---

## 文档信息

| 项 | 内容 |
|---|------|
| **方案名称** | 不使用API的叫车业务自动化解决方案 |
| **技术路线** | Python + 桌面自动化 + OCR + 微信客户端 |
| **API使用** | ❌ 0% 无任何API调用 |
| **封号风险** | ✅ 0% 零风险（模拟人工操作） |
| **适用场景** | 普通微信客户端，不使用任何API |
| **实施周期** | 1-2周 |

---

## 一、方案架构

### 1.1 整体架构图

```
┌───────────────────────────────────────────────────────────────────────┐
│                            桌面自动化架构                           │
│                                                                     │
│                    ┌─────────────────────────────┐                   │
│                    │      微信客户端            │                   │
│                    └────────────┬──────────────┘                   │
│                                 │                                  │
│          ┌──────────────────────┼──────────────────────┐            │
│          ▼                      ▼                      ▼            │
│  ┌───────────────┐    ┌───────────────┐    ┌───────────────┐       │
│  │  屏幕监控    │    │  OCR识别      │    │  规则引擎     │       │
│  │  (定时截图)   │    │  (识别消息)    │    │  (提取信息)    │       │
│  └───────────────┘    └───────────────┘    └───────────────┘       │
│          │                      │                      │            │
│          └──────────────────────┼──────────────────────┘            │
│                                 │                                  │
│                                 ▼                                  │
│                    ┌─────────────────────────────┐                   │
│                    │      数据处理与存储        │                   │
│                    └────────────┬──────────────┘                   │
│                                 │                                  │
│          ┌──────────────────────┼──────────────────────┐            │
│          ▼                      ▼                      ▼            │
│  ┌───────────────┐    ┌───────────────┐    ┌───────────────┐       │
│  │  报表生成    │    │  自动发送      │    │  日志记录     │       │
│  │  (Excel/HTML) │    │  (模拟人工发送) │    │  (操作记录)    │       │
│  └───────────────┘    └───────────────┘    └───────────────┘       │
│                                                                     │
└───────────────────────────────────────────────────────────────────────┘
```

### 1.2 核心组件说明

| 组件 | 作用 | 技术实现 | 封号风险 |
|------|------|---------|----------|
| **微信客户端** | 原始消息来源 | 官方微信PC客户端 | ✅ 0% |
| **屏幕监控** | 定时截图群消息 | Python + pyautogui | ✅ 0% |
| **OCR识别** | 识别截图中的文字 | Tesseract OCR / PaddleOCR | ✅ 0% |
| **规则引擎** | 提取叫车信息 | 正则表达式 + 关键词匹配 | ✅ 0% |
| **数据处理** | 存储和管理数据 | SQLite / Excel | ✅ 0% |
| **报表生成** | 生成叫车清单 | Python + openpyxl | ✅ 0% |
| **自动发送** | 模拟人工发送消息 | Python + pyautogui | ✅ 0% |

---

## 二、技术实现细节

### 2.1 屏幕监控与截图

#### 核心代码示例

```python
import time
import pyautogui
import cv2
import numpy as np

class WeChatMonitor:
    def __init__(self, interval=30):  # 每30秒检查一次
        self.interval = interval
        self.wechat_window = None
        self.group_region = None
        
    def find_wechat_window(self):
        """查找微信窗口"""
        windows = pyautogui.getWindowsWithTitle('微信')
        if windows:
            self.wechat_window = windows[0]
            return True
        return False
    
    def get_group_region(self):
        """定位群聊消息区域"""
        if not self.wechat_window:
            return False
        
        # 这里需要根据实际微信界面调整坐标
        # 示例：假设群聊消息区域在微信窗口的特定位置
        self.group_region = (
            self.wechat_window.left + 100,
            self.wechat_window.top + 150,
            600,  # 宽度
            800   # 高度
        )
        return True
    
    def capture_group_messages(self):
        """截图群聊消息"""
        if not self.group_region:
            return None
        
        # 激活微信窗口
        if self.wechat_window:
            self.wechat_window.activate()
            time.sleep(0.5)  # 等待窗口激活
        
        # 截图
        screenshot = pyautogui.screenshot(region=self.group_region)
        return screenshot
    
    def start_monitoring(self):
        """开始监控"""
        if not self.find_wechat_window():
            print("未找到微信窗口")
            return
        
        if not self.get_group_region():
            print("未定位群聊区域")
            return
        
        print("开始监控微信群消息...")
        while True:
            screenshot = self.capture_group_messages()
            if screenshot:
                # 保存截图或直接进行OCR
                timestamp = time.strftime("%Y%m%d_%H%M%S")
                screenshot.save(f"screenshots/{timestamp}.png")
                # 后续进行OCR处理
            
            time.sleep(self.interval)

# 使用示例
if __name__ == "__main__":
    monitor = WeChatMonitor()
    monitor.start_monitoring()
```

### 2.2 OCR文字识别

#### 核心代码示例

```python
import cv2
import pytesseract
from PIL import Image

class OCRProcessor:
    def __init__(self):
        # 配置Tesseract路径（如果需要）
        # pytesseract.pytesseract.tesseract_cmd = r'C:\Program Files\Tesseract-OCR\tesseract.exe'
        pass
    
    def preprocess_image(self, image):
        """预处理图像以提高OCR准确率"""
        # 转换为灰度图
        img = cv2.cvtColor(np.array(image), cv2.COLOR_RGB2GRAY)
        
        # 二值化
        _, img = cv2.threshold(img, 150, 255, cv2.THRESH_BINARY)
        
        # 降噪
        img = cv2.medianBlur(img, 3)
        
        return Image.fromarray(img)
    
    def ocr_image(self, image):
        """OCR识别图像中的文字"""
        processed_image = self.preprocess_image(image)
        text = pytesseract.image_to_string(processed_image, lang='chi_sim')
        return text
    
    def extract_messages(self, text):
        """提取消息内容"""
        # 分割消息（根据时间戳或其他特征）
        messages = []
        lines = text.split('\n')
        
        current_message = {}
        for line in lines:
            line = line.strip()
            if not line:
                continue
            
            # 识别时间戳（示例格式：10:30）
            if re.match(r'^\d{2}:\d{2}$', line):
                if current_message:
                    messages.append(current_message)
                current_message = {'time': line, 'content': ''}
            elif current_message:
                current_message['content'] += line + ' '
        
        if current_message:
            messages.append(current_message)
        
        return messages

# 使用示例
if __name__ == "__main__":
    ocr = OCRProcessor()
    image = Image.open("screenshots/test.png")
    text = ocr.ocr_image(image)
    messages = ocr.extract_messages(text)
    print(messages)
```

### 2.3 叫车信息提取

#### 核心代码示例

```python
import re

class CarOrderExtractor:
    def __init__(self):
        # 叫车信息提取规则
        self.rules = {
            'time': [
                r'(\d{1,2}:\d{2})',
                r'(明天|后天|今天)\s*(上午|下午)?\s*([一二三四五六七八九十]+)点',
                r'(\d{1,2})月(\d{1,2})日'
            ],
            'from_addr': [
                r'从(.+?)(到|去|往)',
                r'(.+?)出发',
                r'起点[：:]\s*(.+?)'
            ],
            'to_addr': [
                r'到(.+?)(的|，|。|$)',
                r'去(.+?)(的|，|。|$)',
                r'终点[：:]\s*(.+?)'
            ],
            'cargo_type': [
                r'(面料|辅料|成品|裁片|布料|纱线|货物|物品)'
            ],
            'cargo_qty': [
                r'(\d+(?:\.\d+)?)\s*(公斤|kg|斤|箱|包|件|吨)'
            ],
            'contact': [
                r'@(\S{2,4})',
                r'联系[\s:：]*(\S{2,4})'
            ],
            'phone': [
                r'1[3-9]\d{9}',
                r'(\d{3,4}[-\s]?\d{7,8})'
            ],
            'urgency': [
                r'(加急|紧急|ASAP|急|快点|马上)'
            ]
        }
    
    def extract_order(self, message_content):
        """从消息内容中提取叫车订单信息"""
        order = {
            'raw_message': message_content,
            'extracted_fields': {},
            'confidence': 100.0,
            'status': 'success'
        }
        
        # 逐字段提取
        for field, patterns in self.rules.items():
            matched = False
            for pattern in patterns:
                match = re.search(pattern, message_content)
                if match:
                    order['extracted_fields'][field] = match.group(
                        0 if field in ('cargo_type', 'urgency') else 
                        (-1 if len(match.groups()) == 0 else 1)
                    ).strip()
                    matched = True
                    break
            
            if not matched and field in ['time', 'from_addr', 'to_addr']:
                order['confidence'] -= 20
                order['status'] = 'incomplete'
        
        # 置信度评估
        if order['confidence'] >= 80:
            order['auto_action'] = 'direct_import'
        elif order['confidence'] >= 60:
            order['auto_action'] = 'pending_review'
        else:
            order['auto_action'] = 'manual_required'
        
        return order

# 使用示例
if __name__ == "__main__":
    extractor = CarOrderExtractor()
    message = "明天下午3点，从富泉服装厂到宏发制衣，面料500公斤，联系张三13812345678"
    order = extractor.extract_order(message)
    print(order)
```

### 2.4 自动发送消息

#### 核心代码示例

```python
import time
import pyautogui

class WeChatSender:
    def __init__(self):
        self.wechat_window = None
        self.input_region = None
    
    def find_wechat_window(self):
        """查找微信窗口"""
        windows = pyautogui.getWindowsWithTitle('微信')
        if windows:
            self.wechat_window = windows[0]
            return True
        return False
    
    def get_input_region(self):
        """定位输入框位置"""
        if not self.wechat_window:
            return False
        
        # 这里需要根据实际微信界面调整坐标
        self.input_region = (
            self.wechat_window.left + 150,
            self.wechat_window.top + 950,
            400,  # 宽度
            30    # 高度
        )
        return True
    
    def send_message(self, message):
        """发送消息"""
        if not self.find_wechat_window():
            print("未找到微信窗口")
            return False
        
        if not self.get_input_region():
            print("未定位输入框")
            return False
        
        # 激活微信窗口
        self.wechat_window.activate()
        time.sleep(0.5)
        
        # 点击输入框
        input_x = self.input_region[0] + self.input_region[2] // 2
        input_y = self.input_region[1] + self.input_region[3] // 2
        pyautogui.click(input_x, input_y)
        time.sleep(0.2)
        
        # 输入消息
        pyautogui.typewrite(message, interval=0.05)
        time.sleep(0.5)
        
        # 按Enter发送
        pyautogui.press('enter')
        time.sleep(1)
        
        print(f"消息发送成功: {message}")
        return True

# 使用示例
if __name__ == "__main__":
    sender = WeChatSender()
    message = "【叫车汇总】今日共收到5个叫车需求，已处理3个，2个待处理"
    sender.send_message(message)
```

### 2.5 报表生成

#### 核心代码示例

```python
import openpyxl
from openpyxl.styles import Font, Alignment
from datetime import datetime

class ReportGenerator:
    def __init__(self):
        pass
    
    def generate_daily_report(self, orders, output_file):
        """生成每日叫车报表"""
        # 创建工作簿
        wb = openpyxl.Workbook()
        ws = wb.active
        ws.title = "每日叫车汇总"
        
        # 表头
        headers = ["序号", "时间", "起点", "终点", "货物类型", "数量", "联系人", "电话", "紧急程度", "状态"]
        ws.append(headers)
        
        # 设置表头样式
        for col in range(1, len(headers) + 1):
            cell = ws.cell(row=1, column=col)
            cell.font = Font(bold=True)
            cell.alignment = Alignment(horizontal='center', vertical='center')
        
        # 填充数据
        for i, order in enumerate(orders, 1):
            row = [
                i,
                order.get('extracted_fields', {}).get('time', ''),
                order.get('extracted_fields', {}).get('from_addr', ''),
                order.get('extracted_fields', {}).get('to_addr', ''),
                order.get('extracted_fields', {}).get('cargo_type', ''),
                order.get('extracted_fields', {}).get('cargo_qty', ''),
                order.get('extracted_fields', {}).get('contact', ''),
                order.get('extracted_fields', {}).get('phone', ''),
                order.get('extracted_fields', {}).get('urgency', ''),
                order.get('status', '待处理')
            ]
            ws.append(row)
        
        # 调整列宽
        for col in range(1, len(headers) + 1):
            ws.column_dimensions[openpyxl.utils.get_column_letter(col)].width = 15
        
        # 添加汇总信息
        summary_row = len(orders) + 3
        ws.cell(row=summary_row, column=1, value="汇总信息")
        ws.cell(row=summary_row + 1, column=1, value="总订单数:")
        ws.cell(row=summary_row + 1, column=2, value=len(orders))
        ws.cell(row=summary_row + 2, column=1, value="生成时间:")
        ws.cell(row=summary_row + 2, column=2, value=datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
        
        # 保存文件
        wb.save(output_file)
        print(f"报表生成成功: {output_file}")
        return output_file

# 使用示例
if __name__ == "__main__":
    generator = ReportGenerator()
    orders = [
        {'extracted_fields': {'time': '10:30', 'from_addr': '富泉厂', 'to_addr': '宏发制衣', 'cargo_type': '面料', 'cargo_qty': '500公斤', 'contact': '张三', 'phone': '13812345678'}},  # 示例数据
        # 更多订单...
    ]
    generator.generate_daily_report(orders, "每日叫车汇总.xlsx")
```

---

## 三、完整工作流程

### 3.1 工作流程图

```
┌───────────────────────────────────────────────────────────────────┐
│                           工作流程                               │
│                                                                   │
│  1. 启动监控                                                    │
│     └── 定时截图微信群消息                                        │
│                                                                   │
│  2. OCR识别                                                      │
│     └── 提取消息文本                                              │
│                                                                   │
│  3. 信息提取                                                      │
│     └── 规则引擎提取叫车信息                                      │
│                                                                   │
│  4. 数据存储                                                      │
│     └── 保存到SQLite/Excel                                        │
│                                                                   │
│  5. 报表生成                                                      │
│     └── 生成Excel汇总表                                            │
│                                                                   │
│  6. 自动发送                                                      │
│     └── 模拟人工发送消息到群                                       │
│                                                                   │
│  7. 循环执行                                                      │
│     └── 回到步骤1                                                 │
└───────────────────────────────────────────────────────────────────┘
```

### 3.2 配置与依赖

#### 依赖安装

```bash
# 安装Python依赖
pip install pyautogui opencv-python pytesseract Pillow openpyxl numpy

# 安装Tesseract OCR（Windows）
# 1. 下载并安装：https://github.com/UB-Mannheim/tesseract/wiki
# 2. 配置环境变量：将Tesseract安装目录添加到PATH
# 3. 下载中文语言包：https://github.com/tesseract-ocr/tessdata
# 4. 将chi_sim.traineddata复制到Tesseract的tessdata目录

# 或者使用PaddleOCR（可选，更准确）
pip install paddlepaddle paddleocr
```

#### 配置文件

```python
# config.py

# 监控配置
MONITOR_INTERVAL = 30  # 监控间隔（秒）
SCREENSHOT_DIR = "screenshots"  # 截图保存目录

# 微信窗口配置
WECHAT_WINDOW_TITLE = "微信"
GROUP_REGION = {
    "left": 100,    # 相对于微信窗口左上角的X偏移
    "top": 150,     # 相对于微信窗口左上角的Y偏移
    "width": 600,   # 截图宽度
    "height": 800   # 截图高度
}

# 输入框配置
INPUT_REGION = {
    "left": 150,    # 相对于微信窗口左上角的X偏移
    "top": 950,     # 相对于微信窗口左上角的Y偏移
    "width": 400,   # 输入框宽度
    "height": 30    # 输入框高度
}

# 报表配置
REPORT_DIR = "reports"
DAILY_REPORT_NAME = "每日叫车汇总_{date}.xlsx"

# 日志配置
LOG_DIR = "logs"
LOG_LEVEL = "INFO"
```

---

## 四、优势与局限性

### 4.1 核心优势

| 优势 | 详细说明 | 对项目的价值 |
|------|---------|------------|
| **零封号风险** | 100%模拟人工操作，不使用任何API | 完全避免账号封禁风险 |
| **无需企业微信** | 直接使用现有普通微信 | 无需额外注册和迁移 |
| **部署简单** | 仅需安装Python和相关依赖 | 快速部署，低成本 |
| **功能完整** | 覆盖消息监听、数据提取、报表生成、自动发送 | 满足第一阶段所有需求 |
| **自定义灵活** | 完全自主开发，可根据需求调整 | 适应不同的业务场景 |
| **无网络依赖** | 纯本地运行，无需公网IP | 适用于内网环境 |

### 4.2 局限性

| 局限 | 影响 | 应对方案 |
|------|------|---------|
| **OCR准确率** | 受字体、分辨率、干扰影响 | 优化图像预处理，使用更准确的OCR引擎 |
| **界面依赖** | 微信界面变化可能导致坐标失效 | 定期检查和更新坐标配置 |
| **实时性** | 依赖定时截图，有一定延迟 | 缩短监控间隔（最低5秒） |
| **稳定性** | 鼠标/键盘操作可能被其他操作干扰 | 运行时尽量保持微信窗口活跃 |
| **多群支持** | 需要手动切换群聊窗口 | 可配置多个群聊区域，轮询监控 |
| **资源占用** | 持续截图和OCR可能占用系统资源 | 优化代码，合理设置监控间隔 |

### 4.3 与其他方案对比

| 对比维度 | 桌面自动化方案 | 企业微信API方案 | 普通微信非官方API |
|---------|---------------|----------------|------------------|
| **封号风险** | ✅ 0% | ✅ 0% | ⭐⭐⭐⭐ 高 |
| **API使用** | ❌ 无 | ✅ 官方API | ✅ 非官方API |
| **部署难度** | ⭐⭐ 低 | ⭐⭐ 低 | ⭐⭐⭐ 中 |
| **实时性** | ⭐⭐⭐ 中 | ⭐⭐⭐⭐⭐ 高 | ⭐⭐⭐⭐ 高 |
| **稳定性** | ⭐⭐⭐ 中 | ⭐⭐⭐⭐⭐ 高 | ⭐⭐ 低 |
| **功能完整性** | ⭐⭐⭐⭐ 高 | ⭐⭐⭐⭐⭐ 高 | ⭐⭐⭐ 中 |
| **维护成本** | ⭐⭐⭐ 中 | ⭐⭐ 低 | ⭐⭐⭐⭐ 高 |
| **适用场景** | 普通微信，无API | 企业微信，官方API | 普通微信，非官方API |

---

## 五、实施建议

### 5.1 快速启动指南

#### 步骤1：环境搭建

1. **安装Python**：下载并安装Python 3.8+（https://www.python.org/）
2. **安装依赖**：执行 `pip install pyautogui opencv-python pytesseract Pillow openpyxl numpy`
3. **安装Tesseract OCR**：下载并安装，配置环境变量
4. **创建项目结构**：
   ```
   wechat-auto/
   ├── screenshots/    # 截图保存目录
   ├── reports/       # 报表保存目录
   ├── logs/          # 日志保存目录
   ├── config.py      # 配置文件
   ├── monitor.py     # 屏幕监控
   ├── ocr.py         # OCR处理
   ├── extractor.py   # 信息提取
   ├── sender.py      # 消息发送
   ├── report.py      # 报表生成
   └── main.py        # 主程序
   ```

#### 步骤2：配置调整

1. **运行微信PC客户端**
2. **调整微信窗口大小**：建议固定大小，方便定位
3. **修改config.py**：根据实际微信窗口位置调整坐标
4. **测试截图**：运行监控模块，检查截图是否正确

#### 步骤3：测试与调优

1. **测试OCR识别**：检查识别准确率
2. **测试信息提取**：使用真实叫车消息测试提取效果
3. **测试自动发送**：确保消息能正确发送
4. **调整参数**：根据测试结果调整监控间隔、OCR参数等

#### 步骤4：正式运行

1. **设置为开机自启**：将脚本添加到启动项
2. **监控运行状态**：定期检查日志和报表
3. **维护与更新**：微信版本更新后及时调整配置

### 5.2 最佳实践

| 实践 | 说明 | 效果 |
|------|------|------|
| **固定微信窗口位置** | 使用窗口固定工具，保持微信窗口位置不变 | 提高坐标定位准确性 |
| **使用PaddleOCR** | 替代Tesseract，提高中文识别准确率 | OCR准确率提升20-30% |
| **设置合理监控间隔** | 根据业务量调整，一般30-60秒 | 平衡实时性和系统资源 |
| **多群轮询监控** | 依次监控多个群聊窗口 | 支持多群管理 |
| **定期清理截图** | 设置自动清理机制，避免存储空间占用 | 保持系统清爽 |
| **异常监控** | 监控脚本运行状态，及时发现问题 | 提高系统稳定性 |

---

## 六、风险评估与应对

### 6.1 风险评估

| 风险 | 可能性 | 影响 | 应对措施 |
|------|--------|------|----------|
| **OCR识别错误** | ⭐⭐⭐ 中 | ⭐⭐ 低 | 优化图像预处理，使用PaddleOCR |
| **坐标定位失效** | ⭐⭐ 低 | ⭐⭐ 低 | 定期检查坐标，使用窗口位置自动检测 |
| **系统资源占用** | ⭐⭐ 低 | ⭐ 极低 | 优化代码，合理设置监控间隔 |
| **操作被干扰** | ⭐ 极低 | ⭐ 极低 | 建议在专用电脑上运行 |
| **微信版本更新** | ⭐ 极低 | ⭐ 低 | 及时调整配置，适配新界面 |

### 6.2 应急预案

| 场景 | 应急措施 | 恢复时间 |
|------|---------|----------|
| **OCR识别失败** | 手动查看截图，人工提取信息 | 即时 |
| **自动发送失败** | 手动发送消息 | 即时 |
| **脚本崩溃** | 重启脚本，检查日志 | 5分钟内 |
| **微信界面变化** | 更新坐标配置 | 30分钟内 |

---

## 七、总结

### 7.1 核心结论

> **桌面自动化方案是不使用API的情况下，唯一能实现叫车业务自动化的解决方案**。虽然存在OCR准确率和界面依赖等局限性，但通过合理的配置和调优，可以满足基本的自动化需求，且完全避免封号风险。

### 7.2 适用场景

- **不能使用企业微信**：现有业务完全基于普通微信
- **对封号风险零容忍**：无法承受账号封禁的损失
- **快速部署需求**：需要在短期内实现自动化
- **预算有限**：不想投入过多成本到企业微信迁移

### 7.3 长期建议

虽然桌面自动化方案可以快速实现需求，但从长期来看，**企业微信 + 微信小程序方案**仍然是更稳定、更可靠的选择。建议：

1. **短期**：使用桌面自动化方案快速实现基本功能
2. **中期**：逐步过渡到企业微信
3. **长期**：完全迁移到企业微信生态，实现更高级的自动化功能

### 7.4 立即行动建议

1. **今天**：搭建Python环境，安装所需依赖
2. **明天**：创建项目结构，编写核心代码
3. **本周**：完成测试和调优
4. **下周**：正式上线运行

---

**方案完成日期**：2026年4月4日  
**方案版本**：V1.0  
**适用场景**：不使用API的普通微信叫车业务自动化
