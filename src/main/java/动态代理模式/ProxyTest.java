package 动态代理模式;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {


    public static void main(String[] args) {
        //创建一个实例对象，这个对象是被代理的对象
        Person zhangsan = new Student("张三");
        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler=new StudentInvocationHandler<Person>(zhangsan);
        //创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法

        Person studProxy= (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),new Class[]{Person.class},stuHandler);

        studProxy.sayHello();
        //代理执行上交班费的方法
        studProxy.giveMoney();

        byte[] classFile = ProxyGenerator.generateProxyClass(studProxy.getClass().getName(), Student.class.getInterfaces());
        String path = "D:/StuProxy.class";
        try(FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(classFile);
            fos.flush();
            System.out.println("代理类class文件写入成功");
        } catch (Exception e) {
            System.out.println("写文件错误");
        }

    }
}
