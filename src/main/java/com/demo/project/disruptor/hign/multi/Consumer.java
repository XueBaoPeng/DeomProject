package com.demo.project.disruptor.hign.multi;

import com.lmax.disruptor.WorkHandler;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created on 2022/6/2.
 *
 * @author xuebaopeng
 * Description
 */
public class Consumer implements WorkHandler<Order> {
    private String comsumerId;
    private  AtomicInteger count=new AtomicInteger(0);

    private Random random=new Random();
    public Consumer(String comsumerId) {
        this.comsumerId = comsumerId;
    }


    @Override
    public void onEvent(Order order) throws Exception {
       Thread.sleep(1*random.nextInt(5));
       System.out.println("当前消费者："+this.comsumerId +",消费的信息ID"+order.getId());
       count.decrementAndGet();
    }

    public int getCount(){
        return count.get();
    }
}
