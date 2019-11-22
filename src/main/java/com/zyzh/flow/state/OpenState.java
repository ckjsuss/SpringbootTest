package com.zyzh.flow.state;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * OPEN 状态
 * 1、在该类下维护一个计时器: “CLOSE 【超时】==》 HALF-OPEN ”；
 * 2、如果需要执行操作，则直接抛出异常。
 * @author Liu
 */
public class OpenState extends AbstractBreakerState {
    /**
     * HALF-OPEN 状态构造
     * 维护一个计时器
     * @param manager
     * manager.timeout 超时时间（单位毫秒）
     */
    public OpenState(BreakerManager manager) {
        super(manager);
        ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);
            service.schedule(() -> {
                manager.halfOpen();
                service.shutdown();
            },manager.getTimeout(), TimeUnit.MILLISECONDS);

    }

    /**
     *  断开状态：继续执行操作，则直接抛出异常
     *  熔断器状态：OPEN ==》Exception（抛出异常通知）
     */
    @Override
    public void protectedCodeIsAboutToBeCalled() {
        super.protectedCodeIsAboutToBeCalled();
        throw new RuntimeException("服务已熔断，请稍等重试！");
    }
}