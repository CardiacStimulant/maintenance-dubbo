package com.maintenance.web.login.context.shiro;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;

/**
 * shiro配置
 */
@Configuration
public class ShiroConfig {

	@Bean
    ShiroRealm shiroRealm() {
		return new ShiroRealm();
	}
	@Bean
	DefaultWebSecurityManager securityManager() {
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(shiroRealm());
		return manager;
	}

	/**
	 * pom.xml引入原始shiro-spring.jar方式，相当于把原理xml方式配置改成了JAVA方式
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("/login");
		shiroFilterFactoryBean.setSuccessUrl("/index");
		shiroFilterFactoryBean.setUnauthorizedUrl("/login");

		LinkedHashMap<String, String> definitionMap = new LinkedHashMap<>();

		definitionMap.put("/css/**", "anon");
		definitionMap.put("/js/**", "anon");
		definitionMap.put("/fonts/**", "anon");
		definitionMap.put("/img/**", "anon");
		definitionMap.put("/druid/**", "anon");
		definitionMap.put("/login", "authc");
		definitionMap.put("/logout", "logout");
		definitionMap.put("/", "anon");
		definitionMap.put("/**", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(definitionMap);

		return shiroFilterFactoryBean;
	}
}
