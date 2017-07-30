package com.zsj.nettys;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Service;

/**
 * Created by zsj on 2017/7/30.
 * Netty启动tcpServer
 */
@Service
public class TcpServer {

    int port = 8080;

    private void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();


        //netty用于启动nio服务端的辅助类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class).
                option(ChannelOption.SO_BACKLOG,1024).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                    //当接收到新连接的时候，这个方法会调用
                System.out.println("server--接收到1个连接");
                socketChannel.pipeline().addLast(new MyServerHandler());
            }
        });

        //绑定端口，通过阻塞等待操作完成，返回一个channelFuture，用于异步操作的通知回调。
        ChannelFuture channelFuture = serverBootstrap.bind(port).sync();

        //通过阻塞关闭，服务端关闭后main主线程才结束。
        channelFuture.channel().closeFuture().sync();

        //关闭
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


    public static void main(String[] args) {
        try {
            new TcpServer().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
