package com.maintenance.web.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseDrEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1381624733414955159L;
    protected Integer dr;
}
