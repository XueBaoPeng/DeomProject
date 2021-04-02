package com.demo.project.demo.多线程;

public class JoinImplTest {

    public static void main(String[] args) {
        Thread t = new Thread(new RunnableImpl());
        t.start();
        try {
            t.join(500);
            System.out.println("joinFinish");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
