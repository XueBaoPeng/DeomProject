package com.demo.project.disruptor.netty.disruptor;

 import com.demo.project.disruptor.entity.TranslatorDataWapper;
 import com.lmax.disruptor.WorkHandler;

/**
 * @author Alienware
 *
 */
public abstract class MessageConsumer implements WorkHandler<TranslatorDataWapper> {

	protected String consumerId;
	
	public MessageConsumer(String consumerId) {
		this.consumerId = consumerId;
	}

	public String getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		this.consumerId = consumerId;
	}
	

}
