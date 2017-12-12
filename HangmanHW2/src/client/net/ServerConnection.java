package client.net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerConnection implements Runnable{
    private SocketChannel socket;
    private ArrayList<String> messagesToSend = new ArrayList();
    private InetSocketAddress serverAddress;
    private boolean connected = false;
    private Selector selector;
    private boolean timeToSend;
    private Iterable<CommunicationHandler> listeners;
    @Override
    public void run() {
        try {
            initConnection();
            initSelector();
            
            while(connected || !messagesToSend.isEmpty()){
                if(timeToSend){
                    socket.keyFor(selector).interestOps(SelectionKey.OP_WRITE);
                    timeToSend = false;
                }
                
                selector.select();
                
                for(SelectionKey key : selector.selectedKeys()){
                    selector.selectedKeys().remove(key);
                    if(!key.isValid()){
                        continue;
                    }
                    if(key.isConnectable()){
                        completeConnection(key);
                    }
                    else if(key.isReadable()){
                        readFromServer(key);
                    }
                    else if(key.isWritable()){
                        writeToServer(key);
                    }
                    
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            runDisconnect();
        } catch (IOException ex) {
            Logger.getLogger(ServerConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void connect(String host, int port){
        serverAddress = new InetSocketAddress(host,port);
        new Thread(this).start();
    }

    private void initConnection() throws IOException {
        socket = SocketChannel.open();
        socket.configureBlocking(false);
        socket.connect(serverAddress);
        connected = true;
    }

    private void initSelector() throws IOException {
        selector = Selector.open();
        socket.register(selector, SelectionKey.OP_CONNECT);
    }

    private void completeConnection(SelectionKey key) throws IOException {
        socket.finishConnect();
        key.interestOps(SelectionKey.OP_READ);
        try{
            InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteAddress();
            notifyConnection(remoteAddress);
        }catch(IOException UseDefaultInstead){
            notifyConnection(serverAddress);
        }
    }

    private void readFromServer(SelectionKey key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void writeToServer(SelectionKey key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void runDisconnect() throws IOException {
        socket.close();
        socket.keyFor(selector).cancel();
        notifyDisconnect();
    }

    private void notifyDisconnect() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void notifyConnection(InetSocketAddress connectedAddress) {
        Executor pool = ForkJoinPool.commonPool();
        for (CommunicationHandler listener : listeners) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    listener.connected(connectedAddress);
                }
            });
        }
    }
}