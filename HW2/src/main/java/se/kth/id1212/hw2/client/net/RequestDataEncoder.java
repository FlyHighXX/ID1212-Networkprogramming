package se.kth.id1212.hw2.client.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.nio.charset.Charset;
import se.kth.id1212.hw2.common.RequestData;

/**
 *
 * @author Diaco Uthman
 */
public class RequestDataEncoder extends MessageToByteEncoder <RequestData>{
    private final Charset charset = Charset.forName("UTF-8");
    @Override
    protected void encode(ChannelHandlerContext chc, RequestData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getIntValue());
        out.writeInt(msg.getStringValue().length());
        out.writeCharSequence(msg.getStringValue(), charset);
    }
}
