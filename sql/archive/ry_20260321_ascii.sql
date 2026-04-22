-- ----------------------------
-- 1??????
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment '???id',
  parent_id         bigint(20)      default 0                  comment '?????d',
  ancestors         varchar(50)     default ''                 comment '??????',
  dept_name         varchar(30)     default ''                 comment '??????',
  order_num         int(4)          default 0                  comment '??????',
  leader            varchar(20)     default null               comment '?????,
  phone             varchar(11)     default null               comment '??????',
  email             varchar(50)     default null               comment '???',
  status            char(1)         default '0'                comment '????????0??? 1?????,
  del_flag          char(1)         default '0'                comment '?????????????? 2????????,
  create_by         varchar(64)     default ''                 comment '?????,
  create_time 	    datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = '?????;

-- ----------------------------
-- ?????????????-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '??????',   0, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      '????????, 1, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      '????????, 2, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '??????',   1, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '??????',   2, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  '??????',   3, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  '??????',   4, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  '??????',   5, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '??????',   1, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  '??????',   2, '???', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2?????????
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '???ID',
  dept_id           bigint(20)      default null               comment '???ID',
  user_name         varchar(30)     not null                   comment '??????',
  nick_name         varchar(30)     not null                   comment '??????',
  user_type         varchar(2)      default '00'               comment '????????0????????,
  email             varchar(50)     default ''                 comment '??????',
  phonenumber       varchar(11)     default ''                 comment '??????',
  sex               char(1)         default '0'                comment '??????????1??2?????,
  avatar            varchar(100)    default ''                 comment '??????',
  password          varchar(100)    default ''                 comment '???',
  status            char(1)         default '0'                comment '????????0??? 1?????,
  del_flag          char(1)         default '0'                comment '?????????????? 2????????,
  login_ip          varchar(128)    default ''                 comment '???????P',
  login_date        datetime                                   comment '??????????,
  pwd_update_date   datetime                                   comment '?????????????,
  create_by         varchar(64)     default ''                 comment '?????,
  create_time       datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  remark            varchar(500)    default null               comment '???',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '????????;

-- ----------------------------
-- ????????????????-- ----------------------------
insert into sys_user values(1,  103, 'admin', '???', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '?????);
insert into sys_user values(2,  105, 'ry',    '???', '00', 'ry@qq.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '?????);


-- ----------------------------
-- 3?????????
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '???ID',
  post_code     varchar(64)     not null                   comment '??????',
  post_name     varchar(50)     not null                   comment '??????',
  post_sort     int(4)          not null                   comment '??????',
  status        char(1)         not null                   comment '?????0??? 1?????,
  create_by     varchar(64)     default ''                 comment '?????,
  create_time   datetime                                   comment '??????',
  update_by     varchar(64)     default ''			       comment '?????,
  update_time   datetime                                   comment '??????',
  remark        varchar(500)    default null               comment '???',
  primary key (post_id)
) engine=innodb comment = '????????;

