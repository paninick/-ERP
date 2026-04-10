# 技能库与MCP工具清单

**创建日期**：2026-04-01  
**用途**：记录当前可用的技能和MCP工具，供专家团调用

---

## 一、核心技能工具

### 1.1 搜索类技能（P0优先级）

| 技能名称 | 描述 | 适用场景 |
|---------|------|---------|
| **SearchCodebase** | 代码库语义搜索，使用专有检索/嵌入模型 | 搜索代码结构、功能实现、设计模式 |
| **Grep** | 基于ripgrep的代码搜索，支持正则表达式 | 精确搜索特定代码、字符串、模式 |
| **Glob** | 文件模式匹配，支持glob模式 | 查找特定类型的文件、按名称搜索 |
| **search（子代理）** | 启动子代理处理搜索任务，自治执行 | 高优先级、复杂搜索任务 |

### 1.2 文件操作类技能（P0优先级）

| 技能名称 | 描述 | 适用场景 |
|---------|------|---------|
| **Read** | 读取本地文件系统的文件 | 查看现有文件内容 |
| **Write** | 写入文件到本地文件系统 | 创建新文件 |
| **Edit** | 对文件进行精确字符串替换 | 修改现有文件（优先使用） |
| **DeleteFile** | 删除文件（常规文件或目录） | 删除不需要的文件 |

### 1.3 项目管理类技能（P1优先级）

| 技能名称 | 描述 | 适用场景 |
|---------|------|---------|
| **TodoWrite** | 创建和管理待办事项列表 | 规划多步骤复杂任务 |
| **TodoList** | 查看当前待办事项列表 | 跟踪任务进度 |

### 1.4 系统工具类技能（P1优先级）

| 技能名称 | 描述 | 适用场景 |
|---------|------|---------|
| **RunCommand** | 在用户电脑上运行命令 | 执行构建、测试、安装等 |
| **CheckCommandStatus** | 获取之前执行命令的状态 | 检查长时间运行命令的进度 |
| **StopCommand** | 终止当前运行的命令 | 停止不需要的进程 |

### 1.5 诊断与交互类技能（P2优先级）

| 技能名称 | 描述 | 适用场景 |
|---------|------|---------|
| **GetDiagnostics** | 获取VS Code的语言诊断 | 检查代码错误、警告 |
| **AskUserQuestion** | 向用户提问，获取输入 | 澄清需求、获取偏好 |
| **WebSearch** | 搜索互联网获取实时信息 | 获取最新文档、技术资讯 |
| **WebFetch** | 获取网页内容，转换为markdown | 读取在线文档、参考资料 |

### 1.6 Web预览类技能（P2优先级）

| 技能名称 | 描述 | 适用场景 |
|---------|------|---------|
| **OpenPreview** | 显示可用的预览URL给用户 | 展示Web应用、前端页面 |

---

## 二、MCP工具库

### 2.1 浏览器工具

#### 2.1.1 integrated_browser（集成浏览器）

| 工具名称 | 描述 |
|---------|------|
| browser_navigate | 导航到URL |
| browser_navigate_back | 导航回上一页 |
| browser_tabs | 管理浏览器标签 |
| browser_snapshot | 获取页面快照和元素引用 |
| browser_take_screenshot | 截图 |
| browser_click | 点击元素 |
| browser_hover | 悬停元素 |
| browser_type | 输入文本 |
| browser_select_option | 选择选项 |
| browser_press_key | 按键 |
| browser_get_attribute | 获取元素属性 |
| browser_scroll | 滚动 |
| browser_console_messages | 获取控制台消息 |
| browser_network_requests | 获取网络请求 |
| browser_wait_for | 等待 |

#### 2.1.2 mcp_Chrome_DevTools_MCP（Chrome DevTools）

| 工具名称 | 描述 |
|---------|------|
| navigate_page | 导航到页面 |
| new_page | 新建页面 |
| list_pages | 列出页面 |
| select_page | 选择页面 |
| close_page | 关闭页面 |
| click | 点击 |
| fill | 填充 |
| fill_form | 填充表单 |
| hover | 悬停 |
| press_key | 按键 |
| type_text | 输入文本 |
| upload_file | 上传文件 |
| drag | 拖拽 |
| evaluate_script | 执行脚本 |
| handle_dialog | 处理对话框 |
| get_console_message | 获取控制台消息 |
| list_console_messages | 列出控制台消息 |
| get_network_request | 获取网络请求 |
| list_network_requests | 列出网络请求 |
| take_screenshot | 截图 |
| take_snapshot | 获取快照 |
| take_memory_snapshot | 获取内存快照 |
| resize_page | 调整页面大小 |
| emulate | 模拟设备 |
| lighthouse_audit | Lighthouse审计 |
| performance_start_trace | 开始性能追踪 |
| performance_stop_trace | 停止性能追踪 |
| performance_analyze_insight | 性能分析洞察 |
| wait_for | 等待 |

#### 2.1.3 mcp_Playwright（Playwright）

