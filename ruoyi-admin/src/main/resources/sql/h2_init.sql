-- H2数据库初始化脚本
-- 部门表
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint          not null auto_increment    comment '部门id',
  parent_id         bigint          default 0                  comment '父部门id',
  ancestors         varchar(50)     default ''                 comment '祖级列表',
  dept_name         varchar(30)     default ''                 comment '部门名称',
  order_num         int             default 0                  comment '显示顺序',
  leader            varchar(20)     default null               comment '负责人',
  phone             varchar(11)     default null               comment '联系电话',
  email             varchar(50)     default null               comment '邮箱',
  status            char(1)         default '0'                comment '部门状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (dept_id)
);

-- 初始化部门表数据
insert into sys_dept values(100,  0,   '0',          '若依科技',   0, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', CURRENT_TIMESTAMP, '', null);
insert into sys_dept values(101,  100, '0,100',      '深圳总公司', 1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', CURRENT_TIMESTAMP, '', null);
insert into sys_dept values(103,  101, '0,100,101',  '研发部门',   1, '若依', '15888888888', 'ry@qq.com', '0', '0', 'admin', CURRENT_TIMESTAMP, '', null);

-- 用户信息表
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '用户ID',
  dept_id           bigint(20)      default null               comment '部门ID',
  user_name         varchar(30)     not null                   comment '用户账号',
  nick_name         varchar(30)     not null                   comment '用户昵称',
  user_type         varchar(2)      default '00'               comment '用户类型（00系统用户）',
  email             varchar(50)     default ''                 comment '用户邮箱',
  phonenumber       varchar(11)     default ''                 comment '手机号码',
  sex               char(1)         default '0'                comment '用户性别（0男 1女 2未知）',
  avatar            varchar(100)    default ''                 comment '头像地址',
  password          varchar(100)    default ''                 comment '密码',
  status            char(1)         default '0'                comment '账号状态（0正常 1停用）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  login_ip          varchar(128)    default ''                 comment '最后登录IP',
  login_date        datetime                                   comment '最后登录时间',
  pwd_update_date   datetime                                   comment '密码最后更新时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (user_id)
);

-- 初始化用户信息表数据
insert into sys_user values(1,  103, 'admin', '若依', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin', CURRENT_TIMESTAMP, '', null, '管理员');

-- 角色信息表
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '角色ID',
  role_name            varchar(30)     not null                   comment '角色名称',
  role_key             varchar(100)    not null                   comment '角色权限字符串',
  role_sort            int(4)          not null                   comment '显示顺序',
  data_scope           char(1)         default '1'                comment '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  menu_check_strictly  tinyint(1)      default 1                  comment '菜单树选择项是否关联显示',
  dept_check_strictly  tinyint(1)      default 1                  comment '部门树选择项是否关联显示',
  status               char(1)         not null                   comment '角色状态（0正常 1停用）',
  del_flag             char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by            varchar(64)     default ''                 comment '创建者',
  create_time          datetime                                   comment '创建时间',
  update_by            varchar(64)     default ''                 comment '更新者',
  update_time          datetime                                   comment '更新时间',
  remark               varchar(500)    default null               comment '备注',
  primary key (role_id)
);

-- 初始化角色信息表数据
insert into sys_role values('1', '超级管理员',  'admin',  1, 1, 1, 1, '0', '0', 'admin', CURRENT_TIMESTAMP, '', null, '超级管理员');

-- 菜单权限表
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  route_name        varchar(50)     default ''                 comment '路由名称',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
);

-- 初始化菜单信息表数据
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', CURRENT_TIMESTAMP, '', null, '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', CURRENT_TIMESTAMP, '', null, '系统监控目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', CURRENT_TIMESTAMP, '', null, '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', CURRENT_TIMESTAMP, '', null, '角色管理菜单');

-- 用户和角色关联表
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '用户ID',
  role_id   bigint(20) not null comment '角色ID',
  primary key(user_id, role_id)
);

-- 初始化用户和角色关联表数据
insert into sys_user_role values ('1', '1');

