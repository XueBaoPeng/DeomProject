package 读写锁;

import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockTest {
    public static void main(String[] args) {
        final Queue q3=new Queue();

        for (int i=0;i<3;i++){
            new Thread("读线程"+i){
                @Override
                public void run() {
                    while (true) {
                        q3.get();
                    }
                }
            }.start();
        }

        for (int i=0;i<3;i++){
            new Thread("写线程"+i){
                @Override
                public void run() {
                    while(true){
                        q3.put(new Random().nextInt(10000));
                    }
                }
            }.start();
        }

    }


}

class Queue{
    private Object data=null;//共享数据，只能有一个线程写该数据，可以有多个线程读该数据
    private ReentrantReadWriteLock writeLock=new ReentrantReadWriteLock();

    public void get(){
        writeLock.readLock().lock();//上读锁，其他线程不能写只能读
        System.out.println(Thread.currentThread().getName()+"be ready to read data"+data);
        try {
            Thread.sleep((long) (Math.random()*1000));
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"have read data"+data);
        writeLock.readLock().unlock();
    }

    public void put(Object data){

        writeLock.writeLock().lock();//上写锁，不允许其他线程读写操作

        System.out.println(Thread.currentThread().getName()+"be ready to write data!");
        try {
            Thread.sleep((long)(Math.random()*1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.data=data;
        System.out.println(Thread.currentThread().getName() + " have write data: " + data);

        writeLock.writeLock().unlock();
    }

}
