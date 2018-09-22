package httpdemo.http;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class Xml {
    private static WebContext webContext;
    static {
        //1、获取解析工厂
        //2、从解析工厂获取解析器
        //3、编写处理器
        //4、加载文档Document注册处理器
        //5、解析Xml
        SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
        SAXParser saxParser= null;
        try {
            saxParser = saxParserFactory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        WebHandler webHandler=new WebHandler();
        try {
            saxParser.parse(Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("httpdemo/http/web.xml"),webHandler);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Entity> entities=webHandler.getEntities();
        List<Mapping> mappings=webHandler.getMappings();
        webContext=new WebContext(entities,mappings);
    }

    public static Servlet getServletFromUrl(String url) throws Exception {
        String name=webContext.getClass("/"+url);
        Class clazz = Class.forName(name);
        Servlet servlet= (Servlet) clazz.newInstance();
        return servlet;
    }
}
