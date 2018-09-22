package httpdemo.http;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Dispatcher implements Runnable {

    private Socket socket;
    private Request request;
    private Response response;
    public Dispatcher(Socket socket){
        this.socket=socket;
        try {
            //获取请求协议
            request=new Request(socket);
            //返回响应协议
            response=new Response(socket);
        } catch (IOException e) {
            e.printStackTrace();
            this.stop();
        }
    }
    @Override
    public void run() {
       if(request.getUrl().equals("")||request.getUrl()==null) {
            try {
                InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("index.html");
                byte[] bytes = new byte[1024 * 1024];
                int len = inputStream.read(bytes);
                response.print(new String(bytes, 0, len));
                response.pushToBrowser(200);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                //解析xml，通过反射返回对于Servlet
                Servlet servlet = Xml.getServletFromUrl(request.getUrl());
                if (null != servlet) {
                    servlet.service(request, response);
                } else {
                    byte[] bytes = new byte[1024 * 1024];
                    InputStream inputStream=Thread.currentThread().getContextClassLoader().getResourceAsStream("error.html");
                    int len = inputStream.read(bytes);
                    response.print(new String(bytes, 0, len));
                    response.pushToBrowser(404);
                }
            } catch (IOException e) {
                e.printStackTrace();
                try {
                    response.pushToBrowser(505);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        stop();
    }

    private void stop(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
