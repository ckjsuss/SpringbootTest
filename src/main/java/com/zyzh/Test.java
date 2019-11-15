package com.zyzh;

public class Test {
    public static void main(String[] args){
        getString1();
        getString2();
    }

    public static void getString1(){
        Long l = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        for(int a = 0 ; a<1000;a++){
            stringBuilder.append(String.valueOf(Math.random())+String.valueOf(Math.random()));
        }
        System.out.println(System.currentTimeMillis() - l + "ms");
    }

    public static void getString2(){
        Long l = System.currentTimeMillis();
        StringBuilder stringBuilder = new StringBuilder();
        for(int a = 0 ; a<1000;a++){
            stringBuilder.append(String.valueOf(Math.random()));
            stringBuilder.append(String.valueOf(Math.random()));
        }
        System.out.println(System.currentTimeMillis() - l  + "ms");
    }
}
