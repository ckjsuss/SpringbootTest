package com.zyzh.flow.log;

import com.zyzh.flow.state.BreakerManager;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    public static String fileFullPath = BreakerManager.class.getClassLoader().getResource("").getPath()+"log"+sdf.format(new Date())+".txt";
    static {
        System.out.println(fileFullPath);
    }
    public synchronized static void log(String content){
            FileOutputStream fos = null;
            try {
                //true不覆盖已有内容
                fos = new FileOutputStream(fileFullPath, true);
                //写入
                fos.write(content.getBytes());
                // 写入一个换行
                fos.write("\r\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                if(fos != null){
                    try {
                        fos.flush();
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
}
