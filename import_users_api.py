import requests
import os
import json

# ERP系统API配置
ERP_BASE_URL = "http://localhost:8080"
LOGIN_URL = f"{ERP_BASE_URL}/login"
USER_IMPORT_URL = f"{ERP_BASE_URL}/system/user/importData"
UPLOAD_URL = f"{ERP_BASE_URL}/common/upload"

# 用户凭证
USERNAME = "admin"
PASSWORD = "admin123"

def login():
    """获取登录token"""
    try:
        data = {
            "username": USERNAME,
            "password": PASSWORD,
            "code": "",
            "uuid": ""
        }
        
        response = requests.post(LOGIN_URL, data=data)
        result = response.json()
        
        if result.get("code") == 200:
            token = result.get("token")
            print("✅ 登录成功")
            return token
        else:
            print(f"❌ 登录失败: {result.get('msg')}")
            return None
    except Exception as e:
        print(f"❌ 登录过程中出现错误: {e}")
        return None

def upload_file(token, file_path):
    """上传文件"""
    try:
        headers = {
            "Authorization": f"Bearer {token}"
        }
        
        files = {
            "file": open(file_path, 'rb')
        }
        
        response = requests.post(UPLOAD_URL, headers=headers, files=files)
        result = response.json()
        
        if result.get("code") == 200:
            file_name = result.get("fileName")
            print(f"✅ 文件上传成功: {file_name}")
            return file_name
        else:
            print(f"❌ 文件上传失败: {result.get('msg')}")
            return None
    except Exception as e:
        print(f"❌ 文件上传过程中出现错误: {e}")
        return None

def import_users(token, file_path):
    """导入用户数据"""
    try:
        headers = {
            "Authorization": f"Bearer {token}"
        }
        
        files = {
            "file": open(file_path, 'rb')
        }
        
        data = {
            "updateSupport": "true"  # 是否支持更新已存在的用户
        }
        
        response = requests.post(USER_IMPORT_URL, headers=headers, files=files, data=data)
        result = response.json()
        
        if result.get("code") == 200:
            print("✅ 用户数据导入成功")
            print(f"📊 导入结果: {result.get('msg')}")
            return True
        else:
            print(f"❌ 用户数据导入失败: {result.get('msg')}")
            return False
    except Exception as e:
        print(f"❌ 用户数据导入过程中出现错误: {e}")
        return False

def main():
    print("=== ERP系统用户导入测试 ===")
    
    # 获取登录token
    token = login()
    if not token:
        return
    
    print("\n=== 导入第一个用户文件 ===")
    file_path1 = r'C:\Users\91306\Downloads\user_1775309546654.xlsx'
    if os.path.exists(file_path1):
        success1 = import_users(token, file_path1)
        if success1:
            print("✅ 第一个文件导入成功")
        else:
            print("❌ 第一个文件导入失败")
    else:
        print(f"❌ 文件不存在: {file_path1}")
    
    print("\n=== 导入第二个用户文件 ===")
    file_path2 = r'C:\Users\91306\Downloads\user_1775309613502.xlsx'
    if os.path.exists(file_path2):
        success2 = import_users(token, file_path2)
        if success2:
            print("✅ 第二个文件导入成功")
        else:
            print("❌ 第二个文件导入失败")
    else:
        print(f"❌ 文件不存在: {file_path2}")

if __name__ == "__main__":
    main()
