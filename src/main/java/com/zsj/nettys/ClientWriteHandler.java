package com.zsj.nettys;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.Scanner;

/**
 * Created by zsj on 2017/7/30.
 */
public class ClientWriteHandler extends ChannelHandlerAdapter{


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    //tcp连接成功后调用，用于往server发送信息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ByteBuf byteBuf ;
        Scanner scanner = new Scanner(System.in);
        System.out.println("client-请输入发送内容");
        byte[] bytes = scanner.next().getBytes();
        byteBuf = Unpooled.buffer(bytes.length);
        byteBuf.writeBytes(bytes);
        ctx.writeAndFlush(byteBuf);

    }

    //接收到消息的时候调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //读取返回信息
        ByteBuf byteBuf = (ByteBuf)msg;
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        String backMsg = new String(bytes,"UTF-8");
        System.out.println(backMsg);

        //byteBuf.clear();112

        //再次发送消息
        Scanner scanner = new Scanner(System.in);
        System.out.println("client-请输入发送内容");
        byte[] sendMsg = scanner.next().getBytes();
        ByteBuf sendBuf = Unpooled.buffer(sendMsg.length);
        sendBuf.writeBytes(sendMsg);
        ctx.writeAndFlush(sendBuf);
    }
}
