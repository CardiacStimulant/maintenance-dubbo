package com.maintenance.web.manage.dao;

import com.github.pagehelper.Page;
import com.maintenance.web.manage.entity.User;
import com.maintenance.web.manage.entity.UserRole;
import com.maintenance.web.manage.vo.UserRoleVo;
import com.maintenance.web.mvc.GenericMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserManagerDao extends GenericMapper<User> {
    User getUserByLoginAccount(@Param("loginAccount") String loginAccount);

    Page<UserRoleVo> queryPage(@Param("condition") Map<String, Object> condition);

    User getUserByAccount(@Param("account") String account);

    int checkUserLoginAccountExists(@Param("loginAccount") String loginAccount);

    int addUserRoleRelation(UserRole userRole);

    List<UserRole> queryUserRoleList(@Param("userId") Long userId);

    int deleteUserRoleByUserId(@Param("userId") Long userId);

    int deleteUserRoleByUserIdAndRoleIds(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    int updatePassword(@Param("id") Long id, @Param("version") Integer version, @Param("password") String password);
}
