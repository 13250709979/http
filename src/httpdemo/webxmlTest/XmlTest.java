package httpdemo.webxmlTest;


import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class XmlTest {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        //1、获取解析工厂
        //2、从解析工厂获取解析器
        //3、编写处理器
        //4、加载文档Document注册处理器
        //5、解析Xml
        SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
        SAXParser saxParser=saxParserFactory.newSAXParser();
        WebHandler webHandler=new WebHandler();
        saxParser.parse(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("httpdemo/webxmlTest/web.xml"),webHandler);


        List<Entity> entities=webHandler.getEntities();
        List<Mapping> mappings=webHandler.getMappings();
        WebContext webContext=new WebContext(entities,mappings);

        String name=webContext.getClass("/reg");
        Class clazz=Class.forName(name);
        RegisterServlet registerServlet= (RegisterServlet) clazz.newInstance();
        registerServlet.Service();

        String name1=webContext.getClass("/login");
        Class clazz1=Class.forName(name1);
        LoginServlet loginServlet= (LoginServlet) clazz1.newInstance();
        loginServlet.Service();

        System.out.println();
        for (Entity entity:entities){
            System.out.println(entity.getName()+":"+entity.getClazz());
        }
        for (Mapping mapping:mappings){
            System.out.println(mapping.getName()+":"+mapping.getPattern());
        }
    }
}
