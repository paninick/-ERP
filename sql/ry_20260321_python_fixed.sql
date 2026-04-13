-- ----------------------------
-- 1O4Q(10
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment ')1hid',
  parent_id         bigint(20)      default 0                  comment 'X4Qe%d',
  ancestors         varchar(50)     default ''                 comment '~+hRDi0',
  dept_name         varchar(30)     default ''                 comment ')1hZ]',
  order_num         int(4)          default 0                  comment 'gRZ0$iT-|',
  leader            varchar(20)     default null               comment 'tqw\m?,
  phone             varchar(11)     default null               comment 'qe"CO=v',
  email             varchar(50)     default null               comment '',
  status            char(1)         default '0'                comment ')1h5 O}0YE6r 1Knde?,
  del_flag          char(1)         default '0'                comment 'RrjVT~?`mH0p:jjn 2`mH0Rrj?,
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time     datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = ')1ht?;

-- ----------------------------
-- RoPV?)1ht&1f?-- ----------------------------
insert into sys_dept values(100,  0,   '0',          '{0}~bY',   0, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      '#Z^SwnSY?, 1, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      'cwRUSY?, 2, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  '.eB_)1h',   1, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  '/uPn)1h',   2, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  '4Z-[/v)1h',   3, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  't 2Y)1h',   4, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  'ig.am)1h',   5, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  '/uPn)1h',   1, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  't 2Y)1h',   2, '{0}', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2zOdeO0
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment '"&1WID',
  dept_id           bigint(20)      default null               comment ')1hID',
  user_name         varchar(30)     not null                   comment '"&1Wt@_',
  nick_name         varchar(30)     not null                   comment '"&1W5u',
  user_type         varchar(2)      default '00'               comment '"&1W~7p?0~d|"&1W?,
  email             varchar(50)     default ''                 comment '"&1W',
  phonenumber       varchar(11)     default ''                 comment '+[nYr',
  sex               char(1)         default '0'                comment '"&1WCW?"?1o?2Faq?,
  avatar            varchar(100)    default ''                 comment 'oQgQfgCo',
  password          varchar(100)    default ''                 comment '5pUr',
  status            char(1)         default '0'                comment 't@_5 O}0YE6r 1Knde?,
  del_flag          char(1)         default '0'                comment 'RrjVT~?`mH0p:jjn 2`mH0Rrj?,
  login_ip          varchar(128)    default ''                 comment ' Z^j0eP',
  login_date        datetime                                   comment ' Z^j0fi?,
  pwd_update_date   datetime                                   comment '5pUr Z^?mHri?,
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time       datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  remark            varchar(500)    default null               comment 'oV^e',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = '"&1Wm!Ot?;

-- ----------------------------
-- RoPV?"&1Wm!Ot&1f?-- ----------------------------
insert into sys_user values(1,  103, 'admin', '{0}', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '~`[?);
insert into sys_user values(2,  105, 'ry',    '{0}', '00', 'ry@qq.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', null, '4Z-[/v[?);


-- ----------------------------
-- 3xOwcm]O0
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment '[>i}ID',
  post_code     varchar(64)     not null                   comment '[>i}+hr',
  post_name     varchar(50)     not null                   comment '[>i}Z]',
  post_sort     int(4)          not null                   comment 'gRZ0$iT-|',
  status        char(1)         not null                   comment '5 O}0YE6r 1Knde?,
  create_by     varchar(64)     default ''                 comment 'Rmp?,
  create_time   datetime                                   comment 'RmXh',
  update_by     varchar(64)     default ''       comment 'X[gp?,
  update_time   datetime                                   comment 'X[gXh',
  remark        varchar(500)    default null               comment 'oV^e',
  primary key (post_id)
) engine=innodb comment = '[>i}m!Ot?;

-- ----------------------------
-- RoPV?[>i}m!Ot&1f?-- ----------------------------
insert into sys_post values(1, 'ceo',  'D(|?,    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   '$i-W0m_`',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   '\mTYtR.|',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', ' ,la[?,  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4}OWymO0
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment 'YtcXID',
  role_name            varchar(30)     not null                   comment 'YtcXZ]',
  role_key             varchar(100)    not null                   comment 'YtcXQjpCim?,
  role_sort            int(4)          not null                   comment 'gRZ0$iT-|',
  data_scope           char(1)         default '1'                comment 'HrA]|Q?m?,lS&1fHo?23lV9p-lfHo?3-lpn)1hHrA]Qj 4-lpn)1hYAZNm+[fHo6a}',
  menu_check_strictly  tinyint(1)      default 1                  comment 'n]&b YZ$iiZ@Sqej~?,
  dept_check_strictly  tinyint(1)      default 1                  comment ')1h&b YZ$iiZ@Sqej~?,
  status               char(1)         not null                   comment 'YtcX5 O}0YE6r 1Knde?,
  del_flag             char(1)         default '0'                comment 'RrjVT~?`mH0p:jjn 2`mH0Rrj?,
  create_by            varchar(64)     default ''                 comment 'Rmp?,
  create_time          datetime                                   comment 'RmXh',
  update_by            varchar(64)     default ''                 comment 'X[gp?,
  update_time          datetime                                   comment 'X[gXh',
  remark               varchar(500)    default null               comment 'oV^e',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = 'YtcXm!Ot?;

-- ----------------------------
-- RoPV?YtcXm!Ot&1f?-- ----------------------------
insert into sys_role values('1', 'toT~`[?,  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, 'toT~`[?);
insert into sys_role values('2', ' 3lWy?,    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, ' 3lWy?);


-- ----------------------------
-- 5}OM_WfHo/a0
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment 'n]ID',
  menu_name         varchar(50)     not null                   comment 'n]Z]',
  parent_id         bigint(20)      default 0                  comment '`M_WeD',
  order_num         int(4)          default 0                  comment 'gRZ0$iT-|',
  path              varchar(200)    default ''                 comment 'tqefgCo',
  component         varchar(255)    default null               comment 'R"kt}',
  query             varchar(255)    default null               comment 'tqeYPf',
  route_name        varchar(50)     default ''                 comment 'tqeZ]',
  is_frame          int(1)          default 1                  comment '`mT;d}0?1Z<}',
  is_cache          int(1)          default 0                  comment '`dt?dt 1m]&}pHj}',
  menu_type         char(1)         default ''                 comment 'n]~7p!W)} Cn] FY3c?,
  visible           char(1)         default 0                  comment 'n]5 O}0gRZ0 1/ah?,
  status            char(1)         default 0                  comment 'n]5 O}0YE6r 1Knde?,
  perms             varchar(100)    default null               comment 'QjVv',
  icon              varchar(100)    default '#'                comment 'n]ep#r',
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time       datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  remark            varchar(500)    default ''                 comment 'oV^e',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = 'n]Qjt?;

-- ----------------------------
-- RoPV?n]m!Ot&1f?-- ----------------------------
-- m ~FM_W?insert into sys_menu values('1', '~d|~`', '0', '1', 'system',           null, '', '', 1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, '~d|~`)}');
insert into sys_menu values('2', '~d|)b6^', '0', '2', 'monitor',          null, '', '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, '~d|)b6^)}');
insert into sys_menu values('3', '~d|[0S', '0', '3', 'tool',             null, '', '', 1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, '~d|[0S)}');
insert into sys_menu values('4', '{0}9p<j}', '0', '4', 'http://ruoyi.vip', null, '', '', 0, 0, 'M', '0', '0', '', 'guide',    'admin', sysdate(), '', null, '{0}9p<j}fgCo');
-- \m\n]
insert into sys_menu values('100',  '"&1W~`', '1',   '1', 'user',       'system/user/index',        '', '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, '"&1W~`n]');
insert into sys_menu values('101',  'YtcX~`', '1',   '2', 'role',       'system/role/index',        '', '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, 'YtcX~`n]');
insert into sys_menu values('102',  'n]~`', '1',   '3', 'menu',       'system/menu/index',        '', '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, 'n]~`n]');
insert into sys_menu values('103',  ')1h~`', '1',   '4', 'dept',       'system/dept/index',        '', '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, ')1h~`n]');
insert into sys_menu values('104',  '[>i}~`', '1',   '5', 'post',       'system/post/index',        '', '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, '[>i}~`n]');
insert into sys_menu values('105',  'p@iT~`', '1',   '6', 'dict',       'system/dict/index',        '', '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, 'p@iT~`n]');
insert into sys_menu values('106',  'YPftgRu', '1',   '7', 'config',     'system/config/index',      '', '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, 'YPftgRun]');
insert into sys_menu values('107',  '1laqOa', '1',   '8', 'notice',     'system/notice/index',      '', '', 1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, '1laqOan]');
insert into sys_menu values('108',  '0T~~`', '1',   '9', 'log',        '',                         '', '', 1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, '0T~~`n]');
insert into sys_menu values('109',  'f'1V"&1W', '2',   '1', 'online',     'monitor/online/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, 'f'1V"&1Wn]');
insert into sys_menu values('110',  '9p-li`mY', '2',   '2', 'job',        'monitor/job/index',        '', '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, '9p-li`mYn]');
insert into sys_menu values('111',  'HrA])b6^', '2',   '3', 'druid',      'monitor/druid/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, 'HrA])b6^n]');
insert into sys_menu values('112',  ']Y)b6^', '2',   '4', 'server',     'monitor/server/index',     '', '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, ']Y)b6^n]');
insert into sys_menu values('113',  'dt)b6^', '2',   '5', 'cache',      'monitor/cache/index',      '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, 'dt)b6^n]');
insert into sys_menu values('114',  'dtRDi0', '2',   '6', 'cacheList',  'monitor/cache/list',       '', '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'admin', sysdate(), '', null, 'dtRDi0n]');
insert into sys_menu values('115',  't%1]R', '3',   '1', 'build',      'tool/build/index',         '', '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, 't%1]Rn]');
insert into sys_menu values('116',  '`mGr"qW', '3',   '2', 'gen',        'tool/gen/index',           '', '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, '`mGr"qWn]');
insert into sys_menu values('117',  '~d|0[_', '3',   '3', 'swagger',    'tool/swagger/index',       '', '', 1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, '~d|0[_n]');
-- mYn]
insert into sys_menu values('500',  ']}0T~', '108', '1', 'operlog',    'monitor/operlog/index',    '', '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, ']}0T~n]');
insert into sys_menu values('501',  ''}0T~', '108', '2', 'logininfor', 'monitor/logininfor/index', '', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, ''}0T~n]');
-- "&1W~`Y3c
insert into sys_menu values('1000', '"&1W0', '100', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', '"&1Wg', '100', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', '"&1Wm|e', '100', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', '"&1WRrj', '100', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', '"&1W5pNqV', '100', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', '"&1W5pNqS', '100', '6',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', ']u5pUr', '100', '7',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- YtcX~`Y3c
insert into sys_menu values('1007', 'YtcX0', '101', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', 'YtcXg', '101', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', 'YtcXm|e', '101', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', 'YtcXRrj', '101', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', 'YtcX5pNqV', '101', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- n]~`Y3c
insert into sys_menu values('1012', 'n]0', '102', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', 'n]g', '102', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', 'n]m|e', '102', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', 'n]Rrj', '102', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- )1h~`Y3c
insert into sys_menu values('1016', ')1h0', '103', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', ')1hg', '103', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', ')1hm|e', '103', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', ')1hRrj', '103', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- [>i}~`Y3c
insert into sys_menu values('1020', '[>i}0', '104', '1',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', '[>i}g', '104', '2',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', '[>i}m|e', '104', '3',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', '[>i}Rrj', '104', '4',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', '[>i}5pNqV', '104', '5',  '', '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- p@iT~`Y3c
insert into sys_menu values('1025', 'p@iT0', '105', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', 'p@iTg', '105', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', 'p@iTm|e', '105', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', 'p@iTRrj', '105', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', 'p@iT5pNqV', '105', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- YPftgRuY3c
insert into sys_menu values('1030', 'YPf0', '106', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', 'YPfg', '106', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', 'YPfm|e', '106', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', 'YPfRrj', '106', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', 'YPf5pNqV', '106', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- 1laqOaY3c
insert into sys_menu values('1035', 'Oa0', '107', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', 'Oag', '107', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', 'Oam|e', '107', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', 'OaRrj', '107', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- ]}0T~Y3c
insert into sys_menu values('1039', ']}0', '500', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', ']}Rrj', '500', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', '0T~5pNqV', '500', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- '}0T~Y3c
insert into sys_menu values('1042', ''}0', '501', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1043', ''}Rrj', '501', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', '0T~5pNqV', '501', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', 't=WYtI#e', '501', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock',  '#', 'admin', sysdate(), '', null, '');
-- f'1V"&1WY3c
insert into sys_menu values('1046', 'f'1V0', '109', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', 'R_zV[*m  ', '109', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', 'Wfoo[*m  ', '109', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- 9p-li`mYY3c
insert into sys_menu values('1049', '`mY0', '110', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', '`mYg', '110', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', '`mYm|e', '110', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', '`mYRrj', '110', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', '5 wOha?, '110', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', '`mY5pNqV', '110', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');
-- `mGr"qWY3c
insert into sys_menu values('1055', '"qW0', '116', '1', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', '"qWm|e', '116', '2', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1057', '"qWRrj', '116', '3', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', '5pNqS`mGr', '116', '4', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', 'hRM`mGr', '116', '5', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', '"qW`mGr', '116', '6', '#', '', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6zOde\bYtcXO[Nt? "&1WN-1YtcX
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment '"&1WID',
  role_id   bigint(20) not null comment 'YtcXID',
  primary key(user_id, role_id)
) engine=innodb comment = '"&1W\\WycSqe0';

-- ----------------------------
-- RoPV?"&1W\\WycSqe0HrA]
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7}OWycbn]O[Nt? YtcX1-Nn]
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment 'YtcXID',
  menu_id   bigint(20) not null comment 'n]ID',
  primary key(role_id, menu_id)
) engine=innodb comment = 'YtcX\\M_WfSqe0';

-- ----------------------------
-- RoPV?YtcX\\M_WfSqe0HrA]
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
-- 8}OWycb)1hO[Nt? YtcX1-N)1h
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment 'YtcXID',
  dept_id   bigint(20) not null comment ')1hID',
  primary key(role_id, dept_id)
) engine=innodb comment = 'YtcX\\4Q%1Sqe0';

