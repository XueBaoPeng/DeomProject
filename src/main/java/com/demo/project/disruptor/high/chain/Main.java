package com.demo.project.disruptor.high.chain;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {

        //1.构建一个线程池用于提交
        ExecutorService es1=Executors.newFixedThreadPool(5);
        ExecutorService es2=Executors.newFixedThreadPool(4);
        Disruptor<Trade> disruptor=new Disruptor<Trade>(
                new EventFactory<Trade>() {
                    @Override
                    public Trade newInstance() {
                        return new Trade();
                    }
                },
                1024*1024,
                es1,
                ProducerType.SINGLE,
                new BusySpinWaitStrategy()
        );

        //2.把消费者设置到Disruptor
          //2.1串行操作
//        disruptor.handleEventsWith(new Handle1())
//                .handleEventsWith(new Handle2())
//                .handleEventsWith(new Handle3());

        //2.2并行操作
//        disruptor.handleEventsWith(new Handle1(),new Handle2(),new
//                Handle3());
//        disruptor.handleEventsWith(new Handle1());
//        disruptor.handleEventsWith(new Handle2());
//        disruptor.handleEventsWith(new Handle3());

         //2.3菱形操作(1) 通过 CyclicBarrier
//         disruptor.handleEventsWith(new Handle1(),new Handle2()).handleEventsWith(new Handle3());

         //2.3菱形操作(2)
//        EventHandlerGroup<Trade> ehGroup=disruptor.handleEventsWith(new Handle1(),new Handle1());
//        ehGroup.then(new Handle3());

        //2.4六边形操作
        Handle1 h1=new Handle1();
        Handle2 h2=new Handle2();
        Handle3 h3=new Handle3();
        Handle4 h4=new Handle4();
        Handle5 h5=new Handle5();

        disruptor.handleEventsWith(h1,h4);//h1->h4并行
        disruptor.after(h1).handleEventsWith(h2);
        disruptor.after(h4).handleEventsWith(h5);
        disruptor.after(h2,h5).handleEventsWith(h3);

        //3.启动disruptor
        RingBuffer<Trade> ringBuffer=disruptor.start();

        CountDownLatch latch=new CountDownLatch(1);

        long begin=System.currentTimeMillis();

        //4.
        es2.submit(new TradePuhlisher(latch,disruptor));


        latch.await();


        disruptor.shutdown();
        es1.shutdown();
        es2.shutdown();

        System.out.println("总耗时："+(System.currentTimeMillis()-begin));

    }
}
