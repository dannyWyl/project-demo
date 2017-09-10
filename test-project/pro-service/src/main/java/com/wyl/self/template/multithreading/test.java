package com.wyl.self.template.multithreading;

import com.wyl.self.template.runnable.PagingProcessingDataUntil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/9/5.
 * 线程池 (处理数据)
 */
public class test {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c", "d", "e", "f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","t");
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        int rp = 0;
        int pageNum = 0;
        int pageSize = 5;
        int pageTotal = list.size() / pageSize;
        CountDownLatch latch = new CountDownLatch(3);

        try {
            if(list.size() % pageSize != 0){
                pageTotal++;
            }

            for (int i = 1; i < pageTotal; i++) {
                pageNum = i;
                rp = (pageNum - 1) * pageSize;

                List<String> collect = list.stream().limit(pageNum * pageSize).skip(rp).collect(Collectors.toList());
                fixedThreadPool.execute(new PagingProcessingDataUntil(latch, collect));
            }

            latch.await();
            fixedThreadPool.shutdownNow();
            System.out.println("多线程处理结束！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