-- ----------------------------
-- RoPV?YtcX\\4Q%1Sqe0HrA]
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9zOde{[>i}O[Nt? "&1W1-N[>i}
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment '"&1WID',
  post_id   bigint(20) not null comment '[>i}ID',
  primary key (user_id, post_id)
) engine=innodb comment = '"&1Wm^wcm]Sqe0';

-- ----------------------------
-- RoPV?"&1Wm^wcm]Sqe0HrA]
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10yO7dcmnhGDi0?-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment '0T~mZZme',
  title             varchar(50)     default ''                 comment 'Y3 aoV}',
  business_type     int(2)          default 0                  comment 'm,lY~7p?O`u 1g 2m|e 3Rrj?,
  method            varchar(200)    default ''                 comment 'vxZ]',
  request_method    varchar(10)     default ''                 comment 't0wpt!}',
  operator_type     int(1)          default 0                  comment ']}~W?O`u 1Z^t_"&1W 2+[n~deQ}',
  oper_name         varchar(50)     default ''                 comment ']}\mTa',
  dept_name         varchar(50)     default ''                 comment ')1hZ]',
  oper_url          varchar(255)    default ''                 comment 't0wURL',
  oper_ip           varchar(128)    default ''                 comment 'm~nfgCo',
  oper_location     varchar(255)    default ''                 comment ']}fnbcP',
  oper_param        varchar(2000)   default ''                 comment 't0wYPf',
  json_result       varchar(2000)   default ''                 comment 'igemYPf',
  status            int(1)          default 0                  comment ']}5 O}0YE6r 1[P6r?,
  error_msg         varchar(2000)   default ''                 comment 'kZXO',
  oper_time         datetime                                   comment ']}Xh',
  cost_time         bigint(20)      default 0                  comment 'Z#X Aii?,
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = ']}0T~tg}';


-- ----------------------------
-- 11xOtOTh-[0
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment 'p@iTmZZme',
  dict_name        varchar(100)    default ''                 comment 'p@iTZ]',
  dict_type        varchar(100)    default ''                 comment 'p@iT~7p',
  status           char(1)         default '0'                comment '5 O}0YE6r 1Knde?,
  create_by        varchar(64)     default ''                 comment 'Rmp?,
  create_time      datetime                                   comment 'RmXh',
  update_by        varchar(64)     default ''                 comment 'X[gp?,
  update_time      datetime                                   comment 'X[gXh',
  remark           varchar(500)    default null               comment 'oV^e',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = 'p@iT~7pt?;

insert into sys_dict_type values(1,  '"&1WCW', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, '"&1WCWRDi0');
insert into sys_dict_type values(2,  'n]5 ?, 'sys_show_hide',       '0', 'admin', sysdate(), '', null, 'n]5 xOWt?);
insert into sys_dict_type values(3,  '~d|[ O?, 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, '~d|[ OQWt?);
insert into sys_dict_type values(4,  '`mY5 ?, 'sys_job_status',      '0', 'admin', sysdate(), '', null, '`mY5 xOWt?);
insert into sys_dict_type values(5,  '`mYRU|', 'sys_job_group',       '0', 'admin', sysdate(), '', null, '`mYRU|RDi0');
insert into sys_dict_type values(6,  '~d|`', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, '~d|`RDi0');
insert into sys_dict_type values(7,  '1laq~7p', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, '1laq~7pRDi0');
insert into sys_dict_type values(8,  '1laq5 ?, 'sys_notice_status',   '0', 'admin', sysdate(), '', null, '1laq5 xOWt?);
insert into sys_dict_type values(9,  ']}~7p', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, ']}~7pRDi0');
insert into sys_dict_type values(10, '~d|5 ?, 'sys_common_status',   '0', 'admin', sysdate(), '', null, ''}5 xOWt?);


-- ----------------------------
-- 12xOtOJTf0
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment 'p@iT+hr',
  dict_sort        int(4)          default 0                  comment 'p@iTc-|',
  dict_label       varchar(100)    default ''                 comment 'p@iTV7',
  dict_value       varchar(100)    default ''                 comment 'p@iT ?,
  dict_type        varchar(100)    default ''                 comment 'p@iT~7p',
  css_class        varchar(100)    default null               comment '\!}^pp M}O5g|\!}A%Mw?,
  list_class       varchar(100)    default null               comment 't&1xrepj\!}',
  is_default       char(1)         default 'N'                comment '`i?j{7W?NZ<}',
  status           char(1)         default '0'                comment '5 O}0YE6r 1Knde?,
  create_by        varchar(64)     default ''                 comment 'Rmp?,
  create_time      datetime                                   comment 'RmXh',
  update_by        varchar(64)     default ''                 comment 'X[gp?,
  update_time      datetime                                   comment 'X[gXh',
  remark           varchar(500)    default null               comment 'oV^e',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = 'p@iTHrA]t?;

insert into sys_dict_data values(1,  1,  '"?,       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'CW"?);
insert into sys_dict_data values(2,  2,  'o?,       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, 'CWo?);
insert into sys_dict_data values(3,  3,  'Faq',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, 'CWFaq');
insert into sys_dict_data values(4,  1,  'gRZ0',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'gRZ0n]');
insert into sys_dict_data values(5,  2,  '/ah',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '/ahn]');
insert into sys_dict_data values(6,  1,  'YE6r',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'YE6r5 ?);
insert into sys_dict_data values(7,  2,  'Knde',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Knde5 ?);
insert into sys_dict_data values(8,  1,  'YE6r',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'YE6r5 ?);
insert into sys_dict_data values(9,  2,  'PN',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Knde5 ?);
insert into sys_dict_data values(10, 1,  'i?j{',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'i?j{RU|');
insert into sys_dict_data values(11, 2,  '~d|',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, '~d|RU|');
insert into sys_dict_data values(12, 1,  '?,       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, '~d|i?j{?);
insert into sys_dict_data values(13, 2,  'Z?,       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '~d|i?j{Z?);
insert into sys_dict_data values(14, 1,  '1laq',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, '1laq');
insert into sys_dict_data values(15, 2,  'Oa',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, 'Oa');
insert into sys_dict_data values(16, 1,  'YE6r',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'YE6r5 ?);
insert into sys_dict_data values(17, 2,  'Obh',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Obh5 ?);
insert into sys_dict_data values(18, 99, 'O5g|',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'O5g|]}');
insert into sys_dict_data values(19, 1,  'g',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'g]}');
insert into sys_dict_data values(20, 2,  'm|e',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'm|e]}');
insert into sys_dict_data values(21, 3,  'Rrj',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Rrj]}');
insert into sys_dict_data values(22, 4,  'XHo',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, 'XHo]}');
insert into sys_dict_data values(23, 5,  '5pNqV',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '5pNqV]}');
insert into sys_dict_data values(24, 6,  '5pNqS',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '5pNqS]}');
insert into sys_dict_data values(25, 7,  '[*m  ',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, '[*m  ]}');
insert into sys_dict_data values(26, 8,  '"qW`mGr', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, '"qW]}');
insert into sys_dict_data values(27, 9,  'ZoT%HrA]', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'ZoT%]}');
insert into sys_dict_data values(28, 1,  ',aY',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, 'YE6r5 ?);
insert into sys_dict_data values(29, 2,  'o',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Knde5 ?);


-- ----------------------------
-- 13xO,_4OS0
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment 'YPfmZZme',
  config_name       varchar(100)    default ''                 comment 'YPfZ]',
  config_key        varchar(100)    default ''                 comment 'YPf`',
  config_value      varchar(500)    default ''                 comment 'YPf ?,
  config_type       char(1)         default 'N'                comment '~d|PoTu7W?NZ<}',
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time       datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  remark            varchar(500)    default null               comment 'oV^e',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = 'YPf]ut?;

insert into sys_config values(1, 'm~X0-i?j{(FP\!}Z]',     'sys.index.skinName',               'skin-blue',     'Y', 'admin', sysdate(), '', null, 'oX skin-bluezOby?skin-greenzO Oy?skin-purplezOi[y?skin-redO|y?skin-yellow' );
insert into sys_config values(2, '"&1W~`-t@_RoP5pUr',         'sys.user.initPassword',            '123456',        'Y', 'admin', sysdate(), '', null, 'RoPV'hv.?123456' );
insert into sys_config values(3, 'm~X0-nFz_[h?,           'sys.index.sideTheme',              'theme-dark',    'Y', 'admin', sysdate(), '', null, '#ZXmZZ}theme-dark~\lyym[hiheme-light' );
insert into sys_config values(4, 't@_wDY-`i\v.xO}O?,           'sys.account.captchaEnabled',       'true',          'Y', 'admin', sysdate(), '', null, '`[ ZYstzOrTqXQqWrue[ Z$}falseObh?);
insert into sys_config values(5, 't@_wDY-`[ Zde^eP}\Ys?, 'sys.account.registerUser',         'false',         'Y', 'admin', sysdate(), '', null, '`[ Z^eP\de\Yse}true[ Z$}falseObh?);
insert into sys_config values(6, '"&1W'}-ib`WfWt?,           'sys.login.blackIPList',            '',              'Y', 'admin', sysdate(), '', null, 'tgRu'}IPib`WfjR}o+lVR_S$iN;RUk~\nexO.\]}*6lSzO}Zx}');
insert into sys_config values(7, '"&1W~`-RoP5pUrm|e~+hf',     'sys.account.initPasswordModify',   '1',             'Y', 'admin', sysdate(), '', null, '0,lWo*[v.wOha-Wt#0Sh}Z!An`m}.aZ0?-lA_cdeQ}oPIpChaptWo*[v.O}Rkjn'}(l|m-lA_chaptv.xOto');
insert into sys_config values(8, '"&1W~`-t@_5pUrX[g[&1an',     'sys.account.passwordValidateDays', '0',             'Y', 'admin', sysdate(), '', null, '5pUrX[g[&1anX^PkfpKi}HrA]RoPV'h 0m]jR}{0hapt@~$ioB,|0Op_,|365(RX[fY}oPIptpTC~igk[&1an'}~d|}Rkjn'}(l|m-lA_chaptv.xOto');


-- ----------------------------
-- 14zOq0?-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment 'tWWhID',
  user_name      varchar(50)    default ''                comment '"&1Wt@_',
  ipaddr         varchar(128)   default ''                comment ''}IPfgCo',
  login_location varchar(255)   default ''                comment ''}fnbcP',
  browser        varchar(50)    default ''                comment '4Z_Mc'1h?,
  os             varchar(50)    default ''                comment ']}~d|',
  status         char(1)        default '0'               comment ''}5 O}0,aY 1o?,
  msg            varchar(255)   default ''                comment '.aZ0ZXO',
  login_time     datetime                                 comment 'tWWhXh',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = '~d|tWWhtg}';


-- ----------------------------
-- 15xO~u5gbcTv4dA0
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment '`mYID',
  job_name            varchar(64)   default ''                 comment '`mYZ]',
  job_group           varchar(64)   default 'DEFAULT'          comment '`mYR`',
  invoke_target       varchar(500)  not null                   comment 'tQde)#rpCim?,
  cron_expression     varchar(255)  default ''                 comment 'cronFt(1c[?,
  misfire_policy      varchar(20)   default '3'                comment 't3 WFk~+hf?~*[F]F 2Fm Z?3`}F?,
  concurrent          char(1)       default '1'                comment '`B_F?O}O 1~yO?,
  status              char(1)       default '0'                comment '5 O}0YE6r 1PN?,
  create_by           varchar(64)   default ''                 comment 'Rmp?,
  create_time         datetime                                 comment 'RmXh',
  update_by           varchar(64)   default ''                 comment 'X[gp?,
  update_time         datetime                                 comment 'X[gXh',
  remark              varchar(500)  default ''                 comment 'oV^em!O',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = '9p-li`mYtQ[t?;

insert into sys_job values(1, '~d|i?j{XhYP}', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, '~d|i?j{XAnYP}', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, '~d|i?j{X?YP}', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 16xO~u5gbcTv4d=hGDi0
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment '`mY0T~ID',
  job_name            varchar(64)    not null                   comment '`mYZ]',
  job_group           varchar(64)    not null                   comment '`mYR`',
  invoke_target       varchar(500)   not null                   comment 'tQde)#rpCim?,
  job_message         varchar(500)                              comment '0T~m!O',
  status              char(1)        default '0'                comment 'F5 O}0YE6r 1o?,
  exception_info      varchar(2000)  default ''                 comment '[P6rm!O',
  start_time          datetime                                  comment 'F[ o+[i?,
  end_time            datetime                                  comment 'FdkoXh',
  create_time         datetime                                  comment 'RmXh',
  primary key (job_log_id)
) engine=innodb comment = '9p-li`mYtQ[0T~t?;


-- ----------------------------
-- 17O 1laqOat?-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment 'OaID',
  notice_title      varchar(50)     not null                   comment 'OaV}',
  notice_type       char(1)         not null                   comment 'Oa~7p?1laq 2Oa?,
  notice_content    longblob        default null               comment 'OaPmT',
  status            char(1)         default '0'                comment 'Oa5 O}0YE6r 1Obh?,
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time       datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  remark            varchar(255)    default null               comment 'oV^e',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = '1laqOat?;

-- ----------------------------
-- RoPV?Oam!Ot&1f?-- ----------------------------
insert into sys_notice values('1', 'ZE%0aKU?018-07-01 {0}nbXB_/uQf', '2', 'nbX4U9p?, '0', 'admin', sysdate(), '', null, '~`[?);
insert into sys_notice values('2', 'X[Y1laq?018-07-01 {0}~d|Q~\jX[Y', '1', 'X[YPmT',   '0', 'admin', sysdate(), '', null, '~`[?);
insert into sys_notice values('3', '{0}[ gZ-a5g|?, '1', '<p><span style=\"color: rgb(230, 0, 0);\">$i-W0m`m,[|</span></p><p><font color=\"#333333\">RuoYi[ gZ0a0)imp}m1lde\~uRARkZ^t_t-lX5}mp}m-lXrkm ~k!}YtEU\jcm^}m,l}YbW
},a][ YbefV P[UtxO[dexU }OWy,xU O4Q'1xU }OM_WfxU xO,_nbxU xOtOTxU ?/font><span style=\"color: rgb(51, 51, 51);\">[>i}~`</span><span style=\"color: rgb(51, 51, 51);\">xO~u5gbcT?/span><span style=\"color: rgb(51, 51, 51);\">?/span><span style=\"color: rgb(51, 51, 51);\">]Y)b6^zOj0fhG<i yO7dcmnhG<i wO,U.zOSe.atTqXQPSmh}ig;jnexO?HrA]gZ)a yOfHo)a xOWmmT[7NedisdtNocker'1yO|T)1YstzOrzOmYgt0v'}xOW/uQ!}\m*[Y?/span><font color=\"#333333\">RUz[_g`mtL?/font><span style=\"color: rgb(51, 51, 51);\">xOW4ddWt%1)Ut X^ ?/span></p><p><img src=\"https://foruda.gitee.com/images/1773931848342439032/a4d22313_1815095.png\" style=\"width: 64px;\"><br></p><p><span style=\"color: rgb(230, 0, 0);\">9p<j}YCZ(}~?/span></p><p><span style=\"color: rgb(51, 51, 51);\">{0}9p<j}fgCo?nbsp;</span><a href=\"http://ruoyi.vip\" target=\"_blank\">http://ruoyi.vip</a><a href=\"http://ruoyi.vip\" target=\"_blank\"></a></p><p><span style=\"color: rgb(51, 51, 51);\">{0}V0fgCo?nbsp;</span><a href=\"http://doc.ruoyi.vip\" target=\"_blank\">http://doc.ruoyi.vip</a><br></p><p><span style=\"color: rgb(51, 51, 51);\">UZeZ0fgCo*a{RUX /b0}&nbsp;</span><a href=\"http://demo.ruoyi.vip\" target=\"_blank\">http://demo.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">UZeZ0fgCo,aW~dX /b0}&nbsp;</span><a href=\"http://vue.ruoyi.vip\" target=\"_blank\">http://vue.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">UZeZ0fgCo,aN]YX /b0}&nbsp;</span><a href=\"http://cloud.ruoyi.vip\" target=\"_blank\">http://cloud.ruoyi.vip</a></p><p><span style=\"color: rgb(51, 51, 51);\">UZeZ0fgCo.a)T'1lX /b0}&nbsp;</span><a href=\"http://h5.ruoyi.vip\" target=\"_blank\">http://h5.ruoyi.vip</a></p><p><br style=\"color: rgb(48, 49, 51); font-family: &quot;Helvetica Neue&quot;, Helvetica, Arial, sans-serif; font-size: 12px;\"></p>', '0', 'admin', sysdate(), '', null, '~`[?);


-- ----------------------------
-- 18xOS[BZQtf0f0
-- ----------------------------
drop table if exists sys_notice_read;
create table sys_notice_read (
  read_id          bigint(20)       not null auto_increment    comment '[6mZZme',
  notice_id        int(4)           not null                   comment 'Oaid',
  user_id          bigint(20)       not null                   comment '"&1Wid',
  read_time        datetime         not null                   comment 'pTXh',
  primary key (read_id),
  unique key uk_user_notice (user_id, notice_id)   comment 'Z|\z"&1WZ|\zOaYG0fzZ?
) engine=innodb auto_increment=1 comment='Oa[6tg}t?;


-- ----------------------------
-- 19wO,U.zOSe*a{T0
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment ''h_',
  table_name        varchar(200)    default ''                 comment 't%1`~?,
  table_comment     varchar(500)    default ''                 comment 't&1?_ig?,
  sub_table_name    varchar(64)     default null               comment 'O[Np/a0(R0Z?,
  sub_table_fk_name varchar(64)     default null               comment 'p/a0O[N(R;`',
  class_name        varchar(100)    default ''                 comment '9pp}~`~?,
  tpl_category      varchar(200)    default 'crud'             comment 'cmde(R!S}crudWf0]} tree#b0]}?,
  tpl_web_type      varchar(30)     default ''                 comment 'S]lY!o~7pKWlement-uiYX element-plusYX?,
  package_name      varchar(100)                               comment '"qWVpTw[?,
  module_name       varchar(30)                                comment '"qWY3 aoZ?,
  business_name     varchar(30)                                comment '"qWm,lYZ?,
  function_name     varchar(50)                                comment '"qWTqXQZ?,
  function_author   varchar(50)                                comment '"qWTqXQcmn ?,
  form_col_num      int(1)          default 1                  comment 't%1]/uQ,wX]R?Y}\W m
YW?,
  gen_type          char(1)         default '0'                comment '"qW`mGrpt!}?zipX,[V?1wD~umYw[R}',
  gen_path          varchar(200)    default '/'                comment '"qWt}X{o|t00)w[R}',
  options           varchar(1000)                              comment 'O`u"qWY0',
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time     datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  remark            varchar(500)    default null               comment 'oV^e',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = '`mGr"qWm,lYt?;


-- ----------------------------
-- 20wO,U.zOSe*a{T0pAi
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment ''h_',
  table_id          bigint(20)                                 comment '0cXwt'1*}Y?,
  column_name       varchar(200)                               comment 'R@i`~?,
  column_comment    varchar(500)                               comment 'RAi?_ig?,
  column_type       varchar(100)                               comment 'RCih?,
  java_type         varchar(500)                               comment 'JAVA~7p',
  java_field        varchar(200)                               comment 'JAVApAiZ?,
  is_pk             char(1)                                    comment '`mZZme?$}',
  is_increment      char(1)                                    comment '`wD?$}',
  is_required       char(1)                                    comment '`GmT^?$}',
  is_insert         char(1)                                    comment '`mpC_O0tZx}1$}',
  is_edit           char(1)                                    comment '`,h}pAi?$}',
  is_list           char(1)                                    comment '`RDi0pAi?$}',
  is_query          char(1)                                    comment '`0pAi?$}',
  query_type        varchar(200)    default 'EQ'               comment '0pt!}"Xt\m^ wO{~Y,|xOG0\m^ xOv\m^ }O[e}',
  html_type         varchar(200)                               comment 'gRZ0~7pXgyOgYqwO{YxO2YxO]YyOhq6^`m}',
  dict_type         varchar(200)    default ''                 comment 'p@iT~7p',
  sort              int                                        comment 'c-|',
  create_by         varchar(64)     default ''                 comment 'Rmp?,
  create_time     datetime                                   comment 'RmXh',
  update_by         varchar(64)     default ''                 comment 'X[gp?,
  update_time       datetime                                   comment 'X[gXh',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = '`mGr"qWm,lYt%1tZ?;
