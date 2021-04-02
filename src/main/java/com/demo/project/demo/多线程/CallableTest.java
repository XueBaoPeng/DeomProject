package com.demo.project.demo.多线程;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CallableTest {

    public static void main(String[] args) {
        Callable callable=new MyCallable();
        for (int i=0;i<10;i++){
            FutureTask futureTask=new FutureTask(callable);
            new Thread(futureTask,"子线程"+i).start();
            try {
                System.out.println("子线程的返回值："+futureTask.get()+"\n");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
