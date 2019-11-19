package com.zyzh.flow.sa;

public class Ask implements Eat {

    private boolean s;
    @Override
    public boolean onResponse(Object o) throws Exception {
        return s;
    }

    public void setsuccess(boolean t){
        s=t;
    }

}
