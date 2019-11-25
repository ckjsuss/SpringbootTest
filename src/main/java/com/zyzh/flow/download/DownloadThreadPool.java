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
        Long s = System.currentTimeMillis();
        ExecutorService threadPool;
        threadPool = new ThreadPoolExecutor(filesSize, Integer.MAX_VALUE, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
                Executors.defaultThreadFactory(), (r, executor) -> System.out.println(r.toString()+"执行了拒绝策略"));
        //如果前边任务没有完成则调度也不会启动
        for (String filePath : filePaths) {
            threadPool.execute(new DownloadTask(filePath));
        }
        threadPool.shutdown();
        while (!threadPool.isTerminated()) {

        }
        Long e = System.currentTimeMillis();
        System.out.println("多线程下载耗时："+ (e-s) + "ms");
    }

    public static void main(String[] args){
        ArrayList<String> urls = new ArrayList<>();
//        urls.add("http://192.168.1.162:10036/group1/default/20191121/14/27/0/流程1.JPG");
//        urls.add("http://192.168.1.162:10036/group1/default/20191121/17/33/0/添加设备.bmp");
//        urls.add("http://192.168.1.162:10036/group1/default/20191121/17/32/0/流程2.JPG");

        for(int a =0;a<50;a++){
            urls.add("http://img95.699pic.com/photo/40005/1724.jpg_wh860.jpg");
        }

        long a = System.currentTimeMillis();
        for (String url : urls) {
            try {
                Download.downloadNet(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        long b = System.currentTimeMillis();
        System.out.println("循环下载花费时间："+ (b-a) + "ms");
        downloadNetList(urls);
    }
}

