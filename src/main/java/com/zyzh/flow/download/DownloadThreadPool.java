package com.zyzh.flow.download;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * 下载线程池
 * @author Liu
 */
public class DownloadThreadPool {
    private static int corePoolSize = 20;
    public static void downloadNetList(ArrayList<String> filePaths) {
        if (null == filePaths && filePaths.size() == 0) {
            return;
        }
        int filesSize = filePaths.size();
        if( filesSize > corePoolSize){
            filesSize = corePoolSize;
        }
        ExecutorService threadPool;
        threadPool = new ThreadPoolExecutor(filesSize, Integer.MAX_VALUE, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(), (r, executor) -> System.out.println(r.toString()+"执行了拒绝策略"));
        //如果前边任务没有完成则调度也不会启动
        DownloadTask.saveFilePath = "F:/image";
        for (String filePath : filePaths) {
            DownloadTask downloadTask = new DownloadTask(filePath);
            threadPool.execute(downloadTask);
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {}
        System.out.println("下载完成！");
    }

    public static void main(String[] args){
        ArrayList<String> urls = new ArrayList<>();
        for(int a =0;a<50;a++){
            urls.add("http://img95.699pic.com/photo/40005/1724.jpg_wh860.jpg");
        }
        downloadNetList(urls);
    }
}

