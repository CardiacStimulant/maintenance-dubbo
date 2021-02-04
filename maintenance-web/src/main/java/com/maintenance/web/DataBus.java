package com.maintenance.web;

import com.maintenance.web.rpc.IRpcManageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 数据总线
 * 实现：
 *  - 获取基础数据（会从缓存获取，缓存没有时候通过接口从服务提供者获取）
 *  - 写入基础数据（会更新到缓存）
 */
@Data
@Component
public class DataBus {
    @Autowired
    private IRpcManageService iRpcManageService;
//    /** 对接缓存 **/
//    @Autowired
//    private RedisService redisService;
//
//    /** 对接服务接口 **/
//    @Autowired
//    private IRpcBillingService rpcBillingService;
//    @Autowired
//    private IRpcCallService rpcCallService;
//    @Autowired
//    private IRpcManageService rpcManageService;
//    @Autowired
//    private IRpcWorkOrderService rpcWorkOrderService;
//    @Autowired
//    private IRpcWorkOrderTriggerService rpcWorkOrderTriggerService;
//    @Autowired
//    private IRpcCalloutTaskService rpcCalloutTaskService;

//    /** 对接数据库 **/
//    @Autowired
//    private SearchService searchService;
//
//    /**
//     * 获取AgentInfo缓存对象
//     * @param tenantId
//     * @param agentId
//     * @return
//     */
//    public AgentInfo getAgentInfo(String tenantId, String agentId){
//        AgentInfo agentInfo = redisService.getAgentInfo(tenantId, agentId);
//        if(agentInfo == null){
//            //从接口查询持久化数据，并加入缓存
//        }
//        return agentInfo;
//    }
//
//    /**
//     * 从缓存查询所有AgentInfo记录
//     * @param tenantId 租户ID
//     * @return
//     */
//    public List<AgentInfo> getAllAgentInfo(String tenantId){
//        List<AgentInfo> retList = redisService.getAllAgentInfo(tenantId);
//        if(retList == null){
//            //从接口查询持久化数据，并加入缓存
//        }
//        return retList;
//    }
//
//    /**
//     * 获取AgentExtInfo缓存对象
//     * @param tenantId
//     * @param agentId
//     * @return
//     */
//    public AgentExtInfo getAgentExtInfo(String tenantId, String agentId){
//        AgentExtInfo extInfo = redisService.getAgentExtInfo(tenantId, agentId);
//        if(extInfo == null){
//            //从接口查询持久化数据，并加入缓存
//        }
//        return extInfo;
//    }
//
//    /**
//     * 查询客服信息
//     * @param staffId
//     * @return
//     */
//    public StaffDTO getStaffById(String staffId) throws DataBusException {
//        return null;
//    }
//
//    /**
//     * 查询客服技能组信息
//     * @param staffGroupId
//     * @return
//     */
//    public StaffGroupDTO getStaffGroupById(String staffGroupId) throws DataBusException {
//        return null;
//    }
//
//    /**
//     * 查询坐席信息
//     * @param agentId
//     * @return
//     */
//    public AgentDTO getAgentById(String agentId){
//        return null;
//    }
//
//    /**
//     * 根据IUAP侧租户ID查询云呼侧租户信息
//     * @param iuapTenantId
//     * @return
//     */
//    public TenantDTO getTenantByIuapTenantId(String iuapTenantId){
//        return null;
//    }
//
//    /**
//     * 根据租户ID查询租户信息
//     * @param tenantId
//     * @return
//     */
//    public TenantDTO getTenantByTenantId(String tenantId){
//        return null;
//    }
}
