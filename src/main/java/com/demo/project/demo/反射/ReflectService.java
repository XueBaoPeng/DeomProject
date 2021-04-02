package com.demo.project.demo.反射;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectService {


    public ReflectService() {
    }

    public void sayHello(String name){
        System.out.println("hello"+name);
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //通过反射创建ReflectService对象.
        Object service=Class.forName("com.demo.project.demo.反射.ReflectService").getInterfaces();

        Method method=service.getClass().getMethod("sayHello",String.class);

        method.invoke(service,"sss");
    }

}
