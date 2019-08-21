package disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

public class DisruptorMain {

    public static  void  main(String[] args) throws Exception {
        final Logger logger= LogManager.getLogger(DisruptorMain.class.getName());
        //详细队列
        class Element{
            private int value;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }

        //生产者的线程工厂
        ThreadFactory threadFactory=new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"simpleThread");
            }
        };

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = new EventFactory<Element>() {
            @Override
            public Element newInstance() {
                return new Element();
            }
        };

        //处理event的handler
        EventHandler<Element> handler=new EventHandler<Element>() {
            @Override
            public void onEvent(Element element, long l, boolean b) throws Exception {
                logger.info("Element:"+element.getValue());
            }
        };
        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();

       // 指定RingBuffer的大小
        int bufferSize = 16;

        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);
        // 设置EventHandler
        disruptor.handleEventsWith(handler);

        // 启动disruptor的线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();
        for (int l = 0; true; l++)
        {
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            try
            {
                // 返回可用位置的元素
                Element event = ringBuffer.get(sequence);
                // 设置该位置元素的值
                event.setValue(l);
            }
            finally
            {
                ringBuffer.publish(sequence);
            }
            Thread.sleep(10);
        }
    }

}
