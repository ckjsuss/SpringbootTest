package com.zyzh.flow.download;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 下载线程池
 * @author Liu
 */
public class DownloadThreadPool {
    public static void downloadNetList(ArrayList<String> filePaths) {
        if (null == filePaths && filePaths.size() == 0) {
            return;
        }
        int corePoolSize = filePaths.size();
        //拒绝策略为AbortPolic策略，直接抛出异常
        ExecutorService threadPool = new ThreadPoolExecutor(corePoolSize,
                5, 3000,
                TimeUnit.MILLISECONDS,
                new SynchronousQueue<>(), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());
        //如果前边任务没有完成则调度也不会启动
        for (String filePath : filePaths) {
            threadPool.execute(new DownloadTask(filePath));
        }
        threadPool.shutdown();
    }

    public static void main(String[] args){
        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://192.168.1.162:10036/group1/default/20191121/14/27/0/流程1.JPG");
        urls.add("http://192.168.1.162:10036/group1/default/20191121/17/32/0/流程2.JPG");
        urls.add("http://192.168.1.162:10036/group1/default/20191121/17/33/0/添加设备.bmp");
        downloadNetList(urls);
    }
}

