package httpdemo.http.httpTest;

import httpdemo.http.httpServer.Request;
import httpdemo.http.httpServer.Response;

import java.io.IOException;

public interface Servlet {
    public void service(Request request, Response response) throws IOException;
}
