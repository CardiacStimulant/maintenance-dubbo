package com.maintenance.web.manage.controller;

import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.manage.service.RoleService;
import com.maintenance.web.mvc.annotation.BaseControllerAnnotation;
import com.maintenance.web.utils.SearchParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@BaseControllerAnnotation
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查询角色管理分页数据
     * @param searchMap 查询条件，分页条件必传，pageNum：页码，pageSize：页大小
     * @return 角色管理分页数据
     */
    @RequestMapping(value = "/queryPage", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryPage(@RequestParam Map<String, Object> searchMap) {
        // 创建时间的查询条件
        SearchParamsUtil.parseTimeGroup(searchMap, "createTimeGroup", "createTimeList");
        // 更新时间的查询条件
        SearchParamsUtil.parseTimeGroup(searchMap, "lastModifiedGroup", "modifyTimeList");
        return this.roleService.queryPage(searchMap);
    }

    /**
     * 查询所有角色信息
     * @param searchMap 查询条件
     * @return 角色分页数据
     */
    @RequestMapping(value = "/queryList", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryList(@RequestParam Map<String, Object> searchMap) {
        return this.roleService.queryList(searchMap);
    }

    /**
     * 新增角色信息
     * @param role    角色信息
     * @return 角色信息
     */
    @RequestMapping(value = "/addRole", method = RequestMethod.POST)
    public Object addRole(@RequestBody Role role) {
        return this.roleService.addRole(role);
    }

    /**
     * 修改角色信息
     * @param role    角色信息
     * @return 角色信息
     */
    @RequestMapping(value = "/updateRole", method = RequestMethod.POST)
    public Object updateRole(@RequestBody Role role) {
        return this.roleService.updateRole(role);
    }

    /**
     * 批量删除角色信息
     * @param roles    角色信息
     * @return 角色信息
     */
    @RequestMapping(value = "/batchDeleteRole", method = RequestMethod.POST)
    public Object batchDeleteRole(@RequestBody List<Role> roles) {
        this.roleService.batchDeleteRole(roles);
        return null;
    }

    /**
     * 删除角色管理信息
     * @param role    角色信息
     * @return 角色管理信息
     */
    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public Object deleteRole(@RequestBody Role role) {
        this.roleService.deleteRole(role);
        return null;
    }
}
