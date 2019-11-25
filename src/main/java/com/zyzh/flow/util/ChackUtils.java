package com.zyzh.flow.util;

import com.alibaba.fastjson.JSONObject;
import okhttp3.Response;

/**
 * 校验工具
 * @author Liu
 */
public class ChackUtils {
    /**
     * 允许最大内容
     */
    private static final long maxLength = 1024;

    /**
     * 请求结果 返回标识码
     */
    private static final String code = "200";
    public static boolean messageChack(String msg){
        if (null == msg){
            throw new RuntimeException("内容不能为空！");
        }
        if(msg.getBytes().length>maxLength){
            throw new RuntimeException("内容超出1kb！");
        }
        return true;
    }

    /**
     * json串校验
     * @param content
     * @return
     */
    public static boolean jsonContentChack(String content){
        JSONObject jsonObject = JSONObject.parseObject(content);
        String dataString = jsonObject.getString("data");
        String responseCode = jsonObject.getString("state");
        if(responseCode.equals(code)){
            if(null == dataString || dataString.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
