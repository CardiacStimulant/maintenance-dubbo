package com.maintenance.web.entity;

import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 2061393818430184504L;
    protected String createTime;
    protected String createUser;
    protected String lastModified;
    protected String lastModifyUser;

    private String getCurrentDateTime(){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 填充实体的创建时间和修改时间字段
     */
    public void setCreateAndModifyFields(String userName){
        this.setCreateTime(getCurrentDateTime());
        this.setCreateUser(userName);
        this.setLastModified(getCurrentDateTime());
        this.setLastModifyUser(userName);
    }

    /**
     * 填充实体的创建时间和修改时间字段
     */
    public void setCreateAndModifyFields(String userName, String dateTime){
        this.setCreateTime(dateTime);
        this.setCreateUser(userName);
        this.setLastModified(dateTime);
        this.setLastModifyUser(userName);
    }

    /**
     * 填充实体的修改时间字段
     */
    public void setModifyFields(String userName){
        this.setLastModified(getCurrentDateTime());
        this.setLastModifyUser(userName);
    }

    /**
     * 填充实体的修改时间字段
     */
    public void setModifyFields(String userName, String dateTime){
        this.setLastModified(dateTime);
        this.setLastModifyUser(userName);
    }

    /**
     * 填充实体的创建时间字段
     */
    public void setCreateFields(String userName){
        this.setCreateTime(getCurrentDateTime());
        this.setCreateUser(userName);
    }

    /**
     * 填充实体的创建时间字段
     */
    public void setCreateFields(String userName, String dateTime){
        this.setCreateTime(dateTime);
        this.setCreateUser(userName);
    }
}
