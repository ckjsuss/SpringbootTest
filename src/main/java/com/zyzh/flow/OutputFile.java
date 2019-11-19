package com.zyzh.flow;

import java.io.*;

public class OutputFile {
    public static String fileFullPath = "F:\\Workspace_Java\\Github\\SpringbootTest\\src\\main\\java\\com\\zyzh\\flow\\log.txt";
    public synchronized static void wirte(String content){
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
