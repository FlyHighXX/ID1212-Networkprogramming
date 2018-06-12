package se.kth.id1212.hw2.client.startup;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Scanner;
import se.kth.id1212.hw2.client.net.ClientHandler;
import se.kth.id1212.hw2.client.net.RequestDataEncoder;
import se.kth.id1212.hw2.client.net.ResponseDataDecoder;
/**
 *
 * @author Diaco Uthman
 */
public class NettyClient {

    public static void main(String[] args) throws Exception{
        String host = "localhost";
        int port = 8080;
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                public void initChannel(SocketChannel ch)
                        throws Exception {
                    ch.pipeline().addLast(new RequestDataEncoder(),
                            new ResponseDataDecoder(), new ClientHandler());
                }
            });
            Scanner in = new Scanner(System.in);
            System.out.print("Enter the host: ");
            host = in.nextLine();
            System.out.print("Enter the port number: ");
            port = in.nextInt();
            
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
            
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
