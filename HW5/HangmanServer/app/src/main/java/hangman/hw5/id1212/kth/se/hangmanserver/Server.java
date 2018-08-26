package hangman.hw5.id1212.kth.se.hangmanserver;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server extends AsyncTask<Void, Void, String> {
    MainActivity activity;
    TextView infoip;
    ServerSocket serverSocket;
    static final int socketServerPORT = 8080;
    String ip="";
    String message="";

    public Server(MainActivity activity){
        this.activity=activity;
        this.infoip=activity.infoip;
        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    public int getPort(){
        return socketServerPORT;
    }

    public String getIpAddress(){
        return this.ip;
    }

    public void onDestroy(){
        if(serverSocket != null){
            try{
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected String doInBackground(Void... arg0) {
        try{
            ip = InetAddress
                    .getLocalHost().getHostAddress();
            System.out.println("IpAddress is: " + ip);
        }catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }

    @Override
    protected void onPostExecute(String result){
        this.infoip.setText("Server IP: " + getIpAddress() + "\nServer Port: " + getPort());
        super.onPostExecute(result);
    }

    private class SocketServerThread extends Thread{
        int count=0;
        @Override
        public void run(){
            try{
                // Create ServerSocket using specified port
                serverSocket = new ServerSocket(socketServerPORT);

                while(true){
                    Socket socket = serverSocket.accept();
                    count++;
                    message+="#" + count + " from " + socket.getInetAddress() + ": " + socket.getPort() + "\n";
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.msg.setText(message);
                        }
                    });

                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket, count);
                    socketServerReplyThread.run();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SocketServerReplyThread extends Thread{
        private Socket hostThreadSocket;
        int count;

        SocketServerReplyThread(Socket socket, int count){
            this.hostThreadSocket=socket;
            this.count=count;
        }

        @Override
        public void run(){
            OutputStream outputStream;
            String msgReply = "Hello from server, you are #" + count;
            try{
                outputStream = hostThreadSocket.getOutputStream();
                PrintStream printStream = new PrintStream(outputStream);
                printStream.print(msgReply);
                printStream.close();

                message += "replied:  " + msgReply + "\n";
            } catch (IOException e) {
                e.printStackTrace();
                message += "Something went wrong!" + e.toString() + "\n";
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activity.msg.setText(message);
                }
            });
        }
    }
}
