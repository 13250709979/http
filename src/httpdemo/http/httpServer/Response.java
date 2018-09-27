package httpdemo.http.httpServer;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {

    private BufferedWriter bufferedWriter;
    private OutputStream outputStream;

    private StringBuilder context;
    private StringBuilder info;
    private int len;

    private final String BLACK=" ";
    private final String CRLF="\r\n";

    public Response(){
        context=new StringBuilder();
        info=new StringBuilder();
        len=0;
    }

    public Response(Socket socket){
        this();
        try {
            outputStream=socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            info=null;
        }
        bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    public Response(OutputStream outputStream){
        this();
        bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    //动态添加内容
    public Response print(String info){
        context.append(info);
        len+=info.getBytes().length;
        return this;
    }
    public Response println(String info){
        context.append(info).append(CRLF);
        len+=(info+CRLF).getBytes().length;
        return this;
    }

    public void createInfo(int code){
        info.append("HTTP/1.1").append(BLACK);
        info.append(code).append(BLACK);
        switch (code){
            case 200:
                info.append("OK").append(CRLF);
                break;
            case 404:
                info.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                info.append("SERVER ERROR").append(CRLF);
                break;
        }

        info.append("Date:").append(new Date()).append(CRLF);
        info.append("Server:").append("Server/0.0.1;charset=GEK").append(CRLF);
        info.append("Context-type:text/html").append(CRLF);
        info.append("Content-length:").append(len).append(CRLF);
        info.append(CRLF);
    }

    public void pushToBrowser(int code) throws IOException {
        if (info==null){
            code=505;
        }
        createInfo(code);
        bufferedWriter.append(info);
        bufferedWriter.append(context);
        bufferedWriter.flush();
    }
}
