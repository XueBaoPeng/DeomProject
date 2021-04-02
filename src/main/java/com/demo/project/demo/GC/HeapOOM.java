package com.demo.project.demo.GC;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: xuebaopeng
 * @Date: 2019/10/25 17:23
 * @Description: java堆内存溢出异常测试
 * VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 */
public class HeapOOM {

    static class OOMObject{

    }

    public static void  main(String args []) {
        List<OOMObject> list=new ArrayList<OOMObject>();
        while (true){
            list.add(new OOMObject());
        }
    }

}
