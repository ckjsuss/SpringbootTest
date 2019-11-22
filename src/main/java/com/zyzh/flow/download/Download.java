package com.zyzh.flow.download;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Download {
    /**
     * 下载网络文件
     */
    public synchronized static void downloadNet(String path) throws MalformedURLException {
        Long start = System.currentTimeMillis();
        int byteread;
        URL url = new URL(path);
        String flieName = path.substring(path.lastIndexOf("/")+1);
        try {
            URLConnection conn = url.openConnection();
            System.out.println("file_Size："+conn.getContentLength()+"byte");
            InputStream inStream = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream("F:/"+flieName);
            byte[] buffer = new byte[1204];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            Long end = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+":"+(end-start)+"ms");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
