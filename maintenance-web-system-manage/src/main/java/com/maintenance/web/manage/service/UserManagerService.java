package com.maintenance.web.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.maintenance.common.utils.Encrypt;
import com.maintenance.common.utils.PatternUtil;
import com.maintenance.web.context.UserInfo;
import com.maintenance.web.manage.constant.OperationLogBusinessTypeEnum;
import com.maintenance.web.manage.constant.OperationLogOperationTypeEnum;
import com.maintenance.web.manage.dao.UserManagerDao;
import com.maintenance.web.manage.entity.OperationLog;
import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.manage.entity.User;
import com.maintenance.web.manage.entity.UserRole;
import com.maintenance.web.manage.exception.UserManagerException;
import com.maintenance.web.manage.vo.UserRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class UserManagerService {
    @Autowired
    private UserManagerDao userManagerDao;

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查询用户管理分页数据
     * @param condition 查询条件，分页条件必传，pageNum：页码，pageSize：页大小
     * @return 用户管理分页数据
     * @throws UserManagerException 自定义异常
     */
    public PageInfo<UserRoleVo> queryPage(Map<String, Object> condition) throws UserManagerException {
        if(condition==null || condition.size()<=0) {
            log.error("查询用户管理失败，参数为空");
            throw new UserManagerException("查询用户管理失败，参数错误");
        }
        /* 分页参数 */
        Integer pageNum = MapUtils.getInteger(condition, "pageNum");
        Integer pageSize = MapUtils.getInteger(condition, "pageSize");
        if(pageNum==null || pageNum<=0) {
            log.error("查询用户管理失败，参数错误，pageNum为空");
            throw new UserManagerException("查询用户管理失败，参数错误");
        }
        if(pageSize==null || pageSize<=0) {
            log.error("查询用户管理失败，参数错误，pageSize为空");
            throw new UserManagerException("查询用户管理失败，参数错误");
        }
        try {
            PageHelper.startPage(pageNum, pageSize);
            Page<UserRoleVo> page = userManagerDao.queryPage(condition);
            return page.toPageInfo();
        } catch (Exception e) {
            log.error("查询用户管理分页异常，参数：" + condition, e);
            throw new UserManagerException("查询用户管理分页异常，请联系管理员");
        }
    }

    /**
     * 保存用户管理信息
     * @param userRoleVo    用户管理信息
     * @return 用户管理信息
     * @throws UserManagerException 自定义异常
     */
    @Transactional(rollbackFor=Exception.class)
    public UserRoleVo addUserManager(UserRoleVo userRoleVo) throws UserManagerException {
        if(userRoleVo==null) {
            log.error("保存用户信息失败，参数为空");
            throw new UserManagerException("保存用户信息失败，参数为空");
        }
        if(!Strings.isNullOrEmpty(userRoleVo.getName())) {
            log.error("保存用户信息失败，用户姓名为空");
            throw new UserManagerException("保存用户信息失败，用户姓名为空");
        }
        if(!Strings.isNullOrEmpty(userRoleVo.getLoginAccount())) {
            log.error("保存用户信息失败，用户登录账号为空");
            throw new UserManagerException("保存用户信息失败，用户登录账号为空");
        }
        if(Strings.isNullOrEmpty(userRoleVo.getEmail()) && !PatternUtil.isEmail(userRoleVo.getEmail())) {
            log.error("保存用户信息失败，邮箱校验失败");
            throw new UserManagerException("保存用户信息失败，邮箱格式错误");
        }
        if(Strings.isNullOrEmpty(userRoleVo.getMobile()) && !PatternUtil.isMobileOrPhone(userRoleVo.getMobile())) {
            log.error("保存用户信息失败，电话号码校验失败");
            throw new UserManagerException("保存用户信息失败，电话号码格式错误");
        }
        try {
            /* 新增用户信息 */
            User user = userRoleVo.generateUser();
            int addUser = this.addUser(user);
            if(addUser==1) {
                // 添加操作日志
                operationLogService.saveByThread(new OperationLog(user.getId(),
                        OperationLogBusinessTypeEnum.USER.getKey(),
                        OperationLogOperationTypeEnum.ADD.getKey(),
                        UserInfo.getInstance().getUser().getName() + "新增用户：" + user.getName() + "（" + user.getLoginAccount() + "）",
                        UserInfo.getInstance()));
                /* 新增角色信息 */
                this.addUserRoleRelation(user, userRoleVo.getRoleList());
            } else {
                log.error("新增用户信息失败，返回数量为0，参数：" + userRoleVo);
                throw new UserManagerException("新增用户信息失败，请联系管理员");
            }
            userRoleVo.initializationUser(user);
        } catch (UserManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("保存用户信息异常，参数：" + userRoleVo, e);
            throw new UserManagerException("保存用户信息异常，请联系管理员");
        }
        return userRoleVo;
    }

    /**
     * 新增用户信息
     * @param user
     * @return
     */
    private int addUser(User user) {
        // 校验用户登录账号是否重复
        boolean isExists = this.checkUserLoginAccountExists(user.getLoginAccount());
        if(isExists) {
            throw new UserManagerException("新增用户信息失败，登录账号重复");
        } else {
            // 设置默认密码
            if(!Strings.isNullOrEmpty(user.getPassword())) {
                user.setPassword(Encrypt.md5Encrypt("123456"));
            }
            // 设置创建信息和修改信息
            user.setCreateAndModifyFields(UserInfo.getInstance().getUser().getName());
            return this.userManagerDao.insert(user);
        }
    }

    /**
     * 校验用户登录账号是否存在
     * @param loginAccount  登录账号
     * @return
     */
    private boolean checkUserLoginAccountExists(String loginAccount) {
        return this.userManagerDao.checkUserLoginAccountExists(loginAccount)>0;
    }

    /**
     * 新增用户和角色关系
     * @param user 用户信息
     * @param roleList  角色信息
     * @return
     */
    private int addUserRoleRelation(User user, List<Role> roleList) {
        int success = 0;
        if(roleList!=null && roleList.size()>0) {
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            for(Role role : roleList) {
                userRole.setRoleId(role.getId());
                userRole.setCreateAndModifyFields(UserInfo.getInstance().getUser().getName());
                int addRelation = this.userManagerDao.addUserRoleRelation(userRole);
                if(addRelation==1) {
                    success += addRelation;
                    // 添加操作日志
//                    operationLogService.save(new OperationLog(userRole.getId(),
//                            OperationLogBusinessTypeEnum.USER_ROLE_RELATION.getKey(),
//                            OperationLogOperationTypeEnum.ADD.getKey(),
//                            UserInfo.getInstance().getUser().getName() + "新增用户角色关系，用户：" + user.getName() + "（" + user.getLoginAccount() + "）" + "添加角色：" + role.getName(),
//                            UserInfo.getInstance()));
                } else {
                    log.error("添加用户角色失败，返回数量为0");
                }
            }
        }
        return success;
    }

    /**
     * 刪除用户管理信息
     * @param userRoleVos  用户角色信息
     * @return
     */
    public void batchDeleteUserManager(List<UserRoleVo> userRoleVos) throws UserManagerException {
        if(userRoleVos==null || userRoleVos.size()<=0) {
            log.error("删除用户失败，参数为空");
            throw new UserManagerException("删除用户失败，参数为空");
        }
        try {
            for(UserRoleVo userRoleVo : userRoleVos) {
                this.deleteUserManager(userRoleVo);
            }
        } catch (UserManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除用户管理异常，参数：" + userRoleVos, e);
            throw new UserManagerException("删除用户管理异常，请联系管理员");
        }
    }

    /**
     * 刪除用户管理信息
     * @param userRoleVo  用户角色信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserManager(UserRoleVo userRoleVo) throws UserManagerException {
        if(userRoleVo==null) {
            log.error("删除用户失败，参数为空");
            throw new UserManagerException("删除用户失败，参数为空");
        }
        if(userRoleVo.getId()==null || userRoleVo.getId()<=0) {
            log.error("删除用户失败，用户ID为空");
            throw new UserManagerException("删除用户失败，用户ID为空");
        }
        if(userRoleVo.getVersion()==null || userRoleVo.getVersion()<0) {
            log.error("删除用户失败，版本号为空");
            throw new UserManagerException("删除用户失败，参数错误");
        }
        try {
            // 删除用户信息
            int delete = this.userManagerDao.delete(userRoleVo.generateUser());
            if(delete==1) {
                // 添加操作日志
                operationLogService.saveByThread(new OperationLog(userRoleVo.getId(),
                        OperationLogBusinessTypeEnum.USER.getKey(),
                        OperationLogOperationTypeEnum.DELETE.getKey(),
                        UserInfo.getInstance().getUser().getName() + "删除了用户：" + userRoleVo.getName() + "（" + userRoleVo.getLoginAccount() + "）",
                        UserInfo.getInstance()));
                // 删除角色关系
                this.deleteUserRoleByUserId(userRoleVo.getId());
            } else {
                log.error("删除用户失败，删除数量返回为0，参数：" + userRoleVo);
                throw new UserManagerException("删除用户失败，用户已被修改，请重新刷新页面重试");
            }
        } catch (UserManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除用户管理异常，参数：" + userRoleVo, e);
            throw new UserManagerException("删除用户管理异常，请联系管理员");
        }
    }

    /**
     * 删除用户的角色关系
     * @param userId 用户ID
     * @return
     */
    private int deleteUserRoleByUserId(Long userId) {
        int success = 0;
        if(userId!=null && userId>0) {
            success = this.userManagerDao.deleteUserRoleByUserId(userId);
        }
        return success;
    }

    /**
     * 修改用户管理信息
     * @param userRoleVo    用户管理信息
     * @return 用户管理信息
     * @throws UserManagerException 自定义异常
     */
    @Transactional(rollbackFor=Exception.class)
    public UserRoleVo updateUserManager(UserRoleVo userRoleVo) throws UserManagerException {
        if(userRoleVo==null) {
            log.error("保存用户信息失败，参数为空");
            throw new UserManagerException("保存用户信息失败，参数为空");
        }
        if(userRoleVo.getId()!=null && userRoleVo.getId()<=0) {
            log.error("保存用户信息失败，用户ID为空");
            throw new UserManagerException("保存用户信息失败，用户ID为空");
        }
        if(userRoleVo.getVersion()!=null && userRoleVo.getVersion()<0) {
            log.error("保存用户信息失败，版本号为空");
            throw new UserManagerException("保存用户信息失败，参数错误");
        }
        if(!Strings.isNullOrEmpty(userRoleVo.getName())) {
            log.error("保存用户信息失败，用户姓名为空");
            throw new UserManagerException("保存用户信息失败，用户姓名为空");
        }
        if(!Strings.isNullOrEmpty(userRoleVo.getLoginAccount())) {
            log.error("保存用户信息失败，用户登录账号为空");
            throw new UserManagerException("保存用户信息失败，用户登录账号为空");
        }
        if(Strings.isNullOrEmpty(userRoleVo.getEmail()) && !PatternUtil.isEmail(userRoleVo.getEmail())) {
            log.error("保存用户信息失败，邮箱校验失败");
            throw new UserManagerException("保存用户信息失败，邮箱格式错误");
        }
        if(Strings.isNullOrEmpty(userRoleVo.getMobile()) && !PatternUtil.isMobileOrPhone(userRoleVo.getMobile())) {
            log.error("保存用户信息失败，电话号码校验失败");
            throw new UserManagerException("保存用户信息失败，电话号码格式错误");
        }
        try {
            // 查询用户信息，校验数据
            User oldUser = this.userManagerDao.findById(userRoleVo.getId());
            // 校验版本号
            if(!oldUser.getVersion().equals(userRoleVo.getVersion())) {
                log.error("保存用户信息失败，版本号不一致");
                throw new UserManagerException("保存用户信息失败，用户已被修改，请重新刷新页面重试");
            }
            // 校验账号，账号不可修改
            if(!oldUser.getLoginAccount().equals(userRoleVo.getLoginAccount())) {
                log.error("保存用户信息失败，账号不一致");
                throw new UserManagerException("保存用户信息失败，参数错误");
            }
            /* 修改用户信息 */
            User user = userRoleVo.generateUser();
            int updateUser = this.updateUser(user);
            if(updateUser==1) {
                // 添加操作日志
                operationLogService.saveByThread(new OperationLog(user.getId(),
                        OperationLogBusinessTypeEnum.USER.getKey(),
                        OperationLogOperationTypeEnum.UPDATE.getKey(),
                        UserInfo.getInstance().getUser().getName() + "修改用户：" + user.getName() + "（" + user.getLoginAccount() + "）",
                        UserInfo.getInstance()));
                /* 处理角色问题 */
                if(userRoleVo.getRoleList()!=null && userRoleVo.getRoleList().size()>0) {
                    List<Role> roles = userRoleVo.getRoleList();
                    List<Role> removeRoles = new ArrayList<Role>();
                    List<Long> deleteRoleIds = new ArrayList<Long>();
                    // 查询已存在的角色关系
                    List<UserRole> userRoles = this.userManagerDao.queryUserRoleList(user.getId());
                    if(userRoles!=null && userRoles.size()>0) {
                        /* 判断逻辑，如果匹配到相同的角色ID，那么就从待新增的角色集合中移除，如果没有匹配到相同的角色ID，那么将已存在的角色ID添加到待删除的集合中 */
                        boolean isExist;
                        for(UserRole userRole : userRoles) {
                            isExist = false;
                            for(Role role : roles) {
                                if(userRole.getRoleId().equals(role.getId())) {
                                    removeRoles.add(role);
                                    isExist = true;
                                }
                            }
                            if(!isExist) {
                                deleteRoleIds.add(userRole.getRoleId());
                            }
                        }
                    }
                    roles.removeAll(removeRoles);
                    if(deleteRoleIds.size()>0) {
                        this.userManagerDao.deleteUserRoleByUserIdAndRoleIds(user.getId(), deleteRoleIds);
                    }
                    if(roles.size()>0) {
                        /* 新增角色信息 */
                        this.addUserRoleRelation(user, roles);
                    }
                } else {
                    // 删除用户角色关联信息
                    this.deleteUserRoleByUserId(user.getId());
                }
            } else {
                log.error("保存用户信息失败，返回数量为0，参数：" + user);
                throw new UserManagerException("保存用户信息失败，用户已被修改，请重新刷新页面重试");
            }
            userRoleVo.initializationUser(user);
        } catch (UserManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("保存用户信息异常，参数：" + userRoleVo, e);
            throw new UserManagerException("保存用户信息异常，请联系管理员");
        }
        return userRoleVo;
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    private int updateUser(User user) {
        user.setModifyFields(UserInfo.getInstance().getUser().getName());
        return this.userManagerDao.update(user);
    }

    /**
     * 修改密码
     * @param userId    用户ID
     * @param version   版本号
     * @param newPassword   新密码
     * @throws UserManagerException
     */
    public User updatePassword(Long userId, Integer version, String newPassword) throws UserManagerException {
        try {
            if(userId==null || userId<=0) {
                log.error("修改密码失败，用户ID为空");
                throw new UserManagerException("修改密码失败，用户ID为空");
            }
            if(version==null || version<0) {
                log.error("修改密码失败，版本号为空");
                throw new UserManagerException("修改密码失败，参数错误");
            }
            if(!Strings.isNullOrEmpty(newPassword)) {
                log.error("修改密码失败，新密码为空");
                throw new UserManagerException("修改密码失败，新密码参数为空");
            }
            // 查询用户信息
            User user = this.userManagerDao.findById(userId);
            // 校验版本号
            if(!user.getVersion().equals(version)) {
                log.error("修改密码失败，版本号不同");
                throw new UserManagerException("修改密码失败，用户已被修改，请重新刷新页面重试");
            }
            // 校验新密码是否与老密码相同
            newPassword = Encrypt.md5Encrypt(newPassword);
            if(newPassword.equals(user.getPassword())) {
                log.error("修改密码失败，新密码与原密码相同");
                throw new UserManagerException("修改密码失败，新密码与原密码相同");
            }
            // 修改密码
            int update = this.userManagerDao.updatePassword(userId, version, newPassword);
            if(update==1) {
                // 添加操作日志
                operationLogService.saveByThread(new OperationLog(user.getId(),
                        OperationLogBusinessTypeEnum.USER.getKey(),
                        OperationLogOperationTypeEnum.UPDATE.getKey(),
                        UserInfo.getInstance().getUser().getName() + "修改了用户" + user.getName() + "（" + user.getLoginAccount() + "）密码",
                        UserInfo.getInstance()));
                user.setPassword(newPassword);
                return user;
            } else {
                log.error("修改密码失败，更新操作返回数据为0，参数：userId=" + userId + "，version=" + version + "，newPassword=" + newPassword);
                throw new UserManagerException("修改密码失败，请联系管理员");
            }
        } catch (UserManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("修改密码异常，参数：userId=" + userId + "，version=" + version + "，newPassword=" + newPassword, e);
            throw new UserManagerException("修改密码异常，请联系管理员");
        }
    }
}
