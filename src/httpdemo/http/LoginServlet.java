package httpdemo.http;

import java.io.IOException;

public class LoginServlet implements Servlet {
    @Override
    public void service(Request request,Response response) throws IOException {
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("界面");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("第一个界面,Code=200");
        response.print("</body>");
        response.print("</html>");

        response.pushToBrowser(200);
    }
}
