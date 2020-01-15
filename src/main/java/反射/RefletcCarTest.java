package 反射;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RefletcCarTest {

    public static Car initByDefaultConst() throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //①通过类装载器获取Car类对象
        ClassLoader classLoader=Thread.currentThread().getContextClassLoader();
        Class clazz=classLoader.loadClass("反射.Car");
        //②获取类的默认构造器对象并通过它实例化Car
        Constructor constructor=clazz.getDeclaredConstructor((Class[])null);
        Car car= (Car) constructor.newInstance();
        //③通过反射方法设置属性
        Method setBrand=clazz.getMethod("setBrand",String.class);
        setBrand.invoke(car,"红旗CAZ2");
        Method setColor = clazz.getMethod("setColor",String.class);
        setColor.invoke(car,"黑色");
        Method setMaxSpeed = clazz.getMethod("setMaxSpeed",int.class);
        setMaxSpeed.invoke(car,200);
        return  car;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        System.out.println("current loader:"+loader);
        System.out.println("parent loader:"+loader.getParent());
        System.out.println("grandparent loader:"+loader.getParent(). getParent());
        Car car = initByDefaultConst();
        car.introduce();
    }
}
