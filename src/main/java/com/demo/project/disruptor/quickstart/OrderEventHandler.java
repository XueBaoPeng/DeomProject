package com.demo.project.disruptor.quickstart;

import com.lmax.disruptor.EventHandler;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class OrderEventHandler implements EventHandler<OrderEvent> {

    @Override
    public void onEvent(OrderEvent orderEvent, long l, boolean b) throws Exception {
        System.out.println("消费者:"+orderEvent.getValue());
    }
}
