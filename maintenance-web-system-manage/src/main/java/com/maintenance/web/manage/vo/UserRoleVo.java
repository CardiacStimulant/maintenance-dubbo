package com.maintenance.web.manage.vo;

import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.manage.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserRoleVo extends User implements Serializable {
    private static final long serialVersionUID = -66144370839398442L;

    private List<Role> roleList;

    public User generateUser() {
        User user = new User();
        user.setId(this.getId());
        user.setName(this.getName());
        user.setComments(this.getComments());
        user.setEmail(this.getEmail());
        user.setJobNumber(this.getJobNumber());
        user.setLoginAccount(this.getLoginAccount());
        user.setMobile(this.getMobile());
        user.setPassword(this.getPassword());
        user.setVersion(this.getVersion());
        user.setCreateTime(this.getCreateTime());
        user.setCreateUser(this.getCreateUser());
        user.setLastModified(this.getLastModified());
        user.setLastModifyUser(this.getLastModifyUser());
        user.setDr(this.getDr());
        return user;
    }

    public void initializationUser(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setComments(user.getComments());
        this.setEmail(user.getEmail());
        this.setJobNumber(user.getJobNumber());
        this.setLoginAccount(user.getLoginAccount());
        this.setMobile(user.getMobile());
        this.setPassword(user.getPassword());
        this.setVersion(user.getVersion());
        this.setCreateTime(user.getCreateTime());
        this.setCreateUser(user.getCreateUser());
        this.setLastModified(user.getLastModified());
        this.setLastModifyUser(user.getLastModifyUser());
        this.setDr(user.getDr());
    }
}
