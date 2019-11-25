package com.zyzh.flow.breaker;

import java.util.Map;

/**
 * 事件结果返回接口
 * @author Liu
 */
public interface Breaker {
    boolean onResponse(Map map) throws Exception;
}
