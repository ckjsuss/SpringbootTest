package com.zyzh.flow.state;

import com.zyzh.flow.breaker.Breaker;
import java.util.HashMap;

/**
 * 熔断器管理类
 *
 * @author Liu
 */
public class BreakerManager {
    /**
     * 失败次数
     */
    public int failureCount;
    /**
     * 连续成功次数
     */
    public int consecutiveSuccessCount;
    /**
     * 最大调用失败次数
     */
    public int failureThreshold;
    /**
     * 连续调用成功次数
     */
    public int consecutiveSuccessThreshold;
    public long timeout;
    /**
     * 当前熔断器状态
     */
    private AbstractBreakerState state;

    public boolean isClosed() {
        return state instanceof ClosedState;
    }

    public boolean isOpen() {
        return state instanceof OpenState;
    }

    public boolean isHalfOpen() {
        return state instanceof HalfOpenState;
    }

    private void moveToClosedState() {
        state = new ClosedState(this);
    }

    private void moveToOpenState() {
        state = new OpenState(this);
    }

    private void moveToHalfOpenState() {
        state = new HalfOpenState(this);
    }

    protected void increaseFailureCount() {
        failureCount++;
    }

    public void resetFailureCount() {
        failureCount = 0;
    }

    protected boolean failureThresholdReached() {
        return failureCount >= failureThreshold;
    }

    protected void increaseSuccessCount() {
        consecutiveSuccessCount++;
    }

    protected void resetConsecutiveSuccessCount() {
        consecutiveSuccessCount = 0;
    }

    protected boolean consecutiveSuccessThresholdReached() {
        return consecutiveSuccessCount >= consecutiveSuccessThreshold;
    }

    public static BreakerManager getInstance(int failureThreshold, int consecutiveSuccessThreshold, long timeout){
        return LazyBreakerManager.getBreakerManager( failureThreshold,consecutiveSuccessThreshold,timeout);
    }

    /**
     * 内部类延时加载
     * Demand Holder
     */
    private static class LazyBreakerManager{
        public static BreakerManager breakerManager = null;
        public static BreakerManager getBreakerManager(int failureThreshold, int consecutiveSuccessThreshold, long timeout){
            breakerManager = new BreakerManager(failureThreshold,consecutiveSuccessThreshold,timeout);
            return breakerManager;
        }
    }

    /**
     *  熔断器初始化
     * 熔断器切换到闭合状态
     * @param failureThreshold            Close状态下最大失败次数
     * @param consecutiveSuccessThreshold HalfOpen状态下使用的最大连续成功次数
     * @param timeout                     Open状态下的超时时间
     */
    private BreakerManager(int failureThreshold, int consecutiveSuccessThreshold, long timeout) {
        if (failureThreshold < 1 ) {
            throw new RuntimeException("【熔断器闭合状态】最大失败次数：必须大于0！");
        }
        if(consecutiveSuccessThreshold < 1){
            throw new RuntimeException("【半熔断状态】最大连续成功次数：必须大于0！");
        }
        if (timeout < 1) {
            throw new RuntimeException("【熔断器断开状态】超时时间：必须大于0！");
        }
        this.failureThreshold = failureThreshold;
        this.consecutiveSuccessThreshold = consecutiveSuccessThreshold;
        this.timeout = timeout;
        close();
    }

    /**
     * 切换到闭合状态
     */
    public void close() {
        moveToClosedState();
    }

    /**
     * 切换到断开状态
     */
    public void open() {
        moveToOpenState();
    }

    /**
     * 切换到半开状态
     */
    public void halfOpen() {
        moveToHalfOpenState();
    }

    public void execute(Breaker breaker, HashMap map) {
        try {
            boolean flag = breaker.onResponse(map);
            if (flag == true) {
                state.protectedCodeHasBeenCalled();
            } else {
                state.ActUponException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            state.protectedCodeIsAboutToBeCalled();
        }
    }

    @Override
    public String toString() {
        return "BreakerManager{"
                + "失败次数=" + failureCount + ", " +
                "连续成功次数=" + consecutiveSuccessCount + ", " +
                "最大调用失败次数=" + failureThreshold + "," +
                " 连续调用成功次数=" + consecutiveSuccessThreshold
                + '}';
    }
}