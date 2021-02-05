package com.maintenance.web.manage.entity;

import com.maintenance.web.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class RoleResource extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -4305729951607404568L;
    private Long id;
    private Long roleId;
    private Long resourceId;
}
