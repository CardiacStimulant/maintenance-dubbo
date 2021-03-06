package com.maintenance.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDto implements Serializable {
    private static final long serialVersionUID = 5976137413755923481L;
    private String loginAccount;
    private String name;
    private String password;
    private String mobile;
    private String email;
    private String comments;
}
