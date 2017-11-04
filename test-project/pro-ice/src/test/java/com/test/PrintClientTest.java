package com.test;

import com.demo.service.demo.PrinterPrx;

/**
 * ice客户端测试
 */
public class PrintClientTest {

    public static void main(String[] args) {
        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args)) {
            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            PrinterPrx printer = PrinterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }
            String yxw = printer.sayHello("yxw");
            System.out.println("返回信息 -> " + yxw);
        }
    }
}
