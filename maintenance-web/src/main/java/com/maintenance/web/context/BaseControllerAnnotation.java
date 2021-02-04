package com.maintenance.web.context;

import com.maintenance.web.context.response.ResponseResult;
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
