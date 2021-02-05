package com.maintenance.web.manage.vo;

import com.maintenance.web.manage.entity.Resource;
import com.maintenance.web.manage.entity.Role;

import java.io.Serializable;
import java.util.List;

public class RoleResourceVo extends Role implements Serializable {
    private static final long serialVersionUID = -66144370839398442L;

    private List<Resource> resourceList;

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public String toString() {
        return "RoleResourceVo{" +
                super.toString() +
                "resourceList=" + resourceList +
                '}';
    }

    public Role generateRole() {
        Role role = new Role();
        role.setId(this.getId());
        role.setName(this.getName());
        role.setCode(this.getCode());
        role.setComments(this.getComments());
        role.setVersion(this.getVersion());
        role.setCreateTime(this.getCreateTime());
        role.setCreateUser(this.getCreateUser());
        role.setLastModified(this.getLastModified());
        role.setLastModifyUser(this.getLastModifyUser());
        role.setDr(this.getDr());
        return role;
    }

    public void initializationRole(Role role) {
        this.setId(role.getId());
        this.setName(role.getName());
        this.setCode(role.getCode());
        this.setComments(role.getComments());
        this.setVersion(role.getVersion());
        this.setCreateTime(role.getCreateTime());
        this.setCreateUser(role.getCreateUser());
        this.setLastModified(role.getLastModified());
        this.setLastModifyUser(role.getLastModifyUser());
        this.setDr(role.getDr());
    }
}
