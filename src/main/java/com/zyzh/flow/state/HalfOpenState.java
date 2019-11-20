package com.zyzh.flow.state;

/**
 *  HALF-OPEN 状态
 *  1、连续成功调用计数重置为0，当执行成功的时候，自增该字段，当达到连读调用成功次数的阈值时，切换到闭合状态。
 *  如果调用失败，立即切换到断开模式。
 * @author Liu
 */
public class HalfOpenState extends AbstractBreakerState {
    /**
     *  HALF-OPEN 构造
     *  重置连续成功计数
     */
    public HalfOpenState(BreakerManager manager) {
        super(manager);
        manager.resetConsecutiveSuccessCount();
    }

    /**
     *  HALF-OPEN 状态下
     *  有失败，立即切换到断开模式
     *  熔断器状态： HALF-OPEN ==》 OPEN
     */
    @Override
    public void ActUponException() {
        super.ActUponException();
        manager.open();
    }

    /**
     *  连续成功次数达到阈值
     *  熔断器状态：HALF-OPEN ==》 CLOSE
     */
    @Override
    public void protectedCodeHasBeenCalled() {
        super.protectedCodeHasBeenCalled();
        if (manager.consecutiveSuccessThresholdReached()) {
            manager.close();
        }
    }
}