
# AI 虚拟试穿系统

一个功能强大的 AI 虚拟试穿系统，支持上传人物照片和服装图片，一键生成真实的试穿效果！

## ✨ 功能特性

- 📸 图片上传：支持人物照、上衣、下装的上传
- 🎨 多种风格：原生风格、日常风格、电商风格、大片风格
- ⚡ 快速生成：通常 1 分钟左右即可预览结果
- 📱 响应式设计：支持各种设备
- 💾 结果保存：支持下载和分享

## 🛠️ 技术栈

**前端：**
- React 18
- Vite
- TypeScript
- Tailwind CSS
- Axios

**后端：**
- FastAPI
- Python 3.11+
- PyTorch
- Uvicorn

## 🚀 快速开始

### 前端启动

```bash
cd frontend
npm install
npm run dev
```

### 后端启动

```bash
cd backend
python -m venv venv
venv\Scripts\activate
pip install -r requirements.txt
python main.py
```

## 📂 项目结构

```
anytryon-clone/
├── frontend/          # 前端项目
│   ├── src/
│   │   ├── components/
│   │   ├── pages/
│   │   ├── services/
│   │   └── utils/
│   └── package.json
├── backend/           # 后端 API
│   ├── api/
│   ├── models/
│   ├── services/
│   └── main.py
└── README.md
```

## 🎯 使用说明

1. 上传一张清晰的人物照片
2. 上传上衣图片（必填），下装可选
3. 选择你喜欢的风格
4. 点击生成，等待 1 分钟左右
5. 预览并下载你的试穿效果图！

祝你使用愉快！💖

