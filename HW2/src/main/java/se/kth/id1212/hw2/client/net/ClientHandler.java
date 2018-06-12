package se.kth.id1212.hw2.client.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import se.kth.id1212.hw2.client.view.Hangman;
import se.kth.id1212.hw2.common.ResponseData;

/**
 *
 * @author Diaco Uthman
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(new Hangman(ctx)).start();
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception{
        System.out.println((ResponseData)msg);
    }
}
