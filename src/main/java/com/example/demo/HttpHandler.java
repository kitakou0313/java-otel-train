package com.example.demo;

import java.nio.charset.StandardCharsets;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpResponseStatus;

public class HttpHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String responseContent = "Hello, Netty HTTP Server!";
        FullHttpResponse response = new DefaultFullHttpResponse(
            msg.protocolVersion(),
            HttpResponseStatus.OK,
            ctx.alloc().buffer().writeBytes(
                responseContent.getBytes(StandardCharsets.UTF_8)
            )
        );

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
