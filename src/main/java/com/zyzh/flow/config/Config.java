package com.zyzh.flow.config;

/**
 * BreakerManager自定义配置
 * @author Liu
 */
public  class Config {
    private  int failureThreshold;
    private  int consecutiveSuccessThreshold;
    private  long timeout;

    /**
     * @param failureThreshold 最大失败次数
     * @param consecutiveSuccessThreshold 最大连续成功次数
     * @param timeout 熔断器断开状态 超时时间 装换成半开状态
     */
    public Config(int failureThreshold,int consecutiveSuccessThreshold,long timeout ){
        this.failureThreshold = failureThreshold;
        this.consecutiveSuccessThreshold = consecutiveSuccessThreshold;
        this.timeout = timeout;
    }

    public int getFailureThreshold() {
        return failureThreshold;
    }

    public int getConsecutiveSuccessThreshold() {
        return consecutiveSuccessThreshold;
    }

    public long getTimeout() {
        return timeout;
    }
}


