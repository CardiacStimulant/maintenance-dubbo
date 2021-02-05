package com.maintenance.web.manage.controller;

import com.maintenance.web.manage.service.OperationLogService;
import com.maintenance.web.mvc.annotation.BaseControllerAnnotation;
import com.maintenance.web.utils.SearchParamsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@BaseControllerAnnotation
@RequestMapping("log")
public class OperationLogController {
    @Autowired
    private OperationLogService logService;

    /**
     * 查询操作记录分页数据
     * @param searchMap 查询条件，分页条件必传，pageNum：页码，pageSize：页大小
     * @return 操作记录分页数据
     */
    @RequestMapping(value = "/queryPage", method = {RequestMethod.POST, RequestMethod.GET})
    public Object queryPage(@RequestParam Map<String, Object> searchMap) {
        // 创建时间的查询条件
        SearchParamsUtil.parseTimeGroup(searchMap, "createTimeGroup", "createTimeList");
        // 更新时间的查询条件
        SearchParamsUtil.parseTimeGroup(searchMap, "lastModifiedGroup", "modifyTimeList");
        return this.logService.queryPage(searchMap);
    }
}
