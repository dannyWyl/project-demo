package com.wyl.self.util;

import com.alibaba.fastjson.JSONObject;
import com.wyl.self.entity.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author wyl
 * @create 2017-11-17 11:07
 * @desc 多线程数据处理工具类
 **/
public abstract class MulThreadDataHandleUtil<P, E> {

    private static Logger logger = LoggerFactory.getLogger(MulThreadDataHandleUtil.class);

    /**
     * 数据List
     */
    private final ConcurrentLinkedQueue<List<E>> dataQuene = new ConcurrentLinkedQueue<List<E>>();

    /**
     * 入参
     *
     * @param readThreadNum   读取线程数
     * @param handleThreadNum 处理线程数
     * @param pageSize        每次读取数量
     * @param model           查询参数
     * @param timeOut         超时时间
     * @param timeUnit        时间单位
     * @return
     */
    public String handleProcess(int readThreadNum, int handleThreadNum, int pageSize, P model, long timeOut, TimeUnit timeUnit) {
        return this.handleService(readThreadNum, handleThreadNum, pageSize, model, timeOut, timeUnit);
    }

    private String handleService(int readThreadNum, int handleThreadNum, int pageSize, P model, long timeOut, TimeUnit timeUnit) {
        int totalPage = 0;

        try {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setPage(1);
            pageInfo.setRp(pageSize);
            pageInfo = this.readData(model, pageInfo);

            if (pageInfo.getTotal() == 0) {
                logger.info(String.format("本次操作没有可处理的数据，时间：%s, 查询参数：%s", new Date(), JSONObject.toJSONString(model)));
                return "OK";
            }

            totalPage = pageInfo.getTotal() / pageSize;

            // 如果不整除，则+1
            if (pageInfo.getTotal() % pageSize != 0) {
                totalPage++;
            }

            // 线程计数器
            CountDownLatch readDownLatch = new CountDownLatch(totalPage - 1);
            CountDownLatch handleDownLatch = new CountDownLatch(totalPage);

            ExecutorService readThreadPool = Executors.newFixedThreadPool(readThreadNum);
            ExecutorService handleThreadPool = Executors.newFixedThreadPool(handleThreadNum);

            for (int i = 2; i <= totalPage; i++) {
                pageInfo.setPage(i);
                readThreadPool.execute(new TheadReadData<P, E>(this, dataQuene, readDownLatch, pageInfo, model));
            }

            for (int i = 1; i < totalPage + 1; i++) {
                handleThreadPool.execute(new TheadHandleData<P, E>(this, dataQuene.poll(), handleDownLatch));
            }

            boolean readAwait = readDownLatch.await(timeOut, timeUnit);
            boolean handleAwait = handleDownLatch.await(timeOut, timeUnit);
            if (!readAwait) {
                readThreadPool.shutdown();
                throw new RuntimeException(String.format("读取数据线程超时， 时间：%s", new Date()));
            }

            if (!handleAwait) {
                handleThreadPool.shutdown();
                throw new RuntimeException(String.format("处理数据线程超时， 时间：%s", new Date()));
            }

            readThreadPool.shutdown();
            handleThreadPool.shutdown();
        } catch (Exception e) {
            logger.error(String.format("多线程处理数据报错，错误信息：%s", e.getMessage()));
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 读取数据抽象方法
     */
    public abstract PageInfo readData(final P p, final PageInfo pageInfo);

    /**
     * 处理数据抽象方法
     */
    public abstract void handleData(List<E> dataList);
}

/**
 * 数据读取
 */
class TheadReadData<P, E> implements Runnable {

    private final MulThreadDataHandleUtil<P, E> handleUtil;

    /**
     * 数据List
     */
    private final ConcurrentLinkedQueue<List<E>> dataQuene;

    /**
     * 线程计数器
     */
    private final CountDownLatch countDownLatch;

    /**
     * 分页参数
     */
    private final PageInfo pageInfo;

    /**
     * 查询条件
     */
    private final P p;

    public TheadReadData(MulThreadDataHandleUtil<P, E> handleUtil, ConcurrentLinkedQueue<List<E>> dataQuene, CountDownLatch countDownLatch, PageInfo pageInfo, P p) {
        this.handleUtil = handleUtil;
        this.dataQuene = dataQuene;
        this.countDownLatch = countDownLatch;
        this.pageInfo = pageInfo;
        this.p = p;
    }

    @Override
    public void run() {
        // 查询结果集合并添加到数据list中
        PageInfo pageInfo = handleUtil.readData(p, this.pageInfo);
        dataQuene.add(pageInfo.getRows());
        countDownLatch.countDown();
    }
}

/**
 * 数据处理
 */
class TheadHandleData<P, E> implements Runnable {

    private final MulThreadDataHandleUtil<P, E> handleUtil;

    /**
     * 数据List
     */
    private final List<E> dataList;

    /**
     * 线程计数器
     */
    private final CountDownLatch countDownLatch;

    public TheadHandleData(MulThreadDataHandleUtil<P, E> handleUtil, List<E> dataList, CountDownLatch countDownLatch) {
        this.handleUtil = handleUtil;
        this.dataList = dataList;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        // 处理数据
        handleUtil.handleData(dataList);
        countDownLatch.countDown();
    }
}
