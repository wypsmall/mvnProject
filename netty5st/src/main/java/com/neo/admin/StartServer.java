package com.neo.admin;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.neo.handler.HttpServerHandler;


public class StartServer {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		if (args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		new StartServer().run(port);
	}

	public void run(final int port) throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
			childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
					ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
					/*
					 * 使用HttpObjectAggregator把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse
					 * HttpObjectAggregator这个很关键
					 * 加上，handler写成，FullHttpRequest
					 * HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>
					 * 不加，handlder写成，HttpObject
					 * HttpServerHandler extends SimpleChannelInboundHandler<HttpObject>
					 */
					ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(1024));
					ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
					ch.pipeline().addLast("http-handler", new HttpServerHandler());
				}
			});
			ChannelFuture future = b.bind("0.0.0.0", port).sync();
			System.out.println("HTTP服务器启动，请求URL : " + "http://127.0.0.1:" + port);
			future.channel().closeFuture().sync();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
}
