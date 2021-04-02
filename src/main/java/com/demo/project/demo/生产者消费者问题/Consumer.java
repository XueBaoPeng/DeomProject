package com.demo.project.demo.生产者消费者问题;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {

    private BlockingQueue<Message>queue;

    public Consumer(BlockingQueue<Message> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            Message msg;
            //获取并处理消息直到接收到“exit”消息
            while((msg = queue.take()).getMsg() !="exit"){
                Thread.sleep(10);
                System.out.println("Consumed "+msg.getMsg());
            }
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
