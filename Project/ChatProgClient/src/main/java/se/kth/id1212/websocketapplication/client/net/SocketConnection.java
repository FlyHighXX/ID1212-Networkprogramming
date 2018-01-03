package se.kth.id1212.websocketapplication.client.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import se.kth.id1212.websocketapplication.common.Message;

/**
 *
 * @author Diaco Uthman
 */
@ClientEndpoint
public final class SocketConnection {
    private Session session;
    
    public SocketConnection(){
        connect();
    }
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
    }
    
    public void onClose(Session session){
        
    }
    public void connect() {
        WebSocketContainer con = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = URI.create("ws://localhost:8080/BinaryWebSocketServer/chatProg");
            con.connectToServer(this,uri);
        } catch (DeploymentException | IOException ex) {
            Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sentTExt(String text){
        try {
            session.getBasicRemote().sendText(text);
        } catch (IOException ex) {
            Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void sendMessage(Message mes) {
        OutputStream stream;
        try {
            stream = this.session.getBasicRemote().getSendStream();
            ObjectOutputStream objStream = new ObjectOutputStream(stream);
            objStream.writeObject(mes);
        } catch (IOException ex) {
            Logger.getLogger(SocketConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
