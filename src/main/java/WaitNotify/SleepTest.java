package WaitNotify;

/**
 * 程序比较简单，在主线程main中启动线程t1。t1启动之后，当t1中的计算i能被4整除时，t1会通过Thread.sleep(100)休眠100毫秒
 */

class ThreadD extends Thread{

    public ThreadD(String name) {
        super(name);
    }


    @Override
    public synchronized void run() {
        try {
                   for(int i=0; i <10; i++){
                          System.out.printf("%s: %d\n", this.getName(), i);
                            // i能被4整除时，休眠100毫秒
                             if (i%4 == 0)
                                     Thread.sleep(1000);
                       }
                    } catch (InterruptedException e) {
                       e.printStackTrace();
                 }


    }
}

public class SleepTest {

    public static void main(String[] args) {
        ThreadD t1=new ThreadD("t1");
        t1.start();
    }
}
