package com.zyzh.flow;

import com.zyzh.flow.sa.Eat;

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

    protected void moveToClosedState() {
        state = new ClosedState(this);
    }

    protected void moveToOpenState() {
        state = new OpenState(this);
    }

    protected void moveToHalfOpenState() {
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

    /**
     * 在初始状态下，熔断器切换到闭合状态
     *
     * @param failureThreshold            Close状态下最大失败次数
     * @param consecutiveSuccessThreshold HalfOpen状态下使用的最大连续成功次数
     * @param timeout                     Open状态下的超时时间
     */
    public BreakerManager(int failureThreshold, int consecutiveSuccessThreshold, long timeout) {
        if (failureThreshold < 1 || consecutiveSuccessThreshold < 1) {
            throw new RuntimeException("熔断器闭合状态的最大失败次数和半熔断状态的最大连续成功次数必须大于0！");
        }
        if (timeout < 1) {
            throw new RuntimeException("熔断器断开状态超时时间必须大于0！");
        }
        this.failureThreshold = failureThreshold;
        this.consecutiveSuccessThreshold = consecutiveSuccessThreshold;
        this.timeout = timeout;
        moveToClosedState();
    }

    /**
     * 手动切换到闭合状态
     */
    public void close() {
        moveToClosedState();
    }

    /**
     * 手动切换到断开状态
     */
    public void open() {
        moveToOpenState();
    }

    public void execute(Eat eat, Object a) {
        try {
            boolean flag = eat.onResponse(a);
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
        return "BreakerManager{" + "失败次数=" + failureCount + ", 连续成功次数=" + consecutiveSuccessCount + ", 最大调用失败次数=" + failureThreshold + ", 连续调用成功次数=" + consecutiveSuccessThreshold + '}';
    }
}