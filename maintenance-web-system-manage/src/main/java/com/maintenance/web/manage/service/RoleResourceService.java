package com.maintenance.web.manage.service;

import com.maintenance.web.context.UserInfo;
import com.maintenance.web.manage.dao.RoleResourceDao;
import com.maintenance.web.manage.entity.Resource;
import com.maintenance.web.manage.entity.RoleResource;
import com.maintenance.web.manage.exception.RoleResourceManagerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RoleResourceService {
    @Autowired
    private RoleResourceDao roleResourceDao;

    @Autowired
    private ResourceService resourceService;

    /**
     * 角色添加全部资源
     * @param roleId
     * @throws RoleResourceManagerException
     */
    @Transactional(rollbackFor = Exception.class)
    public List<RoleResource> addAllRoleResource(Long roleId) throws RoleResourceManagerException {
        if(roleId==null || roleId<=0) {
            log.error("角色添加资源失败，角色ID为空");
            throw new RoleResourceManagerException("角色添加资源失败，角色ID为空");
        }
        List<RoleResource> roleResources = new ArrayList<>();
        try {
            // 查询角色全部未配置的资源
            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("roleId", roleId);
            searchParams.put("searchConfig", "wait");
            List<Resource> resources = this.resourceService.queryList(searchParams);
            /* 配置角色资源数据 */
            RoleResource roleResource = new RoleResource();
            for(Resource resource : resources) {
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resource.getId());
                this.addRoleResource(roleResource);
                roleResources.add(roleResource);
            }
        } catch (RoleResourceManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("角色添加资源异常，参数：" + roleId, e);
            throw new RoleResourceManagerException("角色添加资源异常，请联系管理员");
        }
        return roleResources;
    }

    /**
     * 角色添加资源
     * @param roleResources
     * @throws RoleResourceManagerException
     */
    @Transactional(rollbackFor = Exception.class)
    public void addRoleResources(List<RoleResource> roleResources) throws RoleResourceManagerException {
        if(roleResources==null || roleResources.size()<=0) {
            return;
        }
        try {
            for(RoleResource roleResource : roleResources) {
                this.addRoleResource(roleResource);
            }
        } catch (RoleResourceManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("角色添加资源异常，参数：" + roleResources, e);
            throw new RoleResourceManagerException("角色添加资源异常，请联系管理员");
        }
    }

    /**
     * 添加角色-资源关系数据
     * @param roleResource
     * @return
     * @throws RoleResourceManagerException
     */
    private void addRoleResource(RoleResource roleResource) throws RoleResourceManagerException {
        if(roleResource==null) {
            log.error("角色添加资源失败，参数为空");
            throw new RoleResourceManagerException("角色添加资源失败，参数为空");
        }
        if(roleResource.getRoleId()==null || roleResource.getRoleId()<=0) {
            log.error("角色添加资源失败，角色ID为空");
            throw new RoleResourceManagerException("角色添加资源失败，角色ID为空");
        }
        if(roleResource.getResourceId()==null || roleResource.getResourceId()<=0) {
            log.error("角色添加资源失败，资源ID为空");
            throw new RoleResourceManagerException("角色添加资源失败，资源ID为空");
        }
        try {
            // 校验角色-资源关系是否已经存在
            boolean isExists = this.checkRoleResourceExists(roleResource);
            if(isExists) {
                log.error("角色添加资源失败，关系已存在，参数：" + roleResource);
                throw new RoleResourceManagerException("角色添加资源失败，角色-资源关系发生变化，请重新刷新后重试");
            } else {
                roleResource.setCreateAndModifyFields(UserInfo.getInstance().getUser().getName());
                int add = this.roleResourceDao.insert(roleResource);
                if(add!=1) {
                    log.error("角色添加资源失败，新增返回数量为0，参数：" + roleResource);
                    throw new RoleResourceManagerException("角色添加资源失败，请联系管理员");
                }
            }
        } catch (RoleResourceManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("角色添加资源异常，参数：" + roleResource, e);
            throw new RoleResourceManagerException("角色添加资源异常，请联系管理员");
        }
    }

    /**
     * 校验角色-资源关系是否存在
     * @param roleResource
     * @return
     */
    private boolean checkRoleResourceExists(RoleResource roleResource) {
        return this.roleResourceDao.checkRoleResourceExists(roleResource)>0;
    }

    /**
     * 角色移除全部资源
     * @param roleId
     * @throws RoleResourceManagerException
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeAllRoleResource(Long roleId) throws RoleResourceManagerException {
        if(roleId==null || roleId<=0) {
            log.error("角色移除资源失败，角色ID为空");
            throw new RoleResourceManagerException("角色移除资源失败，角色ID为空");
        }
        try {
            // 查询角色全部已配置的资源
            Map<String, Object> searchParams = new HashMap<>();
            searchParams.put("roleId", roleId);
            searchParams.put("searchConfig", "configured");
            List<Resource> resources = this.resourceService.queryList(searchParams);
            /* 移除已配置角色资源数据 */
            RoleResource roleResource = new RoleResource();
            for(Resource resource : resources) {
                roleResource.setRoleId(roleId);
                roleResource.setResourceId(resource.getId());
                this.removeRoleResource(roleResource);
            }
        } catch (RoleResourceManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("角色移除资源异常，参数：" + roleId, e);
            throw new RoleResourceManagerException("角色移除资源异常，请联系管理员");
        }
    }

    /**
     * 角色移除资源
     * @param roleResources
     * @throws RoleResourceManagerException
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeRoleResources(List<RoleResource> roleResources) throws RoleResourceManagerException {
        if(roleResources==null || roleResources.size()<=0) {
            return;
        }
        try {
            for(RoleResource roleResource : roleResources) {
                this.removeRoleResource(roleResource);
            }
        } catch (RoleResourceManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("角色移除资源异常，参数：" + roleResources, e);
            throw new RoleResourceManagerException("角色移除资源异常，请联系管理员");
        }
    }

    /**
     * 移除角色-资源关系数据
     * @param roleResource
     * @return
     * @throws RoleResourceManagerException
     */
    private void removeRoleResource(RoleResource roleResource) throws RoleResourceManagerException {
        if(roleResource==null) {
            log.error("角色移除资源失败，参数为空");
            throw new RoleResourceManagerException("角色移除资源失败，参数为空");
        }
        if(roleResource.getRoleId()==null || roleResource.getRoleId()<=0) {
            log.error("角色移除资源失败，角色ID为空");
            throw new RoleResourceManagerException("角色移除资源失败，角色ID为空");
        }
        if(roleResource.getResourceId()==null || roleResource.getResourceId()<=0) {
            log.error("角色移除资源失败，资源ID为空");
            throw new RoleResourceManagerException("角色移除资源失败，资源ID为空");
        }
        try {
            int remove = this.roleResourceDao.removeRoleResource(roleResource);
            if(remove!=1) {
                log.error("角色移除资源失败，移除返回数量为0，参数：" + roleResource);
                throw new RoleResourceManagerException("角色移除资源失败，请联系管理员");
            }
        } catch (RoleResourceManagerException e) {
            throw e;
        } catch (Exception e) {
            log.error("角色移除资源异常，参数：" + roleResource, e);
            throw new RoleResourceManagerException("角色移除资源异常，请联系管理员");
        }
    }

    /**
     * 根据资源ID删除角色资源关系
     * @param resourceId
     * @throws RoleResourceManagerException
     */
    public void removeRoleResourceByResourceId(Long resourceId) throws RoleResourceManagerException {
        if(resourceId==null || resourceId<=0) {
            log.error("根据资源ID删除角色资源信息失败，参数为空");
            throw new RoleResourceManagerException("删除角色资源关系失败，参数为空");
        }
        try {
            this.roleResourceDao.removeRoleResourceByResourceId(resourceId);
        } catch (Exception e) {
            log.error("根据资源ID删除角色资源关系异常，参数：" + resourceId, e);
            throw new RoleResourceManagerException("删除角色资源关系异常，请联系管理员");
        }
    }
}
