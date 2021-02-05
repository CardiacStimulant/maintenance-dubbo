package com.maintenance.web.manage.constant;

/**
 * 业务类型枚举类
 */
public enum OperationLogBusinessTypeEnum {
    USER("user", "用户"),
    ROLE("role", "角色"),
    RESOURCE("resource", "资源"),
    TENANT_TELEPHONE_NUMBER("tenantTelephoneNumber", "租户号码");

    private final String key;

    private final String value;

    private OperationLogBusinessTypeEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name();
    }

    public String getValue() {
        return this.value;
    }

}