-- ----------------------------
-- ????????????????-- ----------------------------
insert into sys_post values(1, 'ceo',  '?????,    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   '??????',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   '??????',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', '???????,  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4?????????
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment '???ID',
  role_name            varchar(30)     not null                   comment '??????',
  role_key             varchar(100)    not null                   comment '???????????,
  role_sort            int(4)          not null                   comment '??????',
  data_scope           char(1)         default '1'                comment '???????????????????2???????????3???????????? 4??????????????????',
  menu_check_strictly  tinyint(1)      default 1                  comment '???????????????????,
  dept_check_strictly  tinyint(1)      default 1                  comment '???????????????????,
  status               char(1)         not null                   comment '????????0??? 1?????,
  del_flag             char(1)         default '0'                comment '?????????????? 2????????,
  create_by            varchar(64)     default ''                 comment '?????,
  create_time          datetime                                   comment '??????',
  update_by            varchar(64)     default ''                 comment '?????,
  update_time          datetime                                   comment '??????',
  remark               varchar(500)    default null               comment '???',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = '????????;

-- ----------------------------
-- ????????????????-- ----------------------------
insert into sys_role values('1', '????????,  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, '????????);
insert into sys_role values('2', '???????,    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, '???????);


-- ----------------------------
-- 5?????????
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment '???ID',
  menu_name         varchar(50)     not null                   comment '??????',
  parent_id         bigint(20)      default 0                  comment '?????D',
  order_num         int(4)          default 0                  comment '??????',
  path              varchar(200)    default ''                 comment '??????',
  component         varchar(255)    default null               comment '??????',
  query             varchar(255)    default null               comment '??????',
  route_name        varchar(50)     default ''                 comment '??????',
  is_frame          int(1)          default 1                  comment '?????????0??1???',
  is_cache          int(1)          default 0                  comment '??????????? 1??????',
  menu_type         char(1)         default ''                 comment '??????????? C??? F?????,
  visible           char(1)         default 0                  comment '????????0??? 1?????,
  status            char(1)         default 0                  comment '????????0??? 1?????,
  perms             varchar(100)    default null               comment '??????',
  icon              varchar(100)    default '#'                comment '??????',
  create_by         varchar(64)     default ''                 comment '?????,
  create_time       datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  remark            varchar(500)    default ''                 comment '???',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = '????????;

-- ----------------------------
-- ????????????????-- ----------------------------
-- ???????insert into sys_menu values('1', '??????', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('2', '??????', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('3', '??????', '0', '3', 'tool',             null, '', '', 1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('4', '??????', '0', '4', 'http://ruoyi.vip', null, '', '', 0, 0, 'M', '0', '0', '', 'guide',    'admin', sysdate(), '', null, '?????????');
-- ??????
insert into sys_menu values('100',  '??????', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('101',  '??????', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('102',  '??????', '1',   '3', 'menu',       'system/menu/index',        '', '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('103',  '??????', '1',   '4', 'dept',       'system/dept/index',        '', '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('104',  '??????', '1',   '5', 'post',       'system/post/index',        '', '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('105',  '??????', '1',   '6', 'dict',       'system/dict/index',        '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('106',  '??????', '1',   '7', 'config',     'system/config/index',      '', '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('107',  '??????', '1',   '8', 'notice',     'system/notice/index',      '', '', 1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('108',  '??????', '1',   '9', 'log',        '',                         '', '', 1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('109',  '??????', '2',   '1', 'online',     'monitor/online/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('110',  '??????', '2',   '2', 'job',        'monitor/job/index',        '', '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('111',  '??????', '2',   '3', 'druid',      'monitor/druid/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('112',  '??????', '2',   '4', 'server',     'monitor/server/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('113',  '??????', '2',   '5', 'cache',      'monitor/cache/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('114',  '??????', '2',   '6', 'cacheList',  'monitor/cache/list',       '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('115',  '??????', '3',   '1', 'build',      'tool/build/index',         '', '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('116',  '??????', '3',   '2', 'gen',        'tool/gen/index',           '', '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('117',  '??????', '3',   '3', 'swagger',    'tool/swagger/index',       '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, '?????????');
-- ??????
insert into sys_menu values('500',  '??????', '108', '1', 'operlog',    'monitor/operlog/index',    '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, '?????????');
insert into sys_menu values('501',  '??????', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, '?????????');
-- ?????????
insert into sys_menu values('1000', '??????', '100', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', '??????', '100', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '??????', '100', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '??????', '100', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '??????', '100', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '??????', '100', '6',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', '??????', '100', '7',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1007', '??????', '101', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', '??????', '101', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', '??????', '101', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', '??????', '101', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', '??????', '101', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1012', '??????', '102', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', '??????', '102', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', '??????', '102', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', '??????', '102', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1016', '??????', '103', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', '??????', '103', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', '??????', '103', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', '??????', '103', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1020', '??????', '104', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', '??????', '104', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '??????', '104', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '??????', '104', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '??????', '104', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1025', '??????', '105', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', '??????', '105', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', '??????', '105', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', '??????', '105', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', '??????', '105', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1030', '??????', '106', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', '??????', '106', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', '??????', '106', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', '??????', '106', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', '??????', '106', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1035', '??????', '107', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', '??????', '107', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', '??????', '107', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', '??????', '107', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1039', '??????', '500', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', '??????', '500', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '??????', '500', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1042', '??????', '501', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1043', '??????', '501', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', '??????', '501', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', '??????', '501', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock',  '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1046', '??????', '109', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', '???????', '109', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', '???????', '109', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1049', '??????', '110', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', '??????', '110', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', '??????', '110', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', '??????', '110', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', '???????, '110', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', '??????', '110', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');
-- ?????????
insert into sys_menu values('1055', '??????', '116', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', '??????', '116', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1057', '??????', '116', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', '??????', '116', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', '??????', '116', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', '??????', '116', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6?????????????? ???N-1???
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '???ID',
  role_id   bigint(20) not null comment '???ID',
  primary key(user_id, role_id)
) engine=innodb comment = '????????????';

-- ----------------------------
-- ????????????????????
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7?????????????? ???1-N???
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment '???ID',
  menu_id   bigint(20) not null comment '???ID',
  primary key(role_id, menu_id)
) engine=innodb comment = '????????????';

-- ----------------------------
-- ????????????????????
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '117');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');

-- ----------------------------
-- 8?????????????? ???1-N???
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment '???ID',
  dept_id   bigint(20) not null comment '???ID',
  primary key(role_id, dept_id)
) engine=innodb comment = '????????????';

-- ----------------------------
-- ????????????????????
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9?????????????? ???1-N???
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '???ID',
  post_id   bigint(20) not null comment '???ID',
  primary key (user_id, post_id)
) engine=innodb comment = '????????????';

-- ----------------------------
-- ????????????????????
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10???????????-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '??????',
  title             varchar(50)     default ''                 comment '??????',
  business_type     int(2)          default 0                  comment '??????????? 1??? 2??? 3?????,
  method            varchar(200)    default ''                 comment '??????',
  request_method    varchar(10)     default ''                 comment '??????',
  operator_type     int(1)          default 0                  comment '??????????? 1?????? 2?????????',
  oper_name         varchar(50)     default ''                 comment '??????',
  dept_name         varchar(50)     default ''                 comment '??????',
  oper_url          varchar(255)    default ''                 comment '???URL',
  oper_ip           varchar(128)    default ''                 comment '??????',
  oper_location     varchar(255)    default ''                 comment '??????',
  oper_param        varchar(2000)   default ''                 comment '??????',
  json_result       varchar(2000)   default ''                 comment '??????',
  status            int(1)          default 0                  comment '????????0??? 1?????,
  error_msg         varchar(2000)   default ''                 comment '??????',
  oper_time         datetime                                   comment '??????',
  cost_time         bigint(20)      default 0                  comment '???????,
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = '?????????';


-- ----------------------------
-- 11?????????
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment '??????',
  dict_name        varchar(100)    default ''                 comment '??????',
  dict_type        varchar(100)    default ''                 comment '??????',
  status           char(1)         default '0'                comment '?????0??? 1?????,
  create_by        varchar(64)     default ''                 comment '?????,
  create_time      datetime                                   comment '??????',
  update_by        varchar(64)     default ''                 comment '?????,
  update_time      datetime                                   comment '??????',
  remark           varchar(500)    default null               comment '???',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = '????????;

insert into sys_dict_type values(1,  '??????', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, '?????????');
insert into sys_dict_type values(2,  '???????, 'sys_show_hide',       '0', 'admin', sysdate(), '', null, '??????????);
insert into sys_dict_type values(3,  '???????, 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, '??????????);
insert into sys_dict_type values(4,  '???????, 'sys_job_status',      '0', 'admin', sysdate(), '', null, '??????????);
insert into sys_dict_type values(5,  '??????', 'sys_job_group',       '0', 'admin', sysdate(), '', null, '?????????');
insert into sys_dict_type values(6,  '??????', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, '?????????');
insert into sys_dict_type values(7,  '??????', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '?????????');
insert into sys_dict_type values(8,  '???????, 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '??????????);
insert into sys_dict_type values(9,  '??????', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, '?????????');
insert into sys_dict_type values(10, '???????, 'sys_common_status',   '0', 'admin', sysdate(), '', null, '??????????);


-- ----------------------------
-- 12?????????
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment '??????',
  dict_sort        int(4)          default 0                  comment '??????',
  dict_label       varchar(100)    default ''                 comment '??????',
  dict_value       varchar(100)    default ''                 comment '???????,
  dict_type        varchar(100)    default ''                 comment '??????',
  css_class        varchar(100)    default null               comment '???????????????????,
  list_class       varchar(100)    default null               comment '?????????',
  is_default       char(1)         default 'N'                comment '??????????N???',
  status           char(1)         default '0'                comment '?????0??? 1?????,
  create_by        varchar(64)     default ''                 comment '?????,
  create_time      datetime                                   comment '??????',
  update_by        varchar(64)     default ''                 comment '?????,
  update_time      datetime                                   comment '??????',
  remark           varchar(500)    default null               comment '???',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = '????????;

insert into sys_dict_data values(1,  1,  '??,       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, '?????);
insert into sys_dict_data values(2,  2,  '??,       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '?????);
insert into sys_dict_data values(3,  3,  '???',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(4,  1,  '???',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(5,  2,  '???',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(6,  1,  '???',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(7,  2,  '???',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(8,  1,  '???',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(9,  2,  '???',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(10, 1,  '???',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(11, 2,  '???',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(12, 1,  '??,       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '????????);
insert into sys_dict_data values(13, 2,  '??,       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '????????);
insert into sys_dict_data values(14, 1,  '???',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '???');
insert into sys_dict_data values(15, 2,  '???',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, '???');
insert into sys_dict_data values(16, 1,  '???',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(17, 2,  '???',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(18, 99, '???',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(19, 1,  '???',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(20, 2,  '???',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(21, 3,  '???',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(22, 4,  '???',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(23, 5,  '???',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(24, 6,  '???',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(25, 7,  '????',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '???????');
insert into sys_dict_data values(26, 8,  '??????', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(27, 9,  '??????', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '??????');
insert into sys_dict_data values(28, 1,  '???',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, '???????);
insert into sys_dict_data values(29, 2,  '???',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '???????);


-- ----------------------------
-- 13?????????
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment '??????',
  config_name       varchar(100)    default ''                 comment '??????',
  config_key        varchar(100)    default ''                 comment '??????',
  config_value      varchar(500)    default ''                 comment '???????,
  config_type       char(1)         default 'N'                comment '??????????N???',
  create_by         varchar(64)     default ''                 comment '?????,
  create_time       datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  remark            varchar(500)    default null               comment '???',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '????????;

insert into sys_config values(1, '??????-????????????',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', sysdate(), '', null, '??? skin-blue?????skin-green?????skin-purple?????skin-red?????skin-yellow' );
insert into sys_config values(2, '??????-?????????',         'sys.user.initPassword',            '123456',        'Y', 'admin', sysdate(), '', null, '????????123456' );
insert into sys_config values(3, '??????-????????,           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', sysdate(), '', null, '??????theme-dark????????heme-light' );
insert into sys_config values(4, '??????-????????,           'sys.account.captchaEnabled',       'true',          'Y', 'admin', sysdate(), '', null, '????????????????rue?????false?????);
insert into sys_config values(5, '??????-????????????????, 'sys.account.registerUser',         'false',         'Y', 'admin', sysdate(), '', null, '?????????????????true?????false?????);
insert into sys_config values(6, '??????-????????,           'sys.login.blackIPList',            '',              'Y', 'admin', sysdate(), '', null, '??????IP??????????????????;????????????*?????????');
insert into sys_config values(7, '??????-????????????',     'sys.account.initPasswordModify',   '1',             'Y', 'admin', sysdate(), '', null, '0?????????????????????????????????????????????????????????????????????????????');
insert into sys_config values(8, '??????-????????????',     'sys.account.passwordValidateDays', '0',             'Y', 'admin', sysdate(), '', null, '?????????????????????????????0??????????????????0???365??????????????????????????????????????????????????????');


-- ----------------------------
-- 14???????????-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment '???ID',
  user_name      varchar(50)    default ''                comment '??????',
  ipaddr         varchar(128)   default ''                comment '???IP???',
  login_location varchar(255)   default ''                comment '??????',
  browser        varchar(50)    default ''                comment '????????,
  os             varchar(50)    default ''                comment '??????',
  status         char(1)        default '0'               comment '????????0??? 1?????,
  msg            varchar(255)   default ''                comment '??????',
  login_time     datetime                                 comment '??????',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = '?????????';


-- ----------------------------
-- 15????????????
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '???ID',
  job_name            varchar(64)   default ''                 comment '??????',
  job_group           varchar(64)   default 'DEFAULT'          comment '??????',
  invoke_target       varchar(500)  not null                   comment '???????????,
  cron_expression     varchar(255)  default ''                 comment 'cron????????,
  misfire_policy      varchar(20)   default '3'                comment '???????????????????? 2???????3????????,
  concurrent          char(1)       default '1'                comment '?????????????? 1?????,
  status              char(1)       default '0'                comment '?????0??? 1?????,
  create_by           varchar(64)   default ''                 comment '?????,
  create_time         datetime                                 comment '??????',
  update_by           varchar(64)   default ''                 comment '?????,
  update_time         datetime                                 comment '??????',
  remark              varchar(500)  default ''                 comment '??????',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '???????????;

insert into sys_job values(1, '????????????', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, '????????????', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, '????????????', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 16???????????????
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '??????ID',
  job_name            varchar(64)    not null                   comment '??????',
  job_group           varchar(64)    not null                   comment '??????',
  invoke_target       varchar(500)   not null                   comment '???????????,
  job_message         varchar(500)                              comment '??????',
  status              char(1)        default '0'                comment '????????0??? 1?????,
  exception_info      varchar(2000)  default ''                 comment '??????',
  start_time          datetime                                  comment '??????????,
  end_time            datetime                                  comment '?????????',
  create_time         datetime                                  comment '??????',
  primary key (job_log_id)
) engine=innodb comment = '??????????????;


-- ----------------------------
-- 17??????????-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment '???ID',
  notice_title      varchar(50)     not null                   comment '??????',
  notice_type       char(1)         not null                   comment '??????????? 2?????,
  notice_content    longblob        default null               comment '??????',
  status            char(1)         default '0'                comment '????????0??? 1?????,
  create_by         varchar(64)     default ''                 comment '?????,
  create_time       datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  remark            varchar(255)    default null               comment '???',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '????????;

-- ----------------------------
-- ????????????????-- ----------------------------
insert into sys_notice values('1', '????????018-07-01 ????????????', '2', '????????, '0', 'admin', sysdate(), '', null, '?????);
insert into sys_notice values('2', '????????018-07-01 ????????????', '1', '??????',   '0', 'admin', sysdate(), '', null, '?????);
insert into sys_notice values('3', '?????????????, '1', '<p><span style=\"color: rgb(230, 0, 0);\">??????</span></p><p><font color=\"#333333\">RuoYi?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????/font><span style=\"color: rgb(51, 51, 51);\">??????</span><span style=\"color: rgb(51, 51, 51);\">????????/span><span style=\"color: rgb(51, 51, 51);\">??/span><span style=\"color: rgb(51, 51, 51);\">???????????????????????????????????????????????????????????????????edis?????ocker???????????????????????????????????/span><font color=\"#333333\">???????????/font><span style=\"color: rgb(51, 51, 51);\">?????????????????????/span></p><p><img src=\"https://foruda.gitee.com/images/1773931848342439032/a4d22313_1815095.png\" style=\"width: 64px;\"><br></p><p><span style=\"color: rgb(230, 0, 0);\">????????/span></p><p><span style=\"color: rgb(51, 51, 51);\">???????????nbsp;</span><a href=\"http://ruoyi.vip\" target=\"_blank\">http://ruoyi.vip</a><a href=\"http://ruoyi.vip\" target=\"_blank\"></a></p><p><span style=\"color: rgb(51, 51, 51);\">???????????nbsp;</span><a href=\"http://doc.ruoyi.vip\" target=\"_blank\">http://doc.ruoyi.vip</a><br></p><p><span style=\"color: rgb(51, 51, 51);\">?????????????????&nbsp;</span><a href=\"http://demo.ruoyi.vip\" target=\"_blank\">http://demo.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">?????????????????&nbsp;</span><a href=\"http://vue.ruoyi.vip\" target=\"_blank\">http://vue.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">?????????????????&nbsp;</span><a href=\"http://cloud.ruoyi.vip\" target=\"_blank\">http://cloud.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">?????????????????&nbsp;</span><a href=\"http://h5.ruoyi.vip\" target=\"_blank\">http://h5.ruoyi.vip</a></p><p><br style=\"color: rgb(48, 49, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 12px;\"></p>', '0', 'admin', sysdate(), '', null, '?????);


-- ----------------------------
-- 18????????????
-- ----------------------------
drop table if exists sys_notice_read;
create table sys_notice_read (
  read_id          bigint(20)       not null auto_increment    comment '??????',
  notice_id        int(4)           not null                   comment '???id',
  user_id          bigint(20)       not null                   comment '???id',
  read_time        datetime         not null                   comment '??????',
  primary key (read_id),
  unique key uk_user_notice (user_id, notice_id)   comment '????????????????????
) engine=innodb auto_increment=1 comment='???????????;


-- ----------------------------
-- 19????????????
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment '???',
  table_name        varchar(200)    default ''                 comment '?????,
  table_comment     varchar(500)    default ''                 comment '?????,
  sub_table_name    varchar(64)     default null               comment '???????????,
  sub_table_fk_name varchar(64)     default null               comment '????????????',
  class_name        varchar(100)    default ''                 comment '????????,
  tpl_category      varchar(200)    default 'crud'             comment '?????????crud?????? tree????????,
  tpl_web_type      varchar(30)     default ''                 comment '???????????lement-ui??? element-plus?????,
  package_name      varchar(100)                               comment '????????,
  module_name       varchar(30)                                comment '????????,
  business_name     varchar(30)                                comment '????????,
  function_name     varchar(50)                                comment '????????,
  function_author   varchar(50)                                comment '??????????,
  form_col_num      int(1)          default 1                  comment '?????????????? ?????,
  gen_type          char(1)         default '0'                comment '???????????zip?????1?????????',
  gen_path          varchar(200)    default '/'                comment '?????????????????????',
  options           varchar(1000)                              comment '?????????',
  create_by         varchar(64)     default ''                 comment '?????,
  create_time 	    datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  remark            varchar(500)    default null               comment '???',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = '???????????;


-- ----------------------------
-- 20???????????????
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment '???',
  table_id          bigint(20)                                 comment '????????,
  column_name       varchar(200)                               comment '?????,
  column_comment    varchar(500)                               comment '?????,
  column_type       varchar(100)                               comment '?????,
  java_type         varchar(500)                               comment 'JAVA???',
  java_field        varchar(200)                               comment 'JAVA?????,
  is_pk             char(1)                                    comment '???????????',
  is_increment      char(1)                                    comment '???????????',
  is_required       char(1)                                    comment '???????????',
  is_insert         char(1)                                    comment '????????????1???',
  is_edit           char(1)                                    comment '??????????????',
  is_list           char(1)                                    comment '??????????????',
  is_query          char(1)                                    comment '??????????????',
  query_type        varchar(200)    default 'EQ'               comment '?????????????????????????????????',
  html_type         varchar(200)                               comment '?????????????????????????????????????????????',
  dict_type         varchar(200)    default ''                 comment '??????',
  sort              int                                        comment '???',
  create_by         varchar(64)     default ''                 comment '?????,
  create_time 	    datetime                                   comment '??????',
  update_by         varchar(64)     default ''                 comment '?????,
  update_time       datetime                                   comment '??????',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = '??????????????;
