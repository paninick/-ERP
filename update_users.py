
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def update_users():
    """更新用户表"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("根据用户列表更新sys_user表")
        print("="*100)
        
        # 用户数据
        user_list = [
            (1, 'admin', '超级管理员'),
            (2, 'fq', '富泉'),
            (108, 'cxj', '陈小建'),
            (109, 'zx', '张晞'),
            (110, 'wjf', '吴建峰'),
            (111, 'zh', '朱辉'),
            (112, 'bj', '宾洁'),
            (113, 'zxn', '张彐南'),
            (114, 'cdf', '陈登峰'),
            (115, 'cjg', '陈建果'),
            (116, 'lhy', '陆红云'),
            (117, 'cp', '陈萍'),
            (118, 'kj', '康进'),
            (119, 'hyf', '何玉芳'),
            (120, 'sxy', '苏晓勇'),
            (121, 'qsp', '秦素平'),
            (122, 'zjj', '仲佳佳'),
            (123, 'dzh', '丁佐华'),
            (124, 'jjf', '蒋建芳'),
            (125, 'cww', '陈韦韦'),
            (126, 'yy', '杨义'),
            (127, 'wwf', '王文风'),
            (128, 'wmm', '王曼曼'),
            (129, 'jxf', '季晓芳'),
            (130, 'syy', '单媛媛'),
            (131, 'jh', '金华'),
            (132, 'nzy', '倪志云'),
            (133, 'cxq', '陈晓清'),
            (134, 'xdp', '许东坡'),
            (136, 'gsj', '顾素娟'),
            (137, 'xy', '谢燕'),
            (139, 'cj', '陈娟'),
            (140, 'lzz', '李振珍'),
            (141, 'xjj', '徐晶晶'),
            (142, 'yyq', '杨亚庆'),
            (143, 'zl', '查磊'),
            (144, 'syf', '司永芳'),
            (145, 'lsy', '李世云'),
            (146, 'gy', '顾云'),
            (147, 'whh', '王宏华'),
            (148, 'test', 'test'),
            (149, 'zyj', '周拥军'),
            (150, 'hf', '洪芳'),
            (151, 'lhq', '李慧群'),
            (152, 'lby', '刘宝煜'),
            (155, 'ql', '秦丽'),
            (156, 'cjj', '曹娇娇'),
            (157, 'zy', '周严'),
            (158, 'yj', '杨建'),
            (159, 'ly', '刘勇'),
            (160, 'sdh', '孙大海'),
            (161, 'gsm', '顾淑梅'),
            (162, 'ylh', '严丽华'),
            (163, 'zw', '张玮'),
            (164, 'mh', '梅红'),
            (165, 'gf', '葛菲'),
            (166, 'qyx', '祁雨翔'),
            (167, 'qah', '钱爱华'),
            (168, 'zqw', '周千文'),
            (169, 'otani', '大谷康二'),
            (170, 'iemitsu', '家光利幸'),
            (171, 'zxh', '朱秀华'),
            (172, 'hyh', '胡宇航'),
            (173, 'qf', '邱帆'),
            (174, 'grp', '顾蓉平'),
            (175, 'xl', '夏亮'),
            (176, 'xhy', '徐海燕')
        ]
        
        update_count = 0
        insert_count = 0
        
        for user_id, user_name, nick_name in user_list:
            # 查找现有用户
            cursor.execute("SELECT user_id FROM sys_user WHERE user_id = %s", (user_id,))
            existing = cursor.fetchone()
            
            if existing:
                # 更新用户
                sql = "UPDATE sys_user SET user_name = %s, nick_name = %s WHERE user_id = %s"
                cursor.execute(sql, (user_name, nick_name, user_id))
                if cursor.rowcount > 0:
                    print(f"  已更新: ID {user_id} -> {user_name} ({nick_name})")
                    update_count += 1
            else:
                # 插入新用户
                sql = """
                    INSERT INTO sys_user 
                    (user_id, user_name, nick_name, create_by, create_time)
                    VALUES (%s, %s, %s, 'admin', NOW())
                """
                cursor.execute(sql, (user_id, user_name, nick_name))
                print(f"  已插入: ID {user_id} -> {user_name} ({nick_name})")
                insert_count += 1
        
        conn.commit()
        
        print("\n" + "="*100)
        print(f"更新完成！")
        print(f"  更新: {update_count} 条")
        print(f"  插入: {insert_count} 条")
        print("="*100)
        
        # 验证
        print("\n【验证结果】")
        print("-"*100)
        sql = "SELECT user_id, user_name, nick_name FROM sys_user ORDER BY user_id"
        cursor.execute(sql)
        print("user_id | user_name | nick_name")
        print("-"*60)
        for row in cursor.fetchall():
            print(f"{row[0]} | {row[1]} | {row[2]}")
        
        print("\n" + "="*100)
        print("用户表更新完成！")
        print("="*100)
        
    except Exception as e:
        print(f"更新失败: {e}")
        import traceback
        traceback.print_exc()
        if conn:
            conn.rollback()
    finally:
        if conn:
            conn.close()

if __name__ == "__main__":
    update_users()

