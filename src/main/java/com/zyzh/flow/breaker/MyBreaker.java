package com.zyzh.flow.breaker;

import java.util.Map;

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
