package com.test.ge.common.utils;

/**
 * 字符串操作工具类
 *
 * @author lxq
 */

public class StringUtil {

    /**
     * 判读字符串是否为空
     * @param string
     * @return
     */
    public static boolean isEmpty(String string){
        if(string == null || string.length()==0){
            return true;
        }
        return false;
    }

}
