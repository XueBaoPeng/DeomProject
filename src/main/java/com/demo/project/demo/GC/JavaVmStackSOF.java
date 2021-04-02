package com.demo.project.demo.GC;

/**
 * @Auther: xuebaopeng
 * @Date: 2019/10/25 17:43
 * @Description:虚拟机栈和本地方法栈OOM测试
 * VM Args: -Xss128k
 */
public class JavaVmStackSOF {

    private int stackLength=1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }

    public static void  main(String args[] ) throws Throwable{
        JavaVmStackSOF javaVmStackSOF=new JavaVmStackSOF();
        try {
            javaVmStackSOF.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length"+javaVmStackSOF.stackLength);
            throw e;
        }
    }

}
