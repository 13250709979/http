package httpdemo.xmlTest;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

//编写处理器
public class PersonHandler extends DefaultHandler {

    private List<Person> personList;
    private Person person;
    private String tag;

    public List<Person> getPersonList() {
        return personList;
    }

    @Override
    public void startDocument()throws SAXException {
        personList=new ArrayList<Person>();
    }

    @Override
    public void endDocument()throws SAXException {

    }

    @Override
    public void characters (char ch[], int start, int length) throws SAXException {
        String contents=new String(ch,start,length).trim();

        if (null!=tag) {
            if (tag.equals("age")) {
                if (contents.length() > 0) {
                    person.setAge(Integer.valueOf(contents));
                }
            } else if (tag.equals("name")) {
                person.setName(contents);
            } else {

            }
        }
    }


    @Override
    public void startElement (String uri, String localName,String qName, Attributes attributes) throws SAXException {
        System.out.println(qName+"--->解析开始");
        if (null!=qName) {
            tag = qName;
            if (tag == "person") {
                person = new Person();
            }
        }
    }

    @Override
    public void endElement (String uri, String localName, String qName)throws SAXException {
        System.out.println(qName+"--->解析结束");
        if (null!=qName) {
            if (qName.equals("person")) {
                personList.add(person);
            }
        }
        tag=null;
    }
}
