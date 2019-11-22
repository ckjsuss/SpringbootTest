package com.zyzh.flow;

import com.zyzh.flow.breaker.MyBreaker;
import com.zyzh.flow.config.Config;
import com.zyzh.flow.state.BreakerManager;

import java.util.HashMap;

/**
 * 测试类
 */
public class Test {
    public static void main(String[] args) {
        //定义熔断器，失败10次进入断开状态
        //在半断开状态下，连续成功15次，进入闭合状态
        //5秒后进入半断开状态

        BreakerManager manager = new BreakerManager(new Config(5,10,3000));
//        BreakerManager manager = new BreakerManager();
        manager.showState();
        //模拟失败10次调用
        MyBreaker myBreaker1 = new MyBreaker();
        myBreaker1.setsuccess(false);
        for (int i = 0; i <=10; i++) {
            manager.execute(myBreaker1, new HashMap());
            System.out.println(manager);
        }
        manager.showState();
        System.out.println("==============1==============");
        //这里如果再调用一次服务，正常会抛出“服务已熔断”的异常
        //等待熔断器超时，从Open转到HalfOpen
        try {
            System.out.println("等待熔断器超时（6s）。。。");
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        manager.showState();
        //连续成功15次调用
        MyBreaker myBreaker2 = new MyBreaker();
        myBreaker2.setsuccess(true);
        for (int i = 0; i<=15; i++) {
            manager.execute(myBreaker2, new HashMap());
            System.out.println(manager);
        }
        manager.showState();
        System.out.println("==============2==============");
        //这里如果出现一次调用服务失败，熔断器会马上进入熔断状体，接下来的调用会抛出“服务已熔断”的异常
        MyBreaker myBreaker3 = new MyBreaker();
        myBreaker3.setsuccess(false);
        for (int i = 0; i <=15; i++) {
            manager.execute(myBreaker3, new HashMap());
            System.out.println(manager);
        }
        manager.showState();
        System.out.println("==============3==============");
        System.out.println("等待(6s) 状态自动切换到半开状态");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        manager.showState();
        System.out.println("==============4==============");

        MyBreaker myBreaker4 = new MyBreaker();
        myBreaker4.setsuccess(true);
        MyBreaker myBreaker5 = new MyBreaker();
        myBreaker5.setsuccess(false);
        for (int i = 0; i<=40; i++) {
            if (i >= 20) {
                manager.execute(myBreaker5, new HashMap());
                System.out.print("第" + (i - 20 + 1) + "次失败！");
            } else {
                manager.execute(myBreaker4, new HashMap());
                System.out.print("第" + (i + 1) + "次成功！");
            }
            manager.showState();
        }
        System.out.println("==============5==============");
        int num = 0;
        while (true) {
            try {
                if (num > 50) {
                    break;
                }
                Thread.sleep(1000);
                num++;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            manager.showState();
        }
    }
}
