package com.test;


import com.test.server.PrintServer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * ice服务端测试
 */
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class PrintServerTest {

    @Test
    public void testHello(){

        System.out.println("111");
    }

    public static void main(String[] args) throws Exception
    {
        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args))
        {
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("SimplePrinterAdapter", "default -p 10000");
            com.zeroc.Ice.Object object = new PrintServer();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }
}
