package redis.Lock;

public class Test {

    public static void main(String[] args) {
        Service service = new Service();
        for (int i = 0; i < 300; i++) {
            ThreadA threadA = new ThreadA(service);
            threadA.start();
        }
    }
}

  class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        this.service = service;
    }

    @Override
    public void run() {
        service.seckill();
    }
}
