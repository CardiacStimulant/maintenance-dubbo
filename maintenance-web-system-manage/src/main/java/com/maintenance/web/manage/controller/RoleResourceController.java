package com.maintenance.web.manage.controller;

import com.maintenance.web.manage.entity.RoleResource;
import com.maintenance.web.manage.service.RoleResourceService;
import com.maintenance.web.mvc.annotation.BaseControllerAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@BaseControllerAnnotation
@RequestMapping("roleResource")
public class RoleResourceController {
    @Autowired
    private RoleResourceService roleResourceService;

    /**
     * 角色添加全部资源（添加角色-资源关系）
     * @param roleId    角色ID
     * @return
     */
    @RequestMapping(value = "/addAllRoleResource", method = RequestMethod.GET)
    public Object addAllRoleResource(@RequestParam("roleId") Long roleId) {
        return this.roleResourceService.addAllRoleResource(roleId);
    }

    /**
     * 角色添加资源
     * @param roleResources    角色ID
     * @return
     */
    @RequestMapping(value = "/addRoleResources", method = RequestMethod.POST)
    public Object addRoleResources(@RequestBody List<RoleResource> roleResources) {
        this.roleResourceService.addRoleResources(roleResources);
        return roleResources;
    }

    /**
     * 角色移除全部资源（添加角色-资源关系）
     * @param roleId    角色ID
     * @return
     */
    @RequestMapping(value = "/removeAllRoleResource", method = RequestMethod.GET)
    public Object removeAllRoleResource(@RequestParam("roleId") Long roleId) {
        this.roleResourceService.removeAllRoleResource(roleId);
        return null;
    }

    /**
     * 角色移除资源
     * @param roleResources    角色ID
     * @return
     */
    @RequestMapping(value = "/removeRoleResources", method = RequestMethod.POST)
    public Object removeRoleResources(@RequestBody List<RoleResource> roleResources) {
        this.roleResourceService.removeRoleResources(roleResources);
        return null;
    }
}
