package com.demo.project.disruptor.quickstart;

/**
 * Created on 2022/6/1.
 *
 * @author xuebaopeng
 * Description
 */
public class OrderEvent {

    private long value;//价格

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
