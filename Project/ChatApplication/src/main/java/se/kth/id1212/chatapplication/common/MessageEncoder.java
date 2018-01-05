package se.kth.id1212.chatapplication.common;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import se.kth.id1212.chatapplication.client.net.JSONObjectBuilder;

/**
 *
 * @author Diaco Uthman
 */
public class MessageEncoder implements Encoder.Text<Message>{

    @Override
    public String encode(Message message) throws EncodeException {
        return JSONObjectBuilder.formatMessage(message.getType(),message.getBody(), message.getSender());
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
    
}
