package httpdemo.xmlTest;

import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class XmlTest {
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        //1、获取解析工厂
        //2、从解析工厂获取解析器
        //3、编写处理器
        //4、加载文档Document注册处理器
        //5、解析Xml
        SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
        SAXParser saxParser=saxParserFactory.newSAXParser();
        PersonHandler personHandler=new PersonHandler();
        saxParser.parse(Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("httpdemo/xmlTest/test.xml"),personHandler);

        List<Person> list=personHandler.getPersonList();
        System.out.println(list.size());
        for (Person person:list){
            System.out.println(person.getName()+":"+person.getAge());
        }
    }
}
