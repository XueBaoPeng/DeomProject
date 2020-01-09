package 模拟CAS;

/**
 * CAS(Compare-And-Swap) 算法是硬件对于并发的支持,针对多处理器操作而设计的处理器中的一种特殊指令,用于
 * 管理对共享数据的并发访问;
 * CAS 是一种无锁的非阻塞算法的实现;
 * CAS 包含了三个操作数:
 * 需要读写的内存值: V
 * 进行比较的预估值: A
 * 拟写入的更新值: B
 * 当且仅当 V == A 时, V = B, 否则,将不做任何操作;
 */

class CompareAndSwap{

    private int value;

    //获取内存值
    public synchronized int get(){
        return value;
    }

    // 无论更新成功与否,都返回修改之前的内存值
    public synchronized int compareAndSwap(int expectedValue, int newValue){
        // 获取旧值
        int oldValue = value;

        if(oldValue == expectedValue){
            this.value = newValue;
        }

        // 返回修改之前的值
        return oldValue;
    }

    // 判断是否设置成功
    public synchronized boolean compareAndSet(int expectedValue, int newValue){
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }

}


public class TestCompareAndSwap {

    public static void main(String[] args) {
        final CompareAndSwap cas = new CompareAndSwap();

        for(int i=0; i<10; i++) {
            // 创建10个线程,模拟多线程环境
            new Thread(new Runnable() {
                public void run() {
                    int expectedValue = cas.get();

                    boolean b = cas.compareAndSet(expectedValue, (int) (Math.random() * 100));
                    System.out.println(b);
                }
            }).start();
        }
    }
}
