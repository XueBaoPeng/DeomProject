# droolsCase

#### 介绍
对drools的几种实现方式进行案例测试

使用drools的三种方式：

	1、项目中，在resource下创建ruls文件夹，规则文件（.drl）全部放在这个文件夹下。

	2、决策表，是一个excel文件，引入drools相关jar可读取excel中的规则进行匹配。

	3、workBench，是一个独立于项目的一个程序，可视图化编辑规则文件并进行打包部署，在项目中可以读取jar中的规则文件，可配合kie-server使用。

    4、workBench+kie-server，这一块的workBench服务和kie-server服务是在centos7中docker中部署的，本地进行远程访问，执行规则。

主要针对以上几个使用方式进行的案例编写测试。

#### 软件架构
springboot+maven+drools

#### 安装教程

workBench+kie-server
参考https://www.cnblogs.com/zhyg/p/10711055.html

workBench可以使用tomcat直接启动，也可用docker镜像，推荐使用docker，更加方便，具体操作请百度，很简单。


### DSL的实现方式 GROOVY
https://blog.csdn.net/tianhaolin1991/article/details/106977846