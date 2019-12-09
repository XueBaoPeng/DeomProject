package 多线程;

import java.util.concurrent.Callable;

public class MyCallable implements Callable {
    int i=0;

    @Override
    public Object call() throws Exception {
        System.out.println(Thread.currentThread().getName()+"i的值："+i);
        return i++;
    }
}
