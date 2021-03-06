package com.demo.project.demo.GC;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xuebaopeng
 * @Date: 2019/11/5 14:47
 * @Description:
 * -Xms100m -Xmx100m -XX:+UseSerialGC  以64kb/50毫秒的速度往堆中填充数据
 */
public class MonitoringTest {

    static  class OOMObject{
        public byte[] placeholder=new byte[64*1024];
    }

    public static void fillHeap(int num) throws InterruptedException{
        List<OOMObject> list=new ArrayList<OOMObject>();
        for (int i=0;i<num;i++){
            Thread.sleep(50);
            list.add(new OOMObject());
        }
    }

    public static void main(String[] args) throws Exception{
        fillHeap(1000);
        System.gc();
    }
}
