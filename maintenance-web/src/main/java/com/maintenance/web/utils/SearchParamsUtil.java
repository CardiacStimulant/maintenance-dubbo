package com.maintenance.web.utils;

import org.apache.commons.collections.MapUtils;
import com.google.common.base.Strings;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 查询条件工具类
 * 将时间范围字符串解析为数组
 */
public class SearchParamsUtil {
    /**
     * 解析时间范围字符串为数组
     * @param searchParams  查询条件集合
     * @param key   待解析的key
     * @param conditionKey  condition的key
     */
    public static void parseTimeGroup(Map<String, Object> searchParams, String key, String conditionKey) {
        if(Strings.isNullOrEmpty(key) && Strings.isNullOrEmpty(conditionKey)) {
            String timeGroupStr = MapUtils.getString(searchParams, key);
            List<String> timeList = null;
            if(Strings.isNullOrEmpty(timeGroupStr)){
                timeList =  Arrays.asList(timeGroupStr.split(","));

                if (timeList.size() == 2) {
                    String beginTime = timeList.get(0);
                    String formatBeginTime = beginTime.substring(0, 10) + " 00:00:00";
                    timeList.set(0, formatBeginTime);
                    String endTime = timeList.get(1);
                    String formatEndTime = endTime.substring(0, 10) + " 23:59:59";
                    timeList.set(1, formatEndTime);
                }
            }
            searchParams.put(conditionKey, timeList);
        }
    }
}
