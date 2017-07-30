package com.zsj.nettys;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import zucc.zhoushiji.utils.DateUtil;

/**
 * Created by zsj on 2017/7/30.
 * 服务端实际处理消息类
 */
public class MyServerHandler extends ChannelHandlerAdapter {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //读取发送到服务端的数据内容
        ByteBuf receiceBuf = (ByteBuf)msg;
        byte[] contents = new byte[receiceBuf.readableBytes()];
        receiceBuf.readBytes(contents);
        System.out.println("server--从客户端接收到的 : "+new String(contents,"UTF-8"));

        //往客户端返回消息
        String sendBack = "client--从服务端成功接收消息 : "+ DateUtil.getCurrentTime();
        ByteBuf backBuf = Unpooled.copiedBuffer(sendBack.getBytes());
        //backBuf.writeBytes(sendBack.getBytes());
        ctx.writeAndFlush(backBuf);
    }

}
