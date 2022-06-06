package com.demo.project.disruptor.quickstart;

import com.lmax.disruptor.EventFactory;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class OrderEventFactory implements EventFactory<OrderEvent> {
    @Override
    public OrderEvent newInstance() {
        return new OrderEvent();//这个方法就是为了返回空的数据对象（Event）
    }
}
