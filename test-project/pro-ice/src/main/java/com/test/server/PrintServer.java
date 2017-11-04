package com.test.server;

import com.demo.service.demo.Printer;
import com.zeroc.Ice.Current;

/**
 * Created by Administrator on 2017/11/4
 */
public class PrintServer implements Printer {

    @Override
    public String sayHello(String s, Current current) {
        System.out.println("111111111111111111111"+s);
        return "hello:" + s;
    }
}
