package com.maintenance.web.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.maintenance.web.context.UserInfo;
import com.maintenance.web.manage.constant.OperationLogBusinessTypeEnum;
import com.maintenance.web.manage.constant.OperationLogOperationTypeEnum;
import com.maintenance.web.manage.dao.RoleDao;
import com.maintenance.web.manage.entity.OperationLog;
import com.maintenance.web.manage.entity.Role;
import com.maintenance.web.manage.exception.RoleManagerException;
import com.maintenance.web.manage.exception.RoleResourceManagerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    @Autowired
    private RoleResourceService roleResourceService;

    @Autowired
    private OperationLogService operationLogService;

    /**
     * 查询角色管理分页数据
     *
     * @param condition 查询条件，分页条件必传，pageNum：页码，pageSize：页大小
     * @return 角色管理分页数据
     * @throws RoleManagerException 自定义异常
     */
    public PageInfo<Role> queryPage(Map<String, Object> condition) throws RoleManagerException {
        if(condition==null || condition.size()<=0) {
            log.error("查询角色管理失败，参数为空");
            throw new RoleManagerException("查询角色管理失败，参数错误");
        }
        Integer pageNum = MapUtils.getInteger(condition, "pageNum");
        Integer pageSize = MapUtils.getInteger(condition, "pageSize");
        if(pageNum==null || pageNum<=0) {
            log.error("查询角色管理失败，参数错误，pageNum为空");
            throw new RoleManagerException("查询角色管理失败，参数错误");
        }
        if(pageSize==null || pageSize<=0) {
            log.error("查询角色管理失败，参数错误，pageSize为空");
            throw new RoleManagerException("查询角色管理失败，参数错误");
        }
        try {
            PageHelper.startPage(pageNum, pageSize);
            Page<Role> page = roleDao.selectAllByPage(condition);
            return page.toPageInfo();
        } catch (Exception e) {
            log.error("查询角色管理分页异常，参数：" + condition, e);
            throw new RoleManagerException("查询角色管理分页异常，请联系管理员");
        }
    }

    /**
     * 查询所有角色信息
     * @param condition 查询条件
     * @return 角色分页数据
     */
    public List<Role> queryList(Map<String, Object> condition) throws RoleManagerException {
        try {
            return roleDao.queryList(condition);
        } catch (Exception e) {
            log.error("查询角色信息异常，参数：" + condition, e);
            throw new RoleManagerException("查询角色信息异常，请联系管理员");
        }
    }

    /**
     * 根据ID查询角色信息
     * @param roleId
     * @return
     * @throws RoleManagerException
     */
    private Role getRoleById(Long roleId) throws RoleManagerException {
        if(roleId==null || roleId<=0) {
            log.error("查询角色信息失败，ID为空");
            throw new RoleManagerException("查询角色信息失败，角色ID为空");
        }
        try {
            return this.roleDao.findById(roleId);
        } catch (Exception e) {
            throw new RoleManagerException("查询角色信息异常，请联系管理员");
        }
    }

    /**
     * 新增角色信息
     * @param role
     * @return
     */
    public Role addRole(Role role) throws RoleManagerException {
        try {
            // 校验角色编码是否重复
            boolean isExists = this.checkRoleCodeExists(role.getCode());
            if(isExists) {
                log.error("新增角色信息失败，角色编码重复，参数信息：" + role);
                throw new RoleManagerException("新增角色信息失败，角色编码重复");
            } else {
                // 设置创建信息和修改信息
                role.setCreateAndModifyFields(UserInfo.getInstance().getUser().getName());
                int add = this.roleDao.insert(role);
                if(add==1) {
                    // 添加操作日志
                    operationLogService.saveByThread(new OperationLog(role.getId(),
                            OperationLogBusinessTypeEnum.ROLE.getKey(),
                            OperationLogOperationTypeEnum.ADD.getKey(),
                            UserInfo.getInstance().getUser().getName() + "新增了角色：" + role.getName() + "（" + role.getCode() + "）",
                            UserInfo.getInstance()));
                    role = this.getRoleById(role.getId());
                } else {
                    log.error("新增角色信息失败，返回数量不正确，数量：" + add);
                    throw new RoleManagerException("保存角色信息失败，请联系管理员");
                }
            }
        } catch (RoleManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("新增角色信息异常，参数：" + role, e);
            throw new RoleManagerException("保存角色信息异常，请联系管理员");
        }
        return role;
    }

    /**
     * 修改角色信息
     * @param role
     * @return
     */
    public Role updateRole(Role role) throws RoleManagerException {
        if(role==null) {
            log.error("修改角色信息失败，参数为空");
            throw new RoleManagerException("保存角色信息失败，参数为空");
        }
        if(role.getId()==null || role.getId()<=0) {
            log.error("修改角色信息失败，角色ID为空");
            throw new RoleManagerException("保存角色信息失败，角色ID为空");
        }
        if(!Strings.isNullOrEmpty(role.getName())) {
            log.error("修改角色信息失败，角色名称为空");
            throw new RoleManagerException("保存角色信息失败，角色名称为空");
        }
        if(!Strings.isNullOrEmpty(role.getCode())) {
            log.error("修改角色信息失败，角色编码为空");
            throw new RoleManagerException("保存角色信息失败，角色编码为空");
        }
        try {
            // 查询角色信息，校验数据
            Role oldRole = this.getRoleById(role.getId());
            // 校验版本号
            if(!oldRole.getVersion().equals(role.getVersion())) {
                log.error("修改角色信息失败，版本号不一致");
                throw new RoleManagerException("保存角色信息失败，角色已被修改，请重新刷新页面重试");
            }
            // 校验账号，角色编码不可修改
            if(!role.getCode().equals(oldRole.getCode())) {
                log.error("保存角色信息失败，角色编码不一致");
                throw new RoleManagerException("保存角色信息失败，参数错误");
            }
            // 设置创建信息和修改信息
            role.setModifyFields(UserInfo.getInstance().getUser().getName());
            int update = this.roleDao.update(role);
            if(update==1) {
                // 添加操作日志
                operationLogService.saveByThread(new OperationLog(role.getId(),
                        OperationLogBusinessTypeEnum.ROLE.getKey(),
                        OperationLogOperationTypeEnum.UPDATE.getKey(),
                        UserInfo.getInstance().getUser().getName() + "修改了角色：" + role.getName() + "（" + role.getCode() + "）",
                        UserInfo.getInstance()));
                role = this.getRoleById(role.getId());
            } else {
                log.error("修改角色信息失败，返回数量不正确，数量：" + update);
                throw new RoleManagerException("保存角色信息失败，请联系管理员");
            }
        } catch (RoleManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("修改角色信息异常，参数：" + role, e);
            throw new RoleManagerException("保存角色信息异常，请联系管理员");
        }
        return role;
    }

    /**
     * 校验角色编码是否存在
     * @param roleCode  角色编码
     * @return
     */
    private boolean checkRoleCodeExists(String roleCode) {
        return this.roleDao.checkRoleCodeExists(roleCode)>0;
    }

    /**
     * 刪除角色
     * @param roles  角色信息
     * @return
     */
    public void batchDeleteRole(List<Role> roles) throws RoleManagerException {
        if(roles==null || roles.size()<=0) {
            log.error("删除角色失败，参数为空");
            throw new RoleManagerException("删除角色失败，参数为空");
        }
        try {
            for(Role role : roles) {
                this.deleteRole(role);
            }
        } catch (RoleManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("删除角色异常，参数：" + roles, e);
            throw new RoleManagerException("删除角色异常，请联系管理员");
        }
    }

    /**
     * 刪除角色信息
     * @param role  角色信息
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Role role) throws RoleManagerException {
        if(role==null) {
            log.error("删除角色失败，参数为空");
            throw new RoleManagerException("删除角色失败，参数为空");
        }
        if(role.getId()==null || role.getId()<=0) {
            log.error("删除角色失败，角色ID为空");
            throw new RoleManagerException("删除角色失败，角色ID为空");
        }
        if(role.getVersion()==null || role.getVersion()<0) {
            log.error("删除角色失败，版本号为空");
            throw new RoleManagerException("删除角色失败，参数错误");
        }
        try {
            // 删除角色信息
            int delete = this.roleDao.delete(role.getId());
            if(delete==1) {
                // 添加操作日志
                operationLogService.saveByThread(new OperationLog(role.getId(),
                        OperationLogBusinessTypeEnum.ROLE.getKey(),
                        OperationLogOperationTypeEnum.DELETE.getKey(),
                        UserInfo.getInstance().getUser().getName() + "删除了角色：" + role.getName() + "（" + role.getCode() + "）",
                        UserInfo.getInstance()));
                // 删除角色关系
                this.roleResourceService.removeAllRoleResource(role.getId());
            } else {
                log.error("删除角色失败，删除数量返回为0，参数：" + role);
                throw new RoleManagerException("删除角色失败，角色已被修改，请重新刷新页面重试");
            }
        } catch (RoleManagerException e) {
            throw e;
        } catch (RoleResourceManagerException e) {
            throw new RoleManagerException(e);
        } catch (Exception e) {
            log.error("删除角色管理异常，参数：" + role, e);
            throw new RoleManagerException("删除角色管理异常，请联系管理员");
        }
    }
}
