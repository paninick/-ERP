-- 修复菜单名称显示问题
USE `ry-vue`;

-- 一级菜单
UPDATE sys_menu SET menu_name = '系统管理' WHERE menu_id = 1;
UPDATE sys_menu SET menu_name = '系统监控' WHERE menu_id = 2;
UPDATE sys_menu SET menu_name = '系统工具' WHERE menu_id = 3;
UPDATE sys_menu SET menu_name = '若依官网' WHERE menu_id = 4;

-- 二级菜单 - 系统管理
UPDATE sys_menu SET menu_name = '用户管理' WHERE menu_id = 100;
UPDATE sys_menu SET menu_name = '角色管理' WHERE menu_id = 101;
UPDATE sys_menu SET menu_name = '菜单管理' WHERE menu_id = 102;
UPDATE sys_menu SET menu_name = '部门管理' WHERE menu_id = 103;
UPDATE sys_menu SET menu_name = '岗位管理' WHERE menu_id = 104;
UPDATE sys_menu SET menu_name = '字典管理' WHERE menu_id = 105;
UPDATE sys_menu SET menu_name = '参数设置' WHERE menu_id = 106;
UPDATE sys_menu SET menu_name = '通知公告' WHERE menu_id = 107;
UPDATE sys_menu SET menu_name = '日志管理' WHERE menu_id = 108;

-- 二级菜单 - 系统监控
UPDATE sys_menu SET menu_name = '在线用户' WHERE menu_id = 109;
UPDATE sys_menu SET menu_name = '定时任务' WHERE menu_id = 110;
UPDATE sys_menu SET menu_name = '数据监控' WHERE menu_id = 111;
UPDATE sys_menu SET menu_name = '服务监控' WHERE menu_id = 112;
UPDATE sys_menu SET menu_name = '缓存监控' WHERE menu_id = 113;
UPDATE sys_menu SET menu_name = '缓存列表' WHERE menu_id = 114;

-- 二级菜单 - 系统工具
UPDATE sys_menu SET menu_name = '表单构建' WHERE menu_id = 115;
UPDATE sys_menu SET menu_name = '代码生成' WHERE menu_id = 116;
UPDATE sys_menu SET menu_name = '系统接口' WHERE menu_id = 117;

-- 三级菜单 - 日志管理
UPDATE sys_menu SET menu_name = '操作日志' WHERE menu_id = 500;
UPDATE sys_menu SET menu_name = '登录日志' WHERE menu_id = 501;

-- 更新部门数据
UPDATE sys_dept SET dept_name = '若依科技' WHERE dept_id = 100;
UPDATE sys_dept SET dept_name = '深圳总公司' WHERE dept_id = 101;
UPDATE sys_dept SET dept_name = '长沙分公司' WHERE dept_id = 102;
UPDATE sys_dept SET dept_name = '研发部门' WHERE dept_id = 103;
UPDATE sys_dept SET dept_name = '市场部门' WHERE dept_id = 104;
UPDATE sys_dept SET dept_name = '测试部门' WHERE dept_id = 105;
UPDATE sys_dept SET dept_name = '财务部门' WHERE dept_id = 106;
UPDATE sys_dept SET dept_name = '运维部门' WHERE dept_id = 107;

-- 更新岗位数据
UPDATE sys_post SET post_name = '董事长' WHERE post_id = 1;
UPDATE sys_post SET post_name = '项目经理' WHERE post_id = 2;
UPDATE sys_post SET post_name = '人力资源' WHERE post_id = 3;
UPDATE sys_post SET post_name = '普通员工' WHERE post_id = 4;

-- 更新角色数据
UPDATE sys_role SET role_name = '超级管理员' WHERE role_id = 1;
UPDATE sys_role SET role_name = '普通角色' WHERE role_id = 2;

-- 更新用户数据
UPDATE sys_user SET nick_name = '若依' WHERE user_id = 1;
UPDATE sys_user SET nick_name = '若依' WHERE user_id = 2;

-- 更新字典数据
UPDATE sys_dict_type SET dict_name = '用户性别' WHERE dict_id = 1;
UPDATE sys_dict_type SET dict_name = '系统状态' WHERE dict_id = 2;
UPDATE sys_dict_type SET dict_name = '正常停用' WHERE dict_id = 3;
UPDATE sys_dict_type SET dict_name = '任务状态' WHERE dict_id = 4;
UPDATE sys_dict_type SET dict_name = '用户分组' WHERE dict_id = 5;
UPDATE sys_dict_type SET dict_name = '是否' WHERE dict_id = 6;
UPDATE sys_dict_type SET dict_name = '通知类型' WHERE dict_id = 7;
UPDATE sys_dict_type SET dict_name = '通知状态' WHERE dict_id = 8;
UPDATE sys_dict_type SET dict_name = '操作类型' WHERE dict_id = 9;
UPDATE sys_dict_type SET dict_name = '通用状态' WHERE dict_id = 10;

-- 更新配置数据
UPDATE sys_config SET config_name = '主框架页-默认皮肤样式名称' WHERE config_id = 1;
UPDATE sys_config SET config_name = '用户管理-账号初始密码' WHERE config_id = 2;
UPDATE sys_config SET config_name = '主框架页-侧边栏主题' WHERE config_id = 3;
UPDATE sys_config SET config_name = '账号自助-验证码开关' WHERE config_id = 4;
UPDATE sys_config SET config_name = '账号自助-是否开启用户注册功能' WHERE config_id = 5;
