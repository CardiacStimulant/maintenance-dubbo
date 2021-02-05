package com.maintenance.web.manage.utils;

import com.maintenance.common.dto.ResourceDto;
import com.maintenance.common.dto.RoleDto;
import com.maintenance.common.dto.UserDto;
import com.maintenance.web.manage.entity.Resource;
import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.manage.entity.User;

public class ConversionDtoUtil {
    /**
     * user to userDto
     * @param user
     * @param userDto
     */
    public static void userVo2UserDto(User user, UserDto userDto) {
        userDto.setLoginAccount(user.getLoginAccount());
        userDto.setName(user.getName());
        userDto.setPassword(user.getPassword());
        userDto.setMobile(user.getMobile());
        userDto.setEmail(user.getEmail());
        userDto.setComments(user.getComments());
    }

    /**
     * role to roleDto
     * @param role
     * @param roleDto
     */
    public static void roleVo2RoleDto(Role role, RoleDto roleDto) {
        roleDto.setName(role.getName());
        roleDto.setCode(role.getCode());
        roleDto.setComments(role.getComments());
    }

    /**
     * resource to resourceDto
     * @param resource
     * @param resourceDto
     */
    public static void resourceVo2ResourceDto(Resource resource, ResourceDto resourceDto) {
        resourceDto.setName(resource.getName());
        resourceDto.setType(resource.getType());
        resourceDto.setOwner(resource.getOwner());
        resourceDto.setUrl(resource.getUrl());
        resourceDto.setKey(resource.getKey());
        resourceDto.setComments(resource.getComments());
    }
}
