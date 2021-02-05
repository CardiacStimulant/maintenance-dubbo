-- 用户表
CREATE TABLE `base_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_account` varchar(32) DEFAULT NULL COMMENT '登录账号',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `job_number` varchar(16) DEFAULT NULL COMMENT '工号',
  `name` varchar(32) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(16) DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) DEFAULT NULL COMMENT '邮箱',
  `comments` varchar(128) DEFAULT NULL COMMENT '描述',
  `version` int(5) DEFAULT 0 COMMENT '版本号',
  `dr` int(1) DEFAULT 0,
  `last_modify_user` varchar(32) DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 角色表
CREATE TABLE `base_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  `code` varchar(32) DEFAULT NULL COMMENT '角色编码',
  `comments` varchar(128) DEFAULT NULL COMMENT '描述',
  `version` int(5) DEFAULT 0 COMMENT '版本号',
  `dr` int(1) DEFAULT 0,
  `last_modify_user` varchar(32) DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- 用户-角色关系表
CREATE TABLE `base_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `last_modify_user` varchar(32) DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户-角色关系表';

-- 资源表
CREATE TABLE `base_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) DEFAULT NULL COMMENT '资源名称',
  `type` varchar(32) DEFAULT NULL COMMENT '资源类型（menu，button）',
  `owner` varchar(32) DEFAULT NULL COMMENT '资源归属（system：系统管理，maintenance：运维管理）',
  `key` varchar(32) DEFAULT NULL COMMENT '资源KEY（作为唯一标识）',
  `url` varchar(128) DEFAULT NULL COMMENT '资源路径',
  `comments` varchar(128) DEFAULT NULL COMMENT '描述',
  `version` int(5) DEFAULT 0 COMMENT '版本号',
  `dr` int(1) DEFAULT 0,
  `last_modify_user` varchar(32) DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='资源表';

-- 角色-资源关系表
CREATE TABLE `base_role_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `resource_id` int(11) NOT NULL COMMENT '资源ID',
  `last_modify_user` varchar(32) DEFAULT NULL,
  `last_modified` datetime DEFAULT NULL,
  `create_user` varchar(32) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色-资源关系表';

-- 操作日志表
CREATE TABLE `base_operation_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增id',
  `business_id` int(11) DEFAULT NULL COMMENT '业务id',
  `business_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '业务类型（user：用户，role：角色，userRoleRelation：用户角色关系）',
  `operation_type` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作类型（add：新增，update：修改，delete：删除）',
  `comments` varchar(256) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '备注',
  `last_modify_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '修改人',
  `last_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user` varchar(32) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL COMMENT '操作人',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='操作日志表';