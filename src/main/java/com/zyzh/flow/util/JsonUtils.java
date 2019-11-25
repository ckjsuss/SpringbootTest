package com.zyzh.flow.util;

import com.alibaba.fastjson.JSONObject;
import com.zyzh.flow.breaker.Breaker;
import com.zyzh.flow.state.BreakerManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

/**
 *  易联平台deviceConfig 下载类
 * @author Liu
 */
public class JsonUtils {
    private static final String urlKey = "urlHead";
    public static String urlHead;

    /**
     * get请求处理
     *  map.put("urlHead","http://192.168.1.159:10010/easyconnect/device/config"); urlHead 必填参数
     *  map.put("productKey","dV2x1wsH5go");
     *  map.put("deviceName","000000000001");
     * @param params
     * @return
     */
    public static String getDeviceJSONContent(HashMap<String,String> params){
        String deviceConfig;
        StringBuffer paramString = new StringBuffer("?");
        urlHead = params.get(urlKey);
        if(null == urlHead){
            throw new IllegalArgumentException("urlHead 参数缺失！");
        }
        if (null != params){
            for (String key : params.keySet()) {
                if(key.equals(urlKey)){
                    continue;
                }
                String value = params.get(key);
                paramString.append(key+"="+value);
                paramString.append("&");
            }
        }
        String pString = paramString.toString().substring(0, paramString.length()-1);
        System.out.println("URL:"+urlHead + pString);
        deviceConfig = sendGet(urlHead + pString);
        return deviceConfig;
    }

    /**
     * get请求核心方法
     * @param url
     * @return
     */
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlName = url;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setConnectTimeout(4000);
            // 建立实际的连接
            conn.connect();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("发送GET请求出现异常！" + e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                System.err.println("关闭流异常");
            }
        }
        return result;
    }

    /**
     * 获取json返回值中指定值
     * @param responseContent
     * @return
     */
    public static String getResponseContentData(String responseContent){
        JSONObject jsonObject = JSONObject.parseObject(responseContent);
        return jsonObject.getJSONObject("data").toString();
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args){
        String deviceConfig = "";
        HashMap<String,String> params = new HashMap<>(2);
        params.put("urlHead","http://192.168.1.159:10010/easyconnect/device/config");
        params.put("productKey","dV2x1wsH5go");
        params.put("deviceName","000000000001");
        try {
            deviceConfig = JsonUtils.getDeviceJSONContent(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(deviceConfig);
        boolean flag = ChackUtils.jsonContentChack(deviceConfig);
        if(flag){
            String filePath = Breaker.class.getClassLoader().getResource("").getPath();
            String fileName = "deviceconfig.json";
            FileUtils.saveFile(filePath, fileName,  getResponseContentData(deviceConfig),true);
        }
    }
}
