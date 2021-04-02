package com.demo.project.demo.多线程;

public class RunnableImpl implements Runnable {
    @Override
    public void run() {
        try {
            System.out.println("开始睡眠");
            Thread.sleep(1000);
            System.out.println("结束睡眠");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
