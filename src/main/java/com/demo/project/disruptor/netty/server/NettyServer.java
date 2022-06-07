package com.demo.project.disruptor.netty.server;

import com.demo.project.disruptor.codec.MarshallingCodeCFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created on 2022/6/7.
 *
 * @author xuebaopeng
 * Description
 */
public class NettyServer {


    public NettyServer(){
        //1.创建俩个工作线程组，一个用于接收网络请求，另一个用于实际处理业务的线程组
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workGroup =new NioEventLoopGroup();

        ServerBootstrap serverBootstrap=new ServerBootstrap();

        try {

            serverBootstrap.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    //表示缓冲区动态调配（自适应）
                    .option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT)
                    //缓存区池化操作
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                            socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });
            //绑定端口，同步等等请求连接
            ChannelFuture cf = serverBootstrap.bind(8765).sync();
            System.err.println("Server Startup...");
            cf.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
            System.err.println("Server ShutDown ....");
        }

    }

}
