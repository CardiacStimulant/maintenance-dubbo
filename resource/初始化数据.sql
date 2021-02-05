-- 初始化用户
INSERT INTO `base_user` (`login_account`, `password`, `job_number`, `name`, `mobile`, `email`, `comments`, `version`, `dr`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('admin', 'ISMvKXpXpadDiUoOSoAfww==', '0001', '管理员', NULL, NULL, '默认生成的管理员', '0', '0', '管理员', now(), '管理员', now());

-- 初始化角色
INSERT INTO `base_role` (`name`, `code`, `comments`, `version`, `dr`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('管理员', 'admin', '默认生成的管理员', '0', '0', '管理员', now(), '管理员', now());

-- 初始化用户-角色关系
INSERT INTO `base_user_role` (`role_id`, `user_id`, `dr`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '1', '0', '管理员', now(), '管理员', now());

-- 初始化资源
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('用户管理', 'menu', 'system', 'userManager', '/fe/user#/', '用户管理菜单', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('角色管理', 'menu', 'system', 'roleManager', '/fe/role#/', '角色管理菜单', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('资源管理', 'menu', 'system', 'resourceManager', '/fe/resource#/', '资源管理描述', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('日志查询', 'menu', 'system', 'operationLog', '/fe/log#/', '日志查询菜单', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('新增', 'button', 'system', 'add', NULL, '新增按钮', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('编辑', 'button', 'system', 'edit', NULL, '编辑按钮', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('删除', 'button', 'system', 'delete', NULL, '删除按钮', '0', '管理员', now(), '管理员', now());
INSERT INTO `base_resource` (`name`, `type`, `owner`, `key`, `url`, `comments`, `version`,  `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('配置', 'button', 'system', 'roleConfig', NULL, '角色配置资源按钮', '0', '管理员', now(), '管理员', now());

-- 初始化资源-角色关系
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '1', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '2', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '3', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '4', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '5', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '6', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '7', '管理员', now(), '管理员', now());
INSERT INTO `base_role_resource` (`role_id`, `resource_id`, `last_modify_user`, `last_modified`, `create_user`, `create_time`) VALUES ('1', '8', '管理员', now(), '管理员', now());
