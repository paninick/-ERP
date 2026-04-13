#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
RuoYi 运维面板后端
"""
import os
import subprocess
import threading
import time
import socket
import json
import signal
from pathlib import Path
from flask import Flask, jsonify, request, Response
from datetime import datetime

app = Flask(__name__, static_folder='static')

BASE_DIR = r"D:\erp\RuoYi-Vue"
JAR_PATH = r"D:\erp\RuoYi-Vue\ruoyi-admin\target\ruoyi-admin.jar"
LOG_DIR = r"D:\erp\RuoYi-Vue\ruoyi-admin\logs"
JAVA_HOME = r"C:\Users\91306\AppData\Local\Programs\Microsoft\jdk-17.0.10.7-hotspot\bin\java.exe"

# 全局进程引用
backend_process = None
process_lock = threading.Lock()

def find_java():
    """找到可用的 java"""
    candidates = [
        JAVA_HOME,
        r"C:\Program Files\Java\jdk-17\bin\java.exe",
        r"C:\Program Files\Microsoft\jdk-17.0.10.7-hotspot\bin\java.exe",
    ]
    for c in candidates:
        if os.path.exists(c):
            return c
    return "java"  # fallback to PATH

def check_port(port):
    """检查端口是否在监听"""
    try:
        with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
            s.settimeout(1)
            return s.connect_ex(('localhost', port)) == 0
    except:
        return False

def get_backend_pid():
    """获取后端进程 PID"""
    try:
        result = subprocess.run(
            ['powershell', '-Command',
             "Get-CimInstance Win32_Process | Where-Object { $_.Name -eq 'java.exe' -and $_.CommandLine -like '*ruoyi-admin*' } | Select-Object -ExpandProperty ProcessId"],
            capture_output=True, text=True, timeout=5
        )
        pid_str = result.stdout.strip()
        if pid_str and pid_str.isdigit():
            return int(pid_str)
    except:
        pass
    return None

def get_service_status():
    """获取服务状态"""
    backend_up = check_port(8080)
    redis_up = check_port(6379)
    mysql_up = check_port(3306)
    frontend_up = check_port(80)
    backend_pid = get_backend_pid()

    return {
        "backend": {
            "running": backend_up,
            "pid": backend_pid,
            "port": 8080,
            "label": "后端服务"
        },
        "frontend": {
            "running": frontend_up,
            "port": 80,
            "label": "前端服务"
        },
        "redis": {
            "running": redis_up,
            "port": 6379,
            "label": "Redis"
        },
        "mysql": {
            "running": mysql_up,
            "port": 3306,
            "label": "MySQL"
        },
        "jar_exists": os.path.exists(JAR_PATH),
        "jar_time": datetime.fromtimestamp(os.path.getmtime(JAR_PATH)).strftime('%Y-%m-%d %H:%M:%S') if os.path.exists(JAR_PATH) else None
    }

@app.route('/api/status')
def status():
    return jsonify(get_service_status())

@app.route('/api/start', methods=['POST'])
def start_backend():
    global backend_process

    if check_port(8080):
        return jsonify({"success": False, "message": "后端已在运行中 (端口 8080)"})

    if not os.path.exists(JAR_PATH):
        return jsonify({"success": False, "message": f"JAR 不存在，请先编译: {JAR_PATH}"})

    java = find_java()
    cmd = [java, '-jar', JAR_PATH]

    try:
        with process_lock:
            backend_process = subprocess.Popen(
                cmd,
                cwd=BASE_DIR,
                stdout=subprocess.DEVNULL,
                stderr=subprocess.DEVNULL,
                creationflags=subprocess.CREATE_NEW_PROCESS_GROUP if os.name == 'nt' else 0
            )
        return jsonify({"success": True, "message": f"后端启动中，PID: {backend_process.pid}，等待约 30 秒..."})
    except Exception as e:
        return jsonify({"success": False, "message": f"启动失败: {str(e)}"})

@app.route('/api/stop', methods=['POST'])
def stop_backend():
    pid = get_backend_pid()
    if not pid:
        if not check_port(8080):
            return jsonify({"success": False, "message": "后端未在运行"})

    try:
        subprocess.run(
            ['powershell', '-Command', f'Stop-Process -Id {pid} -Force'],
            capture_output=True, timeout=10
        )
        time.sleep(1)
        return jsonify({"success": True, "message": f"已停止后端进程 PID: {pid}"})
    except Exception as e:
        return jsonify({"success": False, "message": f"停止失败: {str(e)}"})

@app.route('/api/restart', methods=['POST'])
def restart_backend():
    stop_backend()
    time.sleep(2)
    return start_backend()

@app.route('/api/start-frontend', methods=['POST'])
def start_frontend():
    if check_port(80):
        return jsonify({"success": False, "message": "前端已在运行中 (端口 80)"})

    try:
        npm = r"C:\Program Files\nodejs\npm.cmd"
        if not os.path.exists(npm):
            npm = "npm"

        frontend_dir = os.path.join(BASE_DIR, "ruoyi-ui")
        proc = subprocess.Popen(
            [npm, 'run', 'dev'],
            cwd=frontend_dir,
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL,
            creationflags=subprocess.CREATE_NEW_PROCESS_GROUP if os.name == 'nt' else 0
        )
        return jsonify({"success": True, "message": f"前端启动中，等待约 20 秒..."})
    except Exception as e:
        return jsonify({"success": False, "message": f"启动失败: {str(e)}"})

@app.route('/api/stop-frontend', methods=['POST'])
def stop_frontend():
    if not check_port(80):
        return jsonify({"success": False, "message": "前端未在运行"})

    try:
        subprocess.run(
            ['powershell', '-Command',
             "Get-Process -Name node | Where-Object { $_.CommandLine -like '*vue-cli-service*' } | Stop-Process -Force"],
            capture_output=True, timeout=10
        )
        time.sleep(1)
        return jsonify({"success": True, "message": "前端已停止"})
    except Exception as e:
        return jsonify({"success": False, "message": f"停止失败: {str(e)}"})

@app.route('/api/stop-all', methods=['POST'])
def stop_all():
    results = []

    # 停止后端
    pid = get_backend_pid()
    if pid:
        try:
            subprocess.run(['powershell', '-Command', f'Stop-Process -Id {pid} -Force'],
                         capture_output=True, timeout=10)
            results.append(f"后端已停止 (PID: {pid})")
        except:
            pass

    # 停止前端 - 改进的查找方式
    try:
        # 先杀掉所有 node 进程中的 vue-cli-service
        subprocess.run(
            ['powershell', '-Command',
             "Get-Process -Name node -ErrorAction SilentlyContinue | ForEach-Object { if ($_.MainWindowTitle -like '*ruoyi*' -or $_.Id -eq (Get-NetTCPConnection -LocalPort 80 -ErrorAction SilentlyContinue | Select-Object -First 1 -ExpandProperty OwningProcess)) { Stop-Process -Id $_.Id -Force } }"],
            capture_output=True, timeout=10
        )
        # 直接查找占用80端口的进程
        subprocess.run(
            ['powershell', '-Command',
             "Get-NetTCPConnection -LocalPort 80 -ErrorAction SilentlyContinue | ForEach-Object { Stop-Process -Id $_.OwningProcess -Force -ErrorAction SilentlyContinue }"],
            capture_output=True, timeout=10
        )
        results.append("前端已停止")
    except:
        pass

    if not results:
        return jsonify({"success": False, "message": "没有运行中的服务"})

    return jsonify({"success": True, "message": " | ".join(results)})

@app.route('/api/start-mysql', methods=['POST'])
def start_mysql():
    if check_port(3306):
        return jsonify({"success": False, "message": "MySQL 已在运行"})

    try:
        result = subprocess.run(
            ['powershell', '-Command', 'Start-Service MySQL'],
            capture_output=True, text=True, timeout=10
        )
        time.sleep(2)
        if check_port(3306):
            return jsonify({"success": True, "message": "MySQL 已启动"})
        return jsonify({"success": False, "message": "MySQL 启动可能失败，请手动检查"})
    except Exception as e:
        return jsonify({"success": False, "message": f"启动失败: {str(e)}"})

@app.route('/api/stop-mysql', methods=['POST'])
def stop_mysql():
    if not check_port(3306):
        return jsonify({"success": False, "message": "MySQL 未在运行"})

    try:
        subprocess.run(
            ['powershell', '-Command', 'Stop-Service MySQL -Force'],
            capture_output=True, timeout=10
        )
        time.sleep(1)
        return jsonify({"success": True, "message": "MySQL 已停止"})
    except Exception as e:
        return jsonify({"success": False, "message": f"停止失败: {str(e)}"})

@app.route('/api/start-redis', methods=['POST'])
def start_redis():
    if check_port(6379):
        return jsonify({"success": False, "message": "Redis 已在运行"})

    try:
        redis_path = r"C:\Program Files\Redis\redis-server.exe"
        if not os.path.exists(redis_path):
            redis_path = r"C:\Redis\redis-server.exe"
        if not os.path.exists(redis_path):
            return jsonify({"success": False, "message": "Redis 未安装或路径不存在"})

        subprocess.Popen(
            [redis_path],
            stdout=subprocess.DEVNULL,
            stderr=subprocess.DEVNULL,
            creationflags=subprocess.CREATE_NEW_PROCESS_GROUP if os.name == 'nt' else 0
        )
        time.sleep(2)
        if check_port(6379):
            return jsonify({"success": True, "message": "Redis 已启动"})
        return jsonify({"success": False, "message": "Redis 启动可能失败"})
    except Exception as e:
        return jsonify({"success": False, "message": f"启动失败: {str(e)}"})

@app.route('/api/stop-redis', methods=['POST'])
def stop_redis():
    if not check_port(6379):
        return jsonify({"success": False, "message": "Redis 未在运行"})

    try:
        subprocess.run(
            ['powershell', '-Command',
             "Get-Process -Name redis-server | Stop-Process -Force"],
            capture_output=True, timeout=10
        )
        time.sleep(1)
        return jsonify({"success": True, "message": "Redis 已停止"})
    except Exception as e:
        return jsonify({"success": False, "message": f"停止失败: {str(e)}"})

@app.route('/api/build', methods=['POST'])
def build():
    """Maven 编译"""
    def run_build():
        pass  # SSE stream handles this

    mvn = r"C:\Users\91306\AppData\Local\Programs\Apache\apache-maven-3.9.9\bin\mvn.cmd"
    if not os.path.exists(mvn):
        mvn = "mvn"

    def generate():
        yield "data: 开始编译...\n\n"
        proc = subprocess.Popen(
            [mvn, 'clean', 'package', '-DskipTests'],
            cwd=BASE_DIR,
            stdout=subprocess.PIPE,
            stderr=subprocess.STDOUT,
            text=True,
            encoding='utf-8',
            errors='replace'
        )
        for line in proc.stdout:
            line = line.rstrip()
            if line:
                yield f"data: {line}\n\n"
        proc.wait()
        if proc.returncode == 0:
            yield "data: ✅ 编译成功！\n\n"
            yield "data: __BUILD_SUCCESS__\n\n"
        else:
            yield "data: ❌ 编译失败，请检查错误信息\n\n"
            yield "data: __BUILD_FAILED__\n\n"

    return Response(generate(), mimetype='text/event-stream',
                    headers={'Cache-Control': 'no-cache', 'X-Accel-Buffering': 'no'})

@app.route('/api/logs')
def get_logs():
    """获取日志"""
    log_type = request.args.get('type', 'error')
    lines = int(request.args.get('lines', 50))

    log_file = os.path.join(LOG_DIR, f'sys-{log_type}.log')
    if not os.path.exists(log_file):
        return jsonify({"logs": [], "error": f"日志文件不存在: {log_file}"})

    try:
        result = subprocess.run(
            ['powershell', '-Command', f"Get-Content '{log_file}' -Tail {lines} | Out-String"],
            capture_output=True, text=True, encoding='utf-8', errors='replace', timeout=5
        )
        content = result.stdout
        log_lines = [l for l in content.split('\n') if l.strip()]
        return jsonify({"logs": log_lines[-lines:]})
    except Exception as e:
        return jsonify({"logs": [], "error": str(e)})

@app.route('/api/diagnose')
def diagnose():
    """诊断问题"""
    issues = []
    suggestions = []

    status = get_service_status()

    if not status['mysql']['running']:
        issues.append("❌ MySQL 未运行 (端口 3306)")
        suggestions.append("启动 MySQL 服务：net start mysql 或打开 MySQL Workbench")

    if not status['redis']['running']:
        issues.append("❌ Redis 未运行 (端口 6379)")
        suggestions.append("启动 Redis：redis-server 或 net start redis")

    if not status['backend']['running']:
        issues.append("❌ 后端服务未运行 (端口 8080)")
        if not status['jar_exists']:
            suggestions.append("JAR 不存在，请先点击「编译」按钮")
        else:
            suggestions.append(f"JAR 存在 (编译时间: {status['jar_time']})，点击「启动」按钮")
    else:
        issues.append("✅ 后端服务运行中")
        # 检查最新错误日志
        log_file = os.path.join(LOG_DIR, 'sys-error.log')
        if os.path.exists(log_file):
            try:
                result = subprocess.run(
                    ['powershell', '-Command', f"Get-Content '{log_file}' -Tail 20 | Out-String"],
                    capture_output=True, text=True, encoding='utf-8', errors='replace', timeout=5
                )
                content = result.stdout
                if 'NoResourceFoundException' in content:
                    issues.append("⚠️ 检测到 NoResourceFoundException")
                    suggestions.append("Controller 未被加载，请重新编译后重启后端")
                if 'NullPointerException' in content:
                    issues.append("⚠️ 检测到 NullPointerException")
                    suggestions.append("查看错误日志了解详情")
                if 'Cannot create connection' in content or 'Communications link failure' in content:
                    issues.append("⚠️ 数据库连接失败")
                    suggestions.append("检查 MySQL 是否运行，以及 application-druid.yml 配置")
            except:
                pass

    if not issues:
        issues.append("✅ 所有服务正常")

    return jsonify({"issues": issues, "suggestions": suggestions, "status": status})

@app.route('/')
def index():
    return app.send_static_file('index.html')

if __name__ == '__main__':
    os.makedirs('static', exist_ok=True)
    print("Ops Panel started at http://localhost:9999")
    app.run(host='0.0.0.0', port=9999, debug=False, threaded=True)
