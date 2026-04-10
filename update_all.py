
import pymysql

config = {
    'host': 'localhost',
    'port': 3306,
    'user': 'root',
    'password': '',
    'database': 'ry_vue',
    'charset': 'utf8mb4'
}

def update_users_and_customers():
    """更新用户表和客户表"""
    conn = None
    try:
        conn = pymysql.connect(**config)
        cursor = conn.cursor()
        
        print("="*100)
        print("更新用户表和客户表")
        print("="*100)
        
        # ========== 1. 更新用户表 ==========
        print("\n【1】更新用户表...")
        
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
        
        user_update_count = 0
        user_insert_count = 0
        
        for user_id, user_name, nick_name in user_list:
            cursor.execute("SELECT user_id FROM sys_user WHERE user_id = %s", (user_id,))
            existing = cursor.fetchone()
            
            if existing:
                sql = "UPDATE sys_user SET user_name = %s, nick_name = %s WHERE user_id = %s"
                cursor.execute(sql, (user_name, nick_name, user_id))
                if cursor.rowcount > 0:
                    print(f"  用户已更新: ID {user_id} -> {nick_name}")
                    user_update_count += 1
            else:
                sql = """
                    INSERT INTO sys_user 
                    (user_id, user_name, nick_name, create_by, create_time)
                    VALUES (%s, %s, %s, 'admin', NOW())
                """
                cursor.execute(sql, (user_id, user_name, nick_name))
                print(f"  用户已插入: ID {user_id} -> {nick_name}")
                user_insert_count += 1
        
        conn.commit()
        print(f"\n用户表更新完成: 更新 {user_update_count} 条, 插入 {user_insert_count} 条")
        
        # ========== 2. 更新客户表 ==========
        print("\n【2】更新客户表...")
        
        customer_list = [
            ('T2玉田', 126),
            ('FST 松尾様', 124),
            ('KRB茂泉', 125),
            ('toa 尾方', 113),
            ('FST 松尾 channelone', 131),
            ('MN  片山样', 131),
            ('MN  古川样', 131),
            ('MN  杉浦样', 131),
            ('MN  吉安样', 131),
            ('FENIX  市川样', 131),
            ('FST 伊势样  POLO', 131),
            ('GFJ 井上様', 124),
            ('KRB池岛', 125),
            ('TOA尾方', 125),
            ('MN大沢', 125),
            ('MN手塚', 125),
            ('MAAK渡边', 125),
            ('圣玛利诺清水', 125),
            ('恩瓦德船野', 125),
            ('茉莉林井川', 125),
            ('田村驹浦边', 125),
            ('MN宫内', 125),
            ('FST  藤川様', 124),
            ('WP2-三文字', 128),
            ('MN-片山', 128),
            ('WP2-竹中', 128),
            ('爱格斯凯', 112),
            ('勃尔金', 112),
            ('伊藤忠-菊池', 128),
            ('逺藤能元', 112),
            ('青木', 112),
            ('工藤遼', 112),
            ('TOA-尾方', 128),
            ('MN-青松', 128),
            ('Idiom-上野', 128),
            ('Idiom -西坂', 128),
            ('MN江本', 125),
            ('榊原', 113),
            ('船木', 113),
            ('HIGLAND-链水', 127),
            ('CP-近藤様（DC）', 129),
            ('今村样', 123),
            ('FLEAD -好本', 127),
            ('丰岛', 126),
            ('FST村上', 125),
            ('CP-藤田様（AC）', 129),
            ('CP-森様（LE SOUK）', 129),
            ('市川样', 123),
            ('CP-松縄様', 129),
            ('高仓样', 123),
            ('家光様', 132),
            ('Chambray -山村', 127),
            ('岛田样', 123),
            ('LANDWELL-本村様', 129),
            ('YAMATO山本', 125),
            ('梅内様', 132),
            ('FST-町田', 127),
            ('CP-中込様（JUN）', 129),
            ('相原样', 123),
            ('伊藤忠-徐晓潇（速水）', 128),
            ('伊势様', 132),
            ('国武样', 123),
            ('国武', 132),
            ('苏豪', 112),
            ('和田様', 132),
            ('FST木村様（青山商事）', 129),
            ('DBL EYE-松井', 127),
            ('今野样', 123),
            ('MN   杉浦样', 131),
            ('MN-荻原', 128),
            ('森凯', 112),
            ('FST   吉田', 131),
            ('LEAP  -加藤', 127),
            ('南小姐', 124),
            ('丰岛22部2课', 126),
            ('FST秦（23区）', 129),
            ('Idiom-横山', 128),
            ('CP-牛田様（PH）', 129),
            ('STYLEM-稲垣', 127),
            ('渡边样', 123),
            ('MN-陆美玲', 128),
            ('梅内様', 155),
            ('八木样', 123),
            ('FST遠藤様', 155),
            ('松尾様', 155),
            ('西川様', 155),
            ('茉莉利-林', 127),
            ('上海日晶', 131),
            ('FST伊勢様(KEYUCA)', 129),
            ('FST伊势様', 124),
            ('FST伊勢様', 155),
            ('chapt.tar', 163),
            ('松尾样', 123),
            ('HIGLAND-永木', 127),
            ('double eye-板横', 127),
            ('brocade-锦织', 127),
            ('JUN直贸', 124),
            ('FST遠藤様', 124),
            ('MN-稻岡', 128),
            ('曹', 131),
            ('小川様', 132),
            ('今村样', 164),
            ('渡边样', 164),
            ('FST-秦', 127),
            ('FST国武様', 124),
            ('松尾样', 164),
            ('国武样', 164),
            ('FST-秦', 127),
            ('FST-松尾', 127),
            ('FST-市川', 127),
            ('FST-岛桥', 127),
            ('市川样', 164),
            ('家光様', 166),
            ('小川様', 166),
            ('木村样', 164),
            ('梅内様', 166),
            ('伊勢様', 166),
            ('国武様', 166),
            ('和田様', 166),
            ('安井様', 124),
            ('岛桥样', 123),
            ('新宗忆', 131),
            ('张様', 124),
            ('FST市川様', 124),
            ('FST市川様', 124),
            ('吉村样', 123),
            ('王艳艳', 123),
            ('今野样', 164),
            ('岛桥', 167),
            ('吉村样', 164),
            ('吉田様', 124),
            ('兰总', 112),
            ('八木样', 164),
            ('帝人様', 124),
            ('ZARA', 112),
            ('玉田样', 164),
            ('木村', 112),
            ('MN-森滨', 128),
            ('王样', 164),
            ('相原样', 164),
            ('大谷様', 132),
            ('岛桥样', 164),
            ('FST嶋橋様', 129),
            ('丸安', 112),
            ('市川', 167),
            ('chapt.tar', 167),
            ('林水晶', 131),
            ('国武', 167),
            ('MN  数纳样', 131),
            ('林水晶', 131),
            ('美孚样', 164),
            ('富帼', 112),
            ('依美嘉', 112),
            ('新友達-黄莉', 128),
            ('佐佐木', 167),
            ('黄芸', 164),
            ('前山样', 164),
            ('阿部', 113),
            ('美莎', 112),
            ('菅原様', 132),
            ('小椋様', 132),
            ('有吉样', 123),
            ('汪建红', 112),
            ('4PK', 131),
            ('4PK', 131),
            ('松尾', 167),
            ('LAEP-平野', 127),
            ('市川', 176),
            ('坂本样', 123),
            ('横井', 167),
            ('FST-国武', 128),
            ('松崎', 167),
            ('SORO青岛', 125),
            ('黄芸', 123),
            ('FST 国武', 113),
            ('佐佐木', 112),
            ('薮崎様', 132),
            ('毕然', 167)
        ]
        
        customer_update_count = 0
        customer_insert_count = 0
        
        for customer_name, sales_id in customer_list:
            cursor.execute("SELECT id FROM t_erp_customer WHERE customer_name = %s", (customer_name,))
            existing = cursor.fetchone()
            
            if existing:
                customer_id = existing[0]
                sql = "UPDATE t_erp_customer SET sales_id = %s WHERE id = %s"
                cursor.execute(sql, (sales_id, customer_id))
                if cursor.rowcount > 0:
                    print(f"  客户已更新: {customer_name} -> 业务员 {sales_id}")
                    customer_update_count += 1
            else:
                sql = """
                    INSERT INTO t_erp_customer 
                    (customer_name, sales_id, create_by, create_time)
                    VALUES (%s, %s, 'admin', NOW())
                """
                cursor.execute(sql, (customer_name, sales_id))
                print(f"  客户已插入: {customer_name} -> 业务员 {sales_id}")
                customer_insert_count += 1
        
        conn.commit()
        print(f"\n客户表更新完成: 更新 {customer_update_count} 条, 插入 {customer_insert_count} 条")
        
        # ========== 3. 验证结果 ==========
        print("\n【3】验证结果...")
        
        print("\n用户表样本:")
        cursor.execute("SELECT user_id, nick_name FROM sys_user WHERE user_id BETWEEN 120 AND 130 ORDER BY user_id")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]}")
        
        print("\n客户表样本:")
        cursor.execute("SELECT id, customer_name, sales_id FROM t_erp_customer WHERE sales_id IS NOT NULL ORDER BY id LIMIT 10")
        for row in cursor.fetchall():
            print(f"  {row[0]}: {row[1]} -> 业务员 {row[2]}")
        
        print("\n" + "="*100)
        print("所有更新完成！")
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
    update_users_and_customers()

