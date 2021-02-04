package com.maintenance.web.login.context.shiro;

import com.maintenance.common.dto.ResourceDto;
import com.maintenance.common.dto.RoleDto;
import com.maintenance.common.dto.UserDto;
import com.maintenance.web.DataBus;
import com.maintenance.web.context.UserInfo;
import com.maintenance.web.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
	@Autowired
	private DataBus dataBus;

	/**
	 * 获取用户角色和权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
		return new SimpleAuthorizationInfo();
	}

	/**
	 * 登录认证
	 * @return
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		HttpSession httpSession = HttpRequestUtil.getSession();
		if(httpSession!=null) {
			String userName = (String) token.getPrincipal();
			String password = new String((char[]) token.getCredentials());
			System.out.println("用户" + userName + "认证");
			// 查询用户信息
			UserDto user = dataBus.getUserByAccount(userName);
			if (user == null) {
				throw new UnknownAccountException("用户名或密码错误！");
			}
			if (!password.equals(user.getPassword())) {
				throw new IncorrectCredentialsException("用户名或密码错误！");
			}
			/* 查询用户角色信息 */
			Map<String, Object> condition = new HashMap<>();
			condition.put("userId", user.getId());
			List<RoleDto> roles = dataBus.queryList(condition);
			/* 查询角色资源权限 */
			condition.addCondition("roleList", roles);
			List<ResourceDto> resources = dataBus.queryList(condition);
			// 设置用户信息
			UserInfo.setUserInfoCache(httpSession.getId(), user, roles, resources);

			return new SimpleAuthenticationInfo(user, password, getName());
		} else {
			log.error("shiro登录认证失败，会话为空");
			throw new AuthenticationException("登录失败，会话为空");
		}
	}

}
