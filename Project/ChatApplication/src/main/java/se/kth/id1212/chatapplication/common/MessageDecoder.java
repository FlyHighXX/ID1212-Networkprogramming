package se.kth.id1212.chatapplication.common;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Diaco Uthman
 */
public class MessageDecoder implements Decoder.Text<Message> {
    @Override
    public Message decode(String input) throws DecodeException {
        JsonObject jsonObject = Json.createReader(new StringReader(input)).readObject();
        return new Message(MsgType.valueOf(jsonObject.getString("type")),
                                                jsonObject.getString("message"),
                                                jsonObject.getString("user"));
    }

    @Override
    public boolean willDecode(String arg0) {
        return true;
    }

    @Override
    public void init(EndpointConfig ec) {
    }

    @Override
    public void destroy() {
    }
    
}
