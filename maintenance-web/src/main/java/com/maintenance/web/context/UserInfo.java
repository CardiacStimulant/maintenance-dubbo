package com.maintenance.web.context;

import com.google.common.base.Strings;
import com.maintenance.common.dto.ResourceDto;
import com.maintenance.common.dto.RoleDto;
import com.maintenance.common.dto.UserDto;
import com.maintenance.common.exception.UserInfoException;
import com.maintenance.web.utils.HttpRequestUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 当前登录用户信息
 */
@Slf4j
@Data
public class UserInfo {
    private static Map<String, UserInfo> userInfoCache = new HashMap<>();

    //用户信息
    private UserDto user;

    //用户角色集合
    private List<RoleDto> roleList;

    // 角色资源集合
    private List<ResourceDto> resourceList;

    private UserInfo() {
    }

    /**
     * 设置对象
     *
     * @return
     */
    public static void setUserInfoCache(String sessionId, UserDto user, List<RoleDto> roleList, List<ResourceDto> resourceList) {
        if(Strings.isNullOrEmpty(sessionId)) {
            log.error("设置用户对象失败，sessionId为空");
            throw new UserInfoException("设置用户对象失败，登录信息未获取到");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(user);
        userInfo.setRoleList(roleList);
        userInfo.setResourceList(resourceList);
        UserInfo.userInfoCache.put(sessionId, userInfo);
    }

    /**
     * 设置对象
     *
     * @return
     */
    public static void setUserInfoCache(String sessionId, UserInfo userInfo) {
        if(!Strings.isNullOrEmpty(sessionId)) {
            log.error("设置用户对象失败，sessionId为空");
            throw new UserInfoException("设置用户对象失败，登录信息未获取到");
        }
        UserInfo.userInfoCache.put(sessionId, userInfo);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return
     */
    public static UserInfo getInstance() {
        HttpSession httpSession = HttpRequestUtil.getSession();
        if(httpSession!=null && !Strings.isNullOrEmpty(httpSession.getId())){
            UserInfo userInfo = userInfoCache.get(httpSession.getId());
            if(userInfo != null){
                //通过本地内存快速返回userInfo，除了首次登录外其他时候调用都应该走到这里
                return userInfo;
            }else{
                return new UserInfo();
            }
        } else {
            log.error("获取当前登录用户信息失败，获取session为空");
            throw new UserInfoException("未查询到当前登录用户信息");
        }
    }
}
