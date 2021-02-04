package com.maintenance.web.context;

import com.maintenance.web.context.response.ResponseResultInterceptor;
import com.maintenance.web.context.xss.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liangxc
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    /**
     * 拦截器规则
     * ResponseResultInterceptor：返回对象包装拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new ResponseResultInterceptor()).addPathPatterns("/**");
    }

    /**
     * xssFilter
     * @return
     */
    @Bean
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        XssFilter xssFilter = new XssFilter();
        filterRegistrationBean.setFilter(xssFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
