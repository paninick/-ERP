
import requests
import json

# 测试API配置
BASE_URL = "http://localhost:8080"

def test_notice_api():
    print("=" * 80)
    print("测试打样通知API")
    print("=" * 80)
    
    # 1. 先登录获取token
    print("\n1. 正在登录...")
    login_url = f"{BASE_URL}/login"
    login_data = {
        "username": "admin",
        "password": "admin123"
    }
    
    try:
        login_response = requests.post(login_url, json=login_data)
        print(f"登录状态码: {login_response.status_code}")
        
        if login_response.status_code == 200:
            login_result = login_response.json()
            print(f"登录结果: {login_result}")
            
            if login_result.get('code') == 200:
                token = login_result.get('token')
                print(f"获取到Token: {token[:50]}...")
                
                # 2. 使用token查询打样通知列表
                print("\n2. 查询打样通知列表...")
                notice_url = f"{BASE_URL}/erp/notice/list"
                headers = {
                    "Authorization": f"Bearer {token}"
                }
                params = {
                    "pageNum": 1,
                    "pageSize": 10
                }
                
                notice_response = requests.get(notice_url, headers=headers, params=params)
                print(f"查询状态码: {notice_response.status_code}")
                
                if notice_response.status_code == 200:
                    notice_result = notice_response.json()
                    print(f"\n查询结果:")
                    print(f"  总条数: {notice_result.get('total')}")
                    print(f"  当前页数据条数: {len(notice_result.get('rows', []))}")
                    
                    if notice_result.get('rows'):
                        print("\n  前3条数据:")
                        for i, row in enumerate(notice_result.get('rows')[:3]):
                            print(f"\n  第{i+1}条:")
                            print(f"    打样编号: {row.get('sampleNo')}")
                            print(f"    客户名称: {row.get('customerName')}")
                            print(f"    打样款号: {row.get('styleCode')}")
                            print(f"    业务员: {row.get('salesName')}")
                            print(f"    审批人: {row.get('auditByNickName')}")
                            print(f"    审批状态: {row.get('auditStatus')}")
                else:
                    print(f"查询失败: {notice_response.text}")
            else:
                print(f"登录失败: {login_result.get('msg')}")
        else:
            print(f"登录请求失败: {login_response.text}")
            
    except Exception as e:
        print(f"\n错误: {e}")
        import traceback
        traceback.print_exc()

if __name__ == "__main__":
    test_notice_api()