-- 角色和菜单关联表
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
);

-- 初始化角色和菜单关联表数据
insert into sys_role_menu values ('1', '1');
insert into sys_role_menu values ('1', '2');
insert into sys_role_menu values ('1', '100');
insert into sys_role_menu values ('1', '101');

-- 字典类型表
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '字典主键',
  dict_name        varchar(100)    default ''                 comment '字典名称',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_id),
  unique (dict_type)
);

-- 初始化字典类型表数据
insert into sys_dict_type values(1,  '用户性别', 'sys_user_sex',        '0', 'admin', CURRENT_TIMESTAMP, '', null, '用户性别列表');

-- 字典数据表
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '字典编码',
  dict_sort        int(4)          default 0                  comment '字典排序',
  dict_label       varchar(100)    default ''                 comment '字典标签',
  dict_value       varchar(100)    default ''                 comment '字典键值',
  dict_type        varchar(100)    default ''                 comment '字典类型',
  css_class        varchar(100)    default null               comment '样式属性（其他样式扩展）',
  list_class       varchar(100)    default null               comment '表格回显样式',
  is_default       char(1)         default 'N'                comment '是否默认（Y是 N否）',
  status           char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by        varchar(64)     default ''                 comment '创建者',
  create_time      datetime                                   comment '创建时间',
  update_by        varchar(64)     default ''                 comment '更新者',
  update_time      datetime                                   comment '更新时间',
  remark           varchar(500)    default null               comment '备注',
  primary key (dict_code)
);

