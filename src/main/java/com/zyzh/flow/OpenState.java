package com.zyzh.flow;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 熔断器断开状态
 * 断开状态内部维护一个计数器，如果断开达到一定的时间，则自动切换到半断开状态，并且，在断开状态下，如果需要执行操作，则直接抛出异常。
 */
public class OpenState extends AbstractBreakerState {

    public OpenState(BreakerManager manager) {
        super(manager);
//        final Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                timeoutHasBeenReached();
//                timer.cancel();
//            }
//        }, manager.timeout);
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
            service.schedule(() -> {
                timeoutHasBeenReached();
                service.shutdown();
            },manager.timeout, TimeUnit.MILLISECONDS);

    }

    @Override
    public void protectedCodeIsAboutToBeCalled() {
        super.protectedCodeIsAboutToBeCalled();
        throw new RuntimeException("服务已熔断，请稍等重试！");
    }

    /**
     * 断开超过设定的阈值，自动切换到半断开状态
     */
    private void timeoutHasBeenReached() {
        manager.moveToHalfOpenState();
    }
}