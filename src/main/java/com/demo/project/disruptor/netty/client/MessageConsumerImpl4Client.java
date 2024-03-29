package com.demo.project.disruptor.netty.client;


import com.demo.project.disruptor.entity.TranslatorData;
import com.demo.project.disruptor.entity.TranslatorDataWapper;
import com.demo.project.disruptor.netty.disruptor.MessageConsumer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class MessageConsumerImpl4Client extends MessageConsumer {

	public MessageConsumerImpl4Client(String consumerId) {
		super(consumerId);
	}

	@Override
	public void onEvent(TranslatorDataWapper event) throws Exception {
		TranslatorData response = event.getData();
		ChannelHandlerContext ctx = event.getCtx();
		//业务逻辑处理:
		try {
    		System.err.println("Client端: id= " + response.getId() 
			+ ", name= " + response.getName()
			+ ", message= " + response.getMessage());
		} finally {
			ReferenceCountUtil.release(response);
		}
	}

}