-- 初始化字典数据表数据
insert into sys_dict_data values(1,  1,  '男',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', CURRENT_TIMESTAMP, '', null, '性别男');
insert into sys_dict_data values(2,  2,  '女',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', CURRENT_TIMESTAMP, '', null, '性别女');
insert into sys_dict_data values(3,  3,  '未知',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', CURRENT_TIMESTAMP, '', null, '性别未知');

-- 参数配置表
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '参数主键',
  config_name       varchar(100)    default ''                 comment '参数名称',
  config_key        varchar(100)    default ''                 comment '参数键名',
  config_value      varchar(500)    default ''                 comment '参数键值',
  config_type       char(1)         default 'N'                comment '系统内置（Y是 N否）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
);

-- 初始化参数配置表数据
insert into sys_config values(1, '主框架页-默认皮肤样式名称',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', CURRENT_TIMESTAMP, '', null, '蓝色 skin-blue、绿色 skin-green、紫色 skin-purple、红色 skin-red、黄色 skin-yellow' );
insert into sys_config values(2, '用户管理-账号初始密码',         'sys.user.initPassword',            '123456',        'Y', 'admin', CURRENT_TIMESTAMP, '', null, '初始化密码 123456' );
insert into sys_config values(3, '主框架页-侧边栏主题',           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', CURRENT_TIMESTAMP, '', null, '深色主题theme-dark，浅色主题theme-light' );
insert into sys_config values(4, '账号自助-验证码开关',           'sys.account.captchaEnabled',       'false',          'Y', 'admin', CURRENT_TIMESTAMP, '', null, '是否开启验证码功能（true开启，false关闭）');

-- 自动化任务表
drop table if exists sys_automation;
create table sys_automation (
  task_id           bigint(20)      not null auto_increment    comment '任务ID',
  task_name         varchar(100)    not null                   comment '任务名称',
  task_type         varchar(50)     not null                   comment '任务类型',
  cron_expression   varchar(255)    default ''                 comment '执行周期（Cron表达式）',
  description       varchar(500)    default ''                 comment '任务描述',
  status            varchar(20)     default 'running'          comment '任务状态',
  execute_time      varchar(30)     default ''                 comment '执行时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (task_id)
);

-- 初始化自动化任务表数据
insert into sys_automation values(1, '数据备份任务', 'backup', '0 0 0 * * ?', '每天凌晨执行数据备份', 'running', '', 'admin', CURRENT_TIMESTAMP, '', null);
insert into sys_automation values(2, '系统清理任务', 'clean', '0 0 1 * * ?', '每天凌晨1点执行系统清理', 'paused', '', 'admin', CURRENT_TIMESTAMP, '', null);

-- 系统工具菜单
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '菜单ID',
  menu_name         varchar(50)     not null                   comment '菜单名称',
  parent_id         bigint(20)      default 0                  comment '父菜单ID',
  order_num         int(4)          default 0                  comment '显示顺序',
  path              varchar(200)    default ''                 comment '路由地址',
  component         varchar(255)    default null               comment '组件路径',
  query             varchar(255)    default null               comment '路由参数',
  route_name        varchar(50)     default ''                 comment '路由名称',
  is_frame          int(1)          default 1                  comment '是否为外链（0是 1否）',
  is_cache          int(1)          default 0                  comment '是否缓存（0缓存 1不缓存）',
  menu_type         char(1)         default ''                 comment '菜单类型（M目录 C菜单 F按钮）',
  visible           char(1)         default 0                  comment '菜单状态（0显示 1隐藏）',
  status            char(1)         default 0                  comment '菜单状态（0正常 1停用）',
  perms             varchar(100)    default null               comment '权限标识',
  icon              varchar(100)    default '#'                comment '菜单图标',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default ''                 comment '备注',
  primary key (menu_id)
);

-- 初始化菜单信息表数据
-- 一级菜单
insert into sys_menu values('1', '系统管理', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', CURRENT_TIMESTAMP, '', null, '系统管理目录');
insert into sys_menu values('2', '系统监控', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', CURRENT_TIMESTAMP, '', null, '系统监控目录');
insert into sys_menu values('3', '系统工具', '0', '3', 'tool',             null, '', '', 1, 0, 'M', '0', '0', '', 'tool',     'admin', CURRENT_TIMESTAMP, '', null, '系统工具目录');
-- 二级菜单
insert into sys_menu values('100',  '用户管理', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', CURRENT_TIMESTAMP, '', null, '用户管理菜单');
insert into sys_menu values('101',  '角色管理', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', CURRENT_TIMESTAMP, '', null, '角色管理菜单');
insert into sys_menu values('118',  '自动化任务', '3',   '4', 'automation', 'erp/automation/index',     '', '', 1, 0, 'C', '0', '0', 'erp:automation:list',     'automatic',     'admin', CURRENT_TIMESTAMP, '', null, '自动化任务管理菜单');
-- 自动化任务按钮
insert into sys_menu values('1061', '任务查询', '118', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:query',       '#', 'admin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values('1062', '任务新增', '118', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:add',         '#', 'admin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values('1063', '任务修改', '118', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:edit',        '#', 'admin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values('1064', '任务删除', '118', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:remove',      '#', 'admin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values('1065', '任务运行', '118', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:run',         '#', 'admin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values('1066', '任务暂停', '118', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:pause',       '#', 'admin', CURRENT_TIMESTAMP, '', null, '');
insert into sys_menu values('1067', '任务导出', '118', '7', '#', '', '', '', 1, 0, 'F', '0', '0', 'erp:automation:export',      '#', 'admin', CURRENT_TIMESTAMP, '', null, '');

-- 角色和菜单关联表
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '角色ID',
  menu_id   bigint(20) not null comment '菜单ID',
  primary key(role_id, menu_id)
);

-- 初始化角色和菜单关联表数据
insert into sys_role_menu values ('1', '1');
insert into sys_role_menu values ('1', '2');
insert into sys_role_menu values ('1', '3');
insert into sys_role_menu values ('1', '100');
insert into sys_role_menu values ('1', '101');
insert into sys_role_menu values ('1', '118');
insert into sys_role_menu values ('1', '1061');
insert into sys_role_menu values ('1', '1062');
insert into sys_role_menu values ('1', '1063');
insert into sys_role_menu values ('1', '1064');
insert into sys_role_menu values ('1', '1065');
insert into sys_role_menu values ('1', '1066');
insert into sys_role_menu values ('1', '1067');