| 工具名称 | 描述 |
|---------|------|
| playwright_navigate | 导航 |
| playwright_click | 点击 |
| playwright_fill | 填充 |
| playwright_select | 选择 |
| playwright_hover | 悬停 |
| playwright_type | 输入 |
| playwright_press_key | 按键 |
| playwright_drag | 拖拽 |
| playwright_upload_file | 上传文件 |
| playwright_screenshot | 截图 |
| playwright_save_as_pdf | 保存为PDF |
| playwright_evaluate | 执行脚本 |
| playwright_get | GET请求 |
| playwright_post | POST请求 |
| playwright_put | PUT请求 |
| playwright_patch | PATCH请求 |
| playwright_delete | DELETE请求 |
| playwright_expect_response | 期望响应 |
| playwright_assert_response | 断言响应 |
| playwright_console_logs | 控制台日志 |
| playwright_custom_user_agent | 自定义User-Agent |
| playwright_get_visible_text | 获取可见文本 |
| playwright_get_visible_html | 获取可见HTML |
| playwright_go_back | 返回 |
| playwright_go_forward | 前进 |
| playwright_resize | 调整大小 |
| playwright_close | 关闭 |
| playwright_click_and_switch_tab | 点击并切换标签 |
| playwright_iframe_click | iframe点击 |
| playwright_iframe_fill | iframe填充 |
| start_codegen_session | 开始代码生成会话 |
| end_codegen_session | 结束代码生成会话 |
| get_codegen_session | 获取代码生成会话 |
| clear_codegen_session | 清除代码生成会话 |

#### 2.1.4 mcp_Puppeteer（Puppeteer）

| 工具名称 | 描述 |
|---------|------|
| puppeteer_navigate | 导航 |
| puppeteer_click | 点击 |
| puppeteer_fill | 填充 |
| puppeteer_select | 选择 |
| puppeteer_hover | 悬停 |
| puppeteer_screenshot | 截图 |
| puppeteer_evaluate | 执行脚本 |

### 2.2 Excel工具（mcp_Excel）

| 工具名称 | 描述 |
|---------|------|
| excel_read_sheet | 读取Excel工作表 |
| excel_write_to_sheet | 写入Excel工作表 |
| excel_create_table | 创建Excel表格 |
| excel_format_range | 格式化Excel范围 |
| excel_copy_sheet | 复制Excel工作表 |
| excel_describe_sheets | 描述Excel工作表 |
| excel_screen_capture | Excel截图 |

### 2.3 GitHub工具（mcp_GitHub）

| 工具名称 | 描述 |
|---------|------|
| search_repositories | 搜索仓库 |
| create_repository | 创建仓库 |
| fork_repository | Fork仓库 |
| create_branch | 创建分支 |
| list_commits | 列出提交 |
| get_file_contents | 获取文件内容 |
| create_or_update_file | 创建或更新文件 |
| push_files | 推送文件 |
| create_issue | 创建Issue |
| update_issue | 更新Issue |
| list_issues | 列出Issues |
| get_issue | 获取Issue |
| add_issue_comment | 添加Issue评论 |
| search_issues | 搜索Issues |
| create_pull_request | 创建Pull Request |
| list_pull_requests | 列出Pull Requests |
| get_pull_request | 获取Pull Request |
| update_pull_request_branch | 更新Pull Request分支 |
| create_pull_request_review | 创建Pull Request审查 |
| get_pull_request_reviews | 获取Pull Request审查 |
| get_pull_request_comments | 获取Pull Request评论 |
| get_pull_request_files | 获取Pull Request文件 |
| get_pull_request_status | 获取Pull Request状态 |
| merge_pull_request | 合并Pull Request |
| search_code | 搜索代码 |
| search_users | 搜索用户 |

### 2.4 知识图谱工具（mcp_Knowledge_Graph_Memory）

| 工具名称 | 描述 |
|---------|------|
| create_entities | 创建实体 |
| update_entities | 更新实体 |
| delete_entities | 删除实体 |
| search_nodes | 搜索节点 |
| open_nodes | 打开节点 |
| read_graph | 读取图谱 |
| create_relations | 创建关系 |
| update_relations | 更新关系 |
| delete_relations | 删除关系 |
| add_observations | 添加观察 |
| delete_observations | 删除观察 |

### 2.5 顺序思考工具（mcp_Sequential_Thinking）

| 工具名称 | 描述 |
|---------|------|
| sequentialthinking | 顺序思考，深度分析复杂问题 |

### 2.6 文档查询工具（mcp_context7）

| 工具名称 | 描述 |
|---------|------|
| resolve-library-id | 解析库ID |
| query-docs | 查询文档 |

---

## 三、技能匹配规则

### 3.1 优先级判定
- **P0**：核心技能，必须优先使用
- **P1**：重要技能，需要时使用
- **P2**：辅助技能，按需使用

### 3.2 搜索技能选择
```
如果需要搜索：
  1. 如果是高优先级、复杂搜索 → 使用 search（子代理）
  2. 否则如果是语义搜索、架构搜索 → 使用 SearchCodebase
  3. 否则如果是精确代码搜索 → 使用 Grep
  4. 否则如果是文件查找 → 使用 Glob
```

### 3.3 文件操作技能选择
```
如果需要修改文件：
  1. 如果是创建新文件 → 使用 Write
  2. 否则 → 使用 Edit（优先）

如果需要读取文件：
  1. 使用 Read
```

### 3.4 任务规划
```
如果任务涉及3个或更多工具调用：
  1. 使用 TodoWrite 进行规划
  2. 然后按步骤执行
```

---

## 四、技能调用流程

```
1. 专家团接收任务
2. 分析任务需求
3. 从技能库中查找匹配的技能
4. 按照优先级排序
5. 调用MCP执行技能
6. 获取结果并评估
7. 如果需要，重复步骤3-6
8. 整合结果，生成最终输出
```

---

**文档结束**

**维护人**：Trae Code CN  
**更新日期**：2026-04-01
