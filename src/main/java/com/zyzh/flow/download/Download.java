package com.zyzh.flow.download;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 下载
 *
 * @author Liu
 */
public class Download {
    /**
     *
     *
     * @param path
     * @
     */
    /**
     * 下载网络文件
     * @param path 下载地址
     * @param saveFilePath 报错文件地址
     * @throws MalformedURLException
     */
    public synchronized static void downloadNet(String path, String saveFilePath) throws MalformedURLException {
        int byteread;
        URL url = new URL(path);
        String a = String.valueOf(System.currentTimeMillis());
        String flieName = a + path.substring(path.lastIndexOf("/") + 1);

        try {
            URLConnection conn = url.openConnection();
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(saveFilePath + File.separator + flieName);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
