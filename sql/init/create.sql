# 用户表
CREATE TABLE pn_user
(
    id               bigint AUTO_INCREMENT PRIMARY KEY,
    create_by        bigint        NOT NULL COMMENT '创建人',
    update_by        bigint        NOT NULL COMMENT '修改人',
    create_at        datetime      NOT NULL COMMENT '创建时间',
    update_at        datetime      NOT NULL COMMENT '修改时间',
    is_deleted       tinyint(1)    NOT NULL COMMENT '是否删除（0,1）',
    username         varchar(100)  NOT NULL COMMENT '用户名',
    password         varchar(256)  NOT NULL COMMENT '用户密码',
    full_name        varchar(128)  NOT NULL COMMENT '用户全名',
    phone            varchar(255)  NOT NULL COMMENT '手机号',
    email            varchar(1024) NOT NULL COMMENT '邮箱',
    is_admin         varchar(1)    NOT NULL COMMENT '是否管理员',
    last_login_date  datetime      NOT NULL COMMENT '最后一次登录时间',
    update_last_time datetime      NOT NULL COMMENT '最后一次修改时间'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8 COMMENT '用户信息表';