package se.kth.id1212.hw2.client.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import java.nio.charset.Charset;
import java.util.List;
import se.kth.id1212.hw2.common.ResponseData;

/**
 *
 * @author Diaco Uthman
 */
public class ResponseDataDecoder extends ReplayingDecoder <ResponseData>{
    @Override
    protected void decode(ChannelHandlerContext chc, ByteBuf in, List<Object> out) throws Exception {
        ResponseData data = new ResponseData();
        data.setIntValue(in.readInt());
        data.setStringValue(in.readCharSequence(data.getIntValue(), Charset.forName("UTF-8")).toString());
        out.add(data);
    }
}
