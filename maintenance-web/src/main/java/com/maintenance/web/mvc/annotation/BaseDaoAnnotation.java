package com.maintenance.web.mvc.annotation;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Mapper
@Repository
public @interface BaseDaoAnnotation {
}
