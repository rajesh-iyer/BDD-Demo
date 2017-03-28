package com.xpanxion.bdd.demo;

public class Subtract implements IResult {

    @Override
    public int getResult(Object a, Object b) {
        Integer a1 = Integer.valueOf((String) a);
        Integer b1 = Integer.valueOf((String) b);
        return a1 - b1;
    }
}
