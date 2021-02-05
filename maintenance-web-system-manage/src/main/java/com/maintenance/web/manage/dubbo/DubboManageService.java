package com.maintenance.web.manage.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.maintenance.common.dto.ResourceDto;
import com.maintenance.common.dto.RoleDto;
import com.maintenance.common.dto.UserDto;
import com.maintenance.web.dubbo.IDubboManageService;
import com.maintenance.web.exception.DubboManageException;
import com.maintenance.web.manage.dao.UserManagerDao;
import com.maintenance.web.manage.entity.Resource;
import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.manage.entity.User;
import com.maintenance.web.manage.service.ResourceService;
import com.maintenance.web.manage.service.RoleService;
import com.maintenance.web.manage.utils.ConversionDtoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对外提供的dubbo接口
 */
@Slf4j
@DubboService(version = "${dubbo.service.version}", interfaceClass = IDubboManageService.class, timeout = 30000)
public class DubboManageService implements IDubboManageService {
    @Autowired
    private UserManagerDao userManagerDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    /**
     * 根据当前用户名，查询UserInfo信息
     * @param userAccount
     * @return
     * @throws DubboManageException
     */
    @Override
    public JSONObject getUserInfoByUserAccount(String userAccount) throws DubboManageException {
        // 查询用户信息
        User user = userManagerDao.getUserByAccount(userAccount);
        if (user == null) {
            log.error("根据用户名未查询到用户信息，参数：userAccount=" + userAccount);
            throw new DubboManageException("未查询到用户信息");
        }
        /* 查询用户角色信息 */
        Map<String, Object> condition = new HashMap<>();
        condition.put("userId", user.getId());
        List<Role> roles = roleService.queryList(condition);
        /* 查询角色资源权限 */
        condition.put("roleList", roles);
        List<Resource> resources = resourceService.queryList(condition);

        // 转换为DTO
        // 转换user
        UserDto userDto = new UserDto();
        ConversionDtoUtil.userVo2UserDto(user, userDto);
        // 转换role
        List<RoleDto> roleDtos = new ArrayList<>();
        for(Role role : roles) {
            RoleDto roleDto = new RoleDto();
            ConversionDtoUtil.roleVo2RoleDto(role, roleDto);
            roleDtos.add(roleDto);
        }
        // 转换资源
        List<ResourceDto> resourceDtos = new ArrayList<>();
        for(Resource resource : resources) {
            ResourceDto resourceDto = new ResourceDto();
            ConversionDtoUtil.resourceVo2ResourceDto(resource, resourceDto);
            resourceDtos.add(resourceDto);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userDto", userDto);
        jsonObject.put("roleDtos", roleDtos);
        jsonObject.put("resourceDtos", resourceDtos);
        return jsonObject;
    }
}
