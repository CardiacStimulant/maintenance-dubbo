package com.maintenance.web.manage.entity;

import com.maintenance.web.entity.BaseDrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class Role extends BaseDrEntity implements Serializable {
    private static final long serialVersionUID = 7344534995410472230L;
    private Long id;
    private String name;
    private String code;
    private String comments;
    private Integer version;
}
