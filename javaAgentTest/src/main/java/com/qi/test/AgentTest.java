package com.qi.test;

/**
 * @author qijun
 * @date 2022/3/17 16:04
 */
public class AgentTest {

    public static void main(String[] args) {
        System.out.println("我是main方法");
        AA aa = new AA();
        AA bb = new AA();
        aa.sout("222","333","中国");
        aa.sout1(1,2);
    }
}
