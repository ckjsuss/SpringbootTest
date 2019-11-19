package com.zyzh.flow;

import com.zyzh.flow.sa.Ask;

public class Test {
    public static void main(String[] args) {
        //定义熔断器，失败10次进入断开状态
        //在半断开状态下，连续成功15次，进入闭合状态
        //5秒后进入半断开状态
        BreakerManager manager = new BreakerManager(10, 15, 5000);
        showState(manager);

        //模拟失败10次调用
        Ask a = new Ask();
        a.setsuccess(false);
        for (int i = 0; i < 10; i++) {
            manager.execute(a,new Object());
            System.out.println(manager);
        }
        showState(manager);
        System.out.println("==============1==============");
        //这里如果再调用一次服务，正常会抛出“服务已熔断”的异常


        //等待熔断器超时，从Open转到HalfOpen
        try {
            System.out.println("等待熔断器超时（6s）。。。");
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showState(manager);
        //连续成功15次调用
        Ask b = new Ask();
        b.setsuccess(true);
        for (int i = 0; i < 15; i++) {
            manager.execute(b,b);
            System.out.println(manager);
        }
        showState(manager);
        System.out.println("==============2==============");
        //这里如果出现一次调用服务失败，熔断器会马上进入熔断状体，接下来的调用会抛出“服务已熔断”的异常
        Ask c = new Ask();
        c.setsuccess(false);
        for (int i = 0; i < 15; i++) {
            manager.execute(c,c);
            System.out.println(manager);
        }
        showState(manager);
        System.out.println("==============3==============");
        System.out.println("等待(6s) 状态自动切换到半开状态");
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        showState(manager);
        System.out.println("==============4==============");

        Ask d = new Ask();
        d.setsuccess(true);

        Ask e = new Ask();
        e.setsuccess(false);

        for (int i = 0; i <= 40; i++) {
            if(i>=20){
                manager.execute(e,e);
                System.out.print("第"+ (i-20+1) + "次失败！" );
            }else {
                manager.execute(d,d);
                System.out.print("第"+ (i+1) + "次成功！" );
            }
            showState(manager);
        }
        System.out.println("==============5==============");
        int num = 0;
        while (true){
            try {
                if(num>50){
                    break;
                }
                Thread.sleep(1000);
                num++;
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            showState(manager);
        }
    }

    public static void showState(BreakerManager manager) {
        System.out.println("Breaker is Closed:" + manager.isClosed());
        System.out.println("Breaker is Open:" + manager.isOpen());
        System.out.println("Breaker is isHalfOpen:" + manager.isHalfOpen());
        System.out.println();
    }
}
