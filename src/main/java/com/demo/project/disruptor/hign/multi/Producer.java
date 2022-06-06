package com.demo.project.disruptor.hign.multi;

import com.lmax.disruptor.RingBuffer;

/**
 * Created on 2022/6/2.
 *
 * @author xuebaopeng
 * Description
 */
public class Producer {
    private RingBuffer<Order> ringBuffer;
    public Producer(RingBuffer<Order> ringBuffer) {
    this.ringBuffer=ringBuffer;
    }

    public void sendData(String uuid) {
     long sequence=ringBuffer.next();
     try {
         Order order=ringBuffer.get(sequence);
         order.setId(uuid);
     }finally {
         ringBuffer.publish(sequence);
     }
    }
}
