package com.qi.test;

import java.lang.instrument.Instrumentation;

/**
 * @author qijun
 * @date 2022/3/17 14:47
 */
public class PreMainAgent {
    public static void premain(String param, Instrumentation instrumentation) {
        String[] split = param.split(",");
        instrumentation.addTransformer(new LogPrintTransformer(split[0],split[1]));
    }
}
