package com.zyzh.flow.breaker;

import java.util.Map;

/**
 * 熔断器执行对象
 * @author Liu
 */
public class MyBreaker implements Breaker {

    private boolean res;

    public void setsuccess(boolean t){
        this.res = t;
    }

    @Override
    public boolean onResponse(Map map) throws Exception {
        return res;
    }
}
