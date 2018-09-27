package httpdemo.http.httpTest;

import httpdemo.http.httpServer.Request;
import httpdemo.http.httpServer.Response;

import java.io.IOException;

public class RegisterServlet implements Servlet {

    @Override
    public void service(Request request, Response response) throws IOException {
        System.out.println("RegisterServlet");
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("注册界面");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("第二个界面,Code=200");
        response.print("</body>");
        response.print("</html>");

        response.pushToBrowser(200);

    }
}
