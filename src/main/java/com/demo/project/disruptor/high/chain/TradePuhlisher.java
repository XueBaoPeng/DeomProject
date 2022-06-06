package com.demo.project.disruptor.high.chain;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.dsl.Disruptor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class TradePuhlisher implements Runnable {

    private Disruptor<Trade> disruptor;
    private CountDownLatch countDownLatch;
    private static int PUSH_COUNT=1;
    public TradePuhlisher(CountDownLatch latch, Disruptor<Trade> disruptor) {
     this.disruptor=disruptor;
      this.countDownLatch=latch;
    }

    @Override
    public void run() {
        TradeEventTranslator tradeEventTranslator= new TradeEventTranslator();
        for (int i=0;i<PUSH_COUNT;i++){
            disruptor.publishEvent(tradeEventTranslator);
        }
        countDownLatch.countDown();//进行向下走
    }

    class TradeEventTranslator implements EventTranslator<Trade>{

        private Random random=new Random();
        @Override
        public void translateTo(Trade trade, long l) {
            this.generateTrade(trade);
        }

        private void generateTrade(Trade trade) {
            trade.setPrice(random.nextDouble()*9999);
        }
    }
}
