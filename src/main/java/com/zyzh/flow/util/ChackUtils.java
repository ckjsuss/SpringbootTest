package com.zyzh.flow.util;

/**
 * 校验工具
 * @author Liu
 */
public class ChackUtils {
    private static final long maxLength = 1024;
    public static boolean messageChack(String msg){
        if (null == msg){
            throw new RuntimeException("内容不能为空！");
        }
        if(msg.getBytes().length>maxLength){
            throw new RuntimeException("内容超出1kb！");
        }
        return true;
    }
}
