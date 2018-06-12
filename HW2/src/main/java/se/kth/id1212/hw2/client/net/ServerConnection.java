package se.kth.id1212.hw2.client.net;

import io.netty.channel.ChannelHandlerContext;
import java.util.StringJoiner;
import se.kth.id1212.hw2.common.MsgType;
import se.kth.id1212.hw2.common.RequestData;

/**
 *
 * @author Diaco Uthman
 */
public class ServerConnection {
    private final ChannelHandlerContext ctx;
    private final RequestData msg = new RequestData();
    
    public ServerConnection(ChannelHandlerContext ctx){
        this.ctx=ctx;
    }
    
    public void startNewGame(){
        sendMsg(MsgType.START.toString());
    }

    public void disconnect() {
        sendMsg(MsgType.QUIT.toString());
        ctx.close();
    }

    public void newGuess(String word) {
        sendMsg(MsgType.GUESS.toString(),word);
    }
    
    private void sendMsg(String... parts) {
        StringJoiner joiner = new StringJoiner("##");
        for (String part : parts) {
            joiner.add(part);
        }
        msg.setStringValue(joiner.toString());
        ctx.writeAndFlush(msg);
    }

    public void gameInfo() {
        sendMsg(MsgType.GAMEINFO.toString());
    }
}