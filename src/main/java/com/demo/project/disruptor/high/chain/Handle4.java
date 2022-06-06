package com.demo.project.disruptor.high.chain;

import com.lmax.disruptor.EventHandler;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class Handle4 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handl 4 : set price");
        trade.setPrice(17.0);
    }
}
