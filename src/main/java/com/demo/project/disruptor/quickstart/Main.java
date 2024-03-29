package com.demo.project.disruptor.quickstart;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class Main {


    public static void main(String[] args) {
        //1.实例化disruptor对象
        OrderEventFactory orderEventFactory=new OrderEventFactory();
        int ringBufferSize=1024*1024;
       ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        /**
         * 1.eventFactory:消息（event）工厂对象
         * 2.ringBufferSize:容器的长度
         * 3.executor:线程池（建议使用自定义线程池）
         * 4. ProducerType:单生产者还是多生产者
         * 5：waitStrategy:等待策略
         */
       Disruptor<OrderEvent> disruptor=new Disruptor<OrderEvent>(
                orderEventFactory,
                ringBufferSize,
                executorService,
                ProducerType.SINGLE,
                new BlockingWaitStrategy()
                );

       //2.添加消费者监听
        disruptor.handleEventsWith(new OrderEventHandler());
        //3.启动disruptor
        disruptor.start();

        //4.获取实际存储数据的容器，RingBuffer
       RingBuffer<OrderEvent> ringBuffer= disruptor.getRingBuffer();

       OrderEventProducer producer=new OrderEventProducer(ringBuffer);

        ByteBuffer byteBuffer=ByteBuffer.allocate(8);

        for (long i=0;i<100;i++){
            byteBuffer.putLong(0,i);
            producer.sendData(byteBuffer);
        }

        disruptor.shutdown();
        executorService.shutdown();
    }

}
