package se.kth.id1212.chatapplication.client.net;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import org.glassfish.tyrus.client.ClientManager;
import se.kth.id1212.chatapplication.common.MsgType;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import se.kth.id1212.chatapplication.common.Message;
import se.kth.id1212.chatapplication.common.MessageDecoder;
import se.kth.id1212.chatapplication.common.MessageEncoder;

/**
 *
 * @author Diaco Uthman
 */
@ClientEndpoint(encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class ServerConnection {
    private Session session;
    private final OutputHandler out;
    
    public ServerConnection(OutputHandler outputHandler){
        this.out=outputHandler;
        connect();
    }
    
    @OnOpen
    public void onOpen(Session session){
        System.out.println("Sessionen har lagts in på klienten");
        this.session=session;
    }
    
    @OnClose
    public void onClose(Session session){
        connect();
    }
    
    @OnMessage
    public void onMessage(Message message){
        switch(message.getType()){
            case NEWMESSAGE :
                out.printMessage(message);
                break;
            case SHOWUSER :
                out.printUsername(message);
            default :
                break;
        }
        
    }
    
    public void sendMessage(String input) {
        try {
            session.getBasicRemote().sendText(JSONObjectBuilder.formatMessage(MsgType.NEWMESSAGE,input,""));
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void connect(){
        try {
            URI uri = URI.create("ws://localhost:8080/ChatApplication/chatProg");
            
            ClientManager client = ClientManager.createClient();
            this.session = client.connectToServer(this,uri);
        } catch (DeploymentException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setUsername(String userName) {
        try {
            session.getBasicRemote().sendText(JSONObjectBuilder.formatMessage(MsgType.NEWUSER,"",userName));
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void showUsers() {
        try {
            session.getBasicRemote().sendText(JSONObjectBuilder.formatMessage(MsgType.SHOWUSER,"",""));
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enterConversation() {
        try {
            session.getBasicRemote().sendText(JSONObjectBuilder.formatMessage(MsgType.JOINCONVERSATION,"",""));
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void leaveConversation() {
        try {
            session.getBasicRemote().sendText(JSONObjectBuilder.formatMessage(MsgType.LEAVECONVERSATION,"",""));
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
