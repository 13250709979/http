package httpdemo.firsthttp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server {

    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        Server server01=new Server();
        server01.start();
        server01.receive();
    }

    public void start() throws IOException {
        serverSocket=new ServerSocket(8888);
    }
    public void receive() throws IOException {
        Socket socket= serverSocket.accept();
        System.out.println("一个客户端建立了连接");

        //获取请求协议
        InputStream inputStream=socket.getInputStream();
        byte[] bytes=new byte[1024*1024];
        int len=inputStream.read(bytes);
        String msg=new String(bytes,0,len);
        System.out.println(msg);

        //返回响应协议
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("<html>");
        stringBuilder.append("<head>");
        stringBuilder.append("<title>");
        stringBuilder.append("界面");
        stringBuilder.append("</title>");
        stringBuilder.append("</head>");
        stringBuilder.append("<body>");
        stringBuilder.append("第一个界面");
        stringBuilder.append("</body>");
        stringBuilder.append("</html>");
        int size=stringBuilder.toString().getBytes().length;

        //1、响应行；2、响应头；3、响应正文
        StringBuilder response=new StringBuilder();
        String black=" ";
        String CRLF="\r\n";
        response.append("HTTP/1.1").append(black);
        response.append(200).append(black);
        response.append("OK").append(CRLF);

        response.append("Date:").append(new Date()).append(CRLF);
        response.append("Server:").append("Server/0.0.1;charset=GEK").append(CRLF);
        response.append("Context-type:text/html").append(CRLF);
        response.append("Content-length:").append(size).append(CRLF);
        response.append(CRLF);

        response.append(stringBuilder.toString());

        OutputStream outputStream=socket.getOutputStream();
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
        bufferedWriter.write(response.toString());
        bufferedWriter.flush();

    }
    public void stop(){

    }
}
