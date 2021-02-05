package com.maintenance.web.manage.entity;

import com.maintenance.web.context.UserInfo;
import com.maintenance.web.entity.BaseDrEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class OperationLog extends BaseDrEntity implements Serializable {
    private static final long serialVersionUID = 7344534995410472230L;
    private Long id;
    private Long businessId;
    private String businessType;
    private String operationType;
    private String comments;

    public OperationLog(){}

    /**
     * 初始化实体
     * @param businessId    业务ID
     * @param businessType  业务类型
     * @param operationType 操作类型
     * @param comments  备注
     */
    public OperationLog(Long businessId, String businessType, String operationType, String comments) {
        this.setBusinessId(businessId);
        this.setBusinessType(businessType);
        this.setOperationType(operationType);
        this.setComments(comments);
    }

    /**
     * 初始化实体
     * @param businessId    业务ID
     * @param businessType  业务类型
     * @param operationType 操作类型
     * @param comments  备注
     * @param userInfo 用户信息
     */
    public OperationLog(Long businessId, String businessType, String operationType, String comments, UserInfo userInfo) {
        this.setBusinessId(businessId);
        this.setBusinessType(businessType);
        this.setOperationType(operationType);
        this.setComments(comments);
        this.setCreateAndModifyFields(userInfo.getUser().getName());
    }
}
