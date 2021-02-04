package com.maintenance.web.mvc;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.maintenance.common.exception.GenericException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public abstract class GenericService<T> {
    private GenericMapper<T> genericMapper;

    public void setGenericMapper(GenericMapper<T> genericMapper) {
        this.genericMapper = genericMapper;
    }

    public PageInfo<T> selectAllByPage(PageRequest pageRequest, Map<String, Object> condition) {
        PageHelper.startPage(pageRequest.getPageNumber(), pageRequest.getPageSize());
        return this.genericMapper.selectAllByPage(condition).toPageInfo();
    }

    public List<T> findAll() {
        Map<String, Object> condition = new HashMap<>();
        return this.queryList(condition);
    }

    public List<T> queryList(Map<String, Object> condition) {
        return this.genericMapper.queryList(condition);
    }

    public T findEntity(Map<String, Object> condition) {
        List<T> list = this.genericMapper.queryList(condition);
        if(list!=null && list.size()==1) {
            return list.get(0);
        } else {
            log.error("查询失败，返回数据错误，检索数据不唯一，参数：" + condition);
            throw new GenericException("查询失败，返回数据错误");
        }
    }

    public T findById(Serializable id) {
        return this.genericMapper.findById(id);
    }

    public T insert(T entity) {
        int result = this.genericMapper.insert(entity);
        if(result==0) {
            log.error("保存数据失败，返回数量为0，参数信息：" + entity);
            throw new GenericException("保存数据失败");
        }
        return entity;
    }

    public void insertBatch(List<T> listEntity) {
        for (T t : listEntity) {
            this.insert(t);
        }
    }

    public T update(T entity) {
        int result = this.genericMapper.update(entity);
        if(result==0) {
            log.error("保存数据失败，返回数量为0，参数信息：" + entity);
            throw new GenericException("保存数据失败");
        }
        return entity;
    }

    public void updateBatch(List<T> listEntity) {
        for (T t : listEntity) {
            this.update(t);
        }
    }

    public int delete(Serializable id) {
        return this.genericMapper.delete(id);
    }
}
