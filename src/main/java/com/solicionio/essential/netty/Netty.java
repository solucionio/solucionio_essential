package com.solicionio.essential.netty;

import com.solicionio.essential.Configuration;
import com.solicionio.essential.netty.handler.WebSocketHandler;
import com.solicionio.essential.utils.UtilConsole;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

public class Netty {
    private UnorderedThreadPoolEventExecutor executorGroup;

    public Netty(int port) throws Exception {
        UtilConsole.log("Netty sunucusu aktifleşiyor...");
        enable(port);
    }

    private void enable(int port) throws Exception {
        ResourceLeakDetector.setLevel( ResourceLeakDetector.Level.DISABLED ); // Eats performance

        EventLoopGroup producer = new NioEventLoopGroup(32);
        EventLoopGroup consumer = new NioEventLoopGroup(32);

        try{
            executorGroup = new UnorderedThreadPoolEventExecutor(32);

            ServerBootstrap bootstrap = new ServerBootstrap()
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .group(producer, consumer)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            ChannelPipeline pipeline = channel.pipeline();

                            pipeline.addLast("httpServerCodec", new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(Short.MAX_VALUE));

                            pipeline.addLast(executorGroup, "httpHandler", new NettyListener());
                        }
                    })
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 100000)
                    .option(ChannelOption.SO_RCVBUF, Integer.MAX_VALUE);

            UtilConsole.log("Netty sunucusu aktif edildi!");
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        }catch(Exception ex){
            ex.printStackTrace();
            UtilConsole.log("Netty sunucusu aktif edilemedi.");
            throw ex;
        }finally{
            producer.shutdownGracefully();
            consumer.shutdownGracefully();
        }
    }

    public static class NettyListener extends ChannelInboundHandlerAdapter {
        WebSocketServerHandshaker handshaker;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg){
            //InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();

            if(msg instanceof HttpRequest){
                HttpRequest httpRequest = (HttpRequest) msg;

                HttpHeaders headers = httpRequest.headers();

                if("Upgrade".equalsIgnoreCase(headers.get(HttpHeaderNames.CONNECTION)) && "WebSocket".equalsIgnoreCase(headers.get(HttpHeaderNames.UPGRADE))){
                    ctx.pipeline().replace(this, "websocketHandler", new WebSocketHandler());
                    handleHandshake(ctx, httpRequest);
                }
            }
        }

        protected void handleHandshake(ChannelHandlerContext ctx, HttpRequest req){
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketURL(req), null, true, 41943040);
            handshaker = wsFactory.newHandshaker(req);

            if(handshaker == null){
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
            }else{
                handshaker.handshake(ctx.channel(), req);
            }
        }

        protected String getWebSocketURL(HttpRequest req){
            return "ws://" + req.headers().get("Host") + req.getUri();
        }
    }
}
