package se.kth.id1212.websocketapplication.server.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import se.kth.id1212.websocketapplication.common.Message;

/**
 *
 * @author Diaco Uthman
 */
@ServerEndpoint("/chatProg")
public class BinaryWebSocketServer {
  private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

  @OnOpen
  public void onOpen(Session session) {
    peers.add(session);
  }

  @OnClose
  public void onClose(Session session) {
    peers.remove(session);
  }

  
  @OnMessage
  public void onMessage(String text){
      System.out.println(text);
  }
  @OnMessage
  public void onMessage(InputStream stream) {
      try {
          ObjectInputStream ois = new ObjectInputStream(stream);
          Message mes = (Message) ois.readObject();
          System.out.println("Objektet har lästs in: " + mes.getBody());
      } catch (IOException | ClassNotFoundException ex) {
          Logger.getLogger(BinaryWebSocketServer.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
}
