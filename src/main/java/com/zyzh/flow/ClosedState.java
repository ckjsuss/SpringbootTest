package com.zyzh.flow;

/**
 * 熔断器闭合状态
 * 在闭合状态下，如果发生错误，并且错误次数达到阈值，则状态机切换到断开状态
 */
public class ClosedState extends AbstractBreakerState {

    public ClosedState(BreakerManager manager) {
        super(manager);

        //重置失败计数器
        manager.resetFailureCount();
    }

    @Override
    public void ActUponException() {
        super.ActUponException();

        //如果失败次数达到阈值，则切换到断开状态
        if (manager.failureThresholdReached()) {
            manager.moveToOpenState();
        }
    }
}