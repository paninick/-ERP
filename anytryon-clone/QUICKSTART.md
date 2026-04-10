
# 🚀 快速开始指南

## 前置要求

- Node.js 16+ 
- Python 3.11+
- npm 或 yarn

## 启动项目

### 方式一：使用启动脚本（推荐）

1. **启动后端：**
   双击运行 `start-backend.bat`

2. **启动前端：**
   双击运行 `start-frontend.bat`

### 方式二：手动启动

#### 启动后端

```bash
cd backend
python -m venv venv
venv\Scripts\activate
pip install -r requirements.txt
python main.py
```

后端将在 http://localhost:8000 运行
API 文档：http://localhost:8000/docs

#### 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端将在 http://localhost:3000 运行

## 使用说明

1. 打开浏览器访问 http://localhost:3000
2. 上传人物照片（必填）
3. 上传上衣图片（必填）
4. 可选：上传下装图片
5. 选择喜欢的风格
6. 点击"✨ 一键生成试穿效果"
7. 等待生成，下载你的试穿效果图！

## 项目结构

```
anytryon-clone/
├── frontend/              # 前端项目
│   ├── src/
│   │   ├── components/    # React 组件
│   │   │   ├── Header.jsx
│   │   │   ├── UploadSection.jsx
│   │   │   ├── StyleSelector.jsx
│   │   │   ├── ResultSection.jsx
│   │   │   └── Footer.jsx
│   │   ├── App.jsx        # 主应用组件
│   │   ├── main.jsx       # 入口文件
│   │   └── index.css      # 样式文件
│   ├── index.html
│   ├── package.json
│   ├── vite.config.js
│   ├── tailwind.config.js
│   └── postcss.config.js
├── backend/               # 后端项目
│   ├── main.py           # FastAPI 主文件
│   └── requirements.txt  # Python 依赖
├── start-frontend.bat    # 前端启动脚本
├── start-backend.bat     # 后端启动脚本
└── README.md             # 项目说明
```

## 下一步

- 集成真正的 AI 换装模型（如 Any2AnyTryon）
- 添加用户认证系统
- 实现结果历史记录
- 添加更多风格选项
- 优化用户体验

祝你使用愉快！💖✨

