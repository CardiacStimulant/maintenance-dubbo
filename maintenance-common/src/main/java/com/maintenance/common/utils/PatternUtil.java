package com.maintenance.common.utils;

import com.google.common.base.Strings;

import java.util.regex.Pattern;

/**
 * 校验号码，整形，座机等，是否符合规则
 */
public class PatternUtil {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
    private static final Pattern MOBILE_PHONE_PATTERN = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^((\\d{3,4})|\\d{3,4}-)?\\d{7,8}$");
    private static final Pattern GOOD_STRING_PATTERN = Pattern.compile("^[0-9]{0,}[a-zA-Z]{0,}[\u4e00-\u9fa5]{0,}$");
    private static final Pattern INTEGER_PATTERN = Pattern.compile("^[-\\+]?[\\d]*$");

    /**
     * 正则验证电子邮件
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        if (!Strings.isNullOrEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.toLowerCase()).matches();
    }

    /**
     * 验证是否是手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobilePhone(String phone) {
        if (!Strings.isNullOrEmpty(phone)) {
            return false;
        }
        return MOBILE_PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 正则验证固定电话
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        if (!Strings.isNullOrEmpty(phone)) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * 验证是不是电话号码
     *
     * @return
     */
    public static boolean isMobileOrPhone(String phone) {
        return isMobilePhone(phone) || isPhone(phone);
    }

    /**
     * 判断有没有乱码
     *
     * @param str
     * @return
     */
    public static boolean isGoodString(String str) {
        if (!Strings.isNullOrEmpty(str)) {
            return false;
        }
        return GOOD_STRING_PATTERN.matcher(str).matches();
    }

    /**
     * 判断字符串是否为整数
     */
    public static boolean isInteger(String str) {
        if (!Strings.isNullOrEmpty(str)) {
            return false;
        }
        return INTEGER_PATTERN.matcher(str).matches();
    }
}
