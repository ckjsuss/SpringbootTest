package com.zyzh.flow.state;

/**
 * 熔断器状态转移操作的抽象类
 * @author Liu
 */
public abstract class AbstractBreakerState {

    protected BreakerManager manager;

    public AbstractBreakerState(BreakerManager manager) {
        this.manager = manager;
    }

    /**
     * 调用方法之前处理的操作
     * 如果是断开状态，直接返回，然后等超时转换到半断开状态
     */
    public void protectedCodeIsAboutToBeCalled() {
        if (manager.isOpen()) {
            throw new RuntimeException("服务已熔断，请稍等重试！");
        }
    }

    /**
     * 方法调用成功之后的操作
     */
    public void protectedCodeHasBeenCalled() {
        manager.increaseSuccessCount();
    }

    /**
     * 方法调用发生异常操作后的操作
     * 失败次数计数器（自增），并且保存错误信息
     * 重置连续成功次数
     */
    public void ActUponException() {
        manager.increaseFailureCount();
        manager.resetConsecutiveSuccessCount();
    }

}