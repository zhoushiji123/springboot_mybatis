package com.zsj.nettys;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.Scanner;

/**
 * Created by zsj on 2017/7/30.
 */
public class ClientHandler extends ChannelHandlerAdapter{




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //tcp连接成功后调用，用于往server发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf ;
        Scanner scanner = new Scanner(System.in);
        while (true){
            System.out.println("client-请输入发送内容");
            byte[] bytes = scanner.next().getBytes();
            byteBuf = Unpooled.buffer(bytes.length);
            byteBuf.writeBytes(bytes);
            ctx.writeAndFlush(byteBuf);
        }

    }


    //接收server的应答信息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf readBuf = (ByteBuf)msg;
        byte[] bytes = new byte[readBuf.readableBytes()];
        readBuf.readBytes(bytes);

        String body = new String(bytes,"UTF-8");

        System.out.println("client--从服务端接收到消息 :"+body);
    }
}
