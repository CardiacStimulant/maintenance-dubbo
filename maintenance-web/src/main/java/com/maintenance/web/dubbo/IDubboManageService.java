package com.maintenance.web.dubbo;

import com.alibaba.fastjson.JSONObject;
import com.maintenance.web.exception.DubboManageException;

public interface IDubboManageService {
    /**
     * 根据当前用户名，查询UserInfo信息
     * @param userAccount
     * @return
     * @throws DubboManageException
     */
    JSONObject getUserInfoByUserAccount(String userAccount) throws DubboManageException;
}
