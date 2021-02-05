package com.maintenance.web.manage.dao;

import com.maintenance.web.manage.entity.Resource;
import com.maintenance.web.mvc.GenericMapper;
import com.maintenance.web.mvc.annotation.BaseDaoAnnotation;

@BaseDaoAnnotation
public interface ResourceDao extends GenericMapper<Resource> {
    int checkResourceKeyExists(Resource resource);
}
