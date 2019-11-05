import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Auther: xuebaopeng
 * @Date: 2019/11/5 15:03
 * @Description:
 */
public class ThreadWaitTest {

    /**
     * 线程死循环演示
     */
    public static void createBusyThread(){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){}
            }
        },"testBusyThread");

        thread.start();
    }

    /**
     * 线程锁等待演示
     * @param lock
     */
    public static void createLockThread(final  Object lock){
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock){
                    try {
                        lock.wait();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        },"testLockThread");
        thread.start();
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
           br.readLine();
           createBusyThread();
           br.readLine();
           Object obj=new Object();
         createLockThread(obj);
    }
}
