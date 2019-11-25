package com.zyzh.flow.state;

import com.zyzh.flow.breaker.Breaker;
import com.zyzh.flow.config.Config;
import com.zyzh.flow.log.Logger;

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
    static int failureCount;
    /**
     * 连续成功次数
     */
    static int consecutiveSuccessCount;
    /**
     * 最大调用失败次数
     */
    int failureThreshold;
    /**
     * 连续调用成功次数
     */
    int consecutiveSuccessThreshold;
    /**
     * 超时时间
     */
    long timeout;
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

    public long getTimeout() {
        return timeout;
    }

    /**
     * 熔断器初始化
     * 熔断器切换到闭合状态
     * @param config
     */
    public BreakerManager(Config config) {
        if (config.getFailureThreshold() < 1) {
            throw new RuntimeException("【熔断器闭合状态】最大失败次数：必须大于0！");
        }
        if (config.getConsecutiveSuccessThreshold() < 1) {
            throw new RuntimeException("【半熔断状态】最大连续成功次数：必须大于0！");
        }
        if (config.getTimeout() < 1) {
            throw new RuntimeException("【熔断器断开状态】超时时间：必须大于0！");
        }
        this.failureThreshold = config.getFailureThreshold();
        this.consecutiveSuccessThreshold = config.getConsecutiveSuccessThreshold();
        this.timeout = config.getTimeout();
        close();
    }

    /**
     *  默认熔断器
     *  初始化默认值【最大失败次数：10, 最大连续成功次数：15, 超时时间：5000】
     *  熔断器切换到闭合状态
     */
    public BreakerManager() {
        Config config = new Config(10, 15, 5000);
        this.failureThreshold = config.getFailureThreshold();
        this.consecutiveSuccessThreshold = config.getConsecutiveSuccessThreshold();
        this.timeout = config.getTimeout();
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
        return "BreakerManager{" + "失败次数=" + failureCount + ", " + "连续成功次数=" + consecutiveSuccessCount + "," +
               "最大调用失败次数=" + failureThreshold + "," + " 连续调用成功次数=" + consecutiveSuccessThreshold + '}';
    }

    public void showState() {
        System.out.println("Breaker is Closed:" + this.isClosed());
        System.out.println("Breaker is Open:" + this.isOpen());
        System.out.println("Breaker is isHalfOpen:" + this.isHalfOpen());
        Logger.log(this.toString());
        System.out.println();
    }
}