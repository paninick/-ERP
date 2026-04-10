import requests
import json

try:
    response = requests.get("http://localhost:8000/")
    print("服务器状态: 正常运行 ✅")
    print("响应状态码:", response.status_code)
    print("响应内容:", json.dumps(response.json(), indent=2, ensure_ascii=False))
    print("\n")
    print("API 文档地址: http://localhost:8000/docs")
    print("OpenAPI 文档地址: http://localhost:8000/openapi.json")
except Exception as e:
    print("服务器状态: 连接失败 ❌")
    print("错误:", str(e))
