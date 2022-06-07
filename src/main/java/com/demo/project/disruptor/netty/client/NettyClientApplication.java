package com.demo.project.disruptor.netty.client;

import com.demo.project.disruptor.netty.disruptor.MessageConsumer;
import com.demo.project.disruptor.netty.disruptor.RingBufferWorkerPoolFactory;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created on 2022/6/7.
 *
 * @author xuebaopeng
 * Description
 */

@SpringBootApplication
public class NettyClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyClientApplication.class, args);

        MessageConsumer[] conusmers = new MessageConsumer[4];
        for(int i =0; i < conusmers.length; i++) {
            MessageConsumer messageConsumer = new MessageConsumerImpl4Client("code:clientId:" + i);
            conusmers[i] = messageConsumer;
        }
        RingBufferWorkerPoolFactory.getInstance().initAndStart(ProducerType.MULTI,
                1024*1024,
                //new YieldingWaitStrategy(),
                new BlockingWaitStrategy(),
                conusmers);

        //建立连接 并发送消息
        new NettyClient().sendData();
    }
}
