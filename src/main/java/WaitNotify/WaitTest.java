package WaitNotify;


/**
 * 如下图，说明了“主线程”和“线程t1”的流程。
 *
 * (01) 注意，图中"主线程" 代表“主线程main”。"线程t1" 代表WaitTest中启动的“线程t1”。 而“锁” 代表“t1这个对象的同步锁”。
 * (02) “主线程”通过 new ThreadA("t1") 新建“线程t1”。随后通过synchronized(t1)获取“t1对象的同步锁”。然后调用t1.start()启动“线程t1”。
 * (03) “主线程”执行t1.wait() 释放“t1对象的锁”并且进入“等待(阻塞)状态”。等待t1对象上的线程通过notify() 或 notifyAll()将其唤醒。
 * (04) “线程t1”运行之后，通过synchronized(this)获取“当前对象的锁”；接着调用notify()唤醒“当前对象上的等待线程”，也就是唤醒“主线程”。
 * (05) “线程t1”运行完毕之后，释放“当前对象的锁”。紧接着，“主线程”获取“t1对象的锁”，然后接着运行。
 * https://images0.cnblogs.com/blog/497634/201312/18183712-f04899f92aaa43b6a33a85fecfa60a9d.png
 */
public class WaitTest {

    public static void main(String[] args) {

    ThreadA threadA=new ThreadA("t1");

    synchronized (threadA){
        try {
            // 启动“线程t1”
            System.out.println(Thread.currentThread().getName()+" start t1");
            threadA.start();

          // 主线程等待t1通过notify()唤醒。
          // 主线程等待t1通过notify()唤醒。
            System.out.println(Thread.currentThread().getName()+" wait()");
            threadA.wait();
            System.out.println(Thread.currentThread().getName()+" continue");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    }

}

class ThreadA extends Thread{

    public ThreadA(String name) {
        super(name);
    }

    public void run() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName()+" call notify()");
            // 唤醒当前的wait线程
            notify();
        }
    }
}
