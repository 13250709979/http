package httpdemo.http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private boolean isRunning;

    public static void main(String[] args) throws IOException {
        Server server=new Server();
        server.start();
        server.receive();
    }

    public void start() throws IOException {
        serverSocket=new ServerSocket(8888);
        isRunning=true;
    }

    public void receive()  {
        while (isRunning) {
            try {
                Socket socket = serverSocket.accept();
                System.out.println("一个客户端建立了连接");
                new Thread(new Dispatcher(socket)).start();
                System.out.println("继续监听");
            } catch (IOException e) {
                e.printStackTrace();
                stop();
            }

        }

    }
    public void stop(){
        isRunning=false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
