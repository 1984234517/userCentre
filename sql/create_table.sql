create table user
(
    id           bigint auto_increment comment 'id'
        primary key,
    username     varchar(256)                            null comment '用户名称',
    userAccount  varchar(256)                            null comment '用户账号',
    avatarUrl    varchar(1024)                           null comment '用户头像url',
    gender       tinyint                                 null comment '性别',
    userPassword varchar(256)                            not null comment '用户密码',
    phone        varchar(128)                            null comment '用户电话',
    email        varchar(512)                            null comment '用户邮箱',
    userStatus   int       default 0                     not null comment '用户状态 0表示正常',
    createTime   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    updateTime   datetime default CURRENT_TIMESTAMP   null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint   default 0                     not null comment '是否删除，0表示不删除',
    role         int       default 0                     not null comment '用户角色 0表示普通用户 1表示管理员 2表示VIP',
    planetCode   varchar(512)                            null comment '星球编号'
)
    comment '用户表';