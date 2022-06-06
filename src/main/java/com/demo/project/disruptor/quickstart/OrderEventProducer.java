package com.demo.project.disruptor.quickstart;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class OrderEventProducer {
    private RingBuffer<OrderEvent> ringBuffer;
    public OrderEventProducer(RingBuffer<OrderEvent> ringBuffer) {
        this.ringBuffer=ringBuffer;
    }

    public void sendData(ByteBuffer byteBuffer){
        //1.在生产者发送消息的时候，首先需要从我们的ringBuffer获取一个可用的序号
        long sequence=ringBuffer.next();
        try {
            //2.根据这个序号，找到具体的"OrderEvent" 元素,注意：此时获取的OrderEvent对象是没有被赋值的对象。
            OrderEvent orderEvent=ringBuffer.get(sequence);
            //3.进行实际的赋值处理
            orderEvent.setValue(byteBuffer.getLong(0));
        }finally {
            //4.提交发布操作
            ringBuffer.publish(sequence);
        }
    }
}
