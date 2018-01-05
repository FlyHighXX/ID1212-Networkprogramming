package se.kth.id1212.chatapplication.server.startup;

import java.util.Scanner;
import javax.websocket.DeploymentException;
import se.kth.id1212.chatapplication.server.net.ChatServer;

/**
 *
 * @author Diaco Uthman
 */
public class ServerStartUp {
    public static void main(String[] args){
        org.glassfish.tyrus.server.Server server =
           new org.glassfish.tyrus.server.Server("localhost", 8080, "/ChatApplication", ChatServer.class);
        
        try{
            server.start();
            System.out.println("Press any key to stop the server");
            new Scanner(System.in).nextLine();
        }catch(DeploymentException e){
            throw new RuntimeException(e);
        }finally{
            server.stop();
        }
    }
}
