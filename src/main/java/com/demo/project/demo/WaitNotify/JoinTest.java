package com.demo.project.demo.WaitNotify;

/**
 * 上面的有两个类Father(主线程类)和Son(子线程类)。因为Son是在Father中创建并启动的，所以，Father是主线程类，Son是子线程类。
 * 在Father主线程中，通过new Son()新建“子线程s”。接着通过s.start()启动“子线程s”，并且调用s.join()。
 * 在调用s.join()之后，Father主线程会一直等待，直到“子线程s”运行完毕；在“子线程s”运行完毕之后，
 * Father主线程才能接着运行。 这也就是我们所说的“join()的作用，是让主线程会等待子线程结束之后才能继续运行”！
 *  // 主线程
 *     public class Father extends Thread {
 *         public void run() {
 *             Son s = new Son();
 *             s.start();
 *             try {
 *                 s.join();
 *             } catch (InterruptedException e) {
 *                 e.printStackTrace();
 *             }
 *         }
 *     }
 *     // 子线程
 *     public class Son extends Thread {
 *         public void run() {
 *         }
 *     }
 */
// JoinTest.java的源码
public class JoinTest{

    public static void main(String[] args){
        try {
            ThreadA t1 = new ThreadA("t1"); // 新建“线程t1”

            t1.start();                     // 启动“线程t1”
            t1.join();                        // 将“线程t1”加入到“主线程main”中，并且“主线程main()会等待它的完成”
            System.out.printf("%s finish\n", Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class ThreadA extends Thread{

        public ThreadA(String name){
            super(name);
        }
        public void run(){
            System.out.printf("%s start\n", this.getName());

            // 延时操作
            for(int i=0; i <1000000; i++)
                ;

            System.out.printf("%s finish\n", this.getName());
        }
    }
}
