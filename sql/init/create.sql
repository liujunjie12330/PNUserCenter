DROP TABLE IF EXISTS `pn_permission`;
CREATE TABLE `pn_permission`
(
    `id`         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_at`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
    `create_by`  bigint                                                        NOT NULL DEFAULT -1 COMMENT '创建人',
    `update_by`  bigint                                                        NULL     DEFAULT -1 COMMENT '更新人',
    `is_deleted` tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除1--删除',
    `code`       varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '权限CODE',
    `name`       varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '权限名',
    `display`    varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '展示名称',
    `status`     int                                                           NOT NULL DEFAULT 0 COMMENT '权限状态 0--可授权,1--禁止授权',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '权限表'
  ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `pn_role`;
CREATE TABLE `pn_role`
(
    `id`         bigint                                                        NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_at`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
    `create_by`  bigint                                                        NOT NULL DEFAULT -1 COMMENT '创建人',
    `update_by`  bigint                                                        NOT NULL DEFAULT -1 COMMENT '更新人',
    `is_deleted` tinyint(1)                                                    NOT NULL DEFAULT 0 COMMENT '是否删除1--删除',
    `role_name`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
    `role_value` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT 'role_value-->admin-->000',
    `role_desc`  varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色描述',
    `role_type`  int                                                           NULL     DEFAULT NULL COMMENT '角色类型',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `pn_role_permission`;
CREATE TABLE `pn_role_permission`
(
    `id`            bigint     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_at`     datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`     datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
    `create_by`     bigint     NULL     DEFAULT -1 COMMENT '创建人',
    `update_by`     int        NOT NULL DEFAULT -1 COMMENT '更新人',
    `is_deleted`    tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除1--删除',
    `role_id`       bigint     NOT NULL COMMENT '关联角色id',
    `permission_id` bigint     NOT NULL COMMENT '关联权限id',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色权限关联表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `pn_role_user`;
CREATE TABLE `pn_role_user`
(
    `id`         bigint     NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_at`  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`  datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
    `create_by`  bigint     NOT NULL DEFAULT -1 COMMENT '创建人',
    `update_by`  bigint     NULL     DEFAULT -1 COMMENT '更新人',
    `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除1--删除',
    `role_id`    bigint     NOT NULL COMMENT '角色id',
    `user_id`    bigint     NOT NULL COMMENT '用户id',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_userId_roleId` (`role_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '角色用户关联表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `pn_user`;
CREATE TABLE `pn_user`
(
    `id`               bigint                                                   NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_at`        datetime                                                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_at`        datetime                                                 NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近修改时间',
    `create_by`        bigint                                                   NOT NULL DEFAULT -1 COMMENT '创建人',
    `update_by`        bigint                                                   NOT NULL DEFAULT -1 COMMENT '更新人',
    `is_deleted`       tinyint(1)                                               NOT NULL DEFAULT 0 COMMENT '是否删除1--删除',
    `username`         varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户名',
    `password`         varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户密码',
    `full_name`        varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '用户全名',
    `phone`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT NULL COMMENT '手机号',
    `email`            varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '邮箱',
    `is_admin`         tinyint(1)                                               NOT NULL DEFAULT 0 COMMENT '是否管理员 1--是',
    `last_login_date`  datetime                                                 NULL     DEFAULT NULL COMMENT '最后一次登录时间',
    `update_last_time` datetime                                                 NULL     DEFAULT NULL COMMENT '最后一次修改时间',
    `avatar`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL     DEFAULT '' COMMENT '头像地址',
    PRIMARY KEY (`id`) USING BTREE,
    INDEX `idx_username_password` (`username` ASC, `password` ASC, `full_name` ASC) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '用户信息表'
  ROW_FORMAT = Dynamic;

DROP TABLE IF EXISTS `pn_user_oauth`;
CREATE TABLE `pn_user_oauth`
(
    `id`                bigint                                                         NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `update_at`         datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_at`         datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `create_by`         bigint                                                         NOT NULL DEFAULT 0 COMMENT '修改人',
    `update_by`         bigint                                                         NOT NULL DEFAULT 0 COMMENT '更新人',
    `is_deleted`        tinyint(1)                                                     NOT NULL DEFAULT 0 COMMENT '是否删除',
    `user_id`           bigint                                                         NOT NULL DEFAULT 0 COMMENT '关联用户表(pn_user)',
    `source`            varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci   NULL     DEFAULT NULL COMMENT '第三方登录方式(github,gitee..)',
    `uuid`              varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '三方平台用户id',
    `platform_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci  NULL     DEFAULT NULL COMMENT '三方平台用户名',
    `avatar_url`        varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL     DEFAULT NULL COMMENT '用户头像url',
    `authorized_at`     datetime                                                       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户授权时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT = '第三方登陆信息表'
  ROW_FORMAT = Dynamic;

