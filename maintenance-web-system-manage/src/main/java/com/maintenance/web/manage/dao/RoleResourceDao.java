package com.maintenance.web.manage.dao;

import com.maintenance.web.manage.entity.RoleResource;
import com.maintenance.web.mvc.GenericMapper;
import com.maintenance.web.mvc.annotation.BaseDaoAnnotation;
import org.apache.ibatis.annotations.Param;

@BaseDaoAnnotation
public interface RoleResourceDao extends GenericMapper<RoleResource> {
    /**
     * 校验角色-资源关系是否存在
     * @param roleResource
     * @return
     */
    int checkRoleResourceExists(RoleResource roleResource);

    /**
     * 移除角色-资源关系
     * @param roleResource
     * @return
     */
    int removeRoleResource(RoleResource roleResource);

    /**
     * 根据资源ID删除角色-资源关系
     * @param resourceId
     * @return
     */
    int removeRoleResourceByResourceId(@Param("resourceId") Long resourceId);
}
