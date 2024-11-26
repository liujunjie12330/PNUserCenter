CREATE TABLE pn_user
(
    id               bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'id',
    create_at        datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间',
    update_at        datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '最近修改时间',
    create_by        bigint       DEFAULT -1                NOT NULL COMMENT '创建人',
    update_by        bigint       DEFAULT -1                NOT NULL COMMENT '更新人',
    is_deleted       tinyint(1)   DEFAULT 0                 NOT NULL COMMENT '是否删除1--删除',
    username         varchar(100)                           NOT NULL COMMENT '用户名',
    password         varchar(256)                           NOT NULL COMMENT '用户密码',
    full_name        varchar(128)                           NOT NULL COMMENT '用户全名',
    phone            varchar(255)                           NOT NULL COMMENT '手机号',
    email            varchar(1024)                          NOT NULL COMMENT '邮箱',
    is_admin         tinyint(1)   default 1                 NOT NULL COMMENT '是否管理员 1--是',
    last_login_date  datetime                               NULL COMMENT '最后一次登录时间',
    update_last_time datetime     DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '最后一次修改时间',
    github_id        varchar(255) DEFAULT '' COMMENT 'github登陆账号id',
    gitee_id         varchar(255) DEFAULT '' COMMENT 'gitee登录帐号id',
    gitlab_id        varchar(255) DEFAULT '' COMMENT 'gitlab登陆账号id'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT '用户信息表';