package se.kth.id1212.chatapplication.common;

/**
 *
 * @author Diaco Uthman
 */
public class Message {
    private MsgType type;
    private final String body;
    private String sender;
    
    public Message(MsgType type, String body, String sender){
        this.type=type;
        this.body=body;
        this.sender=sender;
    }
    
    public String getBody() {
        return body;
    }

    public String getSender() {
        return sender;
    }
    
    public MsgType getType(){
        return type;
    }

    public void setUsername(String name) {
        this.sender=name;
    }
}
