package se.kth.id1212.hw2.server.net;

import se.kth.id1212.hw2.common.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;

/**
 *
 * @author Diaco Uthman
 */
public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {
    @Override
    protected void encode(ChannelHandlerContext chc, ResponseData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getStringValue().length());
        out.writeCharSequence(msg.getStringValue(), Charset.forName("UTF-8"));
    }
}
