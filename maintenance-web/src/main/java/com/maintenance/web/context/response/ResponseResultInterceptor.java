package com.maintenance.web.context.response;

import com.maintenance.web.mvc.annotation.BaseControllerAnnotation;
import com.maintenance.web.mvc.annotation.ResponseResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class ResponseResultInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(handler instanceof HandlerMethod) {
            /* 反射获取类和方法 */
            final HandlerMethod handlerMethod = (HandlerMethod) handler;
            final Class<?> clazz = handlerMethod.getBeanType();
            final Method method = handlerMethod.getMethod();
            /* 校验类的注解和方法的注解，满足条件后，添加到attribute */
            if(clazz.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(ResponseConstant.RESPONSE_RESULT_ANNOTATION, clazz.getAnnotation(ResponseResult.class));
            } else if(method.isAnnotationPresent(ResponseResult.class)) {
                request.setAttribute(ResponseConstant.RESPONSE_RESULT_ANNOTATION, method.getAnnotation(ResponseResult.class));
            } else if(clazz.isAnnotationPresent(BaseControllerAnnotation.class)) {
                request.setAttribute(ResponseConstant.RESPONSE_RESULT_ANNOTATION, clazz.getAnnotation(BaseControllerAnnotation.class));
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
