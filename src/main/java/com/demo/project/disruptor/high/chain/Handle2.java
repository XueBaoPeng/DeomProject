package com.demo.project.disruptor.high.chain;

import com.lmax.disruptor.EventHandler;

import java.util.UUID;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class Handle2 implements EventHandler<Trade> {
    @Override
    public void onEvent(Trade trade, long l, boolean b) throws Exception {
        System.out.println("handl2 : set id");
        trade.setId(UUID.randomUUID().toString());
        Thread.sleep(2000);
    }
}
