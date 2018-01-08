package se.kth.id1212.chatapplication.client.net;

import javax.json.Json;
import se.kth.id1212.chatapplication.common.MsgType;

/**
 *
 * @author Diaco Uthman
 */
public class JSONObjectBuilder {
    public static String formatMessage(MsgType type,String message, String user) {
        return Json.createObjectBuilder()
            .add("type", type.toString())
            .add("message", message)
            .add("user", user)
        .build().toString();
    }
}
