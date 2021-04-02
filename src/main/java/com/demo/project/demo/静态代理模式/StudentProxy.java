package com.demo.project.demo.静态代理模式;

/**
 *  * 学生代理类，也实现了Person接口，保存一个学生实体，这样既可以代理学生产生行为
 */
public class StudentProxy implements Person {
    //被代理的类
    Student student;
    public StudentProxy(Person person){
        // 只代理学生对象
        if (person.getClass()==Student.class){
            this.student= (Student) person;
        }
    }
    //代理上交班费，调用被代理学生的上交班费行为
    @Override
    public void giveMoney( ) {
      student.giveMoney();
    }
}
