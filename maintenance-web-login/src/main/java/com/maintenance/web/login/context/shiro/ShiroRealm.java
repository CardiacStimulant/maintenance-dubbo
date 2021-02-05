package com.maintenance.web.login.context.shiro;

import com.alibaba.fastjson.JSONObject;
import com.maintenance.common.dto.ResourceDto;
import com.maintenance.common.dto.RoleDto;
import com.maintenance.common.dto.UserDto;
import com.maintenance.web.context.UserInfo;
import com.maintenance.web.dubbo.IDubboManageService;
import com.maintenance.web.utils.HttpRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
public class ShiroRealm extends AuthorizingRealm {
	@DubboReference(version = "${dubbo.service.version}")
	private IDubboManageService iDubboManageService;

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
			JSONObject jsonObject = iDubboManageService.getUserInfoByUserAccount(userName);
			if(jsonObject==null) {
				log.error("调用dubbo接口成功，返回数据错误，返回对象为空");
				throw new UnknownAccountException("用户名或密码错误！");
			}
			// 获取UserDto
			UserDto userDto = jsonObject.getObject("userDto", UserDto.class);
			if (userDto==null) {
				log.error("调用dubbo接口成功，返回数据错误，返回userDto为空");
				throw new UnknownAccountException("用户名或密码错误！");
			}
			if (!password.equals(userDto.getPassword())) {
				log.error("密码校验失败，password=" + password + "，dtoPassword=" + userDto.getPassword());
				throw new IncorrectCredentialsException("用户名或密码错误！");
			}
			// 获取roleDtos
			List<RoleDto> roleDtos = (List<RoleDto>) jsonObject.get("roleDtos");
			// 获取resourceDtos
			List<ResourceDto> resourceDtos = (List<ResourceDto>) jsonObject.get("resourceDtos");
			// 设置用户信息
			UserInfo.setUserInfoCache(httpSession.getId(), userDto, roleDtos, resourceDtos);
			return new SimpleAuthenticationInfo(userDto, password, getName());
		} else {
			log.error("shiro登录认证失败，会话为空");
			throw new AuthenticationException("登录失败，会话为空");
		}
	}

}
