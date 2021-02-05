package com.maintenance.web.manage.entity;

import com.maintenance.web.entity.BaseDrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class User extends BaseDrEntity implements Serializable {
    private static final long serialVersionUID = 7559254947665253702L;
    private Long id;
    private String loginAccount;
    private String password;
    private String jobNumber;
    private String name;
    private String mobile;
    private String email;
    private String comments;
    private Integer version;
}
