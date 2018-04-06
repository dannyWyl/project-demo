package com.test.server;

import com.demo.service.demo.Printer;
import com.zeroc.Ice.Current;

/**
 * Created by Administrator on 2017/11/4
 */
public class PrintServer implements Printer {

    @Override
    public String sayHello(String s, Current current) {
        System.out.println(String.format("服务已经调同,请求参数 -> %s, %s", s, "12345"));
        return String.format("hello %s", s);
    }
}
