package com.wyl.self.template.runnable;

import com.wyl.self.service.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by Administrator on 2017/9/10.
 * 多线程分页处理数据
 */
public class PagingProcessingDataUntil implements Runnable {

//    @Autowired
//    private Test test;

    private CountDownLatch countDownLatch;

    private List<String> dataList;

    public PagingProcessingDataUntil(CountDownLatch countDownLatch, List<String> dataList) {
        this.countDownLatch = countDownLatch;
        this.dataList = dataList;
    }

    @Override
    public void run() {

        Test test = new com.wyl.self.service.impl.Test();
        dataList.stream().filter(x->x != null).forEach(x->{
                    try {
                        test.test(x);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
        countDownLatch.countDown();
    }
}
