package com.zyzh.flow.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件操作类
 * @author Liu
 */
public class FileUtils {
    /**
     * 保存文件
     * @param filePath 文件路径： 例如 “F:/json”
     * @param fileName 文件名称： 例如 “a1.json”
     * @param content 内容：例如 “{"a1":"123"}”
     * @param deleteOldFile 删除并创建 true，原文件上拼接 false
     */
    public static void saveFile(String filePath,String fileName,String content,boolean deleteOldFile){
        String path = filePath + fileName;
        FileOutputStream fos = null;
        File file = new File(path);
        if (file.exists() && deleteOldFile){
            file.delete();
        }
        try {
            fos = new FileOutputStream(path, true);
            fos.write(content.getBytes());
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
