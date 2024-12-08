# 用户信息表
CREATE TABLE pn_user
(
    id               bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    create_at        datetime   DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_at        datetime   DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '最近修改时间',
    create_by        bigint     DEFAULT -1                NOT NULL COMMENT '创建人',
    update_by        bigint     DEFAULT -1                NOT NULL COMMENT '更新人',
    is_deleted       tinyint(1) DEFAULT 0                 NOT NULL COMMENT '是否删除1--删除',
    username         varchar(100)                         NOT NULL COMMENT '用户名',
    password         varchar(256)                         NOT NULL COMMENT '用户密码',
    full_name        varchar(128)                         NULL COMMENT '用户全名',
    phone            varchar(255)                         NULL COMMENT '手机号',
    email            varchar(1024)                        NULL COMMENT '邮箱',
    is_admin         tinyint(1)                           NOT NULL COMMENT '是否管理员 1--是',
    last_login_date  datetime                             NOT NULL COMMENT '最后一次登录时间',
    update_last_time datetime   DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '最后一次修改时间'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT '用户信息表';

#第三方登陆信息表
create table pn_user_oauth
(
    id                BIGINT AUTO_INCREMENT COMMENT '自增ID' PRIMARY KEY,
    update_at         datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    create_at         datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by         bigint        NOT NULL DEFAULT 0 comment '修改人',
    update_by         bigint        NOT NULL DEFAULT 0 comment '更新人',
    is_deleted        tinyint(1)    NOT NULL DEFAULT 0 COMMENT '是否删除',
    user_id           bigint                 DEFAULT 0 NOT NULL COMMENT '关联用户表(pn_user)',
    source            varchar(40)   NULL COMMENT '第三方登录方式(github,gitee..)',
    uuid              varchar(255)  NULL COMMENT '三方平台用户id',
    platform_username varchar(255)  NULL COMMENT '三方平台用户名',
    avatar_url        varchar(1024) NULL COMMENT '用户头像url',
    authorized_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户授权时间'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 comment '第三方登陆信息表';


