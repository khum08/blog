package com.yzk.util;

import java.util.UUID;

/**
 * <pre>
 *     author : khum 1241367422@qq.com
 *     time   : 2018/9/13
 *     desc   :
 * </pre>
 */
public class UUIDUtil {

    public static void main(String[] strings){
        String s = get();
        System.out.println(s);
    }

    public static String get(){
        String rawUuid = UUID.randomUUID().toString();
        String uuid = rawUuid.replace("-", "");
        return uuid;
    }

}
