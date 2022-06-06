package com.demo.project.disruptor.high.chain;

import com.lmax.disruptor.EventHandler;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class Handle1 implements EventHandler<Trade> {

    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handl1 : set Name");
        trade.setName("H1");
        Thread.sleep(1000);
    }

}
