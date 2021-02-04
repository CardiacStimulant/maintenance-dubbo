package com.maintenance.web.mvc;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericMapper<T> {
    /**
     * 查询分页数据
     * @param condition
     * @return
     */
    Page<T> selectAllByPage(@Param("condition") Map<String, Object> condition);

    /**
     * 查询集合
     * @param condition
     * @return
     */
    List<T> queryList(@Param("condition") Map<String, Object> condition);

    T findById(@Param("id") Serializable id);

    int insert(T entity);

    int update(T entity);

    int delete(@Param("id") Serializable id);
}
