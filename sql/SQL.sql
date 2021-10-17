-- ----------------------------
-- 数据库设计
-- ----------------------------
-- 1、用户信息表
-- ----------------------------
drop table if exists dq_user;
create table dq_user
(
    user_id      bigint(255)  not null auto_increment comment '用户ID',
    user_name    varchar(255) not null unique comment '用户账户',
    nick_name    varchar(255) default '昵称' comment '用户昵称',
    email        varchar(255) default 'email@xx.com' unique comment '邮箱',
    phone_number varchar(255) default '133333333' unique comment '手机号',
    sex          char(2)      default '0' comment '性别（0为男，1为女，2为未知）',
    avatar       varchar(255) default '' comment '头像地址',
    password     varchar(255) not null comment '密码',
    status       char(1)      default '0' comment '状态（0为正常，1为停用）',
    delFlag      char(1)      default '0' comment '删除标志（0为正常，1为删除）',
    loginIp      varchar(255) default '127.0.0.1' comment '最后登陆的IP',
    loginDate    datetime comment '最后登陆的时间',
    role         varchar(255) default '普通用户' comment '角色',
    signature    varchar(255) default '无' comment '个性签名',
    create_time  datetime comment '创建时间',
    update_time  datetime comment '更新时间',
    primary key (user_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '用户表';

-- ----------------------------
-- 初始化-用户表
-- ----------------------------
insert into dq_user
values (1, 'maoyan', '猫颜', '1071352028@qq.com', '17104344673', '1',
        'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        'd93a5def7511da3d0f2d171d9c344e91', '0', '0', '127.0.0.1', sysdate(), '超级管理员', '无', sysdate(), sysdate());
insert into dq_user
values (2, 'zhangsan', '张三', '1061352028@qq.com', '17004344673', '1',
        'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        'd93a5def7511da3d0f2d171d9c344e91', '0', '0', '127.0.0.1', sysdate(), '管理员', '无', sysdate(), sysdate());
insert into dq_user
values (3, 'hello', 'world', '110@qq.com', '110', '0', 'null', 'd93a5def7511da3d0f2d171d9c344e91', '0',
        '0', '127.0.0.1', sysdate(), '普通用户', 'wu', sysdate(), sysdate());
insert into dq_user
values (4, 'helloworld', 'world', '11asdas0@qq.com', '11asda0', '0', 'null', 'd93a5def7511da3d0f2d171d9c344e91', '0',
        '0', '127.0.0.1', sysdate(), '文章管理员', 'wu', sysdate(), sysdate());

-- ----------------------------
-- 2、文章表
-- ----------------------------
drop table if exists dq_article;
create table dq_article
(
    article_id      bigint(255)  not null auto_increment comment '文章ID',
    article_title   varchar(255) not null comment '文章标题',
    article_content text comment '文章内容',
    article_image   varchar(255) default '' comment '文章首页图片',
    type_id         bigint(20)   default 1 comment '文章所属类型ID',
    author_id       bigint(255)  not null comment '作者ID',
    author_nickname varchar(255) default '作者昵称' comment '作者昵称',
    author_username varchar(255) default '作者用户名' comment '作者用户名',
    status          char(1)      default '0' comment '状态（0为正常，1为封禁）',
    article_sort    bigint(255)  default 0 comment '文章排序',
    create_time     datetime comment '创建时间',
    update_time     datetime comment '更新时间',
    primary key (article_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '文章表';

-- ----------------------------
-- 初始化-文章表数据
-- ----------------------------
insert into dq_article
values (1, '文章标题1', '文章内容1', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        1, 1, '猫颜', 'maoyan', '0', 0, sysdate(), sysdate());
insert into dq_article
values (2, '文章标题2', '文章内容2', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        2, 2, '张三', 'zhangsan', '0', 0, sysdate(), sysdate());
insert into dq_article
values (3, '文章标题3', '文章内容3', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        3, 3, 'world', 'hello', '0', 0, sysdate(), sysdate());
insert into dq_article
values (4, '文章标题4', '文章内容4', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        1, 1, '猫颜', 'maoyan', '0', 0, sysdate(), sysdate());
insert into dq_article
values (5, '文章标题5', '文章内容5', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        2, 2, '张三', 'zhangsan', '0', 0, sysdate(), sysdate());
insert into dq_article
values (6, '文章标题6', '文章内容6', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg',
        3, 3, 'world', 'hello', '0', 0, sysdate(), sysdate());



-- ----------------------------
-- 3、文章类型表
-- ----------------------------
drop table if exists dq_type;
create table dq_type
(
    type_id      bigint(255)  not null auto_increment comment '类型ID',
    type_name    varchar(255) not null comment '类型名称',
    type_image   varchar(255) default '类型图片' comment '类型图片',
    status       char(1)      default '0' comment '状态（0为正常，1为封禁）',
    create_manid bigint(20)   default 1 comment '创建者ID',
    update_manid bigint(20)   default 1 comment '更新者ID',
    introduce    varchar(255) default '类型介绍' comment '类型介绍',
    create_time  datetime comment '创建时间',
    update_time  datetime comment '更新时间',
    primary key (type_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '类型表';

-- ----------------------------
-- 初始化-文章类型表数据
-- ----------------------------
insert into dq_type
values (1, 'Java', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg', '0',
        1, 1, 'Java是世界上最好的语言', sysdate(), sysdate());
insert into dq_type
values (2, 'php', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg', '0',
        1, 1, 'Php是世界上最好的语言', sysdate(), sysdate());
insert into dq_type
values (3, 'html', 'https://img2.woyaogexing.com/2020/03/01/63dba6d27b79483ea51f51c42c0604cd!400x400.jpeg', '0',
        1, 1, 'Html是世界上最好的语言', sysdate(), sysdate());

-- ----------------------------
-- 4、评论表
-- ----------------------------
drop table if exists dq_comment;
create table dq_comment
(
    comment_id           bigint(255) not null auto_increment comment '评论ID',
    article_id           bigint(255) not null comment '文章ID',
    content              varchar(255)         default '评论内容' comment '评论内容',
    comment_userid       bigint(20)           default 1 comment '评论者ID',
    comment_usernickname varchar(255)         default '评论者的昵称' comment '评论者的昵称',
    comment_username     varchar(255)         default '评论者的用户名' comment '评论者的用户名',
    status               char(1)              default '0' comment '状态（0为正常，1为封禁）',
    comment_user_avatar  varchar(255)         default '发表评论的用户的头像' comment '发表评论的用户的头像',
    create_time          datetime comment '创建时间',
    comment_type         char(2)              default '1' comment '评论的类型（1-评论 2-回复）',
    to_user_id           bigint(255) not null default 1 comment '被回复的人的ID',
    to_username          varchar(255)         default 'maoyan' comment '被回复的人的用户名',
    to_nickname          varchar(255)         default '猫颜' comment '被回复的人的昵称',
    to_user_avatar       varchar(255)         default '被回复的人的头像' comment '被回复的人的头像',
    reply_id             bigint(255)          default 0 comment '父评论ID,回复的评论的ID(0则为是评论而不是回复)',
    root_id              bigint(255)          default 0 comment '根评论ID(为0则为根评论)',
    primary key (comment_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '评论表';

-- ----------------------------
-- 初始化-评论表数据
-- ----------------------------
insert into dq_comment
values (1, 1, '第一个评论', 1, '猫颜', 'maoyan', '0', 'touxiang', sysdate(), 1, 1, 'maoyan', '猫颜', 'wu tou xiang', 0, 0);
insert into dq_comment
values (2, 1, '第二个评论', 1, '猫颜', 'maoyan', '0', 'touxiang', sysdate(), 1, 1, 'maoyan', '猫颜', 'wu tou xiang', 0, 0);
insert into dq_comment
values (3, 1, '第一个评论的回复', 1, '猫颜', 'maoyan', '0', 'touxiang', sysdate(), 2, 1, 'maoyan', '猫颜', 'touxiang', 1, 1);
insert into dq_comment
values (4, 1, '第一个回复的回复', 1, '猫颜', 'maoyan', '0', 'touxiang', sysdate(), 2, 1, 'maoyan', '猫颜', 'touxiang', 3, 1);


-- 4、角色权限对应表
-- ----------------------------
drop table if exists dq_role_permission;
create table dq_role_permission
(
    role_id         bigint(255)  not null auto_increment comment '角色ID',
    role_name       varchar(255) not null unique default '角色名称' comment '角色名称',
    permission_name varchar(255)                 default 'user-ordinary' comment '权限名称',
    create_time     datetime comment '创建时间',
    update_time     datetime comment '更新时间',
    primary key (role_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '角色权限对应';

-- ----------------------------
-- 初始化-角色权限对应数据
-- ----------------------------
insert into dq_role_permission
values (1, '超级管理员', 'super-admin', sysdate(), sysdate());
insert into dq_role_permission value (2, '管理员', 'admin-ordinary', sysdate(), sysdate());
insert into dq_role_permission value (3, '普通用户', 'user-ordinary', sysdate(), sysdate());
insert into dq_role_permission value (4, '文章管理员', 'user*,admin-article', sysdate(), sysdate());

-- ----------------------------
-- 5、点赞对应表
-- ----------------------------
drop table if exists dq_like;
create table dq_like
(
    like_id           bigint(255) not null auto_increment comment '点赞ID',
    give_like_user_id bigint(255) not null default 1 comment '点赞用户id',
    like_article_id   bigint(255) not null default 1 comment '被点赞的文章id',
    status            char(1)              default '0' comment '状态（0为取消，1为点赞状态）',
    create_time       datetime comment '创建时间',
    update_time       datetime comment '更新时间',
    primary key (like_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '用户点赞表';

-- ----------------------------
-- 初始化-角色权限对应数据
-- ----------------------------
insert into dq_like
values (1, 1, 1, 0, sysdate(), sysdate());
insert into dq_like
values (2, 1, 1, 0, sysdate(), sysdate());
insert into dq_like
values (3, 1, 1, 0, sysdate(), sysdate());

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
-- 5、标签表
-- ----------------------------
drop table if exists dq_tag;
create table dq_tag
(
    tag_id      bigint(255)  not null auto_increment comment 'tagId',
    tag_name    varchar(255) not null unique default 'tag名称' comment 'tag名称',
    create_time datetime comment '创建时间',
    update_time datetime comment '更新时间',

    primary key (tag_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '标签表';

-- ----------------------------
-- 初始化-标签表对应数据
-- ----------------------------
insert into dq_tag
values (1, 'Java', sysdate(), sysdate());
insert into dq_tag
values (2, 'SpringBoot', sysdate(), sysdate());
insert into dq_tag
values (3, 'Docker', sysdate(), sysdate());
insert into dq_tag
values (4, 'Linux', sysdate(), sysdate());
insert into dq_tag
values (5, 'PHP', sysdate(), sysdate());


-- ----------------------------
-- 5、文章标签对应表(一对多)
-- ----------------------------
drop table if exists dq_article_tag;
create table dq_article_tag
(
    article_tag_id bigint(255)  not null auto_increment comment '主键ID',
    article_id     varchar(255) not null default 0 comment '文章ID',
    tag_id         bigint(255)  not null default 0 comment 'tagID',
    create_time    datetime comment '创建时间',
    update_time    datetime comment '更新时间',
    primary key (article_tag_id),
    unique key article_id (article_id, tag_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '文章标签对应表';

-- ----------------------------
-- 初始化-文章标签对应表对应数据
-- ----------------------------
insert into dq_article_tag
values (1, 1, 1, sysdate(), sysdate());
insert into dq_article_tag
values (2, 1, 2, sysdate(), sysdate());
insert into dq_article_tag
values (3, 1, 3, sysdate(), sysdate());
insert into dq_article_tag
values (4, 2, 1, sysdate(), sysdate());
insert into dq_article_tag
values (5, 2, 2, sysdate(), sysdate());
insert into dq_article_tag
values (6, 2, 3, sysdate(), sysdate());

-- ----------------------------
-- 6、消息表
-- ----------------------------
drop table if exists dq_message;
create table dq_message
(
    message_id         bigint(255)  not null auto_increment comment '主键ID',
    re_user_id         bigint(255)  not null default 0 comment '提醒用户的ID',
    message_type       varchar(255) not null comment '消息的类型(1-点赞消息，2-评论消息，2-关注用户的动态，0-服务器消息)',
    se_user_id         bigint(255)           default 0 comment '消息发送的用户ID',
    message_article_id bigint(255)           default 0 comment '消息所在的文章ID',
    message_comment_id bigint(255)           default 0 comment '消息的评论ID',
    message_is_read    char(1)               default 0 comment '消息是否已读(0-未读，1-已读)',
    create_time        datetime comment '创建时间',
    update_time        datetime comment '更新时间',
    primary key (message_id)
) engine = innodb
  auto_increment = 1
  CHARACTER SET = utf8mb4 comment = '消息表';

-- ----------------------------
-- 初始化-消息表
-- ----------------------------
insert into dq_message
values (1, 1, 0, 1, 1, 1, 0, sysdate(), sysdate());