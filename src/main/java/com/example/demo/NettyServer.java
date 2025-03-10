package com.example.demo;

import io.netty.channel.nio.NioEventLoopGroup;

public class NettyServer {
    private final int port;

    public NettyServer(int port){
        this.port = port;
    }

    public void start() throws InterruptedException{
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);

        
    }

    public static void main(String[] args) {
        int port = 8080;
        new NettyServer(port);
    }
    
}
