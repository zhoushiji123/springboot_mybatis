package com.zsj.nettys;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by zsj on 2017/7/30.
 */
public class TcpClient implements Runnable {

    String host =  "127.0.0.1";
    int port =  8080;


    private void connect() throws  Exception{
        //创建nio线程管理组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        //创建客户端辅助启动类
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY,
                true).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new ClientWriteHandler());
            }
        });

        //发起异步连接操作
        ChannelFuture channelFuture = bootstrap.connect(host,port).sync();

        //阻塞等待客户端程序关闭
        channelFuture.channel().closeFuture().sync();

        //退出程序，关闭线程管理组.
        eventLoopGroup.shutdownGracefully();
    }


    public static void main(String[] args) {
        try {
            new TcpClient().connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            this.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
