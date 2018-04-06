package com.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wyl
 * @create 2018-04-04 10:57
 * @desc
 **/
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class TestUtil {

    @Test
    public void testTask(){
        try {
            Thread.sleep(1000_1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
