package com.zyzh.flow.state;

/**
 * CLOSE 状态
 * 在闭合状态下，如果发生错误，并且错误次数达到阈值，则状态机切换到断开状态
 * @author Liu
 */
public class ClosedState extends AbstractBreakerState {
    /**
     * CLOSE 状态构造
     * 失败计数器重置
     * @param manager
     */
    public ClosedState(BreakerManager manager) {
        super(manager);
        manager.resetFailureCount();
    }

    /**
     * 失败次数达到阈值
     * 熔断器状态： CLOSE ==》OPEN
     */
    @Override
    public void ActUponException() {
        super.ActUponException();
        if (manager.failureThresholdReached()) {
            manager.halfOpen();
        }
    }
}