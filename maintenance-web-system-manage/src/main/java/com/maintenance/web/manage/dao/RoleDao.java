package com.maintenance.web.manage.dao;

import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.mvc.GenericMapper;
import com.maintenance.web.mvc.annotation.BaseDaoAnnotation;
import org.apache.ibatis.annotations.Param;

@BaseDaoAnnotation
public interface RoleDao extends GenericMapper<Role> {
    int checkRoleCodeExists(@Param("code") String code);
}
