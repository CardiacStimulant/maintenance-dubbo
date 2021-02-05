package com.maintenance.web.manage.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maintenance.web.manage.dao.OperationLogDao;
import com.maintenance.web.manage.entity.OperationLog;
import com.maintenance.web.manage.exception.OperationLogException;
import com.maintenance.web.mvc.GenericService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class OperationLogService extends GenericService<OperationLog> {
    @Autowired
    private OperationLogDao operationLogDao;

    /**
     * 查询操作记录分页数据
     * @param condition 查询条件，分页条件必传，pageNum：页码，pageSize：页大小
     * @return 操作记录分页数据
     * @throws OperationLogException 自定义异常
     */
    public PageInfo<OperationLog> queryPage(Map<String, Object> condition) throws OperationLogException {
        if(condition==null) {
            log.error("查询操作记录失败，参数为空");
            throw new OperationLogException("查询操作记录失败，参数错误");
        }
        Integer pageNum = MapUtils.getInteger(condition, "pageNum");
        Integer pageSize = MapUtils.getInteger(condition, "pageSize");
        if(pageNum==null || pageNum<=0) {
            log.error("查询操作记录失败，参数错误，pageNum为空");
            throw new OperationLogException("查询操作记录失败，参数错误");
        }
        if(pageSize==null || pageSize<=0) {
            log.error("查询操作记录失败，参数错误，pageSize为空");
            throw new OperationLogException("查询操作记录失败，参数错误");
        }
        try {
            PageHelper.startPage(pageNum, pageSize);
            Page<OperationLog> page = operationLogDao.selectAllByPage(condition);
            return page.toPageInfo();
        } catch (Exception e) {
            log.error("查询操作记录分页异常", e);
            throw new OperationLogException("查询操作记录分页异常，请联系管理员");
        }
    }

    /**
     * 异步添加日志
     * @param operationLog
     */
    public void saveByThread(final OperationLog operationLog) {
        try {
            new Thread(() -> insert(operationLog)).start();
        } catch (Exception e) {
            log.error("保存日志异常，参数：" + operationLog, e);
        }
    }
}
