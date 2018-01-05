package se.kth.id1212.chatapplication.server.net;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import se.kth.id1212.chatapplication.common.Message;
import se.kth.id1212.chatapplication.common.MessageDecoder;
import se.kth.id1212.chatapplication.common.MessageEncoder;
import se.kth.id1212.chatapplication.common.MsgType;
import se.kth.id1212.chatapplication.server.model.Conversation;

/**
 *
 * @author Diaco Uthman
 */
@ServerEndpoint(value = "/chatProg",encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ChatServer {
    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());
    private final Conversation conversation = new Conversation();
    
    @OnOpen
    public void onOpen(Session session) throws IOException, EncodeException{
        System.out.println(session.getId()+" has joined the conversation");
        peers.add(session);
    }
    
    @OnMessage
    public void onMessage(Message message, Session session) throws IOException, EncodeException{
        switch(message.getType()){
            case NEWUSER :
                session.getUserProperties().put("user",message.getSender());
                break;
            case NEWMESSAGE :
                message.setUsername((String)session.getUserProperties().get("user"));
                for(Session peer : peers){
                    if(!session.getId().equals(peer.getId()) && conversation.checkUser(peer)){
                        peer.getBasicRemote().sendObject(message);
                    }
                }
                break;
            case SHOWUSER :
                for(Session peer : peers){
                    if(!session.getId().equals(peer.getId())){
                        message.setUsername((String)peer.getUserProperties().get("user"));
                        session.getBasicRemote().sendObject(message);
                    }
                }
                break;
            case JOINCONVERSATION :
                conversation.addUser(session);
                break;
            case LEAVECONVERSATION :
                conversation.removeUser(session);
                break;
            default :
                System.out.println("The received message has a corrupt message type");
        }
    }
    
    @OnClose
    public void onClose(Session session) throws IOException, EncodeException{
        System.out.println(session.getId()+" has left the conversation");
        peers.remove(session);
        String userString = (String) session.getUserProperties().get("user") + " has left the conversation";
        Message message = new Message(MsgType.BROADCAST,userString,"Server");
        for(Session peer : peers){
            peer.getBasicRemote().sendObject(message);
        }
    }
}
