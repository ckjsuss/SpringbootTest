package com.zyzh.flow.download;

import java.net.MalformedURLException;

/**
 * 下载任务
 * @author Liu
 */
public class DownloadTask implements Runnable{
    private String downloadURL;

    public DownloadTask(String downloadURL){
        this.downloadURL = downloadURL;
    }
    @Override
    public void run() {
        try {
            Download.downloadNet(downloadURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
