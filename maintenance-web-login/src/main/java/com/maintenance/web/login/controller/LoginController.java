package com.maintenance.web.login.controller;

import com.maintenance.common.utils.Encrypt;
import com.maintenance.web.mvc.annotation.BaseControllerAnnotation;
import com.maintenance.web.context.Result;
import com.maintenance.web.context.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@BaseControllerAnnotation
public class LoginController {

	/**
	 * 登录
	 * @param loginAccount
	 * @param password
	 * @return
	 */
    @PostMapping("/login")
	public Object login(@RequestParam("loginAccount") String loginAccount, @RequestParam("password") String password) {
		Result<Object> result;
		try {
			password = Encrypt.md5Encrypt(password);
			UsernamePasswordToken token = new UsernamePasswordToken(loginAccount, password);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			result = Result.success("登录成功");
		} catch (UnknownAccountException e) {
			result = Result.failure("用户不存在");
		} catch (IncorrectCredentialsException e) {
			result = Result.failure("密码错误");
		} catch (LockedAccountException e) {
			result = Result.failure("用户被禁用，请联系管理员");
		} catch (AuthenticationException e) {
			result = Result.failure("认证失败");
		} catch (Exception e) {
			result = Result.failure("登录失败，请联系管理员");
		}
		return result;
	}

	/**
	 * 获取当前登录用户信息
	 * @return
	 */
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public Object getUserInfo() {
		return UserInfo.getInstance();
	}

    /**
     * 未登录或登录失败后跳转到登录页
     */
    @GetMapping("/login")
	public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "未登录");
	}
}
