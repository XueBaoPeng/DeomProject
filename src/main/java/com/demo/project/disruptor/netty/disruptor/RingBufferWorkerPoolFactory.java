package com.demo.project.disruptor.netty.disruptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;


import com.demo.project.disruptor.entity.TranslatorDataWapper;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.WorkerPool;
import com.lmax.disruptor.dsl.ProducerType;

public class RingBufferWorkerPoolFactory {

	private static class SingletonHolder {
		static final RingBufferWorkerPoolFactory instance = new RingBufferWorkerPoolFactory();
	}
	
	private RingBufferWorkerPoolFactory(){
		
	}
	
	public static RingBufferWorkerPoolFactory getInstance() {
		return SingletonHolder.instance;
	}
	
	private static Map<String, MessageProducer> producers = new ConcurrentHashMap<String, MessageProducer>();
	
	private static Map<String, MessageConsumer> consumers = new ConcurrentHashMap<String, MessageConsumer>();

	private RingBuffer<TranslatorDataWapper> ringBuffer;
	
	private SequenceBarrier sequenceBarrier;
	
	private WorkerPool<TranslatorDataWapper> workerPool;
	
	public void initAndStart(ProducerType type, int bufferSize, WaitStrategy waitStrategy, MessageConsumer[] messageConsumers) {
		//1. 构建ringBuffer对象
		this.ringBuffer = RingBuffer.create(type,
				new EventFactory<TranslatorDataWapper>() {
					public TranslatorDataWapper newInstance() {
						return new TranslatorDataWapper();
					}
				},
				bufferSize,
				waitStrategy);
		//2.设置序号栅栏
		this.sequenceBarrier = this.ringBuffer.newBarrier();
		//3.设置工作池
		this.workerPool = new WorkerPool<TranslatorDataWapper>(this.ringBuffer,
				this.sequenceBarrier, 
				new EventExceptionHandler(), messageConsumers);
		//4 把所构建的消费者置入池中
		for(MessageConsumer mc : messageConsumers){
			this.consumers.put(mc.getConsumerId(), mc);
		}
		//5 添加我们的sequences
		this.ringBuffer.addGatingSequences(this.workerPool.getWorkerSequences());
		//6 启动我们的工作池
		this.workerPool.start(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()/2));
	}
	
	public MessageProducer getMessageProducer(String producerId){
		MessageProducer messageProducer = this.producers.get(producerId);
		if(null == messageProducer) {
			messageProducer = new MessageProducer(producerId, this.ringBuffer);
			this.producers.put(producerId, messageProducer);
		}
		return messageProducer;
	}
	
	
	/**
	 * 异常静态类
	 * @author Alienware
	 *
	 */
	static class EventExceptionHandler implements ExceptionHandler<TranslatorDataWapper> {
		public void handleEventException(Throwable ex, long sequence, TranslatorDataWapper event) {
		}

		public void handleOnStartException(Throwable ex) {
		}

		public void handleOnShutdownException(Throwable ex) {
		}
	}
	
	
	
	
	
}



