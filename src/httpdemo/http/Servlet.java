package httpdemo.http;

import java.io.IOException;

public interface Servlet {
    public void service(Request request,Response response) throws IOException;
}
