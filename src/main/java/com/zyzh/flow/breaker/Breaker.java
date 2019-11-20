package com.zyzh.flow.breaker;

import java.util.Map;

public interface Breaker {
    boolean onResponse(Map map) throws Exception;
}
