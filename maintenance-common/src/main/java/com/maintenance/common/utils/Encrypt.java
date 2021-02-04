package com.maintenance.common.utils;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

    /**
     * SHA1：哈希算法加密
     *
     * @param Passwd
     * @return
     */
    public static String sha1Encrypt(String Passwd) {
        MessageDigest alg;
        String result = "";
        String tmp = "";
        try {
            alg = MessageDigest.getInstance("SHA-1");
            alg.update(Passwd.getBytes());
            byte[] bts = alg.digest();
            for (int i = 0; i < bts.length; i++) {
                tmp = (Integer.toHexString(bts[i] & 0xFF));
                if (tmp.length() == 1)
                    result += "0";
                result += tmp;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * MD5：MD5算法加密
     *
     * @param Passwd
     * @return
     */
    public static String md5Encrypt(String Passwd) {
        MessageDigest alg;
        String result = "";
        try {
            alg = MessageDigest.getInstance("MD5");
            alg.update(Passwd.getBytes());
            result = Base64.encodeBase64String(alg.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * BASE64加密
     *
     * @param Passwd
     * @return
     */
    public static String base64Encoder(String Passwd) {
        String result = "";
        // 获取加密实体类
        BASE64Encoder encoder = new BASE64Encoder();
        // 进行加密
        result = encoder.encode(Passwd.getBytes());

        return result;
    }

}
