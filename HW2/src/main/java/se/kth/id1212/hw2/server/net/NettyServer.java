package se.kth.id1212.hw2.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * @author Diaco Uthman
 */
public class NettyServer {
    private int port;
    
    private NettyServer(int portNo){
        this.port=portNo;
    }
    public static void main(String[] args) throws Exception{
        int portNo = 8080;
        new NettyServer(portNo).run();
    }
    
    private void run() throws Exception{
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel c) throws Exception {
                    c.pipeline().addLast(new RequestDataDecoder(),
                            new ResponseDataEncoder(),
                            new ProcessingHandler());
                }
            }).option(ChannelOption.SO_BACKLOG, 128)
              .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture f = b.bind(this.port).sync();
            f.channel().closeFuture().sync();
        }finally{
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
        
        
        
        
        
    }
}
