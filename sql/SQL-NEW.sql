drop database if exists community;
create database community character set utf8mb4;
USE community;

-- ----------------------------
-- 数据库设计
-- ----------------------------
-- 1、用户信息表
-- ----------------------------
drop table if exists dq_user;
create table dq_user
(
    user_id      bigint(255)  not null auto_increment comment '用户ID',
    user_name    varchar(255) not null unique comment '用户名',
    email        varchar(255) not null unique comment '邮箱',
    phone_number varchar(255) unique comment '手机号',
    sex          char(2)      default '0' comment '性别（0为男，1为女，2为未知）',
    avatar       varchar(255) default '' comment '头像地址',
    password     varchar(255) not null comment '密码',
    status       char(1)      default '1' comment '状态（1为正常，0为停用）',
    loginIp      varchar(255) default '127.0.0.1' comment '最后登陆的IP',
    loginDate    datetime comment '最后登陆的时间',
    signature    varchar(255) default '无' comment '个性签名',
    grade        bigint(255)  default 1 comment '用户等级',
    experience   bigint(255)  default 0 comment '用户经验值',
    check_status char(1) comment '邮箱校验的状态(1-通过，0-未通过)',
    school_id    bigint(255)  default 0 comment '用户所在学校ID',
    delete_flag  bigint(255)  default 0 comment '删除标志(不为0则删除,否则等于id)',
    create_time  datetime comment '创建时间',
    update_time  datetime comment '更新时间',
    primary key (user_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '用户表';
# 添加联合唯一键
alter table dq_user
    add unique key (user_id, delete_flag);
-- ----------------------------
-- 初始化-用户表
-- ----------------------------
insert into dq_user
values (1, '猫颜', '1071352028@qq.com', '17104344673', '0',
        'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        'd93a5def7511da3d0f2d171d9c344e91', '1', '127.0.0.1', sysdate(), '提笔,写忧伤，停笔，心怅然', 1, 0, 1, 1, 0,
        sysdate(),
        sysdate());
insert into dq_user
values (2, 'maoyan', '820244680@qq.com', '110', '0', 'logo', 'd93a5def7511da3d0f2d171d9c344e91', '1', '127.0.0.1',
        sysdate(), 'wu', 1, 0, 1, 1, 0, sysdate(), sysdate());
insert into dq_user
values (3, '张三', 'oimaoyanio@163.com', '119', '2', 'avter', 'd93a5def7511da3d0f2d171d9c344e91', '1', '127.0.0.1',
        sysdate(), 'wu', 1, 0, '1', 1, 0, sysdate(), sysdate());

-- ----------------------------
-- 2、用户关注表
-- ----------------------------
drop table if exists dq_follow_interdquser;
create table dq_follow_interdquser
(
    follow_id             bigint(255) not null auto_increment comment '主键ID',
    give_follow_dquser_id bigint(255) not null default 1 comment '发起关注的用户的ID',
    followed_dquser_id    bigint(255) not null default 1 comment '被关注的用户的ID',
    create_time           datetime comment '创建时间',
    update_time           datetime comment '更新时间',
    primary key (follow_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '用户关注表';
-- ----------------------------
-- 初始化-用户关注表
-- ----------------------------
insert into dq_follow_interdquser
values (1, 1, 1, sysdate(), sysdate());
insert into dq_follow_interdquser
values (2, 3, 1, sysdate(), sysdate());

-- ----------------------------
-- 3、用户-角色表(多对多)
-- ----------------------------
drop table if exists dq_user_role;
create table dq_user_role
(
    user_role_id bigint(255)  not null auto_increment comment '主键',

    role_name    varchar(255) not null default '普通用户' comment '角色名称',
    user_id      bigint(255)  not null default 1 comment '所拥有该角色的用户ID',
    role_status  char(1)      not null default 0 comment '当前角色是否激活（0-否，1-是）',
    create_time  datetime comment '创建时间',
    update_time  datetime comment '更新时间',
    primary key (user_role_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '用户-角色表(多对多)';
-- ----------------------------
-- 初始化-用户-角色表(多对多)
-- ----------------------------
insert into dq_user_role
values (1, '超级管理员', 1, 1, sysdate(), sysdate());
insert into dq_user_role
values (2, '普通用户', 2, 0, sysdate(), sysdate());
insert into dq_user_role
values (3, '文章管理员', 2, 1, sysdate(), sysdate());
insert into dq_user_role
values (4, '普通用户', 3, 0, sysdate(), sysdate());

-- ----------------------------
-- 4、角色-权限对应表（多对多）
-- ----------------------------
drop table if exists dq_role_permission;
create table dq_role_permission
(
    role_permission_id bigint(255)  not null auto_increment comment '主键',
    role_name          varchar(255) not null default '普通用户' comment '角色名称',
    permission_name    varchar(255) not null default 'user-ordinary' comment '权限名称',
    create_time        datetime comment '创建时间',
    update_time        datetime comment '更新时间',
    primary key (role_permission_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '角色-权限对应表（多对多）';
-- ----------------------------
-- 初始化-角色-权限对应表（多对多）
-- ----------------------------
insert into dq_role_permission
values (1, '超级管理员', '*', sysdate(), sysdate());
insert into dq_role_permission
values (2, '普通用户', 'user*', sysdate(), sysdate());
insert into dq_role_permission
values (3, '文章管理员', 'admin-article', sysdate(), sysdate());
insert into dq_role_permission
values (4, '高级管理员', 'admin-*', sysdate(), sysdate());
insert into dq_role_permission
values (5, '学校管理员', 'admin-school', sysdate(), sysdate());
insert into dq_role_permission
values (6, '被封禁用户', 'user-article-query,user-article-delete,', sysdate(), sysdate());

-- ----------------------------
-- 5、版块表
-- ----------------------------
drop table if exists dq_section;
create table dq_section
(
    section_id            bigint(255)  not null auto_increment comment '版块主键',
    section_name          varchar(255) not null comment '版块名称',
    section_introduce     varchar(255) not null comment '版块介绍',
    section_logo          varchar(255) not null comment '版块logo',
    section_background    varchar(255) not null comment '版块背景图',
    section_admin_user_id bigint(255)  not null default 1 comment '版主用户ID',
    section_weight        bigint(255)  not null default 0 comment '版块权重',
    delete_flag           bigint(255)           default 0 comment '删除标志(不为0则删除,否则等于id)',
    create_time           datetime comment '创建时间',
    update_time           datetime comment '更新时间',
    primary key (section_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '版块表';
# 建立联合唯一键
alter table dq_section
    add unique key (section_id, delete_flag);
-- ----------------------------
-- 初始化-版块表
-- ----------------------------
insert into dq_section
values (1, '行思工作室', '河南理工大学最强工作室', 'logo', 'background', 1, 0, 0, sysdate(), sysdate());
insert into dq_section
values (2, '英雄联盟', '最好玩的MOBA类游戏',
        'https://tse1-mm.cn.bing.net/th/id/R-C.3f8dabc7aab1dab0a8e7b881d67879ca?rik=BY7xr597qzyPAw&riu=http%3a%2f%2fpic40.photophoto.cn%2f20160701%2f0007019992930239_b.jpg&ehk=ys6yzJOPHtmfIfLMtdvbqJU9DCpz5LhNpY5hkxPotJ4%3d&risl=&pid=ImgRaw&r=0'
           , 'background', 2, 0, 0, sysdate(), sysdate());

-- ----------------------------
-- 6、版块分类表
-- ----------------------------
drop table if exists dq_section_type;
create table dq_section_type
(
    section_type_id      bigint(255)  not null auto_increment comment '版块分类主键',
    section_type_name    varchar(255) not null comment '版块分类名称',
    section_id           bigint(255)  not null comment '分类所属的版块ID',
    section_type_weight  bigint(255)  not null default 0 comment '分类权重',
    section_type_mold    char(1)      not null default 0 comment '分类类型（0-普通分类，1-特殊分类）',
    section_type_network varchar(255) comment '分类网址',
    delete_flag          bigint(255)           default 0 comment '删除标志(不为0则删除,否则等于id)',
    create_time          datetime comment '创建时间',
    update_time          datetime comment '更新时间',
    primary key (section_type_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '版块分类表';
# 建立联合唯一键
alter table dq_section_type
    add unique key (section_type_id, delete_flag);
-- ----------------------------
-- 初始化-版块分类表
-- ----------------------------
insert into dq_section_type
values (1, '程序组', 1, 0, 0, 'url', 0, sysdate(), sysdate());
insert into dq_section_type
values (2, '设计组', 1, 0, 0, 'url', 0, sysdate(), sysdate());
insert into dq_section_type
values (3, '打法技巧', 2, 0, 0, 'url', 0, sysdate(), sysdate());
insert into dq_section_type
values (4, '联盟福利', 2, 0, 0, 'url', 0, sysdate(), sysdate());

-- ----------------------------
-- 2、版块关注表
-- ----------------------------
drop table if exists dq_follow_interdqsection;
create table dq_follow_interdqsection
(
    follow_id             bigint(255) not null auto_increment comment '主键ID',
    give_follow_dquser_id bigint(255) not null comment '发起关注的用户的ID',
    followed_dqsection_id bigint(255) not null comment '被关注的版块的ID',
    create_time           datetime comment '创建时间',
    update_time           datetime comment '更新时间',
    primary key (follow_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '用户关注版块的表';
-- ----------------------------
-- 初始化-版块关注表
-- ----------------------------
insert into dq_follow_interdqsection
values (1, 1, 1, sysdate(), sysdate());
insert into dq_follow_interdqsection
values (2, 1, 2, sysdate(), sysdate());

-- ----------------------------
-- 2、学校表
-- ----------------------------
drop table if exists dq_school;
create table dq_school
(
    school_id         bigint(255)  not null auto_increment comment '学校主键ID',
    school_name       varchar(255) not null unique comment '学校名称',
    school_introduce  text         not null comment '学校介绍',
    school_badge      varchar(255) not null comment '学校校徽',
    school_motto      varchar(255) not null comment '学校校训',
    school_background varchar(255) not null comment '学校背景图',
    school_build_date datetime comment '建校时间',
    school_location   varchar(255) comment '学校位置',
    delete_flag       bigint(255) default 0 comment '删除标志(不为0则删除,否则等于id)',
    create_time       datetime comment '创建时间',
    update_time       datetime comment '更新时间',
    primary key (school_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '学校表';
# 建立联合唯一键
alter table dq_school
    add unique key (school_id, delete_flag);
-- ----------------------------
-- 初始化-学校表
-- ----------------------------
insert into dq_school
values (1, '河南理工大学',
        '1909年，河南理工大学（简称“河南理工”；英文：Henan Polytechnic University，英文简称“HPU”）的前身——焦作路矿学堂，在黄河之滨、太行之阳的焦作诞生，成为我国第一所矿业高等学府和河南省建立最早的高等学校。学校历经福中矿务大学、私立焦作工学院、国立西北工学院、国立焦作工学院、焦作矿业学院（简称“焦作矿院”；英文：Jiaozuo Mining Institute，英文简称“JMI”）和焦作工学院（简称“焦工”；英文：Jiaozuo Institute of Technology，英文简称“JIT”）等重要历史时期，2004年更名河南理工大学，是中央与地方共建、以地方管理为主的河南省特色骨干大学，河南省人民政府与原国家安全生产监督管理总局共建高校，入选国家“中西部高校基础能力建设工程”高校。'
           , 'badge', '明德任责，好学力行', 'background', sysdate(), '河南省焦作市世纪路2001号', 0, sysdate(), sysdate());
insert into dq_school
values (2, '韩山师范学院',
        '韩山师范学院（HANSHAN NORMAL UNIVERSITY），简称“韩山师院、韩师（HSNU）”，是广东省属本科师范院校，是广东省与潮州市共建高校，联合国教科文组织中国创业教育联盟理事单位，位于国家历史文化名城潮州市。韩山师范学院创立于清光绪廿九年（1903年）的“惠潮嘉师范学堂”，其前身可追溯到宋元祐五年（公元1090年）潮人为纪念唐代大文学家韩愈而建立的“韩山书院”，1921年更名为省立第二师范学校，1935年更名为省立韩山师范学校，1949年更名为韩山师范学校，1958年升格为高等师范专科学校，1993年升格为本科师范学院。',
        'badge', '韩山师范学院校训', 'background', sysdate(), 'address', 0, sysdate(), sysdate());

-- ----------------------------
-- 2、文章表
-- ----------------------------
drop table if exists dq_article;
create table dq_article
(
    article_id      bigint(255)  not null auto_increment comment '文章ID',
    article_title   varchar(255) not null comment '文章标题',
    article_content text         not null comment '文章内容',
    article_image   varchar(255) comment '文章首页图片',
    section_id      bigint(255)  not null comment '文章所属版块ID',
    section_type_id bigint(255)  not null comment '文章所在版块中的分类的ID',
    author_id       bigint(255)  not null comment '作者ID',
    status          char(1)     default '1' comment '状态（1为正常，0为封禁）',
    article_weight  bigint(255) default 0 comment '文章权重',
    delete_flag     bigint(255) default 0 comment '删除标志(不为0则删除,否则等于id)',
    create_time     datetime comment '创建时间',
    update_time     datetime comment '更新时间',
    primary key (article_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '文章表';
# 建立联合唯一键
alter table dq_article
    add unique key (article_id, delete_flag);
-- ----------------------------
-- 初始化-文章表数据
-- ----------------------------
insert into dq_article
values (1, '第一篇文章', '# 欢迎您的到来', 'image', 1, 1, 1, 1, 0, 0, sysdate(), sysdate());
insert into dq_article
values (2, '剑圣打法', '后入场，开大无脑砍就完了', 'image', 2, 1, 1, 1, 0, 0, sysdate(), sysdate());

-- ----------------------------
-- 4、评论表
-- ----------------------------
drop table if exists dq_comment;
create table dq_comment
(
    comment_id      bigint(255)  not null auto_increment comment '评论ID',
    article_id      bigint(255)  not null comment '评论所在的文章ID',
    content         varchar(255) not null comment '评论内容',
    comment_user_id bigint(20)            default 1 comment '评论者ID',
    status          char(1)               default '1' comment '状态（1为正常，0为封禁）',
    to_user_id      bigint(255)  not null default 1 comment '被回复的人的ID',
    reply_id        bigint(255)           default 0 comment '父评论ID,回复的评论的ID(0则为是评论而不是回复)',
    root_id         bigint(255)           default 0 comment '根评论ID(为0则为根评论)',
    delete_flag     bigint(255)           default 0 comment '删除标志(不为0则删除,否则等于id)',
    create_time     datetime comment '创建时间',
    primary key (comment_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '评论表';
# 建立联合唯一键
alter table dq_comment
    add unique key (comment_id, delete_flag);
-- ----------------------------
-- 初始化-评论表数据
-- ----------------------------
insert into dq_comment
values (1, 1, 'hello', 1, '1', 1, 0, 0, 0, sysdate());
insert into dq_comment
values (2, 1, '第一条回复', 1, '1', 1, 1, 1, 0, sysdate());
insert into dq_comment
values (3, 1, '第一条回复的回复', 1, '1', 1, 2, 1, 0, sysdate());

-- ----------------------------
-- 4、收藏表
-- ----------------------------
drop table if exists dq_collection;
create table dq_collection
(
    collection_id bigint(255) not null auto_increment comment '收藏ID',
    user_id       bigint(255) not null comment '发起收藏的用户的ID',
    article_id    bigint(255) not null comment '被收藏的文章的ID',
    create_time   datetime comment '创建时间',
    update_time   datetime comment '更新时间',
    primary key (collection_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '收藏表';
-- ----------------------------
-- 初始化-收藏表数据
-- ----------------------------
insert into dq_collection
values (1, 1, 1, sysdate(), sysdate());

-- ----------------------------
-- 5、操作记录表
-- ----------------------------
drop table if exists dq_operlog;
create table dq_operlog
(
    oper_id        bigint(255)  not null auto_increment comment '操作的id',
    title          varchar(255) not null default 1 comment '操作的模块',
    business_type  bigint(10)   not null default 1 comment '业务类型(0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据)',
    method         varchar(255)          default 'add' comment '方法名称',
    request_method varchar(255)          default 'GET' comment '请求方式',
    oper_username  varchar(255)          default '操作用户名' comment '操作用户名',
    oper_url       varchar(255)          default 'localhost' comment '请求的URL',
    oper_ip        varchar(255)          default '127.0.0.1' comment '操作所在的ip',
    oper_location  varchar(255)          default '地球' comment '操作所在的地方',
    oper_param     varchar(255)          default null comment '请求参数',
    json_result    varchar(255)          default null comment '返回的参数',
    status         char(1)               default '0' comment '操作的状态(0正常 1异常)',
    error_msg      varchar(255)          default null comment '错误的消息',
    oper_time      datetime comment '操作的时间',

    primary key (oper_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '操作记录表';

-- ----------------------------
-- 初始化-操作记录表对应数据
-- ----------------------------
insert into dq_operlog
values (1, '文章', 1, 'addDqArticle', 'POST', 'maoyan', 'localhost', '127.0.0.1', '河南', '请求参数', 'null', '0', '错误的消息',
        sysdate());
insert into dq_operlog
values (2, '文章', 1, 'addDqArticle', 'POST', 'maoyan', 'localhost', '127.0.0.1', '河南', '请求参数', 'null', '0', '错误的消息',
        sysdate());
insert into dq_operlog
values (3, '文章', 1, 'addDqArticle', 'POST', 'maoyan', 'localhost', '127.0.0.1', '河南', '请求参数', 'null', '0', '错误的消息',
        sysdate());

-- ----------------------------
-- 添加唯一性约束(两个字段不能同时一样)
-- ----------------------------
alter table dq_follow_interdquser
    add unique key (give_follow_dquser_id, followed_dquser_id);
alter table dq_follow_interdqsection
    add unique key (give_follow_dquser_id, followed_dqsection_id);
alter table dq_collection
    add unique key (user_id, article_id);