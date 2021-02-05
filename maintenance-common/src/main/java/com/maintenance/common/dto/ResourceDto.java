package com.maintenance.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceDto implements Serializable {
    private static final long serialVersionUID = -921021283777973456L;
    private String name;
    private String type;
    private String owner;
    private String url;
    private String key;
    private String comments;
}
