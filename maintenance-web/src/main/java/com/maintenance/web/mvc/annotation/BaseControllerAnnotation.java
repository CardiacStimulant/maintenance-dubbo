package com.maintenance.web.mvc.annotation;

import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@RestController
@ResponseResult
public @interface BaseControllerAnnotation {
}
