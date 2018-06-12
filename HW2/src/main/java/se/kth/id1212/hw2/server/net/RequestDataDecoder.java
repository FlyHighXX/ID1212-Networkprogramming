package se.kth.id1212.hw2.server.net;

import se.kth.id1212.hw2.common.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 *
 * @author Diaco Uthman
 */
public class RequestDataDecoder extends ReplayingDecoder<RequestData> {
    private final Charset charset = Charset.forName("UTF-8");
    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
        RequestData requestData = new RequestData();
        requestData.setIntValue(in.readInt());
        int strLen = in.readInt();
        requestData.setStringValue(in.readCharSequence(strLen, charset).toString());
        out.add(requestData);
    }
}
