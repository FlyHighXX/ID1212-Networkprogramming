package se.kth.id1212.hw2.server.net;

import se.kth.id1212.hw2.common.ResponseData;
import se.kth.id1212.hw2.common.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.io.IOException;
import se.kth.id1212.hw2.common.MsgType;
import se.kth.id1212.hw2.server.controller.Controller;

/**
 *
 * @author Diaco Uthman
 */
public class ProcessingHandler extends ChannelInboundHandlerAdapter {

    private final Controller contr = new Controller();
    private ByteBuf tmp;
    private ChannelFuture future;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        System.out.println("Handler Added");
        tmp = ctx.alloc().buffer(4);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        System.out.println("Handler Removed");
        tmp.release();
        tmp = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        RequestData requestData = (RequestData) msg;
        ResponseData responseData = handleMsg(requestData);
        System.out.println(responseData.getStringValue());
        this.future = ctx.writeAndFlush(responseData);
    }

    private ResponseData handleMsg(RequestData requestData) throws IOException {
        ResponseData responseData = new ResponseData();        
        Message message = new Message(requestData.getStringValue());
        switch (message.msgType) {
            case START:
                contr.newGame();
                String word = contr.getWord();
                responseData.setStringValue(word);
                break;
            case GUESS:
                if (!contr.currentGame()) {
                    String ans = "There is no current game. Start a new one by running the start command.";
                    responseData.setStringValue(ans);
                    break;
                }
                try {
                    contr.newGuess(message.msgBody[0]);
                } catch (NullPointerException e) {
                    break;
                }
                if (contr.getRemainingAttempts() == 0) {
                    responseData.setStringValue("Game over");
                    contr.endGame();
                    break;
                }
                if (contr.checkWord()) {
                    String ans = "YOU'VE WON";
                    responseData.setStringValue(ans);
                    break;
                }
                String ans = contr.getWord();
                responseData.setStringValue(ans);
                break;
            case GAMEINFO:
                if (contr.currentGame()) {
                    ans = "Current score: " + contr.getScore()
                            + "\nCurrent remaining attempts: " + contr.getRemainingAttempts();
                    responseData.setStringValue(ans);
                } else {
                    ans = ("Current score: " + contr.getScore()
                            + "\nThere is no current game");
                    responseData.setStringValue(ans);
                }
                break;
            case QUIT:
                future.addListener(ChannelFutureListener.CLOSE);
                break;
            default:
                throw new IOException("Received corrupt message: " + requestData.getStringValue());
        }
        return responseData;
    }

    private static class Message {

        private MsgType msgType;
        private String[] msgBody;
        private final String receivedString;

        private Message(String receivedString) {
            parse(receivedString);
            this.receivedString = receivedString;
        }

        private void parse(String strToParse) {
            try {
                String[] msgTokens = strToParse.split("##");
                this.msgType = MsgType.valueOf(msgTokens[0].toUpperCase());
                if (msgTokens.length < 2) {
                    this.msgBody = null;
                    return;
                }

                String tempStr = msgTokens[1];
                this.msgBody = tempStr.split(" ");
            } catch (Throwable throwable) {
                System.err.println(throwable);
            }
        }
    }
}
