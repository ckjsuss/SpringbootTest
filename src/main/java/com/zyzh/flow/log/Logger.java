package com.zyzh.flow.log;

import com.zyzh.flow.state.BreakerManager;
import com.zyzh.flow.util.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日志
 * @author Liu
 */
public class Logger {
    private final static String fileName = "log" + new SimpleDateFormat("yyyy_MM_dd_HH").format(new Date())+".log";
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public synchronized static void log(String content) {
        String filePath = BreakerManager.class.getClassLoader().getResource("").getPath();
        content = sdf2.format(new Date())+":"+content;
        FileUtils.saveFile(filePath, fileName, content,false);
    }
}
