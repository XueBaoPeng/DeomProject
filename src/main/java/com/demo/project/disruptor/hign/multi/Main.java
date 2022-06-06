package com.demo.project.disruptor.hign.multi;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;

/**
 * Created on 2022/6/2.
 *
 * @author xuebaopeng
 * Description
 */
public class Main {


    public static void main(String[] args) throws InterruptedException {
        //1.创建ringBuffer
        RingBuffer<Order> ringBuffer=RingBuffer.create(ProducerType.MULTI,
                new EventFactory<Order>() {
                    @Override
                    public Order newInstance() {
                        return new Order();
                    }
                },1024*1024,
                new YieldingWaitStrategy());

        //2.通过ringBuffer创建一个屏障
        SequenceBarrier sequenceBarrier=ringBuffer.newBarrier();

        //3.构建多消费者数组：
       Consumer[] consumers=new Consumer[10];
       for (int i=0;i<consumers.length;i++){
           consumers[i]=new Consumer("C"+i);
       }

       //4.构建多消费者工作吃
        WorkerPool<Order> workerPool=new WorkerPool<>(
                ringBuffer,
                sequenceBarrier,
                 new EventExceptionHandler(),
                consumers);
        //5.设置多个消费者的sequence序号用户单独统计消费进度,并且设置到ringBuffer中
        ringBuffer.addGatingSequences(workerPool.getWorkerSequences());
        //6.启动workerPool
        workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));


        CountDownLatch latch=new CountDownLatch(1);

        for (int i=0;i<100;i++){
            Producer producer=new Producer(ringBuffer);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    for (int j=0;j<100;j++){
                        producer.sendData(UUID.randomUUID().toString());
                    }
                }
            }).start();
        }

        Thread.sleep(3000);
        System.out.println("------------线程创建完毕，开始生产数据------------------");
        latch.countDown();

        Thread.sleep(10000);
        for (int i=0;i<consumers.length;i++){
            System.out.println("第"+i+"个消费者处理的任务总数"+consumers[i].getCount());

        }

    }

 static class EventExceptionHandler implements ExceptionHandler<Order>{

    @Override
    public void handleEventException(Throwable throwable, long l, Order order) {

    }

    @Override
    public void handleOnStartException(Throwable throwable) {

    }

    @Override
    public void handleOnShutdownException(Throwable throwable) {

    }
}

}
